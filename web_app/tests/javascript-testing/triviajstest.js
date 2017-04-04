describe('fetchQuestions', function(){
  var test;
  var tvm;
  beforeEach(function(){
   tvm = new TasksViewModel();
 });



    it('Start one player', function(){
      tvm.startOnePlayer();
      expect(tvm.onePlayerMode()).toBe(true);
    });

    it('Start one player, "is waiting to be false"', function(){
      tvm.startOnePlayer();
      expect(tvm.isWaiting()).toBe(false);
    });

    it('Start one player, "other score should be null "', function(){
      tvm.startOnePlayer();
      expect(tvm.otherScore()).toBe(null);
    });

    it('startGame - is Playing should be true', function(){
      tvm.startGame();
      expect(tvm.isPlaying()).toBe(true);
    });

    it('startGame - score to be zero', function(){
      tvm.startGame();
      expect(tvm.score()).toBe(0);
    });

    it('startGame - question Count to begin at 0 ' , function(){
      tvm.startGame();
      expect(tvm.questionCount()).toBe(0);
    });

    it('Gets Questions', function(){

      var result = tvm.buildQuestions({"questions":[{"question":"2+2", "options":["4","0","2"], "answer":"2"}]});
      expect(result.length).toBeGreaterThan(0);

    });



  });
