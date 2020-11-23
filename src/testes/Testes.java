package testes;

import java.io.PrintStream;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Cliente;
import utilitarios.FabricaConexao;

public class Testes {

	public static void main(String[] args) {
		EntityManager entityMgr = FabricaConexao.getConexao();
		System.out.println("Ok. Conexao estabelecida via JPA.");
		// deleta(entityMgr);
		listaClientes(entityMgr);
	}
	
	private static void deleta(EntityManager em) {
		Cliente c = em.find(Cliente.class, 1389241514);
		if (c == null) {
			System.err.println("NAO ENCONTROU O CLIENTE!");
			return;
		}
		// Exclui o objeto/registro localizado pelo ID	
		em.getTransaction().begin();
		em.remove(c);  // remove -> faz o "delete" no objeto persistente
		em.getTransaction().commit();
		System.err.println("O CLIENTE FOI DELETADO!");
	}

	private static void listaClientes(EntityManager entityMgr) {
		Query query = entityMgr.createQuery("select c from Cliente c", Cliente.class);
		List<Cliente> clientes = query.getResultList();
		 
		StringBuilder sb = new StringBuilder();
		try (// Send all output to the Appendable object sb
		Formatter formatter = new Formatter(sb, Locale.FRANCE)) {
			formatter.format("%1$-30s %2$10s \n", "Nome", "Id");  
			formatter.format("%1$-30s %2$10s \n", "-----------------------", "----------");  			
			for (Cliente c1 : clientes) {		    
				formatter.format("%1$-30s %2$10s \n", c1.getNome(), c1.getId());		    	
			}
			System.out.println(formatter.toString());
		}
		
	}
}
