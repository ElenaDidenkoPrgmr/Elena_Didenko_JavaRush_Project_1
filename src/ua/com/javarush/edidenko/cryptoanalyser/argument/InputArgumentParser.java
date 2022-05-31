package com.javarush.edidenko.cryptoanalyser.argument;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputArgumentParser {

    public static final String ARGUMENT_SEPARATOR = "=";
    public static final String EXEC_ENCRYPT = "0";
    public static final String EXEC_DECRYPT = "1";
    public static final String EXEC_BRUTEFORCE = "2";
    public static final String EXEC_STAT_DECRYPT = "3";


    public String[] parseAndValidateArguments(String[] args) {
        String[] argumentValue = getValueFromSpecificKit(args);
        return validateValuesFromSpecificKit(argumentValue);
    }

    private String[] getValueFromSpecificKit(String[] args) {
        String[] value = null;
        try {

            for (String searchArgForExec : args) {
                if (InputCommandsAndKits.KIT_FROM_ENCRYPT.get(0).contains(searchArgForExec)) {
                    value = getValuesFromKit(args, InputCommandsAndKits.KIT_FROM_ENCRYPT);
                    value[0] = EXEC_ENCRYPT;
                    break;
                }

                if (InputCommandsAndKits.KIT_FROM_DECRYPT.get(0).contains(searchArgForExec)) {
                    value = getValuesFromKit(args, InputCommandsAndKits.KIT_FROM_DECRYPT);
                    value[0] = EXEC_DECRYPT;
                    break;
                }

                if (InputCommandsAndKits.KIT_FROM_BRUTE_FORCE.get(0).contains(searchArgForExec)) {
                    value = getValuesFromKit(args, InputCommandsAndKits.KIT_FROM_BRUTE_FORCE);
                    value[0] = EXEC_BRUTEFORCE;
                    break;
                }

                if (InputCommandsAndKits.KIT_FROM_STATISTICAL_DECRYPTION.get(0).contains(searchArgForExec)) {
                    value = getValuesFromKit(args, InputCommandsAndKits.KIT_FROM_STATISTICAL_DECRYPTION);
                    value[0] = EXEC_STAT_DECRYPT;
                    break;
                }
            }
        } catch (NullPointerException ex) {
            throw new WrongArgumentsException(ParserErrorsMessage.WRONG_COMMAND.Message + Arrays.toString(args),ex);
            //throw new WrongArgumentsException(ParserErrorsMessage.WRONG_COMMAND ,ex);
        }
        return value;
    }

    private String[] validateValuesFromSpecificKit(String[] argumentsValue) {
        if (argumentsValue == null) {
            throw new WrongArgumentsException(ParserErrorsMessage.WRONG_COMMAND_FULL);
        }
        String execCommandMode = argumentsValue[0];
        switch (execCommandMode) {
            case EXEC_ENCRYPT:
            case EXEC_DECRYPT:
                validateIsNumber(argumentsValue[1]);
                validateSourceFile(argumentsValue[2]);
                validateDestinationName(argumentsValue[3], argumentsValue[2]);
                break;
            case EXEC_BRUTEFORCE:
            case EXEC_STAT_DECRYPT:
                validateSourceFile(argumentsValue[1]);
                validateSourceFile(argumentsValue[2]);
                validateDestinationName(argumentsValue[1],argumentsValue[3]);
                break;
        }

        return argumentsValue;
    }

    private void validateIsNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            throw
            new WrongArgumentsException(ParserErrorsMessage.KEY_NOT_INT.Message + s,ex);
        }
    }

    private void validateSourceFile(String s) {
        Path filePath;

        try {
            filePath = Path.of(s);
        } catch (InvalidPathException ex) {
            throw new WrongArgumentsException(ParserErrorsMessage.INVALID_SOURCE);
        }

        if (!Files.isRegularFile(filePath)) {
            throw new WrongArgumentsException(ParserErrorsMessage.WRONG_SOURCE);
        }
    }

    private void validateDestinationName(String dst, String src) {
        if (src.equals(dst)) {
            throw new WrongArgumentsException(ParserErrorsMessage.FILES_OVERLAP);
        }
    }


    private String[] getValuesFromKit(String[] args, List<List<String>> CommandKit) {

        List<List<String>> checkingCommandKit = new ArrayList<>(CommandKit);
        if (checkingCommandKit.size() != args.length) {
            throw new WrongArgumentsException(ParserErrorsMessage.WRONG_COMMAND_FULL.Message+ Arrays.toString(args));
        }

        String[] valueForCommand = new String[checkingCommandKit.size()];

        for (String NextArg : args) {

            int pos = NextArg.indexOf(ARGUMENT_SEPARATOR);
            String partArgWithCommand;
            String partArgWithValue;

            if (pos != -1) {
                partArgWithCommand = NextArg.substring(0, pos);
                partArgWithValue = NextArg.substring(pos + 1);
            } else {
                partArgWithCommand = NextArg;
                partArgWithValue = NextArg;
            }

            for (int j = 0; j < CommandKit.size(); j++) {
                if (CommandKit.get(j).contains(partArgWithCommand)) {
                    valueForCommand[j] = partArgWithValue;
                    break;
                }
            }

            for (int j = 0; j < checkingCommandKit.size(); j++) {
                if (checkingCommandKit.get(j).contains(partArgWithCommand)) {
                    checkingCommandKit.remove(j);
                    break;
                }
            }
        }

        if (checkingCommandKit.isEmpty()) {
            return valueForCommand;
        } else return null;
    }
}
