package com.example.hosa2015.aaa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
//        Log.LOG = false;
//        UMShareAPI mShareAPI = UMShareAPI.get(this);
        /**
         * 定义分享平台的顺序
         */
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA
                };
        final UMImage image = new UMImage(MainActivity.this, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

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
        final UMShareListener umShareListener = new UMShareListener() {
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
}
