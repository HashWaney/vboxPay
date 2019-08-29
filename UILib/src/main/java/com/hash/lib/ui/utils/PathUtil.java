package com.hash.lib.ui.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 地址的管理
 * 替换：BitmapUtils.getDiskCacheDir
 */

@SuppressWarnings("ConstantConditions")
public class PathUtil {

    /**
     * Context.
     */
    private static Application sApplication;

    private static PathUtil mPathUtil;

    private PathUtil() {
    }

    public static void init(Application application) {
        if (sApplication == null) {
            sApplication = application;
            mPathUtil = new PathUtil();
        }
    }

    public static PathUtil getInstance() {
        if (mPathUtil == null) {
            mPathUtil = new PathUtil();
        }
        return mPathUtil;
    }

    /**
     * 数据库的路径
     */
    public String getDataBasesPath() {
        if (sApplication != null) {
            return "/data/data/" + sApplication.getPackageName() + "/databases/";
        }
        return "/data/data/" + "com.hash.native" + "/databases/";
    }

    /**
     * web界面下载存储的图片路径
     */
    public String getWebImgePath() {
        if (sApplication != null) {
            return getDiskCacheDir(sApplication, "html").getAbsolutePath() + File.separator;
        }
        return "";
    }


    /**
     * 获取可以使用的缓存目录
     *
     * @param context
     * @param uniqueName
     * @return
     */
    private static File getDiskCacheDir(Context context, String uniqueName) {
        File cacheFile = new File(getExternalCacheDir(context), uniqueName);
        try {
            if (!cacheFile.exists()) {
                cacheFile.getParentFile().mkdirs();
                boolean isSuccess = cacheFile.mkdirs();
                if (!isSuccess) {//如果没有创建成功,则使用应用内的缓存文件夹来创建
                    cacheFile = new File(context.getCacheDir(), uniqueName);
                    if (!cacheFile.exists()) {
                        cacheFile.getParentFile().mkdirs();
                        cacheFile.mkdirs();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheFile;
    }

    /**
     * 获取程序外部的缓存目录
     */
    public static File getExternalCacheDir(Context mContext) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //检查外置存储卡是否挂载
            return mContext.getExternalCacheDir();
        }
        //外置存储卡不正常,获取应用内存储空间
        return mContext.getCacheDir();
    }

}
