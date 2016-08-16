package cn.yxffcode.beanel;

import cn.yxffcode.beanel.aop.ElParameterAdvisor;
import cn.yxffcode.beanel.test.ITestDao;
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
        final cn.yxffcode.beanel.test.Test user = new cn.yxffcode.beanel.test.Test();
        elBeanProcessor.process(user);
        System.out.println(user);
    }

    @Test
    public void testSpringAop() throws Exception {
        final ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring.xml");
        final ElParameterAdvisor elParameterAdvisor = ctx.getBean(ElParameterAdvisor.class);
        System.out.println(elParameterAdvisor);
        final ITestDao ITestDao = ctx.getBean(ITestDao.class);
        ITestDao.save(new cn.yxffcode.beanel.test.Test());
    }

}
