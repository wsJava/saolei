import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author lvjp
 * @date 2020/6/29
 */
public class GameBoard extends JFrame implements MouseListener, ActionListener {
    private GridLayout layout;
    private JPanel chessboard,bottom;
    private JMenuBar bar;
    private JMenu menu;
    private JMenuItem newGame,option;
    private JLabel amountLogo;
    static JLabel text2;
    static JButton button[][];
    private Box box;
    static int a[],b[];
    static int line, column, flagCount,m2, mineCount, safeCount;			//雷区行列数，标记数据，m2未被正确标志的地雷，地雷数量，安全区域数量

    GameBoard(int line,int column,int mineCount){
        GameBoard.line =line;
        GameBoard.column =column;
        GameBoard.mineCount =mineCount;
        m2= flagCount = GameBoard.mineCount;
        safeCount =line*column- flagCount;
        setLayout(null);
        bar = new JMenuBar();									//设置顶部菜单
        menu = new JMenu("菜单");
        newGame = new JMenuItem("新游戏");
        option = new JMenuItem("选项");
        menu.add(newGame);
        menu.add(option);
        bar.add(menu);
        setJMenuBar(bar);
        option. addActionListener(this);
        newGame.addActionListener(this);
        bottom = new JPanel();								//设置底部组件
        text2 = new JLabel(""+ flagCount);
        text2.setFont(new Font("宋体",1,24));
        bottom.setLayout(new FlowLayout());
        amountLogo = new JLabel("剩余地雷");
        amountLogo.setForeground(Color.red);
        amountLogo.setFont(new Font("宋体",1,18));
        box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(column*10));
        box.add(amountLogo);
        box.add(Box.createHorizontalStrut(6));
        box.add(text2);
        bottom.add(box);
        init();
        setTitle("扫雷");
        chessboard.setSize(30*column,30*line);
        chessboard.setLocation(52, 20);
        bottom.setSize(30*column,90);
        bottom.setLocation(60, 30+30*line);
        add(chessboard);
        add(bottom);
        addWindowListener(new MyListener());
        setBounds((1200-30*column)/2,(650-30*line)/2,30*column+120,30*line+140);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        validate();
    }

    void setRand(int x){					//获取一组随机数
        a[x]=(int)(Math.random()* line);
        b[x]=(int)(Math.random()* column);
        for(int j=x;j>0;j--){
            if(a[x]==a[j-1]&&b[x]==b[j-1]){
                setRand(x);
            }
        }
    }
    void getMount(int x,int y){						//获取周围地雷数量
        int bn=0;
        for(int i = x-1; (i<x+2)&&(i< line); i++){
            for(int j = y-1; (j<y+2)&&(j< column); j++){
                if(i<0||j<0) {
                    continue;
                }
                if(MineType.MINE.getValue().equals(button[i][j].getActionCommand())) {
                    bn++;
                }
            }
        }
        if(bn!=0) {
            button[x][y].setText(""+bn);
        } else{
            for(int i = x-1; (i<x+2)&&(i< line); i++){
                for(int j = y-1; (j<y+2)&&(j< column); j++){
                    if((i==x&&j==y)||i<0||j<0||(MineType.SAFE.getValue().equals(button[i][j].getName()))) {
                        continue;
                    }
                    cleanLei(i,j);
                    getMount(i,j);

                }
            }
        }
    }

    void init(){					//初始化扫雷游戏
        chessboard = new JPanel();							//设置扫雷区域布局
        layout = new GridLayout(line, column);
        chessboard.setLayout(layout);
        button = new JButton[line][column];
        for(int i = 0; i< line; i++){
            for(int j = 0; j< column; j++){
                button[i][j] = new JButton();
                button[i][j].addMouseListener(this);
                button[i][j].setMargin(new Insets(0,0,0,0));
                button[i][j].setFont(new Font("宋体",1,24));
                button[i][j].setActionCommand(MineType.SAFE.getValue());
                button[i][j].setName(MineState.unsweep.getValue());				//未扫flag0，标记flag1，已扫flag2
                button[i][j].setBackground(new Color(193,220,240));
                button[i][j].setOpaque(false);
                chessboard.add(button[i][j]);
            }
        }

        a = new int[mineCount];
        b = new int[mineCount];
        a[0]=(int)(Math.random()* line);
        b[0]=(int)(Math.random()* column);
        button[a[0]][b[0]].setActionCommand(MineType.MINE.getValue());
        for(int i = 1; i< mineCount; i++){
            setRand(i);
            button[a[i]][b[i]].setActionCommand(MineType.MINE.getValue());
        }
    }

    void cleanLei(int x,int y) {
        button[x][y].setName(MineType.SAFE.getValue());
        button[x][y].setBackground( new Color(225,225,225));
        button[x][y].setEnabled(false);
        safeCount--;
        if(safeCount ==0||m2==0)   End.victory();
    }

    private int getLeftSafeCount() {
        // todo
        return safeCount;
    }

    void reSetLei(JButton bu){
        for(int i = 0; i< mineCount; i++){
            button[a[i]][b[i]].setActionCommand(MineType.MINE.getValue());
            button[a[i]][b[i]].setBackground( new Color(193,220,240));
        }
        a[0]=(int)(Math.random()* line);
        b[0]=(int)(Math.random()* column);
        button[a[0]][b[0]].setActionCommand(MineType.SAFE.getValue());
        for(int i = 1; i< mineCount; i++){
            setRand(i);
            button[a[i]][b[i]].setActionCommand(MineType.SAFE.getValue());
        }
        if(bu.getActionCommand().equals(MineType.SAFE.getValue())){
            reSetLei(bu);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGame)  {
            MineSweeper.gameBoard.dispose();
            MineSweeper.gameBoard = new GameBoard(9,9,10);
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
            if((((JButton)e.getSource()).getName().equals(MineState.unsweep.getValue()))&& flagCount >0){
                ((JButton)e.getSource()).setName(MineState.flag.getValue());
                ((JButton)e.getSource()).setBackground(Color.red);
                flagCount--;						//地雷标记数量
                text2.setText(""+ flagCount);
                if(((JButton)e.getSource()).getActionCommand().equals(MineType.SAFE.getValue())) {
                    m2--;
                }
            }
            else if(((JButton)e.getSource()).getName().equals(MineState.flag.getValue())){
                ((JButton)e.getSource()).setName(MineState.unsweep.getValue());
                ((JButton)e.getSource()).setBackground( new Color(193,220,240));
                flagCount++;
                text2.setText(""+ flagCount);
                if((((JButton)e.getSource()).getActionCommand()).equals(MineType.SAFE.getValue())) {
                    m2++;
                }
            }
            if(safeCount ==0||m2==0)  End.victory();
        }
        if(e.getModifiers() == InputEvent.BUTTON1_MASK) {		//单击左键
            if(((JButton)e.getSource()).getName().equals(MineState.unsweep.getValue())){
                if(((JButton)e.getSource()).getActionCommand()==MineType.SAFE.getValue()) {
                    if(safeCount == line * column - mineCount){
                        reSetLei(((JButton)e.getSource()));
                        for(int i = 0; i< line; i++){
                            for(int j = 0; j< column; j++){
                                if(button[i][j] == (JButton)e.getSource()) {
                                    cleanLei(i,j);
                                    getMount(i,j);
                                }
                            }
                        }
                    }
                    else{
                        End.gameover();
                    }
                }
            }
            for(int i = 0; i< line; i++){
                for(int j = 0; j< column; j++){
                    if(button[i][j] == (JButton)e.getSource()) {
                        cleanLei(i,j);
                        getMount(i,j);
                    }
                }
            }
        }
    }
}