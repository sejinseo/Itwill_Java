package edu.java.swing12;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Appmain12 {
	// 테이블의 컬럼 이름을 상수
	private static final String[] COLUMN_NAMES = { "국어", "영어", "수학", "총점", "평균" };

	private JFrame frame;
	private JTextField textKorean;
	private JTextField textEnglish;
	private JTextField textMath;
	private JTable table;
	private DefaultTableModel tableModel; // 테이블의 데이터(행row, 열colum, 셀cell)를 관리(추가, 삭제)하는 클래스
	private JButton btnInput;
	private JButton btnDelete;
	private JLabel labelKorean;
	private JLabel labelEnglish;
	private JLabel labelMath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Appmain12 window = new Appmain12();
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
	public Appmain12() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 378);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		labelKorean = new JLabel("국어");
		labelKorean.setFont(new Font("굴림", Font.PLAIN, 25));
		labelKorean.setBounds(12, 10, 136, 42);
		frame.getContentPane().add(labelKorean);

		textKorean = new JTextField();
		textKorean.setBounds(174, 10, 248, 42);
		frame.getContentPane().add(textKorean);
		textKorean.setColumns(10);

		labelEnglish = new JLabel("영어");
		labelEnglish.setFont(new Font("굴림", Font.PLAIN, 25));
		labelEnglish.setBounds(12, 63, 136, 42);
		frame.getContentPane().add(labelEnglish);

		textEnglish = new JTextField();
		textEnglish.setColumns(10);
		textEnglish.setBounds(174, 63, 248, 42);
		frame.getContentPane().add(textEnglish);

		labelMath = new JLabel("수학");
		labelMath.setFont(new Font("굴림", Font.PLAIN, 25));
		labelMath.setBounds(12, 118, 136, 42);
		frame.getContentPane().add(labelMath);

		textMath = new JTextField();
		textMath.setColumns(10);
		textMath.setBounds(174, 118, 248, 42);
		frame.getContentPane().add(textMath);

		btnInput = new JButton("점수 입력");
		btnInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputScore();
			}
		});
		btnInput.setFont(new Font("굴림", Font.PLAIN, 20));
		btnInput.setBounds(12, 183, 136, 42);
		frame.getContentPane().add(btnInput);

		btnDelete = new JButton("행 삭제");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteRowFromTable();
			}
		});
		btnDelete.setFont(new Font("굴림", Font.PLAIN, 20));
		btnDelete.setBounds(160, 183, 136, 42);
		frame.getContentPane().add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 235, 410, 94);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		// 테이블에 테이블 모델을 설정
		tableModel = new DefaultTableModel(null, COLUMN_NAMES);
		
		// 테이블 모델에서 변화가 생겼을 때(예: 셀의 내용이 변경) 이벤트를 처리하기 위한 이벤트 핸들러를 테이블 모델에 등록.
		
		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
			//	System.out.println("type: " + e.getType()); // 테이블 모델 변화의 이벤트 타입(추가, 삭제, 수정)
				//System.out.println("first row: " + e.getFirstRow()); // 이벤트가 발생한 행 인덱스
				// System.out.println("column: " + e.getColumn());// 이벤트가 발생한 열 인덱스
				handleTableChange(e);
			}
		});
		
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
	}

	private void handleTableChange(TableModelEvent e) {
		// 이벤트 모델 변화 이벤트의 타입을 먼저 찾음
		int eventType = e.getType();
		if (eventType != TableModelEvent.UPDATE) {
			// UPDATE 이벤트만 처리하고, 그 이외의 다른 이벤드(INSERT, DELETE)들은 처리하지 않음.
			return;
		}
		
		
		int row = e.getFirstRow(); // (업데이트)이벤트가 발생한 행 인덱스
		int col = e.getColumn(); // 이벤트가 발생한 열 인덱스
		if (col >= 0 && col <= 2) { // 국어, 영어, 수학 점수 변경이 일어났을 때
			// 총점과 평균을 다시 계산하기 위해서 국어, 영어, 수학 점수를 테이블에서 읽음.
			int kor = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, 0)));
			int eng = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, 1)));
			int math = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, 2)));
			
			Score s = new Score(kor, eng, math);
			
			// 변경된 점수로 계산된 총점과 평균을 테이블 모델에 반영
			tableModel.setValueAt(s.total(), row, 3);
			tableModel.setValueAt(s.mena(), row, 4);
			
			String msg = row + "번 인덱스 행의 정보가 수정됐습니다.";
			JOptionPane.showMessageDialog(frame, msg);
		}
		
	}

	private void deleteRowFromTable() {
		int index = table.getSelectedRow();
		if (index == -1) {
			JOptionPane.showMessageDialog(frame, "선택해 주세요.");
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(frame, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			tableModel.removeRow(index);
		}
		
	}

	private void inputScore() {
		// 국어, 영어, 수학 점수를 읽음(문자열 -> 정수 변환).	
		int korean = 0;
		int english = 0;
		int math = 0;
		
		try {
			korean = Integer.parseInt(textKorean.getText());
			english = Integer.parseInt(textEnglish.getText());
			math = Integer.parseInt(textMath.getText());
		} catch (NumberFormatException e) {
			String message = e.getMessage() + "\n숫자가 아닙니다.";
			JOptionPane.showMessageDialog(frame, message, "입력오류", JOptionPane.ERROR_MESSAGE);
			return; // 메서드 종료
		} finally {
			// 모든 텍스트 필드의 값들을 지움
			clearAllTextFields();
		}
		// Score 객체 생성 -> 총점, 평균 기능 사용 가능
		Score score = new Score(korean, english, math);

		// 테이블 모델에 행(row)을 추가
		Object[] rowData = {
				score.getEnglish(),
				score.getKorean(),
				score.getMath(),
				score.total(),
				score.mena()
		};
		tableModel.addRow(rowData);

		
		
	}

	private void clearAllTextFields() {
		textKorean.setText("");
		textEnglish.setText("");
		textMath.setText("");
		
	}
}
