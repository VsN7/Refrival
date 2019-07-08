package br.com.refrival.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Orcamento.class)
public abstract class Orcamento_ {

	public static volatile SingularAttribute<Orcamento, Calendar> previsaoEntrega;
	public static volatile SingularAttribute<Orcamento, String> cliente;
	public static volatile SingularAttribute<Orcamento, String> observacoes;
	public static volatile SingularAttribute<Orcamento, String> item;
	public static volatile SingularAttribute<Orcamento, String> situacao;
	public static volatile SingularAttribute<Orcamento, Double> valor;
	public static volatile SingularAttribute<Orcamento, String> cpf;
	public static volatile SingularAttribute<Orcamento, Usuario> usuario;
	public static volatile SingularAttribute<Orcamento, Integer> id;
	public static volatile SingularAttribute<Orcamento, Calendar> dataInicio;
	public static volatile SingularAttribute<Orcamento, String> servico;
	public static volatile SingularAttribute<Orcamento, Calendar> dataFinalizacao;

}

