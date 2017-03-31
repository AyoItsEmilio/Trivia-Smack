"""
AccessQuestions.py
"""
from web_app.application.Services import Services

class AccessQuestions(object):
    """Access the questions from the database defined in Services"""

    def __init__(self):
        self.data_access = Services.get_data_access()

    def get_random_questions(self, set_size):

        num_qs = self.get_num_questions()

        if set_size > num_qs:
            set_size = num_qs

        questions = []
        seen = set()

        while len(seen) < set_size:
            question_obj = self.data_access.get_random_question()

            if question_obj is not None:
                qid = question_obj.question
                if qid not in seen:
                    seen.add(qid)
                    questions.append(question_obj)

        return questions

    def get_question(self, **kwargs):
        return self.data_access.get_question(**kwargs)

    def delete_question(self, **kwargs):
        return self.data_access.delete_question(**kwargs)

    def get_all_questions(self):
        return self.data_access.get_all_questions()

    def get_num_questions(self):
        return self.data_access.get_num_questions()

    def add_question(self, question, options, answer):
        self.data_access.insert_question(question, options, answer)
