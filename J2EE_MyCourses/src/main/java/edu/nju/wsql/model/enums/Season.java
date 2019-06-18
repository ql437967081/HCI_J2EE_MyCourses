package edu.nju.wsql.model.enums;

import java.util.HashMap;

public enum Season {
    SPRING, SUMMER, FALL, WINTER;

    private static final Season[] MONTH_TO_SEASON = {
            SPRING, SPRING, SPRING,
            SUMMER, SUMMER, SUMMER,
            FALL, FALL, FALL,
            WINTER, WINTER, WINTER
    };

    public static Season getSeason(int monthValue) {
        return MONTH_TO_SEASON[monthValue - 1];
    }

    private static final String[] SEASON_TO_CHINESE  = {"春季", "夏季", "秋季", "冬季"};

    public String toChinese() {
        return SEASON_TO_CHINESE[ordinal()];
    }

    private static final HashMap<String, Season> CHINESE_TO_SEASON;

    static{
        CHINESE_TO_SEASON = new HashMap<>();
        CHINESE_TO_SEASON.put("春季", SPRING);
        CHINESE_TO_SEASON.put("夏季", SUMMER);
        CHINESE_TO_SEASON.put("秋季", FALL);
        CHINESE_TO_SEASON.put("冬季", WINTER);
    }

    public static Season chineseToSeason(String ch) {
        return CHINESE_TO_SEASON.get(ch);
    }
}
