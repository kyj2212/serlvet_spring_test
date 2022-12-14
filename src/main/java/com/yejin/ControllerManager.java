package com.yejin;


import com.yejin.annotation.Controller;
import com.yejin.annotation.GetMapping;
import com.yejin.annotation.PostMapping;
import com.yejin.mymap.MyMap;
import com.yejin.util.Ut;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerManager {
    private static Map<String, RouteInfo> routeInfos;

    static {
        routeInfos = new HashMap<>();

        scanMappings();
    }

    private static void scanMappings() {
        Reflections ref = new Reflections(App.BASE_PACKAGE_PATH);
        for (Class<?> controllerCls : ref.getTypesAnnotatedWith(Controller.class)) {
            Method[] methods = controllerCls.getDeclaredMethods();

            for (Method method : methods) {
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                PostMapping postMapping = method.getAnnotation(PostMapping.class);

                String httpMethod = null;
                String path = null;

                if (getMapping != null) {
                    path = getMapping.value();
                    httpMethod = "GET";
                }else if (postMapping != null) {
                    path = postMapping.value();
                    httpMethod = "POST";
                }

                if (path != null && httpMethod != null) {
                    String actionPath = Ut.str.beforeFrom(path, "/", 4);

                    String key = httpMethod + "___" + actionPath;

                    routeInfos.put(key, new RouteInfo(path, actionPath, controllerCls, method));
                }
            }
        }
    }

    public static void runAction(HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);

        String routeMethod = rq.getRouteMethod();
        String actionPath = rq.getActionPath();

        String mappingKey = routeMethod + "___" + actionPath;
       // System.out.println("mappingkey "+mappingKey);
        System.out.println("routeInfos "+routeInfos);
        boolean contains = routeInfos.containsKey(mappingKey);
      //  System.out.println("cotains : "+contains);
        System.out.println("cotains : "+routeInfos.get(mappingKey).getPath()+routeInfos.get(mappingKey).getActionPath()+routeInfos.get(mappingKey).getControllerCls()+routeInfos.get(mappingKey).getMethod());

        if (contains == false) {
            rq.println("?????? ????????? ???????????? ????????????.");
            return;
        }

        // ???????????? ??????????????? routeInfo ??? ????????? ????????? ????????? ????????????!!
        // ????????? ????????? ?????? getter, setter??? ???????????? ?????? setRoutInfo??? ?????????
        RouteInfo routeInfo = routeInfos.get(mappingKey);
        rq.setRouteInfo(routeInfo);

        runAction(rq, routeInfo);
    }

    private static void runAction(Rq rq, RouteInfo routeInfo) {
        Class controllerCls = routeInfo.getControllerCls();
        Method actionMethod = routeInfo.getMethod();
        System.out.println("actionMethod : "+actionMethod);

        Object controllerObj = Container.getObj(controllerCls);

        try {
            // ????????? rq ??? ??????
            System.out.println("reqeust : " );
            actionMethod.invoke(controllerObj, rq);
        } catch (IllegalAccessException e) {
            rq.println("??????????????? ?????????????????????.");
        } catch (InvocationTargetException e) {

            throw new RuntimeException();
            // exception cause ?????? ??????
            //rq.println("??????????????? ?????????????????????.");
        }
        finally {
            MyMap myMap = Container.getObj(MyMap.class);
            myMap.closeConnection();
        }
    }

    public static void init() {

    }

    public static Map<String, RouteInfo> getRouteInfosForTest() {
        return routeInfos;
    }
}