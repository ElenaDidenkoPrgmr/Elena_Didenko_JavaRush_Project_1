package com.javarush.edidenko.cryptoanalyser.execution;

public enum ExecErrorsMessage {
    ERROR_FILE("File processing error"),
    ERROR_READ_FILE("File cannot be read"),
    ERROR_CRYPT_FILE("Unable to encrypt file ");

    public final String Message;

    ExecErrorsMessage(String message) {
        Message = message;
    }
}
