"""
events.py
"""
from flask import session
from flask_socketio import emit
from .. import socketio

USERS = []

@socketio.on("joined", namespace="/test")
def joined(message):
    USERS.append(0)
    emit("status", {"msg":str(len(USERS))})

@socketio.on("disconnect")
def disconnect():
    USERS.pop(-1)
