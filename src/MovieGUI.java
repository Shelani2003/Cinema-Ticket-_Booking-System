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
import java.util.List;

public class MovieGUI {

    private JFrame frame;
    private JPanel moviesPanel;
    private MovieManager movieManager;
    private TicketManager ticketManager;

    private Movie selectedMovie = null;      // stores the selected movie
    private JPanel selectedBox = null;       // stores the selected movie box for highlighting

    public MovieGUI() {

        movieManager = new MovieManager();
        ticketManager = new TicketManager();

        frame = new JFrame("Cinema Staff Ticket Booking");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel();
        JLabel title = new JLabel("🎬 Cinema Staff Ticket Booking");
        title.setFont(new Font("Verdana", Font.BOLD, 32));
        title.setForeground(new Color(0, 51, 102)); // dark blue
        topPanel.add(title);
        frame.add(topPanel, BorderLayout.NORTH);

        // ================= MOVIES PANEL (GRID) =================
        moviesPanel = new JPanel();
        moviesPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 3 columns, variable rows
        JScrollPane scrollPane = new JScrollPane(moviesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        // ================= BUTTON PANEL =================
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton addBtn = createButton("Add Movie");
        JButton updateBtn = createButton("Update Movie");
        JButton deleteBtn = createButton("Delete Movie");
        JButton bookBtn = createButton("Book Ticket");
        JButton viewRecordsBtn = createButton("View Records");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(bookBtn);
        buttonPanel.add(viewRecordsBtn);

        frame.add(buttonPanel, BorderLayout.EAST);

        refreshMovies();

        // ================= BUTTON ACTIONS =================
        addBtn.addActionListener(e -> addMovieAction());
        updateBtn.addActionListener(e -> updateMovieAction());
        deleteBtn.addActionListener(e -> deleteMovieAction());
        bookBtn.addActionListener(e -> bookMovieAction());
        viewRecordsBtn.addActionListener(e -> viewRecordsAction());

        frame.setVisible(true);
    }

    // ================= NAVY BLUE BUTTON =================
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(0, 0, 128)); // navy
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        // hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(new Color(0, 0, 180)); }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(new Color(0, 0, 128)); }
        });
        return btn;
    }

    // ================= REFRESH MOVIES =================
    private void refreshMovies() {
        moviesPanel.removeAll();
        List<Movie> movies = movieManager.getMovies();

        for (Movie m : movies) {
            JPanel box = new JPanel();
            box.setPreferredSize(new Dimension(200, 100));
            box.setLayout(new GridLayout(3, 1));
            box.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            box.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Color by type
            if (m instanceof ThreeDMovie) {
                box.setBackground(Color.decode("#E6A817")); // gold for 3D
            } else {
                box.setBackground(Color.decode("#C5C2C3")); // gray for normal
            }

            JLabel nameLabel = new JLabel(m.getName());
            JLabel dateLabel = new JLabel(m.getDate());
            JLabel timeLabel = new JLabel(m.getTime());

            // Center text horizontally
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
            timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            box.add(nameLabel);
            box.add(dateLabel);
            box.add(timeLabel);

            // Click box to select movie
            box.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Remove previous selection highlight
                    if (selectedBox != null) {
                        if (selectedMovie instanceof ThreeDMovie) {
                            selectedBox.setBackground(Color.decode("#E6A817"));
                        } else {
                            selectedBox.setBackground(Color.decode("#C5C2C3"));
                        }
                    }
                    // Set new selection
                    selectedMovie = m;
                    selectedBox = box;
                    box.setBackground(Color.YELLOW); // highlight selected
                }
            });

            moviesPanel.add(box);
        }

        moviesPanel.revalidate();
        moviesPanel.repaint();
    }

    // ================= BUTTON ACTIONS =================
    private void addMovieAction() {
        String name = JOptionPane.showInputDialog(frame, "Movie Name:");
        String date = JOptionPane.showInputDialog(frame, "Date (yyyy-mm-dd):");
        String time = JOptionPane.showInputDialog(frame, "Time (hh:mm):");

        if (name != null && date != null && time != null &&
                !name.isEmpty() && !date.isEmpty() && !time.isEmpty()) {

            int is3D = JOptionPane.showConfirmDialog(frame, "Is this a 3D movie?", "3D Movie",
                    JOptionPane.YES_NO_OPTION);

            Movie movie;
            if (is3D == JOptionPane.YES_OPTION) {
                double extraCharge = Double.parseDouble(
                        JOptionPane.showInputDialog(frame, "Enter extra charge for 3D movie:"));
                movie = new ThreeDMovie(name, date, time, extraCharge);
            } else {
                movie = new Movie(name, date, time);
            }

            movieManager.addMovie(movie);
            refreshMovies();
        }
    }

    private void updateMovieAction() {
        if (selectedMovie == null) {
            JOptionPane.showMessageDialog(frame, "Please select a movie first!", "No Movie Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = JOptionPane.showInputDialog(frame, "New Movie Name:", selectedMovie.getName());
        String date = JOptionPane.showInputDialog(frame, "New Date (yyyy-mm-dd):", selectedMovie.getDate());
        String time = JOptionPane.showInputDialog(frame, "New Time (hh:mm):", selectedMovie.getTime());

        if (name != null && date != null && time != null &&
                !name.isEmpty() && !date.isEmpty() && !time.isEmpty()) {

            // Keep 3D info if applicable
            Movie updatedMovie;
            if (selectedMovie instanceof ThreeDMovie) {
                double extraCharge = ((ThreeDMovie) selectedMovie).getExtraCharge();
                updatedMovie = new ThreeDMovie(name, date, time, extraCharge);
            } else {
                updatedMovie = new Movie(name, date, time);
            }

            // Update in the movie manager
            int index = movieManager.getMovies().indexOf(selectedMovie);
            movieManager.updateMovie(index, updatedMovie);

            // Clear selection
            selectedMovie = null;
            selectedBox = null;

            refreshMovies();
        }
    }

    private void deleteMovieAction() {
        if (selectedMovie == null) {
            JOptionPane.showMessageDialog(frame, "Please select a movie first!", "No Movie Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to delete this movie?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int index = movieManager.getMovies().indexOf(selectedMovie);
            movieManager.deleteMovie(index);

            // Clear selection
            selectedMovie = null;
            selectedBox = null;

            refreshMovies();
        }
    }

    private void bookMovieAction() {
        if (selectedMovie == null) {
            JOptionPane.showMessageDialog(frame, "Please select a movie first!", "No Movie Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new SeatBookingGUI(selectedMovie, ticketManager);
    }

    private void viewRecordsAction() {
        String records = ticketManager.viewPastRecords();
        JTextArea textArea = new JTextArea(records.isEmpty() ? "No tickets booked yet." : records);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPaneRecords = new JScrollPane(textArea);
        scrollPaneRecords.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(frame, scrollPaneRecords, "Past Tickets",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieGUI::new);
    }
}
