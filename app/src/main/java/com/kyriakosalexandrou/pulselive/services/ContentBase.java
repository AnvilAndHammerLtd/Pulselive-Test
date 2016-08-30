
package com.kyriakosalexandrou.pulselive.services;

public abstract class ContentBase {
    protected static String RESPONSE_ID_TEXT = "id";
    protected static String RESPONSE_TITLE_TEXT = "title";
    protected static String RESPONSE_SUBTITLE_TEXT = "subtitle";
    protected static String RESPONSE_DATE_TEXT = "date";

    protected abstract void convertJsonToPojo(String jsonResult);
}