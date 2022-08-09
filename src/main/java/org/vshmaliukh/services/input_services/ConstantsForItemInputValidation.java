package org.vshmaliukh.services.input_services;

import java.util.regex.Pattern;

public class ConstantsForItemInputValidation {

    public static final Pattern PATTERN_FOR_IS_BORROWED = Pattern.compile("[yn]", Pattern.CASE_INSENSITIVE);;
    public static final Pattern PATTERN_FOR_PAGES = Pattern.compile("^[1-9]+[0-9]*$");
    public static final Pattern PATTERN_FOR_TYPE_OF_WORK_WITH_FILES = Pattern.compile("^[1-3]*$");
    public static final Pattern PATTERN_FOR_USER_NAME = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_NAME = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_AUTHOR = Pattern.compile("^(.{1,100}$)");
    public static final Pattern PATTERN_FOR_PUBLISHER = Pattern.compile("^(.{1,100}$)");
}