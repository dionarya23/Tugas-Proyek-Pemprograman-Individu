package p10517016;
import java.sql.*;

public class koneksi {
   private static final String host = "localhost";
   private static final String db="perpus_10517016";
   private static final String user="root";
   private static final String pass=""; 
   private static Connection conn;
   
   public static Connection getConnection(){ 
        if(conn==null){ 
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn=DriverManager.getConnection("jdbc:mysql://"+host+"/"+db,user,pass);
            }catch(ClassNotFoundException cnfe){
                System.out.println("Driver Tidak Ditemukan ="+ cnfe);
            }
            catch(SQLException ex){
                System.out.println("Koneksi Gagal ="+ ex);
            }
        } 
       return conn;
   }
}