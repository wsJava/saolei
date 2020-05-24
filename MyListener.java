package saolei;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class MyListener extends WindowAdapter{
	File f = new File("游戏存档.txt");
	String flag1="flag1";
	String flag2="flag2";
	public void windowClosing(WindowEvent e) {
		if(GameBoard.n<GameBoard.mX*GameBoard.mY-GameBoard.m1) {
			int dialog = JOptionPane.showConfirmDialog(e.getWindow(), 
				"当前游戏正在进行，是否保存游戏进度","退出游戏", JOptionPane.YES_NO_CANCEL_OPTION);
			if(dialog==JOptionPane.YES_OPTION) {
				try {
					f.createNewFile();
					FileOutputStream fo = new FileOutputStream(f);
					ObjectOutputStream out = new ObjectOutputStream(fo);
					LinkedList <LeiButton> buttonList = new LinkedList <LeiButton>();
					for(int i=0;i<GameBoard.mX;i++){
						for(int j=0;j<GameBoard.mY;j++){
							LeiButton lei = new LeiButton();
							lei.setX(i);
							lei.setY(j);
							lei.setLei(GameBoard.button[i][j].getActionCommand());
							lei.setName(GameBoard.button[i][j].getName());
							lei.setAmount(GameBoard.button[i][j].getText());	
							buttonList.add(lei);		
						}
					}
//					Iterator <LeiButton> iter = buttonList.iterator();		//检验是否正确存档
//					while(iter.hasNext()) {	
//						LeiButton lei0 = iter.next();
//						System.out.println(lei0.getX());
//						System.out.println(lei0.getY());
//						System.out.println(lei0.getLei());
//					}
					
					out.writeObject(buttonList);
					out.close();
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
											
				System.exit(0);
			}
			else if(dialog==JOptionPane.NO_OPTION) {
				System.exit(0);
			}
			else if(dialog==JOptionPane.CANCEL_OPTION) {
			}
		}
		else System.exit(0);
	}
	public void windowActivated(WindowEvent e) {
		if(f.exists()) {
			int dialog1 = JOptionPane.showConfirmDialog(e.getWindow(), 
					"是否继续上次未完成的游戏","找到游戏存档", JOptionPane.YES_NO_OPTION);
			if(dialog1==JOptionPane.YES_OPTION) {
				try {
//					buttonList = new LinkedList <LeiButton>();
					FileInputStream fi = new FileInputStream(f);
					ObjectInputStream oi = new ObjectInputStream(fi);
					LinkedList <LeiButton> buttonList = (LinkedList <LeiButton>)oi.readObject();
					fi.close();
					oi.close();
//					Iterator <LeiButton> iter1 = buttonList.iterator();		//检验是否正确存档
//					while(iter1.hasNext()) {	
//						LeiButton lei0 = iter1.next();
//						System.out.println(lei0.getX());
//						System.out.println(lei0.getY());
//						System.out.println(lei0.getLei());
//					}
					LeiButton lei = new LeiButton();
					int x=0,y=0,m=0;
					lei = buttonList.getLast();
					x = lei.getX();
					y = lei.getY();		
					Saolei.saolei.dispose();
					Saolei.saolei = new GameBoard(x+1,y+1,GameBoard.m0);
					for(int i=0;i<GameBoard.m0;i++){
						GameBoard.button[GameBoard.a[i]][GameBoard.b[i]].setActionCommand("lei0");
						GameBoard.button[GameBoard.a[i]][GameBoard.b[i]].setBackground( new Color(193,220,240));
					}
					Iterator <LeiButton> iter = buttonList.iterator();
					while(iter.hasNext()) {
						LeiButton lei1 = iter.next();
						if((lei1.getLei()).equals("lei1")) {
							GameBoard.button[lei1.getX()][lei1.getY()].setActionCommand("lei1");
//							GameBoard.button[lei1.getX()][lei1.getY()].setBackground(Color.green);
							System.out.println(lei1.getX()+","+lei1.getY()+"有雷");
						}
//						System.out.println(lei1.getName());
						if((lei1.getName()).equals(flag1)) {

							GameBoard.button[lei1.getX()][lei1.getY()].setName("flag1");
							GameBoard.button[lei1.getX()][lei1.getY()].setBackground(Color.RED);
//							System.out.println(lei1.getX()+","+lei1.getY()+"有标记");
							GameBoard.m1--;
							GameBoard.text2.setText(""+GameBoard.m1);
						}
						else if(lei1.getName().equals(flag2)) {
							GameBoard.button[lei1.getX()][lei1.getY()].setName("flag2");
							GameBoard.button[lei1.getX()][lei1.getY()].setBackground( new Color(225,225,225));
							GameBoard.button[lei1.getX()][lei1.getY()].setEnabled(false);
							GameBoard.button[lei1.getX()][lei1.getY()].setText(lei1.getAmount());
//							System.out.println(lei1.getX()+","+lei1.getY()+"已扫");
							GameBoard.n--;
						}
					}
				}
				catch(Exception ee) {
					ee.printStackTrace();
				}
				
				f.delete();
			}
			if(dialog1==JOptionPane.NO_OPTION) {
				f.delete();
			}
		}
	}
}

