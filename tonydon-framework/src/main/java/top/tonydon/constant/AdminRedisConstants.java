package top.tonydon.constant;

public class AdminRedisConstants {

    public static final String LOGIN_PREFIX = "admin:login:";
    // 登陆过期时间，三十分钟
    public static final long LOGIN_TTL = 30L;


    // 分类缓存
    public static final String CACHE_CATEGORY_KEY = "admin:category:";
    public static final long CACHE_CATEGORY_TTL = 60L;

    // 用户缓存
    public static final String CACHE_USER_KEY = "admin:user:";
    public static final long CACHE_USER_TTL = 60L;

    // 文章缓存
    public static final String CACHE_ARTICLE_KEY = "admin:article:";
    public static final long CACHE_ARTICLE_TTL = 60L;

    // 评论缓存
    public static final String CACHE_COMMENT_KEY = "admin:comment:";
    public static final long CACHE_COMMENT_TTL = 60L;

}
