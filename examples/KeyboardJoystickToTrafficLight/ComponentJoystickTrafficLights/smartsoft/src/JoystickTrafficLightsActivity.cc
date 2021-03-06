//--------------------------------------------------------------------------
// Code generated by the SmartSoft MDSD Toolchain
// The SmartSoft Toolchain has been developed by:
//  
// Service Robotics Research Center
// University of Applied Sciences Ulm
// Prittwitzstr. 10
// 89075 Ulm (Germany)
//
// Information about the SmartSoft MDSD Toolchain is available at:
// www.servicerobotik-ulm.de
//
// This file is generated once. Modify this file to your needs. 
// If you want the toolchain to re-generate this file, please 
// delete it before running the code generator.
//--------------------------------------------------------------------------
#include "JoystickTrafficLightsActivity.hh"
#include "ComponentJoystickTrafficLights.hh"

#include <iostream>

JoystickTrafficLightsActivity::JoystickTrafficLightsActivity(SmartACE::SmartComponent *comp) 
:	JoystickTrafficLightsActivityCore(comp)
{
	std::cout << "constructor JoystickTrafficLightsActivity\n";
}
JoystickTrafficLightsActivity::~JoystickTrafficLightsActivity() 
{
	std::cout << "destructor JoystickTrafficLightsActivity\n";
}


void JoystickTrafficLightsActivity::on_JoystickServiceIn(const CommBasicObjects::CommJoystick &input)
{
	// upcall triggered from InputPort JoystickServiceIn
	// - use a local mutex here, because this upcal is called asynchroneously from outside of this task
	// - do not use longer blocking calls here since this upcall blocks the InputPort JoystickServiceIn
	// - if you need to implement a long-running procedure, do so within the on_execute() method and in
	//   there, use the method joystickServiceInGetUpdate(input) to get a copy of the input object
}

int JoystickTrafficLightsActivity::on_entry()
{
	// do initialization procedures here, which are called once, each time the task is started
	// it is possible to return != 0 (e.g. when initialization fails) then the task is not executed further
	return 0;
}
int JoystickTrafficLightsActivity::on_execute()
{
	// this method is called from an outside loop,
	// hence, NEVER use an infinite loop (like "while(1)") here inside!!!
	// also do not use blocking calls which do not result from smartsoft kernel
	
	// to get the incoming data, use this methods:
	Smart::StatusCode status;
	CommBasicObjects::CommJoystick joystickObject;
	status = this->joystickServiceInGetUpdate(joystickObject);
	if(status != Smart::SMART_OK) {
		std::cerr << status << std::endl;
		// return 0;
	} else {
		std::cout << "received: " << joystickObject << std::endl;
		CommBasicObjects::CommTrafficLights tl;

		int xPos = joystickObject.getXpos();
		if(xPos < 1000)
		{
			tl.setGreen(true);
			tl.setYellow(false);
			tl.setRed(false);
		} else if(xPos < 2000) {
			tl.setGreen(false);
			tl.setYellow(true);
			tl.setRed(false);
		} else {
			tl.setGreen(false);
			tl.setYellow(false);
			tl.setRed(true);
		}

		std::cout << "push: " << tl << std::endl;

		this->trafficLightsServiceOutPut(tl);
	}

	// it is possible to return != 0 (e.g. when the task detects errors), then the outer loop breaks and the task stops
	return 0;
}
int JoystickTrafficLightsActivity::on_exit()
{
	// use this method to clean-up resources which are initialized in on_entry() and needs to be freed before the on_execute() can be called again
	return 0;
}
