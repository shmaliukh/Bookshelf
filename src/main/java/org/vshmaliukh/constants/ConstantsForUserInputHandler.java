package org.vshmaliukh.constants;

import java.util.regex.Pattern;

public final class ConstantsForUserInputHandler {

    private ConstantsForUserInputHandler(){}

    public static final Pattern PATTERN_FOR_IS_BORROWED = Pattern.compile("[yn]", Pattern.CASE_INSENSITIVE);
    public static final Pattern PATTERN_FOR_PAGES = Pattern.compile("^[1-9]+[0-9]*$");
    public static final Pattern PATTERN_FOR_NAME = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_AUTHOR = Pattern.compile("^(.{1,100}$)");
    //TODO create another regular expression for 'author' input
}
