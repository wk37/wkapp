package com.wangke.wkcore.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by wk on 2016/7/21.
 */
public class FilePathUtil {


    //退出时必须清理
    public static final String SP_MODE = "mode";      // sp情景模式名字，后面 编辑/添加时 判断不能同名
    public static final String SP_ROOM_ID_TEMP = "roomIdTemp";      // sp情景模式名字，后面 编辑/添加时 判断不能同名
    public static final String SP_FILTERSERVICE = "msg_filterservice";//sp服务项目筛选条件
    public static final String SP_FILTERSERVICEUSER = "msg_filterserviceuser";//sp服务手艺人筛选条件
    public static final String SP_CACHE_TEMP = "cache_temp";//sp临时缓存


    //退出时不清理
    public static final String SP_IMUSER_INFO = "ImUserInfo";      // sp环信好友 文件名
    public static final String SP_GROUP_INFO = "GroupInfo";      // sp环信群组 文件名
    public static final String SP_USER_PHONE = "userPhone";      // sp保存登录成功的号码
    public static final String SP_VERSION_CODE= "version_code";      // sp保存版本号，用于判断是否进入引导页
    public static final String GROUP_STATE= "group_state";
    public static final String TOUSHIJI_TIME= "toushiji_before_time";//投食机上一次投食时间




    // 文件分隔符
    private static final String FILE_SEPARATOR = "/";
    // 外存sdcard存放路径
    private static final String SD_EHOME_PATH = Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "DuduEHome" + FILE_SEPARATOR;



    /**
     * 默认缓存地址
     * @return
     */
    public static String getCacheDir(){
        return createPath(".cache");
    }
    /**
     * 图片缓存地址
     * @return
     */
    public static String getImgCacheDir(){
        return createPath(".cacheImg");
    }

    /**
     * 拍照缓存地址
     * @return
     */
    public static String getPhotosDir(){
        return createPath("photos");
    }

    /**
     * 版本更新 apk 地址
     * @return
     */
    public static String getApkUpdate(){
        return createPath("apkUpdate");
    }

    /**
     * 获取录制视频地址
     * @return
     */
    public static String getVideoPath(){
        return createPath("video");
    }


    /**
     * 本app在SD卡创建的文件夹
     * @return
     */
    private static String getEhomePath( ){
        File filePath = new File(SD_EHOME_PATH);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return filePath.getAbsolutePath();
    }

    /**
     * 创建 路径
     * @param pathName
     */
    private static String createPath(String pathName){
        File filePath = new File(SD_EHOME_PATH+pathName);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return filePath.getAbsolutePath();
    }


}
