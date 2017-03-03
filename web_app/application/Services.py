"""
Services.py
"""
import sys
from web_app.persistence.DataAccessObject import DataAccessObject

class Services(object):

    dataAccessService = None

    @staticmethod
    def create_data_access(dbName=None, altDataAccessService=None):

        if Services.dataAccessService is None:
            if altDataAccessService:
                Services.dataAccessService = altDataAccessService
            else:
                Services.dataAccessService = DataAccessObject(dbName)

            Services.dataAccessService.open()

            return Services.dataAccessService

    @staticmethod
    def get_data_access():

        if Services.dataAccessService is None:
            print "Connection to data access has not been established"
            sys.exit(0)

        return Services.dataAccessService

    @staticmethod
    def close_data_access():

        if Services.dataAccessService != None:
            Services.dataAccessService.close()

        Services.dataAccessService = None
