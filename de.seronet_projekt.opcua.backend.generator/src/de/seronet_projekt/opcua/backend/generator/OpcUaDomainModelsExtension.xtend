package de.seronet_projekt.opcua.backend.generator

import org.xtend.smartsoft.generator.commObj.DomainModelsGeneratorExtension
import org.ecore.service.communicationObject.CommObjectsRepository

class OpcUaDomainModelsExtension implements DomainModelsGeneratorExtension {
	
	override getExtensionName(CommObjectsRepository repo) {
		repo.name+"OpcUaBackend"
	}
	
	override getCMakeExtension(CommObjectsRepository repo) 
	'''
	GET_FILENAME_COMPONENT(OPC_UA_BACKEND_DIR "${PROJECT_SOURCE_DIR}/../opcua-backend" REALPATH)
	IF(EXISTS ${OPC_UA_BACKEND_DIR})
		ADD_SUBDIRECTORY(${OPC_UA_BACKEND_DIR}/src-gen ${CMAKE_CURRENT_BINARY_DIR}/opcua-backend)
	ENDIF(EXISTS ${OPC_UA_BACKEND_DIR})
	'''
}