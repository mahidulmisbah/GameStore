package com.example.gamestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

        Button submit;
        String LID,name,gmail,password,city;
        DatabaseHelperActivity mydatabase;

        EditText regEmail,regPassword,location,fullName;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

            mydatabase = new DatabaseHelperActivity(this);
            SQLiteDatabase sqLiteDatabase = mydatabase.getWritableDatabase();

            regEmail = (EditText)findViewById(R.id.reg_email);
            regPassword = (EditText)findViewById(R.id.reg_password);
            location = (EditText)findViewById(R.id.city);
            fullName = (EditText)findViewById(R.id.full_name);
            submit  = (Button)findViewById(R.id.submit);
            submit.setOnClickListener(this);
            Toast.makeText(this,"registration page",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onClick(View view) {
            gmail = regEmail.getText().toString();
            name = fullName.getText().toString();
            password = regPassword.getText().toString();
            city = location.getText().toString();
            Toast.makeText(this,gmail+" "+name+" "+password+" "+city,Toast.LENGTH_LONG).show();
            if( name.isEmpty() )
            {
                Toast.makeText(this,"Insert Your Full Name",Toast.LENGTH_LONG).show();
            }
            else if( gmail.isEmpty())
            {
                Toast.makeText(this,"Insert a valid Email Address",Toast.LENGTH_LONG).show();
            }
            else if( password.isEmpty() )
            {
                Toast.makeText(this,"Insert a Password only contains A-Z or a-z or 0-9",Toast.LENGTH_LONG).show();
            }
            else if(city.isEmpty())
            {
                Toast.makeText(this,"Insert your living city",Toast.LENGTH_LONG).show();
            }
            else {
                Cursor ROWNUM = mydatabase.countRow();
                int cnt = ROWNUM.getCount()+1;
                if(cnt<10) LID = "L00"+ cnt;
                else if(cnt<100) LID = "L0"+ cnt;
                else if(cnt<1000) LID = "L"+ cnt;

                Toast.makeText(SignUp.this,LID+" "+cnt,Toast.LENGTH_LONG).show();
                long rowId = mydatabase.insertIntoLogin(LID,gmail,name,password,city);
                if(rowId<0) Toast.makeText(this,"insertion Problem",Toast.LENGTH_LONG).show();
                else
                {

                    //Toast.makeText(this,"Your Registration is successfully done",Toast.LENGTH_LONG).show();
                    finish();
                    Intent backToLogin = new Intent(SignUp.this, MainActivity.class);
                    startActivity(backToLogin);
                }
            }
        }
    }
