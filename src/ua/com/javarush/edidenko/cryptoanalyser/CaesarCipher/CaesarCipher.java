package com.javarush.edidenko.cryptoanalyser.CaesarCipher;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З',
            'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            '.', ',', '«', '»', '(', ')', '"', '\'', ':', ';', '-', '!', ' ', '?', '\n'};

    public static int size(){
        return ALPHABET.length;
    }


    private final Map<Character, Character> MapForAlphabetSwitcher;

    public CaesarCipher(int key) {
        this.MapForAlphabetSwitcher = getMapForAlphabetSwitcher(key);
    }

    public void doEncryptedWriterFromReader(Reader inStream, Writer outStream) throws IOException {
        while (inStream.ready()) {
            char OneCharacterFromInStream = (char) inStream.read();

            if (MapForAlphabetSwitcher.containsKey(OneCharacterFromInStream)) {
                char encryptCharacterToOutStream = MapForAlphabetSwitcher.get(OneCharacterFromInStream);
                outStream.write(encryptCharacterToOutStream);
            }
        }
    }

    public static int getIndexChar(Character inChar) {
        int len = ALPHABET.length;
        int i = 0;
        while (i < len) {
            if (ALPHABET[i] == inChar) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }


    private char[] getEncryptedAlphabet(int key) {
        int lengthALPHABET = ALPHABET.length;

        key = key % lengthALPHABET;
        if (key < 0) {
            key = lengthALPHABET + key;
        }

        char[] result = new char[lengthALPHABET];
        for (int i = 0; i < lengthALPHABET; i++) {
            result[i] = ALPHABET[(i + key) % lengthALPHABET];
        }

        return result;
    }

    private Map<Character, Character> getMapForAlphabetSwitcher(int key) {
        char[] CaesarAlphabet = getEncryptedAlphabet(key);
        Map<Character, Character> result = new HashMap<>();

        for (int i = 0; i < ALPHABET.length; i++) {
            result.put(ALPHABET[i], CaesarAlphabet[i]);
        }
        return result;
    }
}

