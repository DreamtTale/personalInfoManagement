package personInfo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField userName;
	private JPasswordField password;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
					UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel());
					Login loginGUI = new Login();
					loginGUI.setLocationRelativeTo(null);
					loginGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("µÇ  Â½");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 360, 300);

		// Ìí¼Ó±³¾°Í¼Æ¬
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				try {
					BufferedImage img = ImageIO.read(new File("pic/login.jpg"));
					g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		setContentPane(panel);
		panel.setLayout(null);

		JLabel userNameLabel = new JLabel("ÓÃ»§Ãû£º");
		userNameLabel.setBounds(75, 135, 55, 15);
		panel.add(userNameLabel);

		JLabel passwordLabel = new JLabel("ÃÜ   Âë£º");
		passwordLabel.setBounds(75, 165, 55, 15);
		panel.add(passwordLabel);

		userName = new JTextField();
		userName.setBounds(125, 135, 130, 20);
		userName.setColumns(16);
		panel.add(userName);

		password = new JPasswordField();
		password.setHorizontalAlignment(SwingConstants.LEFT);
		password.setColumns(16);
		password.setEchoChar('*');
		password.setBounds(125, 165, 130, 20);
		panel.add(password);

		JButton loginButton = new JButton("µÇ  Â½");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					ConnectDB.init(userName.getText(),String.valueOf(password.getPassword()));
					ConnectDB.connect();
					JOptionPane.showMessageDialog(null, "µÇÂ¼³É¹¦");
					setVisible(false);
					Display showFrame = new Display();
					showFrame.setLocationRelativeTo(null);
					showFrame.setVisible(true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "µÇÂ¼Ê§°Ü");
				}
			}
		});
		loginButton.setBounds(85, 205, 75, 25);
		panel.add(loginButton);

		JButton quitButton = new JButton("ÍË  ³ö");
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		quitButton.setBounds(190, 205, 75, 25);
		panel.add(quitButton);
	}
}
