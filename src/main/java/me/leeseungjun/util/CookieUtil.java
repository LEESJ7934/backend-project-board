package me.leeseungjun.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;


public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) { //이름, 값, 만료기간을 바탕으로 쿠키 추가
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }


        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())){
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
    public static String serialize(Object obj){
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }
    public static <T> T deserializeFromString(String s, Class<T> cls) {
        byte[] data = Base64.getUrlDecoder().decode(s);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return cls.cast(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Deserialization error", e);
        }
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                deserializeFromString(cookie.getValue(), cls)
        );
    }
}
