package blockchain.app.blockmatrix;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
    private JPanel panel1;
    private JButton hitButton;
    private JList inventory;
    private JButton tradeButton;
    private JList availableTrades;
    private JTextField playerName;
    private JButton takeWeaponButton;
    private JTextField eventField;
    
    // accesses the player in the app.java
    private Player player;

    //You're mother gay and homosexual

    public GUI(Player player) {
        this.player = player;
        
/*        playerName.setText(player.getName());
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
