package fr.ird.runtime.rdf;

public class RDFTriple {

    private final RDFSubject subject;
    private final RDFPredicate predicate;
    private final RDFObject object;

    public RDFTriple(RDFSubject subject, RDFPredicate predicate, RDFObject object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public RDFSubject getSubject() {
        return subject;
    }

    public RDFPredicate getPredicate() {
        return predicate;
    }

    public RDFObject getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "(" +
                subject +
                ", " + predicate +
                ", " + object +
                ')';
    }
}
