package br.com.netdeal.desafio.backend.service.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class AdditionValidatorPasswordImpl implements AdditionValidatorPassword {

    private int score = 0;

    private int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score += score;
    }

    @Override
    public int additionNumberOfCharacters(String password) {
        return multipleBonus(password.length(), 4);
    }

    @Override
    public int uppercaseLetters(String password) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return multipleBonus(password.length() - count, 2);
    }

    @Override
    public int lowercaseLetters(String password) {
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return multipleBonus(password.length() - count, 2);
    }

    @Override
    public int validateNumbers(String password) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return multipleBonus(count, 4);
    }

    @Override
    public int validateSymbols(String password) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return multipleBonus(count, 6);
    }

    @Override
    public int validateMiddleNumbersOrSymbols(String password) {
        Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");
        Matcher matcher = pattern.matcher(password);
        int count = -1;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return multipleBonus(count > 1 ? count : 0, 2);
    }

    @Override
    public int validateRequirements(String password) {
        int total = 0;
        total += additionNumberOfCharacters(password) != 0 ? 1 : 0;
        total += uppercaseLetters(password) != 0 ? 1 : 0;
        total += lowercaseLetters(password) != 0 ? 1 : 0;
        total += validateNumbers(password) != 0 ? 1 : 0;
        total += validateSymbols(password) != 0 ? 1 : 0;
        return multipleBonus(total > 2 ? total : 0, 2);
    }

    @Override
    public int getAdditionScore(String password) {
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
