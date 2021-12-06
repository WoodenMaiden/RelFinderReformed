package fr.ird.runtime.rdf;

public class RDFSubject {
    private final String value;

    public RDFSubject(String s) {
        value = s;
    }

    public String value() {
        return value;
    }
}
