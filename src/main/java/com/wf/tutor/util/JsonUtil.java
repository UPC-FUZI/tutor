package com.wf.tutor.util;


import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class JsonUtil {
    private  static Gson gson=new Gson();

    public static <T> T jsonToObject(String jsonData, Class<T> type) {
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    /**
     *  将Json数组解析成相应的映射对象列表
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T[]> type) {
        T[] list = gson.fromJson(jsonData, type);
        if (list == null)
        {
            return null;
        }
        return Arrays.asList(list);
    }

    public static String objectToJson(Object data) {
        return gson.toJson(data);
    }

}
