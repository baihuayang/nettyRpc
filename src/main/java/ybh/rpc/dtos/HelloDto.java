package ybh.rpc.dtos;

/**
 * Created by yangbaihua on 2016/8/11.
 */
public class HelloDto extends BaseDto{
    public HelloDto(String className, String method, String[] args) {
        super(className, method, args);
    }
}
