package com.example.p4_quiz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * QuizQuestion defines a question in the Quiz
 */
public class QuizQuestion implements Parcelable {
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

    protected QuizQuestion(Parcel in) {
        id = in.readLong();
        state = in.readString();
        capital = in.readString();
        city1 = in.readString();
        city2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(state);
        dest.writeString(capital);
        dest.writeString(city1);
        dest.writeString(city2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizQuestion> CREATOR = new Creator<QuizQuestion>() {
        @Override
        public QuizQuestion createFromParcel(Parcel in) {
            return new QuizQuestion(in);
        }

        @Override
        public QuizQuestion[] newArray(int size) {
            return new QuizQuestion[size];
        }
    };

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

    public boolean gradeQuestion(String answer) {
        if(capital.equals(answer)) {
            return true;
        }
        else return false;
    }
}
