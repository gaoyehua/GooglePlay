package com.yeyu.googleplay.http.proocol;

import com.yeyu.googleplay.domain.AppInfo;
import com.yeyu.googleplay.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gaoyehua on 2016/8/17.
 */
public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {

    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";// 如果没有参数,就传空串,不要传null
    }

    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);

            ArrayList<SubjectInfo> list = new ArrayList<SubjectInfo>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                SubjectInfo info = new SubjectInfo();
                info.des = jo.getString("des");
                info.url = jo.getString("url");

                list.add(info);
            }

            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
