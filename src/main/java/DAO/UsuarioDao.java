
package DAO;

import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UsuarioDao {
    
    // Variable para conexion    
    private Connection conexion;
    
     // SQL para registrar un nuevo usuario
    public PreparedStatement nuevoUsuario(Usuario nuevoUsuario){
        
        PreparedStatement estado = null;
                
        try {   conexion = ConexionDao.Conectar();
            String sql = "INSERT INTO USUARIOS"
                    + " (NOMBRES, APELLIDOS, CORREO, TELEFONO, ESTADOID, CLAVE, DIRECCION) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            estado = conexion.prepareStatement(sql);
         
            estado.setString(1, nuevoUsuario.getNombres());
            estado.setString(2, nuevoUsuario.getApellidos());
            estado.setString(3, nuevoUsuario.getCorreo());
            estado.setString(4, nuevoUsuario.getTelefono());
            estado.setInt(5, nuevoUsuario.getEstado());
            estado.setString(6, nuevoUsuario.getClave());
            estado.setString(7, nuevoUsuario.getDireccion());

                        
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        
        return estado;
    }
    
}
