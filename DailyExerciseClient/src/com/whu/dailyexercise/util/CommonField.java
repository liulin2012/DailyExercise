package com.whu.dailyexercise.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whu.dailyexercise.accountmanager.UserEntity;
import com.whu.dailyexercise.database.DBHelper;
import com.whu.dailyexercise.exercisemethod.MethodEntity;
import com.whu.dailyexercise.exerciseplan.PlanEntity;
import com.whu.dailyexercise.exerciseplan.StaticPlanEntity;

import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.Data;
import android.widget.TextView;

public class CommonField {
	public static String userId="root";
	public static boolean loginOK=false;
	public static TextView homepage1_username;
	public static TextView homepage1_please_login;
	public static TextView homepage2_username;
	public static TextView homepage2_please_login;
	public static DBHelper helper;
	public static SQLiteDatabase sqlitedatabasa;
	public static List<MethodEntity> kindEntityList;
	public static List<ArrayList<MethodEntity>> structEntityList;
	public static List<MethodEntity> choosedList;
	public static List<MethodEntity> finallist;
	public static List<PlanEntity> allPlanlist;
	public static List<PlanEntity> todayplanlist;
	public static List<MethodEntity> todayChoosedMethodlist;  //选中计划的训练方法列表
	public static String sToday;
	public static Date dToday;
	public static String ServerIp="192.168.1.100";
	public static List<ArrayList<StaticPlanEntity>> staticEntityList;
	public static List<StaticPlanEntity> choosedPlanList;
	public static List<UserEntity> userlist;
	
	//public static TextView 
}
