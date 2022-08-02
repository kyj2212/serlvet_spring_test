package com.yejin;

import com.yejin.annotation.Autowired;
import com.yejin.annotation.Controller;
import com.yejin.annotation.Service;
import com.yejin.util.Ut;
import org.reflections.Reflections;

import java.util.*;

public class Container {

  //  private static final ArticleController articleController;
   // private static final HomeController homeController;
    private static List<String> allControllerNames;
    private static Map<Class,Object> classObjectMap;
    //private static Map<Class,Object> fields;

    static {
        classObjectMap=new HashMap<>();
       // fields=new HashMap<>();
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
        resolveDependenciesAllComponents();
    }

    private static void scanServices(Reflections reflections) {
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Service.class)){
            classObjectMap.put(cls, Ut.cls.newObj(cls,null));
        }
    }

    private static void scanControllers(Reflections reflections) {
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            classObjectMap.put(cls, Ut.cls.newObj(cls,null));
            //scanAutowired(new Reflections("com.yejin",new FieldAnnotationsScanner()));
        }

    }
    private static void resolveDependenciesAllComponents() {
    //   System.out.println("resolveDepencies : ");
        for (Class cls : classObjectMap.keySet()) {
            Object o = classObjectMap.get(cls);
        //    System.out.println(o);
            resolveDependencies(o);
        }
    }

    private static void resolveDependencies(Object o) {
        //System.out.println(o.getClass().getDeclaredField("articleService"));
     //   System.out.println(o.getClass().getDeclaredFields().length);
      //  System.out.println(Arrays.stream(o.getClass().getDeclaredFields()).toList());
     //   System.out.println(Arrays.stream(Arrays.stream(o.getClass().getDeclaredFields()).toList().get(0).getDeclaredAnnotations()).toList());

        //   System.out.println(Arrays.stream(o.getClass().getDeclaredField("articleService").getDeclaredAnnotations()).toList());
        //System.out.println(o.getClass().getDeclaredField("articleService").getAnnotation(Autowired.class));
        // System.out.println(o.getClass().getDeclaredField("articleService").getAnnotations().length);
        Arrays.asList(o.getClass().getDeclaredFields())
                .stream()
                .filter(f -> f.isAnnotationPresent(Autowired.class))
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .forEach(field -> {
                    Class cls = field.getType();
                    Object dependency = classObjectMap.get(cls);

                    try {
                        field.set(o, dependency);
                    } catch (IllegalAccessException e) {

                    }
                });
    }

/*    private static void resolveDependencies(Object obj){
        Arrays.asList(obj.getClass().getDeclaredFields())
                .stream()
                .filter(f-> f.isAnnotationPresent(Autowired.class))
                .map(field -> {field.setAccessible(true);
                return field;})
                .forEach(field->{
                    Class cls = field.getType();
                    Object dependency = classObjectMap.get(cls);
                    try {
                        field.set(obj,dependency);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }*/
/*    private static void initObject(Object obj) {
        Arrays.asList(obj.getClass().getDeclaredFields())
                .stream()
                .sequential()
                .filter(f -> f.isAnnotationPresent(Autowired.class))
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .forEach(field -> {
                    Class clazz = field.getType();

                    try {
                        field.set(obj, classObjectMap.get(clazz));
                    } catch (IllegalAccessException e) {

                    }
                });
    }*/
/*
    public static Object getFieldValue(Object o, String fieldName, Object defaultValue) {
        Field field = null;

        try {
            field = o.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return defaultValue;
        }

        field.setAccessible(true);

        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            return defaultValue;
        }
    }
*/

/*
    public static void scanAutowired(Reflections ref){
      //  System.out.println("obj 가 없나?");
        for(Object obj : ref.getFieldsAnnotatedWith(Autowired.class)){
            System.out.println(obj);
            fields.put(obj.getClass(),getObj(obj.getClass()));
        }
        System.out.println(ref.getFieldsAnnotatedWith(Autowired.class));
    }
*/

    public static <T> T getObj(Class<T> cls){
        return (T)classObjectMap.get(cls);
    }

/*    public static <T> T getFileds(Class<T> cls){
        return (T)fields.get(cls);
    }*/

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
