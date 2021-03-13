package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here

        int height, width;
        double doubleHeight = (Math.sqrt(1 + 8 * inputNumbers.size()) - 1) / 2;

        if (doubleHeight % 1 != 0 || inputNumbers.contains(null)) throw new CannotBuildPyramidException();
        else {
            height = (int) doubleHeight;
            width = height * 2 - 1;
        }

        int[][] result = new int[height][width];
        Collections.sort(inputNumbers);
        Queue<Integer> queue = new LinkedList<>(inputNumbers);

        for (int i = 0; i < height; i++) {
            int j = height - i - 1;
            while ((j >= height - i - 1) && (j <= width - (height - i))) {
                result[i][j] = queue.poll();
                j+=2;
            }
        }
        return result;
    }
}
