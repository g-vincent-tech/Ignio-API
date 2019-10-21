package com.foxploit.ignio.deviceanalysisservice.service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

/**
 * Service to find 'Slope'(m) and 'Y-Intercept'(c) for a straight line given, x and y values
 *
 * @author luk3Sky
 */
public class RegressionAnalysisService {

    // function to calculate m and c that best fit points represented by x[] and y[]
    static void approximateRegression(int[] x, int[] y) {
        int n = x.length;
        double m, c, sum_x = 0, sum_y = 0, sum_xy = 0, sum_x2 = 0;
        for (int i = 0; i < n; i++) {
            sum_x += x[i];
            sum_y += y[i];
            sum_xy += x[i] * y[i];
            sum_x2 += pow(x[i], 2);
        }

        m = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - pow(sum_x, 2));
        c = (sum_y - m * sum_x) / n;

        System.out.println("m = " + m);
        System.out.println("c = " + c);
    }

    static public float approximateRegression(List<Float> data_set) {
        int n = data_set.size();
        System.out.println(data_set.size());
        float m, c, sum_x = 0, sum_y = 0, sum_xy = 0, sum_x2 = 0;
        for (int i = 0; i < n; i++) {
            sum_x += (i + 1);
            sum_y += data_set.get(i);
            sum_xy += (i + 1) * data_set.get(i);
            sum_x2 += pow((i + 1), 2);
        }

        m = (float) ((n * sum_xy - sum_x * sum_y) / (n * sum_x2 - pow(sum_x, 2)));
        c = (sum_y - m * sum_x) / n;

        System.out.println("m = " + m);
        System.out.println("c = " + c);
        return m;
    }

    public static void main(String[] args) {
        int[] x = {1, 2, 3, 4, 5};
        int[] y = {14, 27, 40, 55, 68};
        approximateRegression(x, y);

        List<Float> y_set = new ArrayList<>();

        for (int y_i : y) {
            y_set.add((float) y_i);
        }

        approximateRegression(y_set);
    }

}
