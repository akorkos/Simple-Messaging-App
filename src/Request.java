import java.io.Serializable;

public class Request implements Serializable {
    private final String[] args;
    private final int fid;

    Request(String fid, String[] args){
        this.fid = Integer.parseInt(fid);
        this.args = args;
    }

    public int getFid() {
        return fid;
    }

    public String[] getArgs() {
        return args;
    }

}
