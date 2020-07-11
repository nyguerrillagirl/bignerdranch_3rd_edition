package com.bignerdranch.android.geoquiz;

// Chapter 2 version
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";

    private static final String KEY_INDEX = "index";
    private static final String KEY_NUM_CORRECT = "num_correct";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.question_australia, true),
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex;
    private int mNumberOfCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called.");
        setContentView(R.layout.activity_quiz);
        // Check to see if the Bundle has the index to the current question
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mNumberOfCorrectAnswers = savedInstanceState.getInt(KEY_NUM_CORRECT, 0);
        }
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton = (ImageButton) findViewById(R.id.previous_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }
                updateQuestion();
            }
        });

    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        // determine if the True or False buttons should be disabled
        if (mQuestionBank[mCurrentIndex].isAnswered())  {
            // disable the button
            Log.d(TAG, "Both buttons will be disabled...");
            setButtons(false);
        } else {
            // enable the buttons
            Log.d(TAG, "Both button will be enabled...");
            setButtons(true);
        }

    }

    private void setButtons(boolean buttonEnableValue) {
        mTrueButton.setEnabled(buttonEnableValue);
        mFalseButton.setEnabled(buttonEnableValue);
    }

    private void checkAnswer(boolean userPressedTrue) {
        // set question as answered
        setButtons(false);
        mQuestionBank[mCurrentIndex].setAnswered(true);
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            ++mNumberOfCorrectAnswers;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this, messageResId,
                Toast.LENGTH_SHORT).show();

        checkIfAllQuestionAnswered();
    }

    @SuppressLint("DefaultLocale")
    private String getFinalMessage() {
        return String.format("You got %d %% correct.", (int)((double)mNumberOfCorrectAnswers / (double) mQuestionBank.length * 100.0));

    }
    private void checkIfAllQuestionAnswered() {
        int count = 0;
        for (int i=0; i < mQuestionBank.length; i++) {
            if (mQuestionBank[i].isAnswered()) {
                ++count;
            }
        }
        if (count == mQuestionBank.length) {
            // All questions answered
            Toast.makeText(QuizActivity.this, getFinalMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        // Save an index to the current question
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putInt(KEY_NUM_CORRECT, mNumberOfCorrectAnswers);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called.");
    }
}
