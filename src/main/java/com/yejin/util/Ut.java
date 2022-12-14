package com.yejin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ut {

    public static void sleep(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class cls {

        public static <T> T newObj(Class<T> cls, T defaultValue) {
            try {
                return cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                return defaultValue;
            } catch (IllegalAccessException e) {
                return defaultValue;
            } catch (InvocationTargetException e) {
                return defaultValue;
            } catch (NoSuchMethodException e) {
                return defaultValue;
            }
        }
    }
    public static class str {

        public static String decapitalize(String string) {
            if (string == null || string.length() == 0) {
                return string;
            }

            char c[] = string.toCharArray();
            c[0] = Character.toLowerCase(c[0]);

            return new String(c);
        }

        public static String beforeFrom(String str, String fromStr, int matchCount) {
            StringBuilder sb = new StringBuilder();

            String[] bits = str.split(fromStr);

            for (int i = 0; i < matchCount; i++) {
                sb.append(bits[i]);
                if ( i + 1 < matchCount ) {
                    sb.append(fromStr);
                }
            }

            return sb.toString();
        }
    }

    public static class reflection {
        public static <T> T getFieldValue(Object o, String fieldName, T defaultValue) {
            Field field = null;

            try {
                field = o.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return defaultValue;
            }

            field.setAccessible(true);

            try {
                return (T)field.get(o);
            } catch (IllegalAccessException e) {
                return defaultValue;
            }
        }
    }

    public static class json{

        private static final ObjectMapper om;

        static {
           // om = new ObjectMapper().findAndRegisterModules();
            om = new ObjectMapper();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            om.setDateFormat(dateFormat);         //  ?????? -> Json ?????????
        }
        public static String toStr (Object obj, String defaultValue){
            try{

                return om.registerModule(new JavaTimeModule()).writeValueAsString(obj);

            } catch (JsonProcessingException e) {
                 return defaultValue;
            }
        }
        public static <T> T toObj(String jsonStr, Class<T> cls, T defaultValue) {
            try {
                return (T) om.readValue(jsonStr, cls); // content ??? class??? ??????
            } catch (JsonProcessingException e) {
                return defaultValue;
            }
        }
/*        public static <T> List<T> toObj(String jsonStr, TypeReference<List<T>> typeReference, List<T> defaultValue) {
            try {
                return om.readValue(jsonStr, typeReference);
            } catch (JsonProcessingException e) {
                return defaultValue;
            }
        }*/
        public static <T> T toObj(String jsonStr, TypeReference<T> typeReference, T defaultValue) {
            try {
                return om.readValue(jsonStr, typeReference);
            } catch (JsonProcessingException e) {
                return defaultValue;
            }
        }


/*
        public static void dateToJson(ArticleDto articleDto) throws JsonProcessingException {
            // jackson objectmapper ?????? ??????
             ObjectMapper objectMapper = new ObjectMapper();
             String dateJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(articleDto);
             System.out.println(dateJson);
            // {"name":"Java","date":1630813646681}
        }
             public static void dateToJsonWithDateFormat(ArticleDto articleDto) throws JsonProcessingException {
            // jackson objectmapper ?????? ??????
                  ObjectMapper objectMapper = new ObjectMapper();         // Date Format ??????
                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                  objectMapper.setDateFormat(dateFormat);         //  ?????? -> Json ?????????
                  String dateJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(articleDto);         // Json ????????? ??????
                  System.out.println(dateJson); // {"name":"Java","date":"2021-09-05"}
                 //return dateJson;
        }

*/

    }

    public static Map<String, Object> mapOf(Object ... args) {
        // ????????? key,obj ????????? ?????? ???????????? ??????????????? ????????????
        // ????????? key ?????? ?????? string ?????????, key??? ?????? obj ??? ???????????? ?????? ??????????????? ??????????
        // ?????? key??? ?????? ???????????? ???????


        // ??? ????????? ??????????
        // hashmap ??? ??????????????? ???????????? ?????????. ????????? ????????? ??????????????????(hashset, hashmap)??? ???????????? ?????? x
        // LinkedHashMap ??????! (hashset?????? linkdeshashset ??????)
        Map<String, Object> map = new LinkedHashMap<>();
        //Map<String, Object> map = new HashMap<>();
        for(int i=0;i<args.length;i+=2){
            map.put((String)args[i],args[i+1]);
        }
        return map;
    }




}
