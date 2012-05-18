package com.alse.ambushe;

import java.lang.reflect.Array;

import android.R.string;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class main extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final EditText et=(EditText) findViewById(R.id.editText1);
        Button b=(Button) findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					SQLiteDatabase db=openOrCreateDatabase("test", MODE_PRIVATE, null);
					db.execSQL(et.getText().toString());
					db.close();
					updateTable();
					Toast.makeText(main.this, "Successfully updated", Toast.LENGTH_LONG).show();
				}
				catch (Exception e) {
					Toast.makeText(main.this, e.toString(), Toast.LENGTH_LONG).show();
				}
			}
		});
        updateTable();
    }
    public void updateTable(){
    	 SQLiteDatabase db=openOrCreateDatabase("test", MODE_PRIVATE, null);
         Cursor c=db.rawQuery("select * from test" , null);
         Cursor last=c;
         last.moveToLast();
         c.moveToFirst();
         TextView tv=(TextView) findViewById(R.id.textView2);
         tv.setText("");
         //this determies all the column names
         String column_names[] = new String[100];
         int count=0;
         Cursor ti=db.rawQuery("PRAGMA table_info(test)",null);
			if(ti.moveToFirst()){
				do{
					column_names[count]=ti.getString(1);
					count++;
					tv.append(" "+ti.getString(1));
				}while(ti.moveToNext());
			}
         do{
        	 tv.append("\n");
        	 for(int i=0;i<count;i++)
        		 tv.append(" "+c.getString(c.getColumnIndex(column_names[i])).toString());
         }while(c.moveToNext());
         db.close();
    }
}