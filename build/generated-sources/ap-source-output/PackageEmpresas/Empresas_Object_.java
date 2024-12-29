package PackageEmpresas;

import PackageEmpleados.Empleados_Object;
import PackageProductos.Productos_Object;
import PackageSeguros.Seguros_Object;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-28T18:22:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Empresas_Object.class)
public class Empresas_Object_ { 

    public static volatile SingularAttribute<Empresas_Object, String> id_empresarial;
    public static volatile SingularAttribute<Empresas_Object, String> ciudad;
    public static volatile SingularAttribute<Empresas_Object, Date> f_alta;
    public static volatile SingularAttribute<Empresas_Object, Seguros_Object> seguros_id_seguro;
    public static volatile SingularAttribute<Empresas_Object, Integer> id_empresa;
    public static volatile ListAttribute<Empresas_Object, Empleados_Object> empleados;
    public static volatile SingularAttribute<Empresas_Object, String> nombre;
    public static volatile ListAttribute<Empresas_Object, Productos_Object> productos;

}