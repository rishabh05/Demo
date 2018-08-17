//===============================================================================
// © 2015-2017 eWorkplace Apps.  ALL rights reserved.
// Main Author: Shrey Sharma
// Original Date: 29 April 2015
//===============================================================================
package com.demo.utils;

/**
 * call back handler for handling ajax responses
 */
public interface RequestCallback {

    /**
     * Called, When ajax response in successfully transformed into the desired object
     *
     * @param name   string call name returned from ajax response on success
     * @param object object returned from ajax response on success
     */
    void onSuccess(String name, Object object);

    /**
     * Called, When there happens any kind of error, exception or failure in getting ajax response from the server
     *
     * @param name   string call name returned from ajax response on failure
     * @param object returned from ajax response on failure
     */
    void onFailure(String name, Object object);

    /**
     * can be used to get progress
     *
     * @param msg           msg for progress
     * @param name          string call name
     * @param progressCount number of processes completed
     */
    void updateProgress(String name, String msg, int progressCount);
}
