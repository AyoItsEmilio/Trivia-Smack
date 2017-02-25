"""
DataAccessTest.py
"""
import unittest
from .DataAccessStub import DataAccessStub
from web_app.application.Services import Services
from web_app.objects.Question import Question

class DataAccessTest(unittest.TestCase):
    """Unit tests for the DataAccess class"""

    @classmethod
    def setUpClass(cls):
        """Call once"""
        Services.close_data_access()
        Services.create_data_access(\
            altDataAccessService=DataAccessStub("application"))

    @classmethod
    def tearDownClass(cls):
        """Call once"""
        Services.close_data_access()

    def setUp(self):
        self.data_access = Services.get_data_access()
        self.db_size = 9

    def tearDown(self):
        self.data_access = None

    def test_get_random_question(self):
        """Test the get_random_question method"""
        print "Testing DataAccess: get_random_question"

        question_obj = self.data_access.get_random_question()

        self.assertIsInstance(question_obj, Question)
        self.assertIsNotNone(question_obj)

    def test_get_all_questions(self):
        """Test the get_all_questions method"""
        print "Testing DataAccess: get_all_questions"

        all_questions = self.data_access.get_all_questions()

        self.assertIsInstance(all_questions, list)
        self.assertEquals(len(all_questions), self.db_size)
        self.assertEquals(all_questions[0].question,\
                          "How much does a male Polar Bear weigh?")

    def test_get_num_questions(self):
        """Test the get_num_question method"""
        print "Testing DataAccess: get_num_questions"

        num_questions = self.data_access.get_num_questions()

        self.assertIsInstance(num_questions, int)
        self.assertEquals(self.db_size, num_questions)
