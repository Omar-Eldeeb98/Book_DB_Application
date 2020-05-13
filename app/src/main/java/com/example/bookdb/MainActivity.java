package com.example.bookdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton add_Button;
    private MyDatabaseHelper myDb;
    private ArrayList<String>
            book_id,
            book_title,
            book_author,
            book_pages;

    private CustomAdapter customAdapter;

    private ImageView emptyImageView;
    private TextView noDataTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyImageView = (ImageView) findViewById( R.id.empty_image_view );
        noDataTextView = (TextView) findViewById( R.id.no_data_text_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        add_Button = (FloatingActionButton) findViewById(R.id.add_btn);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);

            }
        });

        myDb = new MyDatabaseHelper(MainActivity.this);

        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();



        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this,book_id,book_title,book_author,book_pages);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(customAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == 1)
        {
            recreate();
        }
    }

    public void storeDataInArrays()
    {
        Cursor cursor = myDb.readAllData();
        if(cursor.getCount() == 0 )
        {
         emptyImageView.setVisibility(View.VISIBLE);
         noDataTextView.setVisibility( View.VISIBLE );
        }
        else
        {
            while (cursor.moveToNext())
            {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));

            }

            emptyImageView.setVisibility(View.GONE);
            noDataTextView.setVisibility( View.GONE );

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  =getMenuInflater();
        inflater.inflate( R.menu.my_menu,menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()  == R.id.delete_all)
        {
            confirmDeletionDialog();

        }
        return super.onOptionsItemSelected( item );
    }

    // ask user to confirm deletion or not !
    void confirmDeletionDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle("Delete All?");
        builder.setMessage( "are you sure to delete all?" );
        builder.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper( MainActivity.this );
                myDB.deleteAll();

                // to avoid black screen when delete all row and back to the mainActivity:
                Intent intent = new Intent( MainActivity.this , MainActivity.class );
                startActivity( intent );
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
