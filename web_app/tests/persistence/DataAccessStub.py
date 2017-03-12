"""
DataAccessStub.py
"""
import random
from web_app.persistence.DataAccessInterface import DataAccessInterface
from web_app.objects.Question import Question


class DataAccessStub(DataAccessInterface):
    """Stub database"""

    def __init__(self, name):
        self.db_name = name
        self.questions = []

    def open(self):

        print "Opened database"
        self.insert_question("How much does a male Polar Bear weigh?",
                             ["1200 lbs", "1000 lbs", "600 lbs",
                              "Enough to break the ice"], 1)
        self.insert_question("Is the square root of 10:",
                             ["zero", "greater than 3",
                              "less than 3"], 1)
        self.insert_question("Platypuses lay eggs",
                             ["true", "false"], 0)
        self.insert_question("Helsinki is the capitol of:",
                             ["Sweden", "Russia", "Finland",
                              "Iceland"], 2)
        self.insert_question("If x+y=3 and 2x+y=4, then x equals",
                             ["0", "1", "4", "3"], 1)
        self.insert_question("If x+y<11 and x>6, then y is:",
                             ["positive", "negative",
                              "Not determinable"], 2)
        self.insert_question("The plural of bison is:",
                             ["bisons", "buffalo", "bison",
                              "buffalos"], 2)
        self.insert_question("21, 25, 33, 49, 81, ",
                             ["162", "113", "144", "145"], 2)
        self.insert_question("The Balkans are in:",
                             ["South America", "Europe", "Australia",
                              "Asia"], 1)

    def close(self):
        print "Closed database"
        self.questions = None

    def get_question(self, **kwargs):
        result = None

        for question_obj in self.questions:
            if question_obj.question == kwargs["question"]:
                result = question_obj

        return result

    def get_random_question(self):
        num_qs = self.get_num_questions()
        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0
        return self.questions[rq_num]

    def get_all_questions(self):
        return self.questions

    def get_num_questions(self):
        return len(self.questions)

    def insert_question(self, question, options, answer):
        self.questions.append(Question(question, options, answer))

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
