package controller.tfg.aplicacion;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import controller.tfg.aplicacionJPA.ProxyManager;
import modeldata.tfg.aplicacionJPA.User;

public class ManagementUser {

	private static ProxyManager manager;

	public ManagementUser() {
		if(manager==null){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("aplicacionJPA"); 
			manager = new ProxyManager();
			manager.setEntityManagerFactory(factory);
		}
	}

	public void registrarJPAUser(User newUser) {
		try {
			manager.createObject(newUser);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}

	public User buscarJPAUser(long uid){
		try {
			return manager.findUserByPK(uid);//Buscamos al proveedor con ese eamil		
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}

	public User buscarJPAUserEmail(String email){
		try {
			return manager.findUserByEmail(email);//Buscamos al proveedor con ese eamil		
		} catch (NoResultException e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}

	public User realizarJPALogin(User user){
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
		try {
			manager.updateObject(user);//objeto a actualizar se pasa a funcion JPA
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}

	public List<User> buscarJPAUserCompartir(User user){
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
