package org.yugh.coral.boot.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.yugh.coral.core.common.exception.GlobalException;

import java.io.IOException;

/**
 * @author yugenhai
 */
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    @Nullable
    public boolean hasError(@Nullable ClientHttpResponse httpResponse)
            throws IOException {

        return (httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR || httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    @Nullable
    public void handleError(@Nullable ClientHttpResponse httpResponse)
            throws IOException {
        if (httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            throw new HttpClientErrorException(httpResponse.getStatusCode());
        } else if (httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new GlobalException();
            }
        }
    }
}
