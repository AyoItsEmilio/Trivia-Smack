"""
DataAccessObject.py
"""
import pymongo
from json import dumps, loads
from bson import json_util, ObjectId
from web_app.persistence.DataAccessInterface import DataAccessInterface
from web_app.objects.Question import Question
from flask.ext.pymongo import MongoClient
import unittest
from web_app.persistence.DataAccessObject import DataAccessObject
from web_app.application.Services import Services
from web_app.objects.Question import Question
from web_app.populate_database import populate_db

TEST_DB_NAME = "test"


class DataAccessObjectTest(unittest.TestCase):
    '''
    Very rough draft of testing DataAccessObject.

    To run:
    python -m unittest web_app.tests.persistence.DataAccessObjectTest
    '''

    @classmethod
    def setUpClass(cls):
        """Call once"""
        cls.obj = DataAccessObject(TEST_DB_NAME)
        cls.obj.open()
        cls.client = MongoClient()
        cls.mongo = cls.client[TEST_DB_NAME]

    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_insert_question(self):
        print 'testing insert question'
        question = "How many breeds of cats exist?"
        options = [16, 36, 74, 90]
        answer = 2
        question_id = self.obj.insert_question(
            question=question,
            options=options,
            answer=answer
        )
        inserted_question = self.mongo.questions.find_one(
            {'_id': question_id})

        self.assertIsNotNone(inserted_question)
        self.assertEqual(question, inserted_question['question'])
        self.assertEqual(options, inserted_question['options'])
        self.assertEqual(answer, inserted_question['answer'])

    @classmethod
    def tearDownClass(cls):
        """Call once"""
        Services.close_data_access()
        cls.client.drop_database(TEST_DB_NAME)
