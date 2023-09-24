package br.com.netdeal.desafio.backend.service;

import br.com.netdeal.desafio.backend.service.validator.DeductionsValidatorPassword;
import br.com.netdeal.desafio.backend.service.validator.DeductionsValidatorPasswordImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeductionsValidatorPasswordImplTest {

    public static final String PASSWORD = "ArrozDoce012*";
    public static final String PASSWORD_INSENSITIVE = "ArrozDOce012*";
    public static final String PASSWORD_3 = "12345987655*(){çabcDHGFJkL";
    public static final String PASSWORD_SYMBOLS = ")!@#$%^&*(";

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
        Assertions.assertEquals(-2, result);
    }

    @Test
    void deductionRepeatCharactersCaseInsensitive() {
        int result = builder.deductionRepeatCharacters(PASSWORD_INSENSITIVE);
        Assertions.assertEquals(-2, result);
    }

    @Test
    void deductionConsecutiveUppercaseLetters() {
        int result = builder.deductionConsecutiveUppercaseLetters(PASSWORD_3);
        Assertions.assertEquals(-8, result);
    }

    @Test
    void deductionConsecutiveLowercaseLetters() {
        int result = builder.deductionConsecutiveLowercaseLetters(PASSWORD_3);
        Assertions.assertEquals(-4, result);
    }

    @Test
    void deductionConsecutiveNumbers() {
        int result = builder.deductionConsecutiveNumbers(PASSWORD_3);
        Assertions.assertEquals(-20, result);
    }

    @Test
    void deductionSequentialLetters() {
        int result = builder.deductionSequentialLetters(PASSWORD_3);
        Assertions.assertEquals(-12, result);
    }

    @Test
    void deductionSequentialNumbers() {
        int result = builder.deductionSequentialNumbers(PASSWORD_3);
        Assertions.assertEquals(-18, result);
    }

    @Test
    void deductionSequentialSymbol() {
        int result = builder.deductionSequentialSymbol(PASSWORD_SYMBOLS);
        Assertions.assertEquals(-24, result);
    }
}