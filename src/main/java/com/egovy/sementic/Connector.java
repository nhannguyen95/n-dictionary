package com.egovy.sementic;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public class Connector {
	
	private static final String OWL_PATH = "data2.owl";
	
	private static Connector instance = null; 
	
	private Model model;
	
	private Connector() { super(); }
	
	public static Connector getInstance() {
		if (instance == null) instance = new Connector();
		return instance;
	}
	
	public void connect() {
		FileManager.get().addLocatorClassLoader(Connector.class.getClassLoader());
        this.model = FileManager.get().loadModel(OWL_PATH);
	}
	
	public Literal getQueryResult(String queryString) {
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        Literal name = null;
        try {
            ResultSet results = qexec.execSelect();
            while( results.hasNext() ) {
                QuerySolution soln = results.nextSolution();
                name = soln.getLiteral("x");
            }    
        } finally {
            qexec.close();
        }
        return name;
    }
}
