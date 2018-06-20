package cn.lonelythinker.crypt.annotation;

import cn.lonelythinker.crypt.configuration.CryptAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用加解密
 *
 * <p>在Spring Boot启动类上加上此注解<p>
 *
 * <pre class="code">
 * &#064;SpringBootApplication
 * &#064;EnableCrypt
 * public class App {
 *     public static void main(String[] args) {
 *         SpringApplication.run(App.class, args);
 *     }
 * }
 * <pre>
 *
 * @author lonelythinker
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CryptAutoConfiguration.class})
public @interface EnableCrypt {
}
