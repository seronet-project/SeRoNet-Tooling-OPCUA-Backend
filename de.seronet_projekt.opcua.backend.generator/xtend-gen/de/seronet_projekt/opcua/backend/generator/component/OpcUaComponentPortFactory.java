package de.seronet_projekt.opcua.backend.generator.component;

import com.google.inject.Inject;
import de.seronet_projekt.opcua.backend.generator.commObj.OpcUaCommObjectSelfDescription;
import de.seronet_projekt.opcua.backend.generator.component.OpcUaComponentGenHelpers;
import java.util.Collection;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.ecore.component.componentDefinition.ComponentDefinition;
import org.ecore.component.componentDefinition.ComponentPort;
import org.ecore.service.communicationObject.CommunicationObject;
import org.ecore.service.roboticMiddleware.OpcUa_SeRoNet;
import org.xtend.smartsoft.generator.CopyrightHelpers;

@SuppressWarnings("all")
public class OpcUaComponentPortFactory {
  @Inject
  @Extension
  private CopyrightHelpers _copyrightHelpers;
  
  @Inject
  @Extension
  private OpcUaComponentGenHelpers _opcUaComponentGenHelpers;
  
  @Inject
  @Extension
  private OpcUaCommObjectSelfDescription _opcUaCommObjectSelfDescription;
  
  public String getPortFactoryInterfaceFilename(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = component.getName();
    _builder.append(_name);
    _builder.append("PortFactoryInterface.hh");
    return _builder.toString();
  }
  
  public String getOpcUaBackendPortFactoryHeaderFilename(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = component.getName();
    _builder.append(_name);
    _builder.append("OpcUaBackendPortFactory.hh");
    return _builder.toString();
  }
  
  public String getOpcUaBackendPortFactorySourceFilename(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = component.getName();
    _builder.append(_name);
    _builder.append("OpcUaBackendPortFactory.cc");
    return _builder.toString();
  }
  
  public CharSequence compileOpcUaBackendPortFactoryHeader(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    String _copyright = this._copyrightHelpers.getCopyright();
    _builder.append(_copyright);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#ifndef ");
    String _upperCase = component.getName().toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_OPC_UA_BACKEND_PORTFACTORY_HH_");
    _builder.newLineIfNotEmpty();
    _builder.append("#define ");
    String _upperCase_1 = component.getName().toUpperCase();
    _builder.append(_upperCase_1);
    _builder.append("_OPC_UA_BACKEND_PORTFACTORY_HH_");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// include the main component-definition class");
    _builder.newLine();
    _builder.append("#include \"");
    String _portFactoryInterfaceFilename = this.getPortFactoryInterfaceFilename(component);
    _builder.append(_portFactoryInterfaceFilename);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include <thread>");
    _builder.newLine();
    _builder.append("#include <chrono>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// include SeRoNetSDK library");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/Utils/Task.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/Utils/Component.hpp>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    String _name = component.getName();
    _builder.append(_name);
    _builder.append("OpcUaBackendPortFactory: public ");
    String _name_1 = component.getName();
    _builder.append(_name_1);
    _builder.append("PortFactoryInterface");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("private:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// internal component instance");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("SeRoNet::Utils::Component *componentImpl;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// component thread");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("std::thread component_thread;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// internal component thread method");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int task_execution();");
    _builder.newLine();
    _builder.append("public:");
    _builder.newLine();
    _builder.append("\t");
    String _name_2 = component.getName();
    _builder.append(_name_2, "\t");
    _builder.append("OpcUaBackendPortFactory();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("virtual ~");
    String _name_3 = component.getName();
    _builder.append(_name_3, "\t");
    _builder.append("OpcUaBackendPortFactory();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("virtual void initialize(");
    String _nameClass = this._opcUaComponentGenHelpers.nameClass(component);
    _builder.append(_nameClass, "\t");
    _builder.append(" *component, int argc, char* argv[]) override;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("virtual int onStartup() override;");
    _builder.newLine();
    _builder.newLine();
    {
      Iterable<ComponentPort> _allClientPorts = this._opcUaComponentGenHelpers.getAllClientPorts(component);
      for(final ComponentPort port : _allClientPorts) {
        _builder.append("\t");
        _builder.append("virtual ");
        String _portDefinition = this._opcUaComponentGenHelpers.getPortDefinition(port);
        _builder.append(_portDefinition, "\t");
        _builder.append(" * create");
        String _nameClass_1 = this._opcUaComponentGenHelpers.nameClass(port);
        _builder.append(_nameClass_1, "\t");
        _builder.append("() override;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      Iterable<ComponentPort> _allServerPorts = this._opcUaComponentGenHelpers.getAllServerPorts(component);
      for(final ComponentPort port_1 : _allServerPorts) {
        _builder.append("\t");
        _builder.append("virtual ");
        String _portDefinition_1 = this._opcUaComponentGenHelpers.getPortDefinition(port_1);
        _builder.append(_portDefinition_1, "\t");
        _builder.append(" * create");
        String _nameClass_2 = this._opcUaComponentGenHelpers.nameClass(port_1);
        _builder.append(_nameClass_2, "\t");
        _builder.append("(const std::string &serviceName");
        {
          boolean _isEventServer = this._opcUaComponentGenHelpers.isEventServer(port_1);
          if (_isEventServer) {
            _builder.append(", Smart::IEventTestHandler<");
            CharSequence _commObjectCppList = this._opcUaComponentGenHelpers.getCommObjectCppList(port_1, Boolean.valueOf(true));
            _builder.append(_commObjectCppList, "\t");
            _builder.append("> *");
            String _nameInstance = this._opcUaComponentGenHelpers.nameInstance(port_1);
            _builder.append(_nameInstance, "\t");
            _builder.append("EventTestHandler");
          }
        }
        _builder.append(") override;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("virtual int onShutdown(const std::chrono::steady_clock::duration &timeoutTime=std::chrono::seconds(2)) override;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("virtual void destroy() override;");
    _builder.newLine();
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#endif /* ");
    String _upperCase_2 = component.getName().toUpperCase();
    _builder.append(_upperCase_2);
    _builder.append("_SERONET_SDK_PORTFACTORY_HH_ */");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence compileOpcUaBackendPortFactorySource(final ComponentDefinition component) {
    StringConcatenation _builder = new StringConcatenation();
    String _copyright = this._copyrightHelpers.getCopyright();
    _builder.append(_copyright);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include \"");
    String _opcUaBackendPortFactoryHeaderFilename = this.getOpcUaBackendPortFactoryHeaderFilename(component);
    _builder.append(_opcUaBackendPortFactoryHeaderFilename);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// include all potentially required pattern implementations");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Client/PushClient.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Client/EventClient.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Client/QClientOPCUA.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Client/SendClient.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Client/QueryClient.hpp>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Server/PushServer.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Server/EventServer.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Server/SendServer.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/OPCUA/Server/QueryServer.hpp>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// include referenced CommunicationObject SeRoNetSDK self description implementations");
    _builder.newLine();
    {
      Collection<CommunicationObject> _allCommObjects = this._opcUaComponentGenHelpers.getAllCommObjects(component);
      for(final CommunicationObject obj : _allCommObjects) {
        _builder.append("#include \"");
        CharSequence _repoNamespace = this._opcUaCommObjectSelfDescription.getRepoNamespace(obj);
        _builder.append(_repoNamespace);
        _builder.append("OpcUa/");
        String _opcUaHeaderFileName = this._opcUaCommObjectSelfDescription.getOpcUaHeaderFileName(obj);
        _builder.append(_opcUaHeaderFileName);
        _builder.append("\"");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("// create a static instance of the OpcUaBackendPortFactory");
    _builder.newLine();
    _builder.append("static ");
    String _name = component.getName();
    _builder.append(_name);
    _builder.append("OpcUaBackendPortFactory OpcUaBackendPortFactory;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _name_1 = component.getName();
    _builder.append(_name_1);
    _builder.append("OpcUaBackendPortFactory::");
    String _name_2 = component.getName();
    _builder.append(_name_2);
    _builder.append("OpcUaBackendPortFactory()");
    _builder.newLineIfNotEmpty();
    _builder.append("{  ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("componentImpl = 0;");
    _builder.newLine();
    _builder.append("\t");
    String _nameClass = this._opcUaComponentGenHelpers.nameClass(component);
    _builder.append(_nameClass, "\t");
    _builder.append("::instance()->addPortFactory(\"");
    String _simpleName = OpcUa_SeRoNet.class.getSimpleName();
    _builder.append(_simpleName, "\t");
    _builder.append("\", this);");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    String _name_3 = component.getName();
    _builder.append(_name_3);
    _builder.append("OpcUaBackendPortFactory::~");
    String _name_4 = component.getName();
    _builder.append(_name_4);
    _builder.append("OpcUaBackendPortFactory()");
    _builder.newLineIfNotEmpty();
    _builder.append("{  }");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_5 = component.getName();
    _builder.append(_name_5);
    _builder.append("OpcUaBackendPortFactory::initialize(");
    String _nameClass_1 = this._opcUaComponentGenHelpers.nameClass(component);
    _builder.append(_nameClass_1);
    _builder.append(" *component, int argc, char* argv[])");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("componentImpl = new SeRoNet::Utils::Component(component->connections.component.name);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("int ");
    String _name_6 = component.getName();
    _builder.append(_name_6);
    _builder.append("OpcUaBackendPortFactory::onStartup()");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (!component_thread.joinable()) {");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("component_thread = std::thread(&");
    String _name_7 = component.getName();
    _builder.append(_name_7, "    \t");
    _builder.append("OpcUaBackendPortFactory::task_execution, this);");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return -1;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    {
      Iterable<ComponentPort> _allClientPorts = this._opcUaComponentGenHelpers.getAllClientPorts(component);
      for(final ComponentPort port : _allClientPorts) {
        String _portDefinition = this._opcUaComponentGenHelpers.getPortDefinition(port);
        _builder.append(_portDefinition);
        _builder.append(" * ");
        String _name_8 = component.getName();
        _builder.append(_name_8);
        _builder.append("OpcUaBackendPortFactory::create");
        String _nameClass_2 = this._opcUaComponentGenHelpers.nameClass(port);
        _builder.append(_nameClass_2);
        _builder.append("()");
        _builder.newLineIfNotEmpty();
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("return new ");
        String _portImplementation = this._opcUaComponentGenHelpers.getPortImplementation(port);
        _builder.append(_portImplementation, "\t");
        _builder.append("(componentImpl);");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      Iterable<ComponentPort> _allServerPorts = this._opcUaComponentGenHelpers.getAllServerPorts(component);
      for(final ComponentPort port_1 : _allServerPorts) {
        String _portDefinition_1 = this._opcUaComponentGenHelpers.getPortDefinition(port_1);
        _builder.append(_portDefinition_1);
        _builder.append(" * ");
        String _name_9 = component.getName();
        _builder.append(_name_9);
        _builder.append("OpcUaBackendPortFactory::create");
        String _nameClass_3 = this._opcUaComponentGenHelpers.nameClass(port_1);
        _builder.append(_nameClass_3);
        _builder.append("(const std::string &serviceName");
        {
          boolean _isEventServer = this._opcUaComponentGenHelpers.isEventServer(port_1);
          if (_isEventServer) {
            _builder.append(", Smart::IEventTestHandler<");
            CharSequence _commObjectCppList = this._opcUaComponentGenHelpers.getCommObjectCppList(port_1, Boolean.valueOf(true));
            _builder.append(_commObjectCppList);
            _builder.append("> *");
            String _nameInstance = this._opcUaComponentGenHelpers.nameInstance(port_1);
            _builder.append(_nameInstance);
            _builder.append("EventTestHandler");
          }
        }
        _builder.append(")");
        _builder.newLineIfNotEmpty();
        _builder.append("{");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("return new ");
        String _portImplementation_1 = this._opcUaComponentGenHelpers.getPortImplementation(port_1);
        _builder.append(_portImplementation_1, "\t");
        _builder.append("(componentImpl, serviceName");
        {
          boolean _isEventServer_1 = this._opcUaComponentGenHelpers.isEventServer(port_1);
          if (_isEventServer_1) {
            _builder.append(", ");
            String _nameInstance_1 = this._opcUaComponentGenHelpers.nameInstance(port_1);
            _builder.append(_nameInstance_1, "\t");
            _builder.append("EventTestHandler");
          }
        }
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("int ");
    String _name_10 = component.getName();
    _builder.append(_name_10);
    _builder.append("OpcUaBackendPortFactory::task_execution()");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("componentImpl->run();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("int ");
    String _name_11 = component.getName();
    _builder.append(_name_11);
    _builder.append("OpcUaBackendPortFactory::onShutdown(const std::chrono::steady_clock::duration &timeoutTime)");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// stop component-internal infrastructure");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("componentImpl->stopRunning();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// wait on component thread to exit");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("if (component_thread.joinable()) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// FIXME: don\'t wait infinetly (use timeoutTime here)");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("component_thread.join();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return 0;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("void ");
    String _name_12 = component.getName();
    _builder.append(_name_12);
    _builder.append("OpcUaBackendPortFactory::destroy()");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("// clean-up component\'s internally used resources");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("delete componentImpl;");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
