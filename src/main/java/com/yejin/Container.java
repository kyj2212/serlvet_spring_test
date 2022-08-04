package com.yejin;

import com.yejin.annotation.Autowired;
import com.yejin.annotation.Controller;
import com.yejin.annotation.Repository;
import com.yejin.annotation.Service;
import com.yejin.mymap.MyMap;
import com.yejin.util.Ut;
import org.reflections.Reflections;

import java.lang.reflect.Field;
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
/*                Class article = ArticleController.class;
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

    public static <T> T getObj(Class<T> cls){
        return (T)classObjectMap.get(cls);
    }

    private static void scanComponents() {
        Reflections reflections = new Reflections("com.yejin");
        scanRepositories(reflections);
        scanServices(reflections);
        scanControllers(reflections);
        scanCustom();

        resolveDependenciesAllComponents();
    }

    private static void scanCustom() {
        //System.out.println("MYMAP 객체 생성 in container  ");

        classObjectMap.put(MyMap.class,new MyMap(App.DB_HOST,App.DB_PORT,App.DB_ID, App.DB_PW,App.DB_NAME));
        System.out.println("MYMAP 객체 생성 in container : "+classObjectMap.get(MyMap.class));
        //System.out.println(classObjectMap.get(MyMap.class));
    }

/*    private static void scanCustom() {
        objects.put(MyMap.class, new MyMap(App.DB_HOST, App.DB_PORT, App.DB_ID, App.DB_PASSWORD, App.DB_NAME));
    }*/

    private static void scanRepositories(Reflections reflections) {
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Repository.class)){
            classObjectMap.put(cls, Ut.cls.newObj(cls,null));
        }
    }
    private static void scanServices(Reflections reflections) {
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Service.class)){
            classObjectMap.put(cls, Ut.cls.newObj(cls,null));
        }
    }

    private static void scanControllers(Reflections reflections) {
        for(Class<?> cls : reflections.getTypesAnnotatedWith(Controller.class)){
            Object value=Ut.cls.newObj(cls,null);
            classObjectMap.put(cls,value );
           // scanAutowired(value);
            //scanAutowired(new Reflections("com.yejin",new FieldAnnotationsScanner()));
        }

    }

    public static void scanAutowired(Object obj){
        Class cls=obj.getClass();
        System.out.println(Arrays.stream(cls.getDeclaredFields()).toList()); // 왜 declared? -> private 한 필드에 접근하기 위해서는 declared로 한다
        //System.out.println(ref.getFieldsAnnotatedWith(Autowired.class).stream().toList()); // 아 이 필드의 어노테이션이 구나 오케이
        System.out.println("isannotaion autowired? "+Arrays.stream(cls.getDeclaredFields()).filter(f->f.isAnnotationPresent(Autowired.class)).toList());
        //  System.out.println("obj 가 없나?");
        for(Field field : cls.getDeclaredFields()){
     //   for(Field field : ref.getFieldsAnnotatedWith(Autowired.class)){
            System.out.println(field);
            if(!field.isAnnotationPresent(Autowired.class))
                continue;
            field.setAccessible(true);
            Class o = field.getType(); // 왜 type? type으로 하면 Class 가 나오는데?
            try {
                System.out.println("cls "+cls); // class com.yejin.article.controller.ArticleController
                System.out.println("o "+o); // class com.yejin.article.service.ArticleService
                System.out.println("field "+field); // private com.yejin.article.service.ArticleService com.yejin.article.controller.ArticleController.articleService
                System.out.println("get(cls) "+classObjectMap.get(cls)); // article controller 의 생성된 인스턴스 //com.yejin.article.controller.ArticleController@436813f3
                System.out.println("get(o) "+ classObjectMap.get(o)); // article service 의 생성된 인스턴스 // com.yejin.article.service.ArticleService@8f4ea7c

                Object value = classObjectMap.get(o);
                field.set(obj,value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private static void resolveDependenciesAllComponents() {
    //   System.out.println("resolveDepencies : ");
        for (Class cls : classObjectMap.keySet()) {
            Object o = classObjectMap.get(cls); // "ArticleControllr" 클래스 객체 o 가 있다면
        //    System.out.println(o);
            resolveDependencies(o);
        }
    }

    private static void resolveDependencies(Object o) { // 객체 o 는
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
