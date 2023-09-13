package br.com.netdeal.desafio.backend.service.validator;

import br.com.netdeal.desafio.backend.data.model.PasswordSecurityLevel;
import org.springframework.stereotype.Component;

@Component
public class ValidatorPasswordFacadeImpl implements ValidatorPasswordFacade {
    private final AdditionValidatorPassword additionValidatorPassword;
    private final DeductionsValidatorPassword deductionsValidatorPassword;

    public ValidatorPasswordFacadeImpl(AdditionValidatorPassword additionValidatorPassword, DeductionsValidatorPassword deductionsValidatorPassword) {
        this.additionValidatorPassword = additionValidatorPassword;
        this.deductionsValidatorPassword = deductionsValidatorPassword;
    }

    @Override
    public int obtainScore(String password) {
        int score = 0;
        score += additionValidatorPassword.additionNumberOfCharacters(password);
        score += additionValidatorPassword.uppercaseLetters(password);
        score += additionValidatorPassword.lowercaseLetters(password);
        score += additionValidatorPassword.validateNumbers(password);
        score += additionValidatorPassword.validateSymbols(password);
        score += additionValidatorPassword.validateMiddleNumbersOrSymbols(password);
        score += additionValidatorPassword.validateRequirements(password);
        score += additionValidatorPassword.getAdditionScore(password);

        score += deductionsValidatorPassword.deductionLettersOnly(password);
        score += deductionsValidatorPassword.deductionNumbersOnly(password);
        score += deductionsValidatorPassword.deductionRepeatCharacters(password);
        score += deductionsValidatorPassword.deductionConsecutiveUppercaseLetters(password);
        score += deductionsValidatorPassword.deductionConsecutiveLowercaseLetters(password);
        score += deductionsValidatorPassword.deductionConsecutiveNumbers(password);
        score += deductionsValidatorPassword.deductionSequentialLetters(password);
        score += deductionsValidatorPassword.deductionSequentialNumbers(password);
        score += deductionsValidatorPassword.deductionSequentialSymbol(password);
        return score;
    }

    @Override
    public PasswordSecurityLevel obtainPasswordSecurityLevel(String password) {
        return PasswordSecurityLevel.SUFFICIENT;
    }
}
