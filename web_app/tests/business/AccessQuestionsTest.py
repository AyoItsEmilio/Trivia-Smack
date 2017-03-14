"""
AccessQuestionsTest.py
"""
import unittest
from ..persistence.DataAccessStub import DataAccessStub
from web_app.application.Services import Services
from web_app.business.AccessQuestions import AccessQuestions
from web_app.objects.Question import Question

class AccessQuestionsTest(unittest.TestCase):
    """Unit tests for the AccessQuestions class"""

    @classmethod
    def setUpClass(cls):
        Services.close_data_access()
        Services.create_data_access(\
            altDataAccessService=DataAccessStub("application"))

    @classmethod
    def tearDownClass(cls):
        Services.close_data_access()

    def setUp(self):
        self.access_questions = AccessQuestions()
        self.db_size = 9

    def tearDown(self):
        self.access_questions = None

    def test_get_all_questions(self):
        print "Testing AccessQuestions: get_all_questions"

        all_questions = self.access_questions.get_all_questions()
        self.assertEquals(self.db_size, len(all_questions))

        for question_obj in all_questions:
            self.assertIsInstance(question_obj, Question)

    def test_get_num_questions(self):
        print "Testing AccessQuestions: get_num_questions"

        num_questions = self.access_questions.get_num_questions()
        self.assertEquals(self.db_size, num_questions)


