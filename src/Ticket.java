/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */

public class Ticket extends Booking {

    private int seatNumber;
    private String customerPhone;
    private double ticketPrice;

    public Ticket(Movie movie, int seatNumber, String customerPhone, double ticketPrice) {
        super(movie); // call Booking constructor
        this.seatNumber = seatNumber;
        this.customerPhone = customerPhone;
        this.ticketPrice = ticketPrice;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String toFileString() {
        if (movie instanceof ThreeDMovie) {
            return movie.getName() + "," + movie.getDate() + "," + movie.getTime() + ","
                    + seatNumber + "," + customerPhone + "," + ticketPrice + ",3D,"
                    + ((ThreeDMovie) movie).getExtraCharge();
        } else {
            return movie.getName() + "," + movie.getDate() + "," + movie.getTime() + ","
                    + seatNumber + "," + customerPhone + "," + ticketPrice + ",Normal,0";
        }
    }

    @Override
    public String toString() {
        return "Movie: " + movie.getName() +
                "\nDate: " + movie.getDate() +
                "\nTime: " + movie.getTime() +
                "\nSeat No: " + seatNumber +
                "\nCustomer Phone: " + customerPhone +
                "\nTicket Price: Rs." + ticketPrice;
    }
}
