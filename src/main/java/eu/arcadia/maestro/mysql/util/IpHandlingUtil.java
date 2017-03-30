package eu.arcadia.maestro.mysql.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by John Tsantilis
 * (i [dot] tsantilis [at] yahoo [dot] com A.K.A lumi) on 22/2/2017.
 */

public class IpHandlingUtil {
    /**
     * Determine if the given string is a valid IPv4. This method
     * uses pattern matching to see if the given string could be a valid IP address.
     *
     * @param ipAddress A string that is to be examined to verify whether or not
     *  it could be a valid IP address.
     * @return <code>true</code> if the string is a value that is a valid IP address,
     *  <code>false</code> otherwise.
     */
    public static boolean isIpV4Address(String ipAddress) {
        Matcher m1 = IpHandlingUtil.VALID_IPV4_PATTERN.matcher(ipAddress);

        if (m1.matches()) {
            return true;

        }
        else {
            return false;

        }

    }

    /**
     * Determine if the given string is a valid IPv6. This method
     * uses pattern matching to see if the given string could be a valid IP address.
     *
     * @param ipAddress A string that is to be examined to verify whether or not
     *  it could be a valid IP address.
     * @return <code>true</code> if the string is a value that is a valid IP address,
     *  <code>false</code> otherwise.
     */
    public static boolean isIpV6Address(String ipAddress) {
        Matcher m2 = IpHandlingUtil.VALID_IPV6_PATTERN.matcher(ipAddress);

        if (m2.matches()) {
            return true;

        }
        else {
            return false;

        }

    }

    private static Pattern VALID_IPV4_PATTERN = null;
    private static Pattern VALID_IPV6_PATTERN = null;
    private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    static {
        try {
            VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
            VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);

        }
        catch (PatternSyntaxException ex) {
            Logger.getLogger(IpHandlingUtil.class.getName()).log(Level.SEVERE, "Unable to compile pattern ", ex);

        }

    }

}
