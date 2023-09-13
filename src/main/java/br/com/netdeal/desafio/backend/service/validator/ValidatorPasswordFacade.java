package br.com.netdeal.desafio.backend.service.validator;

import br.com.netdeal.desafio.backend.data.model.PasswordSecurityLevel;

public interface ValidatorPasswordFacade {
    int obtainScore(String password);
    PasswordSecurityLevel obtainPasswordSecurityLevel(String password);
}
