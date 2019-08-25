package com.foxploit.ignio.userinfoservice.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String roleName;
    private String description;

    public Role(String id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}