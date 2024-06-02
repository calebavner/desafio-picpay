package app.service;

import app.controller.dto.TransferDto;
import app.exception.InsufficientBalanceException;
import app.exception.TransferNotAuthorizedException;
import app.exception.WalletNotFoundException;
import app.exception.WalletTypeException;
import app.model.Transfer;
import app.model.Wallet;
import app.repository.TransferRepository;
import app.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferService(AuthorizationService authorizationService,
                           NotificationService notificationService,
                           TransferRepository transferRepository,
                           WalletRepository walletRepository) {
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto dto) {
        var sender = walletRepository.findById(dto.sender())
                .orElseThrow(() -> new WalletNotFoundException(dto.sender()));

        var receiver = walletRepository.findById(dto.receiver())
                .orElseThrow(() -> new WalletNotFoundException(dto.receiver()));

        validadeTransfer(dto, sender);

        sender.debit(dto.value());
        receiver.credit(dto.value());

        Transfer transfer = new Transfer(sender, receiver, dto.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        Transfer transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validadeTransfer(TransferDto transfer, Wallet sender) {
        if(!sender.isWalletTypeUser()) {
            throw new WalletTypeException();
        }

        if(!sender.isBalanceOk(transfer.value())) {
            throw new InsufficientBalanceException();
        }

        if(!authorizationService.isAuthorized(transfer)) {
            throw new TransferNotAuthorizedException();
        }
    }
}
