package com.jen.easy.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jen on 2017/7/21.
 */

class EasyHttpURlConnection extends HttpURLConnection {
    /**
     * @param u the URL
     */
    protected EasyHttpURlConnection(URL u) {
        super(u);
    }




    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {

    }
}
