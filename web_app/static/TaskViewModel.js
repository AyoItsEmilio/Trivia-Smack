$(document).ready(function(){
function TasksViewModel() {
    var self = this;
    var loc = location.protocol + "//" + document.domain + ":" + location.port;
    var max = 2;
    var waitTime = 300;
    var red = "#c13636";
    var green = "#4dc136";
    var countDownTime = 10;
    var oneSecond = 1000;
    var theCountDown;
    self.category = null;
    self.questionsURI = "/api/question_data/"+max;
    self.score = ko.observable(null);
    self.otherScore = ko.observable(null);
    self.questionCount = ko.observable(0);
    self.counter = ko.observable(countDownTime);
    self.isWaiting = ko.observable(false);
    self.showingCategories = ko.observable(false);
    self.onePlayerMode = ko.observable(true);
    self.isPlaying = ko.observable(false);
    
    self.categories = ['all', 'animals', 'books', 'geography',
    'history', 'math', 'movies and TV', 'music', 'other',
    'science', 'sports'];


    self.counter.subscribe(function(newValue) {
        if (newValue === 0){
            startCounter();
            self.questionCount(self.questionCount()+1);
        }
    });

    self.questionCount.subscribe(function(newValue) {
        if (newValue == max){
            console.log("the game ended :)");
            endGame();
        }
    });

    self.startTwoPlayer = function() {
        socket = io.connect(loc, {"force new connection": true});
        self.onePlayerMode(false);
        self.isWaiting(true);

        socket.emit("join_game");

        socket.on("other_player_done", function(data){
            var result;

            if (data.msg === null)
                result = "Other player disconnected! You win!";
            else
                result = data.msg;

            self.otherScore(result);
        });

        socket.on("other_player_ready", function() {
            self.isWaiting(false);
            self.otherScore("Waiting for other player");
            startGame();
        });

        socket.on("clean_up", function() {
            socket.disconnect();
        });
    };

    self.startOnePlayer = function() {
        self.onePlayerMode(true);
        if(self.showingCategories(false)){
            self.showingCategories(true);
        }
        self.isWaiting(false);
        self.otherScore(null);
        // startGame();
    };

    self.chooseCategory = function(category) {
        self.category = category;
        console.log("Category clicked is " + self.category)
        self.questionsURI = self.questionsURI+"/"+self.category
        self.showingCategories(false);
        startGame();
    }

    function startGame() {
        self.questions = ko.observableArray();
        fetchQuestions();
        self.isPlaying(true);
        self.score(0);
        self.questionCount(0);
        startCounter();
    }

    function endGame() {
        self.isPlaying(false);
        if (!self.onePlayerMode()){
            socket.emit("game_over", {"score":self.score()});
            console.log(socket);
        }
        self.questionsURI = "/api/question_data/"+max;

    }

    self.processAnswer = function(optionObj) {

        if (optionObj.isCorrect){
            self.score(self.score()+ self.counter());
            optionObj.option("Right!");    
            optionObj.bgColor(green);
        }
        else{
            optionObj.option("Wrong!");
            optionObj.bgColor(red);
        }

        setTimeout(function(){ 
            self.questionCount(self.questionCount()+1); 
        }, waitTime);

        startCounter();
    };

    function startCounter(){
        clearInterval(theCountDown);
        self.counter(countDownTime);
        theCountDown = 
        setInterval(function(){ self.counter(self.counter()-1); }, oneSecond);
    }

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
                console.log("ajax error " + jqXHR.status);
            }
        };
        return $.ajax(request);
    };

    function fetchQuestions(){
        self.ajax(self.questionsURI, "GET").done(function(data) {
            for (var i = 0; i < data.questions.length; i++) {

                var obs_options = ko.observableArray();

                for (var j = 0; j < data.questions[i].options.length; j++){
                    obs_options.push({
                        option: ko.observable(data.questions[i].options[j]),
                        bgColor: ko.observable(),
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
});
