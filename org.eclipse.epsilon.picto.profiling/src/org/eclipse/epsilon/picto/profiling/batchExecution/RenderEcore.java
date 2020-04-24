package org.eclipse.epsilon.picto.profiling.batchExecution;

import java.io.File;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;

public class RenderEcore {

	public static void main(String[] args) throws Exception {
		String modelsLocation = "models/ecore/%s";
		String[] models = { "comps.ecore", "Ecore.ecore" };
		String metamodel = "models/ecore/Ecore.ecore";
		String transformationFile = "ecore2vis-standalone/ecore.egx";

		PrintStream profilingStream = new PrintStream(new File("batchRenderEcore.csv"));
		profilingStream.println("Model,BatchTimeMillis");

		int numReps = 15;
		for (int i = 0; i < numReps; i++) {
			// render every model in the list
			for (String modelName : models) {
				long start = System.currentTimeMillis();
				ModelRenderer.render(modelsLocation, transformationFile, modelName, metamodel);
				long end = System.currentTimeMillis();
				profilingStream.println(String.format("%s,%d", modelName, end - start));
			}
			// then, delete the output directory for a fresh next generation
			FileUtils.deleteDirectory(new File("gen"));
		}
		profilingStream.close();
		System.out.println("Done");
	}
}
