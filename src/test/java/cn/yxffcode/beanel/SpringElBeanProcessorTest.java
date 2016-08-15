package cn.yxffcode.beanel;

import org.junit.Test;
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

    @ElBean
    public static final class User {

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
}
