package fr.ird.runtime.rdf;

public class RDFPredicate {
    private final String type;

    RDFPredicate(String t){
        type = t;
    }

    public String value() {
        return type;
    }
}
