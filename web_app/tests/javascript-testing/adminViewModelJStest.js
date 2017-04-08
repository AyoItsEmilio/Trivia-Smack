describe('AdminViewModel', function(){
  var test;
  var avm;
  beforeEach(function(){
   avm = new AdminViewModel();
 });

    it('Show options', function(){
      avm.showOptions();
      expect(avm.addingQuestion()).toBe(false);
      expect(avm.viewingQuestions()).toBe(false);
    });

    it('Start adding', function(){
      avm.startAdding();
      expect(avm.addingQuestion()).toBe(true);
    });

    it('Start Viewing"', function(){
      avm.startViewing();
      expect(avm.viewingQuestions()).toBe(true);
    });


  });
