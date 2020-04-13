package com.yeqi.pool;

import com.yeqi.pool.interfaces.connectPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName:ConnectionPool
 * @author: yeqi
 * @create: 2020/4/13 14:57
 * @description 单例  外界操作类
 */

public class ConnectionPool {
    private static ConnectionPool pool1 = null;

    private ConnectionPool() {
         initPool();  //初始化连接池
    }

    public synchronized static ConnectionPool getPool() {
        if (pool1 == null) {
            pool1 = new ConnectionPool();
        }
        return pool1;
    }


    Pool pool = new Pool();

    public void initPool() {
        //循环添加连接到集合中
        for (int i = 0;i<pool.initialSize;i++){
            boolean flag = pool.counts.add(createCon());
            if (flag){
                pool.count++;
            }
        }
        System.out.println("初始化连接池完成");
        pool.getCount();
    }


    public Connection createCon() {
        try {
            Class.forName(pool.driverClassName);
            return DriverManager.getConnection(pool.url,pool.userName,pool.password);
        } catch (Exception e) {
            throw new RuntimeException("数据库连接创建失败:"+e);
        }
    }

    public synchronized Connection getCon() {
        //判断池中是否有空闲连接
        if (pool.counts.size()>0){
            return pool.counts.removeFirst();  //有则直接返回连接
        }
        //如果池中没有空闲连接,判断总连接数是否达到上限
        if (pool.count<pool.maxActive){
            //让连接池自动增长
            autoAdd();
            //增长完成后递归调用返回连接方法
            return getCon();
        }
        //如果池中连接数已达上限，就进入线程等待
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getCon();
    }

    public void autoAdd() {
        //增长的步长为3
        if(pool.count==pool.maxActive){
            throw new RuntimeException("池中数量已达到最大值，不可增长");
        }
        for (int i=0;i<3;i++){  //增长
            if (pool.count ==pool.maxActive){
                break;  //停止增长
            }
            pool.counts.add(createCon());
            pool.count++;
        }
    }

    public void autoReduce() {
        if (pool.count > pool.minIdle && pool.counts.size() > 0) {
            try {
                Connection conn = pool.counts.removeFirst();
                conn.close();
                conn = null;
                pool.count--;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnConn(Connection con) {
        pool.counts.add(con);
        //换回后 缩减连接池
        autoReduce();
    }


}

