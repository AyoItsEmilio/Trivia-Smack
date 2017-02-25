"""
DataAccessObject.py
"""
import random
import pymongo
from json import dumps, loads
from bson import json_util, ObjectId
from web_app.persistence.DataAccessInterface import DataAccessInterface
from web_app.objects.Question import Question
from flask.ext.pymongo import MongoClient


class DataAccessObject(DataAccessInterface):
    """For directly querying the MongoDB"""
    def __init__(self, name):
        self.db_name = name
        self.mongo = None
        self.client = None

    def open(self):
        """Opened the database"""
        try:
            self.client = MongoClient()
            self.mongo = self.client[self.db_name]
        except pymongo.errors.ConnectionFailure, e:
            raise "Could not connect to MongoDB: {}".format(e)

    def close(self):
        """Close the databse"""
        print "Closed the database"
        self.client.close()

    @staticmethod
    def clean(doc):
        """Convert the unicode types to strings"""
        doc["question"] = str(doc["question"])
        doc["options"] = [str(o) for o in doc["options"]]

    def get_random_question(self):
        """Grab a random question from the DB"""
        doc = None
        num_qs = self.get_num_questions()
        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0
        doc_cursor = self.mongo.questions.find().limit(1).skip(rq_num)

        if doc_cursor.count() > 0:
            doc = doc_cursor[0]
            DataAccessObject.clean(doc)

        return Question(doc["question"], doc["options"], doc["answer"])

    def get_question(self, _id):
        """Grabs a question from the DB by its id string"""
        return self.mongo.questions.find_one(
            {'_id': ObjectId(_id)})

    def get_all_questions(self):
        """Return a list of all the questions"""
        result = []

        for doc in self.mongo.questions.find():
            DataAccessObject.clean(doc)
            result.append(Question(doc["question"],
                          doc["options"], doc["answer"]))

        return result

    def get_num_questions(self):
        """Return the number of questions"""
        return self.mongo.questions.count()

    def insert_question(self, question, options, answer):
        """Inserts it into the db"""
        self.mongo.questions.insert_one({
            "question": question,
            "options": options,
            "answer": answer
        })

    def update_question(self, _id, question=None, options=None, answer=None):
        q = self.mongo.questions.find_one({'_id': ObjectId(_id)})

        if q is not None:
            if question is not None:
                q['question'] = question
            if options is not None:
                q['options'] = options
            if answer is not None:
                q['answer'] = answer

            self.mongo.questions.update(
                {"_id": _id}, q
            )

    def delete_question(self, _id):
        self.mongo.questions.remove({'_id': ObjectId(_id)})
