package com.example.Prototype.client;

import java.io.IOException;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hello world!
 *
 */
public class App
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(3000);
        server.listen();
    }
	/*private static Session session;

	public App() {
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Movie.class);
		configuration.addAnnotatedClass(MovieList.class);
		configuration.addAnnotatedClass(Branch.class);
		configuration.addAnnotatedClass(Time.class);
		ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private static void generateMovies() throws Exception {
		Random random = new Random();

		//Time time1=new Time(22,12,2012,"20:30","22:00");
		//Time time2=new Time(25,1,2021,"18:55","21:20");
		Movie movie1=new Movie("The Godfather","Al pacino","Francis Ford","images/3.jpg",
				"The Godfather is a classic movie released in 1972");
			session.save(movie1);
			session.flush();
	    	Movie movie2=new Movie("Interstellar","Matthew Mchounghy","Christopher Nolan",
    				"images/4.jpg","Interstellar is fantasy movie about space released in 2014");
	    	session.save(movie2);
			session.flush();

	}*/

	/*private static List<Car> getAllCars() throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Car> query = builder.createQuery(Car.class);
		query.from(Car.class);
		List<Car> data = session.createQuery(query).getResultList();
		return data;
	}

	private static void printAllCars() throws Exception {
		List<Car> cars = getAllCars();
		Iterator<Car> var2 = cars.iterator();

		while (var2.hasNext()) {
			Car car = (Car) var2.next();
			System.out.print("Id: ");
			System.out.print(car.getId());
			System.out.print(", License plate: ");
			System.out.print(car.getLicensePlate());
			System.out.print(", Price: ");
			System.out.print(car.getPrice());
			System.out.print(", Year: ");
			System.out.print(car.getYear());
			System.out.print('\n');
			int size=car.getGarages().size();
			for(int i=0;i<size-1;i++) {
				System.out.print(car.getGarages().get(i).getAddress()+" ,");
			}
			System.out.print(car.getGarages().get(size-1).getAddress()+"\n");
		}

	}*/

	/*public static void main(String[] args) throws Exception {
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			generateMovies();
			generateBranches();
			Garage garage1=new Garage("Haifa"),garage2=new Garage("Tel-Aviv");
			session.save(garage1);
			session.flush();
			session.save(garage2);
			session.flush();
			String FirstArr[]= {"Roge","Morad","Malki","Liel","Ali"};
			String LastArr[] = {"Azzam","Sabbah", "Grossman","Fridman", "Ali" };
			for (int i = 0; i < 5;i++) {
				Person person = new Person(FirstArr[i], LastArr[i], FirstArr[i]+"@abc.com",FirstArr[i]);
				session.save(person);
				session.flush();
			}
			generateCars();
			
			Random random=new Random();
			List<Car> cars = getAllCars();
			Iterator<Car> var2 = cars.iterator();

			while (var2.hasNext()) {
				Car car = (Car) var2.next();
				int x=random.nextInt()%3;
				if(x==0) {
					car.InsertGarage(garage1);
					garage1.Insertcar(car);
				}
				else if(x==1) {
					car.InsertGarage(garage2);
					garage2.Insertcar(car);
				}
				else {
					car.InsertGarage(garage1);
					car.InsertGarage(garage2);
					garage1.Insertcar(car);
					garage2.Insertcar(car);
				}
			}
			
			garage1.printGarage();
			garage2.printGarage();
			
			printAllCars();*/
		/*	session.getTransaction().commit();
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

	private static void generateBranches() {
		Branch branch1=new Branch("yes planet","Haifa","image/5.jpg","Lev-Hamifratz");
		session.save(branch1);
		session.flush();
		Branch branch2=new Branch("Hot","Tel-Aviv","image/6.jpg","Dezengov");
		session.save(branch2);
		session.flush();
	}*/
}
