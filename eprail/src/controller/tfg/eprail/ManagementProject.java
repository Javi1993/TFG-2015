package controller.tfg.eprail;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modeldata.tfg.eprail.Project;
import modeldata.tfg.eprail.User;

public class ManagementProject {
	
	/*public static void subirJPAProyecto(Project newProject) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newProject);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}*/
	
	public static List<Project> buscarProyectosPropios(User user){

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
	
	public static List<Project> buscarProyectosCompartidos(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprail"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			List<Project> listProject = manager.findProjectsSharedByUserUID(user);//realizamos la busqueda
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
	
	public static Project buscarProyectoIdUID(User user, long id){

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
}
