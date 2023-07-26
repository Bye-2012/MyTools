package com.moon.mytools.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Date: 2023/7/8 20:01
 * Author: Moon
 * Desc:
 */
public class LearnJava {

    public static void main(String[] args) {
        Type type = new HashMap<String, List<String>>() {
        }.getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        System.out.println(types[0]);


        Number[] num = new Integer[3];
        num[1] = 4.0;

        List<JavaA> list = new ArrayList<>();
        ArrayList<JavaB> javaBS = new ArrayList<>();
        javaBS.add(new JavaB());
        list.addAll(javaBS);
        for (JavaA javaA : list) {
            System.out.println(javaA);
        }

        List<? super JavaB> ss = new ArrayList<JavaA>();
        ss.add(new JavaC());

        List<JavaA> list1 = new ArrayList<>();
        List<JavaC> list2 = new ArrayList<>();
        list2.add(new JavaC());
        list1.addAll(list2);


        Proxy.newProxyInstance(LearnJava.class.getClassLoader(), new Class[]{IProxyTest.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }

}

@Api2(path = "http:www.baidu.com")
class JavaA {
}

class JavaB extends JavaA {
    String getName() {
        return "";
    }
}

class JavaC extends JavaB {
    @Override
    String getName() {
        return super.getName();
    }
}

interface IProxyTest {
    void setName(String name);

    String getName();
}