/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author DELL
 */

import java.util.List;

interface MovieManagerInterface {
    
    void addMovie(Movie movie);
    void updateMovie(int index, Movie movie);
    void deleteMovie(int index);
    List<Movie> getMovies();
    
}

