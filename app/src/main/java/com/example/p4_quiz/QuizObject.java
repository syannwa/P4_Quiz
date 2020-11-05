package com.example.p4_quiz;

import java.util.Date;

public class QuizObject {
    private long   id;
    private QuizQuestion q1;
    private QuizQuestion q2;
    private QuizQuestion q3;
    private QuizQuestion q4;
    private QuizQuestion q5;
    private QuizQuestion q6;
    private Integer numberAnswered;
    private Date date;
    private Integer score;


    public QuizObject() {
        this.id = -1;
        this.q1 = null;
        this.q2 = null;
        this.q3 = null;
        this.q4 = null;
        this.q5 = null;
        this.q6 = null;
        this.numberAnswered = 0;
        this.date = null;
        this.score = 0;
    }

    public QuizObject(QuizQuestion q1, QuizQuestion q2, QuizQuestion q3, QuizQuestion q4,
                      QuizQuestion q5, QuizQuestion q6){
        this.id = -1;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.numberAnswered = 0;
        this.score = 0;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getQ1() { return q1.toString(); }

    public void setQ1(QuizQuestion q) { this.q1 = q; }

    public String getQ2() { return q2.toString(); }

    public void setQ2(QuizQuestion q) { this.q2 = q; }

    public String getQ3() { return q3.toString(); }

    public void setQ3(QuizQuestion q) { this.q3 = q; }

    public String getQ4() { return q4.toString(); }

    public void setQ4(QuizQuestion q) { this.q4 = q; }

    public String getQ5() { return q5.toString(); }

    public void setQ5(QuizQuestion q) { this.q5 = q; }

    public String getQ6() { return q6.toString(); }

    public void setQ6(QuizQuestion q) { this.q6 = q; }

    public Integer getNumberAnswered() { return numberAnswered; }

    public void setNumberAnswered(Integer numberAnswered) { this.numberAnswered = numberAnswered; }

    public void incrementNumberAnswered() { this.numberAnswered += 1; }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score) { this.score = score; }

    public void incrementScore() { this.score += 1; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }


    public String toString()
    {
        return id + ": " + score + " " + date;
    }
}
