package ir.shahabazimi.easysqlite;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class DatabaseOperations extends SQLiteOpenHelper {


    private Activity activity;
    private String dbname,sql,Tablename;
    private int dbversion;

    DatabaseOperations(Activity activity, String dbname,int dbversion,String sql,String Tablename){
        super(activity,dbname,null,dbversion);
        this.activity=activity;
        this.dbname=dbname;
        this.dbversion=dbversion;
        this.sql=sql;
        this.Tablename=Tablename;

     }
    DatabaseOperations(Activity activity, String dbname,int dbversion){
        super(activity,dbname,null,dbversion);
        this.activity=activity;
        this.dbname=dbname;
        this.dbversion=dbversion;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tablename);
        onCreate(db);

    }
    public Cursor getdata(String tblname){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * From "+tblname;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            return cursor;
        }
        cursor.close();
        return null;
    }
    public Cursor getspecidata(String tblname,String[] columns){
        String c="";
        for(int i =0 ; i<columns.length;i++){
            c+= columns[i]+",";
        }
        c = c.substring(0, c.length() - 1)+" ";
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select "+c+" From "+tblname;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            return cursor;
        }
        cursor.close();
        return null;
    }

    public void excutesql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public Cursor excuterawsql(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    public boolean insert(String tblname, HashMap<String,String> ColumnsAndValues){
        SQLiteDatabase db = getWritableDatabase();
        Set set = ColumnsAndValues.entrySet();
        Iterator iterator = set.iterator();
        ContentValues values = new ContentValues();

        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            values.put(mentry.getKey().toString(),mentry.getValue().toString());
        }

        if(db.insert(tblname,null,values)!=-1){
            return true;
        }
        return false;
    }

    public boolean update(String tblname,HashMap<String,String> ColumnsAndValues,HashMap<String,String> whereargs){
        SQLiteDatabase db = getWritableDatabase();
        Set set = ColumnsAndValues.entrySet();
        Iterator iterator = set.iterator();
        ContentValues values = new ContentValues();

        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            values.put(mentry.getKey().toString(),mentry.getValue().toString());
        }
        Set set2 = whereargs.entrySet();
        Iterator iterator2 = set2.iterator();
        String whereclause="";
        String[] wherearg=new String[whereargs.size()];
        int count=0;
        while(iterator2.hasNext()) {
            Map.Entry mentry2 = (Map.Entry)iterator2.next();
            whereclause+= mentry2.getKey().toString() +"=? AND ";
            wherearg[count]=mentry2.getValue().toString();
            count++;
        }
        whereclause = whereclause.substring(0, whereclause.length() - 5);
        if(db.update(tblname,values,whereclause,wherearg)!=-1){
            return true;
        }
        return false;

    }

    public void delete(String tblname, HashMap<String,String> whereargs){
        SQLiteDatabase db =getWritableDatabase();
        Set set = whereargs.entrySet();
        Iterator iterator = set.iterator();
        String whereclause="";
        String[] wherearg=new String[whereargs.size()];
        int count=0;
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            whereclause+= mentry.getKey().toString() +"=? AND ";
            wherearg[count]=mentry.getValue().toString();
            count++;
        }
        whereclause = whereclause.substring(0, whereclause.length() - 5);
        db.delete(tblname,whereclause,wherearg);

    }
    public void delete(String tblname, String k,String v){
        SQLiteDatabase db =getWritableDatabase();
        String whereclause = k+"='"+v+"'";
        db.delete(tblname,whereclause,null);

    }
    public void cleartbl(String tblname){
        SQLiteDatabase db =getWritableDatabase();
        db.delete(tblname,null,null);

    }

    public void close(){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null && db.isOpen()){
            db.close();
        }
    }
}
