"""
routes.py
"""
from flask import render_template, request, redirect, jsonify
from web_app.business.AccessQuestions import AccessQuestions
from web_app.business.GameController import GameController
from . import main

MAX_QUESTIONS = 3
GAME_CONTROLLER = GameController.get_instance(MAX_QUESTIONS)

@main.route("/")
def home_page():
    """The homepage"""
    score = GAME_CONTROLLER.score if GAME_CONTROLLER.is_started else None
    GAME_CONTROLLER.start()

    return render_template("homePage.html", score=score)

@main.route("/question_page", methods=["GET", "POST"])
def question_page():
    """The questions page"""

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

@main.route("/api/android/question_data")
def question_data():
    """Returns questions to an android app client"""
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(MAX_QUESTIONS)

    return jsonify(result=questions)
