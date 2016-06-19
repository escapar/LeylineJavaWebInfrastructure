package moe.src.leyline.framework.interfaces.vc.error;

import java.util.Map;

import lombok.Data;
import lombok.ToString;

/**
 * Created by bytenoob on 6/17/16.
 */
@Data @ToString
public class ErrorBrief {

    public Integer status;
    public String error;
    public String message;
    public String timeStamp;
    public String trace;

    public ErrorBrief(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timeStamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }

}

