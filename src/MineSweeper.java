/**
 * 扫雷游戏启动类
 *
 * @author lvjp
 * @date 2020/6/29
 */
public class MineSweeper {

    protected static GameBoard gameBoard;

    public static void main(String[] args) {
        gameBoard = new GameBoard(9, 9, 10);
    }
}
