package com.foxploit.ignio.deviceanalysisservice.service.impl;

import com.foxploit.ignio.deviceanalysisservice.service.AlertTypes;

public class AlertTypeImpl implements AlertTypes {

    public static String alertMessageResolve(int alertType) {
        switch (alertType) {
            case 1:
                return INFO_MESSAGE;
            case 2:
                return WARNING_MESSAGE;
            case 3:
                return DANGER_MESSAGE;
            default:
                return null;
        }
    }

}
