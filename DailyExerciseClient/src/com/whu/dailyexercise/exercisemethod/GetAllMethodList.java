package com.whu.dailyexercise.exercisemethod;

import java.util.ArrayList;
import java.util.List;

import com.whu.dailyexercise.util.CommonField;

import android.content.res.Resources;

public class GetAllMethodList {
	private List<String> list_ExerciseKindName;
	private List<Object> list_ExerciseKindDrawable;
	private List<ArrayList<String>> list_ExerciseListName;
	private List<ArrayList<Object>> list_ExerciseListDrawable;
	public GetAllMethodList(Resources res,String packgename){
		
		//从数据库中获得类别试题对象
		List<MethodEntity> KindEntityList = MethodDao.getAllMethodInfo(0, CommonField.sqlitedatabasa);
		List<ArrayList<MethodEntity>> StructEntityList = MethodDao.getMethodCollection(CommonField.sqlitedatabasa);
		CommonField.kindEntityList = MethodDao.getAllMethodInfo(0, CommonField.sqlitedatabasa);
		CommonField.structEntityList = MethodDao.getMethodCollection(CommonField.sqlitedatabasa);
		//取出类别名和类别图片
		list_ExerciseKindName = new ArrayList<String>();
		list_ExerciseKindDrawable = new ArrayList<Object>();
		for(int i =0;i<KindEntityList.size();i++)
		{
			list_ExerciseKindName.add(KindEntityList.get(i).getMethodname());
			Object ob=res.getIdentifier(KindEntityList.get(i).getPictureurl(),"drawable",packgename);
			list_ExerciseKindDrawable.add(ob);
		}
		//取出每个项目的名称与图片
		list_ExerciseListName = new ArrayList<ArrayList<String>>();
		list_ExerciseListDrawable = new ArrayList<ArrayList<Object>>();
		for(int i = 0;i<StructEntityList.size();i++)
		{
			List<String> list_name = new ArrayList<String>();
			List<Object> list_obj = new ArrayList<Object>();
			for(int j = 0;j<StructEntityList.get(i).size();j++)
			{
				list_name.add(StructEntityList.get(i).get(j).getMethodname());
				Object ob=res.getIdentifier(StructEntityList.get(i).get(j).getPictureurl(),"drawable",packgename);
				list_obj.add(ob);
			}
			list_ExerciseListName.add((ArrayList<String>) list_name);
			list_ExerciseListDrawable.add((ArrayList<Object>) list_obj);
		}
		//ArrList转数组
		//asExerciseKindName = new String[list_ExerciseKindName.size()];
		//asExerciseKindName = list_ExerciseKindName.toArray(asExerciseKindName);
		//aiExerciseKindDrawable = new Object[list_ExerciseKindDrawable.size()];
		//aiExerciseKindDrawable = list_ExerciseKindDrawable.toArray(aiExerciseKindDrawable);
		//asExerciseListName =list_ExerciseListName.toArray(asExerciseListName);
		//aiExerciseListDrawable = list_ExerciseListDrawable.toArray(aiExerciseListDrawable); 
	};
    public List<Object> getExerciseKindDrawable()
    {
    	return list_ExerciseKindDrawable;
    };
    public List<String> getExerciseKindName()
    {
    	return list_ExerciseKindName;
    };
    public List<ArrayList<Object>> getExerciseListDrawable()
    {
    	return list_ExerciseListDrawable;
    };
    public List<ArrayList<String>> getExerciseListName()
    {
    	return list_ExerciseListName;
    };
}
