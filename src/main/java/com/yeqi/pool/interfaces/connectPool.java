package com.yeqi.pool.interfaces;

import java.sql.Connection;

/**
 * @author yeqi
 * 连接池方法接口
 */
public interface connectPool {
    void initPool();   //初始化连接池

    Connection createCon();    //创建数据库连接

    Connection getCon();    //从池中获取连接

    void autoAdd();   //连接池自动增长

    void autoReduce();  //连接池自动缩减

    void returnConn(Connection con); //返回连接到连接池





}
