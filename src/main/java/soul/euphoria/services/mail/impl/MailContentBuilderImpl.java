package soul.euphoria.services.mail.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import soul.euphoria.services.mail.MailContentBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailContentBuilderImpl implements MailContentBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MailContentBuilderImpl.class);

    private final Template confirmMailTemplate;

    public MailContentBuilderImpl() {
        // Initialize FreeMarker configuration
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new SpringTemplateLoader(new ClassRelativeResourceLoader(this.getClass()), "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        try {
            // Load the confirmation email template
            this.confirmMailTemplate = configuration.getTemplate("templates/auth/confirm_mail.ftlh");
        } catch (IOException e) {
            // Log error if template loading fails
            logger.error("Error loading confirmation email template", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String buildConfirmationEmail(String code) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("confirm_code", code);

        StringWriter writer = new StringWriter();
        try {
            // Process the confirmation email template
            confirmMailTemplate.process(attributes, writer);
        } catch (TemplateException | IOException e) {
            // Log error if template processing fails
            logger.error("Error processing confirmation email template", e);
            throw new IllegalStateException(e);
        }
        return writer.toString();
    }
}
