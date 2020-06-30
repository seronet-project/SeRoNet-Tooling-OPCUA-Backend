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
#ifndef COMMBASICOBJECTS_COMMPROPERTYSET_DATA_H_
#define COMMBASICOBJECTS_COMMPROPERTYSET_DATA_H_

#include "CommBasicObjects/PropertyItemEntryData.hh"
#include "CommBasicObjects/PropertySetEntryData.hh"

#include <vector>

namespace CommBasicObjectsIDL 
{
	typedef std::vector<CommBasicObjectsIDL::PropertySetEntry> CommPropertySet_sets_type;
	typedef std::vector<CommBasicObjectsIDL::PropertyItemEntry> CommPropertySet_items_type;
	struct CommPropertySet
	{
		CommPropertySet_sets_type sets;
		CommPropertySet_items_type items;
  	};
};

#endif /* COMMBASICOBJECTS_COMMPROPERTYSET_DATA_H_ */