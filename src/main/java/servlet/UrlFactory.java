package servlet;

import javax.servlet.http.HttpServletRequest;

public class UrlFactory {

    public static String createUrl(HttpServletRequest request, String path) {
        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/" +
                path;
    }
}
