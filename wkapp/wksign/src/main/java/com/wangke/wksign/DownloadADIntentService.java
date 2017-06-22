package com.wangke.wksign;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.wangke.wkcore.http.HttpCallBack;
import com.wangke.wkcore.http.HttpFileCallBack;
import com.wangke.wkcore.http.OkHttpUtil;
import com.wangke.wkcore.others.SPConstants;
import com.wangke.wkcore.utils.WkSpUtil;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wk37 on 2017/6/22.
 */

public class DownloadADIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadADIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String adDeadLine = intent.getStringExtra("adDeadLine");
            String adMD5 = intent.getStringExtra("adMD5");
            // TODO: 2017/6/22   adUrl 为 广告信息 url
            String adUrl = "";
            Map<String, String> map = new TreeMap<>();
            map.put("adDeadLine", adDeadLine);
            map.put("adMD5", adMD5);

            OkHttpUtil.getInstance().get(this, adUrl, map, new HttpCallBack<ADBean>() {
                @Override
                public void onSuccess(Object tag, int code, ADBean data) {
                    WkSpUtil.put(SPConstants.SP_AD_DEADLINE, data.getAdDeadLine());
                    // 请求成功后，下载图片
                    OkHttpUtil.getInstance().download(this, data.getImgUrl(), getFilesDir().getPath(), data.getAdDeadLine() + "", new HttpFileCallBack() {
                        @Override
                        public void onSuccess(Object tag, int code, File data) {

                        }

                        @Override
                        public void onFail(Object tag, String msg) {

                        }
                    });
                }

                @Override
                public void onFail(Object tag, String msg) {

                }
            });


        }


    }

    /**
     * 广告实体类
     */
    private class ADBean {
        Long adDeadLine;
        String imgUrl;

        public Long getAdDeadLine() {
            return adDeadLine;
        }

        public void setAdDeadLine(Long adDeadLine) {
            this.adDeadLine = adDeadLine;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
