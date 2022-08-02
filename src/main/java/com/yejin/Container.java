package com.yejin;

import com.yejin.annotation.Controller;
import com.yejin.article.controller.ArticleController;
import com.yejin.home.controller.HomeController;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Container {

    private static final ArticleController articleController;
    private static final HomeController homeController;
    private static List<String> allController;

    static {

        Class article = ArticleController.class;
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
        }
/*        Reflections reflections = new Reflections("com.yejin");
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            try {
                articleController=(ArticleController) cls.getConstructor(String.class).newInstance("getArticleController");
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }*/
       // homeController=new HomeController();
        allController=new ArrayList<>();

    }
    public static ArticleController getArticleController() {

        return articleController;
    }
    public static HomeController getHomeController(){
        return homeController;
    }

    public static void getAllControllers() {
        Reflections reflections = new Reflections("com.yejin");
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            String name = cls.getSimpleName().split("Controller")[0].toLowerCase();
            allController.add(name);
        }
    }



    public static List<String> getAllControllerNames() {
        Reflections reflections = new Reflections("com.yejin");
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            String name = cls.getSimpleName().split("Controller")[0].toLowerCase();
            allController.add(name);
        }
        return allController;
    }
}
