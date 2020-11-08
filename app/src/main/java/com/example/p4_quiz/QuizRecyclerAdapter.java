package com.example.p4_quiz;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The QuizRecyclerAdapter is the adapter to show the previous quizzes
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {

    public static final String DEBUG_TAG = "QuizRecyclerAdapter";

    private List<QuizObject> quizList;

    public QuizRecyclerAdapter( List<QuizObject> quizList ) {
        this.quizList = quizList;
    }

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
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quiz_result, parent, false );
        return new QuizHolder( view );
    }

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
