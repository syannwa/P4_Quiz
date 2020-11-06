package com.example.p4_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    ActionBar mActionBar;
    static Button submitButton;
    static Button viewPastResults;
    static Button newQuiz;
    static ArrayList<QuizQuestion> quizList;
    static QuizData quizQuestionsData;

    public QuizObject currentQuiz = new QuizObject();
    public static final String DEBUG_TAG = "DEBUG_QuizQuestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        QuizDBHelper helper = QuizDBHelper.getInstance(getApplicationContext());

        mActionBar = getSupportActionBar();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), 6);
        mActionBar.setTitle(mSectionsPagerAdapter.getPageTitle(0));
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if (Quiz.class.isInstance(this)) {
            quizQuestionsData = new QuizData(this);
            quizQuestionsData.close();
            quizQuestionsData.open();
            List<QuizQuestion> fullQuestionsList = quizQuestionsData.retrieveAllQuizQuestions();
            Log.d(DEBUG_TAG, "JobLeadDBReaderTask: Job leads retrieved: " + fullQuestionsList.size());

            quizList = new ArrayList<>();

            int size = 50;
            ArrayList<Integer> list = new ArrayList<>(size);
            for(int i = 0; i <= size; i++) {
                list.add(i);
            }

            Random rand = new Random();
            for (int i = 0; i < 6; i++) {
                int index = rand.nextInt(list.size());
                if(index != 0) {
                    quizList.add(fullQuestionsList.get(list.get(index)));
                }
                else
                    i--;
                Log.d( DEBUG_TAG, "Random number selected: " + list.get(index) );
                list.remove(index);
            }
            createQuiz(quizList);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setTitle(mSectionsPagerAdapter.getPageTitle(position));
                if(position != 5) {
                    //Sydney!!!
                    //grade question
                    //increment number of correct answers (if applicable)
                    Log.d(DEBUG_TAG, "Grade Question");
                }
                else if(position == 5) {
                    Log.d(DEBUG_TAG, "Store Quiz");
                    //Molly!!!
                    //show submit button : button onclick stores final q answer, quiz results, launches results screen
                    //this makes the button visible on the last page!
                    submitButton.setVisibility(View.VISIBLE);
                    submitButton.setOnClickListener(new Quiz.ButtonClickListener());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    /**
     * Listens for the button to be clicked and calls onClick when button is clicked
     */
    private class ButtonClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //goes to the results page if the submit button is pushed
            //for right now the score brought up isnt pulled from the database and is just a placeholder,
            //so that part needs to be changed. it gets the date but it needs to be stored into the database
            //if a date is already stored, just pull it from the database instead
            if(v == submitButton) {
                setContentView(R.layout.activity_result);
                newQuiz = (Button) findViewById(R.id.button);
                viewPastResults = (Button) findViewById(R.id.button2);
                newQuiz.setOnClickListener(new Quiz.ButtonClickListener());
                viewPastResults.setOnClickListener(new Quiz.ButtonClickListener());
                TextView resultText = (TextView) findViewById(R.id.textView3);
                TextView dateText = (TextView) findViewById(R.id.textView4);
                //need to get the result from the database and then set this text box to the appropriate value
                resultText.setText("6 out of 6");       //this is just a placeholder for now
                //should store this in the database or replace this with getting this from the database
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                String strDate = formatter.format(date);
                dateText.setText(strDate);
            }
            //if the new quiz button is pushed on the results page
            else if(v == newQuiz) {
                Intent intent = new Intent(v.getContext(), Quiz.class);
                startActivity(intent);
            }
            //if view past results button is pushed on the results page
            else if(v == viewPastResults)
            {
                //open view quiz results page

            }

        }
    }

    private QuizObject createQuiz(ArrayList<QuizQuestion> quizList) {
        currentQuiz.setQ1(quizList.get(0));
        currentQuiz.setQ2(quizList.get(0));
        currentQuiz.setQ3(quizList.get(0));
        currentQuiz.setQ4(quizList.get(0));
        currentQuiz.setQ5(quizList.get(0));
        currentQuiz.setQ6(quizList.get(0));

        return currentQuiz;
    }

    public void loadView(TextView question, String quest, RadioButton option1, String opt1, RadioButton option2,
                         String opt2, RadioButton option3, String opt3) {
        question.setText(quest);
        option1.setText(opt1);
        option2.setText(opt2);
        option3.setText(opt3);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final int mSize;

        public SectionsPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.mSize = size;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int imageNum = position + 1;
            currentQuiz.setNumberAnswered(position);
            Log.d(DEBUG_TAG, "Number Answered: " + currentQuiz.getNumberAnswered());
            return String.valueOf("Question " + imageNum);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private String quest;
        private String opt1;
        private String opt2;
        private String opt3;
        private int questionNum;
        private TextView question;
        private RadioButton option1;
        private RadioButton option2;
        private RadioButton option3;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                questionNum = getArguments().getInt(ARG_SECTION_NUMBER);
            } else {
                questionNum = -1;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_quiz, container, false);
            submitButton = (Button) rootView.findViewById(R.id.button3);
            question = (TextView) rootView.findViewById(R.id.textView1);
            option1 = (RadioButton) rootView.findViewById(R.id.radioButton);
            option2 = (RadioButton) rootView.findViewById(R.id.radioButton2);
            option3 = (RadioButton) rootView.findViewById(R.id.radioButton3);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            Log.d( DEBUG_TAG, "onBindViewHolder: " + quizList );

            ArrayList<Integer> intList = new ArrayList<>(3);
            for(int i = 0; i <= 2; i++) {
              intList.add(i);
            }

            ArrayList<String> options = new ArrayList<>();
            options.add(quizList.get(questionNum - 1).getCapital());
            options.add(quizList.get(questionNum - 1).getCity1());
            options.add(quizList.get(questionNum - 1).getCity2());

            Collections.shuffle(options);

            quest = "What is the capital of " + quizList.get(questionNum - 1).getState();

            opt1 = options.get(0);
            opt2 = options.get(1);
            opt3 = options.get(2);

            ((Quiz) getActivity()).loadView(question, quest, option1, opt1, option2, opt2, option3, opt3);
        }

        @Override
            public void onDestroy()
            {
                quizQuestionsData.close();
                super.onDestroy();
            }
        }

    }
