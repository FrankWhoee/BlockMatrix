package blockchain.app.blockmatrix;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {
	private JFrame frame;
    private JPanel panel1;
    /*private JButton hitButton;
    private JList inventory;
    private JButton tradeButton;
    private JList availableTrades;
    private JTextField playerName;
    private JButton takeWeaponButton;
    private JTextField eventField;*/
    
    // accesses the player in the app.java
    private Player player;

    //You're mother gay and homosexual

    public GUI(Player player) {
        this.player = player;

        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(new Dimension(1280, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel1);
        
        /*playerName.setText(player.getName());

/*      playerName.setText(player.getName());

        playerName.setEditable(false);
        
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        tradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        takeWeaponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });*/
    }
}
