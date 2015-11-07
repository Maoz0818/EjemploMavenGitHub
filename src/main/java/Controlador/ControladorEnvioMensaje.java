package Controlador;

import Modelo.EnviarCorreo;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ControladorEnvioMensaje {
    
    public boolean enviarCorreo(EnviarCorreo c){
        
        try{
            
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.port", "587");
            p.put("mail.smtp.user", c.getUsuarioCorreo());
            p.put("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p, null);
            BodyPart texto = new MimeBodyPart();
            texto.setText(c.getMensaje());
            
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(c.getUsuarioCorreo()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getDestino()));
            mensaje.setSubject(c.getAsunto());
            mensaje.setContent(m);
            
            Transport t = s.getTransport("smtp");
            t.connect(c.getUsuarioCorreo(), c.getContraseña());
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            return true;
            
        }catch(Exception e){
            System.out.println("Error" + e);
            return false;
        }
    } 
}
