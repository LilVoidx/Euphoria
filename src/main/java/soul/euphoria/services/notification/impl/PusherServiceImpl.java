package soul.euphoria.services.notification.impl;

import com.pusher.rest.Pusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import soul.euphoria.services.mail.impl.EmailSenderImpl;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PusherServiceImpl implements  PusherService {

    @Value("${pusher.app.id}")
    private String appId;

    @Value("${pusher.key}")
    private String key;

    @Value("${pusher.secret}")
    private String secret;

    @Value("${pusher.cluster}")
    private String cluster;
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);

    private Pusher pusher;

    @PostConstruct
    public void initialize() {
        pusher = new Pusher(appId, key, secret);
        pusher.setCluster(cluster); //region
        pusher.setEncrypted(true);
    }
    @Override
    public void sendNotification(String channel, String event, String message, String songTitle, Long songID) {
        // Create hashmap with data needed for the response
        Map<String, String> data = new HashMap<>();
        data.put("message", message);
        data.put("songTitle", songTitle);
        data.put("songId", String.valueOf(songID));
        pusher.trigger(channel, event, data);

        logger.info("Notification sent for channel: {}, event: {}, message: {}, songTitle: {}, songID: {}", channel, event, message, songTitle, songID);
    }
}
