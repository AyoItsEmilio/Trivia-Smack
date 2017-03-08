"""
DataAccessObject.py
"""

from flask.ext.pymongo import MongoClient
import unittest
from web_app.application.Services import Services
TEST_DB_NAME = "test"


class DataAccessObjectTest(unittest.TestCase):
    '''
    Very rough draft of testing DataAccessObject.

    To run:
    python -m unittest web_app.tests.persistence.DataAccessObjectTest
    '''

    def setUp(self):
        Services.create_data_access(
            dbName=TEST_DB_NAME)
        self.data_access = Services.get_data_access()

        self.client = MongoClient()
        self.mongo = self.client[TEST_DB_NAME]

    def tearDown(self):
        """Call once"""
        Services.close_data_access()
        self.client.drop_database(TEST_DB_NAME)

    def test_insert_question(self):
        print 'testing insert question'
        question = "How many breeds of cats exist?"
        options = [16, 36, 74, 90]
        answer = 2
        question_id = self.data_access.insert_question(
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
