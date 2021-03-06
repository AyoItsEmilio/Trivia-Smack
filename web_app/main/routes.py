"""
routes.py

Error handlers are defined at the application level in application.py
(this is the blueprint level)
"""
import re
from flask import jsonify, make_response, request, abort
from flask_httpauth import HTTPBasicAuth
from web_app.business.AccessQuestions import AccessQuestions
from . import main

categories = ["all", "geography", "history", "math and science", "pop culture", "other"]

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
    error = validate_json(request.json)
    if not request.json or error:
        return make_response(jsonify(result={"errorMsg":error}), 400)

    access_questions = AccessQuestions()

    question_obj = clean_json(request.json)

    add_result = access_questions.add_question(\
        question_obj["question"],\
        question_obj["options"],\
        question_obj["answer"],\
        question_obj["category"])

    return make_response(jsonify(result={"success": add_result}), 200)


@main.route("/api/get_questions", methods=["GET"])
@auth.login_required
def get_questions():
    access_questions = AccessQuestions()
    questions = access_questions.get_all_questions()

    return make_response(jsonify(questions=questions), 200)


@main.route("/api/get_question_contains/<q_filter>", methods=["GET"])
@auth.login_required
def get_question_by(q_filter):
    access_questions = AccessQuestions()

    q_filter = q_filter.replace("+", "\+")
    q_filter = re.compile(".*"+q_filter+".*", re.IGNORECASE)
    questions = access_questions.get_question(question=q_filter)

    return make_response(jsonify(questions=questions), 200)


@main.route("/api/delete_question/<question>", methods=["DELETE"])
@auth.login_required
def delete_question(question):
    access_questions = AccessQuestions()

    if access_questions.get_num_questions == 0:
        abort(404)

    question = question.replace("+", "\+")
    result = access_questions.delete_question(question=re.compile(question+".*"))

    return make_response(jsonify({"result":result}, 200))


@main.route("/api/question_data/<int:num_questions>/<string:category>", methods=["GET"])
def question_data(num_questions, category):
    access_questions = AccessQuestions()

    if num_questions < 0 or category not in categories:
        abort(404)

    questions = access_questions.get_random_questions(
        int(num_questions), category)

    return make_response(jsonify(questions=questions), 200)


def validate_json(json):
    if "question" not in json or "options" not in json or "answer" not in json\
        or "category" not in json:
        return "Invalid request"

    if json["question"] == "" or json["options"] == [] or json["answer"] == ""\
        or json["category"] == "":
        return "Fields can't be empty"

    try:
        int(str(json["answer"]))
        answer = int(str(json["answer"]))

        if answer < 0 or answer > len(json["options"])-1:
            return "Answer is out of range"
    except ValueError:
        return "Answer must be a number"

    if not isinstance(json["options"], list):
        return "Options must be a comma separated list"

    if any(o.strip() == "" for o in json["options"]):
        return "An option can't be empty"

    try:
        int(str(json["question"]))
        return "Question must be string"
    except ValueError:
        pass

    if len(json["options"]) <= 1:
        return "There must be more than 1 option"


def clean_json(json):
    result = {}

    if "question" in json:
        result["question"] = str(json["question"]).strip()
    if "options" in json:
        result["options"] = [str(o).strip() for o in json["options"] if o]
    if "answer" in json:
        result["answer"] = int(json["answer"])
    if "category" in json:
        result["category"] = str(json["category"]).strip()

    return result
