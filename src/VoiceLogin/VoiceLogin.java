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
	SecondPage sp = new SecondPage(); // 두번째 페이지 인스턴스 생성
	String snum;
	VoiceLogin(){
		Login();
	}
	public void Login() {
		frame = new JFrame();
		frame.setBackground(UIManager.getColor("Button.highlight"));
		frame.setTitle("도서관 자리발권");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("도서관 자리발권 시스템");
		lblNewLabel.setBounds(0, 32, 434, 41);
		lblNewLabel.setForeground(UIManager.getColor("ProgressBar.selectionBackground"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 35));
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(84, 147, 286, 34);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("학번을 입력하세요");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(UIManager.getColor("ProgressBar.selectionBackground"));
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_1.setBounds(121, 98, 198, 34);
		frame.getContentPane().add(lblNewLabel_1);
		
		btnNewButton = new JButton("ENTER");
		btnNewButton.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		btnNewButton.setBounds(167, 207, 119, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkTable();
			}
		});
		
		frame.setVisible(true);
	}
	//입력한 학번을 확인하여 등록되어있을 경우 개인의 고유번호를 갖고 두번째 페이지로 넘어감
	public void checkTable() {
		switch(textField.getText()) {
		case  "1322039" : 
			sp.Action(1); //괄호안의 숫자가 개인의 고유번호
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
			JOptionPane.showMessageDialog(null, "다시 입력하세요."); //팝업
			break;
		}
	}
	
	public static void main(String[] args) {
		new VoiceLogin();
	}
}
