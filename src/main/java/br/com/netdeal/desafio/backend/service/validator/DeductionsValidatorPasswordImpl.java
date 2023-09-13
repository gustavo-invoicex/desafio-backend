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
        Map<Character, Integer> duplicates = charactersList.stream()
                .reduce(new HashMap<>(),
                        (map, character) -> {
                            map.put(character, map.getOrDefault(character, 0) + 1);
                            return map;
                        },
                        (map1, map2) -> {
                            map2.forEach((key, value) -> map1.merge(key, value, Integer::sum));
                            return map1;
                        });

        for (int duplicate : duplicates.values()) {
            if (duplicate > 1) {
                count += duplicate;
            }
        }

        Boolean isDuplicateCaseSensitive = verifyDuplicateSensitive(charactersList);
        if (count > 0 && isDuplicateCaseSensitive)
            return -2;

        if (count > 0)
            return -1;

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

    @Override
    public int deductionConsecutiveUppercaseLetters(String password) {
        return 0;
    }

    @Override
    public int deductionConsecutiveLowercaseLetters(String password) {
        return 0;
    }

    @Override
    public int deductionConsecutiveNumbers(String password) {
        return 0;
    }

    @Override
    public int deductionSequentialLetters(String password) {
        return 0;
    }

    @Override
    public int deductionSequentialNumbers(String password) {
        return 0;
    }

    @Override
    public int deductionSequentialSymbol(String password) {
        return 0;
    }

    private int multipleDeductions(int count, int weight) {
        return count * weight;
    }
}
