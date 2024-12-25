package PackageTools;

import java.util.regex.Pattern;


public class Validaciones {
    
    
    // METODO PARA VALIDAR LOS CAMPOS DE CORREO Y CONTRASEÑA
    public static boolean validarLogin(String correo, String contraseña) {
        return Pattern.matches("^.{1,25}$", correo) && 
               Pattern.matches("^.{1,25}$", contraseña);
        
    }
}
