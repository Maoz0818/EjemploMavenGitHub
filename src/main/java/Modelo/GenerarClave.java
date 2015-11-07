
package Modelo;

import org.apache.commons.codec.digest.DigestUtils;

public class GenerarClave {
    
    private final String cadena = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    public String GenerarClave(){
        
        int longitudCad = cadena.length(); 
        String claveGenerada = "";
        String caracter = "";
    
        for (int i = 0; i < 8; i++) {
        claveGenerada+=(cadena.charAt((int)(Math.random() * cadena.length())));
	}

        return claveGenerada;
   } 
    
}
