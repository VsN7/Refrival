/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.refrival.dao;

import br.com.refrival.controller.ContaController;
import br.com.refrival.controller.UsuarioController;
import br.com.refrival.dao.exceptions.NonexistentEntityException;
import br.com.refrival.model.Conta;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author vitor
 */
public class ContaDAO implements Serializable {
    
    private static Conta cont;

    public ContaDAO() {
        this.emf = Persistence.createEntityManagerFactory("gerenciadorContas");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conta conta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(conta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conta conta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            conta = em.merge(conta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = conta.getId();
                if (findConta(id) == null) {
                    throw new NonexistentEntityException("The conta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conta conta;
            try {
                conta = em.getReference(Conta.class, id);
                conta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conta with id " + id + " no longer exists.", enfe);
            }
            em.remove(conta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public int primeiroPasso() throws Exception{
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        ContaController cc = new ContaController();
        List<Conta> contas = null;
       try{
           contas = em.createNamedQuery("Conta.selecionar").setParameter("id",uc.getId()).getResultList();
           cc.setContas(contas);
           if(cc.getContas().get(0).getId()!=0){
                return 1;
           }
           else
               return 0;
       } catch (Exception e){
           return 0;
       }
       
   }

    public List<Conta> findContaEntities() {
        return findContaEntities(true, -1, -1);
    }

    public List<Conta> findContaEntities(int maxResults, int firstResult) {
        return findContaEntities(false, maxResults, firstResult);
    }

    private List<Conta> findContaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Conta findConta(int id) {
        EntityManager em = getEntityManager();
        try {
            List<Conta> contas = null;
            contas = em.createNamedQuery("Conta.filtroData").getResultList();
            return em.find(Conta.class, id);
        } finally {
            em.close();
        }
    }

    public int getContaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conta> rt = cq.from(Conta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Conta> listarContaPorMes(int mes) {
        List<Conta> contas = null;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = em.createNamedQuery("Conta.filtroData").setParameter("mes", mes).setParameter("id",uc.getId()).getResultList();
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return null;
        }
    }
    
    
    public List<Conta> listarContaPorMesP(int mes) {
        List<Conta> contas = null;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = em.createNamedQuery("Conta.filtroVencimento").setParameter("mes", mes).setParameter("id",uc.getId()).getResultList();
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return null;
        }
    }
    
    public List<Conta> listarContaPorSituacao(int index) {
        List<Conta> contas = null;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        String situacao="";
        try{
            if(index==1){
                situacao="Aberto";
            }else{
                if(index == 2){
                    situacao="Fechado";
                }
            }
            
           contas = em.createNamedQuery("Conta.filtroSituacao").setParameter("situacao", situacao).setParameter("id",uc.getId()).getResultList();
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return null;
        }
    }
    
    
    
    
    public List<Conta> buscaConta() {
        List<Conta> contas = null;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = em.createNamedQuery("Conta.selecionar").setParameter("id",uc.getId()).getResultList();
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return null;
        }
    }
    
    public double buscaContaValor(int id) {
        double contas = 0;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = (Double) em.createNamedQuery("Conta.selecionarValor").setParameter("id",id).getSingleResult();
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 226 (ContaDAO)!");
            return 0;
        }
    }
    
    
    
    public int buscaContaVezes(int id) {
        int contas = 0;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = (int) em.createNamedQuery("Conta.selecionarVezes").setParameter("id",id).getSingleResult();
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return 0;
        }
    }
    
    public double buscaContaValorPagar(int id) {
        double contas = 0;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = (double) em.createNamedQuery("Conta.selecionarValorPagar").setParameter("id",id).getSingleResult();
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return 0;
        }
    }
    
    public int buscaContaVezesPagar(int id) {
        int contas = 0;
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = (int) em.createNamedQuery("Conta.selecionarVezesPagar").setParameter("id",id).getSingleResult();
            
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 268 (ContaDAO)!");
            return 0;
        }
    }
    
    public Calendar buscaContaVencimento(int id) {
        Calendar contas = Calendar.getInstance();
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = (Calendar) em.createNamedQuery("Conta.selecionarVencimento").setParameter("id",id).getSingleResult();
            
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return contas;
        }
    }
    
    
    
    
    
    public void update(Conta conta,int id, double valor) {
        EntityManager em = getEntityManager();
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            conta.setValorPagar(valor);
            conta.setId(id);
            cont = conta;
            conta = em.merge(conta);
            em.getTransaction().commit();
           
        }catch(Exception e){
            System.out.println("Erro na linha 187 (ContaDAO)!");
        }
    }
    
    public void updateVencimento(Conta conta,int id, Calendar c) {
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            conta.setDataVencimento(c);
            conta.setId(id);
            cont = conta;
            conta = em.merge(conta);
            em.getTransaction().commit();
           
        }catch(Exception e){
            System.out.println("Erro na linha 187 (ContaDAO)!");
        }
    }
    
   public Conta retornaConta(){
        return cont;
    }
   public double retornaContaValor(){
        return cont.getValor();
    }
   public int retornaContaIndex(){
        return cont.getVezes();
    }
   public String retornaContaSituacao(int id){
        String contas = "";
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
           contas = (String) em.createNamedQuery("Conta.selecionarSituacao").setParameter("id",id).getSingleResult();
            
           return contas;
        }catch(Exception e){
            System.out.println("Erro na linha 147 (ContaDAO)!");
            return "false";
        }
    }
    
    public void reduzParcela(Conta conta,int vezes, int id) {
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            conta.setVezesPagar(vezes);
            conta.setId(id);
            cont = conta;
            conta = em.merge(conta);
            em.getTransaction().commit();
           
        }catch(Exception e){
            System.out.println("Erro na linha 187 (ContaDAO)!");
        }
    }
    
    public void alterarSituacao(Conta conta, int id) {
        EntityManager em = getEntityManager();
        UsuarioController uc = new UsuarioController();
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            conta.setSituacao("Fechado");
            conta.setId(id);
            cont = conta;
            conta = em.merge(conta);
            em.getTransaction().commit();
           
        }catch(Exception e){
            System.out.println("Erro na linha 187 (ContaDAO)!");
        }
    }
    
}
