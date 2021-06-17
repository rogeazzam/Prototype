package com.example.Prototype.client;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class App {
    private static Session session;
    private static SessionFactory sessionFactory;

    public App() {
    }

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(MovieList.class);
        configuration.addAnnotatedClass(Branch.class);
        configuration.addAnnotatedClass(BranchesList.class);
        configuration.addAnnotatedClass(Time.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(HomeWatch.class);
        //configuration.addAnnotatedClass(Map.class);
        ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void InitilaizeData() throws Exception {
        Time time1=new Time(22,12,2021,"20:30","22:00");
        Time time2=new Time(30,7,2021,"14:00","16:20");
        Time time3=new Time(20,6,2022,"15:30","17:00");
        Time time4=new Time(28,8,2021,"18:55","20:20");
        Time time5=new Time(2,6,2022,"18:00","19:50");

        Person p1=new Person("roge","azzam","roge_akk","hello","rogeazz");
        Person p2=new Employee("nadeem","saleh","sss_akk","hessso","rs");

        session.save(p1);
        session.flush();
        session.save(p2);
        session.flush();

        session.save(time1);
        session.flush();
        session.save(time2);
        session.flush();
        session.save(time3);
        session.flush();
        session.save(time4);
        session.flush();
        session.save(time5);
        session.flush();

        Movie movie1=new Movie("The Godfather","Al pacino","Francis Ford","images/3.jpg",
                "The Godfather is a classic movie released in 1972",time1);
        Movie movie2=new Movie("Interstellar","Matthew Mchounghy","Christopher Nolan",
                "images/4.jpg","Interstellar is fantasy movie about space released in 2014",time2);
        Movie movie3=new Movie("Joker","Joaquin Phoenix","Todd Phillips",
                "images/joker.jpg","Joker is a drama/crime movie made in 2019.",time3);
        Movie movie4=new Movie("Fifty shades of grey","Dakota Johnson","Sam Taylor",
                "images/fifty_shades.jpg","fifty shades of grey is a romance/drama movie made in 2015",time4);
        Movie movie5=new Movie("The hangover","Bradley Cooper","Todd Phillips",
                "images/hangover.jpg","The hangover is a comedy movie made in 2009",time5);
        Branch branch1=new Branch("yes planet","images/firstCinema.jpg","Haifa","Lev Hamifratz");
        Branch branch2=new Branch("Hot","images/SecondCinema.jpg","Bialek","Keryon");
        /*Hall hall1=new Hall(1,80,0,branch1,map1);
        Hall hall2=new Hall(2,80,0,branch2,map2);
        map1.setHall(hall1);
        map2.setHall(hall2);
        List<Hall> halls1=new ArrayList<Hall>();
        halls1.add(hall1);
        List<Hall> halls2=new ArrayList<Hall>();
        halls2.add(hall2);
        branch1.setHalls(halls1);
        branch2.setHalls(halls2);
        movie1.setHalls(halls1);
        movie2.setHalls(halls1);
        movie3.setHalls(halls2);
        movie4.setHalls(halls1);
        movie5.setHalls(halls2);
        session.save(map1);
        session.flush();
        session.save(map2);
        session.flush();
        session.save(hall1);
        session.flush();
        session.save(hall2);
        session.flush();
        session.save(halls1);
        session.flush();
        session.save(halls2);
        session.flush();*/
        session.save(movie1);
        session.flush();
        session.save(movie2);
        session.flush();
        session.save(movie3);
        session.flush();
        session.save(movie4);
        session.flush();
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

        HomeWatch homeWatch=new HomeWatch(movies1);
        session.save(homeWatch);
        session.flush();

    }

    public static void main(String[] args) {
        try {
            sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            InitilaizeData();
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
}
