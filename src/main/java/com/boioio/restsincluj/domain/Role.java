package com.boioio.restsincluj.domain;

import java.util.Objects;

public class Role extends AbstractModel {
    private long user_id;
    private long role_id;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return user_id == role.user_id &&
                role_id == role.role_id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(user_id, role_id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "user_id=" + user_id +
                ", role_id=" + role_id +
                '}';
    }
}
