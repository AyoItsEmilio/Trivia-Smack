"""
application.py

Note that EB looks for application.py in the root dir
"""
from flask import Flask
from web_app import create_app, set_up, tear_down

set_up()

application = Flask(__name__,\
    static_folder="web_app/static",\
    static_url_path="")

create_app(application)

if __name__ == "__main__":
    application.run()
    tear_down()
