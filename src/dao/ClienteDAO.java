package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import entidades.Cliente;
import entidades.Pedido;
import model.ClienteVO;
import model.PedidoVO;
import utilitarios.FabricaConexao;
import utilitarios.GeradorID;

public class ClienteDAO {
	private EntityManager em = FabricaConexao.getConexao();

	/**
	 * Inclui um objeto Cliente na base de dados
	 */
	public boolean incluir(ClienteVO vo) {
		// utiliza um objeto de transferência (VO)
		Cliente c = new Cliente();
		c.setId(GeradorID.geraNumeroID());
		c.setNome(vo.getNome());
		c.setEndereco(vo.getEndereco());
		vo.setId(c.getId()); // repassa o ID para o VO

		// trata os objetos de pedidos (formato "vo" para entity)
		List<PedidoVO> pedidosVO = vo.getPedidos();
		List<Pedido> pedidosEntity = new ArrayList<Pedido>();
		for (PedidoVO voPed : pedidosVO) {
			Pedido novoPedido = new Pedido();
			// o "id" é criado em Pedido()
			novoPedido.setData(voPed.getData());
			novoPedido.setNomeProduto(voPed.getNomeProduto());
			novoPedido.setQuantidade(voPed.getQuantidade());
			novoPedido.setValorTotal(voPed.getValorTotal());
			pedidosEntity.add(novoPedido);
		}
		c.setPedidos(pedidosEntity);

		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		return true;
	}

	/**
	 * Pesquisa e retorna um Cliente baseado na chave "id"
	 */
	public Cliente findById(Integer id) {
		Cliente c = em.find(Cliente.class, id);
		if (c == null) {
			throw new EntityNotFoundException("Cliente não localizado pelo ID " + id);
		}
		return c;
	}

	/**
	 * Recupera todos os clientes da base de dados 
	 * @return uma Lista de Clientes
	 */
	public List<Cliente> getClientes() {
		Query  query = em.createQuery("select c from Cliente c", Cliente.class);
		return query.getResultList();
	}

	/**
	 * Este método realiza o "update" dos dados do cliente.
	 * @param vo
	 */
	public boolean atualiza(ClienteVO vo) {
		// recupera o objeto persistente
		Cliente c = this.findById(vo.getId());
		c.setNome(vo.getNome());
		c.setEndereco(vo.getEndereco());

		// trata os objetos de pedidos (formato "vo" para entity)
		List<PedidoVO> pedidosVO = vo.getPedidos();
		List<Pedido> pedidosEntity = new ArrayList<Pedido>();
		for (PedidoVO voPed : pedidosVO) {
			Pedido novoPedido = new Pedido();
			// o "id" é criado em Pedido()
			novoPedido.setData(voPed.getData());
			novoPedido.setNomeProduto(voPed.getNomeProduto());
			novoPedido.setQuantidade(voPed.getQuantidade());
			novoPedido.setValorTotal(voPed.getValorTotal());
			pedidosEntity.add(novoPedido);
		}
		c.setPedidos(pedidosEntity);

		em.getTransaction().begin();
		em.merge(c); // merge -> faz o "update" no objeto persistente
		em.getTransaction().commit();
		return true;
	}

	/**
	 * Este método deleta um cliente já cadastrado.
	 * @param vo
	 */
	public boolean delete(ClienteVO vo) {
		// recupera o objeto persistente
		Cliente c = this.findById(vo.getId());
		if (c == null)
			return false;

		em.getTransaction().begin();
		em.remove(c);  // remove -> faz o "delete" no objeto persistente
		em.getTransaction().commit();
		return true;
	}

	// deleta usando o ID
	public boolean delete(int id) {
		// recupera o objeto persistente
		Cliente c = this.findById(id);
		if (c == null)
			return false;
		em.getTransaction().begin();
		em.remove(c);  // remove -> faz o "delete" no objeto persistente
		em.getTransaction().commit();
		return true;
	}

}
