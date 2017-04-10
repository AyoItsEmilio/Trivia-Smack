package comp4350.triviasmack.objects;

import java.io.Serializable;

public class Question implements Serializable {

    private String question;
    private String[] options;
    private int answer;
    String category;

    public Question(String question, String[] options, int answer, String category) {
        if (question == null) {
            throw new NullPointerException("String question cannot be null");
        }
        if (options == null) {
            throw new NullPointerException("String[] options cannot be null");
        }
        if (answer < 0) {
            throw new NullPointerException("int answer cannot be less than 0");
        }
        if (category == null) {
            throw new NullPointerException("String category cannot be null");
        }

        this.question = question;
        this.options = options;
        this.answer = answer;
        this.category = category;
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

    public String getCategory() { return category; }
}
