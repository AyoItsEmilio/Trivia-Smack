"""
routes.py
"""
from flask import render_template, request, redirect, jsonify, session
from web_app.business.AccessQuestions import AccessQuestions
from . import main
from random import randint

max_questions = 3

@main.route("/")
def home_page():
    session["question_count"] = 0
    score = session.get("score", None)
    session["score"] = None
    session["name"] = "sam"+str(randint(0,10))
    print "SESSION NAME:", session["name"]
    return render_template("homePage.html", score=score)

@main.route("/wait_page")
def wait_page():
    return render_template("waitPage.html")

@main.route("/question_page")
def question_page():

    result = request.args.get("result", None)

    if result is not None:

        session["question_count"] += 1

        if session["score"] is None:
            session["score"] = 0

        if int(result) == session["answer"]:
            session["score"] = session["score"] + 1

    if session["question_count"] == max_questions:
        return redirect("/")

    question_obj = AccessQuestions().get_random_question()

    question = question_obj.question
    options = question_obj.options
    answer = question_obj.answer
    session["answer"] = answer

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
