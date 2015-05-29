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

package kinesis;

import java.util.concurrent.TimeUnit;

/**
 * A clock tells time.
 */
public interface Clock {
    /**
     * @return the current time of this clock.
     */
    public long getTime();

    /**
     * @return the units {@link #getTime()} responds with.
     */
    public TimeUnit getTimeUnit();
}
