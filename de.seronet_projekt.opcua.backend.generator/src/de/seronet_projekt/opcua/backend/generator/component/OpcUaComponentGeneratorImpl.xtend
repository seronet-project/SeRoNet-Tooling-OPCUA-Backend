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

package de.seronet_projekt.opcua.backend.generator.component

import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.ecore.component.componentDefinition.ComponentDefinition
import com.google.inject.Inject
import org.xtend.smartsoft.generator.CopyrightHelpers

class OpcUaComponentGeneratorImpl extends AbstractGenerator {
	@Inject extension CopyrightHelpers;
	@Inject extension OpcUaComponentGenHelpers;
	@Inject extension OpcUaComponentPortFactory;
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		for(comp: input.allContents.toIterable.filter(typeof(ComponentDefinition))) {
			// info file with generator version string
			fsa.generateFile("info.txt", comp.compileToolchainVersionFile);
			
			fsa.generateFile("OpcUaBackend.cmake", comp.compileCMakeFile)
			
			fsa.generateFile(comp.opcUaBackendPortFactoryHeaderFilename, comp.compileOpcUaBackendPortFactoryHeader)
			fsa.generateFile(comp.opcUaBackendPortFactorySourceFilename, comp.compileOpcUaBackendPortFactorySource)
		}
	}
	
	def compileToolchainVersionFile(ComponentDefinition comp)
	'''
	«toolchainVersionFileString»
	'''
	
	def compileCMakeFile(ComponentDefinition component) 
	'''
	CMAKE_MINIMUM_REQUIRED(VERSION 3.5)
	
	FIND_PACKAGE(Open62541Cpp QUIET)
	SET(SmartSoft_CD_API_DIR $ENV{SMART_ROOT_ACE}/modules)
	FIND_PACKAGE(SeRoNetSDK QUIET)

	IF(SeRoNetSDK_FOUND)
		«FOR repo: component.allRelatedRepos.sortBy[it.name]»
		FIND_PACKAGE(«repo.name»OpcUa PATHS $ENV{SMART_ROOT_ACE}/modules)
		«ENDFOR»
		SET(CMAKE_CXX_STANDARD 14)
		INCLUDE_DIRECTORIES(${CMAKE_CURRENT_LIST_DIR})
		SET(OPC_UA_BACKEND_SRCS "${CMAKE_CURRENT_LIST_DIR}/«component.opcUaBackendPortFactorySourceFilename»")
	ENDIF(SeRoNetSDK_FOUND)
	'''
}