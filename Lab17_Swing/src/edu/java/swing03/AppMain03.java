package edu.java.swing03;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppMain03 {

	private JFrame frame;
	private JTextField textNumber1;
	private JTextField textNumber2;
	private JTextField textResult;
	private JButton btnPlus;
	private JButton btnMinus;
	private JButton btnMultiply;
	private JButton btnDivide;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppMain03 window = new AppMain03();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppMain03() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 591, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label1 = new JLabel("Number1");
		label1.setFont(new Font("굴림", Font.PLAIN, 30));
		label1.setBounds(12, 10, 170, 53);
		frame.getContentPane().add(label1);
		
		textNumber1 = new JTextField();
		textNumber1.setFont(new Font("굴림", Font.PLAIN, 30));
		textNumber1.setBounds(209, 10, 336, 53);
		frame.getContentPane().add(textNumber1);
		textNumber1.setColumns(10);
		
		JLabel label2 = new JLabel("Number2");
		label2.setFont(new Font("굴림", Font.PLAIN, 30));
		label2.setBounds(12, 81, 170, 53);
		frame.getContentPane().add(label2);
		
		textNumber2 = new JTextField();
		textNumber2.setFont(new Font("굴림", Font.PLAIN, 30));
		textNumber2.setColumns(10);
		textNumber2.setBounds(209, 81, 336, 53);
		frame.getContentPane().add(textNumber2);
		
		btnPlus = new JButton("+");
		btnPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				try {
					String s1 = textNumber1.getText();
					double x = Double.parseDouble(s1);
					double y = Double.parseDouble(textNumber2.getText());
					double result = x + y;
					textResult.setText("" + result);
				} catch (NumberFormatException ex) {
					textResult.setText("Number1 또는 Number2는 숫자로 입력하세요.");
				}
			}
		});
		btnPlus.setFont(new Font("굴림", Font.PLAIN, 30));
		btnPlus.setBounds(12, 177, 97, 59);
		frame.getContentPane().add(btnPlus);
		
		btnMinus = new JButton("-");
		btnMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double x = Double.parseDouble(textNumber1.getText());
					double y = Double.parseDouble(textNumber2.getText());
					double result = x - y;
					textResult.setText("" + result);
					
				} catch (NumberFormatException ex) {
					textResult.setText("Number1 또는 Number2는 숫자로 입력하세요.");
				}
			}
		});
		btnMinus.setFont(new Font("굴림", Font.PLAIN, 30));
		btnMinus.setBounds(161, 177, 97, 59);
		frame.getContentPane().add(btnMinus);
		
		btnMultiply = new JButton("*");
		btnMultiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 파라미터 ActionEvent e: 이벤트가 발생시킨 객체에 대한 정보를 가지고 있는 클래스.
				// actionPerforned 메서드에 전달된 argument e를 메서드에 전달
				performCalculation(e);
			}
		});
		btnMultiply.setFont(new Font("굴림", Font.PLAIN, 30));
		btnMultiply.setBounds(307, 177, 97, 59);
		frame.getContentPane().add(btnMultiply);
		
		btnDivide = new JButton("/");
		btnDivide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performCalculation(e);
			}
		});
		btnDivide.setFont(new Font("굴림", Font.PLAIN, 30));
		btnDivide.setBounds(448, 177, 97, 59);
		frame.getContentPane().add(btnDivide);
		
		textResult = new JTextField();
		textResult.setFont(new Font("굴림", Font.PLAIN, 30));
		textResult.setBounds(12, 284, 533, 72);
		frame.getContentPane().add(textResult);
		textResult.setColumns(10);
	}

	// 바깥 클래스(outer class, enclosing class)에서 정의한 메서드는 inner class에서도 사용 가능!
	private void performCalculation(ActionEvent e) {
		try {
		// JTextField에 있는 문자열을 읽어서 숫자로 변환
			double x = Double.parseDouble(textNumber1.getText());
			double y = Double.parseDouble(textNumber2.getText());
			
		// 이벤트가 발생한 버튼에 따라서 서로 다른 계산을 수행하고 결과를 문자열로 작성
			Object source = e.getSource();
			String resultMessage = "";
			if (source == btnMultiply) {
				resultMessage = x + "*" + y + " = " +(x * y);
			} else if (source == btnDivide) {
				resultMessage = x + "/" + y + " = " +(x / y);
			}
		// 결과 메세지를 JTextField에 출력
			textResult.setText(resultMessage);
			
		} catch (NumberFormatException ex) {
			textResult.setText("Number1 또는 Number2는 숫자로 입력해야 합니다.");
		}
	}

}
