package cn.lonelythinker.crypt.advice;

import cn.lonelythinker.crypt.annotation.Encrypt;
import cn.lonelythinker.crypt.configuration.CryptConfiguration;
import cn.lonelythinker.crypt.util.AesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 请求响应处理类,对有@Encrypt注解的方法的返回数据进行加密操作
 *
 * @author lonelythinker
 */
@ControllerAdvice

public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(EncryptResponseBodyAdvice.class);

    @Autowired
    private CryptConfiguration cryptConfiguration;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.getMethod().isAnnotationPresent(Encrypt.class) && cryptConfiguration.isEnabled()) {
            try {
                String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
                if (StringUtils.isNotBlank(cryptConfiguration.getKey())) {
                    long startTime = System.currentTimeMillis();
                    String result = AesUtil.encrypt(content, cryptConfiguration.getKey());
                    long endTime = System.currentTimeMillis();
                    log.debug("Body数据加密,加密耗费时间:" + (endTime - startTime));
                    return result;
                } else {
                    throw new NullPointerException("请配置参数lonelythinker.crypt.key");
                }
            } catch (Exception e) {
                log.error("数据加密失败", e);
            }
            return null;
        } else {
            return body;
        }
    }
}
