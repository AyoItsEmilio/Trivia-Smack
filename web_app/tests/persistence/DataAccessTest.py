"""
DataAccessTest.py
"""
import unittest
from .DataAccessStub import DataAccessStub
from web_app.application.Services import Services
from web_app.objects.Question import Question

class DataAccessTest(unittest.TestCase):
    """Tests for the DataAccess class"""

    @classmethod
    def tearDownClass(cls):
        Services.close_data_access()

    @classmethod
    def data_access_test(cls, self):
        
        print "Testing DataAccess: get_question"

        data_access = Services.get_data_access()

        question_obj = data_access.get_question()

        self.assertIsInstance(question_obj, Question)
        self.assertIsNotNone(question_obj)

        print "Testing DataAccess: get_all_questions"

        db_size = 9

        data_access = Services.get_data_access()

        all_questions = data_access.get_all_questions()

        self.assertIsInstance(all_questions, list)
        self.assertEquals(len(all_questions), db_size)
        self.assertEquals(all_questions[0].question,\
                          "How much does a male Polar Bear weigh?")

        print "Testing DataAccess: get_num_questions"

        db_size = 9

        data_access = Services.get_data_access()

        num_questions = data_access.get_num_questions()

        self.assertIsInstance(num_questions, int)
        self.assertEquals(db_size, num_questions)

    def test_data_access(self):
        print "Testing DataAccess using db stub"

        Services.close_data_access()
        Services.create_data_access(\
            altDataAccessService=DataAccessStub("application"))

        DataAccessTest.data_access_test(self)
