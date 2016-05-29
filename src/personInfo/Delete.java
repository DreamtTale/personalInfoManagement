package personInfo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class Delete extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel panel;
	private int model;

	public Delete(int selectRow,int m) {
		setTitle("ɾ����Ϣ");
		setBounds(0, 0, 300, 250);
		model=m;
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
		JPanel deletePanel = new JPanel();
		deletePanel.setLayout(null);
		deletePanel.setOpaque(false);
		panel.add(deletePanel, BorderLayout.CENTER);
		JCheckBox choose = new JCheckBox("����ɾ��");
		choose.setBounds(40, 20, 80, 20);
		choose.setOpaque(false);
		deletePanel.add(choose);
		JTextField tf = new JTextField();
		tf.setBounds(70, 50, 150, 20);
		JLabel deleteMore = new JLabel("��");
		deleteMore.setBounds(70, 80, 50, 20);
		JTextField tf2 = new JTextField();
		tf2.setBounds(70, 110, 150, 20);
		choose.addChangeListener(new ChangeAdapter() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (choose.isSelected()) {
					deletePanel.add(tf);
					deletePanel.add(deleteMore);
					deletePanel.add(tf2);
					deletePanel.repaint();
				} else {
					deletePanel.remove(tf);
					deletePanel.remove(deleteMore);
					deletePanel.remove(tf2);
					deletePanel.repaint();
				}
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setOpaque(false);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		JButton deleteButton = new JButton("ɾ    ��");
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "��ʾ:", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if (choose.isSelected()) {
						try {
							Statement stat = ConnectDB.connect();
							String sql = "select * from person";
							ResultSet res = stat.executeQuery(sql);
							for (int i = 0; i <= selectRow; i++) {
								res.next();
							}
							Display.refresh(model);
							int numBegin=Integer.valueOf(tf.getText()),
									numEnd=Integer.valueOf(tf2.getText());
							for (int i = 0; i <= numBegin; i++) {
								res.next();
							}
							for(int i=numBegin;i<=numEnd;i++){
								Display.refresh(model);
								String num=res.getString("num");
								sql = "delete from wages";
								sql = sql + " where num = '" + num + "';";
								System.out.println(sql);
								stat.execute(sql);
								sql = "delete from person";
								sql = sql + " where num = '" + num + "';";
								stat.execute(sql);
								res.next();
							}
							JOptionPane.showMessageDialog(null, "ɾ���ɹ�,���µ�½ϵͳ�鿴");
							dispose();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
						}
					} else {
						try {
							Statement stat = ConnectDB.connect();
							String sql = "select * from person";
							ResultSet res = stat.executeQuery(sql);
							for (int i = 0; i <= selectRow; i++) {
								res.next();
							}
							Display.refresh(model);
							int num=res.getInt("num");
							sql = "delete from wages";
							sql = sql + " where num = '" + num + "';";
							stat.execute(sql);
							sql = "delete from person";
							sql = sql + " where num = '" + num + "';";
							stat.execute(sql);
							JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
							dispose();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
						}
					} 
				}else {
					JOptionPane.showMessageDialog(null, "ȡ����ɾ��!");
				}
			}
		});
		buttonPanel.add(deleteButton);
		getRootPane().setDefaultButton(deleteButton);
		JLabel space = new JLabel("        ");
		buttonPanel.add(space);
		JButton cancelButton = new JButton("ȡ  ��");
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		buttonPanel.add(cancelButton);
	}
}
