package saolei;
import javax.swing.JOptionPane;

public class GameOver {
	public GameOver() {
		JOptionPane.showMessageDialog(null, "不好意思，您输了",
				"游戏失败",JOptionPane.WARNING_MESSAGE);
		System.exit(0);
	}
}
