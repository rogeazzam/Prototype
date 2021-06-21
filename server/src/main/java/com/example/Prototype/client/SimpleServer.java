package com.example.Prototype.client;

import com.example.Prototype.client.ocsf.AbstractServer;
import com.example.Prototype.client.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer extends AbstractServer {
	private static SessionFactory sessionFactory;

	public SimpleServer(int port) {
		super(port);
		sessionFactory=getSessionFactory();
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Movie.class);
		configuration.addAnnotatedClass(MovieList.class);
		configuration.addAnnotatedClass(Branch.class);
		configuration.addAnnotatedClass(BranchesList.class);
		configuration.addAnnotatedClass(Time.class);
		configuration.addAnnotatedClass(Person.class);
		configuration.addAnnotatedClass(HomeWatch.class);
		configuration.addAnnotatedClass(Hall.class);
		configuration.addAnnotatedClass(Map.class);
		ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static <T> List<T> getAll(Class<T> object,Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);

		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	public Time find(List<Time> times, int id){
		for(Time time:times){
			if(time.getId()==id)
				return time;
		}
		return null;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		System.out.println(msgString);
		Session newsession = sessionFactory.openSession();
		Transaction tx=null;
		try {
			tx=newsession.beginTransaction();
			if(msg.getClass().equals(Time.class)){
				newsession.update((Time)msg);
				newsession.flush();
			}else if(msg.getClass().equals(Movie.class)){
				//newsession.delete((Movie)msg);
				newsession.update((Movie)msg);
				newsession.flush();
			}else if(msg.getClass().equals(Hall.class)){
				newsession.update((Hall)msg);
			}
			else if (msgString.startsWith("#showBranches")) {
				try {
					List<Branch> branches=getAll(Branch.class,newsession);
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
					ShowMovies(client,newsession);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (msgString.startsWith("#LOGIN ")) {
				String[] arrOfStr = msgString.split(" ");

				List<Person> people=getAll(Person.class,newsession);
				for(Person person:people)
					if(person.getUserName().equals(arrOfStr[1].toString()) && person.getPassword().equals(arrOfStr[2].toString())){
						client.sendToClient(person);
					}
			}else if(msgString.startsWith("#DeleteTime")){
				int id=Integer.parseInt(msgString.substring(11));
				Movie movie=(Movie)newsession.load(Movie.class,id);
				int timeId=movie.getScreeningTime().get(0).getId();
				System.out.println(timeId);
				Time time=(Time)newsession.load(Time.class,timeId);
				newsession.delete(timeId);
				newsession.flush();

			}else if(msgString.startsWith("#DeleteMovie")){
				int id=Integer.parseInt(msgString.substring(12));
				Movie movieToDelete=(Movie)newsession.load(Movie.class,id);
				List<Time> times=movieToDelete.getScreeningTime();
				for(Time time:times){
					time.setMovie(null);
					time.setHall(null);
					newsession.delete(time);
				}
				movieToDelete.setScreeningTime(null);
				List<MovieList> movieToDeleteLists=movieToDelete.getLists();
				for(MovieList movieList:movieToDeleteLists){
					movieList.getMovies().remove(movieToDelete);
				}
				movieToDelete.setLists(null);
				newsession.delete(movieToDelete);
				newsession.flush();

				//ShowMovies(client, newsession);
				client.sendToClient(new goBack());

			}else if(msgString.startsWith("#HomeWatchList")){
				List<HomeWatch> homeWatch=getAll(HomeWatch.class, newsession);
				 //movies=new MovieList();
				MovieList movies=homeWatch.get(0).getMovies();
				List<Movie> list=movies.getMovies();
				for(Movie movie: list){
					System.out.println(movie.getName());
				}
				client.sendToClient(movies);
			}else if(msgString.startsWith("#AddMovie")){
				addMovie(msgString.substring(9),newsession);
			}else if(msgString.startsWith("#showBranchMovies")){
				int id=Integer.parseInt(msgString.substring(17));
				Branch branch=(Branch) newsession.load(Branch.class,id);
				MovieList movies=branch.getMovie();
				List<Movie> movieList=movies.getMovies();
				for(Movie movie:movieList){
					System.out.println(movie.getName());
				}
				client.sendToClient(movies);
			}
			tx.commit();
		} catch (Exception var10) {
			if (newsession != null) {
				newsession.getTransaction().rollback();
			}

			System.err.println("An error occured, changes have been rolled back.");
			var10.printStackTrace();
		} finally {
			if (newsession != null) {
				newsession.close();
			}

		}
	}

	public void addMovie(String str,Session newsession) throws Exception {
		String[] parts=str.split("%%");
		String[] date=parts[1].split("-");
		int day=Integer.parseInt(date[2]);
		int month=Integer.parseInt(date[1]);
		int year=Integer.parseInt(date[0]);
		Time time=new Time(day,month,year,parts[2],parts[3]);
		//Branch branch=(Branch) newsession.load(Branch.class,parts[4]);
		List<Hall> halls=getAll(Hall.class,newsession);
		Hall goal=new Hall();

		for(Hall hall:halls){
			if(hall.getName()==parts[5])
				goal=hall;
		}

		Movie newMovie=new Movie(parts[0], parts[6], parts[7], parts[8], parts[9]);

		/*if(hall.getBranch()!=branch)
			return;*/

		time.setMovie(newMovie);
		time.setHall(goal);
		goal.addScreeningTime(time);
		newMovie.setScreeningTime(time);

		newsession.save(goal);
		newsession.save(time);
		newsession.save(newMovie);
		newsession.flush();
	}

	public void ShowMovies(ConnectionToClient client, Session newsession) throws Exception {
		List<Movie> movies= SimpleServer.getAll(Movie.class,newsession);
		MovieList movieList=new MovieList();
		for(Movie movie:movies) {
			movieList.setMovies(movie);
		}
		client.sendToClient(movieList);
		System.out.format("Sent movies to client %s\n", client.getInetAddress().getHostAddress());
	}
}