package com.alse.ambushe;

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
				}
				catch (Exception e) {
				}
			}
		});
        updateTable();
    }
    public void updateTable(){
    	 SQLiteDatabase db=openOrCreateDatabase("test", MODE_PRIVATE, null);
         Cursor c=db.rawQuery("select username from test" , null);
         Cursor last=c;
         last.moveToLast();
         c.moveToFirst();
         TextView tv=(TextView) findViewById(R.id.textView2);
         tv.setText("");
         do{
         	Log.d("abc", c.getString(c.getColumnIndex("username")));
         	tv.setText(tv.getText().toString()+"\n"+c.getString(c.getColumnIndex("username")).toString());
         	c.moveToNext();
         }while(!c.isLast());
         db.close();
    }
}