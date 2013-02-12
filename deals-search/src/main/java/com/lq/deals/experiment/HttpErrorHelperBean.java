package com.lq.deals.experiment;

import org.apache.camel.component.http.HttpOperationFailedException;

public class HttpErrorHelperBean {
    public boolean isRedirectionError(Exception exception) {
        return (exception instanceof HttpOperationFailedException) &&
                (((HttpOperationFailedException) exception).getRedirectLocation() != null);
    }
}
