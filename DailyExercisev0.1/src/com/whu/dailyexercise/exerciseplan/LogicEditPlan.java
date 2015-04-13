package com.whu.dailyexercise.exerciseplan;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import com.whu.dailyexercise.exercisemethod.MethodEntity;
import com.whu.dailyexercise.util.CommonField;

public class LogicEditPlan {
	//添加计划
	@SuppressLint("SimpleDateFormat")
	static public int addPlan(List<MethodEntity> methodlist, Date startdate,int weeknum,int[] weekrepeat,String planname)
	{
		Date firstMonday =new Date();
		int[] aiMethodId = new int[methodlist.size()];
		for(int i = 0;i<methodlist.size();i++)
		{
			aiMethodId[i] = methodlist.get(i).getMethodid();
		} 
        Calendar c = Calendar.getInstance();
        c.setTime(startdate);
        int iflag = c.get(Calendar.DAY_OF_WEEK);
        //获得最初的周一
       
        switch(iflag){
	        case 1:
	            //Week += "天";
	        	firstMonday = new Date(startdate.getTime() - 6 * 24 * 60 * 60 * 1000);
	            break;
	        case 2:
	            //Week += "一";
	        case 3:
	            //Week += "二";
	        case 4:
	            //Week += "三";
	        case 5:
	            //Week += "四";
	        case 6:
	            //Week += "五";
	        case 7:
	            //Week += "六";
	        	firstMonday = new Date(startdate.getTime() - (iflag-2) * 24 * 60 * 60 * 1000);
	            break;
	        default:
	            break;          
        } 
        List<Date> list_date = new ArrayList<Date>(); 
        for(int i = 0;i < weeknum;i++)
        {
        	for(int j = 0;j < weekrepeat.length;j++)
        	{
        		if(weekrepeat[j]==1)
        		{
        			Date tempdate = new Date(firstMonday.getTime() + (i*7+j) * 24 * 60 * 60 * 1000);
        			list_date.add(tempdate);
        		}
        	}
        }
        Iterator<Date> iter=list_date.iterator(); 
        while(iter.hasNext())
        {
        	if(iter.next().before(startdate))
        	{
        		iter.remove();
        	}
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] date = new String[list_date.size()];
        for(int i = 0;i<list_date.size();i++)
        {
        	date[i] = simpleDateFormat.format(list_date.get(i));
        }
          
        // String dateOk = simpleDateFormat.format(newDate2);
		int iReturn =  EditPlan.addPlan(CommonField.userId, aiMethodId, "default", date, 0, 0, planname, weeknum, weekrepeat, CommonField.sqlitedatabasa);
		return iReturn;
	}
}
