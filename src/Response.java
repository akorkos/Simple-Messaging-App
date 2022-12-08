import java.io.Serializable;

public class Response implements Serializable {
    private String[] args;
    private RequestCodes fid;

    Response(RequestCodes fid, String[] args){
        this.fid = fid;
        this.args = args;
    }
}
