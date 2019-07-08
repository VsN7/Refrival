/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.refrival.dao;

import br.com.refrival.conectaRelatorio.ConnectionFactory;
import br.com.refrival.controller.OrcamentoController;
import br.com.refrival.controller.UsuarioController;
import br.com.refrival.dao.exceptions.NonexistentEntityException;
import br.com.refrival.model.Orcamento;
import java.awt.Component;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author vitor
 */
public class OrcamentoDAO implements Serializable {
    private static EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        if (emf == null){
            emf = Persistence.
                    createEntityManagerFactory("gerenciadorContas");
        }
        return emf.createEntityManager();
    }

    public void create(Orcamento orcamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(orcamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orcamento orcamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            orcamento = em.merge(orcamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orcamento.getId();
                if (findOrcamento(id) == null) {
                    throw new NonexistentEntityException("The orcamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void editSituacao(Orcamento orcamento) throws NonexistentEntityException{
    
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            orcamento = em.merge(orcamento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orcamento.getId();
                if (findOrcamento(id) == null) {
                    throw new NonexistentEntityException("The orcamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orcamento orcamento;
            try {
                orcamento = em.getReference(Orcamento.class, id);
                orcamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orcamento with id " + id + " no longer exists.", enfe);
            }
            em.remove(orcamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orcamento> findOrcamentoEntities() {
        return findOrcamentoEntities(true, -1, -1);
    }

    public List<Orcamento> findOrcamentoEntities(int maxResults, int firstResult) {
        return findOrcamentoEntities(false, maxResults, firstResult);
    }

    private List<Orcamento> findOrcamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try {
            
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orcamento.class));
            Query q = em.createNamedQuery("Orcamento.selecionar").setParameter("id",uc.getId());
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Orcamento findOrcamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orcamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrcamentoCount() {
        EntityManager em = getEntityManager();
     
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orcamento> rt = cq.from(Orcamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    

    public int primeiroPasso() throws Exception{
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        OrcamentoController oc = new OrcamentoController();
        List<Orcamento> orcamentos = null;
       try{
           orcamentos = em.createNamedQuery("Orcamento.selecionar").setParameter("id",uc.getId()).getResultList();
           oc.setOrcamentos(orcamentos);
           if(oc.getOrcamentos().get(0).getId()!=0){
                return 1;
           }
           else
               return 0;
       } catch (Exception e){
           return 0;
       }
       
   }
    
        public double retornaValorTotal(){
            EntityManager em = getEntityManager();
            double id;
            try{
                Query query = em.createNamedQuery("Orcamento.valorTotal");
                id = (double) query.getSingleResult();
                return id;
            }catch (Exception e){
                System.out.println("Erro ao 218" + e.getMessage());
                return 0;
            }
        }
        
        public double retornaValorTotalD(Date dInicio, Date dFim,String situacao,String s2){
            EntityManager em = getEntityManager();
            double id;
            Calendar c = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            
            try{
                Query query = em.createNamedQuery("Orcamento.valorTotalD");
                c.setTime(dInicio);
                query.setParameter("dInicio", c);
                
                c2.setTime(dFim);
                query.setParameter("dFim", c2);
                query.setParameter("situacao", situacao);
                query.setParameter("s2", s2);
                id = (double) query.getSingleResult();
                return id;
            }catch (Exception e){
                System.out.println("Erro ao 218" + e.getMessage());
                return 0;
            }
        }
        
        public double retornaValorTotalS(String situacao,String s2){
            EntityManager em = getEntityManager();
            double id;
            
            try{
                Query query = em.createNamedQuery("Orcamento.valorTotalS");
                query.setParameter("situacao", situacao);
                query.setParameter("s2", s2);
                id = (double) query.getSingleResult();
                return id;
            }catch (Exception e){
                System.out.println("Erro ao 218" + e.getMessage());
                return 0;
            }
        }
    
    public void relatorioOrcamentosGeral(int id){
        Connection conn;
        JasperPrint jasperPrint = null;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
        
        
        String src = "C:\\Refrival\\relatorios\\orcamentos.jasper";
        Map param = new HashMap();
        param.put("id",id);
        jasperPrint = JasperFillManager.fillReport(src, param, conn);
        } catch (Exception ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JasperViewer jv = new JasperViewer(jasperPrint, false);
        
        jv.setVisible(true);
        jv.setExtendedState(MAXIMIZED_BOTH);
    }
    
    public void relatorioOrcamentosAll(){
        Connection conn;
        JasperPrint jasperPrint = null;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
        
        
        String src = "C:\\Refrival\\relatorios\\orcamentosAll.jasper";
        
        
        Map param = new HashMap();
        param.put("total",this.retornaValorTotal());
        jasperPrint = JasperFillManager.fillReport(src, param, conn);
        } catch (Exception ex) {
            Logger.getLogger(OrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JasperViewer jv = new JasperViewer(jasperPrint, false);
        
        jv.setVisible(true);
        jv.setExtendedState(MAXIMIZED_BOTH);
    }
    
    public void relatorioOrcamentoData(int comboBox,String dInicio, String dFim){
        Connection conn;
        Component rootPane = null;
        JasperPrint jasperPrint = null;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
        
        
        String src = "C:\\Refrival\\relatorios\\orcamentosData.jasper";
        String situacao;
        String s2;
        if(comboBox ==0){
            situacao = "ABERTO";
            s2 = "ABERTO";
        }else{
            if(comboBox==1){
                situacao = "FECHADO";
                s2 = "FECHADO";
            }else{
                situacao = "ABERTO";
                s2 = "FECHADO";
            }
            
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Map param = new HashMap();
        param.put("total",this.retornaValorTotalD(sdf.parse(dInicio),sdf.parse(dFim),situacao,s2));
        param.put("datainicio",sdf.parse(dInicio));
        param.put("dataFim",sdf.parse(dFim));
        param.put("situacoes",situacao);
        param.put("s2", s2);
        jasperPrint = JasperFillManager.fillReport(src, param, conn);
        JasperViewer jv = new JasperViewer(jasperPrint, false);
        
        jv.setVisible(true);
        jv.setExtendedState(MAXIMIZED_BOTH);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Campos Invalidos", "Aviso", JOptionPane.ERROR_MESSAGE, null);
        }
    }
    
    
     public void relatorioOrcamentoSituacao(int comboBox){
        Connection conn;
        Component rootPane = null;
        JasperPrint jasperPrint = null;
        try {
            conn = ConnectionFactory.getInstance().getConnection();
        
        
        String src = "C:\\Refrival\\relatorios\\orcamentosAllSituacao.jasper";
        String situacao;
        String s2;
        if(comboBox ==0){
            situacao = "ABERTO";
            s2 = "ABERTO";
        }else{
            if(comboBox==1){
                situacao = "FECHADO";
                s2 = "FECHADO";
            }else{
                situacao = "ABERTO";
                s2 = "FECHADO";
            }
            
        }
        Map param = new HashMap();
        param.put("total",this.retornaValorTotalS(situacao,s2));
        param.put("situacao",situacao);
        param.put("s2", s2);
        jasperPrint = JasperFillManager.fillReport(src, param, conn);
        
        
        JasperViewer jv = new JasperViewer(jasperPrint, false);
        
        jv.setVisible(true);
        jv.setExtendedState(MAXIMIZED_BOTH);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Campos Invalidos", "Aviso", JOptionPane.ERROR_MESSAGE, null);
        }
    }
    
}

