package com.cos.security1.oauth.provider;

import java.util.Map;

public class GoogleMemberInfo implements OAuth2MemberInfo {

    // oauth2User.getAttributes; 를 받는 필드
    private Map<String, Object> attributes;

    public GoogleMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
