package com.example.roomdatadase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView lbl,t5;
    EditText t1,t2,t3;
    Button b1,b2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=findViewById(R.id.editTextTextPersonName);
        t2=findViewById(R.id.editTextTextPersonName2);
        t3=findViewById(R.id.editTextTextPersonName3);


        b1=findViewById(R.id.button);
        b2=findViewById(R.id.fetch);
        lbl=findViewById(R.id.label);
        t5=findViewById(R.id.holder);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bgThread().start();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "newroom").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                List<User> userList = userDao.myuser();
                String str="";
                for(User users :userList){
                    str=str+"\t"+"ID: "+users.uid+"\n"+"Last Name: "+users.firstName+"\n "+"Firstname: "+users.lastName+"\n";
                    t5.setText(str);
                }

            }
        });

    }



    class bgThread extends Thread{

        @Override
        public void run() {
            super.run();
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "newroom").allowMainThreadQueries().build();

            UserDao userDao = db.userDao();
           // userDao.insertrecord(new User(2,t1.getText().toString(),t2.getText().toString()));
            Boolean check = userDao.is_exist(Integer.parseInt(t1.getText().toString()));
            if(!check){
                userDao.insertrecord(new User(Integer.parseInt(t1.getText().toString()), t2.getText().toString(),t3.getText().toString()));
                lbl.setText("inserted successfully");
            }else{

                lbl.setText(" already exists in database");
            }





        }
    }
}