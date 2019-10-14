package de.seronet_projekt.opcua.backend.generator.component;

import com.google.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.ecore.component.componentDefinition.AbstractComponentElement;
import org.ecore.component.componentDefinition.AnswerPort;
import org.ecore.component.componentDefinition.ComponentDefinition;
import org.ecore.component.componentDefinition.ComponentDefinitionModelUtility;
import org.ecore.component.componentDefinition.ComponentPort;
import org.ecore.component.componentDefinition.DerivedComponentElement;
import org.ecore.component.componentDefinition.InputPort;
import org.ecore.component.componentDefinition.NamedComponentElement;
import org.ecore.component.componentDefinition.OutputPort;
import org.ecore.component.componentDefinition.RequestPort;
import org.ecore.service.communicationObject.CommObjectsRepository;
import org.ecore.service.communicationObject.CommunicationObject;
import org.ecore.service.communicationPattern.CommunicationPattern;
import org.ecore.service.communicationPattern.EventPattern;
import org.ecore.service.communicationPattern.PushPattern;
import org.ecore.service.communicationPattern.SendPattern;
import org.ecore.service.serviceDefinition.CommunicationServiceDefinition;
import org.xtend.smartsoft.generator.component.ComponentGenHelpers;

@SuppressWarnings("all")
public class OpcUaComponentGenHelpers {
  @Inject
  private ComponentGenHelpers compHelper;
  
  public String nameClass(final ComponentDefinition c) {
    return StringExtensions.toFirstUpper(c.getName());
  }
  
  public String nameClass(final AbstractComponentElement elem) {
    String _switchResult = null;
    boolean _matched = false;
    if (elem instanceof NamedComponentElement) {
      _matched=true;
      _switchResult = StringExtensions.toFirstUpper(((NamedComponentElement)elem).getName());
    }
    if (!_matched) {
      if (elem instanceof DerivedComponentElement) {
        _matched=true;
        _switchResult = StringExtensions.toFirstUpper(((DerivedComponentElement)elem).getName());
      }
    }
    return _switchResult;
  }
  
  public String nameInstance(final ComponentDefinition c) {
    return StringExtensions.toFirstLower(c.getName());
  }
  
  public String nameInstance(final AbstractComponentElement elem) {
    String _switchResult = null;
    boolean _matched = false;
    if (elem instanceof NamedComponentElement) {
      _matched=true;
      _switchResult = StringExtensions.toFirstLower(((NamedComponentElement)elem).getName());
    }
    if (!_matched) {
      if (elem instanceof DerivedComponentElement) {
        _matched=true;
        _switchResult = StringExtensions.toFirstLower(((DerivedComponentElement)elem).getName());
      }
    }
    return _switchResult;
  }
  
  public String nameOriginal(final ComponentDefinition c) {
    return c.getName();
  }
  
  public String nameOriginal(final AbstractComponentElement elem) {
    String _switchResult = null;
    boolean _matched = false;
    if (elem instanceof NamedComponentElement) {
      _matched=true;
      _switchResult = ((NamedComponentElement)elem).getName();
    }
    if (!_matched) {
      if (elem instanceof DerivedComponentElement) {
        _matched=true;
        _switchResult = ((DerivedComponentElement)elem).getName();
      }
    }
    return _switchResult;
  }
  
  public CommunicationPattern getPattern(final ComponentPort port) {
    final CommunicationServiceDefinition service = ComponentDefinitionModelUtility.getService(port);
    return ComponentDefinitionModelUtility.getPattern(service);
  }
  
  public boolean isEventServer(final AbstractComponentElement element) {
    return this.compHelper.isEventServer(element);
  }
  
  public Iterable<ComponentPort> getAllClientPorts(final ComponentDefinition comp) {
    return this.compHelper.getAllClientPorts(comp);
  }
  
  public Iterable<ComponentPort> getAllServerPorts(final ComponentDefinition comp) {
    return this.compHelper.getAllServerPorts(comp);
  }
  
  public CharSequence getCommObjectCppList(final CommunicationPattern pattern, final Boolean isSource) {
    return this.compHelper.getCommObjectCppList(pattern, isSource);
  }
  
  public CharSequence getCommObjectCppList(final ComponentPort service, final Boolean isSource) {
    final CommunicationPattern pattern = this.getPattern(service);
    return this.getCommObjectCppList(pattern, isSource);
  }
  
  public String getPortDefinition(final ComponentPort port) {
    return this.compHelper.getPortDefinition(port);
  }
  
  protected String _getPortImplementation(final OutputPort port) {
    String _xblockexpression = null;
    {
      final CommunicationPattern pattern = this.getPattern(port);
      String _switchResult = null;
      boolean _matched = false;
      if (pattern instanceof PushPattern) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("SeRoNet::OPCUA::Server::PushServer<");
        CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(true));
        _builder.append(_commObjectCppList);
        _builder.append(">");
        _switchResult = _builder.toString();
      }
      if (!_matched) {
        if (pattern instanceof EventPattern) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("SeRoNet::OPCUA::Server::EventServer<");
          CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(true));
          _builder.append(_commObjectCppList);
          _builder.append(">");
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        if (pattern instanceof SendPattern) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("SeRoNet::OPCUA::Client::SendClient<");
          CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(true));
          _builder.append(_commObjectCppList);
          _builder.append(">");
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        _switchResult = "";
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  protected String _getPortImplementation(final InputPort port) {
    String _xblockexpression = null;
    {
      final CommunicationPattern pattern = this.getPattern(port);
      String _switchResult = null;
      boolean _matched = false;
      if (pattern instanceof PushPattern) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("SeRoNet::OPCUA::Client::PushClient<");
        CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(false));
        _builder.append(_commObjectCppList);
        _builder.append(">");
        _switchResult = _builder.toString();
      }
      if (!_matched) {
        if (pattern instanceof EventPattern) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("SeRoNet::OPCUA::Client::EventClient<");
          CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(false));
          _builder.append(_commObjectCppList);
          _builder.append(">");
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        if (pattern instanceof SendPattern) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("SeRoNet::OPCUA::Server::SendServer<");
          CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(false));
          _builder.append(_commObjectCppList);
          _builder.append(">");
          _switchResult = _builder.toString();
        }
      }
      if (!_matched) {
        _switchResult = "";
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  protected String _getPortImplementation(final RequestPort port) {
    String _xblockexpression = null;
    {
      final CommunicationPattern pattern = this.getPattern(port);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("SeRoNet::OPCUA::Client::QueryClient<");
      CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(true));
      _builder.append(_commObjectCppList);
      _builder.append(">");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  protected String _getPortImplementation(final AnswerPort port) {
    String _xblockexpression = null;
    {
      final CommunicationPattern pattern = this.getPattern(port);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("SeRoNet::OPCUA::Server::QueryServer<");
      CharSequence _commObjectCppList = this.getCommObjectCppList(pattern, Boolean.valueOf(false));
      _builder.append(_commObjectCppList);
      _builder.append(">");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public Collection<CommunicationObject> getAllCommObjects(final ComponentDefinition component) {
    return ComponentDefinitionModelUtility.getAllCommObjects(component);
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
  
  public String getPortImplementation(final ComponentPort port) {
    if (port instanceof AnswerPort) {
      return _getPortImplementation((AnswerPort)port);
    } else if (port instanceof InputPort) {
      return _getPortImplementation((InputPort)port);
    } else if (port instanceof OutputPort) {
      return _getPortImplementation((OutputPort)port);
    } else if (port instanceof RequestPort) {
      return _getPortImplementation((RequestPort)port);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(port).toString());
    }
  }
}
