package com.example.duskagk.memotest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {
    private Context context;

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE TEST_TABLE ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" NAME TEXT, ");
        sb.append(" content TEXT) ");

        db.execSQL(sb.toString());

        Toast.makeText(context,"Table생성 완료",1).show();


    }
    public void testDB(){
        SQLiteDatabase db=getReadableDatabase();
    }


    public void addMemo(Memo memo) {

        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb=new StringBuffer();
        sb.append("INSERT INTO TEST_TABLE ( ");
        sb.append("NAME, content ) ");
        sb.append("VALUES ( ?, ? ) ");

        db.execSQL(sb.toString(),
                new Object[]{
                        memo.getMname(),
                        memo.getCon()});

        Toast.makeText(context, "Insert 완료", Toast.LENGTH_SHORT).show();
    }



    public List getMemo(){
        StringBuffer sb= new StringBuffer();
        sb.append("SELECT NAME, content FROM TEST_TABLE " );

        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor=db.rawQuery(sb.toString(),null);

        List conte=new ArrayList();
        Memo memo=null;

        while(cursor.moveToNext()){
            memo=new Memo();
            memo.setMname(cursor.getString(0));
            memo.setMcon(cursor.getString(1));
            conte.add(memo);
        }
        return conte;
    }

}

