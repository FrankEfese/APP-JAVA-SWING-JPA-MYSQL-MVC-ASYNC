package PackageProductos;

import PackageEmpresas.Empresas_Object;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-28T18:22:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Productos_Object.class)
public class Productos_Object_ { 

    public static volatile SingularAttribute<Productos_Object, Double> precio;
    public static volatile SingularAttribute<Productos_Object, Empresas_Object> empresas_id_empresa_p;
    public static volatile SingularAttribute<Productos_Object, String> categoria;
    public static volatile SingularAttribute<Productos_Object, Date> f_alta;
    public static volatile SingularAttribute<Productos_Object, Integer> id_producto;
    public static volatile SingularAttribute<Productos_Object, Integer> stock;
    public static volatile SingularAttribute<Productos_Object, String> nombre;
    public static volatile SingularAttribute<Productos_Object, String> identificador;

}