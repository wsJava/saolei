package saolei;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class OptionDialog extends JDialog implements ActionListener{
	JButton yes,no;
	ButtonGroup nandu;
	JLabel label1,label2,label3;
	JTextField text1,text2,text3;
	Box box1,box2,box3,boxV1,boxV2,boxH1,boxH2,box;
	JRadioButton button1,button2,button3,button4;
	StringBuffer s1,s2,s3;
	OptionDialog(){	
//		s1 = new StringBuffer();
//		s2 = new StringBuffer();
//		s3 = new StringBuffer();
		setModal(true);
		setLayout(new FlowLayout());
		nandu = new ButtonGroup();
		button1 = new JRadioButton("初级  10个雷  9 x 9 方格",true);
		button2 = new JRadioButton("中级  40个雷  16 x 16 方格");
		button3 = new JRadioButton("高级  99个雷  16 x 30 方格");
		button4 = new JRadioButton("自定义");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		nandu.add(button1);
		nandu.add(button2);
		nandu.add(button3);
		nandu.add(button4);
		boxV1 = Box.createVerticalBox();
		boxV1.add(button1);
		boxV1.add(button2);
		boxV1.add(button3);
		boxV1.add(button4);
		
		label1 = new JLabel("行数（9-20）：");
		label2 = new JLabel("列数（9-30）：");
		label3 = new JLabel("雷数 (10-540)：");
		text1 = new JTextField(4);
		text2 = new JTextField(4);
		text3 = new JTextField(4);
		text1.addActionListener(this);
		text2.addActionListener(this);
		text3.addActionListener(this);
		text1.setEnabled(false);
		text2.setEnabled(false);
		text3.setEnabled(false);
		text1.setBackground(this.getBackground());
		text2.setBackground(this.getBackground());
		text3.setBackground(this.getBackground());
		box1 = Box.createHorizontalBox();
		box1.add(label1);
		box1.add(Box.createHorizontalStrut(10));
		box1.add(text1);
		box2 = Box.createHorizontalBox();
		box2.add(label2);
		box2.add(Box.createHorizontalStrut(10));
		box2.add(text2);
		box3 = Box.createHorizontalBox();
		box3.add(label3);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(text3);
		
		boxV2 = Box.createVerticalBox();
		boxV2.add(box1);
		boxV2.add(Box.createVerticalStrut(5));
		boxV2.add(box2);
		boxV2.add(Box.createVerticalStrut(5));
		boxV2.add(box3);
		boxV2.add(Box.createHorizontalStrut(10));
		box = Box.createVerticalBox();
		add(boxV1);
		add(boxV2);
	
		boxH2 = Box.createHorizontalBox();
		yes = new JButton("确定");
		yes.addActionListener(this);
		no = new JButton("取消");
		no.addActionListener(this);
		boxH2.add(Box.createVerticalStrut(80));
		boxH2.add(yes);
		boxH2.add(Box.createHorizontalStrut(50));
		boxH2.add(no);
		add(boxH2);
		setTitle("选项");
		setBounds(450,250,250,300);
		setResizable(false);
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button4) {
			text1.setEnabled(true);
			text2.setEnabled(true);
			text3.setEnabled(true);
				
			s1 = new StringBuffer();
			s2 = new StringBuffer();
			s3 = new StringBuffer();
		}
		else if(e.getSource()==yes) {
			if(button1.isSelected()) {
				Saolei.saolei.dispose();
				Saolei.saolei = new GameBoard(9,9,10);
			}
			else if(button2.isSelected()) {
				Saolei.saolei.dispose();
				Saolei.saolei = new GameBoard(16,16,40);
			}
			else if(button3.isSelected()) {
				Saolei.saolei.dispose();
				Saolei.saolei = new GameBoard(16,30,99);
			}
			else if(button4.isSelected()) {
				int t1,t2,t3;
				
				if(((text1.getText()).matches("\\D+")||(text2.getText()).matches("\\D+")||(text3.getText()).matches("\\D+"))) {
					JOptionPane.showMessageDialog(null, "输入了非法字符，请重新输入",
							"输入错误",JOptionPane.WARNING_MESSAGE);
					assert false;
				}
				t1=Integer.parseInt(text1.getText());
				t2=Integer.parseInt(text2.getText());
				t3=Integer.parseInt(text3.getText());
				if(t1>24||t1<9||t2>30||t2<9||t3>600||t3<10) {
					JOptionPane.showMessageDialog(null, "数字超出范围，请重新输入",
							"输入错误",JOptionPane.WARNING_MESSAGE);
				}
				else {
					Saolei.saolei.dispose();
					Saolei.saolei = new GameBoard(t1,t2,t3);
				}
			}
			this.dispose();
		}
		else if(e.getSource()==no) {
			this.dispose();
		}
		else {
			text1.setEnabled(false);
			text2.setEnabled(false);
			text3.setEnabled(false);
			text1.setBackground(this.getBackground());
			text2.setBackground(this.getBackground());
			text3.setBackground(this.getBackground());
		}	
	}
} 
