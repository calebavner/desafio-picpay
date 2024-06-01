package app.service;

import app.client.AuthorizationClient;
import app.exception.PersonalException;
import app.model.Transfer;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(Transfer transfer) {

        var resp = authorizationClient.isAuth();

        if(resp.getStatusCode().isError())
            throw new PersonalException();

        return resp.getBody().authorized();
    }
}
