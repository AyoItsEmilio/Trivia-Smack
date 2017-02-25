"""
Question.py
"""


class Question(object):
    """For holding a question, it's options, and answer.
       The question will serve as an id"""

    def __init__(self, _id, question, options, answer):
        self._id = _id
        self.question = question
        self.options = options
        self.answer = answer
