package com.whu.dailyexercise.activities;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.whu.dailyexercise.exerciseshare.WBShareItemView;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.utils.Utility;
import com.whu.dailyexercise.exerciseshare.Constants;
import com.whu.dailyexercise.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SinaShareActivity extends Activity implements OnClickListener, IWeiboHandler.Response {
    @SuppressWarnings("unused")
    private static final String TAG = "SinaShareActivity";

    /** 鐣岄潰鏍囬 */
    private TextView        mTitleView;
    /** 鍒嗕韩鍥剧墖 */
    private ImageView       mImageView;
    /** 鐢ㄤ簬鎺у埗鏄惁鍒嗕韩鏂囨湰鐨�CheckBox */
    private CheckBox        mTextCheckbox;
    /** 鐢ㄤ簬鎺у埗鏄惁鍒嗕韩鍥剧墖鐨�CheckBox */
    private CheckBox        mImageCheckbox;
    /** 鍒嗕韩缃戦〉鎺т欢 */
    private WBShareItemView mShareWebPageView;
    /** 鍒嗕韩闊充箰鎺т欢 */
    private WBShareItemView mShareMusicView;
    /** 鍒嗕韩瑙嗛鎺т欢 */
    private WBShareItemView mShareVideoView;
    /** 鍒嗕韩澹伴煶鎺т欢 */
    private WBShareItemView mShareVoiceView;
    /** 鍒嗕韩鎸夐挳 */
    private Button          mSharedBtn;
    
    /** 寰崥寰崥鍒嗕韩鎺ュ彛瀹炰緥 */
    private IWeiboShareAPI  mWeiboShareAPI = null;

    /**
     * @see {@link Activity#onCreate}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sina_share);
        initViews();

        // 鍒涘缓寰崥鍒嗕韩鎺ュ彛瀹炰緥
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
        
        // 娉ㄥ唽绗笁鏂瑰簲鐢ㄥ埌寰崥瀹㈡埛绔腑锛屾敞鍐屾垚鍔熷悗璇ュ簲鐢ㄥ皢鏄剧ず鍦ㄥ井鍗氱殑搴旂敤鍒楄〃涓�
        // 浣嗚闄勪欢鏍忛泦鎴愬垎浜潈闄愰渶瑕佸悎浣滅敵璇凤紝璇︽儏璇锋煡鐪�Demo 鎻愮ず
        // NOTE锛氳鍔″繀鎻愬墠娉ㄥ唽锛屽嵆鐣岄潰鍒濆鍖栫殑鏃跺�鎴栨槸搴旂敤绋嬪簭鍒濆鍖栨椂锛岃繘琛屾敞鍐�       
        mWeiboShareAPI.registerApp();
        
        // 濡傛灉鏈畨瑁呭井鍗氬鎴风锛岃缃笅杞藉井鍗氬搴旂殑鍥炶皟
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            mWeiboShareAPI.registerWeiboDownloadListener(new IWeiboDownloadListener() {
                @Override
                public void onCancel() {
                    Toast.makeText(SinaShareActivity.this, 
                            R.string.weibosdk_demo_cancel_download_weibo, 
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        
		// 褰�Activity 琚噸鏂板垵濮嬪寲鏃讹紙璇�Activity 澶勪簬鍚庡彴鏃讹紝鍙兘浼氱敱浜庡唴瀛樹笉瓒宠鏉�帀浜嗭級锛�        // 闇�璋冪敤 {@link IWeiboShareAPI#handleWeiboResponse} 鏉ユ帴鏀跺井鍗氬鎴风杩斿洖鐨勬暟鎹�
        // 鎵ц鎴愬姛锛岃繑鍥�true锛屽苟璋冪敤 {@link IWeiboHandler.Response#onResponse}锛�        // 澶辫触杩斿洖 false锛屼笉璋冪敤涓婅堪鍥炶皟
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
    }
    
    /**
     * @see {@link Activity#onNewIntent}
     */	
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        // 浠庡綋鍓嶅簲鐢ㄥ敜璧峰井鍗氬苟杩涜鍒嗕韩鍚庯紝杩斿洖鍒板綋鍓嶅簲鐢ㄦ椂锛岄渶瑕佸湪姝ゅ璋冪敤璇ュ嚱鏁�        // 鏉ユ帴鏀跺井鍗氬鎴风杩斿洖鐨勬暟鎹紱鎵ц鎴愬姛锛岃繑鍥�true锛屽苟璋冪敤
        // {@link IWeiboHandler.Response#onResponse}锛涘け璐ヨ繑鍥�false锛屼笉璋冪敤涓婅堪鍥炶皟
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    /**
     * 鎺ユ敹寰鎴风鍗氳姹傜殑鏁版嵁銆�     * 褰撳井鍗氬鎴风鍞よ捣褰撳墠搴旂敤骞惰繘琛屽垎浜椂锛岃鏂规硶琚皟鐢ㄣ�
     * 
     * @param baseRequest 寰崥璇锋眰鏁版嵁瀵硅薄
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
        case WBConstants.ErrorCode.ERR_OK:
            Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_CANCEL:
            Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_FAIL:
            Toast.makeText(this, 
                    getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg, 
                    Toast.LENGTH_LONG).show();
            break;
        }
    }

    /**
     * 鐢ㄦ埛鐐瑰嚮鍒嗕韩鎸夐挳锛屽敜璧峰井鍗氬鎴风杩涜鍒嗕韩銆�     */
    @Override
    public void onClick(View v) {
        if (R.id.share_to_btn == v.getId()) {
            try {
                if (mWeiboShareAPI.checkEnvironment(true)) {                    
                    sendMessage(mTextCheckbox.isChecked(), 
                            mImageCheckbox.isChecked(), 
                            mShareWebPageView.isChecked(),
                            mShareMusicView.isChecked(), 
                            mShareVideoView.isChecked(), 
                            mShareVoiceView.isChecked());
                }
            } catch (WeiboShareException e) {
                e.printStackTrace();
                Toast.makeText(SinaShareActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 鍒濆鍖栫晫闈�
     */
    private void initViews() {
        mTitleView = (TextView) findViewById(R.id.share_title);
        mTitleView.setText(R.string.weibosdk_demo_share_to_weibo_title);
        mImageView = (ImageView) findViewById(R.id.share_imageview);
        mTextCheckbox = (CheckBox) findViewById(R.id.share_text_checkbox);
        mImageCheckbox = (CheckBox) findViewById(R.id.shared_image_checkbox);

        mSharedBtn = (Button) findViewById(R.id.share_to_btn);
        mSharedBtn.setOnClickListener(this);
        
        mShareWebPageView = (WBShareItemView)findViewById(R.id.share_webpage_view);
        mShareMusicView = (WBShareItemView)findViewById(R.id.share_music_view);
        mShareVideoView = (WBShareItemView)findViewById(R.id.share_video_view);
        mShareVoiceView = (WBShareItemView)findViewById(R.id.share_voice_view);
        /**mShareWebPageView.setOnCheckedChangeListener(mCheckedChangeListener);
        mShareMusicView.setOnCheckedChangeListener(mCheckedChangeListener);
        mShareVideoView.setOnCheckedChangeListener(mCheckedChangeListener);
        mShareVoiceView.setOnCheckedChangeListener(mCheckedChangeListener);
        */
        
        mShareWebPageView.initWithRes(
                R.string.weibosdk_demo_share_webpage_title, 
                R.drawable.ic_sina_logo, 
                R.string.weibosdk_demo_share_webpage_title, 
                R.string.weibosdk_demo_share_webpage_desc, 
                R.string.weibosdk_demo_test_webpage_url);
        
        mShareMusicView.initWithRes(
                R.string.weibosdk_demo_share_music_title, 
                R.drawable.ic_share_music_thumb, 
                R.string.weibosdk_demo_share_music_title, 
                R.string.weibosdk_demo_share_music_desc, 
                R.string.weibosdk_demo_test_music_url);
        
        mShareVideoView.initWithRes(
                R.string.weibosdk_demo_share_video_title, 
                R.drawable.ic_share_video_thumb, 
                R.string.weibosdk_demo_share_video_title, 
                R.string.weibosdk_demo_share_video_desc, 
                R.string.weibosdk_demo_test_video_url);
        
        mShareVoiceView.initWithRes(
                R.string.weibosdk_demo_share_voice_title, 
                R.drawable.ic_share_voice_thumb, 
                R.string.weibosdk_demo_share_voice_title, 
                R.string.weibosdk_demo_share_voice_desc, 
                R.string.weibosdk_demo_test_voice_url);
    }

    /**
     * 鐩戝惉 RadioButton 鐨勭偣鍑讳簨浠躲�
     */
    /**private WBShareItemView.OnCheckedChangeListener mCheckedChangeListener = new WBShareItemView.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(WBShareItemView view, boolean isChecked) {
            mShareWebPageView.setIsChecked(false);
            mShareMusicView.setIsChecked(false);
            mShareVideoView.setIsChecked(false);
            mShareVoiceView.setIsChecked(false);
            
            view.setIsChecked(isChecked);
        }
    };
    */

    /**
     * 绗笁鏂瑰簲鐢ㄥ彂閫佽姹傛秷鎭埌寰崥锛屽敜璧峰井鍗氬垎浜晫闈�
     * @see {@link #sendMultiMessage} 鎴栬� {@link #sendSingleMessage}
     */
    private void sendMessage(boolean hasText, boolean hasImage, 
			boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {
        
        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
            int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
            if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
            } else {
                sendSingleMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo/*, hasVoice*/);
            }
        } else {
            Toast.makeText(this, R.string.weibosdk_demo_not_support_api_hint, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 绗笁鏂瑰簲鐢ㄥ彂閫佽姹傛秷鎭埌寰崥锛屽敜璧峰井鍗氬垎浜晫闈�
     * 娉ㄦ剰锛氬綋 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 鏃讹紝鏀寔鍚屾椂鍒嗕韩澶氭潯娑堟伅锛�     * 鍚屾椂鍙互鍒嗕韩鏂囨湰銆佸浘鐗囦互鍙婂叾瀹冨獟浣撹祫婧愶紙缃戦〉銆侀煶涔愩�瑙嗛銆佸０闊充腑鐨勪竴绉嶏級銆�     * 
     * @param hasText    鍒嗕韩鐨勫唴瀹规槸鍚︽湁鏂囨湰
     * @param hasImage   鍒嗕韩鐨勫唴瀹规槸鍚︽湁鍥剧墖
     * @param hasWebpage 鍒嗕韩鐨勫唴瀹规槸鍚︽湁缃戦〉
     * @param hasMusic   鍒嗕韩鐨勫唴瀹规槸鍚︽湁闊充箰
     * @param hasVideo   鍒嗕韩鐨勫唴瀹规槸鍚︽湁瑙嗛
     * @param hasVoice   鍒嗕韩鐨勫唴瀹规槸鍚︽湁澹伴煶
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo, boolean hasVoice) {
        
        // 1. 鍒濆鍖栧井鍗氱殑鍒嗕韩娑堟伅
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
        
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
        
        // 鐢ㄦ埛鍙互鍒嗕韩鍏跺畠濯掍綋璧勬簮锛堢綉椤点�闊充箰銆佽棰戙�澹伴煶涓殑涓�锛�        
        if (hasWebpage) {
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (hasMusic) {
            weiboMessage.mediaObject = getMusicObj();
        }
        if (hasVideo) {
            weiboMessage.mediaObject = getVideoObj();
        }
        if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }
        
        // 2. 鍒濆鍖栦粠绗笁鏂瑰埌寰崥鐨勬秷鎭姹�       
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 鐢╰ransaction鍞竴鏍囪瘑涓�釜璇锋眰
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        
        // 3. 鍙戦�璇锋眰娑堟伅鍒板井鍗氾紝鍞よ捣寰崥鍒嗕韩鐣岄潰
        mWeiboShareAPI.sendRequest(request);
    }

    /**
     * 绗笁鏂瑰簲鐢ㄥ彂閫佽姹傛秷鎭埌寰崥锛屽敜璧峰井鍗氬垎浜晫闈�
     * 褰搟@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 鏃讹紝鍙敮鎸佸垎浜崟鏉℃秷鎭紝鍗�     * 鏂囨湰銆佸浘鐗囥�缃戦〉銆侀煶涔愩�瑙嗛涓殑涓�锛屼笉鏀寔Voice娑堟伅銆�     * 
     * @param hasText    鍒嗕韩鐨勫唴瀹规槸鍚︽湁鏂囨湰
     * @param hasImage   鍒嗕韩鐨勫唴瀹规槸鍚︽湁鍥剧墖
     * @param hasWebpage 鍒嗕韩鐨勫唴瀹规槸鍚︽湁缃戦〉
     * @param hasMusic   鍒嗕韩鐨勫唴瀹规槸鍚︽湁闊充箰
     * @param hasVideo   鍒嗕韩鐨勫唴瀹规槸鍚︽湁瑙嗛
     */
    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {
        
        // 1. 鍒濆鍖栧井鍗氱殑鍒嗕韩娑堟伅
        // 鐢ㄦ埛鍙互鍒嗕韩鏂囨湰銆佸浘鐗囥�缃戦〉銆侀煶涔愩�瑙嗛涓殑涓�
        WeiboMessage weiboMessage = new WeiboMessage();
        if (hasText) {
            weiboMessage.mediaObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.mediaObject = getImageObj();
        }
        if (hasWebpage) {
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (hasMusic) {
            weiboMessage.mediaObject = getMusicObj();
        }
        if (hasVideo) {
            weiboMessage.mediaObject = getVideoObj();
        }
        /*if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }*/
        
        // 2. 鍒濆鍖栦粠绗笁鏂瑰埌寰崥鐨勬秷鎭姹�        
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 鐢╰ransaction鍞竴鏍囪瘑涓�釜璇锋眰
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        
        // 3. 鍙戦�璇锋眰娑堟伅鍒板井鍗氾紝鍞よ捣寰崥鍒嗕韩鐣岄潰
        mWeiboShareAPI.sendRequest(request);
    }

    /**
     * 鑾峰彇鍒嗕韩鐨勬枃鏈ā鏉裤�
     * 
     * @return 鍒嗕韩鐨勬枃鏈ā鏉�     */
    private String getSharedText() {
        int formatId = R.string.weibosdk_demo_share_text_template;
        String format = getString(formatId);
        String text = format;
        String demoUrl = getString(R.string.weibosdk_demo_app_url);
        if (mTextCheckbox.isChecked() || mImageCheckbox.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_text_template);
        }
        if (mShareWebPageView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_webpage_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_webpage_demo), demoUrl);
        }
        if (mShareMusicView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_music_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_music_demo), demoUrl);
        }
        if (mShareVideoView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_video_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_video_demo), demoUrl);
        }
        if (mShareVoiceView.isChecked()) {
            format = getString(R.string.weibosdk_demo_share_voice_template);
            text = String.format(format, getString(R.string.weibosdk_demo_share_voice_demo), demoUrl);
        }
        
        return text;
    }

    /**
     * 鍒涘缓鏂囨湰娑堟伅瀵硅薄銆�     * 
     * @return 鏂囨湰娑堟伅瀵硅薄銆�     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        return textObject;
    }

    /**
     * 鍒涘缓鍥剧墖娑堟伅瀵硅薄銆�     * 
     * @return 鍥剧墖娑堟伅瀵硅薄銆�     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
        imageObject.setImageObject(bitmapDrawable.getBitmap());
        return imageObject;
    }

    /**
     * 鍒涘缓澶氬獟浣擄紙缃戦〉锛夋秷鎭璞°�
     * 
     * @return 澶氬獟浣擄紙缃戦〉锛夋秷鎭璞°�
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = mShareWebPageView.getTitle();
        mediaObject.description = mShareWebPageView.getShareDesc();
        mediaObject.setThumbImage(mShareWebPageView.getThumbBitmap());
        mediaObject.actionUrl = mShareWebPageView.getShareUrl();
        mediaObject.defaultText = "Webpage 榛樿鏂囨";
        return mediaObject;
    }

    /**
     * 鍒涘缓澶氬獟浣擄紙闊充箰锛夋秷鎭璞°�
     * 
     * @return 澶氬獟浣擄紙闊充箰锛夋秷鎭璞°�
     */
    private MusicObject getMusicObj() {
        // 鍒涘缓濯掍綋娑堟伅
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = mShareMusicView.getTitle();
        musicObject.description = mShareMusicView.getShareDesc();
        
        // 璁剧疆 Bitmap 绫诲瀷鐨勫浘鐗囧埌瑙嗛瀵硅薄閲�        
        musicObject.setThumbImage(mShareMusicView.getThumbBitmap());
        musicObject.actionUrl = mShareMusicView.getShareUrl();
        musicObject.dataUrl = "www.weibo.com";
        musicObject.dataHdUrl = "www.weibo.com";
        musicObject.duration = 10;
        musicObject.defaultText = "Music 榛樿鏂囨";
        return musicObject;
    }

    /**
     * 鍒涘缓澶氬獟浣擄紙瑙嗛锛夋秷鎭璞°�
     * 
     * @return 澶氬獟浣擄紙瑙嗛锛夋秷鎭璞°�
     */
    private VideoObject getVideoObj() {
        // 鍒涘缓濯掍綋娑堟伅
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = mShareVideoView.getTitle();
        videoObject.description = mShareVideoView.getShareDesc();
        
        // 璁剧疆 Bitmap 绫诲瀷鐨勫浘鐗囧埌瑙嗛瀵硅薄閲�       
        videoObject.setThumbImage(mShareVideoView.getThumbBitmap());
        videoObject.actionUrl = mShareVideoView.getShareUrl();
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = "Vedio 榛樿鏂囨";
        return videoObject;
    }

    /**
     * 鍒涘缓澶氬獟浣擄紙闊抽锛夋秷鎭璞°�
     * 
     * @return 澶氬獟浣擄紙闊充箰锛夋秷鎭璞°�
     */
    private VoiceObject getVoiceObj() {
        // 鍒涘缓濯掍綋娑堟伅
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = mShareVoiceView.getTitle();
        voiceObject.description = mShareVoiceView.getShareDesc();
        
        // 璁剧疆 Bitmap 绫诲瀷鐨勫浘鐗囧埌瑙嗛瀵硅薄閲�        
        voiceObject.setThumbImage(mShareVoiceView.getThumbBitmap());
        voiceObject.actionUrl = mShareVoiceView.getShareUrl();
        voiceObject.dataUrl = "www.weibo.com";
        voiceObject.dataHdUrl = "www.weibo.com";
        voiceObject.duration = 10;
        voiceObject.defaultText = "Voice 榛樿鏂囨";
        return voiceObject;
    }
}