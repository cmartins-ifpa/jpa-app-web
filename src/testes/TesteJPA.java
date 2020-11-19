package testes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidades.Cliente;

public class TesteJPA {

	public static void main(String[] args) {
		EntityManager entityMgr = Persistence
        		.createEntityManagerFactory("EclipseLinkExemploPU")
        		.createEntityManager();
		System.out.println("Ok ----- JPA");
		
		 
		Query query = entityMgr.createQuery("select c from Cliente c", Cliente.class);
		List<Cliente> clientes = query.getResultList();
		for (Cliente c1 : clientes) {
			System.out.println(c1.getNome() + " " + c1.getId());
		}
        

	}

}
