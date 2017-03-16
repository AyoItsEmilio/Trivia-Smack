"""
ConnectionController.py
"""

class ConnectionController(object):

    MAX_PLAYERS = 2

    def __init__(self):
        self.waiting = []
        self.playing = {}

    def join_waiting(self, pid):
        self.waiting.append(pid)

    def game_ready(self):
        return len(self.waiting) == ConnectionController.MAX_PLAYERS

    def join_playing(self):
        player_a = self.waiting.pop(0)
        player_b = self.waiting.pop(0)

        self.playing[player_a] = player_b
        self.playing[player_b] = player_a
        print self.playing

    def get_partner(self, pid):
        result = None

        if pid in self.playing:
            result = self.playing[pid]

        return result

    def leave_playing(self, pid):
        if pid in self.playing:
            self.playing.pop(pid)

    def leave_waiting(self, pid):
        if pid in self.waiting:
            self.waiting.remove(pid)
