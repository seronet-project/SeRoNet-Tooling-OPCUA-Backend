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
#ifndef COMMBASICOBJECTS_COMMFILEMETADATA_H_
#define COMMBASICOBJECTS_COMMFILEMETADATA_H_

#include "CommBasicObjects/CommFileMetaDataCore.hh"

namespace CommBasicObjects {
		
class CommFileMetaData : public CommFileMetaDataCore {
	public:
		// default constructors
		CommFileMetaData();
		
		/**
		 * Constructor to set all values.
		 * NOTE that you have to keep this constructor consistent with the model!
		 * Use  at your own choice.
		 *
		 * The preferred way to set values for initialization is:
		 *      CommRepository::MyCommObject obj;
		 *      obj.setX(1).setY(2).setZ(3)...;
		 */
		// CommFileMetaData(const std::string &filename, const unsigned int &filesize, const CommBasicObjects::FileType &filetype);
		
		CommFileMetaData(const CommFileMetaDataCore &commFileMetaData);
		CommFileMetaData(const DATATYPE &commFileMetaData);
		virtual ~CommFileMetaData();
		
		// use framework specific getter and setter methods from core (base) class
		using CommFileMetaDataCore::get;
		using CommFileMetaDataCore::set;
		
		//
		// feel free to add customized methods here
		//
};

inline std::ostream &operator<<(std::ostream &os, const CommFileMetaData &co)
{
	co.to_ostream(os);
	return os;
}
	
} /* namespace CommBasicObjects */
#endif /* COMMBASICOBJECTS_COMMFILEMETADATA_H_ */