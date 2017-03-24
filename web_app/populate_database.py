"""
populate_database.py
"""
from pymongo import MongoClient

def populate_database(db_name):

    client = MongoClient("172.31.42.63", 27017)
    print "HHHHHHHHERRRERE", client
    client.drop_database(db_name)
    db = client[db_name]

    question_list = [
        {"question": "How much does a male Polar Bear weigh?",
         "options": ["1200 lbs", "1000 lbs", "700 lbs",
                     "Enough to break the ice"],
         "answer": 1},
        {"question": "Is the square root of 10:",
         "options": ["zero", "greater than 3", "less than 3"],
         "answer": 1},
        {"question": "Platypuses lay eggs",
         "options": ["true", "false"],
         "answer": 0},
        {"question": "Helsinki is the capitol of:",
         "options": ["Sweden", "Russia", "Finland", "Saskatchewan"],
         "answer": 2},
        {"question": "If x+y=3 and 2x+y=4, then x equals",
         "options": ["0", "1", "4", "3"],
         "answer": 1},
        {"question": "If x+y<11 and x>6, then y is:",
         "options": ["positive", "negative", "Not determinable"],
         "answer": 2},
        {"question": "The plural of bison is:",
         "options": ["bisons", "buffalo", "bison", "buffalos"],
         "answer": 2},
        {"question": "21, 25, 33, 49, 81, ",
         "options": ["162", "113", "144", "145"],
         "answer": 2},
        {"question": "The Balkans are in:",
         "options": ["South America", "Europe", "Australia", "Asia"],
         "answer": 1}
    ]

    questions = db.questions
    result = questions.insert_many(question_list)
    client.close()
    print result
    return result

if __name__ == "__main__":
    populate_database("application")
