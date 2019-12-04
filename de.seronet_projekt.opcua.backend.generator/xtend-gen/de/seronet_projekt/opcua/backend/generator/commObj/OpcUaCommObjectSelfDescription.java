package de.seronet_projekt.opcua.backend.generator.commObj;

import com.google.inject.Inject;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.ecore.base.basicAttributes.AbstractAttributeType;
import org.ecore.base.basicAttributes.AttributeDefinition;
import org.ecore.base.basicAttributes.InlineEnumerationType;
import org.ecore.base.basicAttributes.PRIMITIVE_TYPE_NAME;
import org.ecore.base.basicAttributes.PrimitiveType;
import org.ecore.base.documentation.AbstractDocumentedElement;
import org.ecore.service.communicationObject.CommObjectsRepository;
import org.ecore.service.communicationObject.CommunicationObject;
import org.ecore.service.communicationObject.CommunicationObjectUtility;
import org.xtend.smartsoft.generator.CopyrightHelpers;

@SuppressWarnings("all")
public class OpcUaCommObjectSelfDescription {
  @Inject
  @Extension
  private CommunicationObjectUtility _communicationObjectUtility;
  
  @Inject
  @Extension
  private CopyrightHelpers _copyrightHelpers;
  
  public String getOpcUaHeaderFileName(final CommunicationObject co) {
    String _firstUpper = StringExtensions.toFirstUpper(co.getName());
    return (_firstUpper + "OpcUa.hh");
  }
  
  public String getOpcUaSourceFileName(final CommunicationObject co) {
    String _firstUpper = StringExtensions.toFirstUpper(co.getName());
    return (_firstUpper + "OpcUa.cc");
  }
  
  public String getUserClassHeaderFileName(final CommunicationObject co) {
    String _firstUpper = StringExtensions.toFirstUpper(co.getName());
    return (_firstUpper + ".hh");
  }
  
  protected CharSequence _getRepoNamespace(final CommObjectsRepository repo) {
    StringConcatenation _builder = new StringConcatenation();
    String _firstUpper = StringExtensions.toFirstUpper(repo.getName());
    _builder.append(_firstUpper);
    return _builder;
  }
  
  protected CharSequence _getRepoNamespace(final CommunicationObject co) {
    StringConcatenation _builder = new StringConcatenation();
    EObject _eContainer = co.eContainer();
    String _firstUpper = StringExtensions.toFirstUpper(((CommObjectsRepository) _eContainer).getName());
    _builder.append(_firstUpper);
    return _builder;
  }
  
  public CharSequence generateOpcUaHeaderFile(final CommunicationObject co) {
    StringConcatenation _builder = new StringConcatenation();
    String _copyright = this._copyrightHelpers.getCopyright();
    _builder.append(_copyright);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include \"");
    CharSequence _repoNamespace = this.getRepoNamespace(co);
    _builder.append(_repoNamespace);
    _builder.append("/");
    String _userClassHeaderFileName = this.getUserClassHeaderFileName(co);
    _builder.append(_userClassHeaderFileName);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/SelfDescription.hpp>");
    _builder.newLine();
    _builder.append("#pragma once");
    _builder.newLine();
    _builder.newLine();
    _builder.append("namespace SeRoNet {");
    _builder.newLine();
    _builder.append("namespace CommunicationObjects {");
    _builder.newLine();
    _builder.append("namespace Description {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("// serialization for ");
    CharSequence _repoNamespace_1 = this.getRepoNamespace(co);
    _builder.append(_repoNamespace_1);
    _builder.append("IDL::");
    String _name = co.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("template <>");
    _builder.newLine();
    _builder.append("IDescription::shp_t SelfDescription(");
    CharSequence _repoNamespace_2 = this.getRepoNamespace(co);
    _builder.append(_repoNamespace_2);
    _builder.append("IDL::");
    String _firstUpper = StringExtensions.toFirstUpper(co.getName());
    _builder.append(_firstUpper);
    _builder.append(" *obj, std::string name);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// serialization for ");
    String _name_1 = co.getName();
    _builder.append(_name_1);
    _builder.newLineIfNotEmpty();
    _builder.append("template <>");
    _builder.newLine();
    _builder.append("inline IDescription::shp_t SelfDescription(");
    CharSequence _repoNamespace_3 = this.getRepoNamespace(co);
    _builder.append(_repoNamespace_3);
    _builder.append("::");
    String _firstUpper_1 = StringExtensions.toFirstUpper(co.getName());
    _builder.append(_firstUpper_1);
    _builder.append(" *obj, std::string name)");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return SelfDescription(&(obj->set()),name); ");
    _builder.newLine();
    _builder.append("} // end SelfDescription");
    _builder.newLine();
    _builder.newLine();
    _builder.append("} // end namespace Description");
    _builder.newLine();
    _builder.append("} // end namespace CommunicationObjects");
    _builder.newLine();
    _builder.append("} // end namespace SeRoNet");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateOpcUaSourceFile(final CommunicationObject co) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"");
    String _opcUaHeaderFileName = this.getOpcUaHeaderFileName(co);
    _builder.append(_opcUaHeaderFileName);
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#define SERONET_NO_DEPRECATED");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/ComplexType.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/ElementPrimitives.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/SelfDescriptionArray.hpp>");
    _builder.newLine();
    _builder.append("#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/ElementArray.hpp>");
    _builder.newLine();
    _builder.newLine();
    {
      EList<AttributeDefinition> _attributes = co.getAttributes();
      for(final AttributeDefinition attr : _attributes) {
        {
          boolean _isCommunicationObject = this._communicationObjectUtility.isCommunicationObject(attr.getType());
          if (_isCommunicationObject) {
            _builder.append("#include \"");
            CharSequence _repoNamespace = this.getRepoNamespace(this._communicationObjectUtility.getCommunicationObjectRef(attr.getType()));
            _builder.append(_repoNamespace);
            _builder.append("OpcUa/");
            String _opcUaHeaderFileName_1 = this.getOpcUaHeaderFileName(this._communicationObjectUtility.getCommunicationObjectRef(attr.getType()));
            _builder.append(_opcUaHeaderFileName_1);
            _builder.append("\"");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("namespace SeRoNet {");
    _builder.newLine();
    _builder.append("namespace CommunicationObjects {");
    _builder.newLine();
    _builder.append("namespace Description {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("// serialization for ");
    CharSequence _repoNamespace_1 = this.getRepoNamespace(co);
    _builder.append(_repoNamespace_1);
    _builder.append("IDL::");
    String _name = co.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    _builder.append("template <>");
    _builder.newLine();
    _builder.append("IDescription::shp_t SelfDescription(");
    CharSequence _repoNamespace_2 = this.getRepoNamespace(co);
    _builder.append(_repoNamespace_2);
    _builder.append("IDL::");
    String _firstUpper = StringExtensions.toFirstUpper(co.getName());
    _builder.append(_firstUpper);
    _builder.append(" *obj, std::string name)");
    _builder.newLineIfNotEmpty();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("auto ret = std::make_shared<SeRoNet::CommunicationObjects::Description::ComplexType>(name);");
    _builder.newLine();
    {
      EList<AttributeDefinition> _attributes_1 = co.getAttributes();
      for(final AttributeDefinition attribute : _attributes_1) {
        _builder.append("\t");
        _builder.append("// add ");
        String _name_1 = attribute.getName();
        _builder.append(_name_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("ret->add(");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("SelfDescription(&(obj->");
        String _name_2 = attribute.getName();
        _builder.append(_name_2, "\t\t");
        _builder.append("), \"");
        String _firstUpper_1 = StringExtensions.toFirstUpper(attribute.getName());
        _builder.append(_firstUpper_1, "\t\t");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append(");");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("return ret;");
    _builder.newLine();
    _builder.append("} // end SelfDescription");
    _builder.newLine();
    _builder.newLine();
    _builder.append("} // end namespace Description");
    _builder.newLine();
    _builder.append("} // end namespace CommunicationObjects");
    _builder.newLine();
    _builder.append("} // end namespace SeRoNet");
    _builder.newLine();
    return _builder;
  }
  
  public String getOpcUaType(final AbstractAttributeType type) {
    String _xifexpression = null;
    if ((type instanceof PrimitiveType)) {
      String _switchResult = null;
      PRIMITIVE_TYPE_NAME _typeName = ((PrimitiveType)type).getTypeName();
      if (_typeName != null) {
        switch (_typeName) {
          case UINT8:
            _switchResult = "ElementUInt8";
            break;
          case UINT16:
            _switchResult = "ElementUInt16";
            break;
          case UINT32:
            _switchResult = "ElementUInt32";
            break;
          case UINT64:
            _switchResult = "ElementUInt64";
            break;
          case INT8:
            _switchResult = "ElementInt8";
            break;
          case INT16:
            _switchResult = "ElementInt16";
            break;
          case INT32:
            _switchResult = "ElementInt32";
            break;
          case INT64:
            _switchResult = "ElementInt64";
            break;
          case FLOAT:
            _switchResult = "ElementFloat";
            break;
          case DOUBLE:
            _switchResult = "ElementDouble";
            break;
          case BOOLEAN:
            _switchResult = "ElementBool";
            break;
          case STRING:
            _switchResult = "ElementString";
            break;
          default:
            break;
        }
      }
      _xifexpression = _switchResult;
    } else {
      if ((type instanceof InlineEnumerationType)) {
        return "ElementInt32";
      }
    }
    return _xifexpression;
  }
  
  public CharSequence getRepoNamespace(final AbstractDocumentedElement co) {
    if (co instanceof CommunicationObject) {
      return _getRepoNamespace((CommunicationObject)co);
    } else if (co instanceof CommObjectsRepository) {
      return _getRepoNamespace((CommObjectsRepository)co);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(co).toString());
    }
  }
}
