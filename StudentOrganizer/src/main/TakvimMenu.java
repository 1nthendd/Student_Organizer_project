package main;

import java.awt.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

public class TakvimMenu {
    JFrame frame;
    JPanel calendarPanel;
    JLabel monthLabel;
    JButton prevButton, nextButton;
    LocalDate currentDate;
    Map<LocalDate, String> notes; 
    Map<LocalDate, Color> dayColors;
    
    static TakvimMenu instance; 

    public TakvimMenu() {
        if (instance != null) {
            return;
        }
        instance = this;

        frame = new JFrame("Takvim");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#2D3867"));
        frame.setLayout(new BorderLayout());

        currentDate = LocalDate.now();
        notes = loadNotes();
        dayColors = new HashMap<>();
        // mark days that have notes with a special color
        for (LocalDate d : notes.keySet()) {
            dayColors.put(d, Color.decode("#4682B4"));
        }

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#2D3867"));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        prevButton = new JButton("<");
        prevButton.setBackground(Color.decode("#586B9C"));
        prevButton.setForeground(Color.decode("#86B0DF"));
        prevButton.setFocusPainted(false);
        prevButton.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        nextButton = new JButton(">");
        nextButton.setBackground(Color.decode("#586B9C"));
        nextButton.setForeground(Color.decode("#86B0DF"));
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        monthLabel = new JLabel();
        monthLabel.setForeground(Color.WHITE);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        updateMonthLabel();
        headerPanel.add(prevButton);
        headerPanel.add(monthLabel);
        headerPanel.add(nextButton);

        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar();
            updateMonthLabel();
        });

        nextButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar();
            updateMonthLabel();
        });

        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7));
        calendarPanel.setBackground(Color.decode("#2D3867"));
        updateCalendar();

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(calendarPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateMonthLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("tr", "TR"));
        monthLabel.setText(currentDate.format(formatter));
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        String[] dayNames = {"Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz"};
        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName, JLabel.CENTER);
            dayLabel.setForeground(Color.WHITE);
            calendarPanel.add(dayLabel);
        }

    YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonthValue());
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int lengthOfMonth = yearMonth.lengthOfMonth();

        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        for (int i = 0; i < dayOfWeek - 1; i++) {
            calendarPanel.add(new JLabel("")); 
        }

        for (int i = 1; i <= lengthOfMonth; i++) {
            LocalDate date = firstDayOfMonth.withDayOfMonth(i);
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.setBackground(getDayColor(date));
            dayButton.setForeground(Color.WHITE);
            dayButton.setFocusPainted(false);
            dayButton.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

            if (date.equals(LocalDate.now()) && notes.containsKey(date)) {
                dayButton.setBackground(Color.decode("#4682B4"));
            }

            dayButton.addActionListener(e -> openNoteForDay(date));

            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private Color getDayColor(LocalDate date) {
        if (dayColors.containsKey(date)) {
            return dayColors.get(date);
        }
        return Color.decode("#586B9C"); 
    }

    private void openNoteForDay(LocalDate date) {
        String existingNote = notes.get(date);

        JTextArea noteArea = new JTextArea();
        noteArea.setBackground(Color.decode("#3A4B76"));
        noteArea.setForeground(Color.WHITE);
        noteArea.setFont(new Font("Arial", Font.PLAIN, 14));
        noteArea.setRows(5);
        noteArea.setLineWrap(true);

        if (existingNote != null) {
            noteArea.setText(existingNote);
        }

        JButton saveButton = new JButton("Kaydet");
        saveButton.setBackground(Color.decode("#586B9C"));
        saveButton.setForeground(Color.decode("#86B0DF"));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#2D3867"));
        panel.add(new JScrollPane(noteArea), BorderLayout.CENTER);
        panel.add(saveButton, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(frame, "Zamanet", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.add(panel);
        
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveNoteForDay(date, noteArea.getText());
            }
        });

        saveButton.addActionListener(e -> {
            saveNoteForDay(date, noteArea.getText());
            dialog.dispose(); 
        });

        dialog.setVisible(true);
    }

    private void saveNoteForDay(LocalDate date, String note) {
        if (!note.isEmpty()) {
            notes.put(date, note);
            dayColors.put(date, Color.decode("#4682B4")); 
            saveNotes();
            updateCalendar();
        } else {
            notes.remove(date);
            dayColors.remove(date);
            saveNotes();
            updateCalendar();
        }
    }

    private Map<LocalDate, String> loadNotes() {
        Map<LocalDate, String> notes = new HashMap<>();
        File file = new File("notes.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        LocalDate date = LocalDate.parse(parts[0]);
                        notes.put(date, parts[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return notes;
    }

    private void saveNotes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("notes.txt"))) {
            for (Map.Entry<LocalDate, String> entry : notes.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (instance == null) {
            new TakvimMenu();
        }
    }
}