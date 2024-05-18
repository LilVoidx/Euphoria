package soul.euphoria.services.notification.impl;

public interface PusherService {

    void sendNotification(String channel, String event, String message, String songTitle, Long songID);
}
