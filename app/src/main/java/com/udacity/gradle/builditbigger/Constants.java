/*
 * Copyright 2018 Soojeong Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.udacity.gradle.builditbigger;

/**
 * Define specific constants.
 */
public class Constants {

    private Constants() {
        // Restrict instantiation
    }

    /** Constants for joke categories */
    public static final String CATEGORY_MATH = "math";
    public static final String CATEGORY_ANIMAL = "animal";
    public static final String CATEGORY_MARRIAGE = "marriage";
    public static final String CATEGORY_TECH = "tech";
    public static final String CATEGORY_FAMILY = "family";

    /** The Root URL */
    public static final String ROOT_URL = "http://192.168.0.19:8080/_ah/api/";
    /** 10.0.2.2 is localhost's IP address in Android emulator */
    public static final String ROOT_URL_EMULATOR = "http://10.0.2.2:8080/_ah/api/";

    /** The detail message for the InterruptedException */
    public static final String EXCEPTION_MESSAGE = "AsyncTask cancelled";

    /** Key for storing the scroll position in MainActivity */
    public static final String LAYOUT_MANAGER_STATE = "layout_manager_state";

}
