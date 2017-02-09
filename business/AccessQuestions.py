"""
AccessQuestions.py
"""
from application.Services import Services

class AccessQuestions(object):
    """For accessing questions using the database defined in Services"""
    def __init__(self):
        self.data_access = Services.get_data_access()

    def get_random_questions(self, set_size):
        """Grab a random subset of questions from the DB"""
        num_questions = self.get_num_questions()
        set_size = set_size if num_questions >= set_size else num_questions

        questions = []
        seen = set()

        while len(seen) < set_size:
            question = self.data_access.get_question()
            qid = question["question"] #we'll just use the question as the id

            if qid not in seen:
                seen.add(qid)
                questions.append(question)

        return [doc for doc in questions]

    def get_all_questions(self):
        """Grab all questions from the DB"""
        return self.data_access.get_all_questions()

    def get_num_questions(self):
        """Return the number of questions"""
        return self.data_access.get_num_questions()