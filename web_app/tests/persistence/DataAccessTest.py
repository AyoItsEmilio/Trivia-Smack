"""
DataAccessTest.py
"""
import unittest
from .DataAccessStub import DataAccessStub
from web_app.application.Services import Services
from web_app.objects.Question import Question


class DataAccessTest(unittest.TestCase):
    """Unit tests for the DataAccess class"""

    def setUp(self):
        '''
        Set everything up after each test because some tests
        manipulate the database
        '''
        Services.create_data_access(
            altDataAccessService=DataAccessStub("application"))
        self.data_access = Services.get_data_access()
        self.db_size = 9

    def tearDown(self):
        '''
        Get rid of stub database instance after each test because some
        tests manipulate the database.
        '''
        Services.close_data_access()
        self.data_access = None

    def test_get_question(self):
        print "Testing DataAccess: get_question"

        target_question = Question(2, "Platypuses lay eggs",
                                   ["true", "false"], 0)

        question_obj = self.data_access.get_question(_id=2)
        self.assertEquals(target_question._id, question_obj._id)
        self.assertEquals(target_question.question, question_obj.question)
        self.assertEquals(target_question.options, question_obj.options)
        self.assertEquals(target_question.answer, question_obj.answer)

    def test_get_random_question(self):
        print "Testing DataAccess: get_random_question"

        question_obj = self.data_access.get_random_question()

        self.assertIsInstance(question_obj, Question)
        self.assertIsNotNone(question_obj)

    def test_get_all_questions(self):
        print "Testing DataAccess: get_all_questions"

        all_questions = self.data_access.get_all_questions()

        self.assertIsInstance(all_questions, list)
        self.assertEquals(len(all_questions), self.db_size)
        self.assertEquals(all_questions[0].question,
                          "How much does a male Polar Bear weigh?")

    def test_get_num_questions(self):
        print "Testing DataAccess: get_num_questions"

        num_questions = self.data_access.get_num_questions()

        self.assertIsInstance(num_questions, int)
        self.assertEquals(self.db_size, num_questions)

    def test_update_question(self):
        '''
        Test the update_question method, updating the question only.
        '''
        print "Testing DataAccess: update_question (Question)"
        question_id = 8
        new_question = "The Balkans are located in:"
        options = ["South America", "Europe", "Australia", "Asia"]
        answer = 1

        self.data_access.update_question(question_id,
                                         question=new_question)

        question_object = self.data_access.get_question(_id=8)

        self.assertEquals(question_object.question, new_question)
        self.assertEquals(question_object.options, options)
        self.assertEquals(question_object.answer, answer)

    def test_update_options(self):
        '''
        Test the update_question method, updating the options only.
        '''

        print "Testing DataAccess: update_question (Options)"
        question_id = 8
        question = "The Balkans are in:"
        new_options = ["South America", "Europe", "Australia",
                       "Africa"]
        answer = 1

        self.data_access.update_question(question_id, options=new_options)

        question_object = self.data_access.get_question(_id=8)

        self.assertEquals(question_object.question, question)
        self.assertEquals(question_object.options, new_options)
        self.assertEquals(question_object.answer, answer)

    def test_update_answer(self):
        '''
        Test the update_question method, updating the answer only.
        '''

        print "Testing DataAccess: update_question (Answer)"
        question_id = 8
        question = "The Balkans are in:"
        options = ["South America", "Europe", "Australia", "Asia"]
        new_answer = 0

        self.data_access.update_question(question_id, answer=new_answer)

        question_object = self.data_access.get_question(_id=8)

        self.assertEquals(question_object.question, question)
        self.assertEquals(question_object.options, options)
        self.assertEquals(question_object.answer, new_answer)

    def test_update_all(self):
        '''
        Test the update_question method, updating everything
        '''
        print "Testing DataAccess: update_question (All)"

        question_id = 8
        new_question = "How many breeds of cats are there"
        new_options = [35, 58, 73, 112]
        new_answer = 2

        self.data_access.update_question(question_id,
                                         question=new_question,
                                         options=new_options,
                                         answer=new_answer)

        question_object = self.data_access.get_question(_id=8)

        self.assertEquals(question_object.question, new_question)
        self.assertEquals(question_object.options, new_options)
        self.assertEquals(question_object.answer, new_answer)

    def test_delete_question(self):
        self.data_access.delete_question(5)

        all_questions = self.data_access.get_all_questions()

        self.assertEquals(len(all_questions), self.db_size-1)
