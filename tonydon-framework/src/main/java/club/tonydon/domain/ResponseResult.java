package club.tonydon.domain;

import club.tonydon.enums.HttpCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public ResponseResult() {}

    public ResponseResult(HttpCodeEnum status) {
        this.code = status.getCode();
        this.msg = status.getMsg();
    }

    public ResponseResult(HttpCodeEnum status, T data) {
        this.code = status.getCode();
        this.msg = status.getMsg();
        this.data = data;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回错误信息，状态码 500
     * @param <T> 泛型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> error() {
        return new ResponseResult<>(HttpCodeEnum.SYSTEM_ERROR);
    }

    public static <T> ResponseResult<T> error(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> error(HttpCodeEnum status) {
        return result(status);
    }


    // 成功结果
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(HttpCodeEnum.SUCCESS);
    }

    public static <T> ResponseResult<T> success(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(HttpCodeEnum.SUCCESS, data);
    }

    /**
     * 根据 HttpCodeEnum 返回信息
     *
     * @param status 状态码
     * @param <T>    泛型
     * @return 响应数据
     */
    public static <T> ResponseResult<T> result(HttpCodeEnum status) {
        return new ResponseResult<>(status);
    }
}