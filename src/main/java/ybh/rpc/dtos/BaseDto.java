package ybh.rpc.dtos;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by yangbaihua on 2016/8/11.
 */
public class BaseDto implements Serializable{
    private String className;
    private String method;
    private String args[];

    public BaseDto(String className, String method, String[] args) {
        this.className = className;
        this.method = method;
        this.args = args;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getClassName() {
        return className;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "className='" + className + '\'' +
                ", method='" + method + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
