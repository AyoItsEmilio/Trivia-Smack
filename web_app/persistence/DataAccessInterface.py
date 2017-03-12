"""
DataAccessInterface.py
"""
from abc import ABCMeta, abstractmethod

class DataAccessInterface(object):
    """A Python abstract base class to act as an interface"""
    __metaclass__ = ABCMeta

    @abstractmethod
    def open(self):
        pass

    @abstractmethod
    def close(self):
        pass

    @abstractmethod
    def get_question(self, **kwargs):
        pass

    @abstractmethod
    def get_random_question(self):
        """Grab a random question from the DB"""
        pass

    @abstractmethod
    def get_all_questions(self):
        pass

    @abstractmethod
    def get_num_questions(self):
        pass

    @abstractmethod
    def insert_question(self, question, options, answer):
        """Insert a question into the DB"""
        pass

    @abstractmethod
    def update_question(self, **kwargs):
        """Update question in DB"""
        pass

    @abstractmethod
    def delete_question(self, **kwargs):
        """Delete question from DB"""
        pass
