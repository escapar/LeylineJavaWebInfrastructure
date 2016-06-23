package moe.src.leyline.framework.interfaces.vc;

import java.util.Map;

/**
 * 错误信息类
 */
public class ErrorBrief {

    public Integer status;
    public String error;
    public String message;
    public String timeStamp;
    public String trace;
    public String exception;

    public ErrorBrief(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timeStamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
        this.exception = (String) errorAttributes.get("exception");
    }

}

