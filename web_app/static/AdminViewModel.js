$(document).ready(function(){
function AdminViewModel() {
    var self = this;
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
    self.loginURI = "/api/login";
    self.addQuestionURI = "/api/add_question";
    self.getQuestionsURI = "/api/get_questions";
    self.getQuestionContainsURI = "/api/get_question_contains/";

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
                console.log("ajax error " + jqXHR.status);
            }
        };
        return $.ajax(request);
    };

    self.login = function() {
        self.ajax(self.loginURI, "GET").done(function(data) {
            console.log("success");
            self.loggedIn(true);
        }).fail(function(jqXHR) {
            console.log("failure");
        });
    };

    self.showOptions = function() {
        self.addingQuestion(false);
        self.viewingQuestions(false);
    };

    self.startAdding = function() {
        self.addingQuestion(true);
    };

    self.addQuestion = function() {

        questionJson = {
            "question": self.question(),
            "options": self.options().split(","),
            "answer": self.answer()};

        self.ajax(self.addQuestionURI, "POST", questionJson).done(function(data) {
            console.log("success");
            self.addingQuestion(false);
        }).fail(function(jqXHR) {
            console.log("failure");
        });
    };

    self.startViewing = function() {

        self.questions.removeAll();

        self.ajax(self.getQuestionsURI, "GET").done(function(data) {
            fillQuestions(data);
        }).fail(function(jqXHR) {
            console.log("failure");
        });

        self.viewingQuestions(true);
    };

    self.getQuestionContains = function() {

        self.questions.removeAll();

        if (self.filter() !== "") {
            self.ajax(self.getQuestionContainsURI+self.filter(), "GET").done(function(data) {
                fillQuestions(data);
            }).fail(function(jqXHR) {
                console.log("failure");
            });
        }
        else {
            self.startViewing();
        }
    };

    function fillQuestions(data) {

        for (var i = 0; i < data.questions.length; i++) {

            self.questions.push({
                question: ko.observable(data.questions[i].question),
                options: ko.observableArray(data.questions[i].options),
                answer: ko.observable(data.questions[i].answer)
            });
        }
    }
}

ko.applyBindings(new AdminViewModel(), $("#admin")[0]);
});
