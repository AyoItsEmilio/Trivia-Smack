"""
DataAccessObject.py
"""

from bson import ObjectId
from flask.ext.pymongo import MongoClient
import pymongo
import random

from web_app.persistence.DataAccessInterface import DataAccessInterface
from web_app.objects.Question import Question


class DataAccessObject(DataAccessInterface):
    """For directly querying the MongoDB"""

    def __init__(self, name):
        self.db_name = name
        self.mongo = None
        self.client = None

    def open(self):
        try:
            self.client = MongoClient()
            self.mongo = self.client[self.db_name]
        except pymongo.errors.ConnectionFailure, e:
            raise "Could not connect to MongoDB: {}".format(e)

    def close(self):
        print "Closed the database"
        self.client.close()

    @staticmethod
    def clean(doc):
        doc["question"] = str(doc["question"])
        doc["options"] = [str(o) for o in doc["options"]]

    def get_random_question(self):
        doc = None
        num_qs = self.get_num_questions()
        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0
        doc_cursor = self.mongo.questions.find().limit(1).skip(rq_num)

        if doc_cursor.count() > 0:
            doc = doc_cursor[0]
            DataAccessObject.clean(doc)

        return Question(doc["_id"], doc["question"],
                        doc["options"], doc["answer"])

    def get_question(self, **kwargs):
        return self.mongo.questions.find_one(kwargs)

    def get_all_questions(self):
        result = []

        for doc in self.mongo.questions.find():
            DataAccessObject.clean(doc)
            result.append(Question(doc["_id"], doc["question"],
                          doc["options"], doc["answer"]))

        return result

    def get_num_questions(self):
        return self.mongo.questions.count()

    def insert_question(self, question, options, answer):
        """Inserts it into the db"""
        return self.mongo.questions.insert_one({
            "question": question,
            "options": options,
            "answer": answer
        }).inserted_id

    def update_question(self, **kwargs):
        """
        Updates an existing question.
        """
        orig_question = dict()

        if '_id' in kwargs:
            orig_question['_id'] = kwargs['_id']
        if 'question' in kwargs:
            orig_question['question'] = kwargs['question']
        if 'options' in kwargs:
            orig_question['options'] = kwargs['options']
        if 'answer' in kwargs:
            orig_question['answer'] = kwargs['answer']

        updated_question = self.get_question(**orig_question)

        if updated_question is not None:
            if 'new_question' in kwargs:
                updated_question['question'] = kwargs['new_question']
            if 'new_options' in kwargs:
                updated_question['options'] = kwargs['new_options']
            if 'new_answer' in kwargs:
                updated_question['answer'] = kwargs['new_answer']

        self.mongo.questions.update(orig_question, updated_question)

    def delete_question(self, **kwargs):
        """
        Deletes an existing questions.
        """
        self.mongo.questions.remove(kwargs)
