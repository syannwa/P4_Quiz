package com.example.p4_quiz;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {

    public static final String DEBUG_TAG = "QuizRecyclerAdapter";

    private List<QuizObject> quizList;

    public QuizRecyclerAdapter( List<QuizObject> quizList ) {
        this.quizList = quizList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class QuizHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView score;

        public QuizHolder(View itemView ) {
            super(itemView);

            date = (TextView) itemView.findViewById( R.id.date );
            score = (TextView) itemView.findViewById( R.id.score );

        }
    }

    @Override
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_result, parent, false );
        return new QuizHolder( view );
    }

    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder( QuizHolder holder, int position ) {
        QuizObject quiz = quizList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + quiz );

        holder.score.setText( "" + quiz.getScore() );
        holder.date.setText( quiz.getDate() );
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

}
