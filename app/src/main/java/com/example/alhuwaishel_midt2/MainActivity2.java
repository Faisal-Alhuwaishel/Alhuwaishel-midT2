package com.example.alhuwaishel_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity2 extends AppCompatActivity {
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText ID = (EditText) findViewById(R.id.inp_id);
        EditText fname = (EditText) findViewById(R.id.inp_fname);
        EditText sname = (EditText) findViewById(R.id.inp_surname);
        EditText pid = (EditText) findViewById(R.id.inp_pid);
        Button button = (Button) findViewById(R.id.buttonAdd);
        Button btn1 = (Button) findViewById(R.id.btnAct1);
        Button btn3 = (Button) findViewById(R.id.btnAct3);
        myDB = new DatabaseHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ID.getText().toString();
                String first_name = fname.getText().toString();
                String surName = sname.getText().toString();
                String pID = pid.getText().toString();
                if(!id.equals("")  && !first_name.equals("") && !surName.equals("") && !pID.equals("")){ //checks input
                    if(!myDB.addData(first_name,surName,Integer.parseInt(pID))){
                        Toasty.error(getBaseContext(), "This is an error toast.",
                                Toast.LENGTH_SHORT, true).show();
                    }
                    else {
                        myDB.addData(first_name,surName,Integer.parseInt(pID));
                        Toasty.success(getBaseContext(), "Success.", Toast.LENGTH_SHORT,true).show();
                        Log.d("Success","Insertion is successful");
                    }
                }
                else{
                    Toast.makeText(MainActivity2.this,"Please Enter a valid input",
                            Toast.LENGTH_LONG).show();//invalid input
                }
            }
        });
    btn1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity2.this,MainActivity.class));
        }
    });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,MainActivity3.class));
            }
        });
    }
}