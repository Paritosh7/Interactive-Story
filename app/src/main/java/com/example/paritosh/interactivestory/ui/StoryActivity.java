package com.example.paritosh.interactivestory.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paritosh.interactivestory.R;
import com.example.paritosh.interactivestory.model.Page;
import com.example.paritosh.interactivestory.model.Story;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity {


    private Story story;
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button choice1Button;dfdfv
    private Button choice2Button;
    private String name;
    Stack<Integer> pageStack = new Stack<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);


        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_name));
        if (name == null || name.isEmpty()) {
            name = "Friend";
        }

        //Log.d("Value of name",name);

        story = new Story();
        loadPage(0);


    }

    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);
        final Page page = story.getPage(pageNumber);
        storyImageView.setImageDrawable(ContextCompat.getDrawable(this, page.getImageId()));


        animation();


        String pageText = getString(page.getTextId());

        storyTextView.setText(String.format(pageText, name));

        if (page.getFinalPage()) {

            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.play_again_button_text);
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } else {

            loadButtons(page);
        }
    }


    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPage(page.getChoice1().getNextPage());
            }
        });

        choice2Button.setText(getString(page.getChoice2().getTextId()));
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadPage(page.getChoice2().getNextPage());

            }
        });
    }

    private void animation() {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(storyImageView, "alpha", 1f, .3f);
        fadeOut.setDuration(2000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(storyImageView, "alpha", 0.3f, 1f);
        fadeIn.setDuration(2000);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeIn)
                .after(3000)
                .after(fadeOut);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }


        });
        animatorSet.start();
    }

    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.isEmpty()) {
            super.onBackPressed();
        } else
            loadPage(pageStack.pop());
    }
}
