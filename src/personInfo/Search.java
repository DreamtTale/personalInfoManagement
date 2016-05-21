package personInfo;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Search extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel panel;

	Search() {
		setTitle("��ѯ��Ϣ");
		setBounds(0, 0, 350, 280);
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				try {
					BufferedImage img = ImageIO.read(new File("pic/bg.jpg"));
					g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout());

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setOpaque(false);
		JLabel label = new JLabel("��֤�ţ�");
		label.setBounds(85, 28, 55, 15);
		searchPanel.add(label);
		JTextField tf = new JTextField();
		tf.setBounds(135, 25, 70, 20);
		searchPanel.add(tf);
		JLabel name = new JLabel("��    ����");
		name.setBounds(85, 55, 55, 15);
		searchPanel.add(name);
		JLabel sex = new JLabel("��    ��");
		sex.setBounds(85, 70, 55, 15);
		searchPanel.add(sex);
		JLabel age = new JLabel("��    �䣺");
		age.setBounds(85, 85, 55, 15);
		searchPanel.add(age);
		JLabel tel = new JLabel("��    ����");
		tel.setBounds(85, 100, 55, 15);
		searchPanel.add(tel);
		JLabel addr = new JLabel("��    ַ��");
		addr.setBounds(85, 115, 55, 15);
		searchPanel.add(addr);
		JLabel id = new JLabel("���֤��");
		id.setBounds(85, 130, 55, 15);
		searchPanel.add(id);
		JLabel pay = new JLabel("��    �ʣ�");
		pay.setBounds(85, 145, 55, 15);
		searchPanel.add(pay);
		JLabel prise = new JLabel("��    ��");
		prise.setBounds(85, 160, 55, 15);
		searchPanel.add(prise);
		JLabel[] resLabel = new JLabel[8];
		for (int i = 0; i < 8; i++) {
			resLabel[i] = new JLabel();
			resLabel[i].setBounds(140, 55 + 15 * i, 55, 15);
			searchPanel.add(resLabel[i]);
		}
		panel.add(searchPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setOpaque(false);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		JButton submitButton = new JButton("�� ѯ");
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sql = "select name,sex,age,tel,addr,id,pay,prise from person join wages on(person.num=wages.num) where "
						+ "person.num='" + tf.getText() + "'";
				Statement stmt;
				try {
					stmt = ConnectDB.connect();
					ResultSet res = stmt.executeQuery(sql);
					res.next();
					for (int i = 0; i < 8; i++) {
						resLabel[i].setText(res.getObject(i + 1).toString().trim());
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "�ʺŲ�����");
				}
			}
		});
		buttonPanel.add(submitButton);
		getRootPane().setDefaultButton(submitButton);

		JLabel space = new JLabel("        ");
		buttonPanel.add(space);

		JButton cancelButton = new JButton("ȡ ��");
		cancelButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		buttonPanel.add(cancelButton);
	}
}
