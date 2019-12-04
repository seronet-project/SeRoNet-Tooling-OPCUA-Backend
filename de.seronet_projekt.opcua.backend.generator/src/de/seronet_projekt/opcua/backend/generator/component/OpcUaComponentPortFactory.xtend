package de.seronet_projekt.opcua.backend.generator.component

import com.google.inject.Inject
import org.ecore.component.componentDefinition.ComponentDefinition
import org.ecore.service.roboticMiddleware.OpcUa_SeRoNet
import org.xtend.smartsoft.generator.CopyrightHelpers
import de.seronet_projekt.opcua.backend.generator.commObj.OpcUaCommObjectSelfDescription

class OpcUaComponentPortFactory {
	@Inject extension CopyrightHelpers;
	@Inject extension OpcUaComponentGenHelpers;
	@Inject extension OpcUaCommObjectSelfDescription;
	
	def String getPortFactoryInterfaceFilename(ComponentDefinition component) '''«component.name»PortFactoryInterface.hh'''
	
	def String getOpcUaBackendPortFactoryHeaderFilename(ComponentDefinition component) '''«component.name»OpcUaBackendPortFactory.hh'''
	def String getOpcUaBackendPortFactorySourceFilename(ComponentDefinition component) '''«component.name»OpcUaBackendPortFactory.cc'''
	
	def compileOpcUaBackendPortFactoryHeader(ComponentDefinition component)
	'''
	«getCopyright()»
	
	#ifndef «component.name.toUpperCase»_OPC_UA_BACKEND_PORTFACTORY_HH_
	#define «component.name.toUpperCase»_OPC_UA_BACKEND_PORTFACTORY_HH_
	
	// include the main component-definition class
	#include "«component.portFactoryInterfaceFilename»"
	
	#include <thread>
	#include <chrono>
	
	// include SeRoNetSDK library
	#include <SeRoNetSDK/SeRoNet/Utils/Task.hpp>
	#include <SeRoNetSDK/SeRoNet/Utils/Component.hpp>
	
	class «component.name»OpcUaBackendPortFactory: public «component.name»PortFactoryInterface
	{
	private:
		// internal component instance
		SeRoNet::Utils::Component *componentImpl;
		
		// component thread
		std::thread component_thread;
		
		// internal component thread method
		int task_execution();
	public:
		«component.name»OpcUaBackendPortFactory();
		virtual ~«component.name»OpcUaBackendPortFactory();
	
		virtual void initialize(«component.nameClass» *component, int argc, char* argv[]) override;
		virtual int onStartup() override;
	
		«FOR port: component.allClientPorts.sortBy[it.name]»
		virtual «port.portDefinition» * create«port.nameClass»() override;
		«ENDFOR»
		
		«FOR port: component.allServerPorts.sortBy[it.name]»
		virtual «port.portDefinition» * create«port.nameClass»(const std::string &serviceName«IF port.isEventServer», std::shared_ptr<Smart::IEventTestHandler<«port.getCommObjectCppList(true)»>> «port.nameInstance»EventTestHandler«ENDIF») override;
		«ENDFOR»
		
		virtual int onShutdown(const std::chrono::steady_clock::duration &timeoutTime=std::chrono::seconds(2)) override;
		virtual void destroy() override;
	};
	
	#endif /* «component.name.toUpperCase»_SERONET_SDK_PORTFACTORY_HH_ */
	'''
	
	def compileOpcUaBackendPortFactorySource(ComponentDefinition component)
	'''
	«getCopyright()»
	
	#include "«component.opcUaBackendPortFactoryHeaderFilename»"
	
	// include all potentially required pattern implementations
	#include <SeRoNetSDK/SeRoNet/OPCUA/Client/PushClient.hpp>
	#include <SeRoNetSDK/SeRoNet/OPCUA/Client/EventClient.hpp>
	#include <SeRoNetSDK/SeRoNet/OPCUA/Client/QClientOPCUA.hpp>
	#include <SeRoNetSDK/SeRoNet/OPCUA/Client/SendClient.hpp>
	#include <SeRoNetSDK/SeRoNet/OPCUA/Client/QueryClient.hpp>
	
	#include <SeRoNetSDK/SeRoNet/OPCUA/Server/PushServer.hpp>
	#include <SeRoNetSDK/SeRoNet/OPCUA/Server/EventServer.hpp>
	#include <SeRoNetSDK/SeRoNet/OPCUA/Server/SendServer.hpp>
	#include <SeRoNetSDK/SeRoNet/OPCUA/Server/QueryServer.hpp>
	
	// include referenced CommunicationObject SeRoNetSDK self description implementations
	«FOR obj: component.allCommObjects.sortBy[it.repoNamespace + it.name]»
	#include "«obj.repoNamespace»OpcUa/«obj.opcUaHeaderFileName»"
	«ENDFOR»
	
	// create a static instance of the OpcUaBackendPortFactory
	static «component.name»OpcUaBackendPortFactory OpcUaBackendPortFactory;
	
	«component.name»OpcUaBackendPortFactory::«component.name»OpcUaBackendPortFactory()
	{  
		componentImpl = 0;
		«component.nameClass»::instance()->addPortFactory("«OpcUa_SeRoNet.simpleName»", this);
	}
	
	«component.name»OpcUaBackendPortFactory::~«component.name»OpcUaBackendPortFactory()
	{  }
	
	void «component.name»OpcUaBackendPortFactory::initialize(«component.nameClass» *component, int argc, char* argv[])
	{
		componentImpl = new SeRoNet::Utils::Component(component->connections.component.name);
	}
	
	int «component.name»OpcUaBackendPortFactory::onStartup()
	{
		if (!component_thread.joinable()) {
	    	component_thread = std::thread(&«component.name»OpcUaBackendPortFactory::task_execution, this);
	    	return 0;
	    }
		return -1;
	}

	«FOR port: component.allClientPorts.sortBy[it.name]»
	«port.portDefinition» * «component.name»OpcUaBackendPortFactory::create«port.nameClass»()
	{
		return new «port.portImplementation»(componentImpl);
	}
	
	«ENDFOR»
	
	«FOR port: component.allServerPorts.sortBy[it.name]»
	«port.portDefinition» * «component.name»OpcUaBackendPortFactory::create«port.nameClass»(const std::string &serviceName«IF port.isEventServer», std::shared_ptr<Smart::IEventTestHandler<«port.getCommObjectCppList(true)»>> «port.nameInstance»EventTestHandler«ENDIF»)
	{
		return new «port.portImplementation»(componentImpl, serviceName«IF port.isEventServer», «port.nameInstance»EventTestHandler«ENDIF»);
	}
	
	«ENDFOR»

	int «component.name»OpcUaBackendPortFactory::task_execution()
	{
		componentImpl->run();
		return 0;
	}

	int «component.name»OpcUaBackendPortFactory::onShutdown(const std::chrono::steady_clock::duration &timeoutTime)
	{
		// stop component-internal infrastructure
		componentImpl->stopRunning();
		// wait on component thread to exit
		if (component_thread.joinable()) {
			// FIXME: don't wait infinetly (use timeoutTime here)
	    	component_thread.join();
	    }
		return 0;
	}
	
	void «component.name»OpcUaBackendPortFactory::destroy()
	{
		// clean-up component's internally used resources
		delete componentImpl;
	}
	'''
}
