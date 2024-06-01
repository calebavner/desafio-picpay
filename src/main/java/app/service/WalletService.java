package app.service;

import app.controller.dto.CreateWalletDto;
import app.exception.WalletAlreadyExistException;
import app.model.Wallet;
import app.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dto) {

        Optional<Wallet> query = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());

        if(query.isPresent())
            throw new WalletAlreadyExistException("cpfCnpj or Email alreayd exists");

        return walletRepository.save(dto.toWallet());
    }
}
