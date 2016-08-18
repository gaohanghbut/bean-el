package cn.yxffcode.beanel.test;

import cn.yxffcode.beanel.ElValue;

/**
 * @author gaohang on 16/8/16.
 */
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
