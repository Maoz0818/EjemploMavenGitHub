package Controlador;

import DAO.UsuarioDao;
import Modelo.EnviarCorreo;
import Modelo.GenerarClave;
import Modelo.Usuario;
import Modelo.Validaciones;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;

public class PrincipalController implements Initializable {
    
    @FXML    private Label lblMensajeNombre;
    @FXML    private Label lblMensajeApellido;
    @FXML    private Label lblMensajeTelefono;
    @FXML    private Label lblMensajeCorreo;
    @FXML    private Label lblMensajeEstado;
    @FXML    private Label lblMensajeDireccion;
    @FXML    private RadioButton rbEstadoActivo;
    @FXML    private RadioButton rbEstadoInactivo;
    @FXML    private TextField txtTelefono;
    @FXML    private TextField txtCorreo;
    @FXML    private TextField txtApellidos;
    @FXML    private TextField txtNombres;
    @FXML    private TextField txtDireccion;
    
    EnviarCorreo c = new EnviarCorreo();
    private final Validaciones validacion = new Validaciones();
    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final GenerarClave claveGenerada = new GenerarClave();
    private int estadoUsuario;
    private String correo;
    private String clave;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void accionGuardar(ActionEvent event) {
        limpiarMensajes();
        
        //Validacion de campos vacios
        if (validacion.validarVacios(txtNombres.getText())) {
           lblMensajeNombre.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtNombres.getText())) {
           lblMensajeNombre.setText("este campo solo recibe letras");
           return;
        }
        
        if (validacion.validarVacios(txtApellidos.getText())) {
           lblMensajeApellido.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo letras
        if (validacion.soloLetras(txtApellidos.getText())) {
           lblMensajeApellido.setText("este campo solo recibe letras");
           return;
           
        }
                
        if (validacion.validarVacios(txtCorreo.getText())) {
           lblMensajeCorreo.setText("campo obligatorio");
           return;
        }
        
        //Validacion de sintaxis de correo
        if (!validacion.validarCorreo(txtCorreo.getText())) {
           lblMensajeCorreo.setText("correo invalido");
           return;
        }
        
         if (validacion.validarVacios(txtTelefono.getText())) {
           lblMensajeTelefono.setText("campo obligatorio");
           return;
        }
        
        //Validacion de campos solo numeros
        if (validacion.soloNumeros(txtTelefono.getText())) {
           lblMensajeTelefono.setText("este campo solo recibe numeros");
           return;
        }
        
        if (validacion.validarVacios(txtDireccion.getText())) {
           lblMensajeDireccion.setText("campo obligatorio");
           return;
        } 
                
        //validar el radiobuton
        if(!(rbEstadoActivo.isSelected() || rbEstadoInactivo.isSelected())){
           lblMensajeEstado.setText("debe seleccionar el estado");
           return;
        }
        
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String telefono = txtTelefono.getText();
        correo = txtCorreo.getText();
        if(rbEstadoActivo.isSelected()){
            estadoUsuario = 1;
        }
        
        if(rbEstadoInactivo.isSelected()){
            estadoUsuario = 2;
        }
        clave = claveGenerada.GenerarClave();
        String direccion = txtDireccion.getText();
        
        Usuario nuevoUsuario = new Usuario();
        
        nuevoUsuario.setEstado(estadoUsuario);
        nuevoUsuario.setNombres(nombres);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setDireccion(direccion);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setClave(DigestUtils.md2Hex(clave));
        
        PreparedStatement estado = usuarioDao.nuevoUsuario(nuevoUsuario);
        
            try {
                int n = estado.executeUpdate();
                JOptionPane.showMessageDialog(null,"Usuario registrado exitosamente");
                estado.close();
            }catch (SQLException e) {
            System.out.println("Error " + e);
            }
            
        limpiarCampos();
        
        enviarCorreo(clave, nuevoUsuario);
    }
    
    @FXML
    private void accionLimpiar(ActionEvent event) {
        limpiarCampos();
    }
    
    @FXML
    private void accionEstadoActivo(ActionEvent event) {
        rbEstadoInactivo.setSelected(false);
    }
    
    @FXML
    private void accionEstadoInactivo(ActionEvent event) {
        rbEstadoActivo.setSelected(false);
    }
    
    
    public void enviarCorreo(String clave, Usuario nuevoUsuario){
        c.setContraseña("vhsvlxyolduuyxrj");
        c.setUsuarioCorreo("correosbdsoft@gmail.com");
        c.setAsunto("Clave de Usuario BDSoft");
        c.setMensaje("Has sido registrado en BDSoft\n\nHola " + nuevoUsuario.getNombres() + ",\n\n Bienvenido a nuestra aplicación\n Tu clave generada es: " + clave + "\n\n No olvides cambiar tu clave para mayor seguridad.");
        c.setDestino(nuevoUsuario.getCorreo().trim());
        
        ControladorEnvioMensaje co = new ControladorEnvioMensaje();
        
        if(co.enviarCorreo(c)){
            JOptionPane.showMessageDialog(null, "Tu clave de acceso ha sido enviada a tu correo");
        }else{
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    
    private void limpiarMensajes(){
        lblMensajeNombre.setText("");
        lblMensajeApellido.setText("");
        lblMensajeDireccion.setText("");
        lblMensajeTelefono.setText("");
        lblMensajeCorreo.setText("");
        lblMensajeEstado.setText("");
    }
    
    private void limpiarCampos(){
        limpiarMensajes();
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }
}
