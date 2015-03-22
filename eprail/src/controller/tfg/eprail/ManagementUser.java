package controller.tfg.eprail;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import modeldata.tfg.eprail.User;

public class ManagementUser {
	
	public void registrarJPAUser(User newUser) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newUser);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public User buscarJPAUser(long uid){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findUserByPK(uid);//Buscamos al proveedor con ese eamil		
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}
	
	public User buscarJPAUserEmail(String email){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findUserByEmail(email);//Buscamos al proveedor con ese eamil		
		} catch (NoResultException e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}
	
	public User realizarJPALogin(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			user = manager.findUserByEmailAndPass(user);//realizamos la busqueda
			user.setLoggedIn(true);
			return user;
		}
		catch (NoResultException e) {
			System.out.println("Descripción: " + e.getMessage());
			user.setLoggedIn(false);
			return user;
		}
	}
	
	public void updateJPAUser(User user) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			manager.updateObject(user);//objeto a actualizar se pasa a funcion JPA
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public List<User> buscarJPAUserCompartir(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			List<User> listUser = manager.findUsersToShare(user);//Buscamos al proveedor con ese eamil		
			if(!listUser.isEmpty())
			{
				return listUser;
			}
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}
}
