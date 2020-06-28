### Shiro学习

#### 认证和记住我
- subject.isAuthenticated() ：表示用户进行了身份验证登录的， 即使有 Subject.login 进行了登录；
- subject.isRemembered()：表示用户是通过记住我登录的， 此时可能并不是真正的你（如你的朋友使用你的电脑，或者 你的cookie 被窃取）在访问的。
> 两者二选一，即 subject.isAuthenticated()==true，则 subject.isRemembered()==false；反之一样。

