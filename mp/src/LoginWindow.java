import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

public class LoginWindow extends JFrame{

    JComboBox loginComboBox;
    private static String selectedUsername;

    LoginWindow(ArrayList<User> allUsers, Connection myConn){
        ImageIcon mpLogo = new ImageIcon("play-button.png");
        this.setIconImage(mpLogo.getImage());
        this.setSize(500,300);
        this.setTitle("Login");
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        JLabel titleLabel = new JLabel("Music Player");
        titleLabel.setLocation(170, 20);
        titleLabel.setSize(300, 40);
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 24));
        this.add(titleLabel);

        JLabel logInLabel = new JLabel("Choose your user: ");
        logInLabel.setLocation(180, 80);
        logInLabel.setSize(300, 40);
        logInLabel.setForeground(Color.black);
        logInLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
        this.add(logInLabel);

        String[] allUsernames = new String[1000];
        for(int i = 0; i < allUsers.size(); i++){
            allUsernames[i] = allUsers.get(i).getUsername();
        }

        loginComboBox = new JComboBox(allUsernames);
        loginComboBox.setBounds(140,110,200,30);
        loginComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loginComboBox.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null,"Select an existing account!");
                }
                else {
                    selectedUsername = loginComboBox.getSelectedItem().toString();
                }
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        loginButton.setBackground(new Color(0x83d4ec));
        loginButton.setBounds(180,170, 120, 30);
        loginButton.setFocusable(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedUsername != null) {
                    Controller.setSelectedUsername(selectedUsername);
                    try {
                        Controller.startMainApp(myConn);
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                        unsupportedLookAndFeelException.printStackTrace();
                    } catch (InstantiationException instantiationException) {
                        instantiationException.printStackTrace();
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Select an user first!");
                }
            }
        });
        this.add(loginButton);

        this.add(loginComboBox);
        this.setVisible(true);
    }

    public static String getSelectedUsername() {
        return selectedUsername;
    }

    public static void setSelectedUsername(String selectedUsername) {
        LoginWindow.selectedUsername = selectedUsername;
    }
}
