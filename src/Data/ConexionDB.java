package Data;

import Model.Alumno;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ConexionDB {
    
    private static String TAG = ConexionDB.class.getSimpleName();
    private static Connection connection;
    private static Statement statement;
 
 public static Connection openConnection(){
    try {
 Class.forName("com.mysql.jdbc.Driver");
 connection = DriverManager.getConnection("jdbc:mysql://localhost/tarea", "root", "1234567890");
 JOptionPane.showMessageDialog(null, "Conexion establecida");
 } catch (ClassNotFoundException | SQLException e) {
 JOptionPane.showMessageDialog(null, "Error de conexion " + e);
 }
 return connection;
 }
 
 public static Boolean insert(Alumno alumno){
     try {
         statement = openConnection().createStatement();
         int r = statement.executeUpdate("INSERT INTO tabla2 "
                 + "VALUES(" + 
                 "'" + alumno.getIdciudad()+ "'," + 
                 "'" + alumno.getCiudad()+ "'," +
                 "'" + alumno.getRegion()+ "'," + 
                 "'" + alumno.getPais()+ "'," + 
                 "'" + alumno.getIdioma()+ "')"         
         );
         System.out.println(String.valueOf(r));
         if (r == 1) 
             return true;
         
     } catch (SQLException ex) {
         System.out.println(TAG + " " + ex.getMessage());
     }
     return false;
 }
 public static Boolean validate(String Matricula){
        
        try{
            statement = openConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM tabla2 WHERE idCiudad ='" + Matricula +"'");
            result.next();
            
            int cantidad = result.getInt(1);
            if(cantidad > 0)
                return false;                    
        }catch (SQLException ex) {
            System.out.println(TAG + " " + ex.getMessage());
        }        
        return true;
    }
 public static Boolean delete(Alumno alumno){
     try{
            statement = openConnection().createStatement();
            statement.execute("DELETE FROM tabla2 WHERE idCiudad = '"+ alumno.getIdciudad()+"'");            
            return true;
        }catch (SQLException ex) {
            System.out.println(TAG + " " + ex.getMessage());
            
        }
    return false;
 }
 public static boolean update(String Matricula, Alumno alumno){
     try{
    		String query = "UPDATE tabla2 SET idCiudad = '" + alumno.getIdciudad() + "',"
                        + "Ciudad = '" + alumno.getCiudad()+"',"
                        + "Region = '" + alumno.getRegion()+"',"
                        + "Pais = '" + alumno.getPais()+"',"
                        + "idIdioma = '" + alumno.getIdioma()+"'"
                        + "WHERE idCiudad  = '" + Matricula +"';";
		    System.out.println(query);
            statement = openConnection().createStatement();            
            statement.execute(query);
            return true;
        }catch (Exception ex) {
            System.out.println(TAG + " " + ex.getMessage());
            ex.printStackTrace();            
        }
     return false;
 }
 public static Alumno getInfoAlumno(String Matricula){
     Alumno alumno = null;
     try{
         statement = openConnection().createStatement();
         String query = "SELECT idCiudad,Ciudad,Region,Pais,idIdioma"
                 + " FROM tabla2 WHERE   '" + Matricula + "';";
         ResultSet result = statement.executeQuery(query);
         System.out.println(query);
         System.out.println("Matricula\t\tCiudad\t\t\tRegion\t\tPais\t\t\t\tIdioma");
         if(result.first()){
             do{                
                 System.out.print(result.getString(1)+"\t\t\t");
                 System.out.print(result.getString(2)+"\t\t\t");
                 System.out.print(result.getString(3)+"\t\t\t");
                 System.out.print(result.getString(4)+"\t\t\t");
                 System.out.print(result.getString(5)+"\n");
             }while(result.next());
         }
      
         
         //System.out.println(" " + result);
         
//        if (!result.next())
//            return alumno;
//        
//        
//         alumno = new Alumno();
//         String matricula = result.getString(1);
//         String ciudad = result.getString(2);
//         String region = result.getString(3);
//         String pais = result.getString(4);
//         String idioma = result.getString(5);
//         
//         alumno.setIdciudad(matricula);
//         alumno.setCiudad(ciudad);
//         alumno.setRegion(region);
//         alumno.setPais(pais);
//         alumno.setIdioma(idioma);
//         
//          while(result.next()){
//            System.out.println("idCiudad" + result.getString("idCiudad"));
//        }
         
         return alumno;
     }catch (SQLException ex) {
         System.out.println(TAG + " " + ex.getMessage());
         ex.printStackTrace();
     } 
     
     
     return alumno;
 }
}
