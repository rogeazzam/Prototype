package com.example.Prototype.client;

import com.example.Prototype.client.ocsf.AbstractServer;
import com.example.Prototype.client.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer extends AbstractServer {
	private static Session session;

	public SimpleServer(int port) {
		super(port);
		
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Movie.class);
		configuration.addAnnotatedClass(MovieList.class);
		configuration.addAnnotatedClass(Branch.class);
		configuration.addAnnotatedClass(BranchesList.class);
		configuration.addAnnotatedClass(Time.class);
		ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private static List<Branch> getAllBranches() throws Exception {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Branch> query = builder.createQuery(Branch.class);
			query.from(Branch.class);
			List<Branch> data = session.createQuery(query).getResultList();
			return data;
	}

	private static List<Movie> getAllMovies() throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		List<Movie> data = session.createQuery(query).getResultList();
		return data;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		System.out.println(msgString);
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			if (msgString.startsWith("#showBranches")) {
				try {
					List<Branch> branches=getAllBranches();
					BranchesList branchesList=new BranchesList();
					for(Branch branch:branches)
						branchesList.setBranch(branch);
					client.sendToClient(branchesList);
					System.out.format("Sent branches to client %s\n", client.getInetAddress().getHostAddress());
					session.getTransaction().commit();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(msgString.startsWith("#showMovies")){
				try {
					List<Movie> movies=getAllMovies();
					MovieList movieList=new MovieList();
					for(Movie movie:movies)
						movieList.setMovies(movie);
					client.sendToClient(movieList);
					System.out.format("Sent movies to client %s\n", client.getInetAddress().getHostAddress());
					session.getTransaction().commit();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception var10) {
			if (session != null) {
				session.getTransaction().rollback();
			}

			System.err.println("An error occured, changes have been rolled back.");
			var10.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

}