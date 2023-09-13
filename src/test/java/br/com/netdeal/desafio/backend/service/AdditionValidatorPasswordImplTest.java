package br.com.netdeal.desafio.backend.service;

import br.com.netdeal.desafio.backend.service.validator.AdditionValidatorPasswordImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdditionValidatorPasswordImplTest {

    public static final String SENHA = "ArrozDoce012*";

    private AdditionValidatorPasswordImpl builder = new AdditionValidatorPasswordImpl();

    @Test
    void testAdditionNumberOfCharactersValidateScore() {
        int result = builder.additionNumberOfCharacters(SENHA);
        Assertions.assertEquals(result, 52);
    }

    @Test
    void testUppercaseLettersValidateScore() {
        int result = builder.uppercaseLetters(SENHA);
        Assertions.assertEquals(result, 22);
    }

    @Test
    void testLowercaseLettersValidateScore() {
        int result = builder.lowercaseLetters(SENHA);
        Assertions.assertEquals(result, 12);
    }

    @Test
    void testValidateNumbersValidateScore() {
        int result = builder.validateNumbers(SENHA);
        Assertions.assertEquals(result, 12);
    }

    @Test
    void testValidateSymbolsValidateScore() {
        int result = builder.validateSymbols(SENHA);
        Assertions.assertEquals(result, 6);
    }

    @Test
    void testValidateMiddleNumbersOrSymbolsValidateScore() {
        int result = builder.validateMiddleNumbersOrSymbols(SENHA);
        Assertions.assertEquals(result, 6);
    }

    @Test
    void testValidateRequirementsValidateScore() {
        int result = builder.validateRequirements(SENHA);
        Assertions.assertEquals(result, 10);
    }

    @Test
    void testGetAdditionalScoreValidateScore() {
        int result = builder.getAdditionScore(SENHA);
        Assertions.assertEquals(result, 120);
    }
}