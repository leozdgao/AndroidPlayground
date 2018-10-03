package me.leozdgao.mydemoapp;

import android.content.ContentResolver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ContentResolver resolver = getContentResolver();
//        resolver.query(DroidTermsExampleContract.CONTENT_URI);
    }
}
