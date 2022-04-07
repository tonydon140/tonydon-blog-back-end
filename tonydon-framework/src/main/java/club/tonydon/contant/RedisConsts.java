package club.tonydon.contant;

public class RedisConsts {
    /**
     * 登陆前缀
     */
    public static final String LOGIN_PREFIX = "admin:";

    /**
     * 必应每日一图前缀
     */
    public static final String BING_IMAGE_PREFIX = "bingImg:";


    /**
     * 登陆过期时间，三十分钟
     */
    public static final long LOGIN_TTL = 30 * 60L;

    /**
     * 每日一图过期时间，24小时
     */
    public static final long BING_IMAGE_TTL = 24 * 60 * 60L;
}
