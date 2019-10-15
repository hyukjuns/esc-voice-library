package VoiceLogin;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ThirdPage {
	private JFrame frame;
	private JTable table;
	private JTable table2;
	private JTable table3;
	private JTable table4;
	private String index;
	
	String[][] table_data = new String[][] {
		{"1", "20"},
		{"2", "19"},
		{"3", "18"},
		{"4", "17"},
		{"5", "16"},
		{"6", "15"},
		{"7", "14"},
		{"8", "13"},
		{"9", "12"},
		{"10", "11"},
	};
	String[][] table2_data = new String[][] {
		{"21", "40"},
		{"22", "39"},
		{"23", "38"},
		{"24", "37"},
		{"25", "36"},
		{"26", "35"},
		{"27", "34"},
		{"28", "33"},
		{"29", "32"},
		{"30", "31"},
	};
	String[][] table3_data = new String[][] {
		{"41", "60"},
		{"42", "59"},
		{"43", "58"},
		{"44", "57"},
		{"45", "56"},
		{"46", "55"},
		{"47", "54"},
		{"48", "53"},
		{"49", "52"},
		{"50", "51"},
	};
	String[][] table4_data = new String[][] {
		{"61", "80"},
		{"62", "79"},
		{"63", "78"},
		{"64", "77"},
		{"65", "76"},
		{"66", "75"},
		{"67", "74"},
		{"68", "73"},
		{"69", "72"},
		{"70", "71"},
	};
	@SuppressWarnings("serial")
	public void third() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			table_data,
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setBounds(12, 57, 81, 160);
		frame.getContentPane().add(table);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        index = table_data[row][col];
		        if (row >= 0 && col >= 0) {
		        	table.setSelectionBackground(Color.BLUE);        	
		        	//JOptionPane.showMessageDialog(null, "발권이 완료되었습니다."+row+" "+col);
		        }
		    }
		});
		
		table2 = new JTable();
		table2.setCellSelectionEnabled(true);
		table2.setModel(new DefaultTableModel(
				table2_data,
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table2.setBounds(105, 57, 81, 160);
		frame.getContentPane().add(table2);
		table2.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		       int row = table2.rowAtPoint(evt.getPoint());
		       int col = table2.columnAtPoint(evt.getPoint());
		       index = table2_data[row][col];
		        if (row >= 0 && col >= 0) {
		        	table2.setSelectionBackground(Color.BLUE);
		        	//JOptionPane.showMessageDialog(null, "발권이 완료되었습니다."+row+" "+col);
		        }
		    }
		});
		
		table3 = new JTable();
		table3.setCellSelectionEnabled(true);
		table3.setModel(new DefaultTableModel(
				table3_data,
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table3.setBounds(248, 57, 81, 160);
		frame.getContentPane().add(table3);
		table3.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table3.rowAtPoint(evt.getPoint());
		        int col = table3.columnAtPoint(evt.getPoint());
		        index = table3_data[row][col];
		        if (row >= 0 && col >= 0) {
		        	table3.setSelectionBackground(Color.BLUE);
		        	//JOptionPane.showMessageDialog(null, "발권이 완료되었습니다."+row+" "+col);
		        }
		    }
		});
		
		table4 = new JTable();
		table4.setCellSelectionEnabled(true);
		table4.setModel(new DefaultTableModel(
				table4_data,
			new String[] {
				"New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table4.setBounds(341, 57, 81, 160);
		frame.getContentPane().add(table4);
		table4.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		       int row = table4.rowAtPoint(evt.getPoint());
		       int col = table4.columnAtPoint(evt.getPoint());
		       index = table4_data[row][col];
		        if (row >= 0 && col >= 0) {
		        	table4.setSelectionBackground(Color.BLUE);
		        	//JOptionPane.showMessageDialog(null, "발권이 완료되었습니다."+row+" "+col);
		        }
		    }
		});
		
		// DefaultTableCellHeaderRenderer 생성(가운데 정렬을 위함)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule2 = table2.getColumnModel();
		for (int i = 0; i < tcmSchedule2.getColumnCount(); i++) {
		tcmSchedule2.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule3 = table3.getColumnModel();
		for (int i = 0; i < tcmSchedule3.getColumnCount(); i++) {
		tcmSchedule3.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule4 = table4.getColumnModel();
		for (int i = 0; i < tcmSchedule4.getColumnCount(); i++) {
		tcmSchedule4.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		
		JLabel lblNewLabel = new JLabel("제 1 열람실");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("돋움", Font.BOLD, 20));
		lblNewLabel.setBounds(167, 10, 104, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("제 1 열람실");
		btnNewButton.setFont(new Font("돋움", Font.BOLD, 15));
		btnNewButton.setForeground(SystemColor.desktop);
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(182, 229, 70, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,index+"번 자리 발권이 완료 되었습니다.");
			}
		});
	frame.setVisible(true);
	}
	
}
