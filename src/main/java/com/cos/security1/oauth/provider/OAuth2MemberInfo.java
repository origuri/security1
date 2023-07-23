package com.cos.security1.oauth.provider;

/*
* 각 api를 사용할 때마다 들어오는 map의 키값이 다를 수 있으므로 인터페이스를
* 사용해서 각각 구현해줌.
* */
public interface OAuth2MemberInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
