package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class DersProgramiMenu {
    JFrame frame;
    JTable table;
    DefaultTableModel model;
    ArrayList<String> daysWithSubjects;

    public DersProgramiMenu() {
        frame = new JFrame("Ders Programı");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#2D3867"));
        frame.setLayout(new BorderLayout());

        String[] columns = {"Gün", "Ders Adı", "Saat"};
        String[][] data = {
                {"Pazartesi", "", ""},
                {"Salı", "", ""},
                {"Çarşamba", "", ""},
                {"Perşembe", "", ""},
                {"Cuma", "", ""}
        };

        model = new DefaultTableModel(data, columns);
        table = new JTable(model);
        table.setBackground(Color.decode("#2D3867"));
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.WHITE);
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton btnDersEkle = new JButton("Ders Ekle");
        btnDersEkle.setBackground(Color.decode("#586B9C"));
        btnDersEkle.setForeground(Color.decode("#86B0DF"));
        btnDersEkle.setFont(new Font("Arial", Font.BOLD, 16));
        btnDersEkle.setFocusPainted(false);
        btnDersEkle.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        btnDersEkle.addActionListener(e -> openAddLessonDialog());

        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#2D3867"));
        panel.add(btnDersEkle);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        daysWithSubjects = new ArrayList<>();
    }

    private void openAddLessonDialog() {
        JDialog dialog = new JDialog(frame, "Ders Ekle", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        dialog.getContentPane().setBackground(Color.decode("#2D3867"));

        JLabel lblDay = new JLabel("Gün Seçin:");
        lblDay.setForeground(Color.WHITE);
        JComboBox<String> cbDay = new JComboBox<>(new String[]{"Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma"});

        JLabel lblSubject = new JLabel("Ders Adı:");
        lblSubject.setForeground(Color.WHITE);
        JTextField tfSubject = new JTextField();

        JLabel lblTime = new JLabel("Saat:");
        lblTime.setForeground(Color.WHITE);
        JTextField tfTime = new JTextField();

        JButton btnAdd = new JButton("Ekle");
        btnAdd.setBackground(Color.decode("#586B9C"));
        btnAdd.setForeground(Color.decode("#86B0DF"));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));

        btnAdd.addActionListener(e -> {
            String day = cbDay.getSelectedItem().toString();
            String subject = tfSubject.getText().trim();
            String time = tfTime.getText().trim();

            if (subject.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Ders adı ve saat boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean dayFound = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(day)) {
                    if (model.getValueAt(i, 1).toString().isEmpty()) {
                        model.setValueAt(subject, i, 1);
                        model.setValueAt(time, i, 2);
                    } else {
                        model.addRow(new Object[]{day, subject, time});
                    }
                    dayFound = true;
                    break;
                }
            }

            if (!dayFound) {
                model.addRow(new Object[]{day, subject, time});
            }

            dialog.dispose();
        });

        dialog.add(lblDay);
        dialog.add(cbDay);
        dialog.add(lblSubject);
        dialog.add(tfSubject);
        dialog.add(lblTime);
        dialog.add(tfTime);
        dialog.add(new JLabel());
        dialog.add(btnAdd);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}

