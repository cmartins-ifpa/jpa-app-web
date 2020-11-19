package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import dao.ClienteDAO;
import entidades.Cliente;
import model.ClienteVO;

@Named 
@SessionScoped
public class ClienteMB implements Serializable {
	private ClienteVO cliente = new ClienteVO();	
	private List<ClienteVO> clientes;
	 
	static ClienteDAO dao = new ClienteDAO();
	
	public ClienteMB() {}
 	
	public String salvar() {
        // se o "id" do objeto "clienteVO" está NULL significa um "novo cliente"
		if (this.cliente.getId()==null) {
			cadastrarNovoCliente();
		} else
			atualizarCliente();
		return "";
	}
	
	public String novoCliente() {
		this.cliente = new ClienteVO();
		FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, 
	                		"Informe os dados do novo cliente", ""));	       
		return "";	
	}
	
    // método privado para incluir um novo cliente na base dados.
	private void cadastrarNovoCliente() {
		boolean incluiu = dao.incluir(cliente);
		if (incluiu)
		   FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cliente <"+cliente.getNome() + "> "
					+ " cadastrado com ID="+cliente.getId(), null));
		else
		   FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Operação!", null));
		// limpa o "VO" para incluir um novo
		this.cliente = new ClienteVO();			
	}

	// método privado para alterar os dados do cliente na base dados.
	private void atualizarCliente() {		
		boolean ok = dao.atualiza(this.cliente);
		if (ok)
		   FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Cliente <" + this.cliente.getNome()
						+ "> atualizado com sucesso!", null));
		else
		   FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Erro na Operação!", null));
		// limpa o "VO" para incluir um novo
		this.cliente = new ClienteVO();					
	}
	
	public void delete(String id) {
		int idPK = Integer.parseInt(id);	
		Cliente cli = dao.findById(idPK);
		dao.delete(idPK);       
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Cliente <" + cli.getNome()
						+ "> Excluído com sucesso!", null));
    }
	
	public String update(String id) {
		int idPK = Integer.parseInt(id);		 
	    Cliente cli = dao.findById(idPK);
	    this.cliente.setEndereco(cli.getEndereco());
	    this.cliente.setId(cli.getId());
	    this.cliente.setNome(cli.getNome());
	    return "";
	}
	
	// getters e setters
	public ClienteVO getCliente() {
		return this.cliente;
	}
	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}

	public List<ClienteVO> getClientes() {
		List<Cliente> clientsEnt = dao.getClientes();
		this.clientes = new ArrayList<ClienteVO>();
		for (Cliente cliente : clientsEnt) {			
			ClienteVO vo = new ClienteVO();
			vo.setEndereco(cliente.getEndereco());
			vo.setId(cliente.getId());
			vo.setNome(cliente.getNome());
			this.clientes.add(vo);	
		}		
		return this.clientes;
	}

	public void setClientes(List<ClienteVO> clientes) {
		this.clientes = clientes;
	}
 
}
