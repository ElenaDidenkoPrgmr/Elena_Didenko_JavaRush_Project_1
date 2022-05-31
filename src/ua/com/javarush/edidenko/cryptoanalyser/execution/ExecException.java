package com.javarush.edidenko.cryptoanalyser.execution;

public class ExecException extends RuntimeException {

    public ExecException() {
    }

    public ExecException(ExecErrorsMessage wrongCommand) {
        super(wrongCommand.Message);
    }

    public ExecException(ExecErrorsMessage wrongCommand, Throwable cause) {
        super(wrongCommand.Message, cause);
    }

    public ExecException(String message) {
       // message = message + description;
        super(message);
    }

    public ExecException(String message, Throwable cause) {
        super(message, cause);
    }
}
