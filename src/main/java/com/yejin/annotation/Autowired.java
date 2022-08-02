package com.yejin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 이 어노테이션은 필드에 붙는거다.
@Retention(RetentionPolicy.RUNTIME) // 런타임에서 찾을 수 있어야 한다.
public @interface Autowired {
}
