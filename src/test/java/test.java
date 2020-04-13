import com.yeqi.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName:test
 * @author: yeqi
 * @create: 2020/4/13 15:49
 * @description
 */

public class test {
    public static void main(String[] args) {
        Connection con = ConnectionPool.getPool().getCon();
        try {
            Statement statement = con.createStatement();
            String sql="select * from t_course where id=1";
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("==="+resultSet.toString());
            con.close();
            ConnectionPool.getPool().returnConn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
