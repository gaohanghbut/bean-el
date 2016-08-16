package cn.yxffcode.beanel.aop;

import cn.yxffcode.beanel.ElBeanProcessor;
import cn.yxffcode.beanel.SpringElBeanProcessor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 通过spring-aop为参数自动执行EL处理
 *
 * @author gaohang on 16/8/16.
 */
public class ElParameterAdvisor extends AbstractPointcutAdvisor {

    private final ElBeanProcessor elBeanProcessor;

    public ElParameterAdvisor() {
        this(new SpringElBeanProcessor());
    }

    public ElParameterAdvisor(final ElBeanProcessor elBeanProcessor) {
        this.elBeanProcessor = elBeanProcessor;
    }

    @Override public Pointcut getPointcut() {
        return new ElParameterAdvisorPointcut();
    }

    @Override public Advice getAdvice() {
        return new ElParameterAdvisorAdvice(elBeanProcessor);
    }

    @Override public boolean isPerInstance() {
        return false;
    }

    public static final class ElParameterAdvisorPointcut implements Pointcut {

        @Override public ClassFilter getClassFilter() {
            return new ClassFilter() {
                @Override public boolean matches(final Class<?> clazz) {
                    final Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (matchMethod(method)) {
                            return true;
                        }
                    }
                    return false;
                }
            };
        }

        private boolean matchMethod(final Method method) {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (parameterAnnotations == null || parameterAnnotations.length == 0) {
                return false;
            }
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                if (parameterAnnotation == null || parameterAnnotation.length == 0) {
                    continue;
                }
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof ElBean) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override public MethodMatcher getMethodMatcher() {
            return new MethodMatcher() {
                @Override public boolean matches(final Method method, final Class<?> targetClass) {
                    return matchMethod(method);
                }

                @Override public boolean isRuntime() {
                    return false;
                }

                @Override public boolean matches(final Method method, final Class<?> targetClass, final Object[] args) {
                    return matches(method, targetClass);
                }
            };
        }
    }

    public static final class ElParameterAdvisorAdvice implements MethodInterceptor {
        private final ElBeanProcessor elBeanProcessor;

        public ElParameterAdvisorAdvice(final ElBeanProcessor elBeanProcessor) {
            this.elBeanProcessor = elBeanProcessor;
        }

        @Override public Object invoke(final MethodInvocation invocation) throws Throwable {
            final Method method = invocation.getMethod();
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            if (parameterAnnotations == null || parameterAnnotations.length == 0) {
                return invocation.proceed();
            }

            outer:
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] parameterAnnotation = parameterAnnotations[i];
                if (invocation.getArguments()[i] == null ||
                        parameterAnnotation == null || parameterAnnotation.length == 0) {
                    continue;
                }
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof ElBean) {
                        elBeanProcessor.process(invocation.getArguments()[i]);
                        continue outer;
                    }
                }
            }

            return invocation.proceed();
        }
    }
}
