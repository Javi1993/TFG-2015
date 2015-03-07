package controller.tfg.eprail;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import modeldata.tfg.eprail.User;

public class ManagementUser {
	
	/*public static void registrarJPAUser(User newUser) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newUser);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
	}*/
	
	public static User buscarJPAUser(long uid){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findUserByPK(uid);//Buscamos al proveedor con ese eamil		
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
		return null;
	}
	
	public static User buscarJPAUserEmail(String email){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findUserByEmail(email);//Buscamos al proveedor con ese eamil		
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
		return null;
	}
	
	public static User realizarJPALogin(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			user = manager.findUserByEmailAndPass(user);//realizamos la busqueda
			user.setLoggedIn(true);
			return user;
		}
		catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());
			user.setLoggedIn(false);
			return user;
		}
	}
	
	/*public static void updateJPAUser(User user) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			manager.updateObject(user);//objeto a actualizar se pasa a funcion JPA
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
	}*/
}
