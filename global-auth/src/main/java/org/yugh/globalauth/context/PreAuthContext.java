package org.yugh.globalauth.context;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yugh.globalauth.common.constants.Constant;
import org.yugh.globalauth.util.SnowFlakeUtil;
import org.yugh.globalauth.util.WebUtils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yugenhai
 */
@Order(1)
@Component
public class PreAuthContext implements ServletRequestListener {

    /**
     * begin
     * <p>
     * If use track log id , in {@link HttpServletRequest} request.getAttribute(Constant.GLOBAL_RPID)
     * I suggest print Constant.GLOBAL_RPID to log Console
     *
     * @param sre
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        Thread thread = Thread.currentThread();
        String rpid = SnowFlakeUtil.nextWaterFlow().toString();
        thread.setName(rpid);
        WebUtils.setSession(request, Constant.GLOBAL_RPID, rpid);
        LocaleContextHolder.setLocale(request.getLocale());
    }
}
