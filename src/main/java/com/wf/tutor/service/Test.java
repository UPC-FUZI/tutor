package com.wf.tutor.service;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String args[]){
        List<S> list = new ArrayList<>();
        list.add(new S(0,1));
        list.add(new S(0,1));
        for (S s: list){
            s.a=2;
        }
        System.out.println(list.toString());
    }
}
class S{
    int a;
    int b;
    public S(int a,int b){
        this.a =a;
        this.b=b;
    }

    @Override
    public String toString() {
        return "S{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
