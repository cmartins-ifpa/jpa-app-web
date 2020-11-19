package testes;

import java.util.List;

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
		for (Cliente c1 : clientes) {
			System.out.println(c1.getNome() + " " + c1.getId());
		}

	}
}
