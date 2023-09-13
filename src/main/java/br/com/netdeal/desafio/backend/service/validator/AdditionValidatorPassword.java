package br.com.netdeal.desafio.backend.service.validator;

public interface AdditionValidatorPassword {

    int additionNumberOfCharacters(String password);

    int uppercaseLetters(String password);

    int lowercaseLetters(String password);

    int validateNumbers(String password);

    int validateSymbols(String password);

    int validateMiddleNumbersOrSymbols(String password);

    int validateRequirements(String password);

    int getAdditionScore(String password);

}
