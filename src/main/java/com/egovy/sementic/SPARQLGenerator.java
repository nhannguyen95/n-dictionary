package com.egovy.sementic;

import com.egovy.utils.Utils;

public class SPARQLGenerator {
    private String prefix;
    private String query = "";
    private SearchAnalyser sa;
    public boolean b = true;
    
    public SPARQLGenerator(SearchAnalyser sa) {
        prefix = "PREFIX ont: <http://www.semanticweb.org/apple/ontologies/2017/1/ont#> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
                "PREFIX xml: <http://www.w3.org/XML/1998/namespace> " +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
        this.sa = sa;
        solve();
    }
    
    private void solve() {
        if (sa.cannotHanlde()) { b = false; return; }
        long hashCode = Utils.getHashCode(sa.getTarget());
        String sHashCode = Long.toString(hashCode);
        if (sa.askAboutSynonym) {
          query = "SELECT ?x " +
                  "WHERE { " +
                        "?word ont:id " + sHashCode + " . " +
                        "?word ont:synonyme ?x . " +
                        "}";
        } else if (sa.askAboutAntonym) {
            query = "SELECT ?x " +
                  "WHERE { " +
                        "?word ont:id " + sHashCode + " . " +
                        "?word ont:antonyme ?x . " +
                        "}";
        } else if (sa.askAboutWordClass) {
            query = "SELECT ?x " +
                    "WHERE { " +
                            "?word ont:id " + sHashCode + " . " +
                            "?word ont:type ?x . " +
                     "}";
        } else if (sa.askAboutNounForm) {
            query = "SELECT ?x " +
                    "WHERE { " +
                               "?word ont:id "+ sHashCode + "  . " +
                               "?word ont:cor_noun ?x . " +
                            "}";
        } else if (sa.askAboutVerbForm) {
            query = "SELECT ?x " +
                    "WHERE { " +
                               "?word ont:id "+ sHashCode + "  . " +
                               "?word ont:cor_verb ?x . " +
                            "}";
        } else if (sa.askAboutAdjForm) {
            query = "SELECT ?x " +
                    "WHERE { " +
                               "?word ont:id "+ sHashCode + "  . " +
                               "?word ont:cor_adj ?x . " +
                            "}";
        } else if (sa.askAboutAdvForm) {
            query = "SELECT ?x " +
                    "WHERE { " +
                               "?word ont:id "+ sHashCode + "  . " +
                               "?word ont:cor_adv ?x . " +
                            "}";
        } else if (sa.askAboutExample) {
            if (sa.askAboutPast) {
                query = "SELECT ?x " +
                        "WHERE { " +
                                "?word ont:has ?usage . " +
                                "?usage ont:simple_past ?x . " +
                                "?usage ont:id ?id . " +
                                "FILTER (?id = "+sHashCode+") . " +
                "}";
            } else if (sa.askAboutPresent) {
                query = "SELECT ?x " +
                        "WHERE { " +
                                "?word ont:has ?usage . " +
                                "?usage ont:present ?x . " +
                                "?usage ont:id ?id . " +
                                "FILTER (?id = "+sHashCode+") . " +
                "}";
            } else if (sa.askAboutFuture) {
                query = "SELECT ?x " +
                        "WHERE { " +
                                "?word ont:has ?usage . " +
                                "?usage ont:simple_future ?x . " +
                                "?usage ont:id ?id . " +
                                "FILTER (?id = "+sHashCode+") . " +
                "}";
            } else if (sa.askAboutGeneral) {
                query = "SELECT ?x " +
                        "WHERE { " +
                                "?word ont:has ?usage . " +
                                "?usage ont:general ?x . " +
                                "?usage ont:id ?id . " +
                                "FILTER (?id = "+sHashCode+") . " +
                "}";
            }
        }
    }
    
    public String getQuery() {
        return prefix + query;
    }
}
