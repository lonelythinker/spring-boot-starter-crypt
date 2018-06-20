package cn.lonelythinker.crypt.annotation;

import java.lang.annotation.*;

/**
 * 加密注解
 *
 * @author lonelythinker
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
}
