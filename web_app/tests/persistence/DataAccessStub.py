"""
DataAccessStub.py
"""
import random
import re
from web_app.persistence.DataAccessInterface import DataAccessInterface
from web_app.objects.Question import Question


class DataAccessStub(DataAccessInterface):
    """Stub database"""

    def __init__(self, name):
        self.db_name = name
        self.questions = []

    def open(self):

        print "Opened database"

        question_list = [
          {
            "question": "How much does a male Polar Bear weigh?",
            "options": ["1200 lbs", "1000 lbs", "600 lbs",
                        "Enough to break the ice"],
            "difficulty":"easy",
            "category":"animal",
            "answer": 1
          },
          {
              "question": "Is the square root of 10:",
              "options": ["zero", "greater than 3", "less than 3"],
              "difficulty":"easy",
              "category":"math",
              "answer": 1
          },
          {
              "question": "Platypuses lay eggs",
              "options": ["true", "false"],
              "difficulty":"easy",
              "category":"animal",
              "answer": 0
          },
          {
              "question": "Helsinki is the capitol of:",
              "options": ["Sweden", "Russia", "Finland", "Iceland"],
              "difficulty":"easy",
              "category":"geography",
              "answer": 2
          },
          {
              "question": "If x+y=3 and 2x+y=4, then x equals",
              "options": ["0", "1", "4", "3"],
              "difficulty":"easy",
              "category":"math",
              "answer": 1
          },
          {
              "question": "If x+y<11 and x>6, then y is:",
              "options": ["positive", "negative", "Not determinable"],
              "difficulty":"easy",
              "category":"math",
              "answer": 2
          },
          {
              "question": "The plural of bison is:",
              "options": ["bisons", "buffalo", "bison", "buffalos"],
              "difficulty":"easy",
              "category":"animal",
              "answer": 2
          },
          {
              "question": "21, 25, 33, 49, 81, ",
              "options": ["162", "113", "144", "145"],
              "difficulty":"easy",
              "category":"math",
              "answer": 2
          },
          {
              "question": "The Balkans are in:",
              "options": ["South America", "Europe", "Australia", "Asia"],
              "difficulty":"easy",
              "category":"geography",
              "answer": 1
          }]

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

    def get_random_question(self):
        num_qs = self.get_num_questions()
        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0
        return self.questions[rq_num]

    def get_all_questions(self):
        return self.questions

    def get_num_questions(self):
        return len(self.questions)

    def insert_question(self, question, options, difficulty, category, answer):
        self.questions.append(Question(question, options,
                              difficulty, category, answer))
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
