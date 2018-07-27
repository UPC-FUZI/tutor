package com.wf.tutor.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerBase {
    private static Logger logger = null;

    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(this.getClass());
        }
        return logger;
    }
}
