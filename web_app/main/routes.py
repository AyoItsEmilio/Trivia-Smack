"""
routes.py

Error handlers are defined at the application level in application.py
(this is the blueprint level)
"""
from flask import jsonify, make_response, request, abort
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

@main.route("/api/add_question", methods=["POST"])
@auth.login_required
def add_question():
    if not request.json or not validate_json(request.json):
        abort(400)

    access_questions = AccessQuestions()

    question_obj = clean_json(request.json)

    add_result = access_questions.add_question(\
        question_obj["question"],\
        question_obj["options"],\
        question_obj["answer"])

    return make_response(jsonify(result={"success": add_result}), 200)

@main.route("/api/get_questions", methods=["GET"])
@auth.login_required
def get_questions():
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions()
    return make_response(jsonify(questions=questions), 200)

@main.route("/api/question_data/<int:num_questions>", methods=["GET"])
def question_data(num_questions):
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(int(num_questions))

    return make_response(jsonify(questions=questions), 200)

def validate_json(json):

    result = True

    try:
        int(str(json["answer"]))
    except ValueError:
        result = False

    result = "question" in json and "options" in json and "answer" in json\
             and isinstance(json["question"], unicode) and result\
             and isinstance(json["options"], list) and result

    return result

def clean_json(json):
    result = {}
    result["question"] = str(json["question"]).strip()
    result["options"] = [str(o).strip() for o in json["options"] if o]
    result["answer"] = int(json["answer"])
    return result
