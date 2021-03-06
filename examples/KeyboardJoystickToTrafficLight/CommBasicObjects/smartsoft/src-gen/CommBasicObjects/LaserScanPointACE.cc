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
#include "CommBasicObjects/LaserScanPointACE.hh"
#include <ace/SString.h>

// serialization operator for element LaserScanPoint
ACE_CDR::Boolean operator<<(ACE_OutputCDR &cdr, const CommBasicObjectsIDL::LaserScanPoint &data)
{
	ACE_CDR::Boolean good_bit = true;
	// serialize list-element index
	good_bit = good_bit && cdr.write_ushort(data.index);
	// serialize list-element distance
	good_bit = good_bit && cdr.write_ushort(data.distance);
	// serialize list-element intensity
	good_bit = good_bit && cdr.write_octet(data.intensity);
	
	return good_bit;
}

// de-serialization operator for element LaserScanPoint
ACE_CDR::Boolean operator>>(ACE_InputCDR &cdr, CommBasicObjectsIDL::LaserScanPoint &data)
{
	ACE_CDR::Boolean good_bit = true;
	// deserialize type element index
	good_bit = good_bit && cdr.read_ushort(data.index);
	// deserialize type element distance
	good_bit = good_bit && cdr.read_ushort(data.distance);
	// deserialize type element intensity
	good_bit = good_bit && cdr.read_octet(data.intensity);
	
	return good_bit;
}

// serialization operator for CommunicationObject LaserScanPoint
ACE_CDR::Boolean operator<<(ACE_OutputCDR &cdr, const CommBasicObjects::LaserScanPoint &obj)
{
	return cdr << obj.get();
}

// de-serialization operator for CommunicationObject LaserScanPoint
ACE_CDR::Boolean operator>>(ACE_InputCDR &cdr, CommBasicObjects::LaserScanPoint &obj)
{
	return cdr >> obj.set();
}
