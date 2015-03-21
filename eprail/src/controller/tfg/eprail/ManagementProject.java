package controller.tfg.eprail;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import modeldata.tfg.eprail.Deletedproject;
import modeldata.tfg.eprail.Project;
import modeldata.tfg.eprail.Statuscategory;
import modeldata.tfg.eprail.User;
import modeldata.tfg.eprail.Sharing;

public class ManagementProject {
	
	public static void subirJPAProyecto(Project newProject) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newProject);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
	}
	
	public static void addJPACompartido(Sharing newShared) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newShared);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
	}
	
	public static void moverJPAProyecto(Deletedproject delProject) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(delProject);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
	}
	
	public static void borrarJPAObject(Object object) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.deleteObject(object);//mandamos el objeto cliente al JPA para que lo borre en la BBDD
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
	}
	
	public static List<Project> buscarJPAProyectosPropios(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

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
			System.out.println("Descripci�n: " + e.getMessage());
		}
		return null;
	}
	
	public static List<Sharing> buscarJPAProyectosCompartidos(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

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
			System.out.println("Descripci�n: " + e.getMessage());
		}
		return null;
	}
	
	public static List<Sharing> buscarJPAUsuariosCompartidos(User user, long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

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
			System.out.println("Descripci�n: " + e.getMessage());
		}
		return null;
	}
	
	public static Statuscategory buscarJPAStatus (byte id)
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findStatuscategoryByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());
		}
		return null;
	}
	
	public static Project buscarJPAProyectoId(long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findProjectByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());
		}
		return null;
	}
	
	public static Project buscarJPAProyectoIdUID(User user, long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findProyectByIdAndUID(user, id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());
		}
		return null;
	}
	
	public static Sharing buscarJPACompartidoId(long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findSharingByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());
		}
		return null;
	}
	
	public static void updateJPASharing(Sharing sharing) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			manager.updateObject(sharing);//objeto a actualizar se pasa a funcion JPA
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
	}
	
	/**
	 * Metodo usado tras borrar permisos a un usuario, sebusca si este usuario dio permisos a otros para revocarlos
	 * @param uid - uid del compartido
	 * @param id - id del proyecto
	 * @return
	 */
	public static Sharing buscarJPAReferido(long uid, long id)
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			return manager.findSharingByIdandUID(uid, id);//realizamos la busqueda
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
		return null;
	}
	
	/**
	 * Busca la entrada en sharings de quien le compartio este proyecto al usuario
	 * @param uid - uid del compartido
	 * @param id - id del proyecto
	 * @return Entrada sharings | null en caso de que el usuario sea el due�o
	 */
	public static Sharing buscarJPAPadre(long uid, long id)
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			return manager.findSharedByIdandUID(uid, id);//realizamos la busqueda
		} catch (Exception e) {
			System.out.println("Descripci�n: " + e.getMessage());						
		}
		return null;
	}
}
