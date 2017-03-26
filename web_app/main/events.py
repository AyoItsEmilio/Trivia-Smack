"""
events.py
"""
from flask import request
from flask_socketio import emit, join_room, leave_room
from web_app.business.ConnectionController import ConnectionController
from .. import socketio

cc = ConnectionController()

@socketio.on("join_game")
def join_game():
    join_room(request.sid)

    cc.join_waiting(request.sid)

    emit("join_waiting")

    if cc.game_ready():
        cc.join_playing()
        emit("other_player_ready", room=request.sid)
        emit("other_player_ready", room=cc.get_partner(request.sid))

@socketio.on("game_over")
def game_over(message):

    partner = cc.get_partner(request.sid)

    if partner is not None:

        emit("other_player_done", {"msg":message["score"]}, room=partner)

        cc.leave_playing(request.sid)

        if cc.playing == {}:
            emit("clean_up", room=partner)
            emit("clean_up", room=request.sid)

@socketio.on("disconnect")
def disconnect():
    other_id = cc.get_partner(request.sid)

    if other_id is not None:
        emit("other_player_done", {"msg":None}, room=other_id)

    cc.leave_playing(request.sid)
    cc.leave_waiting(request.sid)
    leave_room(request.sid)
