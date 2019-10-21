package com.foxploit.ignio.deviceanalysisservice.service.impl;

import com.foxploit.ignio.deviceanalysisservice.service.AnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.foxploit.ignio.deviceanalysisservice.service.AlertTypes.*;
import static java.lang.Math.pow;

/**
 * Service Implementation for Analysis based on linear regression
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    /**
     * Approximate linear regression for a best fit line for the given data set
     *
     * @param data_set y values of the data set
     * @return slope m
     */
    @Override
    public float approximateRegression(List<Float> data_set) {
        int n = data_set.size();
        float m, sum_x = 0, sum_y = 0, sum_xy = 0, sum_x2 = 0;
        for (int i = 0; i < n; i++) {
            sum_x += (i + 1);
            sum_y += data_set.get(i);
            sum_xy += (i + 1) * data_set.get(i);
            sum_x2 += pow((i + 1), 2);
        }

        m = (float) ((n * sum_xy - sum_x * sum_y) / (n * sum_x2 - pow(sum_x, 2)));
        return m;
    }

    @Override
    public int resolveSlopeLevel(float slope) {
        if (slope <= 0.25 && slope >= 0.10) {
            return INFO_LEVEL;
        } else if (slope <= 0.75 && slope > 0.25) {
            return WARNING_LEVEL;
        } else if (slope > 0.75) {
            return DANGER_LEVEL;
        } else {
            return 0;
        }
    }

    @Override
    public int resolveAlertLevel(int weight) {
        if (weight >= 2 && weight <= 4) {
            return INFO_LEVEL;
        } else if (weight > 4 && weight <= 8) {
            return WARNING_LEVEL;
        } else if (weight > 8) {
            return DANGER_LEVEL;
        } else {
            return 0;
        }
    }
}
