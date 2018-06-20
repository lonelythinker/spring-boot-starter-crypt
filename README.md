# spring-boot-starter-crypt
SpringBoot请求统一加解密


使用aes对称加密方式对于传输报文data进行整体加解密
使用rsa对aes 的加密key进行加密
ras公私钥动态获取：在客户端每次启动重新获取公私钥
