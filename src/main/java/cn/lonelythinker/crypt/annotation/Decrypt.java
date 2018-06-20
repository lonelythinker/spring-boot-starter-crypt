package cn.lonelythinker.crypt.annotation;

import java.lang.annotation.*;

/**
 * 解密注解
 *
 * @author lonelythinker
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {
}
