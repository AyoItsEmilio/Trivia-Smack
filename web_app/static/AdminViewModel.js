function AdminViewModel() {
    var self = this;
    var initBound = 10;
    self.username = ko.observable("");
    self.password = ko.observable("");
    self.loggedIn = ko.observable(false);
    self.addingQuestion = ko.observable(false);
    self.viewingQuestions = ko.observable(false);
    self.question = ko.observable("");
    self.options = ko.observable("");
    self.answer = ko.observable("");
    self.questions = ko.observableArray();
    self.filter = ko.observable("");
    self.loginError = ko.observable("");
    self.warningMessage = ko.observable("");
    self.category = ko.observable("");
    self.questionBound = ko.observable(initBound);
    self.moreText = ko.observable("more");
    self.maxQuestions = ko.observable(0);
    self.loginURI = "/api/login";
    self.addQuestionURI = "/api/add_question";
    self.getQuestionsURI = "/api/get_questions";
    self.getQuestionContainsURI = "/api/get_question_contains/";
    self.delQuestionURI = "/api/delete_question/";

    self.maxQuestions.subscribe(function(newValue){
        if (self.questionBound() < newValue){
            self.questionBound(initBound);
            self.moreText("more");
        }
    });

    self.login = function() {
        self.ajax(self.loginURI, "GET").done(function(data) {
            self.loginError("");
            self.loggedIn(true);
            fetchQuestions(self.getQuestionsURI);
        }).fail(function(jqXHR) {

            self.loginError("");
            setTimeout(function(){
                self.loginError("WRONG!");
            }, 90);

            self.username("");
            self.password("");
            console.log("Credential failure");
        });
    };

    self.showMore = function() {
        newBound = self.questionBound() + initBound;
        if (newBound < self.questions().length)
            self.questionBound(newBound);
        else
            self.moreText("no more!");
    };

    self.showOptions = function() {
        self.addingQuestion(false);
        self.viewingQuestions(false);
    };

    self.startAdding = function() {
        self.question("");
        self.options("");
        self.answer("");
        self.category("");
        self.warningMessage("");
        self.addingQuestion(true);
    };

    self.addQuestion = function() {

        questionJson = {
            "question": self.question(),
            "options": self.options().split(","),
            "answer": self.answer(),
            "category": self.category()
        };

        self.ajax(self.addQuestionURI, "POST", questionJson).done(function(data) {
            self.addingQuestion(false);
        }).fail(function(jqXHR) {
            jsonResult = jQuery.parseJSON(jqXHR.responseText); 
            self.warningMessage("");

            setTimeout(function(){
                self.warningMessage(jsonResult.result.errorMsg);
            }, 90);

            console.log("Ajax failure");
        });
    };

    self.startViewing = function() {

        self.filter("");

        self.viewingQuestions(true);
    };

    self.getQuestionContains = function() {

        if (self.filter() !== "") {
            fetchQuestions(self.getQuestionContainsURI+self.filter());
        }
        else {
            fetchQuestions(self.getQuestionsURI);
        }
    };

    self.deleteQuestion = function(q) {

        self.ajax(self.delQuestionURI+q.question(), "DELETE").done(function(data) {
            result = data[0].result;

            if (result) self.questions.remove(q);

        }).fail(function(jqXHR) {
            console.log("Ajax failure");
        });
    };

    self.ajax = function(uri, method, data) {
        var request = {
            url: uri,
            type: method,
            contentType: "application/json",
            accepts: "application/json",
            cache: false,
            dataType: "json",
            data: JSON.stringify(data),
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", 
                    "Basic " + btoa(self.username() + ":" + self.password()));
            },
            error: function(jqXHR) {
                console.log("Ajax failure");
            }
        };
        return $.ajax(request);
    };

    function fetchQuestions(uri) {

        self.ajax(uri, "GET").done(function(data) {
            buildQuestions(data);
        }).fail(function(jqXHR) {
            console.log("Ajax failure");
        });
    }

    function buildQuestions(data) {
        for (var i = 0; i < data.questions.length; i++) {

            if (!containsQuestion(self.questions(), data.questions[i].question, true)) {
                self.questions.push({
                    question: ko.observable(data.questions[i].question),
                    options: ko.observableArray(data.questions[i].options),
                    answer: ko.observable(data.questions[i].answer)
                });
            }
        }

        intersect(data.questions);
        self.maxQuestions(self.questions().length);  
    }

    function intersect(fetched) {
        toRemove = [];

        for (var j = 0; j < self.questions().length; j++) {
            observable = self.questions()[j].question();

            if (!containsQuestion(fetched, observable, false))
                toRemove.push(self.questions()[j]);
        }

        for (var k = 0; k < toRemove.length; k++)
            self.questions.remove(toRemove[k]);
    }

    function containsQuestion(qArray, question, arrIsObs) {
        result = false;

        for (var j = 0; j < qArray.length; j++) {
            if (arrIsObs) val = qArray[j].question();
            else val = qArray[j].question;

            if (question === val) result = true;
        }
        return result;
    }
}

