"""
Question.py
"""

class Question(object):

    def __init__(self, question, options, difficulty, category, answer):
        self.question = question
        self.options = options
        self.difficulty = difficulty
        self.category = category
        self.answer = answer
