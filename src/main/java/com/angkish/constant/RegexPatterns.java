package com.angkish.constant;

/**
 * 正则表达式常量类
 * 用于系统用户相关字段的格式校验
 */
public abstract class RegexPatterns {

    private RegexPatterns() {
        // 禁止实例化
    }

    /**
     * 用户名正则：
     * 4-16 位，由字母、数字、下划线、连字符组成
     */
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{4,16}$";

    /**
     * 密码正则：
     * 8-18 位数字、字母、符号的任意两种组合
     * （密码复杂度可在后期加强）
     */
    public static final String PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{8,18}$";

    /**
     * 手机号正则（中国大陆）
     */
    public static final String PHONE_REGEX =
            "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    /**
     * 邮箱正则（预留）
     * 当前 t_user 表未使用，可扩展
     */
    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
}

