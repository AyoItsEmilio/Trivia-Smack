"""
__init__.py
"""
from flask.json import JSONEncoder
from web_app.populate_database import populate_database
from web_app.business.MyJSONEncoder import MyJSONEncoder
from web_app.application.Services import Services
from web_app.tests.persistence.DataAccessStub import DataAccessStub
from web_app.persistence.DataAccessObject import DataAccessObject

DB_NAME = "application"

def create_app(application):

    application.config.from_object("web_app.config")

    application.json_encoder = MyJSONEncoder

    from .main import main as main_blueprint
    application.register_blueprint(main_blueprint)

def set_up():
    populate_database(DB_NAME)
    Services.create_data_access(dbName=DB_NAME)
    #Services.create_data_access(altDataAccessService=DataAccessStub("stub"))

def tear_down():
    Services.close_data_access()
