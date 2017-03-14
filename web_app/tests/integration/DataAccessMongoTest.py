"""
DataAccessMongoTest.py
"""
import unittest
from web_app.application.Services import Services
from web_app.tests.persistence.DataAccessTest import DataAccessTest
from web_app import DB_NAME

class DataAccessMongoTest(unittest.TestCase):
    """Integration tests for the DataAccess class"""

    def test_data_access(self):
        print "Testing DataAccess using default db service, mongoDB"

        Services.create_data_access(dbName=DB_NAME)
        DataAccessTest.data_access_test(self)
