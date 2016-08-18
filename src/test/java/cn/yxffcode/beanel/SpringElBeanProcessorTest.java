package cn.yxffcode.beanel;

import cn.yxffcode.beanel.test.ITestDao;
import cn.yxffcode.beanel.test.User;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author gaohang on 16/8/15.
 */
public class SpringElBeanProcessorTest {

    @Test
    public void testProcess() throws Exception {
        final SpringElBeanProcessor elBeanProcessor = new SpringElBeanProcessor(new SpelExpressionParser());
        final User user = new User();
        elBeanProcessor.process(user);
        System.out.println(user);
    }

    @Test
    public void testSpringAop() throws Exception {
        final ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring.xml");
        final ITestDao ITestDao = ctx.getBean(ITestDao.class);
        ITestDao.save(new User());
    }

}
