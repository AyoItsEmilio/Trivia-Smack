"""
DataAccessObject.py
"""
import random
import pymongo
from flask_pymongo import MongoClient
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
        except pymongo.errors.ConnectionFailure, conn_exception:
            raise "Could not connect to MongoDB: {}".format(conn_exception)

    def close(self):
        print "Closed the database"
        self.client.close()

    @staticmethod
    def clean(doc):
        doc["question"] = str(doc["question"])
        doc["options"] = [str(o) for o in doc["options"]]

    def get_question(self):
        doc = None
        num_qs = self.get_num_questions()
        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0
        doc_cursor = self.mongo.questions.find().limit(1).skip(rq_num)

        if doc_cursor.count() > 0:
            doc = doc_cursor[0]
            DataAccessObject.clean(doc)

        return Question(doc["question"], doc["options"], doc["answer"])

    def get_all_questions(self):
        result = []

        for doc in self.mongo.questions.find():
            DataAccessObject.clean(doc)
            result.append\
            (Question(doc["question"], doc["options"], doc["answer"]))

        return result

    def get_num_questions(self):
        return self.mongo.questions.count()

