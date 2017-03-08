"""
events.py
"""
from flask import session, request
from flask_socketio import emit, join_room, leave_room
from .. import socketio

USERS = []
PLAYERS = {}
ROOM = "only_one"
MAX_PLAYERS = 2

@socketio.on("join_wait", namespace="/wait")
def join_wait(message):
    user_id = len(USERS)+1
    USERS.append(user_id)
    join_room(ROOM)

    if len(USERS) == MAX_PLAYERS:
        emit("ready", room=ROOM)

@socketio.on("disconnect_wait", namespace="/wait")
def disconnect_wait(message):
    leave_room(ROOM)
    USERS.pop(-1)

@socketio.on("join_game_over", namespace="/game_over")
def join_game_over(message):
    print "SESSION NAME ", session["name"]
    PLAYERS[request.sid] = message["score"]
    join_room(request.sid)
    emit("show_score", {"msg":None}, room=request.sid)

@socketio.on("get_other_score", namespace="/game_over")
def get_other_score(message):
    room = request.sid
    result = None

    for key in PLAYERS:
        if key != request.sid:
            room = key
            result = PLAYERS[request.sid]

    emit("show_score", {"msg":result}, room=room)

@socketio.on("disconnect_game_over", namespace="/game_over")
def disconnect_game_over(message):
    print "disconnected", session["name"]
    leave_room(request.sid)
    PLAYERS.pop(request.sid)
