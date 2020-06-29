import javax.swing.*;

/**
 * @author lvjp
 * @date 2020/6/29
 */
public class End {

    public static void victory() {
        JOptionPane.showMessageDialog(null, "恭喜你，游戏胜利",
                "游戏胜利", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void gameover() {
        JOptionPane.showMessageDialog(null, "很遗憾，游戏失败",
                "游戏失败", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
