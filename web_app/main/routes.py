"""
routes.py
"""
from flask import render_template, request, redirect, jsonify, session
from web_app.business.AccessQuestions import AccessQuestions
from web_app.business.GameController import GameController
from . import main

GAME_CONTROLLER = GameController.get_instance()

@main.route("/")
def home_page():
    score = GAME_CONTROLLER.score if GAME_CONTROLLER.is_started else None
    GAME_CONTROLLER.start()

    return render_template("homePage.html", score=score)

@main.route("/wait_page")
def wait_page():
    return render_template("waitPage.html")

@main.route("/question_page", methods=["GET", "POST"])
def question_page():

    result = request.args.get("result", None)

    if result and GAME_CONTROLLER.evaluate_answer(int(result)):
        GAME_CONTROLLER.increase_score()

    if GAME_CONTROLLER.is_finished():
        return redirect("/")

    question_obj = GAME_CONTROLLER.get_next_question()

    question = question_obj.question
    options = question_obj.options
    answer = question_obj.answer

    return render_template("questionPage.html",\
       question=question,\
       options=options,\
       answer=answer)

@main.route("/api/android/question_data/<num_questions>")
def question_data(num_questions):
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(int(num_questions))

    return jsonify(result=questions)

@main.route("/api/android/post_score", methods=["POST"])
def post_score():
    print "DATA: {}".format(request.form["score"])
    return "Connection succeeded"
