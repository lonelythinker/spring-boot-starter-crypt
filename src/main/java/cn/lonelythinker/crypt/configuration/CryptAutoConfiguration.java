package cn.lonelythinker.crypt.configuration;

import cn.lonelythinker.crypt.advice.DecryptRequestBodyAdvice;
import cn.lonelythinker.crypt.advice.EncryptResponseBodyAdvice;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 加解密自动配置
 *
 * @author lonelythinker
 */
@Configuration
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties(CryptConfiguration.class)
public class CryptAutoConfiguration {

    /**
     * 配置请求加密
     *
     * @return
     */
    @Bean
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice() {
        return new EncryptResponseBodyAdvice();
    }

    /**
     * 配置请求解密
     *
     * @return
     */
    @Bean
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice() {
        return new DecryptRequestBodyAdvice();
    }
}
