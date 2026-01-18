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

public class TicketManager {

    private List<Ticket> tickets;
    private String fileName = "tickets.txt";

    public TicketManager() {
        tickets = new ArrayList<>();
        loadTickets();
    }

    // BOOK TICKET
    public void bookTicket(Ticket ticket) {
        tickets.add(ticket);
        saveTicketToFile(ticket);
    }

    private void saveTicketToFile(Ticket ticket) {
        try (PrintWriter pw = new PrintWriter(
                new BufferedWriter(new FileWriter(fileName, true)))) {
            pw.println(ticket.toFileString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD TICKETS 
    private void loadTickets() {
        tickets.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 8) continue;

                String name = parts[0];
                String date = parts[1];
                String time = parts[2];
                int seat = Integer.parseInt(parts[3]);
                String phone = parts[4];
                double price = Double.parseDouble(parts[5]);
                String type = parts[6];
                double extraCharge = Double.parseDouble(parts[7]);

                Movie movie;
                if ("3D".equalsIgnoreCase(type)) {
                    movie = new ThreeDMovie(name, date, time, extraCharge);
                } else {
                    movie = new Movie(name, date, time);
                }

                tickets.add(new Ticket(movie, seat, phone, price));
            }
        } catch (FileNotFoundException e) {
            // ignore if file doesn't exist yet
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // CHECK SEAT AVAILABILITY 
    public boolean isSeatAvailable(Movie movie, int seatNumber) {
        for (Ticket t : tickets) {
            if (t.getMovie().getName().equals(movie.getName())
                    && t.getMovie().getDate().equals(movie.getDate())
                    && t.getMovie().getTime().equals(movie.getTime())
                    && t.getSeatNumber() == seatNumber) {
                return false;
            }
        }
        return true;
    }

    //  CANCEL TICKET 
    public boolean cancelTicket(Movie movie, int seatNumber, String phone) {
        boolean removed = false;
        Iterator<Ticket> iterator = tickets.iterator();
        while (iterator.hasNext()) {
            Ticket t = iterator.next();
            if (t.getMovie().getName().equals(movie.getName())
                    && t.getMovie().getDate().equals(movie.getDate())
                    && t.getMovie().getTime().equals(movie.getTime())
                    && t.getSeatNumber() == seatNumber
                    && t.getCustomerPhone().equals(phone)) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if (removed) rewriteTicketFile();
        return removed;
    }

    private void rewriteTicketFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Ticket t : tickets) {
                pw.println(t.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // VIEW PAST RECORDS 
    public String viewPastRecords() {
        StringBuilder sb = new StringBuilder();
        for (Ticket t : tickets) {
            sb.append(t.toString()).append("\n--------------------\n");
        }
        return sb.toString();
    }
}
