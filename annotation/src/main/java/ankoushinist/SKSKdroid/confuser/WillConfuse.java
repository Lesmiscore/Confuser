package ankoushinist.SKSKdroid.confuser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface WillConfuse {
    int modifier() default Modifier.PUBLIC;
    int nest() default -1;
    String value();
    boolean printNest() default false;
    String[] constructors() default {};
    int constructorsModifier() default Modifier.PUBLIC;
}
