package ybh.rpc.rpcImpl;

import ybh.rpc.rpcInterface.HelloRpc;

/**
 * Created by yangbaihua on 2016/8/11.
 */
public class HelloImpl implements HelloRpc {
    public String say(String msg) {
        System.out.println("hi~ you msg is :" + msg);
        return "hi~ you msg is :" + msg;
    }
}
