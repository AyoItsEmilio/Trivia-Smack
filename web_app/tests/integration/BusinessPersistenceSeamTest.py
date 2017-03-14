"""
BusinessPersistenceSeamTest.py
"""
import unittest
from web_app.business.AccessQuestions import AccessQuestions
from web_app.application.Services import Services
from web_app import DB_NAME

class BusinessPersistenceSeamTest(unittest.TestCase):
    """Integration Tests for AccessQuestions"""

    @classmethod
    def setUpClass(cls):
        Services.close_data_access()
        Services.create_data_access(DB_NAME)

    def setUp(self):
        self.big_num = 100
        self.db_num_questions = 9
        self.access_questions = AccessQuestions()

    def tearDown(self):
        self.access_questions = None

    def test_get_random_questions(self):
        print "Testing AccessQuestions: get_random_questions"

        num_questions = 3
        questions = self.access_questions.get_random_questions(num_questions)

        self.assertIsNotNone(questions)
        self.assertEquals(len(questions), num_questions)

        for question in questions:
            self.assertIsNotNone(question.question)
            self.assertIsNotNone(question.options)
            self.assertIsNotNone(question.answer)

        for index_i, value_i in enumerate(questions):
            for index_j, value_j in enumerate(questions):
                if index_i != index_j:
                    self.assertNotEquals(value_i.question, value_j.question)

        num_questions = 0
        questions = self.access_questions.get_random_questions(num_questions)

        self.assertIsNotNone(questions)
        self.assertEquals(questions, [])
        self.assertEquals(len(questions), num_questions)

        num_questions = self.access_questions.get_num_questions()

        questions = self.access_questions.get_random_questions\
                (num_questions*self.big_num)

        self.assertEquals(len(questions), num_questions)

        num_questions = -1
        questions = self.access_questions.get_random_questions(num_questions)

        self.assertEquals(questions, [])

    def test_get_all_questions(self):
        print "Testing AccessQuestions: get_all_questions"

        questions = self.access_questions.get_all_questions()

        self.assertNotEquals(len(questions), 0)
        self.assertIsNotNone(questions)
        self.assertNotEquals(questions, [])
        self.assertEquals(len(questions), self.db_num_questions)

        for question in questions:
            self.assertIsNotNone(question.question)
            self.assertIsNotNone(question.options)
            self.assertIsNotNone(question.answer)

    def test_get_num_questions(self):
        print "Testing AccessQuestions: get_num_questions"

        num_questions = self.access_questions.get_num_questions()

        self.assertEquals(num_questions, self.db_num_questions)