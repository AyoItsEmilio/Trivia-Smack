"""
routes.py

Error handlers are defined at the application level in application.py
(this is the blueprint level)
"""
from flask import jsonify, make_response
from flask_httpauth import HTTPBasicAuth
from web_app.business.AccessQuestions import AccessQuestions
from . import main

auth = HTTPBasicAuth()

@auth.get_password
def get_password(username):
    if username == "admin":
        return "comp4350"
    return None

@auth.error_handler
def unauthorized():
    return make_response(jsonify({"error": "Unauthorized access"}), 403)

@main.route("/api/login", methods=["GET"])
@auth.login_required
def login():
    return make_response(jsonify(result={"success":True}), 200)

@main.route("/api/question_data/<int:num_questions>", methods=["GET"])
def question_data(num_questions):
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(int(num_questions))

    return make_response(jsonify(questions=questions), 200)
