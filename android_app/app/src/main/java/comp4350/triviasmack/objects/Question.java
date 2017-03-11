package comp4350.triviasmack.objects;

import java.io.Serializable;

public class Question implements Serializable {

    private String question;
    private String[] options;
    private int answer;

    public Question(String question, String[] options, int answer) {
        if (question == null) {
            throw new NullPointerException("String question cannot be null");
        }
        if (options == null) {
            throw new NullPointerException("String[] options cannot be null");
        }
        if (answer < 0) {
            throw new NullPointerException("int answer cannot be less than 0");
        }

        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getAnswer() {
        return answer;
    }
}
