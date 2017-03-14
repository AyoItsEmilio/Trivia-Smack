"""
events.py
"""
from flask import request
from flask_socketio import emit, join_room
from .. import socketio

JOINED = []
PLAYING = []
DONE = []
MAX_PLAYERS = 2
ROOMS = []
MATCHES = {}

@socketio.on("join_game")
def join_game():
    join_room(len(ROOMS))

    JOINED.append(request.sid)

    if len(JOINED) == MAX_PLAYERS:
        PLAYING.extend(JOINED)

        MATCHES[JOINED[-2]] = JOINED[-1]
        MATCHES[JOINED[-1]] = JOINED[-2]

        del JOINED[:]
        room = len(ROOMS)
        ROOMS.append(room)
        emit("game_is_ready", room=room)

@socketio.on("game_over")
def game_over(message):
    join_room(request.sid)
    PLAYING.remove(request.sid)
    emit("other_player_done", {"msg":message["score"]}, room=MATCHES[request.sid])


@socketio.on("disconnect")
def disconnect():
    if request.sid in JOINED:
        JOINED.remove(request.sid)
    if request.sid in PLAYING:
        PLAYING.remove(request.sid)
