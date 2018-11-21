package com.investor.dto;

public class NotiDTO {
    String a,c,s,b,d,t,v;

    public NotiDTO() {
    }

    public NotiDTO(String a, String c, String s, String b, String d, String t, String v) {
        this.a = a;
        this.c = c;
        this.s = s;
        this.b = b;
        this.d = d;
        this.t = t;
        this.v = v;
    }

    @Override
    public String toString() {
        return "NotiDTO{" +
                "a='" + a + '\'' +
                ", c='" + c + '\'' +
                ", s='" + s + '\'' +
                ", b='" + b + '\'' +
                ", d='" + d + '\'' +
                ", t='" + t + '\'' +
                ", v='" + v + '\'' +
                '}';
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
