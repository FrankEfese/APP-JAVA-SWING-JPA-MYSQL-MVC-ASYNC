package PackageEmpleados;

import PackageEmpresas.Empresas_Object;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-28T18:22:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Empleados_Object.class)
public class Empleados_Object_ { 

    public static volatile SingularAttribute<Empleados_Object, Empresas_Object> empresas_id_empresa;
    public static volatile SingularAttribute<Empleados_Object, Date> f_alta;
    public static volatile SingularAttribute<Empleados_Object, Integer> telefono;
    public static volatile SingularAttribute<Empleados_Object, String> nombre;
    public static volatile SingularAttribute<Empleados_Object, Integer> edad;
    public static volatile SingularAttribute<Empleados_Object, String> dni;
    public static volatile SingularAttribute<Empleados_Object, Integer> id_empleado;

}