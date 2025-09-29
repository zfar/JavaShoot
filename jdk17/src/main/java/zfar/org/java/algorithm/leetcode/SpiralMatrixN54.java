package zfar.org.java.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrixN54 {
    private static int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private boolean getNextStep(int[][] flag, int[] pos) {
        boolean ret = false;
        int yBorder = flag.length - 1;
        int xBorder = flag[0].length - 1;

        int[] tmp = {0, 0};
        int i = pos[2];
        do {
            tmp[0] = pos[0] + directions[i][0];
            tmp[1] = pos[1] + directions[i][1];
            if (0 > tmp[0] || tmp[0] > xBorder || 0 > tmp[1] || tmp[1] > yBorder) {
                i = (i + 1) % directions.length;
                continue;
            }
            if (-1 == flag[tmp[1]][tmp[0]]) {
                i = (i + 1) % directions.length;
                continue;
            }
            pos[0] = tmp[0];
            pos[1] = tmp[1];
            ret = true;
            pos[2] = i;
            break;
        } while (i != pos[2]);

        return ret;
    }

    void process(int[] pos, int[][] flags, int[][] matrix, List<Integer> ret) {
        int x = pos[0];
        int y = pos[1];
        ret.add(matrix[y][x]);
        flags[y][x] = -1;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        if (null == matrix || 0 == matrix.length) {
            return new ArrayList<>();
        }

        List<Integer> ret = new ArrayList<>();
        int[][] flags = new int[matrix.length][matrix[0].length];
        int[] pos = {0, 0, 0};
        do {
            process(pos, flags, matrix, ret);
        } while (getNextStep(flags, pos));

        return ret;
    }

    public static void main(String[] args) {
        SpiralMatrixN54 s = new SpiralMatrixN54();
        int[][] a = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16},{17,18,19,20},{21,22,23,24}};
        System.out.println(s.spiralOrder(a));
    }
}
