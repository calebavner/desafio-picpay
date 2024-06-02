package app.controller;

import app.controller.dto.TransferDto;
import app.model.Transfer;
import app.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> tranfer(@RequestBody @Valid TransferDto dto) {
        return ResponseEntity.ok(transferService.transfer(dto));
    }
}
