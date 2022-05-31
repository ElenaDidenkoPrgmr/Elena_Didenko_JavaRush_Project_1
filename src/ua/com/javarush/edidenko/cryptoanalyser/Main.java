package com.javarush.edidenko.cryptoanalyser;

import com.javarush.edidenko.cryptoanalyser.argument.InputArgumentParser;
import com.javarush.edidenko.cryptoanalyser.argument.WrongArgumentsException;
import com.javarush.edidenko.cryptoanalyser.execution.ExecException;
import com.javarush.edidenko.cryptoanalyser.execution.execCommand;

public class Main {
    public static void main(String[] args) {
        try {
            InputArgumentParser argumentParser = new InputArgumentParser();
            String[] commandArgs = argumentParser.parseAndValidateArguments(args);
            new execCommand(commandArgs);
        } catch (WrongArgumentsException ex) {
            System.err.println(ex);

        } catch (ExecException ex) {
            System.err.println(ex);
            System.err.println("Error details: " + ex.getMessage());
        }
    }
}
