package user;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/api/user/*"})
public class Servlet extends HttpServlet {

    private Service service;
    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public Servlet(Service service){
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath() + request.getPathInfo();
        if(path.matches("/api/user/\\d+")) {
            Optional<User> user = service.find(Long.valueOf(path.split("/")[3]));

            if (user.isPresent()) {
                response.setContentType("application/json");
                response.getWriter()
                        .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else if(path.equals("/api/usernull")){
            response.setContentType("application/json");
            response.getWriter()
                    .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(service.findAll())));
        }
        else{
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
