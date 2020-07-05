package com.bignerdranch.android.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity {
    private static final int TOAST_Y_OFFSET = 150;

    @BindView(R.id.true_button)
    Button mTrueButton;

    @BindView(R.id.false_button)
    Button mFalseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.true_button)
    public void pressedTrueButton() {
        Toast toast = Toast.makeText(QuizActivity.this, R.string.correct_toast,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,TOAST_Y_OFFSET);
        toast.show();
    }

    @OnClick(R.id.false_button)
    public void pressedFalseButton() {
        Toast toast = Toast.makeText(QuizActivity.this, R.string.incorrect_toast,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, TOAST_Y_OFFSET);
        toast.show();
    }
}
