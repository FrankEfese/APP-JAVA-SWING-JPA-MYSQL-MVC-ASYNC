package PackageEmpresas;

import PackageEmpleados.Empleados_Object;
import PackageExceptions.NonexistentEntityException;
import PackageProductos.Productos_Object;
import PackageSeguros.Seguros_Object;
import java.awt.HeadlessException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class Empresas_JPAC_Modelo implements Serializable {

    private EntityManagerFactory emf = null;

    // CONSTRUCTOR
    public Empresas_JPAC_Modelo() {
        this.emf = Persistence.createEntityManagerFactory("GESTOR-JPA-PU");
    }

    // GETTER
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // METODO PARA CREAR UNA EMPRESA (MODELO)
    public void create(Empresas_Object empresas_Object) {
        if (empresas_Object.getEmpleados() == null) {
            empresas_Object.setEmpleados(new ArrayList<Empleados_Object>());
        }
        if (empresas_Object.getProductos() == null) {
            empresas_Object.setProductos(new ArrayList<Productos_Object>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguros_Object seguros_id_seguro = empresas_Object.getSeguros_id_seguro();
            if (seguros_id_seguro != null) {
                seguros_id_seguro = em.getReference(seguros_id_seguro.getClass(), seguros_id_seguro.getId_seguro());
                empresas_Object.setSeguros_id_seguro(seguros_id_seguro);
            }
            List<Empleados_Object> attachedEmpleados = new ArrayList<Empleados_Object>();
            for (Empleados_Object empleadosEmpleados_ObjectToAttach : empresas_Object.getEmpleados()) {
                empleadosEmpleados_ObjectToAttach = em.getReference(empleadosEmpleados_ObjectToAttach.getClass(), empleadosEmpleados_ObjectToAttach.getId_empleado());
                attachedEmpleados.add(empleadosEmpleados_ObjectToAttach);
            }
            empresas_Object.setEmpleados(attachedEmpleados);
            List<Productos_Object> attachedProductos = new ArrayList<Productos_Object>();
            for (Productos_Object productosProductos_ObjectToAttach : empresas_Object.getProductos()) {
                productosProductos_ObjectToAttach = em.getReference(productosProductos_ObjectToAttach.getClass(), productosProductos_ObjectToAttach.getId_producto());
                attachedProductos.add(productosProductos_ObjectToAttach);
            }
            empresas_Object.setProductos(attachedProductos);
            em.persist(empresas_Object);
            if (seguros_id_seguro != null) {
                seguros_id_seguro.getEmpresas().add(empresas_Object);
                seguros_id_seguro = em.merge(seguros_id_seguro);
            }
            for (Empleados_Object empleadosEmpleados_Object : empresas_Object.getEmpleados()) {
                Empresas_Object oldEmpresas_id_empresaOfEmpleadosEmpleados_Object = empleadosEmpleados_Object.getEmpresas_id_empresa();
                empleadosEmpleados_Object.setEmpresas_id_empresa(empresas_Object);
                empleadosEmpleados_Object = em.merge(empleadosEmpleados_Object);
                if (oldEmpresas_id_empresaOfEmpleadosEmpleados_Object != null) {
                    oldEmpresas_id_empresaOfEmpleadosEmpleados_Object.getEmpleados().remove(empleadosEmpleados_Object);
                    oldEmpresas_id_empresaOfEmpleadosEmpleados_Object = em.merge(oldEmpresas_id_empresaOfEmpleadosEmpleados_Object);
                }
            }
            for (Productos_Object productosProductos_Object : empresas_Object.getProductos()) {
                Empresas_Object oldEmpresas_id_empresa_pOfProductosProductos_Object = productosProductos_Object.getEmpresas_id_empresa_p();
                productosProductos_Object.setEmpresas_id_empresa_p(empresas_Object);
                productosProductos_Object = em.merge(productosProductos_Object);
                if (oldEmpresas_id_empresa_pOfProductosProductos_Object != null) {
                    oldEmpresas_id_empresa_pOfProductosProductos_Object.getProductos().remove(productosProductos_Object);
                    oldEmpresas_id_empresa_pOfProductosProductos_Object = em.merge(oldEmpresas_id_empresa_pOfProductosProductos_Object);
                }
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "EMPRESA AGREGADA", "EMPRESAS-JPAC", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR LA EMPRESA", "EMPRESAS-JPAC", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // METODO PARA EDITAR UNA EMPRESA (MODELO)
    public void edit(Empresas_Object empresas_Object) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas_Object persistentEmpresas_Object = em.find(Empresas_Object.class, empresas_Object.getId_empresa());
            Seguros_Object seguros_id_seguroOld = persistentEmpresas_Object.getSeguros_id_seguro();
            Seguros_Object seguros_id_seguroNew = empresas_Object.getSeguros_id_seguro();
            List<Empleados_Object> empleadosOld = persistentEmpresas_Object.getEmpleados();
            List<Empleados_Object> empleadosNew = empresas_Object.getEmpleados();
            List<Productos_Object> productosOld = persistentEmpresas_Object.getProductos();
            List<Productos_Object> productosNew = empresas_Object.getProductos();
            if (seguros_id_seguroNew != null) {
                seguros_id_seguroNew = em.getReference(seguros_id_seguroNew.getClass(), seguros_id_seguroNew.getId_seguro());
                empresas_Object.setSeguros_id_seguro(seguros_id_seguroNew);
            }
            List<Empleados_Object> attachedEmpleadosNew = new ArrayList<Empleados_Object>();
            for (Empleados_Object empleadosNewEmpleados_ObjectToAttach : empleadosNew) {
                empleadosNewEmpleados_ObjectToAttach = em.getReference(empleadosNewEmpleados_ObjectToAttach.getClass(), empleadosNewEmpleados_ObjectToAttach.getId_empleado());
                attachedEmpleadosNew.add(empleadosNewEmpleados_ObjectToAttach);
            }
            empleadosNew = attachedEmpleadosNew;
            empresas_Object.setEmpleados(empleadosNew);
            List<Productos_Object> attachedProductosNew = new ArrayList<Productos_Object>();
            for (Productos_Object productosNewProductos_ObjectToAttach : productosNew) {
                productosNewProductos_ObjectToAttach = em.getReference(productosNewProductos_ObjectToAttach.getClass(), productosNewProductos_ObjectToAttach.getId_producto());
                attachedProductosNew.add(productosNewProductos_ObjectToAttach);
            }
            productosNew = attachedProductosNew;
            empresas_Object.setProductos(productosNew);
            empresas_Object = em.merge(empresas_Object);
            if (seguros_id_seguroOld != null && !seguros_id_seguroOld.equals(seguros_id_seguroNew)) {
                seguros_id_seguroOld.getEmpresas().remove(empresas_Object);
                seguros_id_seguroOld = em.merge(seguros_id_seguroOld);
            }
            if (seguros_id_seguroNew != null && !seguros_id_seguroNew.equals(seguros_id_seguroOld)) {
                seguros_id_seguroNew.getEmpresas().add(empresas_Object);
                seguros_id_seguroNew = em.merge(seguros_id_seguroNew);
            }
            for (Empleados_Object empleadosOldEmpleados_Object : empleadosOld) {
                if (!empleadosNew.contains(empleadosOldEmpleados_Object)) {
                    empleadosOldEmpleados_Object.setEmpresas_id_empresa(null);
                    empleadosOldEmpleados_Object = em.merge(empleadosOldEmpleados_Object);
                }
            }
            for (Empleados_Object empleadosNewEmpleados_Object : empleadosNew) {
                if (!empleadosOld.contains(empleadosNewEmpleados_Object)) {
                    Empresas_Object oldEmpresas_id_empresaOfEmpleadosNewEmpleados_Object = empleadosNewEmpleados_Object.getEmpresas_id_empresa();
                    empleadosNewEmpleados_Object.setEmpresas_id_empresa(empresas_Object);
                    empleadosNewEmpleados_Object = em.merge(empleadosNewEmpleados_Object);
                    if (oldEmpresas_id_empresaOfEmpleadosNewEmpleados_Object != null && !oldEmpresas_id_empresaOfEmpleadosNewEmpleados_Object.equals(empresas_Object)) {
                        oldEmpresas_id_empresaOfEmpleadosNewEmpleados_Object.getEmpleados().remove(empleadosNewEmpleados_Object);
                        oldEmpresas_id_empresaOfEmpleadosNewEmpleados_Object = em.merge(oldEmpresas_id_empresaOfEmpleadosNewEmpleados_Object);
                    }
                }
            }
            for (Productos_Object productosOldProductos_Object : productosOld) {
                if (!productosNew.contains(productosOldProductos_Object)) {
                    productosOldProductos_Object.setEmpresas_id_empresa_p(null);
                    productosOldProductos_Object = em.merge(productosOldProductos_Object);
                }
            }
            for (Productos_Object productosNewProductos_Object : productosNew) {
                if (!productosOld.contains(productosNewProductos_Object)) {
                    Empresas_Object oldEmpresas_id_empresa_pOfProductosNewProductos_Object = productosNewProductos_Object.getEmpresas_id_empresa_p();
                    productosNewProductos_Object.setEmpresas_id_empresa_p(empresas_Object);
                    productosNewProductos_Object = em.merge(productosNewProductos_Object);
                    if (oldEmpresas_id_empresa_pOfProductosNewProductos_Object != null && !oldEmpresas_id_empresa_pOfProductosNewProductos_Object.equals(empresas_Object)) {
                        oldEmpresas_id_empresa_pOfProductosNewProductos_Object.getProductos().remove(productosNewProductos_Object);
                        oldEmpresas_id_empresa_pOfProductosNewProductos_Object = em.merge(oldEmpresas_id_empresa_pOfProductosNewProductos_Object);
                    }
                }
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "EMPRESA ACTUALIZADA", "EMPRESAS-JPAC", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = empresas_Object.getId_empresa();
                if (findEmpresas_Object(id) == null) {
                    throw new NonexistentEntityException("The empresas_Object with id " + id + " no longer exists.");
                }
            }
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR LA EMPRESA", "EMPRESAS-JPAC", JOptionPane.ERROR_MESSAGE);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // METODO PARA ELIMINAR UNA EMPRESA (MODELO)
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas_Object empresas_Object;
            try {
                empresas_Object = em.getReference(Empresas_Object.class, id);
                empresas_Object.getId_empresa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresas_Object with id " + id + " no longer exists.", enfe);
            }
            Seguros_Object seguros_id_seguro = empresas_Object.getSeguros_id_seguro();
            if (seguros_id_seguro != null) {
                seguros_id_seguro.getEmpresas().remove(empresas_Object);
                seguros_id_seguro = em.merge(seguros_id_seguro);
            }
            List<Empleados_Object> empleados = empresas_Object.getEmpleados();
            for (Empleados_Object empleadosEmpleados_Object : empleados) {
                empleadosEmpleados_Object.setEmpresas_id_empresa(null);
                empleadosEmpleados_Object = em.merge(empleadosEmpleados_Object);
            }
            List<Productos_Object> productos = empresas_Object.getProductos();
            for (Productos_Object productosProductos_Object : productos) {
                productosProductos_Object.setEmpresas_id_empresa_p(null);
                productosProductos_Object = em.merge(productosProductos_Object);
            }
            em.remove(empresas_Object);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "EMPRESA ELIMINADA CORRECTAMENTE", "EMPRESAS-JPAC", JOptionPane.INFORMATION_MESSAGE);
        } catch (NonexistentEntityException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR LA EMPRESA", "EMPRESAS-JPAC", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // METODO PARA OBTENER TODAS LAS EMPRESAS (MODELO)
    public List<Empresas_Object> findEmpresas_ObjectEntities() {
        try {
            return findEmpresas_ObjectEntities(true, -1, -1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER LAS EMPRESAS", "EMPRESAS-JPAC", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    // METODO PARA OBTENER TODAS LAS EMPRESAS (MODELO)
    public List<Empresas_Object> findEmpresas_ObjectEntities(int maxResults, int firstResult) {
        return findEmpresas_ObjectEntities(false, maxResults, firstResult);
    }

    // METODO PARA OBTENER TODAS LAS EMPRESAS (MODELO)
    private List<Empresas_Object> findEmpresas_ObjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresas_Object.class));
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

    // METODO PARA OBTENER UNA EMPRESA (MODELO)
    public Empresas_Object findEmpresas_Object(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresas_Object.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER LA EMPRESA", "EMPRESAS-JPAC", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            em.close();
        }
    }

    // METODO PARA OBTENER EL TOTAL DE EMPRESAS (MODELO)
    public int getEmpresas_ObjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresas_Object> rt = cq.from(Empresas_Object.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL TOTAL DE EMPRESAS", "EMPRESAS-JPAC", JOptionPane.ERROR_MESSAGE);
            return 0;
        } finally {
            em.close();
        }
    }

}
