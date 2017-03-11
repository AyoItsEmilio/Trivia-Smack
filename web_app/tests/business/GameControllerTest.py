"""
GameControllerTest.py
"""
import unittest
from ..persistence.DataAccessStub import DataAccessStub
from web_app.application.Services import Services
from web_app.business.GameController import GameController

class GameControllerTest(unittest.TestCase):
    """Unit tests for the GameController class"""

    @classmethod
    def tearDownClass(cls):
        Services.close_data_access()

    @classmethod
    def game_controller_test(cls, self):
        dummy_game_controller = GameController.get_instance()

        print "Testing GameController: Singleton"
        first = GameController.get_instance()
        second = GameController.get_instance()
        self.assertEquals(first, second)

        print "Testing GameController: Start"
        self.assertFalse(dummy_game_controller.is_started)
        dummy_game_controller.start()
        self.assertTrue(dummy_game_controller.is_started)

        print "Testing GameController: increase_score"
        dummy_game_controller.start()
        self.assertEquals(0, dummy_game_controller.score)
        dummy_game_controller.increase_score()
        self.assertEquals(1, dummy_game_controller.score)

        GameController.destroy()

    def test_game_controller(self):
        Services.close_data_access()
        Services.create_data_access(\
            altDataAccessService=DataAccessStub("application"))

        GameControllerTest.game_controller_test(self)
