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
import org.yugh.gateway.config.RedisClient;
import org.yugh.gateway.config.ZuulPropConfig;
import org.yugh.gateway.util.ResultJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
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
    private ZuulPropConfig zuulPropConfig;
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


    /**
     * 路由拦截转发
     *
     * @return
     * @author yugenhai
     * @creation: 2019-06-26 17:50
     */
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestMethod = context.getRequest().getMethod();
        if (Constant.OPTIONS.equals(requestMethod)) {
            log.info("请求的跨域的地址:{}   跨域的方法", request.getServletPath(), requestMethod);
            assemblyCross(context);
            context.setResponseStatusCode(200);
            context.setSendZuulResponse(false);
            return null;
        }
        String readUrl = request.getServletPath().substring(1, request.getServletPath().indexOf('/', 1));
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
            context.setResponseBody(ResultJson.failure(ResultEnum.UNAUTHORIZED, "Url Error, Please Check It").toString());
            return null;
        }
    }



    /**
     * 检查微服务权限
     *
     * @param context
     * @return
     * @author yugenhai
     * @creation: 2019-06-26 17:50
     */
    private Object authToken(RequestContext context) {
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();
        //boolean isLogin = 根据业务判断.isLogined(request, response);
        boolean isLogin = true;
        //是否存在用户或者是否登录
        if (isLogin) {
            try {

                //1:拿到用户信息
                //2:冗余一份、放在上下文里、放缓存一份
                //redisClient.set(user.getNo(), token, 20 * 60L);

            } catch (Exception e) {
                log.error("获取用户信息异常：{}", e.getMessage());
            }

        } else {
            //根据该token查询该用户未登录
            unLogin(request, context);
        }
        return null;
    }


    /**
     * 未登录不路由
     *
     * @param request
     */
    private void unLogin(HttpServletRequest request, RequestContext context) {
        String requestURL = request.getRequestURL().toString();
        String loginUrl = getSsoUrl(request) + "?returnUrl=" + requestURL;
        Map map = new HashMap(2);
        map.put("redirctUrl", loginUrl);
        log.info("检查到该token对应的用户登录状态未登录  跳转到Login页面 : {} ", loginUrl);
        context.setResponseStatusCode(HttpStatusEnum.OK.code());
        assemblyCross(context);
        context.getResponse().setContentType("application/json;charset=UTF-8");
        context.setSendZuulResponse(false);
        context.set("isSuccess", false);
        context.setResponseBody(ResultJson.failure(ResultEnum.UNAUTHORIZED, "This User Not Found, Please Check Token").toString());
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

    /**
     * 拼装跨域处理
     */
    private void assemblyCross(RequestContext ctx) {
        HttpServletResponse response = ctx.getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", ctx.getRequest().getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Methods", "*");
    }



}
