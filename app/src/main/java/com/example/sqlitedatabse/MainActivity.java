package com.example.sqlitedatabse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd,btnReset;
    private EditText editText,lastname;
    private RecyclerView recyclerView;

    List<User> userList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDb roomDb;
    Adapter adapter;

    User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnReset = findViewById(R.id.btnReset);
        editText = findViewById(R.id.et_edittext);
        lastname = findViewById(R.id.et_lastName);

        roomDb = RoomDb.getInstance(this);
        userList = roomDb.userDao().getAll();

        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAction();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReset();
            }
        });



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                adapter = new Adapter(MainActivity.this,userList);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    private void btnAction(){
        String sFirstname = editText.getText().toString().trim();
        String sLastname = lastname.getText().toString();


        if (!sFirstname.equals("") && !sLastname.equals("")){
            User user = new User();
            user.setFirstName(sFirstname);
            user.setLastName(sLastname);

            roomDb.userDao().insert(user);
            editText.setText("");
            lastname.setText("");
            userList.clear();
            userList.addAll(roomDb.userDao().getAll());

            adapter.notifyDataSetChanged();
        }

    }

    private void btnReset(){
        roomDb.userDao().reset(userList);
        userList.clear();
        userList.addAll(roomDb.userDao().getAll());
        adapter.notifyDataSetChanged();
    }



}

