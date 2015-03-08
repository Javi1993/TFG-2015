package controller.tfg.eprail;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modeldata.tfg.eprail.Deletedproject;
import modeldata.tfg.eprail.Project;
import modeldata.tfg.eprail.User;
import modeldata.tfg.eprail.Sharing;

public class ManagementProject {
	
	public static void subirJPAProyecto(Project newProject) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newProject);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public static void addJPACompartido(Sharing newShared) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newShared);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public static void moverJPAProyecto(Deletedproject delProject) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(delProject);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public static void borrarJPAObject(Object object) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.deleteObject(object);//mandamos el objeto cliente al JPA para que lo borre en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public static List<Project> buscarJPAProyectosPropios(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			List<Project> listProject = manager.findProjectsByUserUID(user);//realizamos la busqueda
			if(!listProject.isEmpty())
			{
				return listProject;
			}
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public static List<Sharing> buscarJPAProyectosCompartidos(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			List<Sharing> listProject = manager.findProjectsSharedByUserUID(user);//realizamos la busqueda
			if(!listProject.isEmpty())
			{
				return listProject;
			}
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public static List<Sharing> buscarJPAUsuariosCompartidos(User user, long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			List<Sharing> listProject = manager.findUsersSharedByUserUIDAndIdProject(user, id);//realizamos la busqueda
			if(!listProject.isEmpty())
			{
				return listProject;
			}
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public static Project buscarJPAProyectoId(long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findProjectByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public static Project buscarJPAProyectoIdUID(User user, long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findProyectByIdAndUID(user, id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public static Sharing buscarJPACompartidoId(long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findSharingByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
}
