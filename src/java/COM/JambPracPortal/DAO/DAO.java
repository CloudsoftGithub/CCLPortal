package COM.JambPracPortal.DAO;

import com.mysql.jdbc.Connection;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DAO implements Serializable{

    private Connection cn;

    private PreparedStatement ps;

    public static boolean login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Connection getCn() {
        return this.cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }

    public void Connector() throws Exception {
         try {
            Class.forName("com.mysql.jdbc.Driver");
          // cn = (Connection) DriverManager.getConnection("jdbc:mysql://node53961-cclportal2020.w1-us.cloudjiffy.net/jambpracportal", "root", "VYCmxf59153");
            
            cn = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/jambpracportal?user=root&password=ash123");

        } catch (Exception e) {
            System.out.println("Error in login/Connecting() -->" + e.getMessage());
            throw e;
        } finally {
        }
    }

    public void Close() throws Exception {
        try {
            if (!this.cn.isClosed()) {
                this.cn.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }
}
