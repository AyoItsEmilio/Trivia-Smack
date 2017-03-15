function TasksViewModel() {
    var self = this;
    var max = 3;
    var waitTime = 300;
    var grey = "#5A5A5A";
    var red = "#c13636";
    var green = "#4dc136";
    var white = "white";
    var countDownTime = 10;
    var oneSecond = 1000;
    var theCountDown;
    self.questionsURI = "/api/question_data/"+max;
    self.score = ko.observable(0);
    self.gameStarted = ko.observable(false);
    self.questionCount = ko.observable(0);
    self.showScore = ko.observable(false);
    self.counter = ko.observable(countDownTime);

    self.counter.subscribe(function(newValue) {
        if (newValue == 0){
            startCounter();
            self.questionCount(self.questionCount()+1);
        }
    });

    self.questionCount.subscribe(function(newValue) {
        if (newValue == max){
            setTimeout(function(){
                self.questionCount(0);
                self.gameStarted(false);
            }, waitTime)
        }
    });

    self.ajax = function(uri, method, data) {
        var request = {
            url: uri,
            type: method,
            contentType: "application/json",
            accepts: "application/json",
            cache: false,
            dataType: 'json',
            data: JSON.stringify(data),
            error: function(jqXHR) {
                alert(jqXHR.status);
                console.log("ajax error " + jqXHR.status);
            }
        };
        return $.ajax(request);
    }

    self.processAnswer = function(optionObj) {

        if (optionObj.isCorrect){
            self.score(self.score()+1)
            optionObj.option("Right!");    
            optionObj.bgColor(green);
            optionObj.textColor(white);
        }
        else{
            optionObj.option("Wrong!");
            optionObj.bgColor(red);
            optionObj.textColor(white);
        }

        setTimeout(function(){ 
            self.questionCount(self.questionCount()+1); 
        }, waitTime);

        startCounter();
    }

    self.startGame = function() {
        self.showScore(true);
        self.score(0);
        self.questions = ko.observableArray();
        fetchQuestions();
        self.gameStarted(true);

        startCounter();
    }

    function startCounter(){
        clearInterval(theCountDown);
        self.counter(countDownTime);
        theCountDown = 
        setInterval(function(){ self.counter(self.counter()-1) }, oneSecond);
    }

    function fetchQuestions(){
        self.ajax(self.questionsURI, "GET").done(function(data) {
            for (var i = 0; i < data.questions.length; i++) {

                var obs_options = ko.observableArray();

                for (var j = 0; j < data.questions[i].options.length; j++){
                    obs_options.push({
                        option: ko.observable(data.questions[i].options[j]),
                        bgColor: ko.observable(white),
                        textColor: ko.observable(grey),
                        isCorrect: data.questions[i].answer == j
                    });
                }

                self.questions.push({
                    question: ko.observable(data.questions[i].question),
                    options: obs_options,
                    answer: data.questions[i].answer
                });
            }
        });
    }
}
ko.applyBindings(new TasksViewModel(), $("#main")[0]);
