package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import utilitarios.GeradorID;

@Entity
@Table(name = "tb_pedido")
public class Pedido implements Serializable  {
	  
	@Id private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)  // DATE - retorna apenas a data 
	private Date data;
	
	private String nomeProduto;
	private Integer quantidade;

	@Column(name = "valor_total", precision=18, scale=2)
	private BigDecimal valorTotal;

	public Pedido() {
	  // cria uma chave numérica única (ID)	
	  this.id =	GeradorID.geraNumeroID(); 
	}

	// getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}


}
