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
#ifndef COMMBASICOBJECTS_COMMBUMPEREVENTSTATE_ACE_H_
#define COMMBASICOBJECTS_COMMBUMPEREVENTSTATE_ACE_H_

#include "CommBasicObjects/CommBumperEventState.hh"

#include <ace/CDR_Stream.h>

// serialization operator for DataStructure CommBumperEventState
ACE_CDR::Boolean operator<<(ACE_OutputCDR &cdr, const CommBasicObjectsIDL::CommBumperEventState &data);

// de-serialization operator for DataStructure CommBumperEventState
ACE_CDR::Boolean operator>>(ACE_InputCDR &cdr, CommBasicObjectsIDL::CommBumperEventState &data);

// serialization operator for CommunicationObject CommBumperEventState
ACE_CDR::Boolean operator<<(ACE_OutputCDR &cdr, const CommBasicObjects::CommBumperEventState &obj);

// de-serialization operator for CommunicationObject CommBumperEventState
ACE_CDR::Boolean operator>>(ACE_InputCDR &cdr, CommBasicObjects::CommBumperEventState &obj);

#endif /* COMMBASICOBJECTS_COMMBUMPEREVENTSTATE_ACE_H_ */
