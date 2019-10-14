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

import org.ecore.component.componentDefinition.ComponentDefinition
import org.ecore.component.componentDefinition.InputPort
import org.ecore.component.componentDefinition.RequestPort
import org.ecore.component.componentDefinition.OutputPort
import org.ecore.component.componentDefinition.AnswerPort
import org.ecore.component.componentDefinition.AbstractComponentElement
import org.ecore.component.componentDefinition.NamedComponentElement
import org.ecore.component.componentDefinition.DerivedComponentElement
import org.ecore.service.communicationPattern.EventPattern
import org.ecore.service.communicationPattern.PushPattern
import org.ecore.service.communicationPattern.SendPattern
import org.ecore.service.communicationPattern.CommunicationPattern
import com.google.inject.Inject
import org.ecore.component.componentDefinition.ComponentPort
import org.ecore.component.componentDefinition.ComponentDefinitionModelUtility
import org.ecore.service.communicationObject.CommunicationObject
import java.util.Collection
import java.util.HashSet
import org.ecore.service.communicationObject.CommObjectsRepository

class OpcUaComponentGenHelpers {
	@Inject org.xtend.smartsoft.generator.component.ComponentGenHelpers compHelper;
	
	def nameClass(ComponentDefinition c) { c.name.toFirstUpper }
	def nameClass(AbstractComponentElement elem) { 
		switch(elem) {
			NamedComponentElement: elem.name.toFirstUpper
			DerivedComponentElement: elem.name.toFirstUpper
		}
	}
	def nameInstance(ComponentDefinition c) { c.name.toFirstLower }
	def nameInstance(AbstractComponentElement elem) { 
		switch(elem) {
			NamedComponentElement: elem.name.toFirstLower
			DerivedComponentElement: elem.name.toFirstLower
		}
	}
	def nameOriginal(ComponentDefinition c) { c.name }
	def nameOriginal(AbstractComponentElement elem) {
		switch(elem) {
			NamedComponentElement: elem.name
			DerivedComponentElement: elem.name
		}
	}
	
	def getPattern(ComponentPort port) {
		val service = ComponentDefinitionModelUtility.getService(port)
		return ComponentDefinitionModelUtility.getPattern(service)
	}
	
	def boolean isEventServer(AbstractComponentElement element) {
		return compHelper.isEventServer(element)
	}
	
	def Iterable<ComponentPort> getAllClientPorts(ComponentDefinition comp) {
		return compHelper.getAllClientPorts(comp)
	}
	
	def Iterable<ComponentPort> getAllServerPorts(ComponentDefinition comp) {
		return compHelper.getAllServerPorts(comp)
	}
	
	def CharSequence getCommObjectCppList(CommunicationPattern pattern, Boolean isSource) {
		return compHelper.getCommObjectCppList(pattern, isSource)
	}
	
	def getCommObjectCppList(ComponentPort service, Boolean isSource) {
		val pattern = service.pattern
		return pattern.getCommObjectCppList(isSource)
	}
	
	def String getPortDefinition(ComponentPort port) {
		return compHelper.getPortDefinition(port)
	}
	
	def dispatch String getPortImplementation(OutputPort port) {
		val pattern = port.pattern
		switch(pattern) {
			PushPattern: '''SeRoNet::OPCUA::Server::PushServer<«pattern.getCommObjectCppList(true)»>'''
			EventPattern: '''SeRoNet::OPCUA::Server::EventServer<«pattern.getCommObjectCppList(true)»>'''
			SendPattern: '''SeRoNet::OPCUA::Client::SendClient<«pattern.getCommObjectCppList(true)»>'''
			default: ""
		}
	}
	
	def dispatch String getPortImplementation(InputPort port) {
		val pattern = port.pattern
		switch (pattern) {
			PushPattern: '''SeRoNet::OPCUA::Client::PushClient<«pattern.getCommObjectCppList(false)»>'''
			EventPattern: '''SeRoNet::OPCUA::Client::EventClient<«pattern.getCommObjectCppList(false)»>'''
			SendPattern: '''SeRoNet::OPCUA::Server::SendServer<«pattern.getCommObjectCppList(false)»>'''
			default: ""
		}
	}
	
	def dispatch String getPortImplementation(RequestPort port) {
		val pattern = port.pattern
		'''SeRoNet::OPCUA::Client::QueryClient<«pattern.getCommObjectCppList(true)»>'''
	}
	
	def dispatch String getPortImplementation(AnswerPort port) {
		val pattern = port.pattern
		'''SeRoNet::OPCUA::Server::QueryServer<«pattern.getCommObjectCppList(false)»>'''
	}
	
	def Collection<CommunicationObject> getAllCommObjects(ComponentDefinition component) {
		return ComponentDefinitionModelUtility.getAllCommObjects(component)
	}
	
	def getAllRelatedRepos(ComponentDefinition component) {
		val repos = new HashSet<CommObjectsRepository>();
		for(obj: ComponentDefinitionModelUtility.getAllCommObjects(component)) {
			repos.add(obj.eContainer as CommObjectsRepository)
		}
		return repos
	}
}