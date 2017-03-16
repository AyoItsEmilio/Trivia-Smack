"""
ConnectionController.py
"""

from web_app import MAX_PLAYERS

class ConnectionController(object):

    def __init__(self):
        self.waiting = []
        self.playing = {}

    def join_waiting(self, pid):
        if pid not in self.waiting and pid is not None and\
        len(self.waiting) < MAX_PLAYERS:
            self.waiting.append(pid)

    def game_ready(self):
        return len(self.waiting) == MAX_PLAYERS

    def join_playing(self):
        result = False

        if len(self.waiting) == MAX_PLAYERS:
            result = True

            player_a = self.waiting.pop(0)
            player_b = self.waiting.pop(0)

            self.playing[player_a] = player_b
            self.playing[player_b] = player_a

        return result

    def get_partner(self, pid):
        result = None

        if pid in self.playing:
            result = self.playing[pid]

        return result

    def leave_playing(self, pid):
        result = False

        if pid in self.playing:
            result = True
            self.playing.pop(pid)

        return result

    def leave_waiting(self, pid):
        result = False

        if pid in self.waiting:
            result = True
            self.waiting.remove(pid)

        return result
