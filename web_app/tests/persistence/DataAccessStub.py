"""
DataAccessStub.py
"""
import random
import re
from web_app.persistence.DataAccessInterface import DataAccessInterface
from web_app.persistence.questions import question_list
from web_app.objects.Question import Question

class DataAccessStub(DataAccessInterface):
    """Stub database"""

    def __init__(self, name):
        self.db_name = name
        self.questions = []

    def open(self):
        for question in question_list:
            self.insert_question(**question)

    def close(self):
        print "Closed database"
        self.questions = None

    def get_question(self, **kwargs):
        result = []

        for question_obj in self.questions:
            if re.match(re.compile(kwargs["question"]), question_obj.question):
                result.append(question_obj)

        return result

    def get_random_question(self, category=None):
        num_qs = self.get_num_questions()
        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0
        return self.questions[rq_num]

    def get_all_questions(self):
        return self.questions

    def get_num_questions(self):
        return len(self.questions)

    def insert_question(self, question, options, answer, category):
        self.questions.append(Question(question, options, answer, category))
        return True

    def update_question(self, **kwargs):
        result = None

        for question_obj in self.questions:
            if question_obj.question == kwargs["old_question"]:

                result = {}

                if "new_question" in kwargs:
                    question_obj.question = kwargs["new_question"]
                    result["question"] = kwargs["new_question"]
                if "new_options" in kwargs:
                    question_obj.options = kwargs["new_options"]
                    result["options"] = kwargs["new_options"]
                if "new_answer" in kwargs:
                    question_obj.answer = kwargs["new_answer"]
                    result["answer"] = kwargs["new_answer"]

        return result

    def delete_question(self, **kwargs):

        for question_obj in self.questions:
            if question_obj.question == kwargs["question"]:
                self.questions.remove(question_obj)
