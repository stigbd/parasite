/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.deichman.ls.repository;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.deichman.ls.preference.Preference;
import org.apache.commons.configuration.ConfigurationException;

/**
 *
 * @author sbd
 */
public class RepositoryInMemory implements Repository {

    private final Model inMemoryModel = ModelFactory.createDefaultModel();

    @Override
    public Model retrieveWorkById(String uri) {
        return retrieveResource(uri);
    }

    @Override
    public Model retrieveManifestationById(String uri) {
        return retrieveResource(uri);
    }

    @Override
    public Model retrieveItemById(String uri) {
        return retrieveResource(uri);
    }

    @Override
    public Model createWork(Model model) {
        inMemoryModel.add(model);
        String debug = modelToString(inMemoryModel);
        return model;
    }

    @Override
    public void deleteWork(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Model createManifestation(Model model) {
        inMemoryModel.add(model);
        String debug = modelToString(inMemoryModel);

        return model;
    }

    @Override
    public void deleteManifestation(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Model createItem(Model model) {
        inMemoryModel.add(model);
        String debug = modelToString(inMemoryModel);
        return model;
    }

    @Override
    public void deleteItem(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Model listWorks() {

        String queryString = null;
        try {
            queryString = Preference.listWorks();
        } catch (ConfigurationException ex) {
            Logger.getLogger(RepositoryInMemory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return runConstructQuery(queryString);
    }

    @Override
    public Model listManifestations() {
        String queryString = null;
        try {
            queryString = Preference.listManifestations();
        } catch (ConfigurationException ex) {
            Logger.getLogger(RepositoryInMemory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return runConstructQuery(queryString);
    }

    @Override
    public Model listItems() {
        String queryString = "PREFIX frbr: <http://purl.org/vocab/frbr/core#>"
                + "DESCRIBE ?s ?p ?o\n"
                + "WHERE\n"
                + "{\n"
                + " ?s a frbr:Item ;"
                + " ?p ?o ."
                + "}";
        return runDescribeQuery(queryString);
    }

    @Override
    public Model queryModel(String queryString) {
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, inMemoryModel);
        // Very rudimentary to differentiate between select, construct and select:
        Model resultModel = null;
        if (queryString.toUpperCase().contains("SELECT")) {
            ResultSet resultSet;
            resultSet = qexec.execSelect();
            resultModel = ResultSetFormatter.toModel(resultSet);
        } else if (queryString.toUpperCase().contains("CONSTRUCT")) {
            resultModel = qexec.execConstruct();
        } else if (queryString.toUpperCase().contains("DESCRIBE")) {
            resultModel = qexec.execDescribe();
        } else {
            resultModel = qexec.execConstruct();
        }

        qexec.close();

        return resultModel;
    }

    @Override
    public Model retrieveItemByManifestationId(String uri) {
        String queryString = "PREFIX frbr: <http://purl.org/vocab/frbr/core#>"
                + "DESCRIBE ?s ?p ?o\n"
                + "WHERE\n"
                + "{\n"
                + " ?s a frbr:Item ."
                + " ?s frbr:isItemOf \"" + uri + "\"."
                + "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, inMemoryModel);
        Model resultModel = qexec.execDescribe();
        qexec.close();

        return resultModel;
    }

    private Model retrieveResource(String uri) {
        String queryString = "DESCRIBE <" + uri + ">";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, inMemoryModel);
        Model resultModel = qexec.execDescribe();
        qexec.close();

        return resultModel;
    }

    private Model runDescribeQuery(String queryString) {
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, inMemoryModel);
        Model resultModel = qexec.execDescribe();
        String debug = modelToString(resultModel);
        qexec.close();
        return resultModel;
    }

    private Model runConstructQuery(String queryString) {
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, inMemoryModel);
        Model resultModel = qexec.execConstruct();
        String debug = modelToString(resultModel);
        qexec.close();
        return resultModel;
    }

    private String modelToString(Model m) {
        String syntax = "TURTLE"; // also try "N-TRIPLE" and "TURTLE"
        StringWriter out = new StringWriter();
        m.write(out, syntax);
        return out.toString();
    }

}
