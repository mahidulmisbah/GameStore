package com.example.gamestore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gamestore.R;

import static android.view.animation.AnimationUtils.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelperActivity myDatabaseHelper;
    EditText gmail,password;
    TextView registration;
    Button login ;
    int loginCheck;
    private String s,login_gmail,login_pass,login_id,login_name;
    private String gm,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation blickAnimation = AnimationUtils.loadAnimation(this,R.anim.blink);
        myDatabaseHelper = new DatabaseHelperActivity(this);
        SQLiteDatabase sqLiteDatabase= myDatabaseHelper.getWritableDatabase();
        gmail = (EditText)findViewById(R.id.emaill);
        password = (EditText)findViewById(R.id.password);

        registration = (TextView)findViewById(R.id.registration);
        registration.startAnimation(blickAnimation);
        registration.setOnClickListener(this);

        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login){
            loginCheck = 0;

            gm = gmail.getText().toString();
            pass = password.getText().toString();
            Log.i("message", gm);

            Cursor result = myDatabaseHelper.readFromLogin();
            while(result.moveToNext())
            {
                login_id = result.getString(0);
                login_gmail = result.getString(1);
                login_name = result.getString(2);
                login_pass = result.getString(3);
                if(gm.equalsIgnoreCase(login_gmail))
                {
                    if(pass.compareTo(login_pass) == 0) {
                        loginCheck = 1;
                        gmail.setText("");
                        password.setText("");

                        Intent gameOption = new Intent(MainActivity.this, GameOptionActivity.class);
                        gameOption.putExtra("LID",login_id);
                        gameOption.putExtra("Name",login_name);
                        startActivity(gameOption);
                    }
                }
            }

            if(loginCheck == 0) Toast.makeText(MainActivity.this,"Account Does Not Exits",Toast.LENGTH_LONG).show();
        }

        else if(view.getId()==R.id.registration)
        {
            Intent registration = new Intent(MainActivity.this,SignUp.class);
            startActivity(registration);
        }
    }


    public void onBackPressed() {
        AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogbuilder.setIcon(R.drawable.exit);
        alertDialogbuilder.setTitle(R.string.app_name);
        alertDialogbuilder.setMessage(R.string.exit);
        alertDialogbuilder.setCancelable(false);
        alertDialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }

        });
        AlertDialog alertDialog = alertDialogbuilder.create();
        alertDialog.show();
    }

}