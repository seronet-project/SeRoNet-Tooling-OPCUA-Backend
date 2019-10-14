//--------------------------------------------------------------------------
//
//  Copyright (C) 2019 Alex Lotz, Dennis Stampfer, Matthias Lutz
//
//        lotz@hs-ulm.de
//        stampfer@hs-ulm.de
//        lutz@hs-ulm.de
//
//        Servicerobotics Ulm
//        Christian Schlegel
//        Ulm University of Applied Sciences
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
package de.seronet_projekt.opcua.backend.generator.component.ext

import org.xtend.smartsoft.generator.component.ComponentGeneratorExtension
import org.ecore.component.componentDefinition.ComponentDefinition
import java.util.HashSet
import org.ecore.service.communicationObject.CommObjectsRepository
import org.ecore.component.componentDefinition.ComponentDefinitionModelUtility

class OpcUaComponentGeneratorExtension implements ComponentGeneratorExtension {
	override getExtensionName(ComponentDefinition component) {
		"OpcUaBackendComponentGeneratorExtension"
	}
	
	override getCMakeIncludes(ComponentDefinition component)
	'''
	GET_FILENAME_COMPONENT(OPC_UA_BACKEND_DIR "${PROJECT_SOURCE_DIR}/../opcua-backend" REALPATH)
	IF(EXISTS ${OPC_UA_BACKEND_DIR})
		INCLUDE("${OPC_UA_BACKEND_DIR}/src-gen/OpcUaBackend.cmake")
		LIST(APPEND FURTHER_SRCS ${OPC_UA_BACKEND_SRCS})
	ENDIF(EXISTS ${OPC_UA_BACKEND_DIR})
	'''
	
	override getCMakeTargetConfiguration(ComponentDefinition component)
	'''
	IF(SeRoNetSDK_FOUND)
	#TARGET_LINK_LIBRARIES(${PROJECT_NAME} SeRoNetSDK::SeRoNetSDK)
	«FOR repo: component.allRelatedRepos»
	TARGET_LINK_LIBRARIES(${PROJECT_NAME} «repo.name»OpcUa)
	«ENDFOR»
	ENDIF(SeRoNetSDK_FOUND)
	'''
	
	def getAllRelatedRepos(ComponentDefinition component) {
		val repos = new HashSet<CommObjectsRepository>();
		for(obj: ComponentDefinitionModelUtility.getAllCommObjects(component)) {
			repos.add(obj.eContainer as CommObjectsRepository)
		}
		return repos
	}
}