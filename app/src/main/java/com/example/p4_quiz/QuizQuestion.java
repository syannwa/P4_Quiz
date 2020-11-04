package com.example.p4_quiz;

public class QuizQuestion {
    private long   id;
    private String state;
    private String capital;
    private String city1;
    private String city2;

    public QuizQuestion()
    {
        this.id = -1;
        this.state = null;
        this.capital = null;
        this.city1 = null;
        this.city2 = null;

    }

    public QuizQuestion( String state, String capital, String city1, String city2) {
        this.id = -1;
        this.state = state;
        this.capital = capital;
        this.city1 = city1;
        this.city2 = city2;


    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getCapital() { return capital; }

    public void setCapital(String capital) { this.capital = capital; }

    public String getCity1()
    {
        return city1;
    }

    public void setCity1(String city1)
    {
        this.city1 = city1;
    }

    public String getCity2()
    {
        return city2;
    }

    public void setCity2(String city2)
    {
        this.city2 = city2;
    }

    public String toString()
    {
        return id + ": " + state + " " + capital + " " + city1 + " " + city2;
    }
}
