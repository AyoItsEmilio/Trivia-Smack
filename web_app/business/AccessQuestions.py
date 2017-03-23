"""
AccessQuestions.py
"""
from web_app.application.Services import Services

class AccessQuestions(object):
    """Access the questions from the database defined in Services"""

    def __init__(self):
        self.data_access = Services.get_data_access()

    def get_random_questions(self, set_size):

        num_questions = self.get_num_questions()
        set_size = set_size if num_questions >= set_size else num_questions

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

    def get_all_questions(self):
        return self.data_access.get_all_questions()

    def get_num_questions(self):
        return self.data_access.get_num_questions()
