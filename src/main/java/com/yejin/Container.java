package com.yejin;

import com.yejin.annotation.Controller;
import com.yejin.annotation.Service;
import com.yejin.util.Ut;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {

  //  private static final ArticleController articleController;
   // private static final HomeController homeController;
    private static List<String> allControllerNames;
    private static Map<Class,Object> classObjectMap;

    static {
        classObjectMap=new HashMap<>();
        scanComponents();
        System.out.println("classObjMap : "+classObjectMap.keySet());
        allControllerNames=new ArrayList<>();
        /*        Class article = ArticleController.class;
        Class home = HomeController.class;
        try {
            articleController=(ArticleController) article.getDeclaredConstructor().newInstance();
            homeController=(HomeController) home.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }*/

    }

    private static void scanComponents() {
        Reflections reflections = new Reflections("com.yejin");
        scanServices(reflections);
        scanControllers(reflections);
    }

    private static void scanServices(Reflections reflections) {
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Service.class)){
            classObjectMap.put(cls, Ut.cls.newObj(cls,null));
        }
    }

    private static void scanControllers(Reflections reflections) {
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            classObjectMap.put(cls, Ut.cls.newObj(cls,null));
        }
    }

    public static <T> T getObj(Class<T> cls){
        return (T)classObjectMap.get(cls);
    }

/*
    public static ArticleController getArticleController() {

        return articleController;
    }
    public static HomeController getHomeController(){
        return homeController;
    }
*/

    public static void getAllControllers() {
        Reflections reflections = new Reflections("com.yejin");
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            String name = cls.getSimpleName().split("Controller")[0].toLowerCase();
        }
    }



    public static List<String> getAllControllerNames() {
        Reflections reflections = new Reflections("com.yejin");
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            String name = cls.getSimpleName().split("Controller")[0].toLowerCase();
            allControllerNames.add(name);
        }
        return allControllerNames;
    }
}
