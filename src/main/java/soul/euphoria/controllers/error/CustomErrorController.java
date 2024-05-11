package soul.euphoria.controllers.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import soul.euphoria.controllers.user.ProfileController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Objects.nonNull(status)) {
            int statusCode = Integer.parseInt(status.toString());

            String errorMessage = "";
            String errorDescription = "";

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "OOPS! Page not found";
                errorDescription = "Sorry but the page you are looking for does not exist, have been removed. name changed or is temporarily unavailable";
                logger.error("Page not found");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorMessage = "Internal server error";
                errorDescription = "Sorry but the server is currently experiencing some problems. Please try again later.";
                logger.error("Internal server error");
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                errorMessage = "Bad request";
                errorDescription = "Sorry but your request is invalid. Please try again later.";
                logger.error("Bad request");
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                errorMessage = "Unauthorized";
                errorDescription = "Sorry but you are not authorized to access this resource.";
                logger.error("Unauthorized");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "Forbidden";
                errorDescription = "Sorry but you are not authorized to access this resource.";
                logger.error("Forbidden");
            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                errorMessage = "Method not allowed";
                errorDescription = "Sorry but the method is not allowed.";
                logger.error("Method not allowed");
            } else if (statusCode == HttpStatus.CONFLICT.value()) {
                errorMessage = "Conflict";
                errorDescription = "Sorry but there is a conflict.";
                logger.error("Conflict");
            } else {
                errorMessage = "Unknown error";
                errorDescription = "Sorry but there is an unknown error.";
                logger.error("Unknown error");
            }
            model.addAttribute("statusCode", statusCode);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorDescription", errorDescription);
            return "error/error";
        }
        // If status code is not set, return an Unknown error.
        model.addAttribute("statusCode", "IDK");
        model.addAttribute("errorDescription", "Sorry but there is an unknown error.");
        model.addAttribute("errorMessage", "Unknown error");
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
