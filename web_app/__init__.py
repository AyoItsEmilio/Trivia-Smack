"""
__init__.py
"""
DB_NAME = "application"
MAX_PLAYERS = 2
MONGO_ADDR = "172.17.0.1"
MONGO_PORT = 27017

from flask.json import JSONEncoder
from flask_socketio import SocketIO
from web_app.persistence.populate_database import populate_database
from web_app.business.MyJSONEncoder import MyJSONEncoder
from web_app.application.Services import Services
from web_app.tests.persistence.DataAccessStub import DataAccessStub
from web_app.persistence.DataAccessObject import DataAccessObject

socketio = SocketIO()

def create_app(application):

    application.config.from_object("web_app.config")

    application.json_encoder = MyJSONEncoder

    from .main import main as main_blueprint
    application.register_blueprint(main_blueprint)

    socketio.init_app(application)

def set_up():
    #populate_database(DB_NAME)
    #Services.create_data_access(dbName="application")
    Services.create_data_access(altDataAccessService=DataAccessStub("app"))

def tear_down():
    Services.close_data_access()
