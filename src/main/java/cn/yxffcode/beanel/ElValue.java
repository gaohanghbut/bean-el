package cn.yxffcode.beanel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识属性,指定赋值的el表达式
 *
 * @author gaohang on 16/8/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElValue {

    /**
     * el表达式
     */
    String value();

    /**
     * 如果属性已有值,是否覆盖,默认不覆盖
     */
    boolean override() default false;
}
