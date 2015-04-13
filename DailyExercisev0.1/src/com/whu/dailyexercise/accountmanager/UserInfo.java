package com.whu.dailyexercise.accountmanager;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserInfo {
	public static List<UserEntity> getUserInfo(String userid, SQLiteDatabase sqlitedatabase)
	{
		List<UserEntity> userlist=new ArrayList<UserEntity>();
		String sql="select * from user where userid='"+userid+"'";
		Cursor cr=sqlitedatabase.rawQuery(sql, null);
		while(cr.moveToNext())
		{
			UserEntity user=new UserEntity();
			user.setUserid(cr.getString(cr.getColumnIndex("userid")));
			user.setPassword(cr.getString(cr.getColumnIndex("password")));
			user.setUsername(cr.getString(cr.getColumnIndex("username")));
			user.setHeight(cr.getFloat(cr.getColumnIndex("height")));
			user.setWeight(cr.getFloat(cr.getColumnIndex("weight")));
			user.setSex(cr.getString(cr.getColumnIndex("sex")));
			user.setAge(cr.getInt(cr.getColumnIndex("age")));
			userlist.add(user);
		}
		return userlist;
	}
	
	public static void updateUserInfo(String userid,String username, String sex, String password, float height,float weight,int age, SQLiteDatabase sqlitedatabase)
	{
		String sql1="select * from user where userid='"+userid+"'";
		String sql2="";
		Cursor cr=sqlitedatabase.rawQuery(sql1, null);
		if(cr.moveToNext())
		{
			sql2="update user set username='"+username+"', sex='"+sex+"', password='"+password+"', height="+height+", weight="+weight+",age="+age+" where userid='"+userid+"'";
		}
		else
		{
			sql2="insert into user(userid,password,sex,height ,weight,username,age) values('"+userid+"','"+password+"','"+sex+"',"+height+","+weight+",'"+username+"',"+age+")";
		}
		try{
			sqlitedatabase.execSQL(sql2);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
