package comp4350.triviasmack.objects;

import java.io.Serializable;

public class Profile implements Serializable{

    private String username;
    private int score;

    public Profile(String username, int score) {
        if (username == null){
            throw new NullPointerException("String username cannot be null");
        }
        if (score < 0){
            throw new NullPointerException("int score cannot be less than 0");
        }

        this.username = username;
        this.score = score;
    }

    public Profile(){
        this.username = "Username";
        this.score = 0;
    }

    public String getUsername()                 { return username; }
    public void setUsername(String username)    { this.username = username; }

    public int getScore()                       { return score; }
    public void setScore(int score)             { this.score = score; }
    public void addScore(int score)             { this.score += score; }
}
