package com.ivp.xch.result;


/**
 * 返回Json 类型的数据
 * 
 * @author hcx
 *         2017-0712
 *
 * @param <T>
 */
public class JsonResult<T> extends Result {


    private T extraData;  // 附加数据
    private T data;   // 主数据
    private int total;


    JsonResult(boolean success, T data, int total, String msg) {
        this.success = success;
        this.data = data;
        this.total = total;
        this.message = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getExtraData() {
        return extraData;
    }

    public void setExtraData(T extraData) {
        this.extraData = extraData;
    }

    public static <T> JsonResult<T> getSuccessResult(T data) {
        return new JsonResult<T>(true, data, 0, null);
    }

    public static <T> JsonResult<T> getSuccessResult(T data, String msg) {
        return new JsonResult<T>(true, data, 0, msg);
    }

    public static <T> JsonResult<T> getSuccessResultMsg(String msg) {
        return new JsonResult<T>(true, null, 0, msg);
    }

    public static <T> JsonResult<T> getFailResultMsg(String msg) {
        return new JsonResult<T>(false, null, 0, msg);
    }

    /**
     * hcx
     * 
     * @param data 此次返回的数据
     * @param total 符合查询的数组总和
     * @return
     */
    public static <T> JsonResult<T> getSuccessResult(T data, int total) {
        return new JsonResult<T>(true, data, total, null);
    }

    /**
     * 
     * hcx
     * 
     * @param data 此次返回的数据
     * @param total 符合查询的数组总和
     * @param msg 此次返回的信息
     * @return
     */
    public static <T> JsonResult<T> getSuccessResult(T data, int total, String msg) {
        return new JsonResult<T>(true, data, total, msg);
    }

    /**
     * 有两个 Data 需要返回
     * 
     * @param data
     * @param extraData
     * @param success
     * @param message
     * @param total
     * @return
     */
    public static <T> JsonResult<T> getExtraResult(T data, T extraData, boolean success, String message, Long total) {
        JsonResult<T> re = new JsonResult<T>(success, data, 0, message);
        re.setExtraData(extraData);
        return re;
    }
}
