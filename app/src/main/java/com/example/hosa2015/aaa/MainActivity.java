package com.example.hosa2015.aaa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

public class MainActivity extends Activity {
    private Button button, button2;
    private UMShareListener umShareListener;
    private SHARE_MEDIA[] displaylist;
    private UMImage image;
    private String urlshare = "http://123.56.162.207:8786/baominghuodong/index.html";
    private ShareBoardlistener shareBoardlistener;
    private LinearLayout weiXin, weiXinCircle, qQ, qQCircle, xinLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = new UMImage(MainActivity.this, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        /**
         * 定义分享平台的顺序
         */
        displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA
                };
        button = (Button) findViewById(R.id.button);//调出分享面板按钮
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {  //微博测试
            @Override
            public void onClick(View v) {
                new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.SINA)
                        .setCallback(umShareListener)
                        .withText("umeng")
//                        .withMedia(image)
//                        .withTargetUrl("http://www.baidu.com")
                        .share();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {//自定义按钮
            @Override
            public void onClick(View v) {
                initSharePopUpWindow(findViewById(R.id.button3));
            }
        });


//        Log.LOG = false;
        UMShareAPI mShareAPI = UMShareAPI.get(this);


        /**
         * ShareBoardlistener 某些平台平台需要特殊处理的时候调用这个接口，我这个Demo中并没有使用这个接口
         */
        final ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                if (share_media == SHARE_MEDIA.SINA) {//我这里判断如果调用的是新浪平台就弹出一个提示，
                    Toast.makeText(MainActivity.this, "ShareBoardlistener", Toast.LENGTH_SHORT).show();
                }

            }
        };
        /**
         * 分享的回调接口，貌似QQ QQ空间才会调用这个接口
         */
        umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(MainActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(MainActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            }
        };

        //使用自定义的加载Dialog
        ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setTitle("友情提示");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在加载...");
        Config.dialog = dialog;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(MainActivity.this)
                        .setDisplayList(displaylist)    //加载分享的列表
                        .withText("呵呵")               //分享的内容  。
                        .withTitle("title")            //分享的标题  对新浪微博不管用
                        .withTargetUrl("http://www.baidu.com")  //TatgetUrl只能加在文字中间或后面，当同时传递视频/音频时无效
                        .withMedia(image)
                        .setListenerList(umShareListener)
//                        .setShareboardclickCallback(shareBoardlistener)
                        .open();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 分享调用的popUpWindow
     *
     * @param parent
     */
    private PopupWindow sharePopUpWindow = null;

    private void initSharePopUpWindow(View parent) {
        View v = getLayoutInflater().inflate(R.layout.fenxaingpopupwindow,
                null);
        initShareView(v);
        // 初始化悬浮窗，指定布局
        sharePopUpWindow = new PopupWindow(v, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        sharePopUpWindow.setBackgroundDrawable(new BitmapDrawable());
        sharePopUpWindow.setFocusable(true);
        sharePopUpWindow.setWidth(LayoutParams.MATCH_PARENT);
        sharePopUpWindow.setHeight(LayoutParams.WRAP_CONTENT);
        sharePopUpWindow.setContentView(v);
        sharePopUpWindow.setOutsideTouchable(true);
        sharePopUpWindow.setAnimationStyle(R.style.PopupAnimation);
        sharePopUpWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
        sharePopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(1.0f);
            }
        });
    }

    private void initShareView(View view) {
        weiXin = (LinearLayout) view.findViewById(R.id.weiXin);
        weiXinCircle = (LinearLayout) view.findViewById(R.id.pengYouQuan);
        qQ = (LinearLayout) view.findViewById(R.id.qqq);
        qQCircle = (LinearLayout) view.findViewById(R.id.qqZone);
        xinLang = (LinearLayout) view.findViewById(R.id.sina);
        weiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ShareAction(self).setPlatform(SHARE_MEDIA.WEIXIN)
//                        .setCallback(umShareListener)
//                        .withText("" + app.getActivityIntroduction()) // 分享的内容 。
//                        .withTitle("" + app.getActivityName()) // 分享的标题 对新浪微博不管用
//                        .withTargetUrl(urlshare + "?" + "pageNo=" + "" + "&" + "pageSize=" + "" + "&" + "loginid=" + userId + "&" + "activityid=" + app.getActivityId()) // TatgetUrl只能加在文字中间或后面，当同时传递视频/音频时无效
//                        .withMedia(image)
//                        .share();
            }
        });
        weiXinCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ShareAction(self).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .setCallback(umShareListener)
//                        .withText("" + app.getActivityIntroduction()) // 分享的内容 。
//                        .withTitle("" + app.getActivityName()) // 分享的标题 对新浪微博不管用
//                        .withTargetUrl(urlshare + "?" + "pageNo=" + "" + "&" + "pageSize=" + "" + "&" + "loginid=" + userId + "&" + "activityid=" + app.getActivityId()) // TatgetUrl只能加在文字中间或后面，当同时传递视频/音频时无效
//                        .withMedia(image)
//                        .share();
            }
        });
        qQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ShareAction(self).setPlatform(SHARE_MEDIA.QQ)
//                        .setCallback(umShareListener)
//                        .withText("" + app.getActivityIntroduction()) // 分享的内容 。
//                        .withTitle("" + app.getActivityName()) // 分享的标题 对新浪微博不管用
//                        .withTargetUrl(urlshare + "?" + "pageNo=" + "" + "&" + "pageSize=" + "" + "&" + "loginid=" + userId + "&" + "activityid=" + app.getActivityId()) // TatgetUrl只能加在文字中间或后面，当同时传递视频/音频时无效
//                        .withMedia(image)
//                        .share();
            }
        });
        qQCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ShareAction(self).setPlatform(SHARE_MEDIA.QZONE)
//                        .setCallback(umShareListener)
//                        .withText("" + app.getActivityIntroduction()) // 分享的内容 。
//                        .withTitle("" + app.getActivityName()) // 分享的标题 对新浪微博不管用
//                        .withTargetUrl(urlshare + "?" + "pageNo=" + "" + "&" + "pageSize=" + "" + "&" + "loginid=" + userId + "&" + "activityid=" + app.getActivityId()) // TatgetUrl只能加在文字中间或后面，当同时传递视频/音频时无效
//                        .withMedia(image)
//                        .share();
            }
        });
        xinLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ShareAction(self).setPlatform(SHARE_MEDIA.SINA)
//                        .setCallback(umShareListener)
//                        .withText("" + app.getActivityName()) // 分享的内容 。getActivityIntroduction
////						.withTitle(""+app.getActivityName()) // 分享的标题 对新浪微博不管用
//                        .withTargetUrl(urlshare + "?" + "pageNo=" + "" + "&" + "pageSize=" + "" + "&" + "loginid=" + userId + "&" + "activityid=" + app.getActivityId()) // TatgetUrl只能加在文字中间或后面，当同时传递视频/音频时无效
//                        .withMedia(image)
//                        .share();
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = bgAlpha;
        getWindow().setAttributes(params);
    }
}
