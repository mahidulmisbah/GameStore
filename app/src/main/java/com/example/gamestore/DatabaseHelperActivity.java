package com.example.gamestore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelperActivity extends SQLiteOpenHelper {
    private Context context;

    private static final int DatabaseVersion = 2;
    private static final String DatabaseName = "GameZone";
    private static final String CreateTableLogin = "CREATE TABLE LOGIN ( LID VARCHAR(4) PRIMARY KEY NOT NULL,Gmail VARCHAR(50),Name VARCHAR(50),Password VARCHAR(8),CITY VARCHAR(25));";
    private static final String CreateTableGame = "CREATE TABLE GAME ( GID VARCHAR(4) PRIMARY KEY NOT NULL,GameName VARCHAR(30),HighScore NUMBER);";
    private static final String CreateTableProgress = "CREATE TABLE PROGRESS (LID VARCHAR(4) NOT NULL,GID VARCHAR(4) NOT NULL,AverageScore NUMBER,PersonalBestScore NUMBER,TotalScore NUMBER,GameCount NUMBER,PRIMARY KEY(LID,GID));";

    private static final String readFromLogin = "SELECT LID,Gmail,Name,Password,CITY FROM LOGIN;";
    private static final String readFromProgress = "SELECT LID,GID,AverageScore,PersonalBestScore,TotalScore,GameCount FROM PROGRESS;";
    private static final String readFromGame = "SELECT GID,GameName,HighScore FROM GAME;";

    private static final String readSpecificFromLogin = "SELECT LID,Gmail,Name,Password,CITY FROM LOGIN WHERE LID = ?;";
    private static final String readSpecificFromProgress = "SELECT LID,GID,AverageScore,PersonalBestScore,TotalScore,GameCount FROM PROGRESS WHERE LID = ? AND GID = ?;";
    private static final String readSpecificFromGame = "SELECT GID,GameName,HighScore FROM GAME WHERE GID = ?;";

    private static final String GameJoinWithProgress = "SELECT GID,GameName,HighScore,LID,AverageScore,PersonalBestScore,TotalScore,GameCount FROM GAME,PROGRESS WHERE GAME.GID = PROGRESS.GID AND LID = ? AND GID = ?;";
    private static final String LoginJoinWithProgress = "SELECT LID,Gmail,Name,GID,AverageScore,PersonalBestScore,TotalScore,GameCount FROM LOGIN,PROGRESS WHERE LOGIN.LID = PROGRESS.LID AND LID = ? AND GID = ?;";
    private static final String LoginJoinWithGame = "SELECT LID,Gmail,GID,GameName,HighScore FROM LOGIN,GAME WHERE LID = ? AND GID = ?;";
    private static final String LoginJoinGameJoinProgress = "SELECT Name,CITY,GameName,PersonalBestScore,AverageScore,HighScore,TotalScore,GameCount FROM LOGIN,GAME,PROGRESS WHERE LOGIN.LID = PROGRESS.LID AND GAME.GID = PROGRESS.GID AND LOGIN.LID = ? AND GAME.GID = ?;";

    private static final String RowOfLoginTable = "SELECT * FROM LOGIN;";
    private static final String RowOfGameTable ="SELECT * FROM GAME;";
    private static final String RowOfProgressTable = "SELECT * FROM PROGRESS;";


    private static final String LOGIN = "DROP TABLE IF EXISTS LOGIN;";
    private static final String GAME = "DROP TABLE IF EXISTS GAME;";
    private static final String PROGRESS = "DROP TABLE IF EXISTS PROGRESS;";



    public DatabaseHelperActivity(Context context){
        super(context,DatabaseName,null,DatabaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CreateTableLogin);
            db.execSQL(CreateTableGame);
            db.execSQL(CreateTableProgress);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try{

            Toast.makeText(context,"onUpgrade is called",Toast.LENGTH_LONG).show();
            db.execSQL(LOGIN);
            db.execSQL(GAME);
            db.execSQL(PROGRESS);
            onCreate(db);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
    }

    // LOGIN Table Query------------------------------------------------------------------------------------------------------------------
    //*********************************************************************************************************************************
    public long insertIntoLogin(String LID,String gmail, String name,String password,String city)
    {
        long rowId = 0;
        insertIntoProgress(LID,"G001",0.0,0,0,0);
        insertIntoProgress(LID,"G002",0.0,0,0,0);
        insertIntoProgress(LID,"G003",0.0,0,0,0);
        insertIntoProgress(LID,"G004",0.0,0,0,0);
        insertIntoProgress(LID,"G005",0.0,0,0,0);
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put("LID", LID);
            contentValues.put("Gmail", gmail);
            contentValues.put("Name", name);
            contentValues.put("Password", password);
            contentValues.put("City", city);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            rowId = sqLiteDatabase.insert("LOGIN", null, contentValues);
            Log.d("logValue", rowId+" "+LID+" "+gmail+" "+name+" "+password+" "+city);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return  rowId;
    }

    public Cursor countRow()
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery("SELECT * FROM LOGIN",null);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    void DeleteRow()
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery("DELETE FROM LOGIN WHERE LID = 'L2'",null);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
    }

    // GAME Table Query ---------------------------------------------------------------------------------------------------------------------
    //*********************************************************************************************************************************

    public long insertIntoGame(String GID,String GameName,Integer HighScore)
    {
        long rowId = 0;
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put("GID", GID);
            contentValues.put("GameName", GameName);
            contentValues.put("HighScore", HighScore);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            rowId = sqLiteDatabase.insert("GAME", null, contentValues);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return  rowId;
    }

    public long updateGameTable(String GID,String GameName,Integer HighScore)
    {
        long rowId = 0;
        try {

            Log.d("man", "updateGameTable: "+GID+" "+GameName+" "+HighScore);
            ContentValues contentValues = new ContentValues();
            contentValues.put("GID", GID);
            contentValues.put("GameName", GameName);
            contentValues.put("HighScore", HighScore);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.update("GAME",contentValues, "GID = ?",new String[] {GID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return  rowId;
    }




    // PROGRESS Table Query ---------------------------------------------------------------------------------------------------------------------
    //*********************************************************************************************************************************

    public long insertIntoProgress(String LID, String GID, Double AverageScore, Integer PersonalBestScore, Integer TotalScore, Integer GameCount)
    {
        long rowId = 0;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("LID", LID);
            contentValues.put("GID", GID);
            contentValues.put("AverageScore",AverageScore);
            contentValues.put("PersonalBestScore", PersonalBestScore);
            contentValues.put("TotalScore", TotalScore);
            contentValues.put("GameCount", GameCount);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            rowId = sqLiteDatabase.insert("PROGRESS", null, contentValues);
            Log.d("man", rowId+" "+LID+" "+GID+" "+AverageScore+" "+PersonalBestScore+" "+TotalScore);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return  rowId;
    }


    public void updateProgressTable(String LID, String GID,Double AverageScore,Integer PersonalBestScore,Integer TotalScore,Integer GameCount)
    {
        try {
            Log.d("man", "updateProgressTable: "+PersonalBestScore);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("AverageScore",AverageScore);
            contentValues.put("PersonalBestScore", PersonalBestScore);
            contentValues.put("TotalScore", TotalScore);
            contentValues.put("GameCount", GameCount);
            sqLiteDatabase.update("PROGRESS",contentValues, "LID = ? AND GID = ?",new String[] {LID,GID});
            Log.d("man", LID+" "+GID+" "+AverageScore+" "+PersonalBestScore+" "+TotalScore);

        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
    }

    public Cursor readDataFromProgress()
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery("SELECT LID,GID,AverageScore,PersonalBestScore,TotalScore,GameCount FROM PROGRESS",null);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    //***********************************************************************************************************************
    //****************************Multi table Query**************************************************************************
    public Cursor getScoreBoardData(String lid,String gid)
    {
        Cursor result = null;
        try
        {
            Log.d("man",lid+" "+gid);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery("SELECT GameName,HighScore,AverageScore,PersonalBestScore,TotalScore,GameCount " +
                    "FROM GAME,PROGRESS WHERE GAME.GID = PROGRESS.GID AND PROGRESS.LID = ? AND PROGRESS.GID = ?;"
                    ,new String[] {String.valueOf(lid),String.valueOf(gid)});

        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    Cursor maxScoreINspecificGame(String LID,String GID)
    {
        Cursor result = null;
        try
        {
            Log.d("man",LID+" "+GID);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery("SELECT MAX(PersonalBestScore) FROM PROGRESS WHERE PROGRESS.LID = ? AND PROGRESS.GID = ?;"
                    ,new String[] {String.valueOf(LID),String.valueOf(GID)});

        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    //**************************READ TABLE DATA************************************************************************************************
    public Cursor readFromLogin()
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(readFromLogin,null);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }
    public Cursor readSpecificFromLogin(String LID)
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(readFromLogin,new String[] {LID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }


    public Cursor readFromGame()
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(readFromGame,null);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public Cursor readSpecificFromGame(String GID)
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(readFromGame,new String[] {GID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public Cursor readFromProgress()
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(readFromProgress,null);
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public Cursor readSpecificFromProgress(String LID,String GID)
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(readFromProgress,new String[] {LID,GID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public Cursor readFromLoginJoinProgress(String LID)
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(LoginJoinWithProgress,new String[] {LID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public Cursor readFromLoginJoinGame(String LID,String GID)
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(LoginJoinWithGame,new String[] {LID,GID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public Cursor readFromGameJoinProgress(String LID,String GID)
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(GameJoinWithProgress,new String[] {LID,GID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

    public Cursor readFromLoginJoinGameJoinProgress(String LID,String GID)
    {
        Cursor result = null;
        try
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            result = sqLiteDatabase.rawQuery(LoginJoinGameJoinProgress,new String[] {LID,GID});
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Exception: "+e,Toast.LENGTH_LONG).show();
        }
        return result;
    }

}