package com.example.mathdev305.edittexttowidget;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String PREF_NAME = "pref";
    private final static String SAVE_TEXT_KEY = "save_text_key";
    private EditText editText;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SAVE_TEXT_KEY, text);
                editor.apply();

                Toast.makeText(getBaseContext(), "Text Saved", Toast.LENGTH_LONG).show();
            }
        });
        loadTextFromPref();


    }

    private void loadTextFromPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String text = sharedPreferences.getString(SAVE_TEXT_KEY, "");
        editText.setText(text);

    }
}
