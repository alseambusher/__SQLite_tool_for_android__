package com.alse.ambushe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class main extends Activity {
	private EditText et; 
	public String dbname,tablename;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et=(EditText) findViewById(R.id.editText1);
        
        //this is to set the edit text to previous query state
        SharedPreferences savedQuery=getSharedPreferences("MYPREFS", 0);
        et.setText(savedQuery.getString("query", "default"));
        SharedPreferences settings=PreferenceManager.getDefaultSharedPreferences(this);
        dbname=settings.getString("db", "test");
        tablename=settings.getString("table", "test");
        
        Button b=(Button) findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					SQLiteDatabase db=openOrCreateDatabase(dbname, MODE_PRIVATE, null);
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
    	try{
    	 SQLiteDatabase db=openOrCreateDatabase(dbname, MODE_PRIVATE, null);
         Cursor c=db.rawQuery("select * from "+tablename , null);
         Cursor last=c;
         last.moveToLast();
         c.moveToFirst();
         TextView tv=(TextView) findViewById(R.id.textView2);
         tv.setText("Table name: "+tablename+"\n");
         //this determies all the column names
         String column_names[] = new String[100];
         int count=0;
         Cursor ti=db.rawQuery("PRAGMA table_info("+tablename+")",null);
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
    	catch (Exception e) {
			Toast.makeText(main.this, e.toString(),Toast.LENGTH_LONG).show();
		}
    }
    @Override
    protected void onResume() {
    	super.onResume();
    	//set db name and table name if changed
        SharedPreferences settings=PreferenceManager.getDefaultSharedPreferences(this);
        dbname=settings.getString("db", "test");
        tablename=settings.getString("table", "test");
        updateTable();
    }
    @Override
    //When the application is about to stop
    protected void onStop() {
    	super.onStop();
    	//this will store the state of the query in Edit text
    	SharedPreferences savedQuery=getSharedPreferences("MYPREFS",0);
    	SharedPreferences.Editor editor= savedQuery.edit();
    	editor.putString("query", et.getText().toString());
    	editor.commit();
    }
    //Menu Options************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater=getMenuInflater();
    	inflater.inflate(R.menu.mainmenu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    		case R.id.item1:startActivity(new Intent(this,mainpref.class));
    		break;
    		case R.id.item2:
    			Dialog d=new Dialog(main.this);
    			d.setContentView(R.layout.about);
    			d.setTitle("About");
    			d.show();
    			break;
    	}
       	SharedPreferences settings=PreferenceManager.getDefaultSharedPreferences(this);
        dbname=settings.getString("db", "test");
        tablename=settings.getString("table", "test");
    	updateTable();
    	return true;
    }
  //Menu Options ENDS*********************************************************************
}