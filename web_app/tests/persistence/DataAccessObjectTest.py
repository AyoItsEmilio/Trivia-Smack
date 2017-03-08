"""
DataAccessObject.py
"""

from flask.ext.pymongo import MongoClient
import unittest
from web_app.application.Services import Services
from web_app.objects.Question import Question

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

    def populate_db(self):
        # Questions for tests that need a pre-populated db.
        self.db_size = 9

        self.data_access.insert_question(
                             "How much does a male Polar Bear weigh?",
                             ["1200 lbs", "1000 lbs", "600 lbs",
                              "Enough to break the ice"], 1)
        self.data_access.insert_question(
                             "Is the square root of 10:",
                             ["zero", "greater than 3",
                              "less than 3"], 1)
        self.data_access.insert_question(
                             "Platypuses lay eggs",
                             ["true", "false"], 0)
        self.data_access.insert_question(
                             "Helsinki is the capitol of:",
                             ["Sweden", "Russia", "Finland",
                              "Iceland"], 2)
        self.data_access.insert_question(
                             "If x+y=3 and 2x+y=4, then x equals",
                             ["0", "1", "4", "3"], 1)
        self.data_access.insert_question(
                             "If x+y<11 and x>6, then y is:",
                             ["positive", "negative",
                              "Not determinable"], 2)
        self.data_access.insert_question(
                             "The plural of bison is:",
                             ["bisons", "buffalo", "bison",
                              "buffalos"], 2)
        self.data_access.insert_question(
                             "21, 25, 33, 49, 81, ",
                             ["162", "113", "144", "145"], 2)
        self.data_access.insert_question(
                             "The Balkans are in:",
                             ["South America", "Europe", "Australia",
                              "Asia"], 1)

    def tearDown(self):
        """Call once"""
        Services.close_data_access()
        self.client.drop_database(TEST_DB_NAME)

    def test_insert_question(self):
        """Inserting into empty db"""
        print 'Testing DataAccessObject: insert_question'
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

    def test_get_question(self):
        """Find an existing question"""
        print "Testing DataAccess: get_question"

        self.populate_db()

        question = "Platypuses lay eggs"
        options = ["true", "false"]
        answer = 0

        q = self.data_access.get_question(
            question=question, options=options, answer=answer)
        self.assertIsNotNone(q)
        self.assertEquals(q['question'], question)
        self.assertEquals(q['options'], options)
        self.assertEquals(q['answer'], answer)

    def test_get_random_question(self):
        print "Testing DataAccessObject: get_random_question"

        # populate db first.
        self.populate_db()

        question_obj = self.data_access.get_random_question()

        self.assertIsInstance(question_obj, Question)
        self.assertIsNotNone(question_obj)

    def test_get_all_questions(self):
        print "Testing DataAccess: get_all_questions"
        self.populate_db()

        all_questions = self.data_access.get_all_questions()

        self.assertIsInstance(all_questions, list)
        self.assertEquals(len(all_questions), self.db_size)

    def test_get_num_questions(self):
        print "Testing DataAccess: get_num_questions"
        self.populate_db()

        num_questions = self.data_access.get_num_questions()

        self.assertIsInstance(num_questions, int)
        self.assertEquals(self.db_size, num_questions)

    def test_update_question(self):
        '''
        Test the update_question method, updating the question only.
        '''
        self.populate_db()
        print "Testing DataAccess: update_question (Question)"
        question = "The Balkans are in:"
        new_question = "The Balkans are located in:"
        options = ["South America", "Europe", "Australia", "Asia"]
        answer = 1

        self.data_access.update_question(question=question,
                                         new_question=new_question)

        updated_question = self.data_access.get_question(
            question=new_question)

        self.assertEquals(updated_question['question'], new_question)
        self.assertEquals(updated_question['options'], options)
        self.assertEquals(updated_question['answer'], answer)

    def test_update_options(self):
        '''
        Test the update_question method, updating the options only.
        '''
        self.populate_db()
        print "Testing DataAccess: update_question (Options)"

        question = "The Balkans are in:"
        new_options = ["South America", "Europe", "Australia",
                       "Africa"]
        answer = 1

        self.data_access.update_question(question="The Balkans are in:",
                                         new_options=new_options)

        updated_question = self.data_access.get_question(
            options=new_options)

        self.assertEquals(updated_question['question'], question)
        self.assertEquals(updated_question['options'], new_options)
        self.assertEquals(updated_question['answer'], answer)

    def test_update_answer(self):
        '''
        Test the update_question method, updating the answer only.
        '''
        self.populate_db()
        print "Testing DataAccess: update_question (Answer)"

        question = "The Balkans are in:"
        options = ["South America", "Europe", "Australia", "Asia"]
        new_answer = 0

        self.data_access.update_question(question="The Balkans are in:",
                                         new_answer=new_answer)

        updated_question = self.data_access.get_question(question=question)

        self.assertEquals(updated_question['question'], question)
        self.assertEquals(updated_question['options'], options)
        self.assertEquals(updated_question['answer'], new_answer)

    def test_update_all(self):
        '''
        Test the update_question method, updating everything
        '''
        self.populate_db()
        print "Testing DataAccess: update_question (All)"

        original_question = "If x+y<11 and x>6, then y is:"
        new_question = "How many breeds of cats are there"
        new_options = [35, 58, 73, 112]
        new_answer = 2

        # Use the id to identify original question.
        question_id = self.data_access.get_question(
            question=original_question)['_id']

        self.data_access.update_question(_id=question_id,
                                         new_question=new_question,
                                         new_options=new_options,
                                         new_answer=new_answer)

        updated_question = self.data_access.get_question(_id=question_id)

        self.assertEquals(updated_question['question'], new_question)
        self.assertEquals(updated_question['options'], new_options)
        self.assertEquals(updated_question['answer'], new_answer)
