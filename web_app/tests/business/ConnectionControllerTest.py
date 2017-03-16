"""
ConnectionControllerTest.py
"""
import unittest

from web_app.business.ConnectionController import ConnectionController

class ConnectionControllerTest(unittest.TestCase):

    def setUp(self):
        self.dummy_cc = ConnectionController()

    def tearDown(self):
        self.dummy_cc = None

    def test_constructor(self):
        print "Testing ConnectionController: Constructor"

        self.assertEquals(self.dummy_cc.waiting, [])
        self.assertEquals(self.dummy_cc.playing, {})

    def test_join_waiting(self):
        print "Testing ConnectionController: join_waiting"
        id_a = 55
        id_b = 56
        id_c = 57

        self.assertEquals(self.dummy_cc.waiting, [])
        self.dummy_cc.join_waiting(id_a)
        self.assertEquals(self.dummy_cc.waiting, [id_a])
        self.dummy_cc.join_waiting(None)
        self.assertEquals(self.dummy_cc.waiting, [id_a])
        self.dummy_cc.join_waiting(id_b)
        self.assertEquals(self.dummy_cc.waiting, [id_a, id_b])
        self.dummy_cc.join_waiting(id_b)
        self.assertEquals(self.dummy_cc.waiting, [id_a, id_b])
        self.dummy_cc.join_waiting(id_c)
        self.assertEquals(self.dummy_cc.waiting, [id_a, id_b])

    def test_game_ready(self):
        print "Testing ConnectionController: game_ready"
        id_a = 55
        id_b = 56

        self.assertFalse(self.dummy_cc.game_ready())

        self.dummy_cc.join_waiting(id_a)
        self.dummy_cc.join_waiting(id_b)

        self.assertTrue(self.dummy_cc.game_ready())
        self.dummy_cc.join_playing()
        self.assertFalse(self.dummy_cc.game_ready())

        self.dummy_cc.join_waiting(id_a)
        self.dummy_cc.join_waiting(id_b)
        self.assertTrue(self.dummy_cc.game_ready())
        self.dummy_cc.leave_waiting(id_a)
        self.assertFalse(self.dummy_cc.game_ready())
        self.dummy_cc.join_waiting(id_a)
        self.assertTrue(self.dummy_cc.game_ready())

    def test_join_playing(self):
        print "Testing ConnectionController: join_playing"
        id_a = 55
        id_b = 56
        id_c = 57
        id_d = 58

        self.assertEquals(self.dummy_cc.playing, {})
        self.assertFalse(self.dummy_cc.join_playing())

        self.dummy_cc.join_waiting(id_a)
        self.assertFalse(self.dummy_cc.join_playing())
        self.dummy_cc.join_waiting(id_b)

        self.assertTrue(self.dummy_cc.join_playing())

        self.assertEquals(self.dummy_cc.playing, {id_a:id_b, id_b:id_a})

        self.assertFalse(self.dummy_cc.join_playing())
        self.dummy_cc.join_waiting(id_c)
        self.assertFalse(self.dummy_cc.join_playing())
        self.dummy_cc.join_waiting(id_d)
        self.assertTrue(self.dummy_cc.join_playing())

        self.assertEquals(self.dummy_cc.playing,\
            {id_a:id_b, id_b:id_a, id_c:id_d, id_d:id_c})

    def test_get_partner(self):
        print "Testing ConnectionController: get_partner"
        id_a = 55
        id_b = 56
        id_c = 57
        id_d = 58

        self.assertEquals(self.dummy_cc.get_partner(id_a), None)
        self.assertEquals(self.dummy_cc.get_partner(id_b), None)
        self.assertEquals(self.dummy_cc.get_partner(id_c), None)
        self.assertEquals(self.dummy_cc.get_partner(id_d), None)

        self.dummy_cc.join_waiting(id_a)
        self.dummy_cc.join_waiting(id_b)
        self.dummy_cc.join_playing()

        self.assertEquals(self.dummy_cc.get_partner(id_a), id_b)
        self.assertEquals(self.dummy_cc.get_partner(id_b), id_a)

        self.dummy_cc.join_waiting(id_c)
        self.dummy_cc.join_waiting(id_d)
        self.dummy_cc.join_playing()

        self.assertEquals(self.dummy_cc.get_partner(id_c), id_d)
        self.assertEquals(self.dummy_cc.get_partner(id_d), id_c)
        self.assertNotEquals(self.dummy_cc.get_partner(id_a), id_c)
        self.assertNotEquals(self.dummy_cc.get_partner(id_c), id_a)

        self.dummy_cc.leave_playing(id_c)
        self.assertEquals(self.dummy_cc.get_partner(id_c), None)
        self.assertEquals(self.dummy_cc.get_partner(id_d), id_c)

    def test_leave_waiting(self):
        print "Testing ConnectionController: leave_waiting"
        id_a = 55
        id_b = 56
        id_c = 57
        id_d = 58

        self.assertEquals(self.dummy_cc.waiting, [])
        self.assertFalse(self.dummy_cc.leave_waiting(id_a))
        self.assertFalse(self.dummy_cc.leave_waiting(id_b))
        self.assertFalse(self.dummy_cc.leave_waiting(id_c))
        self.assertFalse(self.dummy_cc.leave_waiting(id_d))

        self.dummy_cc.join_waiting(id_a)
        self.dummy_cc.join_waiting(id_b)

        self.assertEquals(self.dummy_cc.waiting, [id_a, id_b])
        self.assertTrue(self.dummy_cc.leave_waiting(id_a))
        self.assertEquals(self.dummy_cc.waiting, [id_b])
        self.assertTrue(self.dummy_cc.leave_waiting(id_b))
        self.assertEquals(self.dummy_cc.waiting, [])

        self.assertFalse(self.dummy_cc.leave_waiting(id_a))
        self.assertFalse(self.dummy_cc.leave_waiting(id_a))

    def test_leave_playing(self):
        print "Testing ConnectionController: leave_playing"
        id_a = 55
        id_b = 56
        id_c = 57
        id_d = 58

        self.assertEquals(self.dummy_cc.playing, {})
        self.assertFalse(self.dummy_cc.leave_playing(id_a))
        self.assertFalse(self.dummy_cc.leave_playing(id_b))
        self.assertFalse(self.dummy_cc.leave_playing(id_c))
        self.assertFalse(self.dummy_cc.leave_playing(id_d))

        self.dummy_cc.join_waiting(id_a)
        self.dummy_cc.join_waiting(id_b)
        self.assertFalse(self.dummy_cc.leave_playing(id_a))
        self.assertFalse(self.dummy_cc.leave_playing(id_b))
        self.dummy_cc.join_playing()
        self.assertTrue(self.dummy_cc.leave_playing(id_a))
        self.assertTrue(self.dummy_cc.leave_playing(id_b))
