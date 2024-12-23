package PackageLogin;

import PackageExceptions.NonexistentEntityException;
import java.awt.HeadlessException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;

public class Login_JPAC_Modelo implements Serializable {
    
    private EntityManagerFactory emf = null;

    //CONSTRUCTOR
    public Login_JPAC_Modelo(EntityManagerFactory emf) {
        this.emf = emf;
    }   

    //CONSTRUCTOR
    public Login_JPAC_Modelo() {
        this.emf = Persistence.createEntityManagerFactory("GESTOR-JPA-PU");
    }
    
    //GETTER
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    
    //METODO PARA CREAR UN ADMINISTRADOR (MODELO) 
    public void create(Login_Object login_Object) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(login_Object);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "ADMIN AGREGADO", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR EL ADMIN", "INFORMACION", JOptionPane.ERROR_MESSAGE);
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    //METODO PARA EDITAR UN ADMINISTRADOR (MODELO)
    public void edit(Login_Object login_Object) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            login_Object = em.merge(login_Object);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "ADMIN ACTUALIZADO", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = login_Object.getId_login();
                if (findLogin_Object(id) == null) {
                    throw new NonexistentEntityException("The login_Object with id " + id + " no longer exists.");
                }
            }
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR EL ADMIN", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    //METODO PARA ELIMINAR UN ADMINISTRADOR (MODELO)
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Login_Object login_Object;
            try {
                login_Object = em.getReference(Login_Object.class, id);
                login_Object.getId_login();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The login_Object with id " + id + " no longer exists.", enfe);
            }
            em.remove(login_Object);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "ADMIN ELIMINADO CORRECTAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        } catch (NonexistentEntityException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR EL ADMIN", "INFORMACION", JOptionPane.ERROR_MESSAGE);
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }
    

    //METODO PARA OBTENER TODOS LOS ADMINISTRADORES (MODELO)
    public List<Login_Object> findLogin_ObjectEntities() {
        try{
            return findLogin_ObjectEntities(true, -1, -1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER LOS ADMINS", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }  
    }

    //METODO PARA OBTENER TODOS LOS ADMINISTRADORES (MODELO)
    public List<Login_Object> findLogin_ObjectEntities(int maxResults, int firstResult) {
        return findLogin_ObjectEntities(false, maxResults, firstResult);
    }

    //METODO PARA OBTENER TODOS LOS ADMINISTRADORES (MODELO)
    private List<Login_Object> findLogin_ObjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Login_Object.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    
    //METODO PARA OBTENER UN ADMINISTRADOR (MODELO)
    public Login_Object findLogin_Object(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Login_Object.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL ADMIN", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            return null;
        }finally {
            em.close();
        }
    }

    
    //METODO PARA OBTENER EL NUMERO TOTAL DE ADMINISTRADORES (MODELO)
    public int getLogin_ObjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Login_Object> rt = cq.from(Login_Object.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL TOTAL DE ADMINS", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            return 0;
        } finally {
            em.close();
        }
    }

}
