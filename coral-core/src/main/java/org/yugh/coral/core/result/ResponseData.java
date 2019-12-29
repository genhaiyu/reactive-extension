package org.yugh.coral.core.result;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.yugh.coral.core.common.exception.GlobalException;

import java.io.Serializable;

/**
 * @param <T>
 * @author yugenhai
 */
@Slf4j
public final class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 2053742054576495115L;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 状态码
     */
    private String code;

    /**
     * 返回附加信息, java对象。可以是String、List、JSON、Map 类型
     */
    private T data;
    /**
     * 指示客户端是否显示message信息
     */
    private Boolean showMessage = false;
    /**
     * 用户显示信息
     */
    private String showMessageApp;

    /**
     * App message
     */
    private String msg;
    /**
     * App状态码
     **/
    private Integer status = 0;

    /**
     * 生成数据的处理器
     *
     * @param <D>泛型对象
     */
    public interface BusinessProcessor<D> {
        /**
         * 处理业务逻辑，组装返回数据的内容
         *
         * @return D 返回数据的泛型
         */
        D process();
    }

    /**
     * 结果构建
     */
    public static <D> ResponseData<D> build(BusinessProcessor<D> processor) {

        ResponseData<D> ResponseData = new ResponseData<>();
        try {
            D data = processor.process();
            ResponseData.setCode(ResultCode.SUCCESS.code);
            ResponseData.setMessage(ResultCode.SUCCESS.message);
            ResponseData.setShowMessageApp(ResultCode.SUCCESS.message);
            ResponseData.setMsg(ResultCode.APP_SUCCESS.message);
            ResponseData.setStatus(0);
            ResponseData.setData(data);
        } catch (GlobalException re) {
            log.error(re.getMessage());
            ResponseData.setCode(re.getCode() == null ? ResultCode.ERROR.code : re.getCode().code);
            ResponseData.setStatus(re.getCode() == null ? Integer.valueOf(ResultCode.ERROR.code) : NumberUtils.isNumber(re.getCode().code) ? Integer.valueOf(re.getCode().code) : -1);
            ResponseData.setMessage(re.getMessage());
            //修改showMessageApp
            ResponseData.setShowMessageApp(re.getMessage() == null ? ResultCode.getResultCodeByCode(ResponseData.getCode()).getMessage() : re.getMessage());
            ResponseData.setMsg(re.getMessage() == null ? ResultCode.getResultCodeByCode(ResponseData.getCode()).getMessage() : re.getMessage());
            if (re.getShowMessage() != null) {
                //指示客户都是否显示异常 message 信息
                ResponseData.setShowMessage(re.getShowMessage());
            }
            ResponseData.setStatus(999999);
        } catch (HystrixRuntimeException re) {
            log.error("\n ResponseBody HystrixRuntimeException :", re.getMessage());
            ResponseData.setCode(ResultCode.ERROR_MICROSERVICES.code);
            ResponseData.setStatus(Integer.valueOf(ResultCode.ERROR_MICROSERVICES.code));
            ResponseData.setMessage(ResultCode.ERROR_MICROSERVICES.getMessage());
            ResponseData.setShowMessageApp(ResultCode.ERROR_APP_COMMON.message);
            ResponseData.setMsg(ResultCode.ERROR_APP_COMMON.message);
            ResponseData.setStatus(999999);
        } catch (Exception e) {
            log.error("\n ResponseBody RuntimeException:", e);
            ResponseData.putResultCode(ResultCode.ERROR);
            ResponseData.setMessage(e.getMessage());
            ResponseData.setShowMessageApp(ResultCode.ERROR_APP_COMMON.message);
            ResponseData.setMsg(ResultCode.ERROR_APP_COMMON.message);
            ResponseData.setStatus(999999);
        }
        return ResponseData;
    }

    public ResponseData() {
        this.putResultCode(ResultCode.SUCCESS);
    }

    public String getShowMessageApp() {
        return showMessageApp;
    }

    public void setShowMessageApp(String showMessageApp) {
        this.showMessageApp = showMessageApp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(Boolean showMessage) {
        this.showMessage = showMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void putResultCode(ResultCode resultCode) {
        if (ResultCode.SUCCESS.code.equals(resultCode.getCode())) {
            this.setShowMessage(false);
        } else {
            this.setShowMessage(true);
        }
        this.setCode(resultCode.getCode());
        this.setShowMessageApp(resultCode.getMessage());
    }
}
