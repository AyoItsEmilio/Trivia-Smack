"""
application.py

Note that EB looks for application.py in the root dir
"""
from flask import Flask, make_response, jsonify
from web_app import create_app, set_up, tear_down, socketio
import eventlet.wsgi

set_up()

application = Flask(__name__,\
    static_folder="web_app/static",\
    static_url_path="")

create_app(application)

@application.errorhandler(404)
def not_found(error):
    return make_response(jsonify({"error":str(error)}), 404)

@application.errorhandler(400)
def bad_request(error):
    return make_response(jsonify({"error":str(error)}), 400)

if __name__ == "__main__":
    socketio.run(application, host="0.0.0.0")
    tear_down()
eventlet.wsgi.server(eventlet.listen(('', 5000)),application)