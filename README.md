# bean-el
基于Spring EL表达式给bean的属性赋值

# Example
```java

public class User {

    @ElValue("2 + 2")
    private int userId;

    @ElValue("'123'")
    private String password;

    @ElValue("1 == 1")
    private boolean nativeUser;

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isNativeUser() {
        return nativeUser;
    }

    public void setNativeUser(final boolean nativeUser) {
        this.nativeUser = nativeUser;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(userId);
        sb.append(", password='").append(password).append('\'');
        sb.append(", nativeUser=").append(nativeUser);
        sb.append('}');
        return sb.toString();
    }
}
```

## 普通使用
```java
final SpringElBeanProcessor elBeanProcessor = new SpringElBeanProcessor(new SpelExpressionParser());
final User user = new User();
elBeanProcessor.process(user);
```

## 使用Spring AOP 处理方法调用的参数
配置aop
```xml

<aop:config proxy-target-class="true"/>

<bean class="cn.yxffcode.beanel.aop.ElParameterAdvisor">
    <constructor-arg>
        <bean class="cn.yxffcode.beanel.SpringElBeanProcessor"/>
    </constructor-arg>
</bean>

```

在需要处理的方法参数上加@ElBean注解
```java
public class TestDao implements ITestDao {
    @Override public void save(@ElBean User user) {
        System.out.println(user);
    }
}

```

