package zfar.org.java.algorithm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @fileName: exam level2
 * @version: V1.0
 * @Description: 字符串中数字和字母提取和排序
 * 需求：字符串中数字和字母提取和排序，出现次数的优先级对应字符和数字的排序（不重复显示）
 * 时长：30分钟
 * 输入：字符串（包含：数字、大小写字母、特殊字符），示例：adbc5A31#E6Eaa3，a出现3次排在前面，3和E都出现２次，数字靠前,３排在E前面。
 * 输出：
 * 数字倒叙排序，字母正序（小写在前），数字在前。第1规则：出现次数多的在前（如果次数相同，则数字在前）。
 * 排序结果示例：a3E651bcdA
 * 1、数字提出来，倒叙排序 65331
 * 2、字母正序（小写在前）aaabcdAEE
 * 3、排重复，出现次数多的在前（如果次数相同，则数字在前）
 * 4、移除特殊字符；##¥¥¥………
 * 结果：a3E651bcdA
 * @Author: CIC灼识咨询技术部
 * @Date: 2022/11/23 9:37
 * 本代码要注意的事项、备注事项等。
 */
public class StatChars {
    private static final char[] BASE = {0, '0', 'a', 'A'};

    public enum CharType {
        OTHER,
        NUMBER,
        LOWER,
        UPPER,

    }

    /**
     * classify the char
     *
     * @param a the char
     * @return CharType
     */
    public static CharType kindOf(char a) {
        if (a < '0') {
            return CharType.OTHER;
        }
        if (a <= '9') {
            return CharType.NUMBER;
        }
        if (a < 'A') {
            return CharType.OTHER;
        }
        if (a <= 'Z') {
            return CharType.UPPER;
        }
        if (a < 'a') {
            return CharType.OTHER;
        }
        if (a <= 'z') {
            return CharType.LOWER;
        }

        return CharType.OTHER;
    }

    public static class Item implements Comparable<Item> {
        char v;
        int cnt;
        CharType type;

        public Item(char v, int c, CharType t) {
            this.v = v;
            this.cnt = c;
            this.type = t;
        }

        public int compareTo(Item o) {
            int ret = o.cnt - this.cnt;
            if (0 != ret) {
                return ret;
            }

            ret = this.type.ordinal() - o.type.ordinal();
            if (0 != ret) {
                return ret;
            }

            if (CharType.NUMBER == this.type) {
                return o.v - this.v;
            }
            else {
                return this.v - o.v;
            }
        }
    }

    public static void stat(String input, int[][] stats) {
        int i;
        for (i = 0; i < input.length(); ++i) {
            char v = input.charAt(i);
            CharType t = kindOf(v);
            if (t == CharType.OTHER) {
                continue;
            }

            ++stats[t.ordinal()][v - BASE[t.ordinal()]];
        }
    }

    public static void process(CharType[] types, int[][]stats, List<Item> items, boolean detail) {
        if (0 == types.length) {
            return ;
        }

        BiConsumer<CharType, Integer> outputAndCollect = (t, i) -> {
            int tv = t.ordinal();
            char v = (char)(BASE[tv] + i);
            int c = stats[tv][i];

            if (c > 0 && null != items) {
                items.add(new Item(v, c, t));
            }

            if (!detail) return;
            while(c-- > 0) {
                System.out.print(v);
            }
        };

        int i, j;
        int[] typeStats;
        CharType t;
        for (i = 0; i < types.length; ++i) {
            t = types[i];
            typeStats = stats[t.ordinal()];
            if (CharType.NUMBER == t) {
                for (j = typeStats.length - 1; j > -1; --j) {
                    outputAndCollect.accept(t, j);
                }
            }
            else {
                for (j = 0; j < typeStats.length; ++j) {
                    outputAndCollect.accept(t, j);
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String input = "adbc5A31#E6Eaa3";
        int[][] stats = {null, new int[10], new int[26], new int[26]};
        List<Item> items = new ArrayList<>();

        // stat the occurrence
        stat(input, stats);

        // collect the numbers stats and output the numbers descend
        process(new CharType[]{CharType.NUMBER}, stats, items, true);
        // collect the chars stats and output the chars ascend, with the lowercases followed by the uppercases
        process(new CharType[]{CharType.LOWER, CharType.UPPER}, stats, items, true);

        // sort
        Collections.sort(items);

        // output the sorted and deduplicated chars
        items.forEach(it -> System.out.print(it.v));
        System.out.println();
    }

}
