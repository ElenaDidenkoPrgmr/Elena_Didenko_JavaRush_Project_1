package com.javarush.edidenko.cryptoanalyser.argument;

import java.util.ArrayList;
import java.util.List;

public class InputCommandsAndKits {
    public static final List<String> ENCRYPT_COMMAND = new ArrayList<>(List.of("encrypt", "en", "e"));
    public static final List<String> DECRYPT_COMMAND = new ArrayList<>(List.of("decrypt", "de", "d"));
    public static final List<String> BRUTE_FORCE_COMMAND = (new ArrayList<>(List.of("bruteforce", "brute", "bf")));
    public static final List<String> STATISTICAL_DECRYPTION_COMMAND = (new ArrayList<>(List.of("statdecrypt", "stat")));

    private static final List<String> KEY_COMMAND = new ArrayList<>(List.of("-key", "-k"));
    private static final List<String> SOURCE_FILE_COMMAND = new ArrayList<>(List.of("-source", "-src"));
    private static final List<String> DESTINATION_FILE_COMMAND = new ArrayList<>(List.of("-destination", "-dst"));
    private static final List<String> REPRESENTATIVE_FILE_COMMAND = new ArrayList<>(List.of("-representative", "-rep"));
    
    public static final List<List<String>> KIT_FROM_ENCRYPT = getKitForEncrypt();
    public static final List<List<String>> KIT_FROM_DECRYPT = getKitForDecrypt();
    public static final List<List<String>> KIT_FROM_BRUTE_FORCE = getKitForBruteForce();
    public static final List<List<String>> KIT_FROM_STATISTICAL_DECRYPTION = getKitForStatisticalDecryption();

    private static List<List<String>> getKitForEncrypt(){
        List<List<String>> kitForEncrypt = new ArrayList<>();

        kitForEncrypt.add(ENCRYPT_COMMAND);
        kitForEncrypt.add(KEY_COMMAND);
        kitForEncrypt.add(SOURCE_FILE_COMMAND);
        kitForEncrypt.add(DESTINATION_FILE_COMMAND);

        return kitForEncrypt;
    }

    private static List<List<String>> getKitForDecrypt(){
        List<List<String>> kitForDecrypt = new ArrayList<>();

        kitForDecrypt.add(DECRYPT_COMMAND);
        kitForDecrypt.add(KEY_COMMAND);
        kitForDecrypt.add(SOURCE_FILE_COMMAND);
        kitForDecrypt.add(DESTINATION_FILE_COMMAND);

        return kitForDecrypt;
    }

    private static List<List<String>> getKitForBruteForce(){
        List<List<String>> kitForBruteForce = new ArrayList<>();

        kitForBruteForce.add(BRUTE_FORCE_COMMAND);
        kitForBruteForce.add(SOURCE_FILE_COMMAND);
        kitForBruteForce.add(REPRESENTATIVE_FILE_COMMAND);
        kitForBruteForce.add(DESTINATION_FILE_COMMAND);

        return kitForBruteForce;
    }

    private static List<List<String>> getKitForStatisticalDecryption(){
        List<List<String>> kitForStatisticalDecryption = new ArrayList<>();

        kitForStatisticalDecryption.add(STATISTICAL_DECRYPTION_COMMAND);
        kitForStatisticalDecryption.add(SOURCE_FILE_COMMAND);
        kitForStatisticalDecryption.add(REPRESENTATIVE_FILE_COMMAND);
        kitForStatisticalDecryption.add(DESTINATION_FILE_COMMAND);

        return kitForStatisticalDecryption;
    }
}

