/*
package soul.euphoria.controllers.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Here we get the status code from the request
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        HttpStatus status = HttpStatus.valueOf(statusCode);
        if (status == HttpStatus.NOT_FOUND) {
            return "error/404";
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            return "error/500";
        }
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
*/
