package com.javarush.edidenko.cryptoanalyser.argument;

public enum ParserErrorsMessage {
    WRONG_COMMAND("Wrong command: "),
    KEY_NOT_INT("Key must be a number, invalid key: "),
    WRONG_COMMAND_FULL("Invalid or missing command. Caesar cypher command: \n " +
            "1) Encrypting commands: " + InputCommandsAndKits.ENCRYPT_COMMAND +
            "\n 2) Decrypting commands: " + InputCommandsAndKits.DECRYPT_COMMAND +
            "\n 3) Brute forcing commands: " + InputCommandsAndKits.BRUTE_FORCE_COMMAND +
            "\n 4) Statistical encrypting commands: " + InputCommandsAndKits.STATISTICAL_DECRYPTION_COMMAND),
    FILES_OVERLAP("Source and destination files cannot be the same"),
    WRONG_SOURCE("Source file is wrong"),
    INVALID_SOURCE("Invalid file path");


    public final String Message;

    ParserErrorsMessage(String message) {
        Message = message;
    }
}
