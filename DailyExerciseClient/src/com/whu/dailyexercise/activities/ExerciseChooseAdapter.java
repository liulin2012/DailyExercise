package com.whu.dailyexercise.activities;
  
import java.util.HashMap;    
import java.util.List;    
import java.util.Map;    
    
import com.whu.dailyexercise.R;
import android.annotation.SuppressLint;
import android.content.Context;    
import android.view.LayoutInflater;    
import android.view.View;    
import android.view.ViewGroup;    
import android.widget.BaseAdapter;    
import android.widget.CheckBox;    
import android.widget.ImageView;    
import android.widget.TextView;    
    
public class ExerciseChooseAdapter extends BaseAdapter {    
    private LayoutInflater mInflater;    
    private List<Map<String, Object>> mData;    
    public static Map<Integer, Boolean> isSelected;    
    
    public ExerciseChooseAdapter(Context context ,List<Map<String, Object>> data) {    
        mInflater = LayoutInflater.from(context);    
        init(data);    
    }    
    
    //初始化    
    @SuppressLint("UseSparseArrays")
	private void init(List<Map<String, Object>> data) {    
    	mData = data;
        //这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。    
        isSelected = new HashMap<Integer, Boolean>();    
        for (int i = 0; i < mData.size(); i++) {    
            isSelected.put(i, true);    
        }    
    }    
    
    @Override    
    public int getCount() {    
        return mData.size();    
    }    
    
    @Override    
    public Object getItem(int position) {    
        return null;    
    }    
    
    @Override    
    public long getItemId(int position) {    
        return 0;    
    }    
    
    @Override    
    public View getView(int position, View convertView, ViewGroup parent) {    
        ViewHolder holder = null;    
        //convertView为null的时候初始化convertView。    
        if (convertView == null) {    
            holder = new ViewHolder();    
            convertView = mInflater.inflate(R.layout.listview_item_add_plan_exercise, null);    
            holder.img = (ImageView) convertView.findViewById(R.id.listview_item_add_plan_exercise_icon);    
            holder.title = (TextView) convertView.findViewById(R.id.listview_item_add_plan_exercise_txtName);    
            holder.cBox = (CheckBox) convertView.findViewById(R.id.listview_item_add_plan_exercise_select_plan);    
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }    
        holder.img.setBackgroundResource((Integer) mData.get(position).get("img"));    
        holder.title.setText(mData.get(position).get("title").toString());    
        holder.cBox.setChecked(isSelected.get(position));    
        return convertView;    
    }    
    
    public final class ViewHolder {    
        public ImageView img;    
        public TextView title;    
        public CheckBox cBox;    
    }    
}    