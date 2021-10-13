package user;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@WebServlet(urlPatterns = {"/api/avatar/*"})
@MultipartConfig(maxFileSize = 1024 * 1024)
public class AvatarServlet extends HttpServlet {
    private Service service;

    @Inject
    public AvatarServlet(Service service){
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath() + request.getPathInfo();
        System.out.println("file");
        if(path.matches("/api/avatar/\\d+")){
            Long id = Long.valueOf(path.split("/")[3]);
            Optional<User> user = service.find(id);

            if(user.isPresent()) {
                byte[] fileArray = service.findAvatar(id);
                response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
                response.setContentLength(fileArray.length);
                response.getOutputStream().write(fileArray);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath() + request.getPathInfo();
        if (path.matches("/api/avatar/\\d+")) {
            Long id = Long.valueOf(path.split("/")[3]);
            Optional<User> user = service.find(id);
            if (user.isPresent()) {
                Part avatar = request.getPart("avatar");
                if (avatar != null) {
                    service.updateAvatar(id, avatar.getInputStream());
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath() + request.getPathInfo();
        if (path.matches("/api/avatar/\\d+")) {
            Long id = Long.valueOf(path.split("/")[3]);
            Optional<User> user = service.find(id);
            if (user.isPresent()) {
                Part avatar = request.getPart("avatar");
                if (avatar != null) {
                    service.updateAvatar(id, avatar.getInputStream());
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath() + request.getPathInfo();
        if (path.matches("/api/avatar/\\d+")) {
            Long id = Long.valueOf(path.split("/")[3]);
            Optional<User> user = service.find(id);
            if (user.isPresent()) {
                service.deleteAvatar(id);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
