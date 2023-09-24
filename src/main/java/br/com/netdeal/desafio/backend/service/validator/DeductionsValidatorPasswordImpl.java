package br.com.netdeal.desafio.backend.service.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class DeductionsValidatorPasswordImpl implements DeductionsValidatorPassword {

    private int score = 0;

    @Override
    public int deductionLettersOnly(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return -count;
    }

    @Override
    public int deductionNumbersOnly(String password) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(password);
        int count = 0;
        while (matcher.find()) {
            count += matcher.group().length();
        }
        return -count;
    }

    @Override
    public int deductionRepeatCharacters(String password) {
        int count = 0;
        char[] characters = password.toCharArray();
        List<Character> charactersList = new ArrayList<>();
        for (char c : characters) {
            charactersList.add(c);
        }
        Map<Character, Integer> duplicates = charactersList.stream().reduce(new HashMap<>(), (map, character) -> {
            map.put(character, map.getOrDefault(character, 0) + 1);
            return map;
        }, (map1, map2) -> {
            map2.forEach((key, value) -> map1.merge(key, value, Integer::sum));
            return map1;
        });

        for (int duplicate : duplicates.values()) {
            if (duplicate > 1) {
                count += duplicate;
            }
        }

        Boolean isDuplicateCaseSensitive = verifyDuplicateSensitive(charactersList) || verifyDuplicateInsensitive(charactersList);
        if (count > 0 && isDuplicateCaseSensitive) return -2;

        if (count > 0) return -1;

        return 0;
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
            if (collect.stream().filter(character1 -> character1.equals(characterUppercase)).count() > 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int deductionConsecutiveUppercaseLetters(String password) {
        int count = 0;
        char[] caracteres = password.toCharArray();

        for (int i = 0; i < caracteres.length - 1; i++) {
            char atual = caracteres[i];
            char proximo = caracteres[i + 1];

            if (Character.isUpperCase(atual) && Character.isUpperCase(proximo) && atual >= 'A' && atual <= 'Z' && proximo >= 'A' && proximo <= 'Z') {
                count++;
            }
        }
        return multipleDeductions(-2, count);
    }

    @Override
    public int deductionConsecutiveLowercaseLetters(String password) {
        int count = 0;
        char[] caracteres = password.toCharArray();

        for (int i = 0; i < caracteres.length - 1; i++) {
            char atual = caracteres[i];
            char proximo = caracteres[i + 1];

            if (Character.isLowerCase(atual) && Character.isLowerCase(proximo) && atual >= 'a' && atual <= 'z' && proximo >= 'a' && proximo <= 'z') {
                count++;
            }
        }
        return multipleDeductions(-2, count);
    }

    @Override
    public int deductionConsecutiveNumbers(String password) {
        int count = 0;
        char[] caracteres = password.toCharArray();

        for (int i = 0; i < caracteres.length - 1; i++) {
            char atual = caracteres[i];
            char proximo = caracteres[i + 1];

            if (Character.isDigit(atual) && Character.isDigit(proximo)) {
                count++;
            }
        }
        return multipleDeductions(-2, count);
    }

    @Override
    public int deductionSequentialLetters(String password) {
        int count = 0;
        char[] caracteres = password.toCharArray();

        for (int i = 0; i < caracteres.length - 2; i++) {
            char primeiro = Character.toLowerCase(caracteres[i]);
            char segundo = Character.toLowerCase(caracteres[i + 1]);
            char terceiro = Character.toLowerCase(caracteres[i + 2]);

            if (Character.isLetter(primeiro) && Character.isLetter(segundo) && Character.isLetter(terceiro)) {
                if ((primeiro + 1 == segundo && segundo + 1 == terceiro) || (primeiro - 1 == segundo && segundo - 1 == terceiro)) {
                    count++;
                }
            }
        }
        return multipleDeductions(-3, count);
    }

    @Override
    public int deductionSequentialNumbers(String password) {
        int count = 0;
        char[] caracteres = password.toCharArray();

        for (int i = 0; i < caracteres.length - 2; i++) {
            char primeiro = caracteres[i];
            char segundo = caracteres[i + 1];
            char terceiro = caracteres[i + 2];

            if (Character.isDigit(primeiro) && Character.isDigit(segundo) && Character.isDigit(terceiro)) {
                if ((primeiro + 1 == segundo && segundo + 1 == terceiro) || (primeiro - 1 == segundo && segundo - 1 == terceiro)) {
                    count++;
                }
            }
        }
        return multipleDeductions(-3, count);
    }

    @Override
    public int deductionSequentialSymbol(String password) {
        int count = 0;
        char[] caracteres = password.toCharArray();

        for (int i = 0; i < caracteres.length - 2; i++) {
            char primeiro = caracteres[i];
            char segundo = caracteres[i + 1];
            char terceiro = caracteres[i + 2];

            if (isSimboloEspecial(primeiro) && isSimboloEspecial(segundo) && isSimboloEspecial(terceiro)) {
                if ((ordemCrescente(primeiro, segundo, terceiro) || ordemDecrescente(primeiro, segundo, terceiro))) {
                    count++;
                }
            }
        }
        return multipleDeductions(-3, count);
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

    private boolean isSimboloEspecial(char caractere) {
        String simbolosEspeciais = ")!@#$%^&*(";
        return simbolosEspeciais.indexOf(caractere) != -1;
    }

    private boolean ordemCrescente(char a, char b, char c) {
        String ordemEspecifica = ")!@#$%^&*(";
        return ordemEspecifica.indexOf(a) + 1 == ordemEspecifica.indexOf(b) && ordemEspecifica.indexOf(b) + 1 == ordemEspecifica.indexOf(c);
    }

    private boolean ordemDecrescente(char a, char b, char c) {
        String ordemEspecifica = ")!@#$%^&*(";
        return ordemEspecifica.indexOf(a) - 1 == ordemEspecifica.indexOf(b) && ordemEspecifica.indexOf(b) - 1 == ordemEspecifica.indexOf(c);
    }

    private int multipleDeductions(int count, int weight) {
        return count * weight;
    }

    private void setScore(int score) {
        this.score += score;
    }

    private void initializeScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }
}
