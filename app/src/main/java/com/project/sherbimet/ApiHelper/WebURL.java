package com.project.sherbimet.ApiHelper;

public class WebURL {

    private static final String IP_ADDRESS = "192.168.0.105";

    private static final String KEY_MAIN_URL="http://"+IP_ADDRESS+"/sherbimet/";


    public static final String KEY_IMAGE_URL=KEY_MAIN_URL + "/sherbimet/images/";
    public static final String SERVICE_URL =KEY_MAIN_URL + "service_api.php";
    public static final String SUB_SERVICES_URL =KEY_MAIN_URL + "sub_services_api.php";
    public static final String PACKAGE_URL =KEY_MAIN_URL + "package_api.php";
    public static final String SIGN_UP_URL =KEY_MAIN_URL + "signup_api.php";
}
