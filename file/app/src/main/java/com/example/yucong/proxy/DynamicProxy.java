package com.example.yucong.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 */



public class DynamicProxy  implements InvocationHandler {

    private Object object;
    public DynamicProxy(Object object) {
        super();
        this.object=object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Object result=  method.invoke(object,args);
        return result;
    }
}
