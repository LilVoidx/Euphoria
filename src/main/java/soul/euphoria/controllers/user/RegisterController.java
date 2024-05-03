    package soul.euphoria.controllers.user;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PostMapping;
    import soul.euphoria.dto.forms.UserForm;
    import soul.euphoria.services.user.RegisterService;

    import javax.validation.Valid;

    @Controller
    public class RegisterController {

        private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

        @Autowired
        private RegisterService registerService;

        @GetMapping("/register")
        public String showRegistrationForm(Model model) {
            model.addAttribute("userForm", new UserForm());
            return "auth/register_page";
        }

        @PostMapping("/register")
        public String registerUser(@Valid @ModelAttribute("userForm") UserForm userForm,
                                   BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "auth/register_page"; // Return back to registration page with errors
            }

            try {
                // Log user registration details
                logger.info("User registration details: {}", userForm.toString());

                // Register the user
                registerService.registerUser(userForm);
                logger.info("User registered successfully");

                // Redirect to confirm page after successful registration
                return "redirect:/confirm-account";
            } catch (Exception e) {
                logger.error("Error occurred during user registration", e);

                // Redirect to centralized error page
                return "redirect:/error";
            }
        }

        @GetMapping("/confirm-account")
        public String confirmAccount() {
            return "auth/confirm_account_page";
        }
    }
