"""
Question.py
"""

class Question(object):

    def __init__(self, question, options, answer, category):
        self.question = question
        self.options = options
        self.category = category
        self.answer = answer
