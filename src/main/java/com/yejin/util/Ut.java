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
            om.setDateFormat(dateFormat);         //  객체 -> Json 문자열
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
                return (T) om.readValue(jsonStr, cls); // content 의 class로 반환
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
            // jackson objectmapper 객체 생성
             ObjectMapper objectMapper = new ObjectMapper();
             String dateJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(articleDto);
             System.out.println(dateJson);
            // {"name":"Java","date":1630813646681}
        }
             public static void dateToJsonWithDateFormat(ArticleDto articleDto) throws JsonProcessingException {
            // jackson objectmapper 객체 생성
                  ObjectMapper objectMapper = new ObjectMapper();         // Date Format 설정
                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                  objectMapper.setDateFormat(dateFormat);         //  객체 -> Json 문자열
                  String dateJson = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(articleDto);         // Json 문자열 출력
                  System.out.println(dateJson); // {"name":"Java","date":"2021-09-05"}
                 //return dateJson;
        }

*/

    }

    public static Map<String, Object> mapOf(Object ... args) {
        // 몇개의 key,obj 조합이 올지 모르니까 가변인자로 받는거야
        // 그런데 key 값은 항상 string 일텐데, key에 대한 obj 는 정해주는 것이 효과적이지 않는가?
        // 그냥 key는 미리 정해놓는 건가?


        // 왜 순서가 바뀌지?
        // hashmap 은 저장순서를 유지하지 않는다. 해싱을 구현한 컬렉션클래스(hashset, hashmap)는 저장순서 유지 x
        // LinkedHashMap 사용! (hashset이면 linkdeshashset 사용)
        Map<String, Object> map = new LinkedHashMap<>();
        //Map<String, Object> map = new HashMap<>();
        for(int i=0;i<args.length;i+=2){
            map.put((String)args[i],args[i+1]);
        }
        return map;
    }




}
