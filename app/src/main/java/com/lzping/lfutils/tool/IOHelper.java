package com.lzping.lfutils.tool;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/1/10.
 */

public class IOHelper {
    /**
     * 读取assets文件内容
     */
    public static String readAssetsFile(Context context,String fileName){
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {

            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader  = new BufferedReader(inputReader);

            StringBuffer sb = new StringBuffer();

            String line = null ;
            while ((line = bufReader.readLine()) != null){
               sb.append(line);
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputReader!=null){
                try {
                    inputReader.close();
                } catch (IOException e) {
                }
            }
            if (bufReader!=null){
                try {
                    bufReader.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }


    /**
     * json String ---> json array
     * 需要gson
     */
    public static JsonArray parseString2JsonArray(String strByJson){
        try {
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            //将JSON的String 转成一个JsonArray对象
            JsonArray array = parser.parse(strByJson).getAsJsonArray();
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * jsonArray -> arraylist
     *
     */
    public static <T> List<T> parseJsonArray2List(JsonArray jsonArray, Class<T> type){
        try {
            Gson gson = new Gson();
            List<T> list = new ArrayList<>();
            //加强for循环遍历JsonArray
            for (JsonElement user : jsonArray) {
                //使用GSON，直接转成Bean对象
                T bean = gson.fromJson(user, type);
                list.add(bean);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json String ---> list<Object>
     */
    public static <T> List<T> parseString2JsonList(String strByJson,Class<T> type){
        try {
           return parseJsonArray2List(parseString2JsonArray(strByJson),type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * map -> String
     */
    /**
     * 将Map转化为Json文本
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJsonString(Map<String, T> map) {
        String jsonStr = "";
        try {
            Gson gson = new Gson();
            jsonStr = gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * String -> map
     */
    public static HashMap<String, String> jsonString2Map(String data){
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        HashMap<String, String> map = g.fromJson(data, new TypeToken<HashMap<String, String>>() {}.getType());
        return map;
    }

    /**
     * String -> Bundle
     *
     */
    public static Bundle jsonString2Bundle(String jsonString){
        Bundle bundle = new Bundle();
        HashMap<String,String> map = jsonString2Map(jsonString);
        Iterator<Map.Entry<String, String>> ite = map.entrySet().iterator();
        Map.Entry entry = null;
        String key = null;
        String value = null;
        while (ite.hasNext()){
            entry = ite.next();
            key = entry.getKey().toString();
            value = entry.getValue().toString();
            bundle.putString(key,value);
        }
        return bundle;
    }


    /**
     * 检测 数据 - >    {key=value,key=value}
     * @param data
     */
    public static String checkData(String data) {
       String nData = data.replaceAll("'","\"");
        if (!nData.startsWith("{")){
            nData = "{"+nData;
        }
        if (!nData.endsWith("}")){
            nData = nData+"}";
        }
        return nData;
    }






}
