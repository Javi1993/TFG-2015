package controller.tfg.eprail;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import controller.tfg.eprailJPA.ProxyManager;
import modeldata.tfg.eprailJPA.Project;
import modeldata.tfg.eprailJPA.Sharing;
import modeldata.tfg.eprailJPA.Statuscategory;
import modeldata.tfg.eprailJPA.User;

public class ManagementProject {
	
	public void subirJPAProyecto(Project newProject) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newProject);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public void addJPACompartido(Sharing newShared) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.createObject(newShared);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public void borrarJPAObject(Object object) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			manager.deleteObject(object);//mandamos el objeto cliente al JPA para que lo borre en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public List<Project> buscarJPAProyectosPropios(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findProjectsByUserUID(user);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public List<Project> buscarJPAProyectosRepetidos(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findRepeatProjectsByUserUID(user);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public List<Project> buscarJPARepetido(Project old, User userBean){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findProyectByNameAndIDrepeat(old, userBean);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public List<Project> buscarAllProjectsIterableJPA(int offset, int max, User userBean){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findAllProjectsIterable(offset, max, userBean);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public List<Sharing> buscarAllProjectsShIterableJPA(int offset, int max, User userBean){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findAllProjectsShIterable(offset, max, userBean);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public List<Sharing> buscarJPAProyectosCompartidos(User user){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findProjectsSharedByUserUID(user);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public List<Sharing> buscarJPAUsuariosCompartidos(User user, long id){

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
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public Statuscategory buscarJPAStatus (byte id)
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);
		try {
			return manager.findStatuscategoryByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}
	
	public Project buscarJPAProyectoId(long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

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
	
	public Project buscarJPAProyectoIdUID(User user, long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

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
	
	public Sharing buscarJPACompartidoId(long id){

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

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
	
	public void updateJPASharing(Sharing sharing) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			manager.updateObject(sharing);//objeto a actualizar se pasa a funcion JPA
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	public void updateJPAProject(Project project) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			manager.updateObject(project);//objeto a actualizar se pasa a funcion JPA
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}
	
	/**
	 * Metodo usado tras borrar permisos a un usuario, sebusca si este usuario dio permisos a otros para revocarlos
	 * @param uid - uid del compartido
	 * @param id - id del proyecto
	 * @return
	 */
	public Sharing buscarJPAReferido(long uid, long id)
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			return manager.findSharingByIdandUID(uid, id);//realizamos la busqueda
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}
	
	/**
	 * Busca una entrada en sharing que coincida con el UID (compartido) y el id del proyecto
	 * @param uid - uid del compartido
	 * @param id - id del proyecto
	 * @return Entrada sharings | null en caso de que el usuario sea el dueño o no le hyan compartido ese proyecto
	 */
	public Sharing buscarJPAPadre(long uid, long id)
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("eprailJPA"); 

		ProxyManager manager = new ProxyManager();
		manager.setEntityManagerFactory(factory);

		try {
			return manager.findSharedByIdandUID(uid, id);//realizamos la busqueda
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}
}
