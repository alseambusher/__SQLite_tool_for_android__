package com.alse.ambushe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class basicfunctions {
	public static Boolean dropColumn(SQLiteDatabase db,String TableName,String ColumnName){
		try{
			//db.execSQL("create temp(id default 'Null')");
			String query="create table temp(";
			Cursor c=db.rawQuery("PRAGMA table_info("+TableName+")",null);
			c.moveToFirst();
			do{
				if(c.getString(1)!=ColumnName)
					query+=c.getString(1)+",";
			}while(c.moveToNext());
			query=query.substring(0,query.length()-1);
			query+=");insert into temp select ";
			c.moveToFirst();
			do{
				if(c.getString(1)!=ColumnName)
					query+=c.getString(1)+",";
			}while(c.moveToNext());
			query=query.substring(0,query.length()-1);
			query+=" from "+TableName+";";
			//alter table zsvdfsva rename to zsvdfvxdfbfg
			//db.execSQL("");
			Log.d("dropcolumn", query);
			return true;
		}
		catch (Exception e) {
			Log.d("dropcolumn","error occured");
			return false;
		}
	}
	public static Boolean addColumn(SQLiteDatabase db,String TableName,String ColumnName,String Type,String Default,String Attribute,Boolean isPrimaryKey,Boolean isAutoIncreament){
		return false;
		
	}
	//TODO MODIFY COLUMN
}
