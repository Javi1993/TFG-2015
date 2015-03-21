package controller.tfg.eprail;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import modeldata.tfg.eprail.*;

@SuppressWarnings("unchecked")
public class ProxyManager {


	@PersistenceUnit(unitName="eprailJPA")
	private EntityManagerFactory emf;

	public ProxyManager() {

	}

	public ProxyManager(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManager getEntityManager() {
		if (emf == null) {
			throw new RuntimeException(
					"The EntityManagerFactory is null.  This must be passed in to the constructor or set using the setEntityManagerFactory() method.");
		}
		return emf.createEntityManager();
	}

	/**
	 * Funcion que inserta un objeto en la db
	 * @param object: Objeto que se quiere insertar
	 * @return
	 * @throws Exception
	 */
	public Object createObject(Object object) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return object;
	}

	/**
	 * Funci�n que borra un objeto en la db
	 * @param object: objeto que se quiere borrar
	 * @return
	 * @throws Exception
	 */
	public String deleteObject(Object object) throws Exception {

		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			object = em.merge(object);
			em.remove(object);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return "";
	}

	/**
	 * Actualiza todos los campos de un objeto en la db
	 * @param object: objeto que se quiere actualizar
	 * @return
	 * @throws Exception
	 */
	public Object updateObject(Object object) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			object = em.merge(object);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return object;
	}

	public User findUserByPK(long uid) {
		User user = null;
		EntityManager em = getEntityManager();
		try {
			user = (User) em.find(User.class, uid);
		} finally {
			em.close();
		}
		return user;
	}

	public Project findProjectByPK(long id) {
		Project project = null;
		EntityManager em = getEntityManager();
		try {
			project = (Project) em.find(Project.class, id);
		} finally {
			em.close();
		}
		return project;
	}
	
	public Statuscategory findStatuscategoryByPK(byte id) {
		Statuscategory status = null;
		EntityManager em = getEntityManager();
		try {
			status = (Statuscategory) em.find(Statuscategory.class, id);
		} finally {
			em.close();
		}
		return status;
	}

	public Sharing findSharingByPK(long id) {
		Sharing sharing = null;
		EntityManager em = getEntityManager();
		try {
			sharing = (Sharing) em.find(Sharing.class, id);
		} finally {
			em.close();
		}
		return sharing;
	}

	public User findUserByEmail(String email) {
		return (User) getEntityManager()
				.createQuery(
						"SELECT c FROM User c WHERE c.email LIKE :custEmail")
						.setParameter("custEmail",email)
						.getSingleResult();
	}

	public List<User> findUsersToShare(User user) {
		return (List<User>) getEntityManager()
				.createQuery(
						"SELECT c FROM User c WHERE c.uid <> :custUID")
						.setParameter("custUID",user.getUid())
						.getResultList();
	}

	public User findUserByEmailAndPass(User user) {
		return (User) getEntityManager()
				.createQuery(
						"SELECT c FROM User c WHERE c.email LIKE :custEmail AND c.password LIKE :custPass AND c.isValidate = 1")
						.setParameter("custEmail", user.getEmail())
						.setParameter("custPass", user.getPassword())
						.getSingleResult();
	}

	public Project findProyectByIdAndUID(User user, long id) {
		return (Project) getEntityManager()
				.createQuery(
						"SELECT c FROM Project c WHERE c.user.uid LIKE :custUID AND c.idProject LIKE :custId")
						.setParameter("custUID", user.getUid())
						.setParameter("custId", id)
						.getSingleResult();
	}
	
	public Sharing findSharingByIdandUID(long uid, long id) {
		return (Sharing) getEntityManager()
				.createQuery(
						"SELECT c FROM Sharing c WHERE c.user2.uid LIKE :custUID AND c.project.idProject LIKE :custId")
						.setParameter("custUID", uid)
						.setParameter("custId", id)
						.getSingleResult();
	}
	
	public Sharing findSharedByIdandUID(long uid, long id) {
		return (Sharing) getEntityManager()
				.createQuery(
						"SELECT c FROM Sharing c WHERE c.user1.uid LIKE :custUID AND c.project.idProject LIKE :custId")
						.setParameter("custUID", uid)
						.setParameter("custId", id)
						.getSingleResult();
	}

	public List<Project> findProjectsByUserUID (User user)
	{
		return 	(List<Project>) getEntityManager()
				.createQuery(
						"SELECT c FROM Project c WHERE c.user.uid LIKE :custUID")
						.setParameter("custUID", user.getUid())
						.getResultList();
	}

	public List<Sharing> findProjectsSharedByUserUID (User user)
	{
		return 	(List<Sharing>) getEntityManager()
				.createQuery(
						"SELECT c FROM Sharing c WHERE c.user1.uid LIKE :custUID")
						.setParameter("custUID", user.getUid())
						.getResultList();
	}

	public List<Sharing> findUsersSharedByUserUIDAndIdProject (User user, long id)
	{
		return 	(List<Sharing>) getEntityManager()
				.createQuery(
						"SELECT c FROM Sharing c WHERE c.user1.uid <> :custUID AND c.project.idProject LIKE :custID AND c.user2.uid = :custUID")
						.setParameter("custUID", user.getUid())
						.setParameter("custID", id)
						.getResultList();
	}
}
