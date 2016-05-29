package personInfo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Revise extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField nameField;
	private JTextField sexField;
	private JTextField ageField;
	private JTextField telField;
	private JTextField addrField;
	private JTextField idField;
	private JTextField payField;
	private JTextField priseField;

	public Revise(int selectRow) {
		setTitle("修改信息");
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
		JPanel revisePanel = new JPanel();
		revisePanel.setLayout(null);
		revisePanel.setOpaque(false);
		panel.add(revisePanel, BorderLayout.CENTER);
		JLabel num = new JLabel("认证号:");
		num.setBounds(80, 15, 55, 15);
		revisePanel.add(num);
		JLabel name = new JLabel("姓    名:");
		name.setBounds(80, 40, 55, 15);
		revisePanel.add(name);
		JLabel sex = new JLabel("性    别:");
		sex.setBounds(80, 65, 55, 15);
		revisePanel.add(sex);
		JLabel age = new JLabel("年     龄:");
		age.setBounds(80, 90, 55, 15);
		revisePanel.add(age);
		JLabel tel = new JLabel("电    话:");
		tel.setBounds(80, 115, 55, 15);
		revisePanel.add(tel);
		JLabel addr = new JLabel("地    址:");
		addr.setBounds(80, 140, 55, 15);
		revisePanel.add(addr);
		JLabel id = new JLabel("身份证:");
		id.setBounds(80, 165, 55, 15);
		revisePanel.add(id);
		JLabel pay = new JLabel("工    资:");
		pay.setBounds(80, 190, 55, 15);
		revisePanel.add(pay);
		JLabel prise = new JLabel("奖    金:");
		prise.setBounds(80, 215, 55, 15);
		revisePanel.add(prise);
		JLabel numField = new JLabel();
		numField.setBounds(125, 13, 132, 20);
		revisePanel.add(numField);
		nameField = new JTextField();
		nameField.setBounds(125, 38, 132, 20);
		revisePanel.add(nameField);
		sexField = new JTextField();
		sexField.setBounds(125, 63, 132, 20);
		revisePanel.add(sexField);
		ageField = new JTextField();
		ageField.setBounds(125, 88, 132, 20);
		revisePanel.add(ageField);
		telField = new JTextField();
		telField.setBounds(125, 113, 132, 20);
		revisePanel.add(telField);
		addrField = new JTextField();
		addrField.setBounds(125, 138, 132, 20);
		revisePanel.add(addrField);
		idField = new JTextField();
		idField.setBounds(125, 163, 132, 20);
		revisePanel.add(idField);
		payField = new JTextField();
		payField.setBounds(125, 188, 132, 20);
		revisePanel.add(payField);
		priseField = new JTextField();
		priseField.setBounds(125, 213, 132, 20);
		revisePanel.add(priseField);

		try {
			Statement stat = ConnectDB.connect();
			String sql = "select * from person";
			ResultSet res = stat.executeQuery(sql);
			for (int i = 0; i <= selectRow; i++) {
				res.next();
			}
			numField.setText(res.getString("num"));
			stat.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setOpaque(false);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		JButton reviseButton = new JButton("修  改");
		reviseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Statement stat = ConnectDB.connect();
					Person[] reviseInfo = new Person[9];
					reviseInfo[0] = new Person(numField.getText());
					reviseInfo[1] = new Person(nameField.getText());
					reviseInfo[2] = new Person(sexField.getText());
					reviseInfo[3] = new Person(ageField.getText());
					reviseInfo[4] = new Person(telField.getText());
					reviseInfo[5] = new Person(addrField.getText());
					reviseInfo[6] = new Person(idField.getText());
					reviseInfo[7] = new Person(payField.getText());
					reviseInfo[8] = new Person(priseField.getText());
					String sql = "update person ";
					sql = sql + "set name = '" + nameField.getText() + "',";
					sql = sql + "sex = '" + sexField.getText() + "',";
					sql = sql + "age = '" + ageField.getText() + "',";
					sql = sql + "tel = '" + telField.getText() + "',";
					sql = sql + "addr = '" + addrField.getText() + "',";
					sql = sql + "id = '" + idField.getText() + "'";
					sql = sql + " where num ='" + numField.getText()+"'";
					stat.execute(sql);
					sql="update wages ";
					sql = sql + "set pay = " + payField.getText() + ",";
					sql = sql + "prise = " + priseField.getText() + "";
					sql = sql + " where num ='" + numField.getText()+"'";
					stat.execute(sql);
					Display.refresh(Display.MODEL);
					JOptionPane.showMessageDialog(null, "更改成功");
					dispose();
				} catch (Exception e1) {

				}
			}
		});
		buttonPanel.add(reviseButton);
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
}
