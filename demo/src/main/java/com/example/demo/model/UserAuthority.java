package com.example.demo.model;

public class UserAuthority {
    private String userId;

    private Integer authorityId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public UserAuthority(String userId, Integer authorityId) {
        this.userId = userId;
        this.authorityId = authorityId;
    }
}