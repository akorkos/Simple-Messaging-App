import java.io.Serializable;

public class Request implements Serializable {
    private String[] args;
    private RequestCodes fid;

    Request(RequestCodes fid, String[] args){
        this.fid = fid;
        this.args = args;
    }

    public RequestCodes getFid() {
        return fid;
    }

    public String[] getArgs() {
        return args;
    }
}
