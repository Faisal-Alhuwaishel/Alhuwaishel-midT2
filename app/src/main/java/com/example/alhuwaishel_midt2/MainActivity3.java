package com.example.alhuwaishel_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.dmoral.toasty.Toasty;

public class MainActivity3 extends AppCompatActivity {
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        EditText input = (EditText) findViewById(R.id.inputFname);
        Button btnFind = (Button) findViewById(R.id.findAll);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnAct2 = (Button) findViewById(R.id.btnAct2_Act3);
        TextView result = (TextView) findViewById(R.id.txtResult);
        myDB = new DatabaseHelper(this);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDB.getListContents();
                String res = "";
                while (cursor.moveToNext()){
                    res += "id: " + cursor.getString(0)+ " ";
                    res += "First name: " + cursor.getString( 1)+ " ";
                    res += "Surname: " + cursor.getString( 2)+ " ";
                    res += "Personal ID: " + cursor.getString( 3)+ "\n";
                }
                result.setText(res);
            }
        });
    btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        String name = input.getText().toString();
        Cursor cursor = myDB.deleteSpecificProduct(name);
            String res = "";
            while (cursor.moveToNext()){
                res += "id: " + cursor.getString(0)+ " ";
                res += "First name: " + cursor.getString( 1)+ " ";
                res += "Surname: " + cursor.getString( 2)+ " ";
                res += "Personal ID: " + cursor.getString( 3)+ "\n";
            }
            Toasty.success(getBaseContext(), "Success."+res, Toasty.LENGTH_SHORT,true).show();
            Log.d("Success","Entry Deleted"+res);
        }
    });
    btnAct2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity3.this,MainActivity2.class));
        }
    });
    }

}