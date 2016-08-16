package cn.yxffcode.beanel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Field;

/**
 * @author gaohang on 16/8/15.
 */
public class SpringElBeanProcessor implements ElBeanProcessor {

    private final ExpressionParser expressionParser;

    public SpringElBeanProcessor() {
        this(new SpelExpressionParser());
    }

    public SpringElBeanProcessor(final ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Override public void process(final Object obj) {
        if (obj == null) {
            return;
        }
        Class<?> type = obj.getClass();
        try {
            while (type != Object.class) {
                final Field[] declaredFields = type.getDeclaredFields();

                for (Field field : declaredFields) {
                    final ElValue elValue = field.getAnnotation(ElValue.class);
                    if (elValue != null) {
                        final Expression expression = expressionParser.parseExpression(elValue.value());
                        final Object value = expression.getValue();
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(obj, value);
                        continue;
                    }
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    final Object value = field.get(obj);
                    if (value != null) {
                        process(value);
                    }
                }

                type = type.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
