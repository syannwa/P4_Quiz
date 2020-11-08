package com.example.p4_quiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;

/**
 * The Main Activity for the State Capitals Quiz. Used to start a new quiz
 * or to view the past quizzes
 */
public class MainActivity extends AppCompatActivity {

    private Button continueButton;
    private Button viewQuizzes;
    private TextView introduction;
    private ImageView img;

    @Override
    /**
     * Creates the main menu splash screen for the app
     *
     * @param savedInstanceState the bundled saved state of the application
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grabs all the views and sets the variables
        continueButton = findViewById(R.id.button);
        viewQuizzes = findViewById(R.id.button2);
        introduction = findViewById(R.id.textView2);
        img = findViewById(R.id.imageView4);
        continueButton.setOnClickListener(new ButtonClickListener());


        //tries to open the resources for the main textView on the main activity.
        try
        {
            // Gain access to the app's resources
            Resources res = getResources();
            // Open a resource for reading and read its content into a byte array
            InputStream in_s = res.openRawResource(R.raw.description);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);

            // Display the content of the file
            introduction.setText(new String(b));
            //set the image on the splash screen
            img.setImageResource(R.drawable.map);
        }
        catch (Exception e)
        {
            introduction.setText("Error: can't show info text.");
        }

    }

    /**
     * Listens for the button to be clicked and calls onClick when button is clicked
     */
    private class ButtonClickListener implements View.OnClickListener
    {
        @Override
        /**
         * Runs when continue is clicked. When it is clicked, it opens a new intent for the page
         * that contains the fragments and our list of countries to be clicked.
         *
         * @param v the button view that has called the method
         */
        public void onClick(View v)
        {
            if(v == continueButton) {
                Intent intent = new Intent(v.getContext(), Quiz.class);
                startActivity(intent);
            }
            else if(v == viewQuizzes) {
                Log.d("View Past", "view quizzes");
                Intent intent = new Intent(v.getContext(), ReviewQuizzesActivity.class);
                startActivity(intent);
            }
        }
    }
}