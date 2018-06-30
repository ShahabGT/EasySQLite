package ir.shahabazimi.easysqlite;

import android.app.Activity;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;


public class EasyDatabase {

    private Activity activity;
    private String DatabaseName;
    private int DatabaseVersion;
    private String TableName;
    private ArrayList<ColumnModel> Column = new ArrayList<ColumnModel>();
    private DatabaseOperations db;
    private final String TAG="EasyDatabase";


    public EasyDatabase(@NonNull Activity activity,@NonNull String DatabaseName,@NonNull int DatabaseVersion){
        this.activity=activity;
        this.DatabaseName=DatabaseName;
        this.DatabaseVersion=DatabaseVersion;
    }
    public EasyDatabase createTable(@NonNull String TableName){
        this.TableName=TableName;
        return this;
    }

    public EasyDatabase addColumn(@NonNull String ColumnName,@NonNull String ColumnType, String ColumnConstraint){
        ColumnModel columnModel = new ColumnModel();

        columnModel.setName(ColumnName);
        columnModel.setType(ColumnType);
        columnModel.setConstraint(ColumnConstraint);


        this.Column.add(columnModel);
        return this;
    }
    public EasyDatabase addColumn(@NonNull String ColumnName,@NonNull String ColumnType,@NonNull String ColumnConstraint,@NonNull String ColumnConstraint2){
        ColumnModel columnModel = new ColumnModel();

        columnModel.setName(ColumnName);
        columnModel.setType(ColumnType);
        columnModel.setConstraint(ColumnConstraint);
        columnModel.setConstraint2(ColumnConstraint2);


        this.Column.add(columnModel);
        return this;
    }




    public void Build(){
        if(Column.size()<1){
            Log.d(TAG,"No Column");
        }else {
            String columns = "";
            String Constraint,Constraint2;

            for (int i = 0; i < Column.size(); i++) {
                ColumnModel columnModel = Column.get(i);

                if(columnModel.getConstraint()==null){
                    Constraint="";
                }else {
                    Constraint= columnModel.getConstraint();
                }
                if(columnModel.getConstraint2()==null){
                    Constraint2="";
                }else {
                    Constraint2= columnModel.getConstraint2();
                }

                columns += columnModel.getName() + " " + columnModel.getType() + " " + Constraint + " "+ Constraint2 + ", ";
            }

            columns = columns.substring(0, columns.length() - 2)+" ";
            String SQL = "CREATE TABLE IF NOT EXISTS " + TableName + " ( " + columns + " );";
            db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion, SQL,TableName);
            db.getWritableDatabase();
            db.excutesql(SQL);
            db.close();
            Column.clear();
        }
    }
    public void ExecuteSQL(@NonNull String SQL){
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        db.excutesql(SQL);
        db.close();
    }

    public Cursor ExecuteRawSQL(@NonNull String SQL){
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        Cursor cursor =db.excuterawsql(SQL);
        db.close();
        return  cursor;
    }
    public void DeleteFromTable(@NonNull String TableName,@NonNull HashMap<String,String> WhereArgs){
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        db.delete(TableName,WhereArgs);
        db.close();
    }
    public void DeleteFromTable(@NonNull String TableName,@NonNull String WhereClause,@NonNull String WhereArg){
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        db.delete(TableName,WhereClause,WhereArg);
        db.close();
    }
    public void ClearTable(@NonNull String TableName){
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        db.cleartbl(TableName);
        db.close();
    }

    public Cursor GetAllDataFromTable(@NonNull String TableName){
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        Cursor cursor= db.getdata(TableName);
        db.close();
        return cursor;
    }
    public Cursor GetSpecificColumnsFromTable(@NonNull String TableName,@NonNull String[] Columns){
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        Cursor cursor= db.getspecidata(TableName,Columns);
        db.close();
        return cursor;
    }

    public boolean InsertIntoTable(@NonNull String TableName,@NonNull  HashMap<String,String> Values) {
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        if (Values.isEmpty()) {
            db.close();
            return false;
        } else {
            if (db.insert(TableName, Values)) {
                db.close();
                return true;

            } else {
                db.close();
                return false;
            }
        }
    }
    public boolean UpdateTable(@NonNull String TableName,@NonNull  HashMap<String,String> Values,@NonNull  HashMap<String,String> WhereArgs) {
        db = new DatabaseOperations(activity, DatabaseName, DatabaseVersion);
        if (Values.isEmpty()||WhereArgs.isEmpty()) {
            db.close();
            return false;
        } else {
            if (db.update(TableName, Values,WhereArgs)) {
                db.close();
                return true;

            } else {
                db.close();
                return false;
            }
        }
    }

}
