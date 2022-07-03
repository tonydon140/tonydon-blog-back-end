package top.tonydon.enums;

public enum HttpCodeEnum {
    // 成功
    SUCCESS(200, "操作成功"),
    // 登录
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),

    /*************************************************
     *
     * 系统错误
     *
     *************************************************/
    SYSTEM_ERROR(500, "出现错误"),


    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505, "用户名或密码错误"),
    NO_ID_ERROR(506, "id不存在"),
    ONLY_ONE_USER_ERROR(507, "只有一个用户"),

    /*************************************************
     *
     * 文件错误
     *
     *************************************************/
    FILE_ERROR(510, "文件错误"),
    // 空文件上传
    FILE_EMPTY_ERROR(511, "空文件上传"),
    // 文件名错误
    NO_FILENAME_ERROR(512, "文件名错误"),
    // 文件类型部署图片
    NOT_IMAGE_ERROR(513, "文件类型不是图片"),


    /*************************************************
     *
     * 文章错误
     *
     *************************************************/
    ARTICLE_ERROR(520, "文章错误"),
    // 文章不存在
    ARTICLE_NOT_EXIST(521, "文章不存在"),


    /*************************************************
     *
     * 分类错误
     *
     *************************************************/
    CATEGORY_ERROR(530, "分类错误"),
    // 不能删除未分类
    CANNOT_REMOVE_NOT_CLASSIFIED(531, "不能删除未分类"),
    // 分类下存在文章
    CATEGORY_HAS_ARTICLE(532, "分类下存在文章");


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
