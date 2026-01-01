package main;

import javax.swing.*;
import java.awt.*;

class NotlarMenu {
    JFrame frame;
    JButton btnAddNote;
    JPanel notesPanel;

    public NotlarMenu() {
        frame = new JFrame("Notlar");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#2D3867"));
        frame.setLayout(new BorderLayout());

        btnAddNote = new JButton("Not Ekle");
        btnAddNote.setBackground(Color.decode("#586B9C"));
        btnAddNote.setForeground(Color.decode("#86B0DF"));
        btnAddNote.setFont(new Font("Arial", Font.BOLD, 16));
        btnAddNote.setFocusPainted(false);
        btnAddNote.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS));
        notesPanel.setBackground(Color.decode("#2D3867"));

        btnAddNote.addActionListener(e -> openAddNoteDialog());

        frame.add(btnAddNote, BorderLayout.NORTH);
        frame.add(new JScrollPane(notesPanel), BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openAddNoteDialog() {
        JDialog dialog = new JDialog(frame, "Yeni Not", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.getContentPane().setBackground(Color.decode("#2D3867"));

        JLabel lblTitle = new JLabel("Başlık:");
        lblTitle.setForeground(Color.WHITE);
        JTextField tfTitle = new JTextField();

        JLabel lblNote = new JLabel("Not:");
        lblNote.setForeground(Color.WHITE);
        JTextArea taNote = new JTextArea();

        JButton btnSave = new JButton("Kaydet");
        btnSave.setBackground(Color.decode("#586B9C"));
        btnSave.setForeground(Color.decode("#86B0DF"));
        btnSave.setFocusPainted(false);
        btnSave.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        btnSave.addActionListener(e -> {
            String title = tfTitle.getText().trim();
            String note = taNote.getText().trim();

            if (title.isEmpty() || note.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Başlık ve not boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel notePanel = new JPanel();
            notePanel.setBackground(Color.decode("#2D3867"));
            notePanel.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel(title);
            titleLabel.setForeground(Color.WHITE);
            JTextArea noteArea = new JTextArea(note);
            noteArea.setEditable(false);
            noteArea.setBackground(Color.decode("#2D3867"));
            noteArea.setForeground(Color.WHITE);

            notePanel.add(titleLabel, BorderLayout.NORTH);
            notePanel.add(new JScrollPane(noteArea), BorderLayout.CENTER);

            notesPanel.add(notePanel);
            notesPanel.revalidate();
            notesPanel.repaint();

            dialog.dispose();
        });

        dialog.add(lblTitle);
        dialog.add(tfTitle);
        dialog.add(lblNote);
        dialog.add(new JScrollPane(taNote));
        dialog.add(new JLabel());
        dialog.add(btnSave);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
