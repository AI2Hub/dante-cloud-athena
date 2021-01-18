/*
 * Copyright (c) 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud-athena
 * Module Name: eurynome-cloud-athena-kernel
 * File Name: FormAuthenticationFailureHandler.java
 * Author: gengwei.zheng
 * Date: 2021/1/12 下午7:20
 * LastModified: 2020/2/3 下午6:18
 */

package cn.herodotus.eurynome.athena.kernel.service;

import cn.herodotus.eurynome.security.response.exception.VerificationCodeIsEmptyException;
import cn.herodotus.eurynome.security.response.exception.VerificationCodeIsNotExistException;
import cn.herodotus.eurynome.security.response.exception.VerificationCodeIsNotRightException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/26 18:08
 */
@Slf4j
@Component
public class FormLoginAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {

    public static final String ERROR_MESSAGE_KEY = "SPRING_SECURITY_LAST_EXCEPTION_CUSTOM_MESSAGE";
    private static final String LOGIN_URL = "/login";

    private static Map<String, String> exceptionDictionary = new HashMap<>();

    static {
        exceptionDictionary.put("UsernameNotFoundException", "用户名/密码无效");
        exceptionDictionary.put("DisabledException", "用户已被禁用");
        exceptionDictionary.put("AccountExpiredException", "账号已过期");
        exceptionDictionary.put("CredentialsExpiredException", "凭证已过期");
        exceptionDictionary.put("BadCredentialsException", "用户名/密码无效");
        exceptionDictionary.put("VerificationCodeIsEmptyException", "请输入验证码！");
        exceptionDictionary.put("VerificationCodeIsNotExistException", "验证码不存在！请刷新重试");
        exceptionDictionary.put("VerificationCodeIsNotRightException", "验证码输入错误！");
    }

    @PostConstruct
    public void postConstruct() {
        Map<String, String> exceptionMappings = new HashMap<>(8);
        exceptionMappings.put(UsernameNotFoundException.class.getName(), LOGIN_URL);
        exceptionMappings.put(DisabledException.class.getName(), LOGIN_URL);
        exceptionMappings.put(AccountExpiredException.class.getName(), LOGIN_URL);
        exceptionMappings.put(CredentialsExpiredException.class.getName(), LOGIN_URL);
        exceptionMappings.put(BadCredentialsException.class.getName(), LOGIN_URL);
        exceptionMappings.put(VerificationCodeIsEmptyException.class.getName(), LOGIN_URL);
        exceptionMappings.put(VerificationCodeIsNotExistException.class.getName(), LOGIN_URL);
        exceptionMappings.put(VerificationCodeIsNotRightException.class.getName(), LOGIN_URL);
        this.setExceptionMappings(exceptionMappings);
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        String errorMessage = "请刷新重试！";

        String exceptionName = e.getClass().getSimpleName();
        if (StringUtils.isNotEmpty(exceptionName)) {
            if (exceptionDictionary.containsKey(exceptionName)) {
                errorMessage = exceptionDictionary.get(exceptionName);
            } else {
                log.warn("[Luban] |- Form Login Authentication Failur eHandler,  Can not find the exception name [{}] in dictionary, please do optimize ", exceptionName);
            }
        }

        if (this.isUseForward()) {
            httpServletRequest.setAttribute(ERROR_MESSAGE_KEY, errorMessage);
        } else {
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null || this.isAllowSessionCreation()) {
                httpServletRequest.getSession().setAttribute(ERROR_MESSAGE_KEY, errorMessage);
            }
        }

        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
