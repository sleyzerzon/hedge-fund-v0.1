/*
 * Copyright 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.onenow.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;

/**
 * A collection of utilities for the Amazon Kinesis sample application.
 */
public class SampleUtils {


    /**
     * Creates a Region object corresponding to the AWS Region. If an invalid region is passed in
     * then the JVM is terminated with an exit code of 1.
     * 
     * @param regionStr the common name of the region for e.g. 'us-east-1'.
     * @return A Region object corresponding to regionStr.
     */
    public static Region parseRegion(String regionStr) {
        Region region = RegionUtils.getRegion(regionStr);

        if (region == null) {
            System.err.println(regionStr + " is not a valid AWS region.");
            System.exit(1);
        }
        return region;
    }

}
