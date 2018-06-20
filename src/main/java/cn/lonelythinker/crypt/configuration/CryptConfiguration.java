package cn.lonelythinker.crypt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加解密配置
 *
 * @author lonelythinker
 */
@ConfigurationProperties(prefix = "lonelythinker.crypt")
public class CryptConfiguration {

    /**
     * 加解密的KEY
     */
    private String key;

    /**
     * 启停,停止模式下不做加解密
     */
    private boolean enabled = false;

    /**
     * 加解密Headers,默认true
     */
    private boolean cryptHeaders = true;


    /**
     * 加解密Headers的key前缀,默认"crypt-"
     */
    private String cryptHeadersKeyPrefix = "crypt-";

    /**
     * 加解密Body,默认true
     */
    private boolean cryptBody = true;

    /**
     * 字符集,默认UTF-8
     */
    private String charset = "UTF-8";

    /**
     * 签名过期时间(分钟),默认10分钟
     */
    private Long signExpireTime = 10L;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isCryptHeaders() {
        return cryptHeaders;
    }

    public void setCryptHeaders(boolean cryptHeaders) {
        this.cryptHeaders = cryptHeaders;
    }

    public String getCryptHeadersKeyPrefix() {
        return cryptHeadersKeyPrefix;
    }

    public void setCryptHeadersKeyPrefix(String cryptHeadersKeyPrefix) {
        this.cryptHeadersKeyPrefix = cryptHeadersKeyPrefix;
    }

    public boolean isCryptBody() {
        return cryptBody;
    }

    public void setCryptBody(boolean cryptBody) {
        this.cryptBody = cryptBody;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Long getSignExpireTime() {
        return signExpireTime;
    }

    public void setSignExpireTime(Long signExpireTime) {
        this.signExpireTime = signExpireTime;
    }
}
