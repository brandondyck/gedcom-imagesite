/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.dyck.brandon.gedcomsite;

import java.io.File;
import java.io.IOException;
import org.folg.gedcom.model.Gedcom;
import org.folg.gedcom.parser.ModelParser;
import org.xml.sax.SAXParseException;

/**
 *
 * @author brandon
 */
public class Main {
    public static void main(String[] args) {
	System.out.println("Hello gedcomsite!");
	ModelParser parser = new ModelParser();
	try {
	Gedcom model = parser.parseGedcom(new File("test_data/sample_large.ged"));
	HTMLGenerator.fromModel(model);
	} catch (SAXParseException spe) {
	    System.err.println("could not parse model: " + spe.getMessage());
	} catch (IOException ioe) {
	    System.err.println("could not open file: " + ioe.getMessage());
	}
	System.out.println("Goodbye gedcomsite!");
    }
}
