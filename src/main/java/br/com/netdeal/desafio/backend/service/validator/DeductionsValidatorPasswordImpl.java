package br.com.netdeal.desafio.backend.service.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

import static br.com.netdeal.desafio.backend.config.Constasts.ONE;
import static br.com.netdeal.desafio.backend.config.Constasts.THREE;
import static br.com.netdeal.desafio.backend.config.Constasts.TWO;
import static br.com.netdeal.desafio.backend.config.Constasts.ZERO;

@Component
public class DeductionsValidatorPasswordImpl implements DeductionsValidatorPassword {

    private int score = ZERO;

    @Override
    public int deductionLettersOnly(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(password);
        int count = ZERO;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return -count;
    }

    @Override
    public int deductionNumbersOnly(String password) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(password);
        int count = ZERO;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return -count;
    }

    @Override
    public int deductionRepeatCharacters(String password) {
        int count = ZERO;
        char[] characters = password.toCharArray();
        List<Character> charactersList = new ArrayList<>();
        for (char c : characters) {
            charactersList.add(c);
        }
        Map<Character, Integer> duplicates = charactersList.stream().reduce(new HashMap<>(), (map, character) -> {
            map.put(character, map.getOrDefault(character, ZERO) + ONE);
            return map;
        }, (map1, map2) -> {
            map2.forEach((key, value) -> map1.merge(key, value, Integer::sum));
            return map1;
        });

        for (int duplicate : duplicates.values()) {
            if (duplicate > ONE) {
                count += duplicate;
            }
        }

        Boolean isDuplicateCaseSensitive = verifyDuplicateSensitive(charactersList) || verifyDuplicateInsensitive(charactersList);
        if (count > ZERO && isDuplicateCaseSensitive) return -TWO;

        if (count > ZERO) return -ONE;

        return ZERO;
    }

    private Boolean verifyDuplicateSensitive(List<Character> characters) {
        for (Character character : characters) {
            char characterUppercase = Character.toUpperCase(character);
            if (characters.contains(characterUppercase) && characters.contains(character) && Character.isLowerCase(character)) {
                return true;
            }
        }
        return false;
    }

    private Boolean verifyDuplicateInsensitive(List<Character> characters) {
        for (Character character : characters) {
            char characterUppercase = Character.toUpperCase(character);
            List<Character> collect = characters.stream().map(Character::toUpperCase).toList();
            if (collect.stream().filter(character1 -> character1.equals(characterUppercase)).count() > ONE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int deductionConsecutiveUppercaseLetters(String password) {
        int count = ZERO;
        char[] caracteres = password.toCharArray();

        for (int i = ZERO; i < caracteres.length - ONE; i++) {
            char atual = caracteres[i];
            char proximo = caracteres[i + ONE];

            if (Character.isUpperCase(atual) && Character.isUpperCase(proximo) && atual >= 'A' && atual <= 'Z' && proximo >= 'A' && proximo <= 'Z') {
                count++;
            }
        }
        return multipleDeductions(-TWO, count);
    }

    @Override
    public int deductionConsecutiveLowercaseLetters(String password) {
        int count = ZERO;
        char[] caracteres = password.toCharArray();

        for (int i = ZERO; i < caracteres.length - ONE; i++) {
            char atual = caracteres[i];
            char proximo = caracteres[i + ONE];

            if (Character.isLowerCase(atual) && Character.isLowerCase(proximo) && atual >= 'a' && atual <= 'z' && proximo >= 'a' && proximo <= 'z') {
                count++;
            }
        }
        return multipleDeductions(-TWO, count);
    }

    @Override
    public int deductionConsecutiveNumbers(String password) {
        int count = ZERO;
        char[] caracteres = password.toCharArray();

        for (int i = ZERO; i < caracteres.length - ONE; i++) {
            char atual = caracteres[i];
            char proximo = caracteres[i + ONE];

            if (Character.isDigit(atual) && Character.isDigit(proximo)) {
                count++;
            }
        }
        return multipleDeductions(-TWO, count);
    }

    @Override
    public int deductionSequentialLetters(String password) {
        int count = ZERO;
        char[] caracteres = password.toCharArray();

        for (int i = ZERO; i < caracteres.length - TWO; i++) {
            char first = Character.toLowerCase(caracteres[i]);
            char second = Character.toLowerCase(caracteres[i + ONE]);
            char third = Character.toLowerCase(caracteres[i + TWO]);

            if (Character.isLetter(first) && Character.isLetter(second) && Character.isLetter(third)) {
                if ((first + ONE == second && second + ONE == third) || (first - ONE == second && second - ONE == third)) {
                    count++;
                }
            }
        }
        return multipleDeductions(-THREE, count);
    }

    @Override
    public int deductionSequentialNumbers(String password) {
        int count = ZERO;
        char[] caracteres = password.toCharArray();

        for (int i = ZERO; i < caracteres.length - TWO; i++) {
            char first = caracteres[i];
            char second = caracteres[i + ONE];
            char third = caracteres[i + TWO];

            if (Character.isDigit(first) && Character.isDigit(second) && Character.isDigit(third)) {
                if ((first + ONE == second && second + ONE == third) || (first - ONE == second && second - ONE == third)) {
                    count++;
                }
            }
        }
        return multipleDeductions(-THREE, count);
    }

    @Override
    public int deductionSequentialSymbol(String password) {
        int count = ZERO;
        char[] caracteres = password.toCharArray();

        for (int i = ZERO; i < caracteres.length - TWO; i++) {
            char first = caracteres[i];
            char second = caracteres[i + ONE];
            char third = caracteres[i + TWO];

            if (isEspecialSymbol(first) && isEspecialSymbol(second) && isEspecialSymbol(third)) {
                if ((sortAsc(first, second, third) || sortDesc(first, second, third))) {
                    count++;
                }
            }
        }
        return multipleDeductions(-THREE, count);
    }

    @Override
    public int getDeductionScore(String password) {
        initializeScore();
        setScore(deductionLettersOnly(password));
        setScore(deductionNumbersOnly(password));
        setScore(deductionRepeatCharacters(password));
        setScore(deductionConsecutiveUppercaseLetters(password));
        setScore(deductionConsecutiveLowercaseLetters(password));
        setScore(deductionConsecutiveNumbers(password));
        setScore(deductionSequentialLetters(password));
        setScore(deductionSequentialNumbers(password));
        setScore(deductionSequentialSymbol(password));
        return getScore();
    }

    private boolean isEspecialSymbol(char caractere) {
        String simbolosEspeciais = ")!@#$%^&*(";
        return simbolosEspeciais.indexOf(caractere) != -ONE;
    }

    private boolean sortAsc(char a, char b, char c) {
        String ordemEspecifica = ")!@#$%^&*(";
        return ordemEspecifica.indexOf(a) + ONE == ordemEspecifica.indexOf(b) && ordemEspecifica.indexOf(b) + ONE == ordemEspecifica.indexOf(c);
    }

    private boolean sortDesc(char a, char b, char c) {
        String ordemEspecifica = ")!@#$%^&*(";
        return ordemEspecifica.indexOf(a) - ONE == ordemEspecifica.indexOf(b) && ordemEspecifica.indexOf(b) - ONE == ordemEspecifica.indexOf(c);
    }

    private int multipleDeductions(int count, int weight) {
        return count * weight;
    }

    private void setScore(int score) {
        this.score += score;
    }

    private void initializeScore() {
        this.score = ZERO;
    }

    public int getScore() {
        return score;
    }
}
