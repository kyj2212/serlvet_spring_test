package com.yejin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@AllArgsConstructor
public class RouteInfo {
    @Getter
    private String path;
    @Getter // 강사님은 안했던거 같은데? 확인해보기
    private String actionPath;
    @Getter
    private Class controllerCls;
    @Getter
    private Method method;


}