package com.yeqi.pool;

import java.io.IOException;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName:Pool
 * @author: yeqi
 * @create: 2020/4/13 17:01
 * @description  连接池的属性
 */

public class Pool {
    //成员属性
    protected static String driverClassName;
    protected static String userName;
    protected static String password;
    protected static String url;
    //初始化连接数量
    protected static int initialSize = 5;
    //最小连接数量
    protected static int minIdle = 5;
    //最大连接数量(默认15)
    protected static int maxActive = 15;

    //当前连接数量
    protected static int count;
    public static int getCount() {
        System.out.println("当前池子连接数为："+count);
        return count;
    }
    //用集合作为池子存储连接
    LinkedList<Connection> counts = new LinkedList<Connection>();

    //初始化成员属性
    static {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("db.properties"));
            driverClassName = properties.getProperty("db.driver");
            url = properties.getProperty("db.url");
            userName = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            try{
                initialSize = new Integer(properties.getProperty("db.initialSize"));
            }catch (NumberFormatException e){
                System.out.println("initialSize使用默认值="+initialSize);
            }
            try{
                minIdle = new Integer(properties.getProperty("db.minIdle"));
            }catch (NumberFormatException e){
                System.out.println("minIdle使用默认值="+minIdle);
            }
            try{
                maxActive = new Integer(properties.getProperty("db.maxActive"));
            }catch (NumberFormatException e){
                System.out.println("maxActive使用默认值="+maxActive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
