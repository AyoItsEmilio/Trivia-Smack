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
        self.curr_id = 0

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
        """
        Grabs a question from the database.
        """

        for question in self.questions:
            found_question = True
            if '_id' in kwargs:
              found_question = found_question and (kwargs['_id'] == question._id)
            if 'question' in kwargs:
              found_question = found_question and (kwargs['question'] == question.question)
            if 'options' in kwargs:
              print kwargs['options']
              print question.options
              found_question = found_question and (kwargs['options'] == question.options)
            if 'answer' in kwargs:
              found_question = found_question and (kwargs['answer'] == question.answer)

            if found_question:
              return question

        # return next((x for x in self.questions if x._id == _id), None)

    def get_random_question(self):
        """Grab a random question from the DB"""
        num_qs = self.get_num_questions()
        rq_num = random.randint(0, num_qs-1) if num_qs > 0 else 0
        return self.questions[rq_num]

    def get_all_questions(self):
        return self.questions

    def get_num_questions(self):
        return len(self.questions)

    def insert_question(self, question, options, answer):
        self.questions.append(Question(self.curr_id, question,
                              options, answer))
        self.curr_id += 1

    def update_question(self, **kwargs):
        """
        Updates an existing question.
        """
        orig_question = dict()

        if '_id' in kwargs:
            orig_question['_id'] = kwargs['_id']
        if 'question' in kwargs:
            orig_question['question'] = kwargs['question']
        if 'options' in kwargs:
            orig_question['options'] = kwargs['options']
        if 'answer' in kwargs:
            orig_question['answer'] = kwargs['answer']

        updated_question = self.get_question(**orig_question)

        if updated_question is not None:
            if 'new_question' in kwargs:
                updated_question.question = kwargs['new_question']
            if 'new_options' in kwargs:
                updated_question.options = kwargs['new_options']
            if 'new_answer' in kwargs:
                updated_question.answer = kwargs['new_answer']

    def delete_question(self, _id):
        for question in self.questions:
            if question._id == _id:
                self.questions.remove(question)
