"""
QuestionTest.py
"""
import unittest
from web_app.objects.Question import Question

class QuestionTest(unittest.TestCase):
    """Unit tests for the Question class"""

    def setUp(self):
        """Call before every test case"""
        self._id = 0
        self.question = "What's my favorite color?"
        self.options = ["blue", "green", "red"]
        self.answer = 0
        self.question_obj = Question(self._id, self.question, self.options,
                                     self.answer)

    def tearDown(self):
        """Call after every test case"""
        self.question_obj = None

    def test_init(self):
        """Test Question initialization"""
        print "Testing Question: Constructor"

        self.assertIsNotNone(self.question_obj)
        self.assertEquals(self.question, self.question_obj.question)
        self.assertEquals(self.options, self.question_obj.options)
        self.assertEquals(self.answer, self.question_obj.answer)

    def test_mutation(self):
        """Test mutators"""
        print "Testing Question: Mutators"

        _id = 0
        question = "What kind of bear is best?"
        options = ["grizzly", "black", "polar"]
        answer = 1

        self.question_obj._id = _id
        self.question_obj.question = question
        self.question_obj.options = options
        self.question_obj.answer = answer

        self.assertEquals(_id, self.question_obj._id)
        self.assertEquals(question, self.question_obj.question)
        self.assertEquals(options, self.question_obj.options)
        self.assertEquals(answer, self.question_obj.answer)

    def test_failure(self):
        """Test for failure"""
        print "Testing Question: Invalid Args"
        try:
            Question()
            self.fail("Expected typeError")
        except TypeError:
            pass
