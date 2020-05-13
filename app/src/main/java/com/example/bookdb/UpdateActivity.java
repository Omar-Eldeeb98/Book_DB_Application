package com.example.bookdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input2 ,author_input2,pages_input2;
    Button update_button , delete_button;

    String id , title , author , pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update );

        title_input2 = (EditText) findViewById( R.id.title_input2 );
        author_input2 = (EditText) findViewById( R.id.author_input2 );
        pages_input2 = (EditText) findViewById( R.id.pages_input2 );
        update_button = (Button) findViewById( R.id.update_button );

        getAndSetIntentData();
        ActionBar ab = getSupportActionBar();
        if (ab!=null)
        {
            ab.setTitle("BOOK NAME | " +  title );
        }


        update_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabaseHelper myDB = new MyDatabaseHelper( UpdateActivity.this );
                title = title_input2.getText().toString().trim();
                author = author_input2.getText().toString().trim();
                pages = pages_input2.getText().toString().trim();
                myDB.updateData( id , title,author,pages );

            }
        });

        delete_button = (Button) findViewById( R.id.delete_button );
        delete_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeletionDialog();

            }
        } );

    }

    void getAndSetIntentData()
    {
        if (getIntent().hasExtra( "id" ) && getIntent().hasExtra( "title" ) &&
                getIntent().hasExtra( "author" ) && getIntent().hasExtra( "pages" ))
        {
            // get data from intent
            id = getIntent().getStringExtra( "id" );
            title = getIntent().getStringExtra( "title" );
            author = getIntent().getStringExtra( "author" );
            pages = getIntent().getStringExtra( "pages" );

            // set the data from intent
            title_input2.setText( title );
            author_input2.setText( author );
            pages_input2.setText( pages );

        }
        else
        {
            Toast.makeText( this , "no data" , Toast.LENGTH_SHORT ).show();
        }
    }

    // ask user to confirm deletion or not !
    void confirmDeletionDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Delete" + title + "?" );
        builder.setMessage( "are you sure to delete" + title + "?" );
        builder.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDb = new MyDatabaseHelper( UpdateActivity.this );
                myDb.deleteRow( id );
                finish();


            }
        } );
        builder.setNegativeButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        } );

        builder.create().show();

    }



    }

