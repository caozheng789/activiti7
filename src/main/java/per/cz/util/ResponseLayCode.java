package per.cz.util;

/**
 * Created by Administrator on 2020/3/25.
 */
public enum  ResponseLayCode {

    SUCCESS(0),
    NULL(1);

    private int code;

    private ResponseLayCode(int code) {
    this.code = code;
    }

    public int getCode() {
    return code;
    }

    public void setCode(int code) {
    this.code = code;
}
}
