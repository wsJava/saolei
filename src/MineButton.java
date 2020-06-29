import java.io.Serializable;

/**
 * 扫雷中的地雷按钮类
 *
 * @author lvjp
 */
public class MineButton implements Serializable {

    private static final long serialVersionUID = -1809618686040394759L;

    /**
     * 地雷横坐标
     */
    private int x;

    /**
     * 地雷纵坐标
     */
    private int y;

    /**
     *是否是地雷
     */
    private String isMine;

    /**
     * 地雷的标志
     */
    private String flag;

    /**
     * 周围地雷数量
     */
    private Integer amount;

    /*
     * getter and setter
     */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getIsMine() {
        return isMine;
    }

    public void setMine(String mine) {
        isMine = mine;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
