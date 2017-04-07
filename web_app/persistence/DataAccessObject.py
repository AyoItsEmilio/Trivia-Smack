"""
DataAccessObject.py
"""
import random
from flask_pymongo import MongoClient
import pymongo
from web_app.persistence.DataAccessInterface import DataAccessInterface
from web_app.objects.Question import Question
from web_app import MONGO_ADDR, MONGO_PORT

class DataAccessObject(DataAccessInterface):
    """For directly querying the MongoDB"""

    def __init__(self, name):
        self.db_name = name
        self.mongo = None
        self.client = None

    def open(self):
        try:
            self.client = MongoClient(MONGO_ADDR, MONGO_PORT)
            self.mongo = self.client[self.db_name]

        except pymongo.errors.ConnectionFailure, conn_exception:
            raise "Could not connect to MongoDB: {}".format(conn_exception)

    def close(self):
        print "Closed the database"
        self.client.close()

    @staticmethod
    def clean(doc):
        doc["question"] = str(doc["question"]).strip()
        doc["category"] = str(doc["category"]).strip()
        doc["options"] = [str(o).strip() for o in doc["options"]]

    def get_random_question(self, category=None):
        result = None
        doc = None

        if category is None or category == "all":
            category_filter = {}
            num_qs = self.get_num_questions()
        else:
            category_filter = {"category":category}
            num_qs = self.mongo.questions.count(category_filter)

        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0

        doc_cursor = self.mongo.questions.find(category_filter).skip(rq_num)

        if doc_cursor.count() > 0:
            doc = doc_cursor[0]
            DataAccessObject.clean(doc)
            result = Question(doc["question"], doc["options"], doc["answer"], doc["category"])

        return result

    def get_question(self, **kwargs):
        result = []

        for doc in self.mongo.questions.find(kwargs):
            DataAccessObject.clean(doc)
            result.append(Question(doc["question"],\
                                   doc["options"],\
                                   doc["answer"],\
                                   doc["category"]))

        return result

    def get_all_questions(self):
        result = []

        for doc in self.mongo.questions.find():
            DataAccessObject.clean(doc)
            result.append(Question(doc["question"],\
                                   doc["options"],\
                                   doc["answer"],\
                                   doc["category"]))

        return result

    def get_num_questions(self):
        return self.mongo.questions.count()

    def insert_question(self, question, options, answer, category):
        return self.mongo.questions.insert_one({
            "question": question,
            "options": options,
            "answer": answer,
            "category": category,
        }).inserted_id

    def update_question(self, **kwargs):
        result = None
        update = {}

        if "new_question" in kwargs:
            update["question"] = kwargs["new_question"]
        if "new_options" in kwargs:
            update["options"] = kwargs["new_options"]
        if "new_answer" in kwargs:
            update["answer"] = kwargs["new_answer"]

        if update and kwargs.get("old_question", None):
            result = self.mongo.questions.find_and_modify(\
                        query={"question":kwargs["old_question"]},\
                        update={"$set": update})

        return result

    def delete_question(self, **kwargs):
        result = self.mongo.questions.remove(kwargs)
        return result
