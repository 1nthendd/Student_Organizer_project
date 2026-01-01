package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class SinavlarMenu {
    JFrame frame;
    JTable table;
    DefaultTableModel model;
    ArrayList<String> subjects;

    public SinavlarMenu() {
        frame = new JFrame("Sınavlar");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#2D3867"));
        frame.setLayout(new BorderLayout());

        String[] columns = {"Ders Adı", "Vize", "Final", "Laboratuvar", "Ortalama"};
        subjects = new ArrayList<>();

        String[][] data = new String[subjects.size()][5];
        for (int i = 0; i < subjects.size(); i++) {
            data[i][0] = subjects.get(i);
        }

        model = new DefaultTableModel(data, columns);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        
        table.setBackground(Color.decode("#2D3867"));
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.WHITE);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#2D3867"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAddSubject = new JButton("Yeni Ders Ekle");
        btnAddSubject.setBackground(Color.decode("#586B9C"));
        btnAddSubject.setForeground(Color.decode("#86B0DF"));
        btnAddSubject.setFont(new Font("Arial", Font.BOLD, 16));
        btnAddSubject.setFocusPainted(false);
        btnAddSubject.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        JButton btnCalculateAverage = new JButton("Ortalama Hesapla");
        btnCalculateAverage.setBackground(Color.decode("#586B9C"));
        btnCalculateAverage.setForeground(Color.decode("#86B0DF"));
        btnCalculateAverage.setFont(new Font("Arial", Font.BOLD, 16));
        btnCalculateAverage.setFocusPainted(false);
        btnCalculateAverage.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        btnAddSubject.addActionListener(e -> openAddSubjectDialog());
        btnCalculateAverage.addActionListener(e -> calculateAllAverages());

        buttonPanel.add(btnAddSubject);	
        buttonPanel.add(btnCalculateAverage);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openAddSubjectDialog() {
        JDialog dialog = new JDialog(frame, "Yeni Ders Ekle", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 2, 10, 10));
        dialog.getContentPane().setBackground(Color.decode("#2D3867"));

        JLabel lblSubject = new JLabel("Ders Adı:");
        lblSubject.setForeground(Color.WHITE);
        JTextField tfSubject = new JTextField();

        JButton btnAdd = new JButton("Ekle");
        btnAdd.setBackground(Color.decode("#586B9C"));
        btnAdd.setForeground(Color.decode("#86B0DF"));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        btnAdd.addActionListener(e -> {
            String subject = tfSubject.getText().trim();
            if (subject.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Ders adı boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            subjects.add(subject);
            model.addRow(new Object[]{subject, "", "", "", ""});
            dialog.dispose();
        });

        dialog.add(lblSubject);
        dialog.add(tfSubject);
        dialog.add(new JLabel());
        dialog.add(btnAdd);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void calculateAverage(int row) {
        try {
            double vize = Double.parseDouble((String) model.getValueAt(row, 1));
            double finalGrade = Double.parseDouble((String) model.getValueAt(row, 2));
            double lab = model.getValueAt(row, 3) != null && !model.getValueAt(row, 3).toString().isEmpty() ? 
                    Double.parseDouble((String) model.getValueAt(row, 3)) : 0;

            double average;
            if (lab == 0) {
                average = vize * 0.4 + finalGrade * 0.6;
            } else {
                average = lab * 0.3 + vize * 0.3 + finalGrade * 0.4;
            }
            model.setValueAt(String.format("%.2f", average), row, 4);
        } catch (NumberFormatException ex) {
        }
    }

    public void calculateAllAverages() {
        for (int i = 0; i < model.getRowCount(); i++) {
            calculateAverage(i);
        }
    }
}
