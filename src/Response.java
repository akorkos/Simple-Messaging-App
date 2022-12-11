import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {
    private Object value;

    private ResponseCodes rid;

    Response(ResponseCodes rid, Object value){
        this.rid = rid;
        this.value = value;
    }

    Response(ResponseCodes rid){
         this.rid = rid;
    }

    public String getAuthToken(){
        return (String) value;
    }

    public ResponseCodes getRid() {
        return rid;
    }
}
