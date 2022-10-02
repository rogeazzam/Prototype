package com.example.Prototype.client;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        configuration.addAnnotatedClass(NetworkManager.class);
        configuration.addAnnotatedClass(BranchManager.class);
        configuration.addAnnotatedClass(ContentManager.class);
        configuration.addAnnotatedClass(HomeWatch.class);
        configuration.addAnnotatedClass(Map.class);
        configuration.addAnnotatedClass(Hall.class);
        configuration.addAnnotatedClass(Ticket.class);
        configuration.addAnnotatedClass(CinemaTicket.class);
        configuration.addAnnotatedClass(HomeTicket.class);
        configuration.addAnnotatedClass(MultipleTickets.class);
        configuration.addAnnotatedClass(Costumer.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Seat.class);
        configuration.addAnnotatedClass(PurplePass.class);
        configuration.addAnnotatedClass(Report.class);
        configuration.addAnnotatedClass(TicketReport.class);
        configuration.addAnnotatedClass(RefundReport.class);
        configuration.addAnnotatedClass(otherpurchaseReport.class);
        configuration.addAnnotatedClass(ComplaintReport.class);
        configuration.addAnnotatedClass(MovieUpdate.class);
        ServiceRegistry serviceRegistry = (new StandardServiceRegistryBuilder())
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void InitilaizeData() throws Exception {
        Time kkkk[]=new Time[13];

        kkkk[0]=new Time(3,7,2021,"20:30","22:00");
        kkkk[1]=new Time(8,7,2021,"14:00","16:20");
        kkkk[2]=new Time(30,7,2021,"15:30","17:00");
        kkkk[3]=new Time(28,8,2021,"18:55","20:20");
        kkkk[4]=new Time(2,9,2021,"18:00","19:50");
        kkkk[5]=new Time(5,7,2021,"18:00","19:50");
        kkkk[6]=new Time(5,7,2021,"20:00","22:30");
        kkkk[7]=new Time(6,7,2021,"15:00","16:30");
        kkkk[8]=new Time(6,7,2021,"18:30","20:30");
        kkkk[9]=new Time(7,7,2021,"20:00","22:30");
        kkkk[10]=new Time(7,7,2021,"13:00","16:30");
        kkkk[11]=new Time(8,7,2021,"17:00","19:30");
        kkkk[12]=new Time(8,7,2021,"17:20","18:30");
        //Time kkkk[0]4=new Time(31,12,2021,"22:00","00:00");
        //Time kkkk[0]5=new Time(20,1,2022,"15:00","17:30");

        Movie mmmm[]=new Movie[10];

        Branch branch1=new Branch("yes planet","images/firstCinema.jpg","Haifa","Lev Hamifratz");
        Branch branch2=new Branch("Hot","images/SecondCinema.jpg","Bialek","Keryon");
        Branch branch3=new Branch("Hot cinemas","images/FourthCinema.jpg","Haifa","Grand Kenyon");
        Branch branch4=new Branch("Globus Max","images/FifthCinema.jpg","Nahariya","Kenyon Nahariya");

        mmmm[0]=new Movie("The Godfather","Marlon Brando","Francis Ford","images/3.jpg",
                "The Godfather is a classic movie released in 1972");
        mmmm[1]=new Movie("Interstellar","Matthew Mchounghy","Christopher Nolan",
                "images/4.jpg","Interstellar is fantasy movie about space released in 2014");
        mmmm[2]=new Movie("Joker","Joaquin Phoenix","Todd Phillips",
                "images/joker.jpg","Joker is a drama/crime movie made in 2019.");
        mmmm[3]=new Movie("Fifty shades of grey","Dakota Johnson","Sam Taylor",
                "images/fifty_shades.jpg","fifty shades of grey is a romance/drama movie made in 2015");
        mmmm[4]=new Movie("The hangover","Bradley Cooper","Todd Phillips",
                "images/hangover.jpg","The hangover is a comedy movie made in 2009");
        mmmm[5]=new Movie("The Godfather 2","Al pacino","Francis Ford","images/the_godfather2.jpg",
                "The Godfather is a classic movie sequel released in 1974");
        mmmm[6]=new Movie("Taxi driver","Robert De Niro","Martin Scorcese",
                "images/taxi_driver.jpg","Taxi Driver movie");
        mmmm[7]=new Movie("The Dark Knight","Christian Bale","Christopher Nolan",
                "images/the_dark_knight.jpg","The Dark Knight movie.");
        mmmm[8]=new Movie("12 angry men","Henry Fonda","Sidney Lumet",
                "images/angry_men.jpg","12 Angry men movie.");
        mmmm[9]=new Movie("The wolf of wallstreet","Leonardo Di Caprio","Martin Scorcese",
                "images/the_wolf.jpg","The wolf of wallstreet movie.");


        MovieList movies1=new MovieList();
        MovieList movies2=new MovieList();

        Person p1=new NetworkManager("roge","azzam","roge_akk","hello","rogeazz");
        Person p2=new ContentManager("nadeem","saleh","sss_akk","hessso","rs");

        Account account1=new Account("11","11","11","11",2000);

        BranchesList branchesList=new BranchesList();

        Map maps[]=new Map[14];
        Hall halls[]=new Hall[24];

        for(int i=1; i < 24;i++){
            halls[i]=new Hall();
            char c=(char)((int)'a'+i/5);
            halls[i].setName(c + String.valueOf(i%5));
        }
        for(int i=1; i < 14; i++)
            maps[i]=new Map();

        session.save(p1);
        session.flush();
        session.save(p2);
        session.flush();

        session.save(account1);
        session.flush();

        kkkk[0].setHall(halls[1]);
        kkkk[0].setMap(maps[1]);
        kkkk[0].setMovie(mmmm[0]);
        maps[1].setTime(kkkk[0]);
        mmmm[0].setScreeningTime(kkkk[0]);
        movies1.setMovies(mmmm[0]);
        mmmm[0].setList(movies1);
        halls[1].setBranch(branch1);
        halls[1].addScreeningTime(kkkk[0]);
        branch1.setHall(halls[1]);

        kkkk[1].setHall(halls[2]);
        kkkk[1].setMap(maps[2]);
        kkkk[1].setMovie(mmmm[1]);
        maps[2].setTime(kkkk[1]);
        mmmm[1].setScreeningTime(kkkk[1]);
        halls[2].setBranch(branch1);
        halls[2].addScreeningTime(kkkk[1]);

        kkkk[2].setHall(halls[2]);
        kkkk[2].setMap(maps[3]);
        kkkk[2].setMovie(mmmm[1]);
        maps[3].setTime(kkkk[2]);
        mmmm[1].setScreeningTime(kkkk[2]);
        movies1.setMovies(mmmm[1]);
        mmmm[1].setList(movies1);
        halls[2].addScreeningTime(kkkk[2]);
        branch1.setHall(halls[2]);

        kkkk[3].setHall(halls[3]);
        kkkk[3].setMap(maps[4]);
        kkkk[3].setMovie(mmmm[2]);
        maps[4].setTime(kkkk[3]);
        mmmm[2].setScreeningTime(kkkk[3]);
        movies1.setMovies(mmmm[2]);
        mmmm[2].setList(movies1);
        halls[3].addScreeningTime(kkkk[3]);

        kkkk[4].setHall(halls[3]);
        kkkk[4].setMap(maps[5]);
        kkkk[4].setMovie(mmmm[3]);
        maps[5].setTime(kkkk[4]);
        mmmm[3].setScreeningTime(kkkk[4]);
        movies1.setMovies(mmmm[3]);
        mmmm[3].setList(movies1);
        halls[3].setBranch(branch1);
        branch1.setHall(halls[3]);
        halls[3].addScreeningTime(kkkk[4]);

        kkkk[5].setHall(halls[4]);
        kkkk[5].setMap(maps[6]);
        kkkk[5].setMovie(mmmm[4]);
        maps[6].setTime(kkkk[5]);
        mmmm[4].setScreeningTime(kkkk[5]);
        movies1.setMovies(mmmm[4]);
        mmmm[4].setList(movies1);
        halls[4].setBranch(branch1);
        branch1.setHall(halls[4]);
        halls[4].addScreeningTime(kkkk[5]);

        kkkk[6].setHall(halls[4]);
        kkkk[6].setMap(maps[7]);
        kkkk[6].setMovie(mmmm[4]);
        maps[7].setTime(kkkk[6]);
        mmmm[4].setScreeningTime(kkkk[6]);
        halls[4].addScreeningTime(kkkk[6]);

        kkkk[7].setHall(halls[5]);
        kkkk[7].setMap(maps[8]);
        kkkk[7].setMovie(mmmm[5]);
        maps[8].setTime(kkkk[7]);
        mmmm[5].setScreeningTime(kkkk[7]);
        movies2.setMovies(mmmm[5]);
        mmmm[5].setList(movies2);
        halls[5].setBranch(branch2);
        branch2.setHall(halls[5]);
        halls[5].addScreeningTime(kkkk[7]);

        kkkk[8].setHall(halls[6]);
        kkkk[8].setMap(maps[9]);
        kkkk[8].setMovie(mmmm[6]);
        maps[9].setTime(kkkk[8]);
        mmmm[6].setScreeningTime(kkkk[8]);
        movies2.setMovies(mmmm[6]);
        mmmm[6].setList(movies2);
        halls[6].setBranch(branch2);
        branch2.setHall(halls[6]);
        halls[6].addScreeningTime(kkkk[8]);

        kkkk[9].setHall(halls[1]);
        kkkk[9].setMap(maps[10]);
        kkkk[9].setMovie(mmmm[6]);
        maps[10].setTime(kkkk[9]);
        mmmm[6].setScreeningTime(kkkk[9]);
        halls[1].addScreeningTime(kkkk[9]);

        kkkk[10].setHall(halls[7]);
        kkkk[10].setMap(maps[11]);
        kkkk[10].setMovie(mmmm[7]);
        maps[11].setTime(kkkk[10]);
        mmmm[7].setScreeningTime(kkkk[10]);
        movies2.setMovies(mmmm[7]);
        mmmm[7].setList(movies2);
        halls[7].setBranch(branch2);
        branch2.setHall(halls[7]);
        halls[7].addScreeningTime(kkkk[10]);

        kkkk[11].setHall(halls[8]);
        kkkk[11].setMap(maps[12]);
        kkkk[11].setMovie(mmmm[8]);
        maps[12].setTime(kkkk[11]);
        mmmm[8].setScreeningTime(kkkk[11]);
        movies2.setMovies(mmmm[8]);
        mmmm[8].setList(movies2);
        halls[8].setBranch(branch2);
        branch2.setHall(halls[8]);
        halls[8].addScreeningTime(kkkk[11]);

        kkkk[12].setHall(halls[9]);
        kkkk[12].setMap(maps[13]);
        kkkk[12].setMovie(mmmm[9]);
        maps[13].setTime(kkkk[12]);
        mmmm[9].setScreeningTime(kkkk[12]);
        movies2.setMovies(mmmm[9]);
        mmmm[9].setList(movies2);
        halls[9].setBranch(branch2);
        branch2.setHall(halls[9]);
        halls[9].addScreeningTime(kkkk[12]);


        Movie[] arr=new Movie[15];

        arr[0]=new Movie("Schindler's list", "Liam Nesson", "Steven Spielberg", "images/schindler.jpg",
                                    "Oskar Schindler, a German industrialist and member of the Nazi party, tries to save his Jewish employees after witnessing the persecution of Jews in Poland.");
        arr[1]=new Movie("The Gladiator", "Russel Crow", "Ridley Scott", "images/gladiator.jpg",
                    "Commodus takes over power and demotes Maximus, one of the preferred generals of his father, Emperor Marcus Aurelius. As a result, Maximus is relegated to fighting till death as a gladiator.");
        arr[2]=new Movie("The Prestige", "Christian Bale", "Christopher Nolan", "images/prestige.jpg",
                        "Two friends and fellow magicians become bitter enemies after a sudden tragedy. As they devote themselves to this rivalry, they make sacrifices that bring them fame but with terrible consequences.");
        arr[3]=new Movie("Memento", "Guy Pearce", "Christopher Nolan", "images/memento.jpg",
                "Leonard Shelby, an insurance investigator, suffers from anterograde amnesia and uses notes and tattoos to hunt for the man he thinks killed his wife, which is the last thing he remembers.");
        arr[4]=new Movie("Braveheart", "Mel Gibson", "Mel Gibson", "images/braveheart.jpg",
                                "William Wallace, a Scottish rebel, along with his clan, sets out to battle King Edward I of England, who killed his bride a day after their marriage.");
        arr[5]=new Movie("Heat", "Robert De Niro", "Michael Mann", "images/heat.jpg",
                            "Lieutenant Hanna, a detective, decides to catch a highly intelligent seasonal criminal who has vowed to pull off one last robbery before he retires for good.");
        arr[6]=new Movie("There will be blood", "Daniel Day-Lewis", "Paul Anderson", "images/blood.jpg",
                                    "Daniel Plainview is a ruthless oil prospector who goes on a relentless pursuit to become the most powerful oil tycoon. For this, he even resorts to manipulating and using his adopted son.");
        arr[7]=new Movie("Hacksaw Ridge", "Andrew Garfield", "Mel Gibson", "images/hacksaw_ridge.jpg",
                            "The true story of Pfc. Desmond T. Doss (Andrew Garfield), who won the Congressional Medal of Honor despite refusing to bear arms during WWII on religious grounds.");
        arr[8]=new Movie("Ford V Ferrari", "Matt Daemon", "James Mangold", "images/fordvferrari.jpg",
                                "Carroll Shelby, an automotive designer, and Ken Miles, a race car driver, join hands to build a trailblazing vehicle and compete against the Ferrari race cars.");
        arr[9]=new Movie("Parasite", "Ki-woo", "Bong Joon-ho", "images/parasite.jpg",
                            "The struggling Kim family sees an opportunity when the son starts working for the wealthy Park family. Soon, all of them find a way to work within the same household and start living a parasitic life.");
        arr[10]=new Movie("GoodFellas", "Ray Liotta", "Martin Scorsese", "images/goodfellas.jpg",
                                "Young Henry Hill, with his friends Jimmy and Tommy, begins the climb from being a petty criminal to a gangster on the mean streets of New York.");
        arr[11]=new Movie("The Departed", "Leonardo Di Caprio", "Martin Scorsese", "images/the_departed.jpg",
                                "An undercover agent and a spy constantly try to counter-attack each other in order to save themselves from being exposed in front of the authorities. Meanwhile, both try to infiltrate an Irish gang.");
        arr[12]=new Movie("Django Unchained", "Jaime Foxx", "Quentin Tarantino", "images/django_unchained.jpg",
                                "When Django, a slave, is freed, he joins forces with a bounty hunter to rescue his wife, who has been enslaved by Calvin, a hard-hearted plantation owner");
        arr[13]=new Movie("sdsd","sdsd","sdfsf","images/the_matrix.jpg","dfdf");
        arr[14]=new Movie("fdsf","fgr", "sd","images/fight_club.jpg","sf");


        MovieList movieList[]=new MovieList[5];
        Branch branchesList1[]=new Branch[4];

        branchesList1[0]=branch1;
        branchesList1[1]=branch2;
        branchesList1[2]=branch3;
        branchesList1[3]=branch4;

        movieList[0]=movies1;
        movieList[1]=movies2;
        movieList[2]=new MovieList();
        movieList[3]=new MovieList();
        movieList[4]=new MovieList();

        Map maps1[]=new Map[43];

        Time[] times1=new Time[43];
        for(int i=0; i < 43; i++){
            maps1[i]=new Map();
            int day=ThreadLocalRandom.current().nextInt(1, 32);
            int month=ThreadLocalRandom.current().nextInt(7,10);
            int temp=ThreadLocalRandom.current().nextInt(0, 24);
            int temp2;
            if(temp == 22)
                temp2=0;
            else if(temp == 23)
                temp2=1;
            else
                temp2=temp + 2;
            String hour="";
            String end="";
            if(temp < 10) {
                hour += "0";
            }
            hour+=String.valueOf(temp);
            if(temp2 < 10)
                end+="0";
            end+=String.valueOf(temp2);
            end+=":00";
            hour+=":00";
            times1[i]=new Time(day, month, 2021, hour, end);
        }


        times1[0].setHall(halls[10]);
        times1[0].setMap(maps1[0]);
        times1[0].setMovie(mmmm[9]);
        maps1[0].setTime(times1[0]);
        mmmm[9].setScreeningTime(times1[0]);
        movieList[2].setMovies(mmmm[9]);
        mmmm[9].setList(movieList[2]);
        halls[10].setBranch(branch3);
        branch3.setHall(halls[10]);
        halls[10].addScreeningTime(times1[0]);

        times1[1].setHall(halls[15]);
        times1[1].setMap(maps1[1]);
        times1[1].setMovie(mmmm[9]);
        maps1[1].setTime(times1[1]);
        mmmm[9].setScreeningTime(times1[1]);
        movieList[3].setMovies(mmmm[9]);
        mmmm[9].setList(movieList[3]);
        halls[15].setBranch(branch4);
        branch4.setHall(halls[15]);
        halls[15].addScreeningTime(times1[1]);

        times1[2].setHall(halls[8]);
        times1[2].setMap(maps1[2]);
        times1[2].setMovie(mmmm[0]);
        maps1[2].setTime(times1[2]);
        mmmm[0].setScreeningTime(times1[2]);
        halls[8].addScreeningTime(times1[2]);
        movieList[1].setMovies(mmmm[0]);
        mmmm[0].setList(movieList[1]);
        halls[8].addScreeningTime(times1[2]);

        times1[3].setHall(halls[11]);
        times1[3].setMap(maps1[3]);
        times1[3].setMovie(mmmm[2]);
        maps1[3].setTime(times1[3]);
        mmmm[2].setScreeningTime(times1[3]);
        movieList[2].setMovies(mmmm[2]);
        mmmm[2].setList(movieList[2]);
        halls[11].setBranch(branch3);
        branch3.setHall(halls[11]);
        halls[11].addScreeningTime(times1[3]);

        times1[4].setHall(halls[19]);
        times1[4].setMap(maps1[4]);
        times1[4].setMovie(mmmm[3]);
        maps1[4].setTime(times1[4]);
        mmmm[3].setScreeningTime(times1[4]);
        movieList[3].setMovies(mmmm[3]);
        mmmm[3].setList(movieList[3]);
        halls[19].addScreeningTime(times1[4]);

        times1[5].setHall(halls[20]);
        times1[5].setMap(maps1[5]);
        times1[5].setMovie(mmmm[5]);
        maps1[5].setTime(times1[5]);
        mmmm[5].setScreeningTime(times1[5]);
        movieList[4].setMovies(mmmm[5]);
        mmmm[5].setList(movieList[4]);
        halls[20].addScreeningTime(times1[5]);

        times1[6].setHall(halls[16]);
        times1[6].setMap(maps1[6]);
        times1[6].setMovie(mmmm[7]);
        maps1[6].setTime(times1[6]);
        mmmm[7].setScreeningTime(times1[6]);
        movieList[3].setMovies(mmmm[7]);
        mmmm[7].setList(movieList[3]);
        halls[16].setBranch(branch4);
        branch4.setHall(halls[16]);
        halls[16].addScreeningTime(times1[6]);

        times1[7].setHall(halls[6]);
        times1[7].setMap(maps1[7]);
        times1[7].setMovie(mmmm[8]);
        maps1[7].setTime(times1[7]);
        mmmm[8].setScreeningTime(times1[7]);
        movieList[1].setMovies(mmmm[8]);
        mmmm[8].setList(movieList[1]);
        halls[6].addScreeningTime(times1[7]);

        times1[8].setHall(halls[21]);
        times1[8].setMap(maps1[8]);
        times1[8].setMovie(arr[0]);
        maps1[8].setTime(times1[8]);
        arr[0].setScreeningTime(times1[8]);
        movieList[4].setMovies(arr[0]);
        arr[0].setList(movieList[4]);
        halls[21].addScreeningTime(times1[8]);

        times1[9].setHall(halls[1]);
        times1[9].setMap(maps1[9]);
        times1[9].setMovie(arr[0]);
        maps1[9].setTime(times1[9]);
        arr[0].setScreeningTime(times1[9]);
        movieList[0].setMovies(arr[0]);
        arr[0].setList(movieList[0]);
        halls[1].addScreeningTime(times1[9]);

        times1[10].setHall(halls[12]);
        times1[10].setMap(maps1[10]);
        times1[10].setMovie(arr[1]);
        maps1[10].setTime(times1[10]);
        arr[1].setScreeningTime(times1[10]);
        movieList[2].setMovies(arr[1]);
        arr[1].setList(movieList[2]);
        halls[12].setBranch(branch3);
        branch3.setHall(halls[12]);
        halls[12].addScreeningTime(times1[10]);

        times1[11].setHall(halls[8]);
        times1[11].setMap(maps1[11]);
        times1[11].setMovie(arr[1]);
        maps1[11].setTime(times1[11]);
        arr[1].setScreeningTime(times1[11]);
        movieList[1].setMovies(arr[1]);
        arr[1].setList(movieList[1]);
        halls[8].addScreeningTime(times1[11]);

        int x=12, y=2;

        times1[x].setHall(halls[22]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[4].setMovies(arr[y]);
        arr[y].setList(movieList[4]);
        halls[22].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[17]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        halls[17].setBranch(branch4);
        branch4.setHall(halls[17]);
        halls[17].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[18]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[3].setMovies(arr[y]);
        arr[y].setList(movieList[3]);
        halls[18].setBranch(branch4);
        branch4.setHall(halls[18]);
        halls[18].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[13]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[2].setMovies(arr[y]);
        arr[y].setList(movieList[2]);
        halls[13].setBranch(branch3);
        branch3.setHall(halls[13]);
        halls[13].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[4]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[0].setMovies(arr[y]);
        arr[y].setList(movieList[0]);
        halls[4].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[13]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[2].setMovies(arr[y]);
        arr[y].setList(movieList[2]);
        halls[13].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[23]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[4].setMovies(arr[y]);
        arr[y].setList(movieList[4]);
        halls[23].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[3]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[0].setMovies(arr[y]);
        arr[y].setList(movieList[0]);
        halls[3].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[19]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[4].setMovies(arr[y]);
        arr[y].setList(movieList[4]);
        halls[19].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[10]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[2].setMovies(arr[y]);
        arr[y].setList(movieList[2]);
        halls[10].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[14]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[2].setMovies(arr[y]);
        arr[y].setList(movieList[2]);
        halls[14].setBranch(branch3);
        branch3.setHall(halls[14]);
        halls[14].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[22]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[4].setMovies(arr[y]);
        arr[y].setList(movieList[4]);
        halls[22].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[18]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[3].setMovies(arr[y]);
        arr[y].setList(movieList[3]);
        halls[18].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[14]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[2].setMovies(arr[y]);
        arr[y].setList(movieList[2]);
        halls[14].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[9]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[1].setMovies(arr[y]);
        arr[y].setList(movieList[1]);
        halls[9].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[2]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[0].setMovies(arr[y]);
        arr[y].setList(movieList[0]);
        halls[2].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[16]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[3].setMovies(arr[y]);
        arr[y].setList(movieList[3]);
        halls[16].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[22]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[4].setMovies(arr[y]);
        arr[y].setList(movieList[4]);
        halls[22].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[22]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        halls[22].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[1]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[0].setMovies(arr[y]);
        arr[y].setList(movieList[0]);
        halls[1].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[3]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        halls[3].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[6]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[1].setMovies(arr[y]);
        arr[y].setList(movieList[1]);
        halls[6].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[17]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[3].setMovies(arr[y]);
        arr[y].setList(movieList[3]);
        halls[17].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[18]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        halls[18].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[11]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[2].setMovies(arr[y]);
        arr[y].setList(movieList[2]);
        halls[11].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[13]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[2].setMovies(arr[y]);
        arr[y].setList(movieList[2]);
        halls[13].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[21]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[4].setMovies(arr[y]);
        arr[y].setList(movieList[4]);
        halls[21].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[4]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[0].setMovies(arr[y]);
        arr[y].setList(movieList[0]);
        halls[4].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[15]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[3].setMovies(arr[y]);
        arr[y].setList(movieList[3]);
        halls[15].addScreeningTime(times1[x]);

        x++;

        times1[x].setHall(halls[20]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[4].setMovies(arr[y]);
        arr[y].setList(movieList[4]);
        halls[20].addScreeningTime(times1[x]);

        x++;
        y++;

        times1[x].setHall(halls[6]);
        times1[x].setMap(maps1[x]);
        times1[x].setMovie(arr[y]);
        maps1[x].setTime(times1[x]);
        arr[y].setScreeningTime(times1[x]);
        movieList[1].setMovies(arr[y]);
        arr[y].setList(movieList[1]);
        halls[6].addScreeningTime(times1[x]);

        branchesList.setBranch(branch1);
        branchesList.setBranch(branch2);

        branch1.addBranchesList(branchesList);
        branch2.addBranchesList(branchesList);

        branch1.setMovie(movieList[0]);
        movieList[0].setBranch(branch1);

        branch2.setMovie(movieList[1]);
        movieList[1].setBranch(branch2);

        branch3.setMovie(movieList[2]);
        movieList[2].setBranch(branch3);

        branch4.setMovie(movieList[3]);
        movieList[3].setBranch(branch4);

        for(int m=1; m < 24; m++)
            session.save(halls[m]);


        System.out.println("sdsdsd");
        for(int i=1;i < 14; i++) {
            for (int m = 0; m < 80; m++) {
                session.save(maps[i].getSeats().get(m));
                session.save(maps[i].getSeats().get(m).getTicket());
            }
            session.save(maps[i]);
        }

        for(int i=1;i < 43; i++) {
            for (int m = 0; m < 80; m++) {
                session.save(maps1[i].getSeats().get(m));
                session.save(maps1[i].getSeats().get(m).getTicket());
            }
            session.save(maps1[i]);
        }

        for(int i=0; i < 10; i++){
            for(int l=0; l < mmmm[i].getScreeningTime().size()-1; l++){
                for(int f=0; f < mmmm[i].getScreeningTime().size()-l-1; f++){
                    if(mmmm[i].getScreeningTime().get(f).greater(mmmm[i].getScreeningTime().get(f+1))){
                        Collections.swap(mmmm[i].getScreeningTime(), f, f+1);
                    }
                }
            }
        }

        for(int i=0; i < 15; i++){
            for(int l=0; l < arr[i].getScreeningTime().size()-1; l++){
                for(int f=0; f < arr[i].getScreeningTime().size()-l-1; f++){
                    if(arr[i].getScreeningTime().get(f).greater(arr[i].getScreeningTime().get(f+1))){
                        Collections.swap(arr[i].getScreeningTime(), f, f+1);
                    }
                }
            }
        }

        /*save*/
        for(int i=0; i < 10; i++){
            for(int l=0; l < mmmm[i].getScreeningTime().size(); l++){
                Time temp=mmmm[i].getScreeningTime().get(l);
                session.save(temp);
                //session.flush();
            }
        }

        for(int i=0; i < 15; i++){
            for(int l=0; l < arr[i].getScreeningTime().size(); l++){
                Time temp=arr[i].getScreeningTime().get(l);
                session.save(temp);
                //session.flush();
            }
        }
        /////////


        /*System.out.println("sdsdsd2");
        session.save(kkkk[0]);
        session.save(kkkk[1]);
        session.save(kkkk[2]);
        session.save(kkkk[3]);
        session.save(kkkk[4]);
        session.save(kkkk[5]);
        session.save(kkkk[6]);
        session.save(kkkk[7]);
        session.save(kkkk[8]);
        session.save(kkkk[9]);
        session.save(kkkk[10]);
        session.save(kkkk[11]);
        session.save(kkkk[12]);*/
/*
        for (int i=0; i < 43; i++)
            session.save(times1[i]);
         */

        for(int i=0; i < 10; i++) {
            session.save(mmmm[i]);
        }

        for(int i=0; i < 15; i++) {
            session.save(arr[i]);
        }
        session.save(branch1);
        session.save(branch2);
        session.save(branch3);
        session.save(branch4);

        session.save(branchesList);


        HomeWatch homeWatch1=new HomeWatch(movies1);
        movies1.setHomeWatch(homeWatch1);

        session.save(movies1);
        session.save(homeWatch1);

        session.save(movieList[1]);
        session.save(movieList[2]);
        session.save(movieList[3]);
        System.out.println("sdsdsd3");
        session.flush();

        PurplePass purplePass=PurplePass.PurplePass();
        session.save(purplePass);

        session.flush();

        Branch branch=(Branch) session.load(Branch.class, 2);

        branch.setMovie(movieList[4]);
        movieList[4].setBranch(branch);

        for(int i=19; i < 24; i++){
            branch.setHall(halls[i]);
            halls[i].setBranch(branch);
            session.update(halls[i]);
        }

        branch.setCity("Tel Aviv");
        branch.setName("MovieLand");
        branch.setStreetAddress("Kenyon Azreilli");
        branch.setImgSrc("images/ThirdCinema.jpg");

        session.update(movieList[4]);
        session.update(branch);
        session.flush();

        Time[] times=new Time[5];
        times[0]=new Time(8, 7, 2021, "10:00", "22:00");
        times[1]=new Time(9, 7, 2021, "10:00", "22:00");
        times[2]=new Time(10, 7, 2021, "10:00", "22:00");
        times[3]=new Time(11, 7, 2021, "10:00", "22:00");
        times[4]=new Time(12, 7, 2021, "10:00", "22:00");

        for(int i=0; i < 5; i++) {
            times[i].setMap(null);
            times[i].setHall(null);
            times[i].setMovie(mmmm[i]);
            mmmm[i].setScreeningTime(times[i]);
            for (int m = 0; m < 10; m++) {
                Ticket homeTicket = new HomeTicket(times[i]);
                homeTicket.setSeat(null);
                times[i].addTicket(homeTicket);
                session.save(homeTicket);
            }

            session.save(times[i]);
            session.update(mmmm[i]);

           /* */
        }
        for(int i=0; i < 10; i++) {
            for (int l = 0; l < mmmm[i].getScreeningTime().size() - 1; l++) {
                for (int f = 0; f < mmmm[i].getScreeningTime().size() - l - 1; f++) {
                    if (mmmm[i].getScreeningTime().get(f).greater(mmmm[i].getScreeningTime().get(f + 1))) {
                        Collections.swap(mmmm[i].getScreeningTime(), f, f + 1);
                    }
                }
            }
        }

            for(int i=0; i < 10; i++) {
                session.update(mmmm[i]);
            }
            session.flush();

            RefundReport refundReport=new RefundReport("roge azzam", "d", new Date(), 300);
            session.save(refundReport);
            session.flush();

            Person branchManager1=new BranchManager("Liel", "Friedmann", "lielsfn@jf.com", "hii", "g1");
            Person branchManager2=new BranchManager("Sam", "saann", "liskdfn@jf.com", "hii", "g2");
            Person branchManager3=new BranchManager("sjs", "ckk", "liom", "hii", "g3");
            Person branchManager4=new BranchManager("ghol", "dfokc", "lielsjf.com", "hii", "g4");
            Person branchManager5=new BranchManager("aaa", "dofl", "lielsfn@jf", "hii", "g5");

            branchManager1.setBranch(branch1);
            branchManager2.setBranch(branch);
            branchManager3.setBranch(branch2);
            branchManager4.setBranch(branch3);
            branchManager5.setBranch(branch4);

            branch1.setManager((BranchManager) branchManager1);
            branch.setManager((BranchManager)branchManager2);
            branch2.setManager((BranchManager)branchManager3);
            branch3.setManager((BranchManager)branchManager4);
            branch4.setManager((BranchManager)branchManager5);

            session.update(branch1);
            session.update(branch);
            session.update(branch2);
            session.update(branch3);
            session.update(branch4);

            session.save(branchManager1);
            session.save(branchManager2);
            session.save(branchManager3);
            session.save(branchManager4);
            session.save(branchManager5);

            System.out.println("fdsf");

            session.flush();

            Person[] employees=new Employee[20];
            for(int i=0; i < 20; i++){
                Random random=new Random();
                employees[i]=new Employee("sam"+String.valueOf((char)i), "das"+String.valueOf((char)i),
                        String.valueOf((char)(random.nextInt()%100))+"hotmail.com", "123", "e"+String.valueOf(i));
                if(i<4){
                    branch1.addEmployee((Employee) employees[i]);
                    employees[i].setBranch(branch1);
                }else if(i<8){
                    branch.addEmployee((Employee) employees[i]);
                    employees[i].setBranch(branch);
                }else if(i<12){
                    branch2.addEmployee((Employee) employees[i]);
                    employees[i].setBranch(branch2);
                }else if(i<16){
                    branch3.addEmployee((Employee) employees[i]);
                    employees[i].setBranch(branch3);
                }else{
                    branch4.addEmployee((Employee) employees[i]);
                    employees[i].setBranch(branch4);
                }
            }

            for(int i=0; i < 20; i++)
                session.save(employees[i]);

            session.update(branch1);
            session.update(branch);
            session.update(branch2);
            session.update(branch3);
            session.update(branch4);

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
