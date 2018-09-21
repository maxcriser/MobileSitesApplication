package com.example.maksim_zakharenka.broowerandroidapplication;

import java.util.ArrayList;
import java.util.List;

public final class BrandConstants {

    interface URL {

        String URL_HOST = "https://avroramarket.by";
        String URL_HOST_PROFILE = "https://avrora.market";
        String URL_HOST_COUNTRY = "https://avroramarket24.ru";
    }

    static final List<String> AVAILABLE_HOSTS = new ArrayList<String>() {{
        add(URL.URL_HOST);
        add(URL.URL_HOST_COUNTRY);
    }};

    static final List<String> CHROME_TAB_HOSTS = new ArrayList<String>() {{
        add(URL.URL_HOST_PROFILE);
    }};

}
