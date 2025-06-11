	package paquete.conexion;
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;
	
	public class DatabaseConnection {
	
	    private static final String URL = "jdbc:mysql://localhost:3306/akihabara_db";
	    private static final String USER = "root";
	    private static final String PASSWORD = "1234";
	
	    public static Connection getConnection() {
	        Connection connection = null;
	
	        try {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println(" Conexión exitosa a la base de datos.");
	        } catch (SQLException e) {
	            System.out.println(" Error al conectar con la base de datos:");
	            e.printStackTrace();
	        }
	
	        return connection;
	    }
	}
