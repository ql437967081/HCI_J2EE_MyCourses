package edu.nju.wsql.service.utils;

import edu.nju.wsql.model.enums.Season;

import java.util.*;

public class SortUtil {
    public static List<String> getSortedTerms(Map<Integer, List<Season>> allTerms) {
        for (List<Season> list: allTerms.values())
            list.sort(Comparator.comparingInt(Enum::ordinal));
        List<Integer> years = new ArrayList<>(allTerms.keySet());
        Collections.sort(years);
        List<String> terms = new ArrayList<>();
        for (int i = years.size() - 1; i >= 0; i--) {
            int year = years.get(i);
            List<Season> list = allTerms.get(year);
            for (int j = list.size() - 1; j >= 0; j--)
                terms.add(year + "年" + list.get(j).toChinese() + "学期");
        }
        return terms;
    }
}
