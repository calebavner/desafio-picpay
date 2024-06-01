package app.service;

import app.client.NotificationClient;
import app.model.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer) {
        try {
            LOGGER.info("Sending notification");
            var resp = notificationClient.sendNotification(transfer);

            if(resp.getStatusCode().isError())
                LOGGER.error("Error while send notification");
            
        } catch(Exception e) {
            LOGGER.error("Error while send notification", e);
        }
    }
}
