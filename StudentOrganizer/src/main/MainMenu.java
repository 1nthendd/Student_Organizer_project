package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

class MainMenu {
    JFrame frame;

    public MainMenu() {
        frame = new JFrame("Öğrenci Organizatörü");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#2D3867"));
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        JButton btnDersProgrami = createButton("Ders Programı");
        JButton btnSinavlar = createButton("Sınavlar");
        JButton btnNotlar = createButton("Notlar");
        JButton btnTakvim = createButton("Takvim");

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(10, 0, 10, 0);
        gbc1.gridy = 3; 
        frame.add(btnTakvim, gbc1);

        btnTakvim.addActionListener(e -> new TakvimMenu());
        
        gbc1.gridy = 0;
        frame.add(btnDersProgrami, gbc1);

        gbc1.gridy = 1;
        frame.add(btnSinavlar, gbc1);

        gbc1.gridy = 2;
        frame.add(btnNotlar, gbc1);

        btnDersProgrami.addActionListener(e -> new DersProgramiMenu());
        btnSinavlar.addActionListener(e -> new SinavlarMenu());
        btnNotlar.addActionListener(e -> new NotlarMenu());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(Color.decode("#586B9C"));
        button.setForeground(Color.decode("#86B0DF"));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#586B9C"), 2, true));
        return button;
    }
}
