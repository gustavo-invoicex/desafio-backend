package br.com.netdeal.desafio.backend.service.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

import static br.com.netdeal.desafio.backend.config.Constasts.FOUR;
import static br.com.netdeal.desafio.backend.config.Constasts.ONE;
import static br.com.netdeal.desafio.backend.config.Constasts.SIX;
import static br.com.netdeal.desafio.backend.config.Constasts.THREE;
import static br.com.netdeal.desafio.backend.config.Constasts.TWO;
import static br.com.netdeal.desafio.backend.config.Constasts.ZERO;

@Component
public class AdditionValidatorPasswordImpl implements AdditionValidatorPassword {

    private int score = ZERO;

    private int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score += score;
    }

    private void initializeScore() {
        this.score = ZERO;
    }

    @Override
    public int additionNumberOfCharacters(String password) {
        return multipleBonus(password.length(), FOUR);
    }

    @Override
    public int uppercaseLetters(String password) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(password);
        int count = ZERO;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        if (count == ZERO) return ZERO;
        return multipleBonus(password.length() - count, TWO);
    }

    @Override
    public int lowercaseLetters(String password) {
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher(password);
        int count = ZERO;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        if (count == ZERO) return ZERO;
        return multipleBonus(password.length() - count, TWO);
    }

    @Override
    public int validateNumbers(String password) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(password);
        int count = ZERO;
        while (matcher.find()) {
            count += matcher.group().length();
        }

        if (isNumbersOnly(password)) return ZERO;
        return multipleBonus(count, FOUR);
    }

    private Boolean isNumbersOnly(String password) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(password);
        int count = ZERO;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return count != ZERO;
    }

    @Override
    public int validateSymbols(String password) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
        Matcher matcher = pattern.matcher(password);
        int count = ZERO;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return multipleBonus(count, SIX);
    }

    @Override
    public int validateMiddleNumbersOrSymbols(String password) {
        Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");
        Matcher matcher = pattern.matcher(password);
        int count = -ONE;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return multipleBonus(count >= ONE ? count : ZERO, TWO);
    }

    @Override
    public int validateRequirements(String password) {
        int total = ZERO;
        total += additionNumberOfCharacters(password) != ZERO ? ONE : ZERO;
        total += uppercaseLetters(password) != ZERO ? ONE : ZERO;
        total += lowercaseLetters(password) != ZERO ? ONE : ZERO;
        total += validateNumbers(password) != ZERO ? ONE : ZERO;
        total += validateSymbols(password) != ZERO ? ONE : ZERO;
        return multipleBonus(total > THREE ? total : ZERO, TWO);
    }

    @Override
    public int getAdditionScore(String password) {
        initializeScore();
        setScore(additionNumberOfCharacters(password));
        setScore(uppercaseLetters(password));
        setScore(lowercaseLetters(password));
        setScore(validateNumbers(password));
        setScore(validateSymbols(password));
        setScore(validateMiddleNumbersOrSymbols(password));
        setScore(validateRequirements(password));
        return getScore();
    }

    private static Matcher makeMatcher(String password, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password);
    }

    private int multipleBonus(int count, int weight) {
        return count * weight;
    }
}
