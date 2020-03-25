package per.cz.util;


/**
 * 封装后端返回给LayUI的Json数据
 * @author EDZ
 *
 * @param <T>
 */
public class JsonUtil<T> {

    private int code;
    private long count;
    private String msg;
    private T data;


    /**
     * 结果非空构造方法(有数据构造方法)
     * @param code
     * @param count
     * @param data
     */
    public JsonUtil(int code, long count, T data) {
        super();
        this.code = code;
        this.count = count;
        this.data = data;
    }

    public JsonUtil(int code, T data) {
        this.code = code;
        this.data = data;
    }

    /**
     * 返回空值构造方法(无数据构造方法)
     * @param code
     * @param count
     * @param msg
     */
    public JsonUtil(int code, long count, String msg) {
        super();
        this.code = code;
        this.count = count;
        this.msg = msg;
    }


    /**
     * getter、setter方法
     * @return
     */
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public long getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }


    /**
     * 返回有数据信息
     * @param data
     * @return
     */
    public static <T> JsonUtil<T> createBySuccess( T data){
        return new JsonUtil<T>(ResponseLayCode.SUCCESS.getCode(),data);
    }

    /**
     * 返回空数据信息
     * @param count
     * @param msg
     * @return
     */
    public static <T> JsonUtil<T> createByNull(long count, String msg){
        return new JsonUtil<T>(ResponseLayCode.NULL.getCode(),count,msg);
    }
    /**
     * 返回空数据信息(count为0)
     * @param msg
     * @return
     */
    public static <T> JsonUtil<T> createByNull(String msg){
        return new JsonUtil<T>(ResponseLayCode.NULL.getCode(),ResponseLayCode.SUCCESS.getCode(),msg);
    }


}
