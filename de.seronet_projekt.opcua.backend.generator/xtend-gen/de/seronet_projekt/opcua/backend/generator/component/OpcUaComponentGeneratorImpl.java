package de.seronet_projekt.opcua.backend.generator.component;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import de.seronet_projekt.opcua.backend.generator.component.OpcUaComponentGenHelpers;
import de.seronet_projekt.opcua.backend.generator.component.OpcUaComponentPortFactory;
import java.util.HashSet;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.ecore.component.componentDefinition.ComponentDefinition;
import org.ecore.service.communicationObject.CommObjectsRepository;
import org.xtend.smartsoft.generator.CopyrightHelpers;

@SuppressWarnings("all")
public class OpcUaComponentGeneratorImpl extends AbstractGenerator {
  @Inject
  @Extension
  private CopyrightHelpers _copyrightHelpers;
  
  @Inject
  @Extension
  private OpcUaComponentGenHelpers _opcUaComponentGenHelpers;
  
  @Inject
  @Extension
  private OpcUaComponentPortFactory _opcUaComponentPortFactory;
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    Iterable<ComponentDefinition> _filter = Iterables.<ComponentDefinition>filter(IteratorExtensions.<EObject>toIterable(input.getAllContents()), ComponentDefinition.class);
    for (final ComponentDefinition comp : _filter) {
      {
        fsa.generateFile("info.txt", this.compileToolchainVersionFile(comp));
        fsa.generateFile("OpcUaBackend.cmake", this.compileCMakeFile(comp));
        fsa.generateFile(this._opcUaComponentPortFactory.getOpcUaBackendPortFactoryHeaderFilename(comp), this._opcUaComponentPortFactory.compileOpcUaBackendPortFactoryHeader(comp));
        fsa.generateFile(this._opcUaComponentPortFactory.getOpcUaBackendPortFactorySourceFilename(comp), this._opcUaComponentPortFactory.compileOpcUaBackendPortFactorySource(comp));
      }
    }
  }
  
  public CharSequence compileToolchainVersionFile(final ComponentDefinition comp) {
    StringConcatenation _builder = new StringConcatenation();
    String _toolchainVersionFileString = this._copyrightHelpers.getToolchainVersionFileString();
    _builder.append(_toolchainVersionFileString);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence compileCMakeFile(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CMAKE_MINIMUM_REQUIRED(VERSION 3.5)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("FIND_PACKAGE(Open62541Cpp QUIET)");
    _builder.newLine();
    _builder.append("SET(SmartSoft_CD_API_DIR $ENV{SMART_ROOT_ACE}/modules)");
    _builder.newLine();
    _builder.append("FIND_PACKAGE(SeRoNetSDK QUIET)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("IF(SeRoNetSDK_FOUND)");
    _builder.newLine();
    {
      HashSet<CommObjectsRepository> _allRelatedRepos = this._opcUaComponentGenHelpers.getAllRelatedRepos(component);
      for(final CommObjectsRepository repo : _allRelatedRepos) {
        _builder.append("\t");
        _builder.append("FIND_PACKAGE(");
        String _name = repo.getName();
        _builder.append(_name, "\t");
        _builder.append("OpcUa PATHS $ENV{SMART_ROOT_ACE}/modules)");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("SET(CMAKE_CXX_STANDARD 14)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("INCLUDE_DIRECTORIES(${CMAKE_CURRENT_LIST_DIR})");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("SET(OPC_UA_BACKEND_SRCS \"${CMAKE_CURRENT_LIST_DIR}/");
    String _opcUaBackendPortFactorySourceFilename = this._opcUaComponentPortFactory.getOpcUaBackendPortFactorySourceFilename(component);
    _builder.append(_opcUaBackendPortFactorySourceFilename, "\t");
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
    _builder.append("ENDIF(SeRoNetSDK_FOUND)");
    _builder.newLine();
    return _builder;
  }
}
