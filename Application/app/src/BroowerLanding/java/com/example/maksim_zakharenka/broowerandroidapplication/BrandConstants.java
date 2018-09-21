package com.example.maksim_zakharenka.broowerandroidapplication;

import java.util.ArrayList;
import java.util.List;

public final class BrandConstants {

    interface URL {

        String URL_HOST = "https://broower.github.io";
    }

    static final List<String> AVAILABLE_HOSTS = new ArrayList<String>() {{
        add(URL.URL_HOST);
    }};

    static final List<String> CHROME_TAB_HOSTS = new ArrayList<String>() {{

    }};
}
