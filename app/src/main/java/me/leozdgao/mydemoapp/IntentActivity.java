package me.leozdgao.mydemoapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class IntentActivity extends AppCompatActivity {
    Button btnIntnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        Log.i("Lifecycle", "onCreate");

        btnIntnet = (Button) findViewById(R.id.btn_intent);
        btnIntnet.setOnClickListener((v) -> {
//            String value = "Some value here";
//            Context context = IntentActivity.this;
//            Class destActivity = ChildActivity.class;
//            Intent intent = new Intent(context, destActivity);
//            intent.putExtra(Intent.EXTRA_TEXT, value);
//
//            startActivity(intent);

            openWebPage("http://www.baidu.com");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("Lifecycle", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("Lifecycle", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("Lifecycle", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("Lifecycle", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("Lifecycle", "onDestroy");
    }

    private void openWebPage(String url) {
        Uri destUri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, destUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
