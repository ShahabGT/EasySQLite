package ir.shahabazimi.easysqliteexample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

import ir.shahabazimi.easysqlite.Constraints;
import ir.shahabazimi.easysqlite.DataTypes;
import ir.shahabazimi.easysqlite.EasyDatabase;

public class MainActivity extends AppCompatActivity {
    EasyDatabase easyDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        easyDatabase= new EasyDatabase(MainActivity.this,"Test2DB",1);

//        easyDatabase.createTable("Messages")
//                .addColumn("id",DataTypes.INT,"")
//                .addColumn("name",DataTypes.TEXT,"")
//                .addColumn("message",DataTypes.TEXT,"")
//                .Build();
//
//
//        HashMap<String,String> values = new HashMap<>();
//        values.put("id","1");
//        values.put("name","Ali");
//        values.put("message","using hashmap");
//        easyDatabase.InsertIntoTable("Messages",values);
//        HashMap<String,String> values1 = new HashMap<>();
//        values1.put("id","2");
//        values1.put("name","Ali");
//        values1.put("message","using hashmap1");
//        easyDatabase.InsertIntoTable("Messages",values1);
//        HashMap<String,String> values2 = new HashMap<>();
//        values2.put("id","3");
//        values2.put("name","Mohammad");
//        values2.put("message","using hashmap");
//        easyDatabase.InsertIntoTable("Messages",values2);
//        HashMap<String,String> values3 = new HashMap<>();
//        values3.put("id","5");
//        values3.put("name","gholi");
//        values3.put("message","using hashmap3");
//        easyDatabase.InsertIntoTable("Messages",values3);
//
//        easyDatabase.DeleteFromTable("Messages","id","1");

            easyDatabase.createTable("fvff").addColumn("first",DataTypes.TEXT, Constraints.NOT_NULL,Constraints.PRIMARY_KEY).addColumn("second",DataTypes.TEXT,Constraints.NOT_NULL)
                    .addColumn("third",DataTypes.TEXT,"")
                    .addColumn("fourth",DataTypes.TEXT,"",Constraints.NOT_NULL).Build();
        easyDatabase.GetAllDataFromTable("Messages");
        HashMap<String,String> valuesx = new HashMap<>();
        valuesx.put("name","akbar");
        valuesx.put("message","using hashmap");
        HashMap<String,String> where = new HashMap<>();
        where.put("id","1");
        easyDatabase.UpdateTable("Messages",valuesx,where);

        easyDatabase.DeleteFromTable("Messages","Sender","Shahab Azimi");

        easyDatabase.GetSpecificColumnsFromTable("Messages",new String[]{"Sender","Date"} );


    }
}
