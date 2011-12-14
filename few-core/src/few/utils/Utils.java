package few.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 12.10.11
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static boolean isNull(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isNotNull(String s) {
        return s != null && !s.trim().isEmpty();
    }

    static final MessageDigest sha1;
    static {
        try {
            sha1 = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
    }

    public static String produceSHA1fromPassword(String login, String password) {
        byte[] bytes = sha1.digest((login + "_" + password).getBytes());
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            hexString.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return hexString.toString();
    }

    static final Random random;
    static final String charset = "!0123456789abcdefghijklmnopqrstuvwxyz";
    static {
        random = new Random(System.currentTimeMillis());
    }


    public static String generateNewPassword() {
        int length = 8;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = random.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }
        return sb.toString();
    }

    // from http://www.regular-expressions.info/email.html
    private static final String EMAIL_VALIDATION_REGEXP_INFO =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static final String EMAIL_VALIDATION_REGEXP = EMAIL_VALIDATION_REGEXP_INFO;

    static Pattern p = Pattern.compile(
            EMAIL_VALIDATION_REGEXP
    );

    public static boolean checkEmail(String sEmail) {
        Matcher m = p.matcher(sEmail.toLowerCase());

        return m.matches();
    }

    private static final String HEX_STRING = "0123456789abcdef";
    private static final char[] HEX_CHARS = HEX_STRING.toCharArray();
    public static String stringToHex(String input) {
        if (input == null) throw new NullPointerException();
        try {
            return asHex(input.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }

    public static String hexToString(String input) {
        if (input == null) throw new NullPointerException();
        try {
            return new String(fromHex(input), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }

    private static String asHex(byte[] buf)
    {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }

    private static byte[] fromHex(String hex) {
        byte buf[] = new byte[hex.length()/2];
        char ar[] = hex.toCharArray();
        for (int i = 0; i < ar.length; i+=2) {
            char c1 = ar[i];
            char c2 = ar[i+1];
            buf[i/2] = (byte)(HEX_STRING.indexOf(c1)*16 + (byte)HEX_STRING.indexOf(c2));
        }
        return buf;
    }

    public static String generateUID() {
        return UUID.randomUUID().toString();
    }

    public static Date curDate() {
        return new Date( (System.currentTimeMillis() / 1000L) * 1000L );
    }

    public static String exceptionToString(Throwable t) {
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        t.printStackTrace(new PrintStream(str));
        try {
            str.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
