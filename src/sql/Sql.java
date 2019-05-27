/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import static sql.BD.createNewDatabase;
import static sql.BD.createNewTable;

/**
 *
 * @author Hector Pose Carames
 */
public class Sql {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewDatabase("test.db");
        ClienteBD cliente = new ClienteBD();
        cliente.setVisible(true);
        

        
//        createNewTable();
//        BD bd = new BD();
//        bd.insert("Paquito", "El Chocolatero");
//        bd.insert("Pink", "Floyd");
//        bd.selectAll();
//        bd.getId();
       
        
        
    }
    
}
