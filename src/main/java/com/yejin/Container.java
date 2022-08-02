package com.yejin;

import com.yejin.annotation.Controller;
import com.yejin.article.controller.ArticleController;
import com.yejin.home.controller.HomeController;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private static final ArticleController articleController;
    private static final HomeController homeController;
    private static List<String> allController;

    static {
        articleController=ArticleController.getInstance();
        homeController=HomeController.getInstance();
       // homeController=new HomeController();
        allController=new ArrayList<>();

    }
    public static ArticleController getArticleController(){
        return articleController;
    }
    public static HomeController getHomeController(){
        return homeController;
    }

    public static void getAllControllers() {
        Reflections reflections = new Reflections("com.yejin");
        for(Class<?> classes : reflections.getTypesAnnotatedWith(Controller.class)){
            String name = classes.getSimpleName().split("Controller")[0].toLowerCase();
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
