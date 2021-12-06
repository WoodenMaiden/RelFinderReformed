package fr.ird.runtime.rdf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentSkipListSet;

public class RDFGraph {
    final private Set<RDFTriple> collection;

    public RDFGraph(String path) throws IOException {
        File stockedGraph = new File(path);
        if (!stockedGraph.exists() || !stockedGraph.canRead()) throw new IOException("Can't read in file " + stockedGraph.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(stockedGraph));

        String fileContent = "";
        String str;

        while ((str = reader.readLine()) != null) {
            fileContent += str;
        }

        JSONArray bindings = new JSONObject(fileContent).getJSONArray("bindings");

        this.collection = new ConcurrentSkipListSet<>(new Comparator<RDFTriple>() {
            @Override
            public int compare(RDFTriple t1, RDFTriple t2) {
                return t1.getSubject().value().compareTo(t2.getSubject().value())
                       + t1.getObject().value().compareTo(t2.getObject().value());
            }
        });

        for (int i = 0; i < bindings.length(); ++i) {
            JSONObject obj = bindings.getJSONObject(i);
            this.collection.add(new RDFTriple(new RDFSubject(obj.getJSONObject("s").getString("value")),
                                              new RDFPredicate(obj.getJSONObject("s").getString("value")),
                                              new RDFObject(obj.getJSONObject("s").getString("value"))));
        }
    }

    public RDFTriple[] getTriplesOfSubject(RDFSubject s) {
        StringJoiner joint = new StringJoiner("|");
        for (RDFTriple t : collection) {
            if (t.getSubject().equals(s)) joint.add(t.toString().substring(1,t.toString().length()-1));
        }

        String[] str = joint.toString().split("|");
        RDFTriple[] triplets = new  RDFTriple[str.length];
        int i = 0;

        for (String triple : str) {
            String[] array = triple.split(",");
            triplets[i] = new RDFTriple(new RDFSubject(array[0]),
                    new RDFPredicate(array[1]),
                    new RDFObject(array[2]));

            ++i;
        }

        return triplets;
    }

    public RDFTriple[] getEntitiesOfSubject(RDFSubject s) {
        RDFTriple[] triples = this.getTriplesOfSubject(s);

        int size = 0;
        for (int i = 0; i < triples.length; i++) {
            if(triples[i].getObject().isData()) continue;
            ++size;
        }

        RDFTriple[] validTriples = new RDFTriple [size];
        size = 0;

        for (int i = 0; i < triples.length; i++) {
            if(triples[i].getObject().isData()) continue;
            validTriples[size] = triples[i];
            ++size;
        }
        return validTriples;
    }

    @Override
    public String toString() {
        String str = "[\n";
        for (RDFTriple t : this.collection) {
            str = '(' + t.getSubject().value() + ", "
                      + t.getPredicate().value() + ", "
                      + t.getObject().value() + ")\n";
        }
        return str + "\n]";
    }
}
