package controller.tfg.aplicacion;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import controller.tfg.aplicacionJPA.ProxyManager;
import modeldata.tfg.aplicacionJPA.Project;
import modeldata.tfg.aplicacionJPA.Sharing;
import modeldata.tfg.aplicacionJPA.Statuscategory;
import modeldata.tfg.aplicacionJPA.User;

public class ManagementProject {

	private static ProxyManager manager;

	public ManagementProject() {
		if(manager==null){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("aplicacionJPA"); 
			manager = new ProxyManager();
			manager.setEntityManagerFactory(factory);
		}
	}

	public void subirJPAProyecto(Project newProject) {
		try {
			manager.createObject(newProject);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}

	public void addJPACompartido(Sharing newShared) {
		try {
			manager.createObject(newShared);//mandamos el objeto cliente al JPA para que lo inserte en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}

	public void borrarJPAObject(Object object) {
		try {
			manager.deleteObject(object);//mandamos el objeto cliente al JPA para que lo borre en la BBDD
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}

	public List<Project> buscarJPAProyectosPropios(User user){
		try {
			return manager.findProjectsByUserUID(user);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public List<Project> buscarJPAProyectosRepetidos(User user){
		try {
			return manager.findRepeatProjectsByUserUID(user);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public List<Project> buscarJPARepetido(Project old, User userBean){
		try {
			return manager.findProyectByNameAndIDrepeat(old, userBean);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public List<Project> buscarAllProjectsIterableJPA(int offset, int max, User userBean){
		try {
			return manager.findAllProjectsIterable(offset, max, userBean);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public List<Sharing> buscarAllProjectsShIterableJPA(int offset, int max, User userBean){
		try {
			return manager.findAllProjectsShIterable(offset, max, userBean);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public List<Sharing> buscarJPAProyectosCompartidos(User user){
		try {
			return manager.findProjectsSharedByUserUID(user);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public List<Sharing> buscarJPAUsuariosCompartidos(User user, long id){
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
		try {
			return manager.findStatuscategoryByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public Project buscarJPAProyectoId(long id){
		try {
			return manager.findProjectByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public Project buscarJPAProyectoIdUID(User user, long id){
		try {
			return manager.findProyectByIdAndUID(user, id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public Sharing buscarJPACompartidoId(long id){
		try {
			return manager.findSharingByPK(id);//realizamos la busqueda
		}
		catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());
		}
		return null;
	}

	public void updateJPASharing(Sharing sharing) {
		try {
			manager.updateObject(sharing);//objeto a actualizar se pasa a funcion JPA
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
	}

	public void updateJPAProject(Project project) {
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
		try {
			return manager.findSharedByIdandUID(uid, id);//realizamos la busqueda
		} catch (Exception e) {
			System.out.println("Descripción: " + e.getMessage());						
		}
		return null;
	}
}
