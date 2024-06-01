package app.client;

import app.model.Transfer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${client.notification-service.url}")
public interface NotificationClient {

    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody Transfer transfer);
}
