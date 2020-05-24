package saolei;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GameBoard extends JFrame implements MouseListener,ActionListener {
	GridLayout grid;
	JPanel board,chessboard,bottom;
	JMenuBar bar;
	JMenu menu;
	JMenuItem newGame,option;
	JLabel timeLogo,text1,amountLogo;
	static JLabel text2;
	static JButton button[][];
//	ImageIcon flag;
	Box box;
	static int a[],b[];
	static int mX,mY,m1,m2,m0,n;			//mX、mY雷区行列数，m0地雷数，m1标记数量，m2未被正确标志的地雷，n非地雷

	GameBoard(int mX,int mY,int mlei){
		GameBoard.mX=mX;
		GameBoard.mY=mY;
		GameBoard.m0=mlei;
		m2=m1=m0;
		n=mX*mY-m1;
		setLayout(null);
		bar = new JMenuBar();									//设置顶部菜单
		menu = new JMenu("菜单");
		newGame = new JMenuItem("新游戏");
		option = new JMenuItem("选项");
//		help = new JMenuItem("帮助");
		menu.add(newGame);
		menu.add(option);
//		menu.add(help);
		bar.add(menu);
		setJMenuBar(bar);
		option. addActionListener(this);
		newGame.addActionListener(this);
		bottom = new JPanel();								//设置底部组件
//		text1 = new JLabel("0");
		text2 = new JLabel(""+m1);
//		text1.setFont(new Font("宋体",1,24));
		text2.setFont(new Font("宋体",1,24));
		bottom.setLayout(new FlowLayout());
		amountLogo = new JLabel("剩余地雷");
		amountLogo.setForeground(Color.red);
		amountLogo.setFont(new Font("宋体",1,18));
		box = Box.createHorizontalBox();
		box.add(Box.createHorizontalStrut(mY*10));
		box.add(amountLogo);
		box.add(Box.createHorizontalStrut(6));
		box.add(text2);
		bottom.add(box);
//		flag = new ImageIcon("img\\lei.jpg");	
		init();
		setTitle("扫雷");
		chessboard.setSize(30*mY,30*mX);
		chessboard.setLocation(52, 20);
		bottom.setSize(30*mY,90);
		bottom.setLocation(60, 30+30*mX);
		add(chessboard);
		add(bottom);
		addWindowListener(new MyListener());
		setBounds((1200-30*mY)/2,(650-30*mX)/2,30*mY+120,30*mX+140);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		validate();
	}
	
	void setRand(int x){					//获取一组随机数
		a[x]=(int)(Math.random()*mX);
		b[x]=(int)(Math.random()*mY);
		for(int j=x;j>0;j--){
			if(a[x]==a[j-1]&&b[x]==b[j-1]){
				setRand(x);
			}
		}
	}
	void getMount(int x,int y){						//获得周围数量
		int bn=0;
//		System.out.println("当前位置："+(x+1)+","+(y+1));
		for(int i=x-1;(i<x+2)&&(i<mX);i++){
			for(int j=y-1;(j<y+2)&&(j<mY);j++){
//				System.out.println("当前位置："+(x+1)+","+(y+1));
				if(i<0||j<0) {
					continue;
				}
				if("lei1"==button[i][j].getActionCommand()) {
					bn++;
//					System.out.println((x+1)+","+(y+1)+"附近有雷");
				}
			}
		}
		if(bn!=0) {
			button[x][y].setText(""+bn);
		}
		else{
//			System.out.println((x+1)+","+(y+1)+"附近没有雷");
			for(int i=x-1;(i<x+2)&&(i<mX);i++){
				for(int j=y-1;(j<y+2)&&(j<mY);j++){
					if((i==x&&j==y)||i<0||j<0||(button[i][j].getName()=="flag2")) {
						continue;
					}
					cleanLei(i,j);
//					System.out.println("getMount"+(i+1)+","+(j+1));
					getMount(i,j);
					
				}	
			}
		}
	}

	void init(){					//初始化扫雷游戏
		chessboard = new JPanel();							//设置扫雷区域布局
		grid = new GridLayout(mX,mY);
		chessboard.setLayout(grid);
		button = new JButton[mX][mY];
		for(int i=0;i<mX;i++){
			for(int j=0;j<mY;j++){
				button[i][j] = new JButton();
				button[i][j].addMouseListener(this);
				button[i][j].setMargin(new Insets(0,0,0,0));
				button[i][j].setFont(new Font("宋体",1,24));
//				button[i][j].setIcon(flag);
				button[i][j].setActionCommand("lei0");		//无雷lei0，有雷lei1
				button[i][j].setName("flag0");				//未扫flag0，标记flag1，已扫flag2
				button[i][j].setBackground( new Color(193,220,240));
				chessboard.add(button[i][j]);
			}
		}
		
		a = new int[m0];
		b = new int[m0];
		a[0]=(int)(Math.random()*mX);
		b[0]=(int)(Math.random()*mY);
		button[a[0]][b[0]].setActionCommand("lei1");
//		button[a[0]][b[0]].setBackground(Color.green);
		for( int i=1;i<m0;i++){
			setRand(i);
			button[a[i]][b[i]].setActionCommand("lei1");
//			System.out.println((a[i]+1)+","+(b[i]+1)+","+button[a[i]][b[i]].getActionCommand());
//			button[a[i]][b[i]].setBackground(Color.green);
		}
	}
	
	void cleanLei(int x,int y) {
		button[x][y].setName("flag2");
		button[x][y].setBackground( new Color(225,225,225));
		button[x][y].setEnabled(false);
//		System.out.println("cleanLei"+(x+1)+","+(y+1));
		n--;
//		System.out.println("剩余雷的数量："+n);
		if(n==0||m2==0)   new Victory();
	}
	
	void reSetLei(JButton bu){
		for(int i=0;i<m0;i++){
			button[a[i]][b[i]].setActionCommand("lei0");
			button[a[i]][b[i]].setBackground( new Color(193,220,240));
		}
		a[0]=(int)(Math.random()*mX);
		b[0]=(int)(Math.random()*mY);
		button[a[0]][b[0]].setActionCommand("lei1");
//		button[a[0]][b[0]].setBackground(Color.green);
		for( int i=1;i<m0;i++){
			setRand(i);
			button[a[i]][b[i]].setActionCommand("lei1");
//			button[a[i]][b[i]].setBackground(Color.green);
		}
		if(bu.getActionCommand().equals("lei1")){
			reSetLei(bu);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame)  {
			Saolei.saolei.dispose();
			Saolei.saolei = new GameBoard(9,9,10);
		}
		if(e.getSource() == option)  {
			new OptionDialog();
		}
	}
	public void mouseReleased(MouseEvent e) {	
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {	
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getModifiers() == InputEvent.BUTTON3_MASK) {		//单击右键
			if((((JButton)e.getSource()).getName().equals("flag0"))&&m1>0){
				((JButton)e.getSource()).setName("flag1");
				((JButton)e.getSource()).setBackground(Color.red);
				m1--;						//地雷标记数量
				text2.setText(""+m1);			
				if(((JButton)e.getSource()).getActionCommand().equals("lei1")) {
					m2--;
				}
			}
			else if(((JButton)e.getSource()).getName().equals("flag1")){
				((JButton)e.getSource()).setName("flag0");
				((JButton)e.getSource()).setBackground( new Color(193,220,240));
				m1++;
				text2.setText(""+m1);
				if((((JButton)e.getSource()).getActionCommand()).equals("lei1")) {
					m2++;
				}
			}
			if(n==0||m2==0)   new Victory();
		}
		if(e.getModifiers() == InputEvent.BUTTON1_MASK) {		//单击左键				
			if(((JButton)e.getSource()).getName().equals("flag0")){
				if(((JButton)e.getSource()).getActionCommand()=="lei1") {
					if(n==mX*mY-m0){
						reSetLei(((JButton)e.getSource()));
						for(int i=0;i<mX;i++){
							for(int j=0;j<mY;j++){
								if(button[i][j] == (JButton)e.getSource()) {
									cleanLei(i,j);
									getMount(i,j);
								}
							}
						}
					}
					else{
						new GameOver();
					}
				}
			}
			for(int i=0;i<mX;i++){
				for(int j=0;j<mY;j++){
					if(button[i][j] == (JButton)e.getSource()) {							
						cleanLei(i,j);
						getMount(i,j);	
					}
				}
			}
		}
	}
}


