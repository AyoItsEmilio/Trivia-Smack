"""
populate_database.py
"""
from pymongo import MongoClient
from web_app import MONGO_ADDR, MONGO_PORT
from questions import question_list


def populate_database(db_name):

    client = MongoClient(MONGO_ADDR, MONGO_PORT)
    client.drop_database(db_name)
    db = client[db_name]

    questions = db.questions
    result = questions.insert_many(question_list)
    client.close()
    return result

if __name__ == "__main__":
    populate_database("application")
