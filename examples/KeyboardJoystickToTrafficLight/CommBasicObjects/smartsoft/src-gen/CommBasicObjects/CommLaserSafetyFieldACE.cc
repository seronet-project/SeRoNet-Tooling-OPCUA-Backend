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
// Please do not modify this file. It will be re-generated
// running the code generator.
//--------------------------------------------------------------------------
#include "CommBasicObjects/CommLaserSafetyFieldACE.hh"
#include <ace/SString.h>
#include "CommBasicObjects/enumSafetyFieldStateData.hh"

// serialization operator for element CommLaserSafetyField
ACE_CDR::Boolean operator<<(ACE_OutputCDR &cdr, const CommBasicObjectsIDL::CommLaserSafetyField &data)
{
	ACE_CDR::Boolean good_bit = true;
	// serialize list-element protectiveState
	good_bit = good_bit && cdr.write_long(data.protectiveState);
	// serialize list-element warningState
	good_bit = good_bit && cdr.write_long(data.warningState);
	
	return good_bit;
}

// de-serialization operator for element CommLaserSafetyField
ACE_CDR::Boolean operator>>(ACE_InputCDR &cdr, CommBasicObjectsIDL::CommLaserSafetyField &data)
{
	ACE_CDR::Boolean good_bit = true;
	// deserialize type element protectiveState
	good_bit = good_bit && cdr.read_long(data.protectiveState);
	// deserialize type element warningState
	good_bit = good_bit && cdr.read_long(data.warningState);
	
	return good_bit;
}

// serialization operator for CommunicationObject CommLaserSafetyField
ACE_CDR::Boolean operator<<(ACE_OutputCDR &cdr, const CommBasicObjects::CommLaserSafetyField &obj)
{
	return cdr << obj.get();
}

// de-serialization operator for CommunicationObject CommLaserSafetyField
ACE_CDR::Boolean operator>>(ACE_InputCDR &cdr, CommBasicObjects::CommLaserSafetyField &obj)
{
	return cdr >> obj.set();
}