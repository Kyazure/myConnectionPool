# myConnectionPool
轻量版数据库连接池  
参数：  
driverClassName----数据库驱动  
userName-------数据库名  
password-------密码  
url  
initialSize----- 初始化连接数量(默认为5)    
minIdle------最小连接数量--------(默认为5)  
maxActive----最大连接数量(默认15)  
使用步骤：  
1.导入jar(myConnectionPool-1.0.jar)    
2.配置 db.properties(配置文件命名方式需为db.properties)  
(db.properties)如：  
db.driver=com.mysql.jdbc.Driver  
db.url=jdbc:mysql://127.0.0.1:3306/test01?useSSL=false&useUnicode=true&characterEncoding=utf-8  
db.username=  
db.password=  
测试连接代码：  
public class test {  
    public static void main(String[] args) {  
        Connection con = ConnectionPool.getPool().getCon();  
        try {  
            Statement statement = con.createStatement();  
            String sql="select * from t_course where id=1";  
            ResultSet resultSet = statement.executeQuery(sql);  
            System.out.println("==="+resultSet);  
            ConnectionPool.getPool().returnConn(con);  //返还连接到连接池  
            con.close();    
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}  
测试结果:  
initialSize使用默认值=5  
minIdle使用默认值=5  
maxActive使用默认值=15  
初始化连接池完成  
当前池子连接数为：5  
===com.mysql.jdbc.JDBC42ResultSet@5cb0d902  
