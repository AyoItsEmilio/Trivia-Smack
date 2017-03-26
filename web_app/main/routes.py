"""
routes.py

Error handlers are defined at the application level in application.py
(this is the blueprint level)
"""
from flask import jsonify, make_response
from web_app.business.AccessQuestions import AccessQuestions
from . import main

@main.route("/api/question_data/<int:num_questions>", methods=["GET"])
def question_data(num_questions):
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(int(num_questions))

    return make_response(jsonify(questions=questions), 200)
