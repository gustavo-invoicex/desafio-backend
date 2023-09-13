package br.com.netdeal.desafio.backend.service;

import br.com.netdeal.desafio.backend.service.validator.DeductionsValidatorPassword;
import br.com.netdeal.desafio.backend.service.validator.DeductionsValidatorPasswordImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeductionsValidatorPasswordImplTest {

    public static final String PASSWORD = "ArrozDoce012*";
    public static final String PASSWORD_INSENSITIVE = "ArrozDOce012*";

    private final DeductionsValidatorPassword builder = new DeductionsValidatorPasswordImpl();

    @Test
    void deductionLettersOnly() {
        int result = builder.deductionLettersOnly(PASSWORD);
        Assertions.assertEquals(0, result);
    }

    @Test
    void deductionNumbersOnly() {
        int result = builder.deductionNumbersOnly(PASSWORD);
        Assertions.assertEquals(0, result);
    }

    @Test
    void deductionRepeatCharacters() {
        int result = builder.deductionRepeatCharacters(PASSWORD);
        Assertions.assertEquals(-1, result);
    }

    @Test
    void deductionRepeatCharactersCaseInsensitive() {
        int result = builder.deductionRepeatCharacters(PASSWORD_INSENSITIVE);
        Assertions.assertEquals(-2, result);
    }

    @Test
    void deductionConsecutiveUppercaseLetters() {
    }

    @Test
    void deductionConsecutiveLowercaseLetters() {
    }

    @Test
    void deductionConsecutiveNumbers() {
    }

    @Test
    void deductionSequentialLetters() {
    }

    @Test
    void deductionSequentialNumbers() {
    }

    @Test
    void deductionSequentialSymbol() {
    }
}