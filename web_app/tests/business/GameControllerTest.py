"""
GameControllerTest.py
"""
import unittest
from ..persistence.DataAccessStub import DataAccessStub
from web_app.application.Services import Services
from web_app.business.GameController import GameController

class GameControllerTest(unittest.TestCase):
    """Unit tests for the Question class"""

    @classmethod
    def setUpClass(cls):
        Services.close_data_access()
        Services.create_data_access(\
            altDataAccessService=DataAccessStub("application"))

    @classmethod
    def tearDownClass(cls):
        Services.close_data_access()

    def setUp(self):
        self.dummy_game_controller = GameController.get_instance()

    def tearDown(self):
        GameController.destroy()

    def test_singleton(self):
        print "Testing GameController: Singleton"
        first = GameController.get_instance()
        second = GameController.get_instance()
        self.assertEquals(first, second)

    def test_start(self):
        print "Testing GameController: Start"
        self.assertFalse(self.dummy_game_controller.is_started)
        self.dummy_game_controller.start()
        self.assertTrue(self.dummy_game_controller.is_started)

    def test_increase_score(self):
        print "Testing GameController: increase_score"
        self.dummy_game_controller.start()
        self.assertEquals(0, self.dummy_game_controller.score)
        self.dummy_game_controller.increase_score()
        self.assertEquals(1, self.dummy_game_controller.score)
