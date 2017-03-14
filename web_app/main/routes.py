"""
routes.py
"""
from flask import jsonify, request
from web_app.business.AccessQuestions import AccessQuestions
from . import main

@main.route("/api/question_data/<num_questions>", methods=["GET"])
def question_data(num_questions):
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(int(num_questions))

    return jsonify(questions=questions)

@main.route("/api/android/post_score", methods=["POST"])
def post_score():
    print "DATA: {}".format(request.form["score"])
    return "Connection succeeded"
