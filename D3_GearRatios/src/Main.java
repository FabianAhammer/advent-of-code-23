import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {
        part1();
        part2();
    }

    public static void part2() {
        int finalSum = 0;

        final String input1 = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""";

        final Map<Integer, List<HashedIntEntry>> intMap = new HashMap<>();
        final List<HashedIntEntry> inters = new ArrayList<>();
        final String[] splitLines = Input.INPUT.split("\n");
        for (char c : splitLines[0].toCharArray()) {
            inters.add(new HashedIntEntry(-1));
        }
        for (int lineCount = 0; lineCount < splitLines.length; lineCount++) {
            final List<HashedIntEntry> tempInt = inters.stream().collect(Collectors.toList());
            final String line = splitLines[lineCount];
            StringBuilder joinedKeys = new StringBuilder();
            for (int i = 0; i < line.toCharArray().length; i++) {
                //digit --> build!
                final char characterInLine = line.toCharArray()[i];
                if (Pattern.compile("\\d").matcher(String.valueOf(characterInLine)).matches()) {
                    joinedKeys.append(characterInLine);
                } else if (!joinedKeys.isEmpty()) {
                    final HashedIntEntry hashedIntEntry = new HashedIntEntry(Integer.parseInt(joinedKeys.toString()));
                    for (int lastIter = i; lastIter > i - joinedKeys.toString().length(); lastIter--) {
                        tempInt.set(lastIter - 1, hashedIntEntry);
                    }
                    joinedKeys = new StringBuilder();
                }
            }
            if (!joinedKeys.isEmpty()) {
                final HashedIntEntry hashedIntEntry = new HashedIntEntry(Integer.parseInt(joinedKeys.toString()));
                for (int lastIter = line.length() - 1; lastIter > line.length() - 1 - joinedKeys.toString().length(); lastIter--) {
                    tempInt.set(lastIter, hashedIntEntry);
                }
            }
            intMap.put(lineCount, tempInt);
        }

        for (int lineCount = 0; lineCount < splitLines.length; lineCount++) {
            final String line = splitLines[lineCount];
            final char[] charArray = line.toCharArray();
            for (int charPosition = 0; charPosition < charArray.length; charPosition++) {
                final String currentChar = String.valueOf(charArray[charPosition]);
                //We have a number
                if (Pattern.compile("\\*").matcher(currentChar).matches()) {
                    finalSum += initiateSearch(lineCount, splitLines, charPosition, intMap);
                }
            }
        }
        System.out.println("-------------");
        System.out.println(finalSum);
        System.out.println("-------------");
    }


    public static void part1() {
        // First

        int finalSum = 0;
        final Map<Integer, List<Boolean>> booleanMap = new HashMap<>();
        final List<Boolean> booleans = new ArrayList<>();
        final String[] splitLines = Input.INPUT.split("\n");
        for (char c : splitLines[0].toCharArray()) {
            booleans.add(false);
        }
        for (int lineCount = 0; lineCount < splitLines.length; lineCount++) {
            final List<Boolean> tempBoolean = booleans.stream().collect(Collectors.toList());
            final String line = splitLines[lineCount];
            for (int i = 0; i < line.toCharArray().length; i++) {
                //digit --> build!
                final char characterInLine = line.toCharArray()[i];
                if (Pattern.compile("[^.]").matcher(String.valueOf(characterInLine)).matches()) {
                    tempBoolean.set(i, true);
                }
            }
            booleanMap.put(lineCount, tempBoolean);
        }
        for (int lineCount = 0; lineCount < splitLines.length; lineCount++) {
            final String line = splitLines[lineCount];
            final char[] charArray = line.toCharArray();
            //List of lookup tables for valid positions
            final List<Boolean> previousLine = booleanMap.getOrDefault(lineCount - 1, Collections.emptyList());
            final List<Boolean> currentLine = booleanMap.get(lineCount);
            final List<Boolean> nextLine = booleanMap.getOrDefault(lineCount + 1, Collections.emptyList());

            //Iterate line each char
            StringBuilder builder = new StringBuilder();
            for (int charPosition = 0; charPosition < charArray.length; charPosition++) {
                final String currentChar = String.valueOf(charArray[charPosition]);
                boolean hit = false;
                //We have a number
                if (Pattern.compile("\\d").matcher(currentChar).matches()) {
                    builder.append(currentChar);
                    hit = true;
                }

                //we have a entry, and either have no hit or are at the end
                if (!builder.isEmpty() && (!hit || charPosition == charArray.length - 1)) {
                    if (hit) {
                        charPosition++;
                    }
                    final int number = Integer.parseInt(builder.toString());
                    final int leftOfNumber = charPosition - builder.toString().length() - 1;
                    final int rightOfNumber = charPosition;
                    if (leftOfNumber >= 0 && currentLine.get(leftOfNumber)) {
                        finalSum += number;
                    } else if (rightOfNumber <= charArray.length - 1 && currentLine.get(rightOfNumber)) {
                        finalSum += number;
                    } else {
                        final int cpFinal = finalSum;
                        for (int i = Math.max(leftOfNumber, 0); i <= Math.min(rightOfNumber, charArray.length - 1); i++) {
                            if (!previousLine.isEmpty() && previousLine.get(i)) {
                                finalSum += number;
                                break;
                            } else if (!nextLine.isEmpty() && nextLine.get(i)) {
                                finalSum += number;
                                break;
                            }
                        }
                        if (cpFinal == finalSum) {
                            System.out.printf("%s\n", number);
                        }
                    }
                    builder = new StringBuilder();
                }
            }
        }
        System.out.println("-------------");
        System.out.println(finalSum);
        System.out.println("-------------");
    }

    /**
     * Searches the found lineindex +1 / -1 and TWO adjacentcies up
     */
    private static int initiateSearch(final int lineIndex, final String[] lines, final int charIndex, final Map<Integer, List<HashedIntEntry>> integerMap) {
        final List<HashedIntEntry> foundAdjacent = new ArrayList<>();

        final String currentLine = lines[lineIndex];
        final Map<Integer, List<Integer>> allowedPositionsLineToCharList = new HashMap<>();

        if (charIndex == currentLine.length() - 1) {
            allowedPositionsLineToCharList.put(lineIndex - 1, List.of(charIndex - 1, charIndex));
            allowedPositionsLineToCharList.put(lineIndex, List.of(charIndex - 1));
            allowedPositionsLineToCharList.put(lineIndex + 1, List.of(charIndex - 1, charIndex));
        } else if (charIndex == 0) {
            allowedPositionsLineToCharList.put(lineIndex - 1, List.of(charIndex, charIndex + 1));
            allowedPositionsLineToCharList.put(lineIndex, List.of(charIndex + 1));
            allowedPositionsLineToCharList.put(lineIndex + 1, List.of(charIndex, charIndex + 1));

        } else {
            allowedPositionsLineToCharList.put(lineIndex - 1, List.of(charIndex - 1, charIndex, charIndex + 1));
            allowedPositionsLineToCharList.put(lineIndex, List.of(charIndex - 1, charIndex + 1));
            allowedPositionsLineToCharList.put(lineIndex + 1, List.of(charIndex - 1, charIndex, charIndex + 1));
        }

        for (Map.Entry<Integer, List<Integer>> integerListEntry : allowedPositionsLineToCharList.entrySet()) {
            if (integerListEntry.getKey() < 0 || integerListEntry.getKey() >= lines.length) {
                continue;
            }
            for (Integer charPos : integerListEntry.getValue()) {
                final HashedIntEntry hashEntry = integerMap.get(integerListEntry.getKey()).get(charPos);
                if (hashEntry.getValue() != -1) {
                    if (foundAdjacent.stream().noneMatch(e -> e.getUuid().equals(hashEntry.getUuid()))) {
                        foundAdjacent.add(hashEntry);
                    }
                }
            }
        }

        if (foundAdjacent.size() != 2) {
            return 0;
        }

        return foundAdjacent.get(0).getValue() * foundAdjacent.get(1).getValue();
    }
}