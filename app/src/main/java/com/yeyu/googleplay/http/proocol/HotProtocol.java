package com.yeyu.googleplay.http.proocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by gaoyehua on 2016/8/17.
 */
public class HotProtocol extends BaseProtocol<ArrayList<String>> {

    @Override
    public String getKey() {
        return "hot";
    }

    @Override
    public String getParams() {
        return "";// 如果没有参数,就传空串,不要传null
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);

            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < ja.length(); i++) {
                String keyword = ja.getString(i);

                list.add(keyword);
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
