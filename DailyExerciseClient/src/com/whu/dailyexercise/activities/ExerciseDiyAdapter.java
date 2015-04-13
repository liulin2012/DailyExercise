package com.whu.dailyexercise.activities;
  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whu.dailyexercise.R;
import com.whu.dailyexercise.exercisemethod.GetAllMethodList;

import android.annotation.SuppressLint;
import android.content.Context;    
import android.view.LayoutInflater;    
import android.view.View;    
import android.view.ViewGroup;     
import android.widget.BaseExpandableListAdapter;
   
import android.widget.CheckBox;
import android.widget.ImageView;    
import android.widget.TextView;    
    
@SuppressLint("UseSparseArrays")
public class ExerciseDiyAdapter extends BaseExpandableListAdapter {  
	public static Map<Integer,Map<Integer, Boolean>> ChildIsSelected;  
 
	private List<String> list_ExerciseKindName = new ArrayList<String>();
	private List<Object> list_ExerciseKindDrawable = new ArrayList<Object>();
	private List<ArrayList<String>> list_ExerciseListName = new ArrayList<ArrayList<String>>();
	private List<ArrayList<Object>> list_ExerciseListDrawable= new ArrayList<ArrayList<Object>>();
	
    //构造方法（重写方法中，有返回值为view的方法）
    private Context context = null;
    @SuppressLint("UseSparseArrays")
	public ExerciseDiyAdapter(Context context,GetAllMethodList methodlist) {
    	this.context = context;
    	//logos = new int[] {image,R.drawable.exercise_kind_chest,R.drawable.exercise_kind_abdominals,R.drawable.exercise_kind_biceps,R.drawable.exercise_kind_quadriceps,R.drawable.exercise_kind_back,R.drawable.exercise_kind_run};
    	list_ExerciseKindDrawable = methodlist.getExerciseKindDrawable();
    	list_ExerciseKindName = methodlist.getExerciseKindName();
    	list_ExerciseListDrawable = methodlist.getExerciseListDrawable();
    	list_ExerciseListName = methodlist.getExerciseListName();
    	
    	//初始化子项check判定map
    	ChildIsSelected = new HashMap<Integer, Map<Integer,Boolean>>();
        for (int i = 0; i < list_ExerciseKindDrawable.size(); i++) {  
        	HashMap<Integer, Boolean> map1 = new HashMap<Integer, Boolean>();
            for(int j = 0; j < list_ExerciseListDrawable.get(i).size(); j++)  
            {
            	map1.put(j, false);
            }
            ChildIsSelected.put(i, map1);
        } 
    }
    
    //重写ExpandableListAdapter中的各个方法
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return list_ExerciseKindDrawable.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return list_ExerciseKindName.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return list_ExerciseListDrawable.get(groupPosition).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return list_ExerciseListName.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
    	View view=LayoutInflater.from(this.context).inflate(R.layout.listview_item_base_exercise, null);
    	ImageView logo = (ImageView)view.findViewById(R.id.icon);
        logo.setImageResource((Integer) list_ExerciseKindDrawable.get(groupPosition));
        TextView textView = (TextView)view.findViewById(R.id.tvExerChooseListItem);
        textView.setText(list_ExerciseKindName.get(groupPosition).toString());
        return view;
    }

    @Override //设置子选项样式
    public View getChildView(int groupPosition, int childPosition,
    boolean isLastChild, View convertView, ViewGroup parent) {
    // TODO Auto-generated method stub
    	ChildViewHolder holder = new ChildViewHolder();
	    convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_item_add_plan_exercise, null);    
	    holder.img = (ImageView) convertView.findViewById(R.id.listview_item_add_plan_exercise_img);    
	    holder.title = (TextView) convertView.findViewById(R.id.listview_item_add_plan_exercise_txtName);    
	    holder.cBox = (CheckBox) convertView.findViewById(R.id.listview_item_add_plan_exercise_select_plan);    
	    convertView.setTag(holder);    
    	holder.img.setImageResource((Integer) list_ExerciseListDrawable.get(groupPosition).get(childPosition));
    	holder.title.setText(getChild(groupPosition, childPosition).toString());
    	holder.cBox.setChecked((boolean)ChildIsSelected.get(groupPosition).get(childPosition));
    	return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition,
            int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

   
    
    public final class ChildViewHolder {    
        public ImageView img;    
        public TextView title;    
        public CheckBox cBox;    
    }   
};    