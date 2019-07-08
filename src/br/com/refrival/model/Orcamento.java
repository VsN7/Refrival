
package br.com.refrival.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Vitor
 */

@Entity(name = "Orcamento")
@NamedQueries({
        @NamedQuery(name = "Orcamento.selecionar", 
                query = "SELECT o FROM Orcamento o WHERE o.usuario.id = :id "),
        
        @NamedQuery(name = "Orcamento.valorTotal", 
                query = "SELECT SUM(o.valor) FROM Orcamento o"),
        
        @NamedQuery(name = "Orcamento.valorTotalD", 
                query = "SELECT SUM(o.valor) FROM Orcamento o where previsaoEntrega between :dInicio and :dFim AND (o.situacao = :situacao OR situacao = :s2)"),
        @NamedQuery(name = "Orcamento.valorTotalS", 
                query = "SELECT SUM(o.valor) FROM Orcamento o where (o.situacao = :situacao OR o.situacao = :s2)"),
})


public class Orcamento implements Serializable{

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double valor;
    @Temporal(TemporalType.DATE)
    private Calendar dataInicio;
    @Temporal(TemporalType.DATE)
    private Calendar dataFinalizacao;
    @Temporal(TemporalType.DATE)
    private Calendar previsaoEntrega;
    private String servico;
    private String item;
    private String cliente;
    private String observacoes;
    private String situacao;
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public Calendar getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Calendar dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @ManyToOne
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getPrevisaoEntrega() {
        return previsaoEntrega;
    }

    public void setPrevisaoEntrega(Calendar previsaoEntrega) {
        this.previsaoEntrega = previsaoEntrega;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
