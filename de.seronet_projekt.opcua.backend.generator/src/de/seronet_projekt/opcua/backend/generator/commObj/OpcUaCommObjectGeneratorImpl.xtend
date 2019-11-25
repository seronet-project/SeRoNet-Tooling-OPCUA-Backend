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

import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.ecore.service.communicationObject.CommObjectsRepository
import org.ecore.service.communicationObject.CommunicationObject
import com.google.inject.Inject
import java.util.Collection
import java.util.HashSet
import org.ecore.service.communicationObject.CommunicationObjectUtility
import org.ecore.service.communicationObject.CommElementReference

class OpcUaCommObjectGeneratorImpl extends AbstractGenerator {
	@Inject extension OpcUaCommObjectSelfDescription
	@Inject extension CommunicationObjectUtility;
	
	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		for(repo: input.allContents.toIterable.filter(typeof(CommObjectsRepository))) {
			fsa.generateFile("CMakeLists.txt", repo.compileCMakeFile)
			fsa.generateFile(repo.name+"OpcUaConfig.cmake.in", repo.compileCMakeConfigFile)
			
			for(obj: repo.elements.filter(CommunicationObject)) {
				fsa.generateFile(obj.repoNamespace+"OpcUa/"+obj.opcUaHeaderFileName, obj.generateOpcUaHeaderFile)
				fsa.generateFile(obj.repoNamespace+"OpcUa/"+obj.opcUaSourceFileName, obj.generateOpcUaSourceFile)
			}
		}
	}
	
	def compileCMakeFile(CommObjectsRepository repo) 
	'''
	CMAKE_MINIMUM_REQUIRED(VERSION 3.5)
	
	PROJECT("«repo.name»OpcUa" VERSION 1.0)
	
	FIND_PACKAGE(Open62541Cpp QUIET)
	SET(SmartSoft_CD_API_DIR $ENV{SMART_ROOT_ACE}/modules)
	FIND_PACKAGE(SeRoNetSDK QUIET)

	IF(SeRoNetSDK_FOUND)
		SET(CMAKE_CXX_STANDARD 14)
		
		FILE(GLOB OPCUA_SRCS ${PROJECT_SOURCE_DIR}/${PROJECT_NAME}/*.cc)
		FILE(GLOB OPCUA_HDRS ${PROJECT_SOURCE_DIR}/${PROJECT_NAME}/*.hh)
		«FOR ext: repo.externalRepositories»
		# find depndency «repo.name»OpcUa
		FIND_PACKAGE(«ext.name»OpcUa PATHS $ENV{SMART_ROOT_ACE}/modules)
		«ENDFOR»
		
		ADD_LIBRARY(${PROJECT_NAME} SHARED ${OPCUA_SRCS})
		TARGET_LINK_LIBRARIES(${PROJECT_NAME} «repo.name» SeRoNetSDK::SeRoNetSDK)
		«FOR ext: repo.externalRepositories»
		# link depndency «ext.name»
		TARGET_LINK_LIBRARIES(${PROJECT_NAME} «ext.name»OpcUa)
		«ENDFOR»
		TARGET_INCLUDE_DIRECTORIES(${PROJECT_NAME} PUBLIC
		$<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}>
		$<INSTALL_INTERFACE:include>
		)
		
		ADD_DEPENDENCIES(autoinstall ${PROJECT_NAME})
		
		CONFIGURE_FILE(${CMAKE_CURRENT_SOURCE_DIR}/${PROJECT_NAME}Config.cmake.in ${CMAKE_CURRENT_BINARY_DIR}/${PROJECT_NAME}Config.cmake @ONLY)
		INSTALL(FILES ${CMAKE_CURRENT_BINARY_DIR}/${PROJECT_NAME}Config.cmake DESTINATION modules)
		
		IF(DEFINED ${PROJECT_NAME}_VERSION)
			SET_TARGET_PROPERTIES(${PROJECT_NAME} PROPERTIES VERSION ${${PROJECT_NAME}_VERSION} SOVERSION ${${PROJECT_NAME}_VERSION_MAJOR})
			INCLUDE(CMakePackageConfigHelpers)
			write_basic_package_version_file(${PROJECT_NAME}ConfigVersion.cmake COMPATIBILITY SameMajorVersion)
			IF(EXISTS ${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake)
				INSTALL(FILES ${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake DESTINATION modules)
				SMART_TRACE_GENERATED_FILE(${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake)
			ENDIF(EXISTS ${PROJECT_BINARY_DIR}/${PROJECT_NAME}ConfigVersion.cmake)
		ENDIF(DEFINED ${PROJECT_NAME}_VERSION)
		
		INSTALL(TARGETS ${PROJECT_NAME} EXPORT ${PROJECT_NAME}Targets DESTINATION lib)
		EXPORT(EXPORT ${PROJECT_NAME}Targets)
		INSTALL(EXPORT ${PROJECT_NAME}Targets DESTINATION modules)
		INSTALL(FILES ${OPCUA_HDRS} DESTINATION include/${PROJECT_NAME})
	ENDIF(SeRoNetSDK_FOUND)
	'''
	
	def compileCMakeConfigFile(CommObjectsRepository repo)
	'''
	CMAKE_MINIMUM_REQUIRED(VERSION 3.5)
	
	FIND_PACKAGE(Open62541Cpp QUIET)
	SET(SmartSoft_CD_API_DIR $ENV{SMART_ROOT_ACE}/modules)
	FIND_PACKAGE(SeRoNetSDK QUIET)
	
	FIND_FILE(SMART_MACROS SmartMacros2.cmake PATHS $ENV{SMART_ROOT_ACE}/CMakeMacros /opt/smartSoftAce/CMakeMacros)
	INCLUDE(${SMART_MACROS})
	INTERNAL_IMPORT_PACKAGE(«repo.name»)
	
	«FOR ext: repo.externalRepositories»
	# find depndency «ext.name»OpcUa
	FIND_PACKAGE(«ext.name»OpcUa PATHS $ENV{SMART_ROOT_ACE}/modules)
	«ENDFOR»
	
	# include generated target configurations
	INCLUDE(${CMAKE_CURRENT_LIST_DIR}/@PROJECT_NAME@Targets.cmake)
	
	# the following variables are depricated and should not be used anymore:
	# @PROJECT_NAME@_LIBRARIES
	# @PROJECT_NAME@_INCLUDES
	
	# instead, just directly link the library @PROJECT_NAME@ to your executable target like this:
	#
	# TARGET_LINK_LIBRARIES(YourExecutableTarget @PROJECT_NAME@)
	#
	# (all the includes and additional libraries are automatically determined from the target @PROJECT_NAME@)
	'''
	
	def getExternalRepositories(CommObjectsRepository repo) {
		var Collection<CommObjectsRepository> repos = new HashSet<CommObjectsRepository>();
		for(obj : repo.communicationObjects) {
			for(attr: obj.attributes) {
				val type = attr.type
				if(type instanceof CommElementReference) {
					val ref = type.typeName
					val other = (ref.eContainer as CommObjectsRepository)
					if(other != repo) {
						repos.add(other)
					}
				}
			}
		}
		return repos;
	}
}
