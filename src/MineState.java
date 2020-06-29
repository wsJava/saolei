/**
 * 标志类型
 *
 * @author lvjp
 * @date 2020/6/29
 */
public enum MineState {
    /**
     * 未扫
     */
    unsweep("unsweep"),

    /**
     * 标记地雷
     */
    flag("flag"),

    /**
     * 已扫
     */
    sweep("sweep");

    private String value;

    MineState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
