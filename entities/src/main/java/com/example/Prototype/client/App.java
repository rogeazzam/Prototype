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
        configuration.addAnnotatedClass(Map.class);
        configuration.addAnnotatedClass(Hall.class);
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
         

        Map map1=new Map();
        Hall hall1=new Hall();
        map1.setHall(hall1);
        hall1.setMap(map1);

        Map map2=new Map();
        Hall hall2=new Hall();
        map2.setHall(hall2);
        hall2.setMap(map2);

        Map map3=new Map();
        Hall hall3=new Hall();
        map3.setHall(hall3);
        hall3.setMap(map3);

        hall1.setName("a1");
        hall2.setName("b1");
        hall3.setName("b2");

        Branch branch1=new Branch("yes planet","images/firstCinema.jpg","Haifa","Lev Hamifratz");
        Branch branch2=new Branch("Hot","images/SecondCinema.jpg","Bialek","Keryon");

        hall1.setBranch(branch1);
        hall2.setBranch(branch2);
        hall3.setBranch(branch2);

        branch1.setHall(hall1);
        branch2.setHall(hall2);
        branch2.setHall(hall3);

        Movie movie1=new Movie("The Godfather","Al pacino","Francis Ford","images/3.jpg",
                "The Godfather is a classic movie released in 1972");
        time1.setMovie(movie1);
        time1.setHall(hall1);
        hall1.addScreeningTime(time1);
        movie1.setScreeningTime(time1);
        Movie movie2=new Movie("Interstellar","Matthew Mchounghy","Christopher Nolan",
                "images/4.jpg","Interstellar is fantasy movie about space released in 2014");
        time2.setMovie(movie2);
        time2.setHall(hall1);
        hall1.addScreeningTime(time2);
        movie2.setScreeningTime(time2);
        Movie movie3=new Movie("Joker","Joaquin Phoenix","Todd Phillips",
                "images/joker.jpg","Joker is a drama/crime movie made in 2019.");
        time3.setMovie(movie3);
        time3.setHall(hall2);
        hall2.addScreeningTime(time3);
        movie3.setScreeningTime(time3);
        Movie movie4=new Movie("Fifty shades of grey","Dakota Johnson","Sam Taylor",
                "images/fifty_shades.jpg","fifty shades of grey is a romance/drama movie made in 2015");
        time4.setMovie(movie4);
        time4.setHall(hall3);
        hall3.addScreeningTime(time4);
        movie4.setScreeningTime(time4);
        Movie movie5=new Movie("The hangover","Bradley Cooper","Todd Phillips",
                "images/hangover.jpg","The hangover is a comedy movie made in 2009");
        time5.setMovie(movie5);
        time5.setHall(hall2);
        hall2.addScreeningTime(time5);
        movie5.setScreeningTime(time5);
        Time time12=new Time(31,12,2021,"22:00","00:00");
        Time time13=new Time(20,1,2022,"15:00","17:30");
        time12.setHall(hall1);
        time12.setMovie(movie1);
        hall1.addScreeningTime(time12);
        time13.setHall(hall1);
        time13.setMovie(movie1);
        hall1.addScreeningTime(time13);
        movie1.setScreeningTime(time12);
        movie1.setScreeningTime(time13);

        session.save(map1);
         
        session.save(time1);
        session.save(time12);
        session.save(time13);
        session.save(hall1);
        session.save(movie1);

        session.save(time2);
        session.save(movie2);
         
        session.save(map2);
         
        session.save(hall2);
        session.save(time3);
        session.save(movie3);
         
        session.save(map3);
         
        session.save(hall3);
        session.save(movie4);
        session.save(time4);
        session.save(movie5);
        session.save(time5);

        MovieList movies1=new MovieList();
        movies1.setMovies(movie1);
        movies1.setMovies(movie2);
        movie1.setList(movies1);
        movie2.setList(movies1);
        session.save(movies1);
        MovieList movies2=new MovieList();
        movies2.setMovies(movie3);
        movies2.setMovies(movie4);
        movies2.setMovies(movie5);
        movie3.setList(movies2);
        movie4.setList(movies2);
        movie5.setList(movies2);
        session.save(movies2);
        branch1.setMovie(movies1);
        branch2.setMovie(movies2);
        session.save(branch1);
        session.save(branch2);
         
        BranchesList branchesList=new BranchesList();
        branchesList.setBranch(branch1);
        branchesList.setBranch(branch2);
        session.save(branchesList);


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
