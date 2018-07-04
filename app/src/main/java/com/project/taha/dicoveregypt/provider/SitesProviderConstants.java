package com.project.taha.dicoveregypt.provider;

import android.net.Uri;

public interface SitesProviderConstants {


    Uri CONTENT_URI_1 = Uri.parse
            ("content://com.project.taha.dicoveregypt/SITES_LIST");
    Uri CONTENT_URI_2 = Uri.parse
            ("content://com.project.taha.dicoveregypt/SITE_ID");

    String DESC = "desc";
    String NAME = "name";
    String IMAGE = "image";
    String LATLONG = "latlong";
}
