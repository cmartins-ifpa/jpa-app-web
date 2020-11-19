package utilitarios;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
   public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Sessão criada " + event.getSession().getId());
        //  aqui pode ser usado para abrir a conexao ao banco.
   }

   public void sessionDestroyed(HttpSessionEvent event) {
        String ultimoAcesso = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"))
        		.format(new Date(event.getSession().getLastAccessedTime()));
        System.out.println("Sessão expirada "+event.getSession().getId()
        		           +". Ultimo Acesso = "+ultimoAcesso);
        // aqui pode ser usado para fechar as conexoes
   }
}
