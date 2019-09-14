package VoiceLogin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class VoiceLogin {
	private JFrame frame;
	private JTextField textField;
	private JButton btnNewButton;
	SecondPage sp = new SecondPage(); //�ι�° ������ �ν��Ͻ�����
	String snum;
	VoiceLogin(){
		Login();
	}
	public void Login() {
		frame = new JFrame();
		frame.setBackground(UIManager.getColor("Button.highlight"));
		frame.setTitle("������ �ڸ��߱�");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("������ �ڸ��߱� �ý���");
		lblNewLabel.setBounds(0, 32, 434, 41);
		lblNewLabel.setForeground(UIManager.getColor("ProgressBar.selectionBackground"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("����", Font.BOLD, 35));
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(84, 147, 286, 34);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("�й��� �Է��ϼ���.");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(UIManager.getColor("ProgressBar.selectionBackground"));
		lblNewLabel_1.setFont(new Font("����", Font.BOLD, 20));
		lblNewLabel_1.setBounds(121, 98, 198, 34);
		frame.getContentPane().add(lblNewLabel_1);
		
		btnNewButton = new JButton("ENTER");
		btnNewButton.setFont(new Font("����", Font.BOLD | Font.ITALIC, 20));
		btnNewButton.setBounds(167, 207, 119, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkTable();
			}
		});
		
		frame.setVisible(true);
	}
	//�Է��� �й��� Ȯ���Ͽ� ��ϵǾ������� ������ ������ȣ�� ������ �ι�° �������� �Ѿ
	public void checkTable() {
		switch(textField.getText()) {
		case  "1322039" : 
			sp.Action(1); //���ڴ� ������ȣ
			break;
		case  "1422011" : 
			sp.Action(2);
			break;
		case "1422061" :
			sp.Action(3);
			break;
		case "1422007" :
			sp.Action(4);
			break;
		case "1422014" :
			sp.Action(5);
			break;
		default :
			JOptionPane.showMessageDialog(null, "�ٽ� �Է��ϼ���."); //�˾�
			break;
		}
	}
	
	public static void main(String[] args) {
		new VoiceLogin();
	}
}
