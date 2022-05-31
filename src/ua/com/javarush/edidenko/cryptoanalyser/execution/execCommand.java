package com.javarush.edidenko.cryptoanalyser.execution;

import com.javarush.edidenko.cryptoanalyser.CaesarCipher.CaesarCipher;
import com.javarush.edidenko.cryptoanalyser.argument.InputArgumentParser;

import java.io.*;
import java.util.*;

public class execCommand {
    public static final int AMOUNT_CHAR_IN_BEGIN_WORD = 3;

    public execCommand(String[] argumentsValue) {
        String execCommandMode = argumentsValue[0];
        switch (execCommandMode) {
            case InputArgumentParser.EXEC_ENCRYPT:
                encrypt(argumentsValue);
                break;
            case InputArgumentParser.EXEC_DECRYPT:
                decrypt(argumentsValue);
                break;
            case InputArgumentParser.EXEC_BRUTEFORCE:
                bruteForce(argumentsValue);
                break;
            case InputArgumentParser.EXEC_STAT_DECRYPT:
                statisticalDecryption(argumentsValue);
                break;
        }
    }

    private void encrypt(String[] argEncrypt) {
        int key = Integer.parseInt(argEncrypt[1]);
        String sourceFileForEncryptName = argEncrypt[2];
        String destFileName = argEncrypt[3];
        encryptSrcToDstWithKey(key, sourceFileForEncryptName, destFileName);
    }

    private static void encryptSrcToDstWithKey(int key, String src, String dst) {
        CaesarCipher caesarCipher = new CaesarCipher(key);

        try (FileReader reader = new FileReader(src);
             BufferedReader bufferedReader = new BufferedReader(reader);
             FileWriter writer = new FileWriter(dst);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            caesarCipher.doEncryptedWriterFromReader(bufferedReader, bufferedWriter);

        } catch (IOException ex) {
            throw new ExecException(ExecErrorsMessage.ERROR_FILE, ex);
        }
    }

    private void decrypt(String[] argDecrypt) {
        String keyEncrypt = argDecrypt[1];
        String keyDecrypt;

        if (keyEncrypt.startsWith("-")) {
            keyDecrypt = keyEncrypt.substring(1);
        } else {
            keyDecrypt = "-" + keyEncrypt;
        }

        argDecrypt[1] = keyDecrypt;
        encrypt(argDecrypt);
    }

    private void bruteForce(String[] argBruteForce) {
        String sourceFileForDecryptName = argBruteForce[1];
        String representativeFileName = argBruteForce[2];
        String destFileName = argBruteForce[3];

        Set<String> beginOfWordsInRepresentativeFileSet = new HashSet<>();
        try (FileReader readerRepresentativeFile = new FileReader(representativeFileName);
             BufferedReader bufferedReaderRepresentativeFile = new BufferedReader(readerRepresentativeFile)) {
            beginOfWordsInRepresentativeFileSet = getAllBeginOfWords(bufferedReaderRepresentativeFile);
        } catch (IOException ex) {
            throw new ExecException(ExecErrorsMessage.ERROR_FILE, ex);
        }

        int key = 0;
        int countIdenticalBeginOfWordsMax = 0;
        int BestKeyForIdenticalBeginOfWords = 0;

        while (key < CaesarCipher.size()) {
            encryptSrcToDstWithKey(key, sourceFileForDecryptName, destFileName);

            try (FileReader temporaryBruteForceFile = new FileReader(destFileName);
                 BufferedReader temporaryBruteForceBuff = new BufferedReader(temporaryBruteForceFile)) {

                Set<String> beginOfWordsInTemporaryBruteForceFileSet = getAllBeginOfWords(temporaryBruteForceBuff);

                int countIdenticalBeginningOfWordsForCurrentKey =
                        getIdenticalMember(beginOfWordsInRepresentativeFileSet, beginOfWordsInTemporaryBruteForceFileSet);

                if (countIdenticalBeginningOfWordsForCurrentKey > countIdenticalBeginOfWordsMax) {
                    countIdenticalBeginOfWordsMax = countIdenticalBeginningOfWordsForCurrentKey;
                    BestKeyForIdenticalBeginOfWords = key;
                }
            } catch (IOException ex) {
                throw new ExecException(ExecErrorsMessage.ERROR_FILE, ex);
            }
            key++;
        }
        encryptSrcToDstWithKey(BestKeyForIdenticalBeginOfWords, sourceFileForDecryptName, destFileName);
    }

    private void statisticalDecryption(String[] argStatDecrypt) {
        String sourceFileForDecryptName = argStatDecrypt[1];
        String representativeFileName = argStatDecrypt[2];
        String destFileName = argStatDecrypt[3];

        Map<Character,Integer> frequencyCharInRepresentativeFile;
        Map<Character,Integer> frequencyCharInSourceFile;

        try (FileReader readerRepresentativeFile = new FileReader(representativeFileName);
             BufferedReader bufferedReaderRepresentativeFile = new BufferedReader(readerRepresentativeFile);

             FileReader readerSourceFile = new FileReader(sourceFileForDecryptName);
             BufferedReader bufferedReaderSourceFile = new BufferedReader(readerSourceFile)

        ) {
            frequencyCharInRepresentativeFile  = getFrequencyChar(bufferedReaderRepresentativeFile);
            frequencyCharInSourceFile = getFrequencyChar(bufferedReaderSourceFile);
            int IndexMostPopularCharInRepresentativeFile =
                    CaesarCipher.getIndexChar(getMostPopularChar(frequencyCharInRepresentativeFile));
            int IndexMostPopularCharInSourceFile =
                    CaesarCipher.getIndexChar(getMostPopularChar(frequencyCharInSourceFile));
            if (IndexMostPopularCharInRepresentativeFile == -1 ||
            IndexMostPopularCharInSourceFile == -1){
                throw new ExecException(ExecErrorsMessage.ERROR_CRYPT_FILE);
            }


            int key = CaesarCipher.getIndexChar(getMostPopularChar(frequencyCharInRepresentativeFile)) -
                    CaesarCipher.getIndexChar(getMostPopularChar(frequencyCharInSourceFile));

            encryptSrcToDstWithKey(key, sourceFileForDecryptName, destFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getIdenticalMember(Set<String> oneSet, Set<String> twoSet) {
        int countIdenticalMember = 0;
        for (String s : twoSet) {
            if (oneSet.contains(s)) {
                countIdenticalMember++;
            }
        }
        return countIdenticalMember;
    }

    private Set<String> getAllBeginOfWords(BufferedReader in) throws IOException {
        Set<String> beginOfWordsSet = new HashSet<>();
        while (in.ready()) {
            String nextReaderLine = in.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(nextReaderLine, " ");

            while (stringTokenizer.hasMoreTokens()) {
                String nextWord = stringTokenizer.nextToken().toLowerCase(Locale.ROOT);
                if (nextWord.length() < AMOUNT_CHAR_IN_BEGIN_WORD) {
                    continue;
                }
                String beginNextWord = nextWord.substring(0, AMOUNT_CHAR_IN_BEGIN_WORD);
                beginOfWordsSet.add(beginNextWord);
            }
        }
        return beginOfWordsSet;
    }

    private Map<Character,Integer> getFrequencyChar(BufferedReader in){
        Map<Character,Integer> frequencyChar = new HashMap<>();
        try {
            while (in.ready()){
                Character singleChar = (char) in.read();

                if (frequencyChar.containsKey(singleChar)){
                    int amountOld = frequencyChar.remove(singleChar);
                    frequencyChar.put(singleChar, ++amountOld);
                } else{
                    frequencyChar.put(singleChar,1);
                }
            }

        }catch (IOException ex){
            throw new ExecException(ExecErrorsMessage.ERROR_READ_FILE, ex);
        }
        return frequencyChar;
    }

    private Character getMostPopularChar(Map<Character,Integer> map){
        int maxAmount = 0;
        Character result = null;

        Set<Map.Entry<Character,Integer>> entries = map.entrySet();
        for (Map.Entry<Character,Integer> charAndPopular : entries) {
            if (maxAmount < charAndPopular.getValue()){
                maxAmount = charAndPopular.getValue();
                result = charAndPopular.getKey();
            }
        }

        return result;
    }
}
