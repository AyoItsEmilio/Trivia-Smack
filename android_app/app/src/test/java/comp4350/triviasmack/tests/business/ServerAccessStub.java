package comp4350.triviasmack.tests.business;


import java.util.ArrayList;
import java.util.Collections;


import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.objects.Question;


public class ServerAccessStub implements ServerAccess {

    private final ArrayList<Question> questions;
    private final static String category = "all";

    public ServerAccessStub() {
        questions = new ArrayList<>();
        System.out.println("Opened stub server access");

        String optionsA[] = {"1200 lbs", "1000 lbs", "600 lbs", "Enough to break the ice"};
        questions.add(new Question("How much does a Polar Bear weigh?", optionsA, 1, category));

        String optionsB[] = {"zero", "greater than 3", "less than 3"};
        questions.add(new Question("Is the square root of 10:", optionsB, 1, category));

        String optionsC[] = {"true", "false"};
        questions.add(new Question("Platypuses lay eggs", optionsC, 0, category));

        String optionsD[] = {"Sweden", "Russia", "Finland", "Iceland"};
        questions.add(new Question("Helsinki is the capitol of:", optionsD, 2, category));

        String optionsE[] = {"0", "1", "4", "3"};
        questions.add(new Question("If x+y=3 and 2x+y=4, then x equals", optionsE, 1, category));

        String optionsF[] = {"positive", "negative", "Not determinable"};
        questions.add(new Question("If x+y<11 and x>6, then y is:", optionsF, 2, category));

        String optionsG[] = {"bisons", "buffalo", "bison", "buffalos"};
        questions.add(new Question("The plural of bison is:", optionsG, 2, category));

        String optionsH[] = {"162", "113", "144", "145"};
        questions.add(new Question("21, 25, 33, 49, 81, ", optionsH, 2, category));

        String optionsI[] = {"South America", "Europe", "Australia", "Asia"};
        questions.add(new Question("The Balkans are in:", optionsI, 1, category));

        String optionsJ[] = {"1", "18", "None"};
        questions.add(new Question("How much wood would a woodchuck chuck if a woodchuck could chuck wood?", optionsJ, 1, category));
    }

    public void getRandomQuestions(ArrayList<Question> questions, int numQuestions, String category) {

        if (numQuestions > this.questions.size()) {
            numQuestions = this.questions.size();
        }

        questions.addAll(this.questions.subList(0, numQuestions));
        Collections.shuffle(questions);
    }
}
