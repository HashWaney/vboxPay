package com.ydtl.uboxpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.NotificationManager;

import java.io.File;

public class InstallPackageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传来的数据
        File apkfile = (File) this.getIntent().getExtras().getSerializable("file");
        Uri uri = Uri.fromFile(apkfile);
        //设置安装文件的Intent
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(installIntent);
        //获取Notification的管理器类
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //清除Notification
        nm.cancelAll();
        //结束该activity返回安装之前的主界面
        this.finish();
    }
}
