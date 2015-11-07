package Controlador;

import Modelo.EnviarCorreo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

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
    @FXML    private TextField txtDireccion;
    
    EnviarCorreo c = new EnviarCorreo();
    
    @FXML
    private void accionGuardar(ActionEvent event) {
        enviarCorreo();
    }
    
    @FXML
    private void accionLimpiar(ActionEvent event) {
        
    }
    
    @FXML
    private void accionEstadoActivo(ActionEvent event) {
        
    }
    
    @FXML
    private void accionEstadoInactivo(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void enviarCorreo(){
        c.setContraseña("vhsvlxyolduuyxrj");
        c.setUsuarioCorreo("correosbdsoft@gmail.com");
        c.setAsunto("Clave de usuario");
        c.setMensaje("12345");
        c.setDestino("maoz182009@hotmail.com");
        
        ControladorEnvioMensaje co = new ControladorEnvioMensaje();
        
        if(co.enviarCorreo(c)){
            JOptionPane.showMessageDialog(null, "Se envio el correo");
        }else{
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
}
