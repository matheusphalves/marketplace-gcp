package com.marketplace.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Matheus Alves
 */
public class NetworkUtils {

    public static String getHostAddress() {
        try {

            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
