
package sql;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        // url = ruta de la base de datos
        String url = "jdbc:sqlite:/home/menuven/Documentos/sqlite/tests.db";
        // Introducir la sentencia en la variable "sql".       
        String sql = "CREATE TABLE IF NOT EXISTS empresa (\n"
                + "	cif PRIMARY KEY,\n"
                + "	nome text NOT NULL, \n"
                + "     telefono text \n"              
                + " );";
        
        
        String sql2 = "CREATE TABLE IF NOT EXISTS cliente (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	nome text NOT NULL,\n"
                + "	apellido text NOT NULL, \n"
                + "     ciudad text NOT NULL, \n"
                + "     cif text, \n"
                + "     FOREIGN KEY(cif) REFERENCES empresa(cif) \n"
                + " );";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // creamos la tabla cargando nuestra sentencia sql
            stmt.execute(sql);
            stmt.execute(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
   
    Connection connect() {
        // url = ruta de nuestra base de datos
        String url = "jdbc:sqlite:/home/menuven/Documentos/sqlite/tests.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    
    
    public void selectAll(){
        String sql = "SELECT id, nome, apellido, ciudad, cif FROM cliente";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // bucle para imprimir todos los datos de nuestra tabla
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("nome") + "\t" +
                                   rs.getString("apellido") + "\t"+
                                   rs.getString("ciudad") + "\t"+
                                   rs.getString("cif"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(String nome, String apellido, String ciudad, String cif) {
       
        // insert para añadir clientes a nuestra base de datos
        // el id se genera autómaticamente
        String sql = "INSERT INTO cliente(nome,apellido,ciudad,cif) VALUES(?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, nome);
            pstmt.setString(2, apellido);
            pstmt.setString(3, ciudad);
            pstmt.setString(4, cif);
            pstmt.executeUpdate();
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }       
        
        
    }
    public void insert2(String cif, String nome, String telefono) {
       
        // insert para añadir clientes a nuestra base de datos
        // el id se genera autómaticamente
        String sql = "INSERT INTO empresa(cif,nome,telefono) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, cif);
            pstmt.setString(2, nome);
            pstmt.setString(3, telefono);
            pstmt.executeUpdate();
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void update(int id, String nome, String apellido, String ciudad) {
        String sql = "UPDATE cliente SET nome = ? , "
                + "apellido = ? , "
                + "ciudad = ? "                
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            
            pstmt.setString(1, nome);
            pstmt.setString(2, apellido);
            pstmt.setString(3, ciudad);            
            pstmt.setInt(4, id);
             
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void update2(String cif, String nome, String telefono) {
        String sql = "UPDATE empresa SET nome = ? , "               
                + "telefono = ? "                
                + "WHERE cif = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, nome);
            pstmt.setString(2, telefono);
            pstmt.setString(3, cif);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            //recibe el parámetro
            pstmt.setInt(1, id);
            //ejecuta la sentencia
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void delete2(String cif) {
        String sql = "DELETE FROM empresa WHERE cif = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            //recibe el parámetro (cif en nuestro caso que es el primary key)
            pstmt.setString(1, cif);
            // ejecutamos la sentencia sql
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getCif(String id){
        String sql = "SELECT cif FROM cliente where id ="+id;
        String dato = "";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            dato = rs.getString("cif");
            
        } catch (SQLException ex) {
           Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
       }
     
       return dato;
    }
    
    public int getId(){
        //como el id se genera automáticamente tenemos que recuperarlo 
        //para introducirlo en la tabla
        String sql = "SELECT max(id) from cliente";
        int rowID = 0;
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){            
             rowID = rs.getInt("max(id)");
            
        } catch (SQLException ex) {
           Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
       }
     
       return rowID;
}
    
    public ArrayList<Object[]> tablas(){
       ArrayList<Object[]> tablas  = new ArrayList<>();
       String sql = "SELECT id, nome, apellido, ciudad, cif FROM cliente";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
        while (rs.next()) {
                Object[] datos = new Object[5];
                datos[0] = Integer.toString(rs.getInt("id"));
                datos[1] = rs.getString("nome");
                datos[2] = rs.getString("apellido");
                datos[3] = rs.getString("ciudad");
                datos[4] = rs.getString("cif");
                
                tablas.add(datos);
        }        
   
    }  catch (SQLException ex) {
           Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
       }
     
        return tablas;
        }
    public ArrayList<Object[]> tablaE(){
       ArrayList<Object[]> tablaE  = new ArrayList<>();
       String sql = "SELECT cif, nome, telefono FROM empresa";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
        while (rs.next()) {
                Object[] datos = new Object[3];               
                datos[0] = rs.getString("cif");
                datos[1] = rs.getString("nome");
                datos[2] = rs.getString("telefono");
                
                tablaE.add(datos);
        }        
   
    }  catch (SQLException ex) {
           Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
       }
     
        return tablaE;
        }
    
    
}