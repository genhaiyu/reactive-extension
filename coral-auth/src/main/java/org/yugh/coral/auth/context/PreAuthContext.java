package org.yugh.coral.auth.context;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.yugh.coral.auth.util.SnowFlakeUtil;
import org.yugh.coral.auth.util.StringPool;
import org.yugh.coral.auth.util.WebUtils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 放入rpid
 * <p>
 * 生成规则见 {@link SnowFlakeUtil}
 *
 * @author yugenhai
 */
@Component
public class PreAuthContext implements ServletRequestListener {

    /**
     * 初始化监听器时放入rpid作为当前线程名
     *
     * @param sre
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        Thread thread = Thread.currentThread();
        String rpid = SnowFlakeUtil.nextWaterFlow().toString();
        thread.setName(rpid);
        WebUtils.setSession(request, StringPool.GLOBAL_RPID, rpid);
        LocaleContextHolder.setLocale(request.getLocale());
    }
}
