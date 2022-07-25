package top.tonydon.constant;

public class BlogRedisConstants {

    // pexels 缓存
    public static final String PEXELS_IMAGE_PREFIX = "blog:pexels:";
    public static final long PEXELS_IMAGE_TTL = 60L;

    // 文章缓存
    public static final String CACHE_ARTICLE_KEY = "blog:article:";
    public static final long CACHE_ARTICLE_TTL = 60L;

    // 热门文章
    public static final String CACHE_HOT_ARTICLE_KEY = "blog:article:hot";
    public static final long CACHE_HOT_ARTICLE_TTL = 10L;

    // 文章评论数量缓存
    public static final String CACHE_ARTICLE_COMMENT_COUNT_KEY = "blog:article:comment_count:";
    public static final long CACHE_ARTICLE_COMMENT_COUNT_TTL = 24 * 60L;

    // 分类缓存
    public static final String CACHE_CATEGORY_KEY = "blog:category:";
    public static final long CACHE_CATEGORY_TTL = 24 * 60L;

    // 分类列表缓存
    public static final String CACHE_CATEGORY_LIST_KEY = "blog:category:list";
    public static final long CACHE_CATEGORY_LIST_TTL = 24 * 60L;

    // 友链缓存
    public static final String CACHE_FRIEND_LINK_KEY = "blog:friend_link";
    public static final long CACHE_FRIEND_LINK_TTL = 24 * 60L;

    // 文章访问量缓存
    public static final String CACHE_ARTICLE_VIEW_COUNT_KEY = "blog:article:view_count:";

    // 评论缓存
    public static final String CACHE_COMMENT_KEY = "blog:comment:";
    public static final long CACHE_COMMENT_TTL = 24 * 60L;

}
