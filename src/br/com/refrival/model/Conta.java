/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.refrival.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vitor
 */


@Entity(name = "Conta")
@NamedQueries({
        @NamedQuery(name = "Conta.filtroData", 
                query = "SELECT c FROM Conta c WHERE MONTH(c.dataCompra) = :mes and c.usuario.id = :id "),
    
        @NamedQuery(name = "Conta.consultaId", 
                query = "SELECT u.id FROM Usuario u WHERE u.login = :login and u.senha = :senha "),
        
        @NamedQuery(name = "Conta.selecionar", 
                query = "SELECT c FROM Conta c WHERE c.usuario.id = :id "),
        
        @NamedQuery(name = "Conta.selecionarValorPagar", 
                query = "SELECT c.valorPagar FROM Conta c WHERE c.id = :id "),
        
        @NamedQuery(name = "Conta.selecionarVezesPagar", 
                query = "SELECT c.vezesPagar FROM Conta c WHERE c.id = :id "),
        
        @NamedQuery(name = "Conta.selecionarSituacao", 
                query = "SELECT c.situacao FROM Conta c WHERE c.id = :id "),
        
        @NamedQuery(name = "Conta.selecionarValor", 
                query = "SELECT c.valor FROM Conta c WHERE c.id = :id "),
        
        @NamedQuery(name = "Conta.selecionarVezes", 
                query = "SELECT c.vezes FROM Conta c WHERE c.id = :id "),
        
        @NamedQuery(name = "Conta.filtroSituacao", 
                query = "SELECT c FROM Conta c WHERE c.situacao = :situacao and c.usuario.id = :id "),
        
        @NamedQuery(name = "Conta.filtroDataVencimento", 
                query = "SELECT c FROM Conta c WHERE MONTH(c.dataCompra) = :mes and c.situacao = 'Aberto' and c.usuario.id = :id "),
        
        @NamedQuery(name = "Conta.selecionarVencimento", 
                query = "SELECT c.dataVencimento FROM Conta c WHERE c.id = :id"),
        
        @NamedQuery(name = "Conta.filtroVencimento", 
                query = "SELECT c FROM Conta c WHERE MONTH(c.dataVencimento) = :mes and c.situacao = 'Aberto' and c.usuario.id = :id "),
        
})
public class Conta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String titulo;
    private double valor;
    private boolean parcelado;
    private int vezes;
    private String situacao;
    private double valorPagar;
    private int vezesPagar;
    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    public int getVezesPagar() {
        return vezesPagar;
    }

    public void setVezesPagar(int vezesPagar) {
        this.vezesPagar = vezesPagar;
    }
    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    @Temporal(TemporalType.DATE)
    private Calendar dataCompra;
    @Temporal(TemporalType.DATE)
    private Calendar dataVencimento;

    public Calendar getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Calendar dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
    @ManyToOne
    private Usuario usuario;
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Conta(String titulo, double valor, boolean parcelado, int vezes, Calendar dataCompra, String situacao) {
        this.titulo = titulo;
        this.valor = valor;
        this.parcelado = parcelado;
        this.vezes = vezes;
        this.dataCompra = dataCompra;
        this.situacao = situacao;
    }

    public EntityManager getEntityManager() {
        EntityManagerFactory emf = null;
        emf = Persistence.createEntityManagerFactory("ExemploLoginPU");
        return emf.createEntityManager();
    }
    

    public Conta() {
        this.dataCompra = Calendar.getInstance();
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isParcelado() {
        return parcelado;
    }

    public void setParcelado(boolean parcelado) {
        this.parcelado = parcelado;
    }

    public int getVezes() {
        return vezes;
    }

    public void setVezes(int vezes) {
        this.vezes = vezes;
    }

    public Calendar getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Calendar dataCompra) {
        this.dataCompra = dataCompra;
    }
    

}
