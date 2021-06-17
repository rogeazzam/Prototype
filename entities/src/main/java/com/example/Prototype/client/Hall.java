/*package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "number",unique = true)
    private int num;

    @NotNull
    @Column(name = "num_of_seats")
    private int numOfseats;

    @ManyToMany(
            mappedBy = "halls",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Movie.class
    )
    private List<Movie> movies;

    public int getId() {
        return id;
    }

    @NotNull
    @Column(name = "seats_taken")
    private int seatsTaken;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "branch_id"
    )
    private Branch branch;

    public Hall() {
    }

    public Hall(int num, int numOfseats, int seatsTaken, Branch branch) {
        this.num = num;
        this.numOfseats = numOfseats;
        this.seatsTaken = seatsTaken;
        this.branch = branch;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNumOfseats() {
        return numOfseats;
    }

    public void setNumOfseats(int numOfseats) {
        this.numOfseats = numOfseats;
    }

    public int getSeatsTaken() {
        return seatsTaken;
    }

    public void setSeatsTaken(int seatsTaken) {
        this.seatsTaken = seatsTaken;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<Movie> getMovies(){
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
*/