package io.ssafy.soupapi.global.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public abstract class OAuth2RequestProcessor extends DefaultOAuth2UserService {

    private final OAuth2UserRequest oAuth2UserRequest;
    private final OAuth2User oAuth2User;
    private final String registrationId;

    OAuth2RequestProcessor(OAuth2UserRequest oAuth2UserRequest){
        this.oAuth2UserRequest = oAuth2UserRequest;
        this.registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        this.oAuth2User = loadUser(oAuth2UserRequest);
    }

    abstract String getEmail(Map<String, Object> userAttributes);

    abstract String getNickname(Map<String, Object> userAttributes);
    abstract String getProfileImageUrl(Map<String, Object> userAttributes);

    public Map<String, Object> getSocialUserAttributes(){
        Map<String, Object> userAttributes = new HashMap<>();
        Map<String, Object> oAuth2Attributes = oAuth2User.getAttributes();

        userAttributes.put("platform", this.registrationId);
        userAttributes.put("email", getEmail(oAuth2Attributes));
        userAttributes.put("nickname", getNickname(oAuth2Attributes));
        userAttributes.put("profileImageUrl", getProfileImageUrl(oAuth2Attributes));
        userAttributes.put(loadUserNameAttributeName(), oAuth2User.getName());

        return userAttributes;
    }

    public String loadUserNameAttributeName(){
        return oAuth2UserRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
    }

}
