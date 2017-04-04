"""
DataAccessTest.py
"""
import unittest
import re
from .DataAccessStub import DataAccessStub
from web_app.application.Services import Services
from web_app.objects.Question import Question

class DataAccessTest(unittest.TestCase):

    @classmethod
    def data_access_test(cls, self):

        DataAccessTest.data_access = Services.get_data_access()
        DataAccessTest.db_size = 10

        DataAccessTest.get_question_test(self)
        DataAccessTest.get_random_question_test(self)
        DataAccessTest.get_all_questions_test(self)
        DataAccessTest.get_num_questions_test(self)
        DataAccessTest.update_question_test(self)
        DataAccessTest.update_options_test(self)
        DataAccessTest.update_answer_test(self)
        DataAccessTest.delete_question_test(self)
        DataAccessTest.update_all_test(self)
        DataAccessTest.update_answer_test(self)

        Services.close_data_access()
        self.data_access = None

    @classmethod
    def get_question_test(cls, self):
        print "Testing DataAccess: get_question"

        question = "Platypuses lay eggs"
        target_question = Question(question, ["true", "false"], 0)

        question_obj = DataAccessTest.data_access.get_question(question=question)[0]

        self.assertEquals(target_question.question, question_obj.question)
        self.assertEquals(target_question.options, question_obj.options)
        self.assertEquals(target_question.answer, question_obj.answer)

        question = re.compile("^Platypus")

        question_obj = DataAccessTest.data_access.get_question(question=question)[0]

        self.assertEquals(target_question.question, question_obj.question)
        self.assertEquals(target_question.options, question_obj.options)
        self.assertEquals(target_question.answer, question_obj.answer)

        question = re.compile(".*Platypus.*")

        question_obj = DataAccessTest.data_access.get_question(question=question)[0]

        self.assertEquals(target_question.question, question_obj.question)
        self.assertEquals(target_question.options, question_obj.options)
        self.assertEquals(target_question.answer, question_obj.answer)

    @classmethod
    def get_random_question_test(cls, self):
        print "Testing DataAccess: get_random_question"

        question_obj = DataAccessTest.data_access.get_random_question()

        self.assertIsInstance(question_obj, Question)
        self.assertIsNotNone(question_obj)

    @classmethod
    def get_all_questions_test(cls, self):
        print "Testing DataAccess: get_all_questions"

        all_questions = DataAccessTest.data_access.get_all_questions()

        self.assertIsInstance(all_questions, list)
        self.assertEquals(len(all_questions), DataAccessTest.db_size)
        self.assertEquals(all_questions[0].question,
                          "How much does a male Polar Bear weigh?")

    @classmethod
    def get_num_questions_test(cls, self):
        print "Testing DataAccess: get_num_questions"

        num_questions = DataAccessTest.data_access.get_num_questions()

        self.assertIsInstance(num_questions, int)
        self.assertEquals(DataAccessTest.db_size, num_questions)

    @classmethod
    def update_question_test(cls, self):
        print "Testing DataAccess: update_question (Question)"

        old_question = "The Balkans are in:"
        new_question = "The Balkans are located in:"
        options = ["South America", "Europe", "Australia", "Asia"]
        answer = 1

        result =\
        DataAccessTest.data_access.update_question(old_question=old_question,
                                                   new_question=new_question)

        self.assertIsNotNone(result)

        question_object =\
            DataAccessTest.data_access.get_question(question=new_question)[0]

        self.assertEquals(question_object.question, new_question)
        self.assertEquals(question_object.options, options)
        self.assertEquals(question_object.answer, answer)

        DataAccessTest.data_access.update_question(old_question=new_question,
                                                   new_question=old_question)

    @classmethod
    def update_options_test(cls, self):
        print "Testing DataAccess: update_question (Options)"

        question = "The Balkans are in:"
        old_options = ["South America", "Europe", "Australia", "Asia"]
        new_options = ["South America", "Europe", "Australia", "Africa"]
        answer = 1

        result =\
        DataAccessTest.data_access.update_question(old_question=question,
                                                   new_options=new_options)

        self.assertIsNotNone(result)

        question_object =\
        DataAccessTest.data_access.get_question(question=question)[0]

        self.assertEquals(question_object.question, question)
        self.assertEquals(question_object.options, new_options)
        self.assertEquals(question_object.answer, answer)

        DataAccessTest.data_access.update_question(old_question=question,
                                                   new_options=old_options)

    @classmethod
    def update_answer_test(cls, self):
        print "Testing DataAccess: update_question (Answer)"

        question = "The Balkans are in:"
        options = ["South America", "Europe", "Australia", "Asia"]
        old_answer = 1
        new_answer = 0

        DataAccessTest.data_access.update_question(old_question=question,\
                                                   new_answer=new_answer)

        question_object =\
        DataAccessTest.data_access.get_question(question=question)[0]

        self.assertEquals(question_object.question, question)
        self.assertEquals(question_object.options, options)
        self.assertEquals(question_object.answer, new_answer)

        DataAccessTest.data_access.update_question(old_question=question,
                                                   new_answer=old_answer)

    @classmethod
    def update_all_test(cls, self):
        print "Testing DataAccess: update_question (All)"

        new_question = "How many breeds of cats are there"
        new_options = ["35", "58", "73", "112"]
        new_answer = 2

        old_question = "The Balkans are in:"
        old_options = ["South America", "Europe", "Australia", "Asia"]
        old_answer = 1

        DataAccessTest.data_access.update_question(old_question=old_question,
                                                   new_question=new_question,
                                                   new_options=new_options,
                                                   new_answer=new_answer)

        question_object =\
        DataAccessTest.data_access.get_question(question=new_question)[0]

        self.assertEquals(question_object.question, new_question)
        self.assertEquals(question_object.options, new_options)
        self.assertEquals(question_object.answer, new_answer)

        DataAccessTest.data_access.update_question(old_question=new_question,
                                                   new_question=old_question,
                                                   new_options=old_options,
                                                   new_answer=old_answer)

    @classmethod
    def delete_question_test(cls, self):
        print "Testing DataAccess: delete_question"

        question = "The Balkans are in:"
        question_obj =\
        DataAccessTest.data_access.get_question(question=question)[0]

        DataAccessTest.data_access.delete_question(question=question)

        all_questions = DataAccessTest.data_access.get_all_questions()

        self.assertEquals(len(all_questions), (DataAccessTest.db_size-1))

        question_none =\
        DataAccessTest.data_access.get_question(question=question)

        self.assertEquals(question_none, [])

        DataAccessTest.data_access.insert_question(question_obj.question,
                                                   question_obj.options,
                                                   question_obj.answer)

    def test_data_access(self):
        print "Testing DataAccess using db stub"

        Services.close_data_access()
        Services.create_data_access(\
            altDataAccessService=DataAccessStub("test_db"))

        DataAccessTest.data_access_test(self)
