package ankoushinist.SKSKdroid.confuser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WarningMessage {
    String value();
}
