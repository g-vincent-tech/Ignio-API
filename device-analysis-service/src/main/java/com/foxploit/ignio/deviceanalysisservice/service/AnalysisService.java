package com.foxploit.ignio.deviceanalysisservice.service;

/**
 * Service Interface for managing Analysis Service.
 */
public interface AnalysisService {

    /**
     * Approximate regression line for the given data (Y values)
     */
    void approximateRegression(int[] x, int[] y);

}
