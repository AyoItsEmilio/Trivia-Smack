"""
BusinessPersistenceSeamTest.py
"""
import unittest
import re
from web_app.objects.Question import Question
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

    def test_get_question(self):
        print "Testing AccessQuestions: get_question"
        question = "Platypuses lay eggs"
        target_question = Question(question, ["true", "false"], 0)

        question_obj = self.access_questions.get_question(question=question)[0]

        self.assertEquals(target_question.question, question_obj.question)
        self.assertEquals(target_question.options, question_obj.options)
        self.assertEquals(target_question.answer, question_obj.answer)

        question = re.compile("^Platypus")

        question_obj = self.access_questions.get_question(question=question)[0]

        self.assertEquals(target_question.question, question_obj.question)
        self.assertEquals(target_question.options, question_obj.options)
        self.assertEquals(target_question.answer, question_obj.answer)

    def test_delete_question(self):
        print "Testing AccessQuestion: delete_question"

        question = "The Balkans are in:"
        question_obj = self.access_questions.get_question(question=question)[0]

        orig_size = self.access_questions.get_num_questions()

        self.access_questions.delete_question(question=question)

        all_questions = self.access_questions.get_all_questions()

        self.assertEquals(len(all_questions), orig_size-1)

        question_none = self.access_questions.get_question(question=question)

        self.assertEquals(question_none, [])

        self.access_questions.add_question(question_obj.question,
                                           question_obj.options,
                                           question_obj.answer)

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
