package de.seronet_projekt.opcua.backend.generator.component.ext;

import java.util.Collection;
import java.util.HashSet;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.ecore.component.componentDefinition.ComponentDefinition;
import org.ecore.component.componentDefinition.ComponentDefinitionModelUtility;
import org.ecore.service.communicationObject.CommObjectsRepository;
import org.ecore.service.communicationObject.CommunicationObject;
import org.xtend.smartsoft.generator.component.ComponentGeneratorExtension;

@SuppressWarnings("all")
public class OpcUaComponentGeneratorExtension implements ComponentGeneratorExtension {
  @Override
  public String getExtensionName(final ComponentDefinition component) {
    return "OpcUaBackendComponentGeneratorExtension";
  }
  
  @Override
  public CharSequence getCMakeIncludes(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("GET_FILENAME_COMPONENT(OPC_UA_BACKEND_DIR \"${PROJECT_SOURCE_DIR}/../opcua-backend\" REALPATH)");
    _builder.newLine();
    _builder.append("IF(EXISTS ${OPC_UA_BACKEND_DIR})");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("INCLUDE(\"${OPC_UA_BACKEND_DIR}/src-gen/OpcUaBackend.cmake\")");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("LIST(APPEND FURTHER_SRCS ${OPC_UA_BACKEND_SRCS})");
    _builder.newLine();
    _builder.append("ENDIF(EXISTS ${OPC_UA_BACKEND_DIR})");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence getCMakeTargetConfiguration(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("IF(SeRoNetSDK_FOUND)");
    _builder.newLine();
    _builder.append("#TARGET_LINK_LIBRARIES(${PROJECT_NAME} SeRoNetSDK::SeRoNetSDK)");
    _builder.newLine();
    {
      HashSet<CommObjectsRepository> _allRelatedRepos = this.getAllRelatedRepos(component);
      for(final CommObjectsRepository repo : _allRelatedRepos) {
        _builder.append("TARGET_LINK_LIBRARIES(${PROJECT_NAME} ");
        String _name = repo.getName();
        _builder.append(_name);
        _builder.append("OpcUa)");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("ENDIF(SeRoNetSDK_FOUND)");
    _builder.newLine();
    return _builder;
  }
  
  public HashSet<CommObjectsRepository> getAllRelatedRepos(final ComponentDefinition component) {
    final HashSet<CommObjectsRepository> repos = new HashSet<CommObjectsRepository>();
    Collection<CommunicationObject> _allCommObjects = ComponentDefinitionModelUtility.getAllCommObjects(component);
    for (final CommunicationObject obj : _allCommObjects) {
      EObject _eContainer = obj.eContainer();
      repos.add(((CommObjectsRepository) _eContainer));
    }
    return repos;
  }
}
