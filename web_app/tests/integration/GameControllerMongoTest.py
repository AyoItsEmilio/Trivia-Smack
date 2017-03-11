import unittest
from web_app.application.Services import Services
from web_app.tests.business.GameControllerTest import GameControllerTest
from web_app import DB_NAME

class GameControllerMongoTest(unittest.TestCase):
    """Integration tests for the GameController class"""

    def test_data_access(self):
        print "Testing GameController using default db service, mongoDB"

        Services.create_data_access(dbName=DB_NAME)
        GameControllerTest.game_controller_test(self)
