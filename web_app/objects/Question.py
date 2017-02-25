"""
Question.py
"""


class Question(object):
    """For holding a question, it's options, and answer.
       The question will serve as an id"""

    def __init__(self, id_, question, options, answer):
        self.id_ = id_
        self.question = question
        self.options = options
        self.answer = answer
