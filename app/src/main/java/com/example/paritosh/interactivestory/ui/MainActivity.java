package com.example.paritosh.interactivestory.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paritosh.interactivestory.R;

public class MainActivity extends AppCompatActivity {

    private EditText nameField;
    private Button button;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("MainActivity","Inside MainActivity class");
        super.onCreate(savedInstanceState);
        //Log.d("MainActivity","After OnCreate");
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.nameEditText);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameField.getText().toString();
                toStartActivity(name);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameField.setText("");
    }

    private void toStartActivity(String name) {
        Intent intent = new Intent(this, StoryActivity.class);
        Resources resources = getResources();
        String key = resources.getString(R.string.key_name);
        intent.putExtra(key, name);
        startActivity(intent);


    }
}
