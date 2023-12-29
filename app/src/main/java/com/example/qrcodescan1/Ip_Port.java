package com.example.qrcodescan1;

public  class Ip_Port {
   static private String ip="0" ;
    static private String port="4676";

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Ip_Port.ip = ip;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Ip_Port.port = port;
    }
}
