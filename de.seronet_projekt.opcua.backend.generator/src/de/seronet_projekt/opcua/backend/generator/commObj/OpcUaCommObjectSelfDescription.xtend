//--------------------------------------------------------------------------
//
//  Copyright (C) 2018 Alex Lotz, Dennis Stampfer, Matthias Lutz
//
//        lotz@hs-ulm.de
//        stampfer@hs-ulm.de
//        lutz@hs-ulm.de
//
//        Servicerobotics Ulm
//        Christian Schlegel
//        University of Applied Sciences
//        Prittwitzstr. 10
//        89075 Ulm
//        Germany
//
//  This file is part of the SmartSoft MDSD Toolchain. 
//
// Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
// 
// 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the 
//    documentation and/or other materials provided with the distribution.
// 
// 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this 
//    software without specific prior written permission.
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
// THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS 
// BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
// POSSIBILITY OF SUCH DAMAGE.
//
//--------------------------------------------------------------------------

package de.seronet_projekt.opcua.backend.generator.commObj

import com.google.inject.Inject
import org.ecore.service.communicationObject.CommunicationObject
import org.ecore.service.communicationObject.CommObjectsRepository
import org.ecore.service.communicationObject.CommunicationObjectUtility
import org.xtend.smartsoft.generator.CopyrightHelpers
import org.ecore.base.basicAttributes.PrimitiveType
import org.ecore.base.basicAttributes.AbstractAttributeType
import org.ecore.base.basicAttributes.PRIMITIVE_TYPE_NAME
import org.ecore.base.basicAttributes.InlineEnumerationType

class OpcUaCommObjectSelfDescription {
	@Inject extension CommunicationObjectUtility;
	@Inject extension CopyrightHelpers;

	def getOpcUaHeaderFileName(CommunicationObject co) {
		return co.name.toFirstUpper + "OpcUa.hh"
	}
	def getOpcUaSourceFileName(CommunicationObject co) {
		return co.name.toFirstUpper + "OpcUa.cc"
	}
	
	def getUserClassHeaderFileName(CommunicationObject co) {
		return co.name.toFirstUpper + ".hh"
	}
	
	// helper methods to unify the repository namespace definition
	def dispatch getRepoNamespace(CommObjectsRepository repo) '''«repo.name.toFirstUpper»'''
	def dispatch getRepoNamespace(CommunicationObject co) '''«(co.eContainer as CommObjectsRepository).name.toFirstUpper»'''
	
	
	def generateOpcUaHeaderFile(CommunicationObject co)
	'''
	«copyright»
	
	#include "«co.repoNamespace»/«co.userClassHeaderFileName»"
	
	#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/SelfDescription.hpp>
	#pragma once
	
	namespace SeRoNet {
	namespace CommunicationObjects {
	namespace Description {
		
	// serialization for «co.repoNamespace»IDL::«co.name»
	template <>
	IDescription::shp_t SelfDescription(«co.repoNamespace»IDL::«co.name.toFirstUpper» *obj, std::string name);

	// serialization for «co.name»
	template <>
	inline IDescription::shp_t SelfDescription(«co.repoNamespace»::«co.name.toFirstUpper» *obj, std::string name)
	{
		return SelfDescription(&(obj->set()),name); 
	} // end SelfDescription
	
	} // end namespace Description
	} // end namespace CommunicationObjects
	} // end namespace SeRoNet
	'''
	
	def generateOpcUaSourceFile(CommunicationObject co)
	'''
		#include "«co.opcUaHeaderFileName»"
		
		#define SERONET_NO_DEPRECATED
		#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/ComplexType.hpp>
		#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/ElementPrimitives.hpp>
		#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/SelfDescriptionArray.hpp>
		#include <SeRoNetSDK/SeRoNet/CommunicationObjects/Description/ElementArray.hpp>
		
		«FOR attr: co.attributes»
			«IF attr.type.isCommunicationObject»
			#include "«attr.type.communicationObjectRef.repoNamespace»OpcUa/«attr.type.communicationObjectRef.opcUaHeaderFileName»"
			«ENDIF»
		«ENDFOR»
		
		namespace SeRoNet {
		namespace CommunicationObjects {
		namespace Description {
			
		// serialization for «co.repoNamespace»IDL::«co.name»
		template <>
		IDescription::shp_t SelfDescription(«co.repoNamespace»IDL::«co.name.toFirstUpper» *obj, std::string name)
		{
			auto ret = std::make_shared<SeRoNet::CommunicationObjects::Description::ComplexType>(name);
			«FOR attribute: co.attributes»
				// add «attribute.name»
				ret->add(
					SelfDescription(&(obj->«attribute.name»), "«attribute.name.toFirstUpper»")
				);
			«ENDFOR»
			return ret;
		} // end SelfDescription
		
		} // end namespace Description
		} // end namespace CommunicationObjects
		} // end namespace SeRoNet
	'''

	def getOpcUaType(AbstractAttributeType type) {
		if(type instanceof PrimitiveType) {
			switch(type.typeName) {
				case PRIMITIVE_TYPE_NAME::UINT8: "ElementUInt8"
				case PRIMITIVE_TYPE_NAME::UINT16: "ElementUInt16"
				case PRIMITIVE_TYPE_NAME::UINT32: "ElementUInt32"
				case PRIMITIVE_TYPE_NAME::UINT64: "ElementUInt64"
				case PRIMITIVE_TYPE_NAME::INT8: "ElementInt8"
				case PRIMITIVE_TYPE_NAME::INT16: "ElementInt16"
				case PRIMITIVE_TYPE_NAME::INT32: "ElementInt32"
				case PRIMITIVE_TYPE_NAME::INT64: "ElementInt64"
				case PRIMITIVE_TYPE_NAME::FLOAT: "ElementFloat"
				case PRIMITIVE_TYPE_NAME::DOUBLE: "ElementDouble"
				case PRIMITIVE_TYPE_NAME::BOOLEAN: "ElementBool"
				case PRIMITIVE_TYPE_NAME::STRING: "ElementString"
			}
		} else if(type instanceof InlineEnumerationType) {
			return "ElementInt32"
		}
	}
}
