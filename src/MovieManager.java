/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
import java.io.*;
import java.util.*;

public class MovieManager implements MovieManagerInterface {

    private List<Movie> movies = new ArrayList<>();
    private final String fileName = "movies.txt";

    public MovieManager() {
        loadMovies();
    }

    private void loadMovies() {
        movies.clear();
        try {
            File file = new File(fileName);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) { // name, date, time, type, extraCharge
                    String name = parts[0];
                    String date = parts[1];
                    String time = parts[2];
                    String type = parts[3];
                    double extraCharge = Double.parseDouble(parts[4]);

                    Movie movie;
                    if ("3D".equalsIgnoreCase(type)) {
                        movie = new ThreeDMovie(name, date, time, extraCharge);
                    } else {
                        movie = new Movie(name, date, time);
                    }
                    movies.add(movie);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMovies() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Movie m : movies) {
                if (m instanceof ThreeDMovie) {
                    pw.println(m.getName() + "," + m.getDate() + "," + m.getTime() + ",3D," + ((ThreeDMovie) m).getExtraCharge());
                } else {
                    pw.println(m.getName() + "," + m.getDate() + "," + m.getTime() + ",Normal,0");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        saveMovies();
    }

    public void updateMovie(int index, Movie movie) {
        movies.set(index, movie);
        saveMovies();
    }

    public void deleteMovie(int index) {
        movies.remove(index);
        saveMovies();
    }
}
