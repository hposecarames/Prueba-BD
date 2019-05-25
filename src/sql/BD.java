
package sql;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hector Pose Carames
 */
public class BD {
   public static void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:/home/menuven/Documentos/sqlite/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver " + meta.getDriverName());
                System.out.println("Base de datos creada.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:/home/menuven/Documentos/sqlite/tests.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS cliente (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	nome text NOT NULL,\n"
                + "	apellido NOT NULL, \n"
                + "     ciudad text \n"
                + " );";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
   
    Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/home/menuven/Documentos/sqlite/tests.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    
    /**
     * select all rows in the warehouses table
     */
    public void selectAll(){
        String sql = "SELECT id, nome, apellido, ciudad FROM cliente";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("nome") + "\t" +
                                   rs.getString("apellido") + "\t"+
                                   rs.getString("ciudad"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(String nome, String apellido, String ciudad) {
        String sql = "INSERT INTO cliente(nome,apellido, ciudad) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, nome);
            pstmt.setString(2, apellido);
            pstmt.setString(3, ciudad);
            pstmt.executeUpdate();
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    public void update(int id, String nome, String apellido, String ciudad) {
        String sql = "UPDATE cliente SET nome = ? , "
                + "apellido = ? "
                + "ciudad = ?"
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, nome);
            pstmt.setString(2, apellido);
            pstmt.setString(3, ciudad);
            pstmt.setInt(4, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String getId(){
        String sql = "SELECT max(id) from cliente";
        String rowID = "";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            System.out.println(rs.getInt("max(id)"));
            rowID = Integer.toString(rs.getInt("max(id)"));
            
        } catch (SQLException ex) {
           Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
       }
     
       return rowID;
}
}
    

