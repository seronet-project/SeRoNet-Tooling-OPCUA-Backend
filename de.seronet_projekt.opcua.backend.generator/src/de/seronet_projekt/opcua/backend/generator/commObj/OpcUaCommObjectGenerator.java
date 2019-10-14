package de.seronet_projekt.opcua.backend.generator.commObj;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.xtend.smartsoft.generator.GeneratorHelper;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.seronet_projekt.opcua.backend.generator.ExtendedOutputConfigurationProvider;

public class OpcUaCommObjectGenerator extends AbstractGenerator {

	@Override
	public void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// use google Guice to resolve all injected Xtend classes!
		Injector injector = Guice.createInjector(new OpcUaCommObjectGeneratorModule());
		OpcUaCommObjectGeneratorImpl gen = injector.getInstance(OpcUaCommObjectGeneratorImpl.class);

		// use the generator-helper class
		GeneratorHelper genHelper = new GeneratorHelper(injector,resource);
		
		// create the opcua folder (if not already created)
		genHelper.createFolder(ExtendedOutputConfigurationProvider.OPC_UA_OUTPUT);
		
		// clean-up the "opcua/src-gen" directory
		genHelper.invokeDirectoryCleaner(IFileSystemAccess2.DEFAULT_OUTPUT);
		
		// execute generator using a configured FileSystemAccess
		gen.doGenerate(resource, genHelper.getConfiguredFileSystemAccess(), context);
		
		// refresh the source-folder (and its subfolders down to depth 3) for making changes visible
		genHelper.refreshFolder(ExtendedOutputConfigurationProvider.OPC_UA_OUTPUT, 3);
	}

}
