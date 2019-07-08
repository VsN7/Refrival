package br.com.refrival.controller;

import br.com.refrival.dao.OrcamentoDAO;
import br.com.refrival.dao.exceptions.NonexistentEntityException;
import br.com.refrival.model.Orcamento;
import br.com.refrival.model.Usuario;
import java.awt.Component;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.observablecollections.ObservableCollections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vitor
 */
public class OrcamentoController {
    private final OrcamentoDAO dao;
    private Orcamento orcamento;
    private List<Orcamento> orcamentos;
    static int id;
    static String login;

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
    
     public void edit(int id,String cliente,String cpf, String servico, String item, Calendar cInicio, Calendar cPrevisao, double valor, String observacoes, String situacao) throws Exception {
        Component rootPane = null;
         Orcamento orcamento = new Orcamento();
         UsuarioController us = new UsuarioController();
         Usuario usuario = new Usuario();
         usuario.setId(us.getId());
         orcamento.setId(id);
         orcamento.setCliente(cliente);
         orcamento.setCpf(cpf);
         orcamento.setServico(servico);
         orcamento.setItem(item);
         orcamento.setDataInicio(cInicio);
         orcamento.setPrevisaoEntrega(cPrevisao);
         orcamento.setPrevisaoEntrega(cPrevisao);
         orcamento.setObservacoes(observacoes);
         orcamento.setValor(valor);
         orcamento.setSituacao(situacao);
         orcamento.setUsuario(usuario);
         dao.edit(orcamento);
        JOptionPane.showMessageDialog(rootPane, "Alteração realizada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
        
     }

    public OrcamentoController() {
        orcamento = new Orcamento();
        dao = new OrcamentoDAO();
        orcamentos = ObservableCollections.observableList(new ArrayList<>());
        pesquisar();
    }
    
    private void pesquisar(){
        orcamentos.clear();
        orcamentos.addAll(dao.findOrcamentoEntities());
    }
    
    public void salvar() throws NoSuchAlgorithmException{
        Component rootPane = null;
        UsuarioController uc = new UsuarioController();
        Usuario usuario = new Usuario();
        usuario.setId(uc.getId());
        orcamento.setUsuario(usuario);
        dao.create(orcamento);
         
        JOptionPane.showMessageDialog(rootPane, "Cadastro realizado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
        
        orcamento = new Orcamento();
        pesquisar();
    }
    
    
    
    public void destroy(int id) throws NonexistentEntityException{
        Component rootPane = null;
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o orçamento?", "Excluir", JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            dao.destroy(id);
            
            JOptionPane.showMessageDialog(rootPane, "Exclusão realizada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
        
        }
    }
    
    

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }
    
    public void setOrcamentos(List<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
    }

    public List<Orcamento> getOrcamentos() {
        return orcamentos;
    }
    public void cancelar(){
        orcamento = new Orcamento();
    }
    public int primeiroPasso() throws Exception{
        return dao.primeiroPasso();
    }
    
    public void relatorioOrcamentosGeral(int id){
        dao.relatorioOrcamentosGeral(id);
    }
    
    public void relatorioOrcamentoAll(){
        dao.relatorioOrcamentosAll();
    }
    
    public void relatorioOrcamentoData(int combobox,String dInicio, String dFinal){
        Component rootPane = null;
        try{
            dao.relatorioOrcamentoData(combobox,dInicio,dFinal);
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Campos Invalidos", "Aviso", JOptionPane.ERROR_MESSAGE, null);
        }
    }
    
    public void editSituacao(int index, Calendar c, String situacao) throws Exception{
        Component rootPane = null;
        Orcamento orcamento = new Orcamento();
        UsuarioController us = new UsuarioController();
        Usuario usuario = new Usuario();
        usuario.setId(us.getId());
        getOrcamentos().get(index).setDataFinalizacao(c);
        getOrcamentos().get(index).setSituacao(situacao);
        dao.edit(getOrcamentos().get(index));
        if(getOrcamentos().get(index).getSituacao().equals("ABERTO")){
            
            JOptionPane.showMessageDialog(rootPane, "Orçamento reaberto com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
        
        }else{
            
            JOptionPane.showMessageDialog(rootPane, "Orçamento Finalizado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE, null);
        
        }
    }
    
    public void relatorioSituacao(int comboBox){
        dao.relatorioOrcamentoSituacao(comboBox);
    }
    
}
