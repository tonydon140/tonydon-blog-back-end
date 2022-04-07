package club.tonydon.enums;

public enum HttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),

    // 错误代码
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    NO_ID_ERROR(506,"id不存在"),
    ONLY_ONE_USER_ERROR(507,"只有一个用户"),

    // 文件错误
    FILE_EMPTY_ERROR(510, "空文件上传"),
    NO_FILENAME_ERROR(511,"文件名错误"),
    NOT_IMAGE_ERROR(511,"文件类型不是图片");

    final int code;
    final String msg;
    HttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
