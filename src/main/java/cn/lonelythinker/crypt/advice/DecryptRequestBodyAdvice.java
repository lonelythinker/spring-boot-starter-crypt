package cn.lonelythinker.crypt.advice;

import cn.lonelythinker.crypt.annotation.Decrypt;
import cn.lonelythinker.crypt.configuration.CryptConfiguration;
import cn.lonelythinker.crypt.util.AesUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

/**
 * 请求数据接收处理类,对有@Decrypt注解的方法的数据进行解密操作
 *
 * @author lonelythinker
 */
@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    private final Logger log = LoggerFactory.getLogger(DecryptRequestBodyAdvice.class);

    @Autowired
    private CryptConfiguration cryptConfiguration;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if (parameter.getMethod().isAnnotationPresent(Decrypt.class) && cryptConfiguration.isEnabled()) {
            try {
                return new DecryptHttpInputMessage(inputMessage, cryptConfiguration.getKey(), cryptConfiguration.getCharset());
            } catch (Exception e) {
                log.error("数据解密失败", e);
            }
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    private class DecryptHttpInputMessage implements HttpInputMessage {
        private Logger log = LoggerFactory.getLogger(DecryptRequestBodyAdvice.class);
        private HttpHeaders headers;
        private InputStream body;

        public DecryptHttpInputMessage(HttpInputMessage inputMessage, String key, String charset) throws Exception {
            if (cryptConfiguration.isCryptHeaders()) {
                long startTime = System.currentTimeMillis();
                HttpHeaders cryptHeaders = inputMessage.getHeaders();
                if (!cryptHeaders.isEmpty()) {
                    cryptHeaders.forEach((k, valueList) -> {
                        if (StringUtils.startsWith(k, cryptConfiguration.getCryptHeadersKeyPrefix())) {
                            if (CollectionUtils.isNotEmpty(valueList)) {
                                cryptHeaders.put(k, valueList.stream().map(value -> {
                                    try {
                                        return AesUtil.decrypt(value, key);
                                    } catch (Exception e) {
                                        log.error("Headers数据解密失败", e);
                                    }
                                    return null;
                                }).collect(Collectors.toList()));
                            }
                        }
                    });
                }
                long endTime = System.currentTimeMillis();
                log.debug("Headers数据解密,解密耗费时间:" + (endTime - startTime));
            } else {
                this.headers = inputMessage.getHeaders();
            }

            if (cryptConfiguration.isCryptBody()) {
                String bodyContent = IOUtils.toString(inputMessage.getBody(), charset);
                String decryptBody = "";
                // JSON 数据格式的不进行解密操作
                if (bodyContent.startsWith("{")) {
                    decryptBody = bodyContent;
                } else {
                    decryptBody = AesUtil.decrypt(bodyContent, key);
                }
                this.body = IOUtils.toInputStream(decryptBody, charset);
            } else {
                this.body = inputMessage.getBody();
            }
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
