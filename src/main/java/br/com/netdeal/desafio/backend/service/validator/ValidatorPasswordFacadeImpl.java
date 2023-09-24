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

    private int obtainScore(String password) {
        int score = 0;
        score += additionValidatorPassword.getAdditionScore(password);
        score += deductionsValidatorPassword.getDeductionScore(password);
        return score;
    }

    @Override
    public PasswordSecurityLevel obtainPasswordSecurityLevel(String password) {
        return switch (obtainScore(password) / 20) {
            case 0 -> PasswordSecurityLevel.VERY_WEAK;
            case 1 -> PasswordSecurityLevel.WEAK;
            case 2 -> PasswordSecurityLevel.GOOD;
            case 3 -> PasswordSecurityLevel.STRONG;
            default -> PasswordSecurityLevel.VERY_STRONG;
        };
    }

    @Override
    public int obtainScorePercent(String password) {
        int score = obtainScore(password);
        return Math.min(score, 100);
    }
}
