package de.seronet_projekt.opcua.backend.generator.commObj;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import de.seronet_projekt.opcua.backend.generator.commObj.OpcUaCommObjectSelfDescription;
import java.util.Collection;
import java.util.HashSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.ecore.base.basicAttributes.AbstractAttributeType;
import org.ecore.base.basicAttributes.AttributeDefinition;
import org.ecore.service.communicationObject.AbstractCommElement;
import org.ecore.service.communicationObject.CommElementReference;
import org.ecore.service.communicationObject.CommObjectsRepository;
import org.ecore.service.communicationObject.CommunicationObject;
import org.ecore.service.communicationObject.CommunicationObjectUtility;

@SuppressWarnings("all")
public class OpcUaCommObjectGeneratorImpl extends AbstractGenerator {
  @Inject
  @Extension
  private OpcUaCommObjectSelfDescription _opcUaCommObjectSelfDescription;
  
  @Inject
  @Extension
  private CommunicationObjectUtility _communicationObjectUtility;
  
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess2 fsa, final IGeneratorContext context) {
    Iterable<CommObjectsRepository> _filter = Iterables.<CommObjectsRepository>filter(IteratorExtensions.<EObject>toIterable(input.getAllContents()), CommObjectsRepository.class);
    for (final CommObjectsRepository repo : _filter) {
      {
        fsa.generateFile("CMakeLists.txt", this.compileCMakeFile(repo));
        String _name = repo.getName();
        String _plus = (_name + "OpcUaConfig.cmake.in");
        fsa.generateFile(_plus, this.compileCMakeConfigFile(repo));
        Iterable<CommunicationObject> _filter_1 = Iterables.<CommunicationObject>filter(repo.getElements(), CommunicationObject.class);
        for (final CommunicationObject obj : _filter_1) {
          {
            CharSequence _repoNamespace = this._opcUaCommObjectSelfDescription.getRepoNamespace(obj);
            String _plus_1 = (_repoNamespace + "OpcUa/");
            String _opcUaHeaderFileName = this._opcUaCommObjectSelfDescription.getOpcUaHeaderFileName(obj);
            String _plus_2 = (_plus_1 + _opcUaHeaderFileName);
            fsa.generateFile(_plus_2, this._opcUaCommObjectSelfDescription.generateOpcUaHeaderFile(obj));
            CharSequence _repoNamespace_1 = this._opcUaCommObjectSelfDescription.getRepoNamespace(obj);
            String _plus_3 = (_repoNamespace_1 + "OpcUa/");
            String _opcUaSourceFileName = this._opcUaCommObjectSelfDescription.getOpcUaSourceFileName(obj);
            String _plus_4 = (_plus_3 + _opcUaSourceFileName);
            fsa.generateFile(_plus_4, this._opcUaCommObjectSelfDescription.generateOpcUaSourceFile(obj));
          }
        }
      }
    }
  }
  
  public CharSequence compileCMakeFile(final CommObjectsRepository repo) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CMAKE_MINIMUM_REQUIRED(VERSION 3.5)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("PROJECT(\"");
    String _name = repo.getName();
    _builder.append(_name);
    _builder.append("OpcUa\" VERSION 1.0)");
    _builder.newLineIfNotEmpty();
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
    _builder.append("\t");
    _builder.append("SET(CMAKE_CXX_STANDARD 14)");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("FILE(GLOB OPCUA_SRCS ${PROJECT_SOURCE_DIR}/${PROJECT_NAME}/*.cc)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("FILE(GLOB OPCUA_HDRS ${PROJECT_SOURCE_DIR}/${PROJECT_NAME}/*.hh)");
    _builder.newLine();
    {
      Collection<CommObjectsRepository> _externalRepositories = this.getExternalRepositories(repo);
      for(final CommObjectsRepository ext : _externalRepositories) {
        _builder.append("\t");
        _builder.append("# find depndency ");
        String _name_1 = repo.getName();
        _builder.append(_name_1, "\t");
        _builder.append("OpcUa");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("FIND_PACKAGE(");
        String _name_2 = ext.getName();
        _builder.append(_name_2, "\t");
        _builder.append("OpcUa PATHS $ENV{SMART_ROOT_ACE}/modules)");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ADD_LIBRARY(${PROJECT_NAME} SHARED ${OPCUA_SRCS})");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("TARGET_LINK_LIBRARIES(${PROJECT_NAME} ");
    String _name_3 = repo.getName();
    _builder.append(_name_3, "\t");
    _builder.append(" SeRoNetSDK::SeRoNetSDK)");
    _builder.newLineIfNotEmpty();
    {
      Collection<CommObjectsRepository> _externalRepositories_1 = this.getExternalRepositories(repo);
      for(final CommObjectsRepository ext_1 : _externalRepositories_1) {
        _builder.append("\t");
        _builder.append("# link depndency ");
        String _name_4 = ext_1.getName();
        _builder.append(_name_4, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("TARGET_LINK_LIBRARIES(${PROJECT_NAME} ");
        String _name_5 = ext_1.getName();
        _builder.append(_name_5, "\t");
        _builder.append("OpcUa)");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("TARGET_INCLUDE_DIRECTORIES(${PROJECT_NAME} PUBLIC");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("$<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("$<INSTALL_INTERFACE:include>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append(")");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ADD_DEPENDENCIES(autoinstall ${PROJECT_NAME})");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("CONFIGURE_FILE(${CMAKE_CURRENT_SOURCE_DIR}/${PROJECT_NAME}Config.cmake.in ${CMAKE_CURRENT_BINARY_DIR}/${PROJECT_NAME}Config.cmake @ONLY)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("INSTALL(FILES ${CMAKE_CURRENT_BINARY_DIR}/${PROJECT_NAME}Config.cmake DESTINATION modules)");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("IF(DEFINED ${PROJECT_NAME}_VERSION)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("SET_TARGET_PROPERTIES(${PROJECT_NAME} PROPERTIES VERSION ${${PROJECT_NAME}_VERSION} SOVERSION ${${PROJECT_NAME}_VERSION_MAJOR})");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("INCLUDE(CMakePackageConfigHelpers)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("write_basic_package_version_file(${PROJECT_NAME}ConfigVersion.cmake COMPATIBILITY SameMajorVersion)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("IF(EXISTS ${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake)");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("INSTALL(FILES ${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake DESTINATION modules)");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("SMART_TRACE_GENERATED_FILE(${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake)");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ENDIF(EXISTS ${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("ENDIF(DEFINED ${PROJECT_NAME}_VERSION)");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("INSTALL(TARGETS ${PROJECT_NAME} EXPORT ${PROJECT_NAME}Targets DESTINATION lib)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("EXPORT(EXPORT ${PROJECT_NAME}Targets)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("INSTALL(EXPORT ${PROJECT_NAME}Targets DESTINATION modules)");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("INSTALL(FILES ${OPCUA_HDRS} DESTINATION include/${PROJECT_NAME})");
    _builder.newLine();
    _builder.append("ENDIF(SeRoNetSDK_FOUND)");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileCMakeConfigFile(final CommObjectsRepository repo) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CMAKE_MINIMUM_REQUIRED(VERSION 3.5)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("FIND_PACKAGE(");
    String _name = repo.getName();
    _builder.append(_name);
    _builder.append(" PATHS $ENV{SMART_ROOT_ACE}/modules)");
    _builder.newLineIfNotEmpty();
    _builder.append("FIND_PACKAGE(Open62541Cpp QUIET)");
    _builder.newLine();
    _builder.append("SET(SmartSoft_CD_API_DIR $ENV{SMART_ROOT_ACE}/modules)");
    _builder.newLine();
    _builder.append("FIND_PACKAGE(SeRoNetSDK QUIET)");
    _builder.newLine();
    {
      Collection<CommObjectsRepository> _externalRepositories = this.getExternalRepositories(repo);
      for(final CommObjectsRepository ext : _externalRepositories) {
        _builder.append("# find depndency ");
        String _name_1 = ext.getName();
        _builder.append(_name_1);
        _builder.append("OpcUa");
        _builder.newLineIfNotEmpty();
        _builder.append("FIND_PACKAGE(");
        String _name_2 = ext.getName();
        _builder.append(_name_2);
        _builder.append("OpcUa PATHS $ENV{SMART_ROOT_ACE}/modules)");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("# include generated target configurations");
    _builder.newLine();
    _builder.append("INCLUDE(${CMAKE_CURRENT_LIST_DIR}/@PROJECT_NAME@Targets.cmake)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# the following variables are depricated and should not be used anymore:");
    _builder.newLine();
    _builder.append("# @PROJECT_NAME@_LIBRARIES");
    _builder.newLine();
    _builder.append("# @PROJECT_NAME@_INCLUDES");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# instead, just directly link the library @PROJECT_NAME@ to your executable target like this:");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.append("# TARGET_LINK_LIBRARIES(YourExecutableTarget @PROJECT_NAME@)");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.append("# (all the includes and additional libraries are automatically determined from the target @PROJECT_NAME@)");
    _builder.newLine();
    return _builder;
  }
  
  public Collection<CommObjectsRepository> getExternalRepositories(final CommObjectsRepository repo) {
    Collection<CommObjectsRepository> repos = new HashSet<CommObjectsRepository>();
    Iterable<CommunicationObject> _communicationObjects = this._communicationObjectUtility.getCommunicationObjects(repo);
    for (final CommunicationObject obj : _communicationObjects) {
      EList<AttributeDefinition> _attributes = obj.getAttributes();
      for (final AttributeDefinition attr : _attributes) {
        {
          final AbstractAttributeType type = attr.getType();
          if ((type instanceof CommElementReference)) {
            final AbstractCommElement ref = ((CommElementReference)type).getTypeName();
            EObject _eContainer = ref.eContainer();
            final CommObjectsRepository other = ((CommObjectsRepository) _eContainer);
            boolean _notEquals = (!Objects.equal(other, repo));
            if (_notEquals) {
              repos.add(other);
            }
          }
        }
      }
    }
    return repos;
  }
}
