package soul.euphoria.controllers.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model, HttpStatus status) {
        int statusCode = status.value();
        String errorMessage = "";
        switch (statusCode) {
            case 404:
                errorMessage = "Page not found";
                break;
            case 500:
                errorMessage = "Internal server error";
                break;
            case 400:
                errorMessage = "Bad request";
                break;
            case 401:
                errorMessage = "Unauthorized";
                break;
            case 403:
                errorMessage = "Forbidden";
                break;
            case 405:
                errorMessage = "Method not allowed";
                break;
            case 409:
                errorMessage = "Conflict";
                break;
            default:
                errorMessage = "Unknown error";
                break;
        }
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
