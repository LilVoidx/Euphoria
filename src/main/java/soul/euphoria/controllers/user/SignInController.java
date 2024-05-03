    package soul.euphoria.controllers.user;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    public class SignInController {

        @GetMapping("/signIn")
        public String showSignInForm() {
            return "auth/sign_in_page";
        }
    }
