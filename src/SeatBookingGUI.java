/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SeatBookingGUI {

    private JFrame frame;
    private JButton[] seats; // 25 seats
    private Movie movie;
    private TicketManager ticketManager;
    private final int TOTAL_SEATS = 25;

    public SeatBookingGUI(Movie movie, TicketManager ticketManager) {
        this.movie = movie;
        this.ticketManager = ticketManager;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Book Seats for " + movie.getName());
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= SCREEN LABEL =================
        JLabel screen = new JLabel("SEATS", SwingConstants.CENTER);
        screen.setFont(new Font("Arial", Font.BOLD, 28));
        screen.setForeground(Color.BLACK);
        screen.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(screen, BorderLayout.NORTH);

        // ================= SEAT PANEL =================
        JPanel seatPanel = new JPanel(new GridLayout(5, 5, 10, 10));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        seats = new JButton[TOTAL_SEATS];

        for (int i = 0; i < TOTAL_SEATS; i++) {
            int seatNumber = i + 1;
            JButton seatBtn = new JButton(String.valueOf(seatNumber));
            seatBtn.setFont(new Font("Arial", Font.BOLD, 16));
            seatBtn.setOpaque(true);
            seatBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            seatBtn.setForeground(Color.WHITE);
            seatBtn.setBackground(new Color(0, 153, 76)); // green
            seatBtn.setToolTipText("Seat " + seatNumber);

            // Check if already booked
            if (!ticketManager.isSeatAvailable(movie, seatNumber)) {
                seatBtn.setBackground(Color.RED);
                seatBtn.setEnabled(false);
            }

            // Hover effect
            seatBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (seatBtn.isEnabled())
                        seatBtn.setBackground(seatBtn.getBackground().brighter());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (seatBtn.isEnabled())
                        seatBtn.setBackground(new Color(0, 153, 76));
                }
            });

            final int finalSeatNumber = seatNumber;
            seatBtn.addActionListener(e -> bookSeat(seatBtn, finalSeatNumber));

            seats[i] = seatBtn;
            seatPanel.add(seatBtn);
        }

        frame.add(seatPanel, BorderLayout.CENTER);

        // ================= BOTTOM PANEL =================
        JPanel bottomPanel = new JPanel();

        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton viewBtn = new JButton("View Past Records");

        JButton[] buttons = {cancelBtn, viewBtn};
        for (JButton b : buttons) {
            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setBackground(new Color(0, 102, 204));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);

            // Hover effect
            b.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { b.setBackground(new Color(0, 153, 255)); }
                @Override
                public void mouseExited(MouseEvent e) { b.setBackground(new Color(0, 102, 204)); }
            });

            bottomPanel.add(b);
        }

        cancelBtn.addActionListener(e -> cancelTicket());
        viewBtn.addActionListener(e -> {
            String records = ticketManager.viewPastRecords();
            JTextArea textArea = new JTextArea(records.isEmpty() ? "No tickets booked yet." : records);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(700, 400));
            JOptionPane.showMessageDialog(frame, scrollPane, "Past Tickets",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // ================= BOOK SEAT =================
    private void bookSeat(JButton seatBtn, int seatNo) {
        try {
            String phone = JOptionPane.showInputDialog(frame, "Enter Phone Number:");
            double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter Ticket Price:"));

            if (movie instanceof ThreeDMovie) {
                price += ((ThreeDMovie) movie).getExtraCharge();
            }

            Ticket ticket = new Ticket(movie, seatNo, phone, price);
            ticketManager.bookTicket(ticket);

            seatBtn.setBackground(Color.RED);
            seatBtn.setEnabled(false);
            JOptionPane.showMessageDialog(frame, ticket.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    // ================= CANCEL TICKET =================
    private void cancelTicket() {
        try {
            int seatNo = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Seat Number:"));
            String phone = JOptionPane.showInputDialog(frame, "Enter Phone Number:");

            boolean success = ticketManager.cancelTicket(movie, seatNo, phone);

            if (success) {
                JButton seatBtn = seats[seatNo - 1];
                seatBtn.setBackground(new Color(0, 153, 76));
                seatBtn.setForeground(Color.WHITE);
                seatBtn.setEnabled(true);

                JOptionPane.showMessageDialog(frame, "Ticket cancelled successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Ticket not found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }
}
