package com.raota.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터에 @LoginUser 어노테이션이 붙어있고, 타입이 Long인지 확인
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(LoginMember.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 개발 편의성을 위해 헤더에서 ID를 가져옴
        String userId = request.getHeader("X-User-Id");

        if (userId != null) {
            return Long.valueOf(userId);
        }

        // 헤더가 없으면 기본 테스트 유저 ID 반환
        return 1L;
    }
}
