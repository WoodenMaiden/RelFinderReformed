package fr.ird.runtime.rdf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RDFObject extends RDFSubject {

    public RDFObject(String s) {
        super(s);
    }

    public boolean isData() {
        String uriRegex = "^(((ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*))?(\\/?)(\\{?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+\\~\\{\\}\\&%\\$#_]*)?(\\}?)(\\/?)$";
        final Pattern pattern = Pattern.compile(uriRegex);
        final Matcher matcher = pattern.matcher(this.value());
        return !matcher.find();
    }
}
