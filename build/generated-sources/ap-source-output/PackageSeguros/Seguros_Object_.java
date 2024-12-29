package PackageSeguros;

import PackageEmpresas.Empresas_Object;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-28T18:22:48", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Seguros_Object.class)
public class Seguros_Object_ { 

    public static volatile SingularAttribute<Seguros_Object, Double> precio;
    public static volatile SingularAttribute<Seguros_Object, Integer> id_seguro;
    public static volatile SingularAttribute<Seguros_Object, Date> f_alta;
    public static volatile ListAttribute<Seguros_Object, Empresas_Object> empresas;
    public static volatile SingularAttribute<Seguros_Object, String> nombre;

}