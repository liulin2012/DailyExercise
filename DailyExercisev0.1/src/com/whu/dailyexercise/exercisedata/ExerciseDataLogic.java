package com.whu.dailyexercise.exercisedata;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.webkit.WebView;

public class ExerciseDataLogic {

		public static String getRemoteData(){
			 try {  
		            JSONObject object1 = new JSONObject();  
		            object1.put("name", "����");  
		            object1.put("color", "#1f7e92");  
		            Random random = new Random();
		            //js�е���������Ҫʹ��JSONArray����
		            JSONArray jadata= new JSONArray();  
		            for(int i=0;i<12;i++){
		            	jadata.put(10);
		            }
		            object1.put("value", jadata);    
		            JSONArray jsonArray = new JSONArray();  
		            jsonArray.put(object1);  
		            return jsonArray.toString();  
		        } catch (JSONException e) {  
		            e.printStackTrace();  
		        }  
		        return null;  
		}
		
		public static void updateData(WebView wv){
			
			wv.loadUrl("javascript:setContentInfo('"+getRemoteData()+"')");  
		}
}
