package de.seronet_projekt.opcua.backend.generator;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.ecore.service.communicationObject.CommObjectsRepository;
import org.xtend.smartsoft.generator.commObj.DomainModelsGeneratorExtension;

@SuppressWarnings("all")
public class OpcUaDomainModelsExtension implements DomainModelsGeneratorExtension {
  @Override
  public String getExtensionName(final CommObjectsRepository repo) {
    String _name = repo.getName();
    return (_name + "OpcUaBackend");
  }
  
  @Override
  public CharSequence getCMakeExtension(final CommObjectsRepository repo) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("GET_FILENAME_COMPONENT(OPC_UA_BACKEND_DIR \"${PROJECT_SOURCE_DIR}/../opcua-backend\" REALPATH)");
    _builder.newLine();
    _builder.append("IF(EXISTS ${OPC_UA_BACKEND_DIR})");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ADD_SUBDIRECTORY(${OPC_UA_BACKEND_DIR}/src-gen ${CMAKE_CURRENT_BINARY_DIR}/opcua-backend)");
    _builder.newLine();
    _builder.append("ENDIF(EXISTS ${OPC_UA_BACKEND_DIR})");
    _builder.newLine();
    return _builder;
  }
}
