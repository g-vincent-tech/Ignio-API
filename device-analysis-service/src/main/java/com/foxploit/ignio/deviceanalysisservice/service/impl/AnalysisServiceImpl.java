package com.foxploit.ignio.deviceanalysisservice.service.impl;

import com.foxploit.ignio.deviceanalysisservice.service.AnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.pow;

/**
 * Service Implementation for Analysis based on linear regression
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    /**
     * Approximate linear regression for a best fit line for the given data set
     *
     * @param data_set
     * @return slope m
     */
    @Override
    public float approximateRegression(List<Float> data_set) {
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

}
