package br.com.netdeal.desafio.backend.service.validator;

import br.com.netdeal.desafio.backend.data.model.PasswordSecurityLevel;

public interface ValidatorPasswordFacade {
    PasswordSecurityLevel obtainPasswordSecurityLevel(String password);

    int obtainScorePercent(String password);
}
