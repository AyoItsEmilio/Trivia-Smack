"""
routes.py
"""
from flask import request, jsonify
from web_app.business.AccessQuestions import AccessQuestions
from . import main

@main.route("/api/question_data/<num_questions>", methods=["GET"])
def question_data(num_questions):
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(int(num_questions))

    return jsonify(questions=questions)
