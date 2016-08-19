package com.yeyu.googleplay.http.proocol;

import com.yeyu.googleplay.Utils.IOUtils;
import com.yeyu.googleplay.Utils.LogUtils;
import com.yeyu.googleplay.Utils.StringUtils;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.http.HttpHelper;
import com.yeyu.googleplay.http.HttpHelper.HttpResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public abstract class BaseProtocol<T> {

    //index表示分页
    public T getData(int index) {
        //判断是否有缓存
        String result = getCache(index);
        if (StringUtils.isEmpty(result)) {
            //没有缓存
            // 请求服务器
            result = getDataFromServer(index);
        }

        //开始解析
        if (result != null) {
            T data = parseData(result);
            return data;
        }
        return null;
    }

    //从网络获取数据
    private String getDataFromServer(int index) {
        // http://www.yeyu.com/home?index=0&name=zhangsan&age=18
        HttpResult httpResult = HttpHelper.get(HttpHelper.URL
                + getKey() + "?index=" + index + getParams());
        if (httpResult != null) {
            String result = httpResult.getString();
            LogUtils.e(result);
            //写缓存
            if (!StringUtils.isEmpty(result)) {
                setCache(index, result);
            }
        }
        return null;
    }

    // 获取网络链接关键词, 子类必须实现
    public abstract String getKey();

    // 获取网络链接参数, 子类必须实现
    public abstract String getParams();

    //写缓存
    //以index为Key，json为Value
    public void setCache(int index, String json) {
        // 以url为文件名, 以json为文件内容,保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();// 本应用的缓存文件夹
        // 生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index
                + getParams());

        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);
            //失效截止时间
            long deadline = System.currentTimeMillis() + 30 * 60 * 1000;//半个小时
            writer.write(deadline + "\n");
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    //读缓存
    public String getCache(int index) {
        //以url为文件名，以json为文件内容，将缓存保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();
        //生成缓存文件
        File cacheFile = new File(cacheDir, getKey() + "?index=" + index + getParams());

        //判断缓存是否存在
        if (cacheFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String deadline = reader.readLine();// 读取第一行的有效期
                long deadtime = Long.parseLong(deadline);

                if (System.currentTimeMillis() < deadtime) {// 当前时间小于截止时间,
                    // 说明缓存有效
                    // 缓存有效
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    return sb.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }
    // 解析json数据, 子类必须实现
    public abstract T parseData(String result);
}
