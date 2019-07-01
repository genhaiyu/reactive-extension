package org.yugh.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.yugh.gateway.common.constants.Constant;
import org.yugh.gateway.common.enums.DeployEnum;
import org.yugh.gateway.common.enums.HttpStatusEnum;
import org.yugh.gateway.common.enums.ResultEnum;
import org.yugh.gateway.config.AuthPropConfig;
import org.yugh.gateway.config.RedisClient;
import org.yugh.gateway.util.ResultJson;
import org.yugh.gateway.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    private AuthPropConfig authPropConfig;
    @Autowired
    private RedisClient redisClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * 部署级别可调控
     *
     * @return
     * @author yugenhai
     * @creation: 2019-06-26 17:50
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        if (activeType.equals(DeployEnum.DEV.getType())) {
            log.info("请求地址 ： {}      当前环境 ：{} ", request.getServletPath(), DeployEnum.DEV.getType());
            return true;
        } else if (activeType.equals(DeployEnum.TEST.getType())) {
            log.info("请求地址 ： {}      当前环境 ：{} ", request.getServletPath(), DeployEnum.TEST.getType());
            return true;
        } else if (activeType.equals(DeployEnum.PROD.getType())) {
            log.info("请求地址 ： {}      当前环境 ：{} ", request.getServletPath(), DeployEnum.PROD.getType());
            return true;
        }
        return true;
    }


    /**
     * 路由拦截
     *
     * @return
     * @author yugenhai
     * @creation: 2019-06-26 17:50
     */
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String readUrl = WebUtil.getRequestURI(request);
        try {
            //白名单直接访问
            if (!StringUtils.isEmpty(authPropConfig.getExcludeUrls())) {
                for (String url : authPropConfig.getExcludeUrls()) {
                    if (url.contains(readUrl)) {
                        log.info("请求链接白名单 ： {}      直接跳过 ：{} ", url, request.getServletPath());
                        return null;
                    }
                }
            }
            //异常情况不路由
            if (request.getServletPath().length() <= Constant.PATH_LENGTH || authPropConfig.getApiUrlMap().size() == 0) {
                return null;
            }
            //链接打过来实例名
            String serverName = request.getServletPath().substring(1, request.getServletPath().indexOf('/', 1));
            log.info("请求路径 ： {}       实例名 ： {} ", request.getServletPath(), serverName);
            //取到实例名
            for (String key : authPropConfig.getApiUrlMap().keySet()) {
                log.info("路由已配置微服务 Instance IDs ：{} ", key);
            }
            // 1:检查白名单微服务名
            // 2:获取到微服务的别名级别
            if (authPropConfig.getApiUrlMap().containsKey(serverName)) {
                authToken(context, authPropConfig.getApiUrlMap().get(serverName));
                return null;
            } else {
                //如果不在白名单中,拒绝访问
                log.info("路由请求地址 ：{}  请求被拒绝, 不允许直接访问底层方法 ", request.getServletPath());
                noSignIn(request, context);
                return null;
            }

        } catch (Exception e) {
            log.info("gateway路由器请求异常 ：{}  请求被拒绝 ", e.getMessage());
            context.setResponseStatusCode(HttpStatusEnum.UNAUTHORIZED.code());
            context.setResponseBody(ResultJson.failure(ResultEnum.FORBIDDEN,("Url Error, Please Check It")).toString());
            context.setSendZuulResponse(false);
            return null;
        }
    }


    /**
     * 检查微服务权限
     *
     * @param context
     * @param authUserType
     * @return
     * @author yugenhai
     * @creation: 2019-06-26 17:50
     */
    private Object authToken(RequestContext context, int authUserType) {
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        //boolean isLogin = 根据业务判断.isLogined(request, response);
        boolean isLogin = true;
        if (isLogin) {
            //登录状态
            try {

                //1:拿到用户信息
                //2:冗余一份、放在上下文里、放缓存一份
                //redisClient.set(user.getNo(), token, 20 * 60L);

            } catch (Exception e) {
                log.error("获取用户信息异常：{}", e.getMessage());
            }

        } else {
            //根据该token查询该用户未登录
            noSignIn(request, context);
        }
        return null;
    }


    /**
     * 未登录不路由
     *
     * @param request
     */
    private void noSignIn(HttpServletRequest request, RequestContext context) {
        String requestURL = request.getRequestURL().toString();
        String loginUrl = getSsoUrl(request) + "?returnUrl=" + requestURL;
        Map map = new HashMap(2);
        map.put("redirctUrl", loginUrl);
        log.info("检查到该token对应的用户登录状态未登录  跳转到Login页面 ： {} ", loginUrl);
        context.setResponseStatusCode(HttpStatusEnum.UNAUTHORIZED.code());
        context.setResponseBody(ResultJson.failure(ResultEnum.UNAUTHORIZED,("This User Not Login, Please Check Token")).toString());
        context.setSendZuulResponse(false);
    }


    /**
     * 重定向前缀拼接
     *
     * @param request
     * @return
     */
    private String getSsoUrl(HttpServletRequest request) {
        String serverName = request.getServerName();
        if (StringUtils.isEmpty(serverName)) {
            return "https://github.com/yugenhai108";
        }
        return "https://github.com/yugenhai108";

    }


}
