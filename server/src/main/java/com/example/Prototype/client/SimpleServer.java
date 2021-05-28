package com.example.Prototype.client;

import com.example.Prototype.client.ocsf.AbstractServer;
import com.example.Prototype.client.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

	public static <T> List<T> getAllMovies(Class<T> object) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			InitlaizeData();
			if (msgString.startsWith("#showBranches")) {
				try {
					List<Branch> branches=getAllBranches();
					BranchesList branchesList=new BranchesList();
					for(Branch branch:branches)
						branchesList.setBranch(branch);
					client.sendToClient(branchesList);
					System.out.format("Sent branches to client %s\n", client.getInetAddress().getHostAddress());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(msgString.startsWith("#showMovies")){
				try {
					List<Movie> movies= SimpleServer.getAllMovies(Movie.class);
					MovieList movieList=new MovieList();
					for(Movie movie:movies) {
						movieList.setMovies(movie);
					}/*
					MovieList movieList=new MovieList();
					movieList.setMovies(new Movie("sasd","aldk","mjf","images/2.jpg","sfasf dfd",
							new Time(22,12,15,"sd","55")));*/
					client.sendToClient(movieList);
					System.out.format("Sent movies to client %s\n", client.getInetAddress().getHostAddress());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			session.getTransaction().commit();
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

	private void InitlaizeData(){
		Time time1=new Time(22,12,2021,"20:30","22:00");
		Time time2=new Time(1,5,2020,"18:55","20:20");

		session.save(time1);
		session.flush();
		session.save(time2);
		session.flush();

		Movie movie1=new Movie("The Godfather","Al pacino","Francis Ford","images/3.jpg",
				"The Godfather is a classic movie released in 1972",time1);
		session.save(movie1);
		session.flush();
		Movie movie2=new Movie("Interstellar","Matthew Mchounghy","Christopher Nolan",
				"images/4.jpg","Interstellar is fantasy movie about space released in 2014",time2);
		session.save(movie2);
		session.flush();
		Movie movie3=new Movie("Joker","Joaquin Phoenix","Todd Phillips",
				"images/joker.jpg","Joker is a drama/crime movie made in 2019.",time1);
		session.save(movie3);
		session.flush();
		Movie movie4=new Movie("Fifty shades of grey","Dakota Johnson","Sam Taylor",
				"images/fifty_shades.jpg","fifty shades of grey is a romance/drama movie made in 2015",time1);
		session.save(movie4);
		session.flush();
		Movie movie5=new Movie("The hangover","Bradley Cooper","Todd Phillips",
				"images/hangover.jpg","The hangover is a comedy movie made in 2009",time2);
		session.save(movie5);
		session.flush();
		MovieList movies1=new MovieList();
		movies1.setMovies(movie1);
		movies1.setMovies(movie2);
		session.save(movies1);
		session.flush();
		MovieList movies2=new MovieList();
		movies2.setMovies(movie3);
		movies2.setMovies(movie4);
		movies2.setMovies(movie5);
		session.save(movies2);
		session.flush();
		Branch branch1=new Branch("yes planet","images/firstCinema.jpg","Haifa","Lev Hamifratz");
		Branch branch2=new Branch("Hot","images/SecondCinema.jpg","Bialek","Keryon");
		branch1.setMovie(movies1);
		branch2.setMovie(movies2);
		session.save(branch1);
		session.flush();
		session.save(branch2);
		session.flush();
		BranchesList branchesList=new BranchesList();
		branchesList.setBranch(branch1);
		branchesList.setBranch(branch2);
		session.save(branchesList);
		session.flush();

	}

}