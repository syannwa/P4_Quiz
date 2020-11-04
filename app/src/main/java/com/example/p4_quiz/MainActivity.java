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

public class MainActivity extends AppCompatActivity {

    private Button continueButton;
    private TextView introduction;
    private ImageView planeImage;

    @Override
    /**
     * Creates and sets all variables to their corresponding views
     * in the app. So the splash screen is properly created and when the
     * continue button is clicked, it continues to the other main page
     * that contains the list of countries to pick from.
     *
     * @param savedInstanceState the bundled saved state of the application
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grabs all the views and sets the variables
        continueButton = findViewById(R.id.button);
        introduction = findViewById(R.id.textView2);
        planeImage = findViewById(R.id.imageView4);
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
            planeImage.setImageResource(R.drawable.map);
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
            Intent intent = new Intent(v.getContext(), Quiz.class);
            startActivity(intent);
        }
    }
}