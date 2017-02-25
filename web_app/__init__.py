"""
__init__.py
"""
from flask.json import JSONEncoder
from populate_database import populate_db
from web_app.business.MyJSONEncoder import MyJSONEncoder
from web_app.application.Services import Services
from web_app.tests.persistence.DataAccessStub import DataAccessStub
from web_app.persistence.DataAccessObject import DataAccessObject

DB_NAME = "application"


def create_app(application):
    """Create the application"""

    application.config.from_object("web_app.config")

    application.json_encoder = MyJSONEncoder

    from .main import main as main_blueprint
    application.register_blueprint(main_blueprint)


def set_up():
    populate_db()
    Services.create_data_access(dbName="application")


def tear_down():
    Services.close_data_access()
