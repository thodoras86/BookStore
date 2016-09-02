//package ws;
//
//import java.sql.Array;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import bean.User;
//import model.Account;
//
//public class BookService {
//
//	
//	public int add(int a, int b) {
//		return a+b;
//	}
//	
//	public User[] retrieveUsers() {
//		
//		// setup connection
//		Connection con = null;
//		ArrayList<User> users = new ArrayList<User>();
//		
//		
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Laptop2015");
//			// System.out.println("Connection opened");
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("Retrieval");
//		
//		
//		
//		Account ac = new Account(con);
//		try {
//			users = ac.getUsers();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		int siz = users.size();
//		User[] u = new User[siz];
//		
//		for (int i=0; i<siz; i++) {
//			u[i] = users.get(i);
//		}
//		
//		// close db connection
//		try {
//			con.close();
//			// System.out.println("Connection closed");
//		} catch (SQLException e) {
//			System.out.println("Cant close connection");
//		}
//		
//		return u;
//	}
//}
