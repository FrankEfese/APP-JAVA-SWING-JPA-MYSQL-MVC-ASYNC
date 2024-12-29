package PackageEmpleados;

import PackageExceptions.NonexistentEntityException;
import PackageEmpresas.Empresas_Object;
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


public class Empleados_JPAC_Modelo implements Serializable {
    
    private EntityManagerFactory emf = null;
       
    // CONSTRUCTOR
    public Empleados_JPAC_Modelo() {
        this.emf = Persistence.createEntityManagerFactory("GESTOR-JPA-PU");
    }
    
    // GETTER
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    // METODO PARA CREAR UN EMPLEADO (MODELO)
    public void create(Empleados_Object empleados_Object) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas_Object empresas_id_empresa = empleados_Object.getEmpresas_id_empresa();
            if (empresas_id_empresa != null) {
                empresas_id_empresa = em.getReference(empresas_id_empresa.getClass(), empresas_id_empresa.getId_empresa());
                empleados_Object.setEmpresas_id_empresa(empresas_id_empresa);
            }
            em.persist(empleados_Object);
            if (empresas_id_empresa != null) {
                empresas_id_empresa.getEmpleados().add(empleados_Object);
                empresas_id_empresa = em.merge(empresas_id_empresa);
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "EMPLEADO AGREGADO", "EMPLEADOS-JPAC", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR AL EMPLEADO", "EMPLEADOS-JPAC", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    // METODO PARA ACTUALIZAR UN EMPLEADO (MODELO)
    public void edit(Empleados_Object empleados_Object) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados_Object persistentEmpleados_Object = em.find(Empleados_Object.class, empleados_Object.getId_empleado());
            Empresas_Object empresas_id_empresaOld = persistentEmpleados_Object.getEmpresas_id_empresa();
            Empresas_Object empresas_id_empresaNew = empleados_Object.getEmpresas_id_empresa();
            if (empresas_id_empresaNew != null) {
                empresas_id_empresaNew = em.getReference(empresas_id_empresaNew.getClass(), empresas_id_empresaNew.getId_empresa());
                empleados_Object.setEmpresas_id_empresa(empresas_id_empresaNew);
            }
            empleados_Object = em.merge(empleados_Object);
            if (empresas_id_empresaOld != null && !empresas_id_empresaOld.equals(empresas_id_empresaNew)) {
                empresas_id_empresaOld.getEmpleados().remove(empleados_Object);
                empresas_id_empresaOld = em.merge(empresas_id_empresaOld);
            }
            if (empresas_id_empresaNew != null && !empresas_id_empresaNew.equals(empresas_id_empresaOld)) {
                empresas_id_empresaNew.getEmpleados().add(empleados_Object);
                empresas_id_empresaNew = em.merge(empresas_id_empresaNew);
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "EMPLEADO ACTUALIZADO", "EMPLEADOS-JPAC", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = empleados_Object.getId_empleado();
                if (findEmpleados_Object(id) == null) {
                    throw new NonexistentEntityException("The empleados_Object with id " + id + " no longer exists.");
                }
            }
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR AL EMPLEADO", "EMPLEADOS-JPAC", JOptionPane.ERROR_MESSAGE);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    // METODO PARA ELIMINAR UN EMPLEADO (MODELO)
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados_Object empleados_Object;
            try {
                empleados_Object = em.getReference(Empleados_Object.class, id);
                empleados_Object.getId_empleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados_Object with id " + id + " no longer exists.", enfe);
            }
            Empresas_Object empresas_id_empresa = empleados_Object.getEmpresas_id_empresa();
            if (empresas_id_empresa != null) {
                empresas_id_empresa.getEmpleados().remove(empleados_Object);
                empresas_id_empresa = em.merge(empresas_id_empresa);
            }
            em.remove(empleados_Object);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "EMPLEADO ELIMINADO CORRECTAMENTE", "EMPLEADOS-JPAC", JOptionPane.INFORMATION_MESSAGE);
        } catch (NonexistentEntityException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR AL EMPLEADO", "EMPLEADOS-JPAC", JOptionPane.ERROR_MESSAGE);
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    // METODO PARA OBTENER LOS EMPLEADOS (MODELO)
    public List<Empleados_Object> findEmpleados_ObjectEntities() {
        try{
            return findEmpleados_ObjectEntities(true, -1, -1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER LOS EMPLEADOS", "EMPLEADOS-JPAC", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }  
    }

    // METODO PARA OBTENER LOS EMPLEADOS (MODELO)
    public List<Empleados_Object> findEmpleados_ObjectEntities(int maxResults, int firstResult) {
        return findEmpleados_ObjectEntities(false, maxResults, firstResult);
    }

    // METODO PARA OBTENER LOS EMPLEADOS (MODELO)
    private List<Empleados_Object> findEmpleados_ObjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados_Object.class));
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
    
    // METODO PARA OBTENER UN EMPLEADO (MODELO)
    public Empleados_Object findEmpleados_Object(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados_Object.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL EMPLEADO", "EMPLEADOS-JPAC", JOptionPane.ERROR_MESSAGE);
            return null;
        }finally {
            em.close();
        }
    }
    
    // METODO PARA OBTENER EL TOTAL DE EMPLEADOS (MODELO)
    public int getEmpleados_ObjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados_Object> rt = cq.from(Empleados_Object.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL TOTAL DE EMPLEADOS", "EMPLEADOS-JPAC", JOptionPane.ERROR_MESSAGE);
            return 0;
        } finally {
            em.close();
        }
    }
    
}
