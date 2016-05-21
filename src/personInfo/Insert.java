package personInfo;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Insert extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField numField;
	private JTextField nameField;
	private JTextField sexField;
	private JTextField ageField;
	private JTextField telField;
	private JTextField addrField;
	private JTextField idField;
	private JTextField payField;
	private JTextField priseField;

	public Insert() {
		setTitle("插入信息");
		setBounds(0, 0, 355, 320);
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
		panel.setLayout(new BorderLayout());
		add(panel);
		JPanel insertPanel = new JPanel();
		insertPanel.setLayout(null);
		insertPanel.setOpaque(false);
		panel.add(insertPanel, BorderLayout.CENTER);
		JLabel num = new JLabel("认证号:");
		num.setBounds(80, 15, 55, 15);
		insertPanel.add(num);
		JLabel name = new JLabel("姓    名:");
		name.setBounds(80, 40, 55, 15);
		insertPanel.add(name);
		JLabel sex = new JLabel("性    别:");
		sex.setBounds(80,65, 55, 15);
		insertPanel.add(sex);
		JLabel age = new JLabel("年     龄:");
		age.setBounds(80, 90, 55, 15);
		insertPanel.add(age);
		JLabel tel = new JLabel("电    话:");
		tel.setBounds(80, 115, 55, 15);
		insertPanel.add(tel);
		JLabel addr = new JLabel("地    址:");
		addr.setBounds(80, 140, 55, 15);
		insertPanel.add(addr);
		JLabel id = new JLabel("身份证:");
		id.setBounds(80, 165, 55, 15);
		insertPanel.add(id);
		JLabel pay = new JLabel("工    资:");
		pay.setBounds(80, 190, 55, 15);
		insertPanel.add(pay);
		JLabel prise = new JLabel("奖    金:");
		prise.setBounds(80, 215, 55, 15);
		insertPanel.add(prise);
		numField = new JTextField();
		numField.setBounds(125, 13, 132, 20);
		insertPanel.add(numField);
		nameField = new JTextField();
		nameField.setBounds(125, 38, 132, 20);
		insertPanel.add(nameField);
		sexField = new JTextField();
		sexField.setBounds(125, 63, 132, 20);
		insertPanel.add(sexField);
		ageField = new JTextField();
		ageField.setBounds(125, 88, 132, 20);
		insertPanel.add(ageField);
		telField = new JTextField();
		telField.setBounds(125, 113, 132, 20);
		insertPanel.add(telField);
		addrField = new JTextField();
		addrField.setBounds(125, 138, 132, 20);
		insertPanel.add(addrField);
		idField = new JTextField();
		idField.setBounds(125, 163, 132, 20);
		insertPanel.add(idField);
		payField = new JTextField();
		payField.setBounds(125, 188, 132, 20);
		insertPanel.add(payField);
		priseField = new JTextField();
		priseField.setBounds(125, 213, 132, 20);
		insertPanel.add(priseField);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setOpaque(false);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		JButton insertButton = new JButton("插    入");
		insertButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Statement stat = ConnectDB.connect();
					if (Integer.parseInt(numField.getText()) > 1000000
							&& Integer.parseInt(numField.getText()) < 9999999) {
						Person[] insertInfo = new Person[9];
						insertInfo[0] = new Person(numField.getText());
						insertInfo[1] = new Person(nameField.getText());
						insertInfo[2] = new Person(sexField.getText());
						insertInfo[3] = new Person(ageField.getText());
						insertInfo[4] = new Person(telField.getText());
						insertInfo[5] = new Person(addrField.getText());
						insertInfo[6] = new Person(idField.getText());
						insertInfo[7] = new Person(payField.getText());
						insertInfo[8] = new Person(priseField.getText());
						Display.add(insertInfo);
						String sql = "insert into person";
						sql = sql + " values(";
						sql = sql + "'" + numField.getText() + "',";
						sql = sql + "'" + nameField.getText() + "',";
						sql = sql + "'" + sexField.getText() + "',";
						sql = sql + "'" + ageField.getText() + "',";
						sql = sql + "'" + telField.getText() + "',";
						sql = sql + "'" + addrField.getText() + "',";
						sql = sql + "'" + idField.getText() + "')";
						stat.execute(sql);
						sql = "insert into wages";
						sql = sql + " values(";
						sql = sql + "'" + numField.getText() + "',";
						sql = sql + payField.getText() + ",";
						sql = sql + priseField.getText() + ")";
						stat.execute(sql);
						stat.close();
						JOptionPane.showMessageDialog(null, "插入成功");
						reFresh();
					} else {
						JOptionPane.showMessageDialog(null, "账号不可用");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "帐号已存在");
				}
			}
		});
		buttonPanel.add(insertButton);
		getRootPane().setDefaultButton(insertButton);
		JLabel space = new JLabel("        ");
		buttonPanel.add(space);
		JButton cancelButton = new JButton("取  消");
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		buttonPanel.add(cancelButton);
	}

	public void reFresh() {
		numField.setText("");
		nameField.setText("");
		sexField.setText("");
		ageField.setText("");
		telField.setText("");
		addrField.setText("");
		idField.setText("");
		payField.setText("");
		priseField.setText("");
		numField.requestFocus();
	}

//	public static void main(String[] args) {
//		Insert insert = new Insert();
//		insert.setLocationRelativeTo(null);
//		insert.setModal(true);
//		insert.setVisible(true);
//	}
}
