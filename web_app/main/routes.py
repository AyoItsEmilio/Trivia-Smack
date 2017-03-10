"""
routes.py
"""
from flask import render_template, request, redirect, jsonify, session
from web_app.business.AccessQuestions import AccessQuestions
from . import main
from .events import JOINED

max_questions = 3

@main.route("/")
def home_page():
    session.clear()
    return render_template("homePage.html")

@main.route("/wait_page")
def wait_page():
    joined = session.get("joined", None)

    if not joined:
        session.clear()
        session["question_count"] = 0
        session["score"] = 0
        session["name"] = len(JOINED)
        session["joined"] = True
    else:
        session["joined"] = False
        return render_template("alreadyPlaying.html")

    return render_template("waitPage.html")

@main.route("/alreadyPlaying.html")
def already_playing():
    return render_template("alreadyPlaying.html")

@main.route("/question_page")
def question_page():

    result = request.args.get("result", None)

    if result is not None:

        session["question_count"] += 1

        if int(result) == session["answer"]:
            session["score"] += 1

    if session["question_count"] == max_questions:
        return redirect("/over_page")

    question_obj = AccessQuestions().get_random_question()

    question = question_obj.question
    options = question_obj.options
    answer = question_obj.answer
    session["answer"] = answer

    return render_template("questionPage.html",\
       question=question,\
       options=options,\
       answer=answer)

@main.route("/over_page")
def over_page():
    session["joined"] = False
    return render_template("overPage.html", score=session.get("score", None))

@main.route("/api/android/question_data/<num_questions>")
def question_data(num_questions):
    access_questions = AccessQuestions()
    questions = access_questions.get_random_questions(int(num_questions))

    return jsonify(result=questions)

@main.route("/api/android/post_score", methods=["POST"])
def post_score():
    print "DATA: {}".format(request.form["score"])
    return "Connection succeeded"
