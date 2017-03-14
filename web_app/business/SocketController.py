"""
SocketController.py
"""

class SocketController(object):

    INSTANCE = None
    MAX_PLAYERS = 2

    def __init__(self):
        self.joined = []
        self.playing = []
        self.done = []
        self.rooms = []
        self.matches = {}

    @classmethod
    def get_instance(cls):

        if SocketController.INSTANCE is None:
            SocketController.INSTANCE = SocketController()

        return SocketController.INSTANCE

    @classmethod
    def destroy(cls):
        SocketController.INSTANCE = None

    def start(self):
        self.question_count = 0
        self.score = 0
        self.is_started = True

    def increase_score(self):
        self.score += 1

    def evaluate_answer(self, answer_index):
        result = True if answer_index == self.curr_question.answer else False
        return result

    def is_finished(self):
        return SocketController.MAX_QUESTIONS == self.question_count