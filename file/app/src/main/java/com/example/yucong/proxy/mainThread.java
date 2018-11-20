package com.example.yucong.proxy;

import java.lang.reflect.Proxy;

public class mainThread {

    public static void  main(String[] args){

        xiaomin xiao=new xiaomin();

        DynamicProxy dynamicProxy=new DynamicProxy(xiao);

       ClassLoader classLoader= xiao.getClass().getClassLoader();
       //动态生成一个律师代理类
        IlawSuit lawer=(IlawSuit) Proxy.newProxyInstance(classLoader,new Class[]{IlawSuit.class},dynamicProxy);
        lawer.burden();

    }




}
