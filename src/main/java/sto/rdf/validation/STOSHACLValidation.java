package sto.rdf.validation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.util.FileUtils;
import org.topbraid.shacl.util.ModelPrinter;
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
		STOSHACLValidation validation = new STOSHACLValidation();

		validation.runValidation();
		//		validation.runSmallValidation();
		//		validation.runMinimalSchemaValidation();
	}


	private void runMinimalSchemaValidation() {
		Model dataModel = JenaUtil.createMemoryModel();
		//dataModel.read("C:/Users/sbader/git/StandardOntology/sto.ttl", FileUtils.langTurtle);
		dataModel.read("C:/Users/sbader/git/StandardOntology/concerns.ttl", FileUtils.langTurtle);

		Model shapeModel = JenaUtil.createMemoryModel();
		shapeModel.read(ConfigManager.getSmallStandardShape(), FileUtils.langTurtle);


		// Perform the validation of everything, using the data model
		// also as the shapes model - you may have them separated
		Resource report = ValidationUtil.validateModel(dataModel, shapeModel, true);

		// Print violations
		System.out.println(ModelPrinter.get().print(report.getModel()));
	}


	private void runSmallValidation() {
		Model dataModel = JenaUtil.createMemoryModel();
		dataModel.read("C:/Users/sbader/git/StandardOntology/sto.ttl", FileUtils.langTurtle);

		Model shapeModel = JenaUtil.createMemoryModel();
		shapeModel.read(ConfigManager.getLabelAndCommentShape(), FileUtils.langTurtle);


		// Perform the validation of everything, using the data model
		// also as the shapes model - you may have them separated
		Resource report = ValidationUtil.validateModel(dataModel, shapeModel, true);

		// Print violations
		System.out.println(ModelPrinter.get().print(report.getModel()));

	}


	public void runValidation() {
		try {
			String data = "C:/Users/sbader/git/StandardOntology/sto.ttl";
			String shape = ConfigManager.getFullSchemaShape();
			Model dataModel = JenaUtil.createDefaultModel();
			dataModel.read(data);
			

			dataModel.read("C:/Users/sbader/git/StandardOntology/concerns.ttl");
			dataModel.read("C:/Users/sbader/git/StandardOntology/frameworks.ttl");
			

			Model shapeModel = JenaUtil.createMemoryModel();
//			shapeModel.read(ConfigManager.getLabelAndCommentShape(), FileUtils.langTurtle);
			shapeModel.read(ConfigManager.getSmallSchemaShapes(), FileUtils.langTurtle);


			// Perform the validation of everything, using the data model
			// also as the shapes model - you may have them separated
			Resource reportResource = ValidationUtil.validateModel(dataModel, shapeModel, true);
			boolean conforms  = reportResource.getProperty(SH.conforms).getBoolean();
			if (!conforms) {
				String report = ModelPrinter.get().print(reportResource.getModel());
				File reportFile = new File("output/ValidationReport.ttl");
				reportFile.createNewFile();     
				OutputStream reportOutputStream = new FileOutputStream(reportFile);
				RDFDataMgr.write(reportOutputStream, reportResource.getModel(), RDFFormat.TURTLE);
				System.out.println("Report generated in: " + reportFile.getAbsolutePath());
			} else {
				System.out.println("No validations detected!");
			}
				
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
	}
}