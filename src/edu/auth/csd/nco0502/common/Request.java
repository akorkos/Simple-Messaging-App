package edu.auth.csd.nco0502.common;

import java.io.Serializable;

public class Request implements Serializable {
    private final String[] args;
    private final int fid;

    /**
     * Represents a request send from the client to the server
     * @param fid function to be executed
     * @param args necessary information for the request execution
     */
    public Request(String fid, String[] args){
        this.fid = Integer.parseInt(fid);
        this.args = args;
    }

    /**
     * @return the fid, function to be executed
     */
    public int getFid() {
        return fid;
    }

    /**
     * @return the args list, for the request execution
     */
    public String[] getArgs() {
        return args;
    }

}
