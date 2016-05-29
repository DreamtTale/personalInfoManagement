package personInfo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;

public class Display extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private static JPanel tablePanel;
	static int MODEL;

	public Display() throws Exception {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 530);
		MODEL = 0;

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
		setContentPane(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("个人信息表");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("黑体", Font.PLAIN, 25));
		label.setBounds(310, 15, 130, 23);
		panel.add(label);

		tablePanel = new JPanel();
		tablePanel.setBounds(10, 40, 700, 410);
		panel.add(tablePanel);
		addTable();

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 683, 397);
		tablePanel.add(scrollPane);

		JComboBox<String> jcb = new JComboBox<String>(new String[] { "all", "person", "wages" });
		jcb.setBounds(600, 10, 100, 20);
		jcb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				MODEL = jcb.getSelectedIndex();
				try {
					refresh(MODEL);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(jcb);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 460, 705, 33);
		buttonPanel.setOpaque(false);
		panel.add(buttonPanel);

		JButton button1 = new JButton("查 询");
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Search search = new Search();
				search.setLocationRelativeTo(null);
				search.setModal(true);
				search.setVisible(true);
			}
		});
		buttonPanel.add(button1);

		JButton button2 = new JButton("插 入");
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Insert insert = new Insert();
				insert.setLocationRelativeTo(null);
				insert.setModal(true);
				insert.setVisible(true);
			}
		});
		buttonPanel.add(button2);

		JButton button3 = new JButton("删 除");
		button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = table.getSelectedRow();
				int option = JOptionPane.showConfirmDialog(null, "确认删除吗?", "提示:", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					try {
						Statement stat = ConnectDB.connect();
						String sql = "select * from person";
						ResultSet res = stat.executeQuery(sql);
						for (int i = 0; i <= selectRow; i++) {
							res.next();
						}
						int num = res.getInt("num");
						switch(Display.MODEL){
						case 0:
							sql = "delete from wages";
							sql = sql + " where num = '" + num + "';";
							stat.execute(sql);
							sql = "delete from person";
							sql = sql + " where num = '" + num + "';";
							stat.execute(sql);
							break;
						case 1:
							sql = "delete from person";
							sql = sql + " where num = '" + num + "';";
							stat.execute(sql);
							break;
						case 2:
							sql = "delete from wages";
							sql = sql + " where num = '" + num + "';";
							stat.execute(sql);
							break;
						}
						refresh(MODEL);
						JOptionPane.showMessageDialog(null, "删除成功");
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "删除失败");
					}
				} else {
					JOptionPane.showMessageDialog(null, "取消了删除!");
				}
			}
		});
		buttonPanel.add(button3);

		JButton button_2 = new JButton("修 改");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRows = table.getSelectedRow();
				Revise revise = new Revise(selectRows);
				revise.setLocationRelativeTo(null);
				revise.setModal(true);
				revise.setVisible(true);
			}
		});
		buttonPanel.add(button_2);

		JButton button_3 = new JButton("退 出");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "确认退出吗?", "退出?", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		buttonPanel.add(button_3);

	}

	public static void addTable() throws Exception {
		Statement stat = ConnectDB.connect();
		String sql = "select count(*) from person";
		ResultSet res = stat.executeQuery(sql);
		res.next();
		int row = res.getInt(1);
		int col = 9;

		sql = "select person.num,name,sex,age,tel,addr,id,pay,prise from person join wages on(person.num=wages.num)";
		res = stat.executeQuery(sql);

		String[] tableHead = { "认证号", "姓名", "性别", "年龄", "电话", "地址", "身份证号", "工资", "奖金" };
		Object[][] tableInfo = new Object[row][];
		int i = 0;
		while (res.next()) {
			tableInfo[i] = new Object[col];
			for (int j = 0; j < col; j++) {
				tableInfo[i][j] = res.getObject(j + 1).toString().trim();
			}
			i++;
		}
		tablePanel.setLayout(null);

		tableModel = new DefaultTableModel(tableInfo, tableHead);
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		tablePanel.setOpaque(false);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		r.setOpaque(false);
		table.setDefaultRenderer(Object.class, r);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setPreferredScrollableViewportSize(new Dimension(800, 800));
		tablePanel.add(table);
	}

	public static void refresh(int m) throws Exception {
		switch (m) {
		case 0: {
			Statement stat = ConnectDB.connect();
			String sql = "select count(*) from person";
			ResultSet res = stat.executeQuery(sql);
			res.next();
			int row = res.getInt(1);
			int col = 9;

			sql = "select person.num,name,sex,age,tel,addr,id,pay,prise from person join wages on(person.num=wages.num)";
			res = stat.executeQuery(sql);

			String[] tableHead = { "认证号", "姓名", "性别", "年龄", "电话", "地址", "身份证号", "工资", "奖金" };
			Object[][] tableInfo = new Object[row][];
			int i = 0;
			while (res.next()) {
				tableInfo[i] = new Object[col];
				for (int j = 0; j < col; j++) {
					tableInfo[i][j] = res.getObject(j + 1).toString().trim();
				}
				i++;
			}
			tableModel.setDataVector(tableInfo, tableHead);
			tablePanel.repaint();
			break;
		}
		case 1: {
			Statement stat = ConnectDB.connect();
			String sql = "select count(*) from person";
			ResultSet res = stat.executeQuery(sql);
			res.next();
			int row = res.getInt(1);
			int col = 7;

			sql = "select num,name,sex,age,tel,addr,id from person";
			res = stat.executeQuery(sql);

			String[] tableHead = { "认证号", "姓名", "性别", "年龄", "电话", "地址", "身份证号" };
			Object[][] tableInfo = new Object[row][];
			int i = 0;
			while (res.next()) {
				tableInfo[i] = new Object[col];
				for (int j = 0; j < col; j++) {
					tableInfo[i][j] = res.getObject(j + 1).toString().trim();
				}
				i++;
			}
			tableModel.setDataVector(tableInfo, tableHead);
			tablePanel.repaint();
			break;
		}
		case 2: {
			Statement stat = ConnectDB.connect();
			String sql = "select count(*) from person";
			ResultSet res = stat.executeQuery(sql);
			res.next();
			int row = res.getInt(1);
			int col = 3;

			sql = "select num,pay,prise from wages";
			res = stat.executeQuery(sql);

			String[] tableHead = { "认证号", "工资", "奖金" };
			Object[][] tableInfo = new Object[row][];
			int i = 0;
			while (res.next()) {
				tableInfo[i] = new Object[col];
				for (int j = 0; j < col; j++) {
					tableInfo[i][j] = res.getObject(j + 1).toString().trim();
				}
				i++;
			}
			tableModel.setDataVector(tableInfo, tableHead);
			tablePanel.repaint();
			break;
		}
		}
	}

	public static void main(String ars[]) throws Exception {
		Display showFrame = new Display();
		showFrame.setLocationRelativeTo(null);
		showFrame.setVisible(true);
	}
}