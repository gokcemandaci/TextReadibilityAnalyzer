import java.util.Scanner;

/**
 * Text Readability Analyzer
 *
 * This program takes a text input and calculates:
 *   - Average word length
 *   - Average sentence length
 *   - Punctuation density
 *   - Uppercase letter ratio
 *   - A final readability score
 *
 * Readability formula (inspired by Flesch reading ease):
 *   score = 100 - (avgSentenceLength * 2 + avgWordLength * 5)
 *
 * Higher score = easier to read
 *
 * @author CS Student, 3rd Year
 * @version 1.0
 */
public class TextReadabilityAnalyzer {

    // I'll use these constants for the formula weights
    // makes it easier to tweak later
    static final double SENTENCE_WEIGHT = 2.0;
    static final double WORD_WEIGHT     = 5.0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===================================");
        System.out.println("   TEXT READABILITY ANALYZER       ");
        System.out.println("===================================");
        System.out.println("Paste your text below, then press Enter twice:");
        System.out.println();

        // Read multiple lines until user enters an empty line
        StringBuilder sb = new StringBuilder();
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty()) break;   // empty line = done
            sb.append(line).append(" ");
        }

        String text = sb.toString().trim();

        if (text.isEmpty()) {
            System.out.println("No text entered. Exiting.");
            return;
        }

        // --- Run the analysis ---
        analyzeAndPrint(text);

        scanner.close();
    }

    //  MAIN ANALYSIS METHOD

    static void analyzeAndPrint(String text) {

        // 1) Split into words
        String[] words = getWords(text);
        int wordCount = words.length;

        // 2) Split into sentences
        String[] sentences = getSentences(text);
        int sentenceCount = sentences.length;

        // 3) Count total characters in words (letters only, no spaces/punctuation)
        int totalWordChars = 0;
        for (String word : words) {
            // strip punctuation attached to words like "hello," or "world."
            String cleaned = word.replaceAll("[^a-zA-Z0-9]", "");
            totalWordChars += cleaned.length();
        }

        // 4) Count punctuation marks in the whole text
        int punctuationCount = countPunctuation(text);

        // 5) Count uppercase letters
        int uppercaseCount = countUppercase(text);
        int letterCount    = countLetters(text);

        // --- Calculate metrics ---

        double avgWordLength = (wordCount > 0)
                ? (double) totalWordChars / wordCount
                : 0;

        double avgSentenceLength = (sentenceCount > 0)
                ? (double) wordCount / sentenceCount
                : 0;

        // punctuation density = punctuation marks per 100 characters
        double punctuationDensity = (text.length() > 0)
                ? ((double) punctuationCount / text.length()) * 100
                : 0;

        // uppercase ratio = uppercase letters / all letters * 100
        double uppercaseRatio = (letterCount > 0)
                ? ((double) uppercaseCount / letterCount) * 100
                : 0;

        // Readability score (clamped to 0-100 range)
        double readabilityScore = 100 - (avgSentenceLength * SENTENCE_WEIGHT
                                       + avgWordLength     * WORD_WEIGHT);

        // clamp: score shouldn't go below 0 or above 100
        readabilityScore = Math.max(0, Math.min(100, readabilityScore));

        // --- Print results ---
        printResults(
            text, wordCount, sentenceCount,
            avgWordLength, avgSentenceLength,
            punctuationDensity, uppercaseRatio,
            readabilityScore
        );
    }

    //  HELPER METHODS

    /**
     * Splits text into words by whitespace.
     */
    static String[] getWords(String text) {
        if (text == null || text.trim().isEmpty()) return new String[0];
        return text.trim().split("\\s+");
    }

    /**
     * Splits text into sentences.
     * Sentences end with  .  !  or  ?
     */
    static String[] getSentences(String text) {
        if (text == null || text.trim().isEmpty()) return new String[0];

        // split on . ! ? but only if followed by space or end of string
        String[] sentences = text.trim().split("[.!?]+");

        // filter out empty strings that might appear after split
        int count = 0;
        for (String s : sentences) {
            if (!s.trim().isEmpty()) count++;
        }

        if (count == 0) return new String[]{ text }; // treat whole text as 1 sentence

        String[] result = new String[count];
        int i = 0;
        for (String s : sentences) {
            if (!s.trim().isEmpty()) result[i++] = s.trim();
        }
        return result;
    }

    /**
     * Counts punctuation characters: . , ! ? ; : " ' ( ) -
     */
    static int countPunctuation(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (isPunctuation(c)) count++;
        }
        return count;
    }

    static boolean isPunctuation(char c) {
        String punctuations = ".,!?;:\"'()-";
        return punctuations.indexOf(c) != -1;
    }

    /**
     * Counts uppercase letters A-Z
     */
    static int countUppercase(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) count++;
        }
        return count;
    }

    /**
     * Counts all letters (a-z, A-Z)
     */
    static int countLetters(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) count++;
        }
        return count;
    }

    /**
     * Converts score to a human-readable label
     */
    static String getScoreLabel(double score) {
        if (score >= 80) return "Very Easy";
        else if (score >= 60) return "Easy";
        else if (score >= 40) return "Medium";
        else if (score >= 20) return "Hard";
        else return "Very Hard";
    }

    //  PRINT RESULTS

    static void printResults(
            String text,
            int wordCount,
            int sentenceCount,
            double avgWordLength,
            double avgSentenceLength,
            double punctuationDensity,
            double uppercaseRatio,
            double readabilityScore) {

        System.out.println();
        System.out.println("===================================");
        System.out.println("         ANALYSIS RESULTS          ");
        System.out.println("===================================");

        System.out.printf("  Total Characters    : %d%n", text.length());
        System.out.printf("  Total Words         : %d%n", wordCount);
        System.out.printf("  Total Sentences     : %d%n", sentenceCount);
        System.out.println("-----------------------------------");
        System.out.printf("  Avg Word Length     : %.2f chars%n",  avgWordLength);
        System.out.printf("  Avg Sentence Length : %.2f words%n",  avgSentenceLength);
        System.out.printf("  Punctuation Density : %.2f%%%n",      punctuationDensity);
        System.out.printf("  Uppercase Ratio     : %.2f%%%n",      uppercaseRatio);
        System.out.println("-----------------------------------");
        System.out.printf("  Readability Score   : %.1f / 100%n",  readabilityScore);
        System.out.printf("  Difficulty Level    : %s%n",          getScoreLabel(readabilityScore));
        System.out.println("===================================");
        System.out.println();
        System.out.println("Formula used:");
        System.out.println("  score = 100 - (avgSentenceLength * 2 + avgWordLength * 5)");
        System.out.println("  (score clamped between 0 and 100)");
        System.out.println();
    }
}