package cn.yxffcode.beanel.test;

import cn.yxffcode.beanel.aop.ElBean;

/**
 * @author gaohang on 16/8/16.
 */
public class TestDao implements ITestDao {
    @Override public void save(@ElBean User user) {
        System.out.println(user);
    }
}
