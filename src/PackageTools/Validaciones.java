package PackageTools;

import java.util.regex.Pattern;


public class Validaciones {
    
    
    // METODO PARA VALIDAR LOS CAMPOS DE CORREO Y CONTRASEÑA
    public static boolean validarLogin(String correo, String contraseña) {
        return Pattern.matches("^.{1,25}$", correo) && 
               Pattern.matches("^.{1,25}$", contraseña);
        
    }
    
    // METODO PARA VALIDAR EL NOMBRE DEL SEGURO
    public static boolean validarSeguro(String nombre) {
        return Pattern.matches("^.{1,25}$", nombre);
        
    }
    
    
    // METODO PARA VALIDAR LOS CAMPOS DE ID-EMP , NOMBRE Y CIUDAD
    public static boolean validarEmpresa(String idEmp, String nombre, String ciudad) {
        return Pattern.matches("^.{1,25}$", idEmp) && 
               Pattern.matches("^.{1,25}$", nombre)&& 
               Pattern.matches("^.{1,25}$", ciudad);
        
    }
}
