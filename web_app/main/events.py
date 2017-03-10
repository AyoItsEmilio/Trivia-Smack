"""
events.py
"""
from flask import session, request
from flask_socketio import emit, join_room, leave_room
from .. import socketio

JOINED = []
DONE = {}
MATCHES = []
ROOM = "only_one"
MAX_PLAYERS = 2

@socketio.on("join_wait", namespace="/wait")
def join_wait():
    JOINED.append(session["name"])
    join_room(ROOM)

    if len(JOINED) % MAX_PLAYERS == 0:
        MATCHES.append(set([JOINED[-1], JOINED[-2]]))
        emit("ready", room=ROOM)

@socketio.on("join_game_over", namespace="/game_over")
def join_game_over(message):
    DONE[session["name"]] = message["score"]
    join_room(session["name"])
    emit("show_score", {"msg":None}, room=session["name"])

@socketio.on("get_other_score", namespace="/game_over")
def get_other_score():
    room = session["name"]
    result = None

    for key in DONE:
        if set([key, session["name"]]) in MATCHES:
            room = key
            result = DONE[session["name"]]

    emit("show_score", {"msg":result}, room=room)

@socketio.on("disconnect_game_over", namespace="/game_over")
def disconnect_game_over():
    leave_room(session["name"])
    DONE.pop(session["name"])
    JOINED.pop(session["name"])
