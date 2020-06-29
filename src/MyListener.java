import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lvjp
 * @date 2020/6/29
 */
public class MyListener extends WindowAdapter {


    File f = new File("游戏存档.txt");

    public void windowClosing(WindowEvent e) {
        if (GameBoard.safeCount < GameBoard.line * GameBoard.column - GameBoard.flagCount) {
            int dialog = JOptionPane.showConfirmDialog(e.getWindow(),
                    "当前游戏正在进行，是否保存游戏进度", "退出游戏", JOptionPane.YES_NO_CANCEL_OPTION);
            if (dialog == JOptionPane.YES_OPTION) {
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    ObjectOutputStream out = new ObjectOutputStream(fo);
                    List<MineButton> buttonList = new ArrayList<>();
                    for (int i = 0; i < GameBoard.line; i++) {
                        for (int j = 0; j < GameBoard.column; j++) {
                            MineButton lei = new MineButton();
                            lei.setX(i);
                            lei.setY(j);
                            lei.setMine(GameBoard.button[i][j].getActionCommand());
                            lei.setFlag(GameBoard.button[i][j].getName());
                            lei.setAmount(Integer.valueOf(GameBoard.button[i][j].getText()));
                            buttonList.add(lei);
                        }
                    }

                    out.writeObject(buttonList);
                    out.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                System.exit(0);
            } else if (dialog == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else if (dialog == JOptionPane.CANCEL_OPTION) {
            }
        } else System.exit(0);
    }

    public void windowActivated(WindowEvent e) {
        if (f.exists()) {
            int dialog1 = JOptionPane.showConfirmDialog(e.getWindow(),
                    "是否继续上次未完成的游戏", "找到游戏存档", JOptionPane.YES_NO_OPTION);
            if (dialog1 == JOptionPane.YES_OPTION) {
                try {
                    FileInputStream fi = new FileInputStream(f);
                    ObjectInputStream oi = new ObjectInputStream(fi);
                    List<MineButton> buttonList = (ArrayList<MineButton>) oi.readObject();
                    fi.close();
                    oi.close();

                    MineButton last = buttonList.get(buttonList.size() - 1);
                    int x = last.getX();
                    int y = last.getY();
                    MineSweeper.gameBoard.dispose();

                    int mineCount = 0;
                    for (MineButton mb : buttonList) {
                        if (mb.getIsMine().equals(MineType.MINE.getValue())) {
                            mineCount++;
                        }
                    }

                    MineSweeper.gameBoard = new GameBoard(x + 1, y + 1, mineCount);
                    for (int i = 0; i < GameBoard.mineCount; i++) {
                        GameBoard.button[GameBoard.a[i]][GameBoard.b[i]].setActionCommand(MineType.SAFE.getValue());
                        GameBoard.button[GameBoard.a[i]][GameBoard.b[i]].setBackground(new Color(193, 220, 240));
                    }
                    for (MineButton lei1 : buttonList) {
                        if ((lei1.getIsMine()).equals(MineType.MINE.getValue())) {
                            GameBoard.button[lei1.getX()][lei1.getY()].setActionCommand(MineType.MINE.getValue());
                        }
                        if ((lei1.getFlag()).equals(MineState.flag.getValue())) {

                            GameBoard.button[lei1.getX()][lei1.getY()].setName(MineState.flag.getValue());
                            GameBoard.button[lei1.getX()][lei1.getY()].setBackground(Color.RED);
                            GameBoard.flagCount--;
                            GameBoard.text2.setText("" + GameBoard.flagCount);
                        } else if (lei1.getFlag().equals(MineState.sweep.getValue())) {
                            GameBoard.button[lei1.getX()][lei1.getY()].setName(MineState.sweep.getValue());
                            GameBoard.button[lei1.getX()][lei1.getY()].setBackground(new Color(225, 225, 225));
                            GameBoard.button[lei1.getX()][lei1.getY()].setEnabled(false);
                            GameBoard.button[lei1.getX()][lei1.getY()].setText(lei1.getAmount().toString());
                            GameBoard.safeCount--;
                        }
                    }
                } catch (Exception ee) {
                    ee.printStackTrace();
                }

                f.delete();
            }
            if (dialog1 == JOptionPane.NO_OPTION) {
                f.delete();
            }
        }
    }
}