/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whu.dailyexercise.exerciseshare;

import com.whu.dailyexercise.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 璇ョ被绠�崟鐨勫皝瑁呬簡鍒嗕韩涓紝鐢ㄤ簬鏄剧ず鏂囧瓧銆佸浘鐗囥�瑙嗛銆侀煶涔愮瓑鍐呭鐨勭粍鍚堟帶浠躲�
 * 
 * @author SINA
 * @since 2013-10-22
 */
public class WBShareItemView extends LinearLayout {
    
    /** UI 鍏冪礌 */
    private TextView    mTitleView;
    private ImageView   mThumbView;
    private TextView    mShareTitleView;
    private TextView    mShareDescView;
    private TextView    mShareUrlView;
    private RadioButton mCheckedBtn;
    
    /** RadioButton 鍦ㄧ偣鍑绘椂鐨勫洖璋冨嚱鏁�*/
    private OnCheckedChangeListener mOnCheckedChangeListener;
    
    /**
     * 褰�RadioButton 鐐瑰嚮鏃讹紝璇ユ帴鍙ｅ搴旂殑鐨勫洖璋冨嚱鏁拌璋冪敤銆�     */
    public interface OnCheckedChangeListener {
        public void onCheckedChanged(WBShareItemView view, boolean isChecked);
    }
    
    /**
     * 鍒涘缓涓�釜缁勫悎鎺т欢銆�     * 
     * @see View#View(Context)
     */
    public WBShareItemView(Context context) {
        this(context, null);
    }
    
    /**
     * 浠�XML 閰嶇疆鏂囦欢涓垱寤轰竴涓粍鍚堟帶浠躲�
     * 
     * @see View#View(Context, AttributeSet)
     */
    public WBShareItemView(Context context, AttributeSet attrs) {
        // Need API Level > 8
        //this(context, attrs, 0);
        super(context, attrs);
        initialize(context);
    }
    
    /**
     * 浠�XML 閰嶇疆鏂囦欢涓垱寤轰竴涓粍鍚堟帶浠躲�
     * 
     * @see View#View(Context, AttributeSet)
     */
    // Need API Level > 8
    /*
    public WBShareItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }
     */
    /**
     * 鐢ㄨ祫婧�ID 鏉ュ垵濮嬪寲鐣岄潰鍏冪礌銆�     */
    public void initWithRes(
            int titleResId, 
            int thumbResId, 
            int shareTitleResId, 
            int shareDescResId, 
            int shareUrlResId) {
        mTitleView.setText(titleResId);
        mThumbView.setImageResource(thumbResId);
        mShareTitleView.setText(shareTitleResId);
        mShareDescView.setText(shareDescResId);
        mShareUrlView.setText(shareUrlResId);
    }
    
    /**
     * 鍒濆鍖栫晫闈㈠厓绱犮�
     */
    public void initWithData(
            String title, 
            Drawable thumb, 
            String shareTitle, 
            String shareDesc, 
            String shareUrl) {
        mTitleView.setText(title);
        mThumbView.setImageDrawable(thumb);
        mShareTitleView.setText(shareTitle);
        mShareDescView.setText(shareDesc);
        mShareUrlView.setText(shareUrl);
    }
    
    /**
     * 璁剧疆 RadioButton 鍦ㄧ偣鍑绘椂鐨勫洖璋冨嚱鏁般�
     * 
     * @param listener 鍥炶皟鐩戝惉鍣�     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }
    
    /**
     * 鑾峰彇褰撳墠鎺т欢鐨�RadioButton 鏄惁琚�涓殑鐘舵�銆�     * 
     * @return 閫変腑锛岃繑鍥�true锛涘惁鍒欙紝杩斿洖 false
     */
    public boolean isChecked() {
        return mCheckedBtn.isChecked();
    }
    
    /**
     * 璁剧疆褰撳墠鎺т欢鐨�RadioButton 鐨勮閫変腑鐘舵�銆�     * 
     * @param isChecked 閫変腑鐘舵�
     */
    public void setIsChecked(boolean isChecked) {
        mCheckedBtn.setChecked(isChecked);
    }
    
    /**
     * 鑾峰彇褰撳墠鐣岄潰鐨�Title銆�     */
    public String getTitle() {
        return mTitleView.getText().toString();
    }
    
    /**
     * 鑾峰彇褰撳墠鐣岄潰鐨勭缉鐣ュ浘瀵瑰簲鐨�Drawable銆�     */
    public Drawable getThumbDrawable() {
        return mThumbView.getDrawable();
    }
    
    /**
     * 鑾峰彇褰撳墠鐣岄潰鐨勭缉鐣ュ浘瀵瑰簲鐨�Bitmap銆�     */
    public Bitmap getThumbBitmap() {
        return ((BitmapDrawable) mThumbView.getDrawable()).getBitmap();
    }
    
    /**
     * 鑾峰彇褰撳墠鐣岄潰鐨勫垎浜�Title銆�     */
    public String getShareTitle() {
        return mShareTitleView.getText().toString();
    }
    
    /**
     * 鑾峰彇褰撳墠鐣岄潰鐨勫垎浜弿杩般�
     */
    public String getShareDesc() {
        return mShareDescView.getText().toString();
    }
    
    /**
     * 鑾峰彇褰撳墠鐣岄潰鐨勫垎浜�URL銆�     */
    public String getShareUrl() {
        return mShareUrlView.getText().toString();
    }

    /**
     * 鍒濆鍖栫晫闈�
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.share_item_template, this);

        mTitleView      = (TextView)findViewById(R.id.item_title_view);
        mThumbView      = (ImageView)findViewById(R.id.item_thumb_image_btn);
        mShareTitleView = (TextView)findViewById(R.id.item_share_title_view);
        mShareDescView  = (TextView)findViewById(R.id.item_share_desc_view);
        mShareUrlView   = (TextView)findViewById(R.id.item_share_url_view);
        mCheckedBtn     = (RadioButton)findViewById(R.id.item_checked_btn);
        
        mCheckedBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.onCheckedChanged(WBShareItemView.this, isChecked);
                }
            }
        });
    }
}
