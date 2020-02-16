package org.yugh.coral.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.yugh.coral.auth.common.enums.DeployEnum;
import org.yugh.coral.core.common.constant.StringPool;
import org.yugh.coral.core.result.ResultEnum;
import org.yugh.coral.core.result.ResultJson;
import org.yugh.coral.zuul.config.ZuulPropConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * //路由拦截转发请求
 *
 * @author: 余根海
 * @creation: 2019-06-26 17:50
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
public class PreAuthFilter extends ZuulFilter {


    @Value("${spring.profiles.active}")
    private String activeType;
    @Autowired
    private ZuulPropConfig zuulPropConfig;

    private static final String OPTIONS = "OPTIONS";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }


    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        if (activeType.equals(DeployEnum.DEV.getType())) {
            log.info("请求地址 : {}      当前环境  : {} ", request.getServletPath(), DeployEnum.DEV.getType());
            return true;
        } else if (activeType.equals(DeployEnum.TEST.getType())) {
            log.info("请求地址 : {}      当前环境  : {} ", request.getServletPath(), DeployEnum.TEST.getType());
            return true;
        } else if (activeType.equals(DeployEnum.PROD.getType())) {
            log.info("请求地址 : {}      当前环境  : {} ", request.getServletPath(), DeployEnum.PROD.getType());
            return true;
        }
        return true;
    }


    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestMethod = context.getRequest().getMethod();
        //判断请求方式
        if (OPTIONS.equals(requestMethod)) {
            log.info("请求的跨域的地址 : {}   跨域的方法", request.getServletPath(), requestMethod);
            assemblyCross(context);
            context.setResponseStatusCode(HttpStatus.OK.value());
            context.setSendZuulResponse(false);
            return null;
        }
        //转发信息共享 其他服务不要依赖MVC拦截器，或重写拦截器
        if (isIgnore(request, this::exclude, this::checkLength)) {
            String token = getCookieBySso(request);
            if (!StringUtils.isEmpty(token)) {
                //context.addZuulRequestHeader(JwtUtil.HEADER_AUTH, token);
            }
            log.info("请求白名单地址 : {} ", request.getServletPath());
            return null;
        }
        String serverName = request.getServletPath().substring(1, request.getServletPath().indexOf('/', 1));
        String authUserType = zuulPropConfig.getApiUrlMap().get(serverName);
        log.info("实例服务名: {}  对应用户类型: {}", serverName, authUserType);
        if (!StringUtils.isEmpty(authUserType)) {
            //用户是否合法和登录
            authToken(context);
        } else {
            //下线前删除配置的实例名
            log.info("实例服务: {}  不允许访问", serverName);
            unauthorized(context, HttpStatus.FORBIDDEN.value(), "请求的服务已经作废,不可访问");
        }
        return null;

        /*String readUrl = request.getServletPath().substring(1, request.getServletPath().indexOf('/', 1));
        try {
            if (request.getServletPath().length() <= Constant.PATH_LENGTH || zuulPropConfig.getRoutes().size() == 0) {
                throw new Exception();
            }
            Iterator<Map.Entry<String,String>> zuulMap = zuulPropConfig.getRoutes().entrySet().iterator();
            while(zuulMap.hasNext()){
                Map.Entry<String, String> entry = zuulMap.next();
                String routeValue = entry.getValue();
                if(routeValue.startsWith(Constant.ZUUL_PREFIX)){
                    routeValue = routeValue.substring(1, routeValue.indexOf('/', 1));
                }
                if(routeValue.contains(readUrl)){
                    log.info("请求白名单地址 : {}     请求跳过的真实地址  :{} ", routeValue, request.getServletPath());
                    return null;
                }
            }
            log.info("即将请求登录 : {}       实例名 : {} ", request.getServletPath(), readUrl);
            authToken(context);
            return null;
        } catch (Exception e) {
            log.info("gateway路由器请求异常 :{}  请求被拒绝 ", e.getMessage());
            assemblyCross(context);
            context.set("isSuccess", false);
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatusEnum.OK.code());
            context.getResponse().setContentType("application/json;charset=UTF-8");
            context.setResponseBody(JsonUtils.toJson(JsonResult.buildErrorResult(HttpStatusEnum.UNAUTHORIZED.code(),"Url Error, Please Check It")));
            return null;
        }
        */
    }


    private Object authToken(RequestContext context) {
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        /*boolean isLogin = sessionManager.isLogined(request, response);
        //用户存在
        if (isLogin) {
            try {
                User user = sessionManager.getUser(request);
                log.info("用户存在 : {} ", JsonUtils.toJson(user));
               // String token = userAuthUtil.generateToken(user.getNo(), user.getUserName(), user.getRealName());
                log.info("根据用户生成的Token :{}", token);
                //转发信息共享
               // context.addZuulRequestHeader(JwtUtil.HEADER_AUTH, token);
                //缓存 后期所有服务都判断
                redisClient.set(user.getNo(), token, 20 * 60L);
                //冗余一份
                userService.syncUser(user);
            } catch (Exception e) {
                log.error("调用SSO获取用户信息异常 :{}", e.getMessage());
            }
        } else {
            //根据该token查询该用户不存在
            unLogin(request, context);
        }*/
        return null;

    }


    private void unLogin(HttpServletRequest request, RequestContext context) {
        String requestURL = request.getRequestURL().toString();
        String loginUrl = getSsoUrl(request) + "?returnUrl=" + requestURL;
        //Map map = new HashMap(2);
        //map.put("redirctUrl", loginUrl);
        log.info("检查到该token对应的用户登录状态未登录  跳转到Login页面 : {} ", loginUrl);
        assemblyCross(context);
        context.getResponse().setContentType("application/json;charset=UTF-8");
        context.set("isSuccess", false);
        context.setSendZuulResponse(false);
        //context.setResponseBody(ResultJson.failure(map, "This User Not Found, Please Check Token").toString());
        context.setResponseStatusCode(HttpStatus.OK.value());
    }


    private boolean isIgnore(HttpServletRequest request, Function<HttpServletRequest, Boolean>... functions) {
        return Arrays.stream(functions).anyMatch(f -> f.apply(request));
    }

    private boolean exclude(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (!CollectionUtils.isEmpty(zuulPropConfig.getExcludeUrls())) {
            return zuulPropConfig.getPatterns().stream()
                    .map(pattern -> pattern.matcher(servletPath))
                    .anyMatch(Matcher::find);
        }
        return false;
    }


    private boolean checkLength(HttpServletRequest request) {
        return request.getServletPath().length() <= StringPool.YES.length() || CollectionUtils.isEmpty(zuulPropConfig.getApiUrlMap());
    }


    private String getCookieBySso(HttpServletRequest request) {
        Cookie cookie = this.getCookieByName(request, "");
        return cookie != null ? cookie.getValue() : null;
    }


    private void unauthorized(RequestContext ctx, int code, String msg) {
        assemblyCross(ctx);
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(ResultJson.failure(ResultEnum.UNAUTHORIZED, msg).toString());
        ctx.set("isSuccess", false);
        ctx.setResponseStatusCode(HttpStatus.OK.value());
    }


    private Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = new HashMap(16);
        Cookie[] cookies = request.getCookies();
        if (!StringUtils.isEmpty(cookies)) {
            Cookie[] c1 = cookies;
            int length = cookies.length;
            for (int i = 0; i < length; ++i) {
                Cookie cookie = c1[i];
                cookieMap.put(cookie.getName(), cookie);
            }
        } else {
            return null;
        }
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        }
        return null;
    }


    private String getSsoUrl(HttpServletRequest request) {
        String serverName = request.getServerName();
        if (StringUtils.isEmpty(serverName)) {
            return "https://github.com/yugenhai";
        }
        return "https://github.com/yugenhai";

    }

    private void assemblyCross(RequestContext ctx) {
        HttpServletResponse response = ctx.getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", ctx.getRequest().getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Methods", "*");
    }


}
