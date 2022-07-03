package top.tonydon.constant;

/**
 * 实体常量
 */
public class EntityConstants {
    /*************************************************
     *
     * Article 文章
     *
     *************************************************/
    // 文章草稿
    public static final String ARTICLE_STATUS_DRAFT = "0";
    // 文章已发布
    public static final String ARTICLE_STATUS_PUBLISH = "1";


    /*************************************************
     *
     * FriendLink 友链
     *
     *************************************************/
    // 友链审核通过
    public static final String LINK_STATUS_NORMAL = "1";
    // 友链审核未通过
    public static final String LINK_STATUS_NOT_PASS = "0";
    // 友链未审核
    public static final String LINK_STATUS_UNREVIEWED = "2";


    /*************************************************
     *
     * Category 分类
     *
     *************************************************/
    // 分类状态正常
    public static final String CATEGORY_STATUS_NORMAL = "0";
    // 未分类的 id
    public static final long NOT_CLASSIFIED_ID = 1L;
    // 未分类的名称
    public static final String NOT_CLASSIFIED_NAME = "未分类";


    /**
     * 用户状态正常
     */
    public static final String USER_STATUS_NORMAL = "0";

    public static final String DEL_DELETED = "1";
    public static final String DEL_NORMAL = "0";
}
