package user.servlet;

import servlet.UrlFactory;
import user.service.UserService;
import user.entity.User;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/api/avatar/*"})
@MultipartConfig(maxFileSize = 1024 * 1024)
public class AvatarServlet extends HttpServlet {
    private UserService service;

    @Inject
    public AvatarServlet(UserService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath() + request.getPathInfo();
        if (path.matches("/api/avatar/\\d+")) {
            getAvatar(response, path);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath() + request.getPathInfo();
        if (path.matches("/api/avatar/\\d+")) {
            putAvatar(request, response, path);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath() + request.getPathInfo();
        if (path.matches("/api/avatar/\\d+")) {
            postAvatar(request, response, path);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath() + request.getPathInfo();
        if (path.matches("/api/avatar/\\d+")) {
            deleteAvatar(response, path);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getAvatar(HttpServletResponse response, String path) throws IOException {
        Long id = Long.valueOf(path.split("/")[3]);
        byte[] fileArray = service.findAvatar(id);
        if (fileArray.length != 0) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
            response.setContentLength(fileArray.length);
            response.getOutputStream().write(fileArray);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException {
        Long id = Long.valueOf(path.split("/")[3]);
        byte[] fileArray = service.findAvatar(id);
        if (fileArray.length != 0) {
            Part avatar = request.getPart("avatar");
            if (avatar != null) {
                service.updateAvatar(id, avatar.getInputStream());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException {
        Long id = Long.valueOf(path.split("/")[3]);
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            Part avatar = request.getPart("avatar");
            if (avatar != null) {
                byte[] fileArray = service.findAvatar(id);
                if (fileArray.length == 0) {
                    service.createAvatar(id, avatar.getInputStream());
                    response.addHeader(HttpHeaders.LOCATION, UrlFactory.createUrl(request, "/api/avatar/" + id));
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteAvatar(HttpServletResponse response, String path) throws IOException {
        Long id = Long.valueOf(path.split("/")[3]);
        Optional<User> user = service.find(id);
        if (user.isPresent()) {
            service.deleteAvatar(id);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}