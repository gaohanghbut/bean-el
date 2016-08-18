# bean-el
基于Spring EL表达式给bean的属性赋值

# Example

在需要被EL赋值的属性上打@ElValue注解,指定EL表达式
```java

public class User {

    @ElValue("2 + 2")
    private int userId;

    @ElValue("'123'")
    private String password;

    @ElValue("1 == 1")
    private boolean nativeUser;
    //getters and setters
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

