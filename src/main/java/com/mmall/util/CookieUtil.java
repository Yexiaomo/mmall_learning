package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.swing.StringUIClientPropertyKey;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    private final  static String COOKIE_DOMAIN = ".mmall.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    /**
     * 从request当中读取cookie，判断是否存在cookieName = mmall_login_token的cookie
     *
     * @param request request
     * @return 存在则将其返回，不存在返回null
     */
    public static String readLoginToken(HttpServletRequest request){
        final Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                log.info("read cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 存入cookie，cookie过期时间为60 * 60 * 24 * 365
     *
     * @param response response
     * @param token    token
     */
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        log.info("write cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
        response.addCookie(cookie);
    }

    /**
     * 删除cookie,查看request当中的cookie存在name为mmall_login_token的cookie，则将过期时间maxAge设置为0<br>
     * maxAge=0染回给浏览器，浏览器则会将其删除
     *
     * @param request  request
     * @param response response
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response){
        final Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME));
                cookie.setDomain(COOKIE_DOMAIN);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                log.info("del cookieName:{}, cookieVal:{}", cookie.getName(), cookie.getValue());
                response.addCookie(cookie);
                return;
            }
        }
    }
}
