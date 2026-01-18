/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
import java.io.Serializable;

public  class Movie implements Serializable {
    private String name;
    private String date;
    private String time;

    // Constructor
    public Movie(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    // Getters and setters (Encapsulation)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    // Convert movie to string to save in file
    
    @Override
    public String toString() {
        return name + "     " + date + "     "+ time;
    }
}
