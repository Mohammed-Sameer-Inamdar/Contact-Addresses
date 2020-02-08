package com.contactinformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDataBase {
    MyDBHandler myhandler;
    public MyDataBase(Context context)
    {
        myhandler = new MyDBHandler(context);
    }

    public long insertData(String name, String contact, String address){
            SQLiteDatabase dbb = myhandler.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MyDBHandler.NAME, name);
            values.put(MyDBHandler.CONTACT, contact);
            values.put(MyDBHandler.ADDRESS, address);
        if(checkData(name)==false){
            long id = dbb.insert(MyDBHandler.TABLE_NAME, null, values);
            return id;
        }else {
            return 0;
        }


    }

    public ArrayList<String> getData()
    {
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase db = myhandler.getWritableDatabase();
        String[] columns = {MyDBHandler.ID,MyDBHandler.NAME,MyDBHandler.CONTACT,MyDBHandler.ADDRESS};
        Cursor cursor = db.query(MyDBHandler.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MyDBHandler.NAME));
            String contact = cursor.getString(cursor.getColumnIndex(MyDBHandler.CONTACT));
            String address = cursor.getString(cursor.getColumnIndex(MyDBHandler.ADDRESS));

            result.add(name +"  =:=  "+contact+"\n"+address);
        }
        return result;
    }


    public int delete(String uname){
        SQLiteDatabase db = myhandler.getWritableDatabase();
        String[] whereArgs = {uname};

        int count = db.delete(MyDBHandler.TABLE_NAME,MyDBHandler.NAME+" = ?",whereArgs);
        return count;
    }

    public int addAddress(String searchname, String upaddress){
        SQLiteDatabase datab = myhandler.getWritableDatabase();
        ContentValues contents = new ContentValues();
        contents.put(MyDBHandler.ADDRESS,upaddress);
        String[] whereArgs= {searchname};
        int cc = datab.update(MyDBHandler.TABLE_NAME,contents,MyDBHandler.NAME+" = ?",whereArgs);
        return cc;

    }

    public boolean checkData(String namesearch){
        boolean flag = false;
        SQLiteDatabase dataB = myhandler.getWritableDatabase();
        String Query = "SELECT * FROM " + MyDBHandler.TABLE_NAME + " WHERE "+MyDBHandler.NAME+ " = ?";
        Cursor cursorsearch = dataB.rawQuery(Query,new String[] {namesearch});
        if(cursorsearch.getCount()<=0){
            cursorsearch.close();
        }else {
            flag = true;
            cursorsearch.close();
        }
        return flag;
    }

    static class MyDBHandler extends SQLiteOpenHelper {
        //information of database
        private static final int DATABASE_VERSION =1;
        private static final String DATABASE_NAME = "InformationDB";
        public static final String TABLE_NAME = "information";
        public static final String ID = "_id";
        public static final String NAME = "PersonName";
        public static final String CONTACT = "PersonContact";
        public static final String ADDRESS ="PersonAddress";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(30) ,"+ CONTACT+" VARCHAR(10), "+ ADDRESS+" VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        //initialization the database
        public MyDBHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context=context;
        }

        public void onCreate (SQLiteDatabase db){
            try{
                db.execSQL(CREATE_TABLE);
            }catch(Exception e){
                Message.message(context, ""+e);
            }

        }
        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}

