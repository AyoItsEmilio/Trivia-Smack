"""
events.py
"""
from flask import session
from flask_socketio import emit, join_room, leave_room
from .. import socketio

USERS = []
ROOM = "only_one"
MAX_PLAYERS = 2

@socketio.on("joined", namespace="/test")
def joined(message):
    user_id = len(USERS)+1
    USERS.append(user_id)

    join_room(ROOM)

    if len(USERS) == MAX_PLAYERS:
        emit("ready", room=ROOM)


@socketio.on("disconnect")
def disconnect():
    leave_room(ROOM)
    USERS.pop(-1)
