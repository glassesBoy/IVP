package com.ivp.xch.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.ivp.xch.result.JsonResult;

public class JsonHelper {


    // public static void fromJson() {
    // Gson gson = new GsonBuilder().create();
    // // Person p = gson.fromJson(reader, Person.class);
    // // System.out.println(p);
    // }
    //
    // public static String toJson(Object obj) {
    // Gson gson = new GsonBuilder().create();
    //// new GsonBuilder().setDateFormat(" yyyy-MM-ddTHH:mm:ss.SSS").create();
    // // yyyy-MM-dd'T'HH:mm:ss 把T加单引
    // return gson.toJson(obj);
    // // return new Gson().toJson(obj);
    // }
    //


    private static ObjectMapper mapper = new ObjectMapper();
    private static Hibernate4Module hbm4 = new Hibernate4Module();
    private static SimpleModule module = new SimpleModule();

    static {
        // module.addSerializer(BigDecimal.class, new CombinedPriceSerializer());
        // module.addSerializer(BigDecimal.class, new SimpleSerializer());
        mapper.registerModule(hbm4);
        mapper.registerModule(module);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 任意类型对象，转为JSON格式字符串
     * 
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("========toJSON2 Exception" + e);
            return null;
        }
    }

    /**
     * JSON 格式字符串，转成 JsonNode
     * 
     * @param jsonString
     * @return
     */
    public static JsonNode fromJson(String jsonString) {
        try {
            return mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode readJson(String jsonString) {
        try {
            return mapper.readValue(jsonString, JsonNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("登录成功"));
        System.out.println("============== " + json);
    }
}
