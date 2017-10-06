package com.nao20010128nao.confuser;

import java.lang.annotation.*;
import java.lang.reflect.*;

@Target({ElementType.CONSTRUCTOR,ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface NestedOverriden {
    int modifier() default Modifier.PUBLIC;
    boolean returnRequired() default false;
}
