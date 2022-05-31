package com.javarush.edidenko.cryptoanalyser.argument;

public class WrongArgumentsException extends RuntimeException {

    public WrongArgumentsException() {
    }

    public WrongArgumentsException(ParserErrorsMessage wrongCommand) {
        super(wrongCommand.Message);
    }

    public WrongArgumentsException(ParserErrorsMessage wrongCommand, Throwable cause) {
        super(wrongCommand.Message,cause);
    }

    public WrongArgumentsException(String message) {
        super(message);
    }

    public WrongArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }
}