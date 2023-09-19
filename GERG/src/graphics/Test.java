package graphics;

import java.util.Arrays;

public class Test {

    static int[][] map = {
            {0,2,0,0,3},
            {0,1,1,0,0},
            {0,1,0,2,3},
            /*
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3},
            {0, 1, 0, 0, 2, 0, 3, 3, 3, 0, 1, 1, 1, 0, 0, 0},
            {0, 1, 1, 0, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2, 0, 0},
            {0, 0, 1, 0, 3, 0, 2, 2, 0, 0, 1, 1, 1, 1, 2, 0},
            {2, 0, 1, 0, 3, 0, 2, 2, 2, 0, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 0, 3, 2, 2, 2, 0, 0, 0, 0, 1, 1, 1, 1},
             */
    };

    public static void main(String[] args) {
        /* Generador de Vertices */
        int rows = map.length + 1, cols = map[0].length + 1;
        float[] vertices = new float[rows*cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int n = i * cols + j;
                vertices[n] = n;
            }
        }

        /* Generador de Indices */
        rows -= 1; cols -= 1;
        int[] dif = {0,1,cols+1, cols+2};
        int[] indices = new int[rows*cols*4];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int n = i * cols + j;
                for (int k = 0; k < 4; k++) {
                    int m = i * cols * 4 + j * 4 + k;
                    indices[m] = n + i + dif[k];
                }
            }
        }
    }
}
