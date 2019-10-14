package de.seronet_projekt.opcua.backend.generator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

public class ExtendedOutputConfigurationProvider implements IOutputConfigurationProvider {
	public final static String OPC_UA_OUTPUT = "OPC_UA_OUTPUT";
	public final static String SRC_OUTPUT = "SRC_OUTPUT";
	public final static String PROJECT_ROOT_FOLDER = "PROJECT_ROOT_FOLDER";

	/**
	 * @return a set of {@link OutputConfiguration} available for the generator
	 */
	public Set<OutputConfiguration> getOutputConfigurations() {
		OutputConfiguration defaultOutput = new OutputConfiguration(
				IFileSystemAccess.DEFAULT_OUTPUT);
		defaultOutput.setDescription("Output Folder");
		defaultOutput.setOutputDirectory("./opcua-backend/src-gen");
		defaultOutput.setOverrideExistingResources(true);
		defaultOutput.setCreateOutputDirectory(true);
		defaultOutput.setCleanUpDerivedResources(true);
		defaultOutput.setSetDerivedProperty(true);
		
		OutputConfiguration opcuaOutput = new OutputConfiguration(
				OPC_UA_OUTPUT);
		opcuaOutput.setDescription("Output Folder for Custom Code");
		opcuaOutput.setOutputDirectory("./opcua-backend");
		opcuaOutput.setOverrideExistingResources(false);
		opcuaOutput.setCreateOutputDirectory(true);
		opcuaOutput.setCleanUpDerivedResources(false);
		opcuaOutput.setSetDerivedProperty(false);
		
		OutputConfiguration srcOutput = new OutputConfiguration(
				SRC_OUTPUT);
		srcOutput.setDescription("Output Folder for Custom Code");
		srcOutput.setOutputDirectory("./opcua-backend/src");
		srcOutput.setOverrideExistingResources(false);
		srcOutput.setCreateOutputDirectory(true);
		srcOutput.setCleanUpDerivedResources(false);
		srcOutput.setSetDerivedProperty(false);
		
		OutputConfiguration rootConfigOutput = new OutputConfiguration(
				PROJECT_ROOT_FOLDER);
		rootConfigOutput.setDescription("Project Root Output Folder");
		rootConfigOutput.setOutputDirectory("./");
		rootConfigOutput.setOverrideExistingResources(false);
		rootConfigOutput.setCreateOutputDirectory(false);
		rootConfigOutput.setCleanUpDerivedResources(false);
		rootConfigOutput.setSetDerivedProperty(false);
		
		HashSet<OutputConfiguration> configurations = new HashSet<OutputConfiguration>();
		configurations.add(defaultOutput);
		configurations.add(opcuaOutput);
		configurations.add(srcOutput);
		configurations.add(rootConfigOutput);
		return configurations;
	}
}
