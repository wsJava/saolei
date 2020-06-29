/**
 * @author lvjp
 * @date 2020/6/29
 */
public enum MineType {
    MINE("mine"),
    SAFE("safe");
    private String value;

    MineType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
