"""
application.py

Note that EB looks for application.py in the root dir
"""
from flask import Flask
from web_app import create_app, set_up, tear_down, socketio

set_up()

application = Flask(__name__,\
    static_folder="web_app/static",\
    static_url_path="")

create_app(application)

if __name__ == "__main__":
    socketio.run(application, host="0.0.0.0")
    tear_down()
