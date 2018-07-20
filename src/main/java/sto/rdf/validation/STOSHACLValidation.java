package sto.rdf.validation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;
import org.topbraid.spin.util.JenaUtil;

import util.ConfigManager;

/**
 * Validating the RDF data based on the STO ontology
 * @author Irlan Grangel
 * @Version = 0.1
 * @Date 20/07/2018
 */

public class STOSHACLValidation {

	public static void main(String[] args) {
    try {
      String data = ConfigManager.getInputData();
      String shape = ConfigManager.getInputShape();
      Model dataModel = JenaUtil.createDefaultModel();
      dataModel.read(data);
      Model shapeModel = JenaUtil.createDefaultModel();
      shapeModel.read(shape);
      Resource reportResource = 
          ValidationUtil.validateModel(dataModel, shapeModel, true);
      boolean conforms  = reportResource.getProperty(SH.conforms).getBoolean();
      if (!conforms) {
        String report = ConfigManager.getOutputReport();
        File reportFile = new File(report);
        reportFile.createNewFile();     
        OutputStream reportOutputStream = new FileOutputStream(reportFile);
        RDFDataMgr.write(reportOutputStream, reportResource.getModel(), RDFFormat.TURTLE);
        System.out.println("Report generated in: " + reportFile.getAbsolutePath());
      }
    } catch (Throwable t) {
    	System.out.println(t.getMessage());
    }
  }
}