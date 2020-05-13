package com.example.bookdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private EditText
            title_input ,
            author_input ,
            pages_input;

    private Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = (EditText) findViewById(R.id.title_input);
        author_input = (EditText) findViewById(R.id.author_input);
        pages_input = (EditText) findViewById(R.id.pages_input);

        add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);

                myDB.addBook(title_input.getText().toString().trim()
                        ,author_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim()));

            }
        });

    }
}
