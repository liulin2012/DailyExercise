package com.whu.dailyexercise.activities;

import com.whu.dailyexercise.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditNameDlg extends Dialog
{
	//定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener{
            public void back(String name);
    }
    
    private OnCustomDialogListener customDialogListener;
    private String title;
    private EditText etName;
    private TextView dlgTitle;
	 public EditNameDlg(Context context,OnCustomDialogListener customDialogListener,String title) {
	     super(context);
	     this.customDialogListener = customDialogListener;
	     this.title =title;
	 }

	@Override
     protected void onCreate(Bundle savedInstanceState) { 
             super.onCreate(savedInstanceState);
             setTitle(null);
             setContentView(R.layout.edit_plan_name_dailog);
             etName = (EditText)findViewById(R.id.ed_new_plan_name);
             Button BtnOk = (Button)findViewById(R.id.btn_ok);
             Button BtnCancel = (Button)findViewById(R.id.btn_cancel);
             BtnOk.setOnClickListener(clickOkListener);
             BtnCancel.setOnClickListener(clickCancelListener);
             dlgTitle = (TextView)findViewById(R.id.dlg_title);
             dlgTitle.setText(this.title);
     }
	 
	 private View.OnClickListener clickOkListener = new View.OnClickListener() {
         
         @Override
         public void onClick(View v) {
                 customDialogListener.back(String.valueOf(etName.getText()));
                 EditNameDlg.this.dismiss();
         }
	 };
	 private View.OnClickListener clickCancelListener = new View.OnClickListener() {
         
         @Override
         public void onClick(View v) {
                 EditNameDlg.this.dismiss();
         }
	 };
	 
}