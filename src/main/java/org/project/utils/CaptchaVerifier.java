package org.project.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

/**
 * The utility class for verifying if the captcha response is valid i.e. to verify the authentication code
 */
public class CaptchaVerifier {
    private static final Logger log = LoggerFactory.getLogger(CaptchaVerifier.class);
    private static String SITE_KEY;
    private static String SECRET_KEY;
    private static String VERIFY_URL;

    static {
        try (InputStream in = CaptchaVerifier.class.getResourceAsStream("/captcha_credentials.properties")){
            Properties properties = new Properties();
            properties.load(in);

            SITE_KEY = properties.getProperty("Site_key");
            SECRET_KEY = properties.getProperty("Secret_key");
            VERIFY_URL = properties.getProperty("Site_verify_URL");
        } catch (IOException exception) {
            log.error("Couldn't read captcha properties");
        }
    }

    public static boolean verify(String reCaptchaResp) {
        if (UtilProvider.isEmpty(reCaptchaResp)) return false;

        try {
            URL verifyUrl = new URL(VERIFY_URL);
            HttpsURLConnection con = (HttpsURLConnection) verifyUrl.openConnection();
            setHeaderInfo(con);

            final String postParams = String.format("secret=%s&response=%s", SECRET_KEY, reCaptchaResp);
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            log.info(String.format("Captcha response code from connection verify URL is: %s\nWith SiteKey :%s",
                    con.getResponseCode(), SITE_KEY));
            InputStream in = con.getInputStream();

            JsonReader jsonReader = Json.createReader(in);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception exception) {
            log.warn("Exception occurred during captcha verification");
        }
        return false;
    }

    private static void setHeaderInfo(HttpsURLConnection con) throws ProtocolException {
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    }
}
