package br.com.netdeal.desafio.backend.service.validator;

public interface DeductionsValidatorPassword {

    int deductionLettersOnly(String password);

    int deductionNumbersOnly(String password);

    int deductionRepeatCharacters(String password);

    int deductionConsecutiveUppercaseLetters(String password);

    int deductionConsecutiveLowercaseLetters(String password);

    int deductionConsecutiveNumbers(String password);

    int deductionSequentialLetters(String password);

    int deductionSequentialNumbers(String password);

    int deductionSequentialSymbol(String password);
}
