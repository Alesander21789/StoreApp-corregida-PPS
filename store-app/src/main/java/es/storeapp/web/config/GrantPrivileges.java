package es.storeapp.web.config;


import java.sql.*;
import java.util.Properties;

/*public class CrearDb {

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:derby:work/database");

        CallableStatement cs = conn.prepareCall(
                "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(?, ?)");
        cs.setString(1, "derby.user.<username>");
        cs.setString(2, "<password>");
        cs.execute();

        System.out.println("User created successfully");
    }

}*/

public class GrantPrivileges {

    static final String DB_URL = "jdbc:derby:work/database;";
    static final String USER = "Alesander";
    static final String PASS = "123456789_*.A";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            System.setProperty("derby.database.sqlAuthorization", "TRUE");
            Properties props = new Properties();
            props.put("user","app");
            props.put("password","secr3t");


            conn = DriverManager.getConnection(DB_URL,props);
            stmt = conn.createStatement();

            //Create user
            CallableStatement cs = conn.prepareCall(
                    "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(?, ?)");
           // cs.setString(1, USER);
            cs.setString(1, "derby.user." + USER);
            cs.setString(2, PASS);
            cs.execute();

            CallableStatement cs2 = conn.prepareCall(
                    "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.sqlAuthorization', 'TRUE')");
            cs2.execute();

            // Granting privileges to a user
            String grantSQL = "GRANT SELECT, INSERT, UPDATE, DELETE ON comments  TO Alesander";
            stmt.executeUpdate(grantSQL);
            grantSQL = "GRANT SELECT, INSERT, UPDATE, DELETE ON order_lines  TO Alesander";
            stmt.executeUpdate(grantSQL);
            grantSQL = "GRANT SELECT, INSERT, UPDATE, DELETE ON orders  TO Alesander";
            stmt.executeUpdate(grantSQL);
            grantSQL = "GRANT SELECT, INSERT, UPDATE, DELETE ON products  TO Alesander";
            stmt.executeUpdate(grantSQL);
            grantSQL = "GRANT SELECT, INSERT, UPDATE, DELETE ON users TO Alesander";
            stmt.executeUpdate(grantSQL);
            grantSQL = "GRANT SELECT, INSERT, UPDATE, DELETE ON categories  TO Alesander";
            stmt.executeUpdate(grantSQL);
            System.out.println("Privileges granted successfully");
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
