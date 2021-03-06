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
#include "CommBasicObjects/CommIRScanCore.hh"

// serialization/deserialization operators
//#include "CommBasicObjects/CommIRScanACE.hh"

// include the hash.idl containing the hash constant
#include "hash.hh"
#include <assert.h>
#include <cstring>
#include <iostream>

// SmartUtils used in from_xml method
#include "smartKnuthMorrisPratt.hh"

#ifdef ENABLE_HASH
	#include <boost/functional/hash.hpp>
#endif

namespace CommBasicObjects 
{
	const char* CommIRScanCore::getCompiledHash()
	{
		return CommBasicObjectsIDL::REPO_HASH;
	}
	
	void CommIRScanCore::getAllHashValues(std::list<std::string> &hashes)
	{
		// get own hash value
		hashes.push_back(getCompiledHash());
		// get hash value(s) for CommBasicObjects::CommPose3d(idl_CommIRScan.poses3D)
		CommBasicObjects::CommPose3d::getAllHashValues(hashes);
		// get hash value(s) for CommBasicObjects::CommTimeStamp(idl_CommIRScan.timeStamp)
		CommBasicObjects::CommTimeStamp::getAllHashValues(hashes);
	}
	
	void CommIRScanCore::checkAllHashValues(std::list<std::string> &hashes)
	{
		// check own hash value
		if (strcmp(getCompiledHash(), hashes.front().c_str()) != 0)
		{
			std::cerr << "###################################################" << std::endl;
			std::cerr << "WARNING: HASHES OF COMMUNICATION OBJECTS MISSMATCH!" << std::endl;
			std::cerr << "CommIRScanCore hash" << std::endl;
			std::cerr << "Expected: " << getCompiledHash() << std::endl;
			std::cerr << "Received: " << hashes.front() << std::endl;
			std::cerr << "###################################################" << std::endl;
		}
		assert(strcmp(getCompiledHash(), hashes.front().c_str()) == 0);
		hashes.pop_front();
		
		// check hash value(s) for CommBasicObjects::CommPose3d(idl_CommIRScan.poses3D)
		CommBasicObjects::CommPose3d::checkAllHashValues(hashes);
		// check hash value(s) for CommBasicObjects::CommTimeStamp(idl_CommIRScan.timeStamp)
		CommBasicObjects::CommTimeStamp::checkAllHashValues(hashes);
	}
	
	#ifdef ENABLE_HASH
	size_t CommIRScanCore::generateDataHash(const DATATYPE &data)
	{
		size_t seed = 0;
		
		boost::hash_combine(seed, data.distance_min);
		boost::hash_combine(seed, data.distance_max);
		std::vector<CommBasicObjectsIDL::CommPose3d>::const_iterator data_poses3DIt;
		for(data_poses3DIt=data.poses3D.begin(); data_poses3DIt!=data.poses3D.end(); data_poses3DIt++) {
			seed += CommBasicObjects::CommPose3d::generateDataHash(*data_poses3DIt);
		}
		std::vector<ACE_CDR::Double>::const_iterator data_distancesIt;
		for(data_distancesIt=data.distances.begin(); data_distancesIt!=data.distances.end(); data_distancesIt++) {
			boost::hash_combine(seed, *data_distancesIt);
		}
		seed += CommBasicObjects::CommTimeStamp::generateDataHash(data.timeStamp);
		std::vector<ACE_CDR::Double>::const_iterator data_raw_readingsIt;
		for(data_raw_readingsIt=data.raw_readings.begin(); data_raw_readingsIt!=data.raw_readings.end(); data_raw_readingsIt++) {
			boost::hash_combine(seed, *data_raw_readingsIt);
		}
		
		return seed;
	}
	#endif
	
	// default constructor
	CommIRScanCore::CommIRScanCore()
	:	idl_CommIRScan()
	{  
		setDistance_min(0.04);
		setDistance_max(0.4);
		setPoses3D(std::vector<CommBasicObjects::CommPose3d>());
		setDistances(std::vector<double>());
		setTimeStamp(CommBasicObjects::CommTimeStamp());
		setRaw_readings(std::vector<double>());
	}
	
	CommIRScanCore::CommIRScanCore(const DATATYPE &data)
	:	idl_CommIRScan(data)
	{  }

	CommIRScanCore::~CommIRScanCore()
	{  }
	
	void CommIRScanCore::to_ostream(std::ostream &os) const
	{
	  os << "CommIRScan(";
	  os << getDistance_min() << " ";
	  os << getDistance_max() << " ";
	  std::vector<CommBasicObjects::CommPose3d>::const_iterator poses3DIt;
	  std::vector<CommBasicObjects::CommPose3d> poses3DList = getPoses3DCopy();
	  for(poses3DIt=poses3DList.begin(); poses3DIt!=poses3DList.end(); poses3DIt++) {
	  	poses3DIt->to_ostream(os);
	  }
	  std::vector<double>::const_iterator distancesIt;
	  std::vector<double> distancesList = getDistancesCopy();
	  for(distancesIt=distancesList.begin(); distancesIt!=distancesList.end(); distancesIt++) {
	  	os << *distancesIt << " ";
	  }
	  getTimeStamp().to_ostream(os);
	  std::vector<double>::const_iterator raw_readingsIt;
	  std::vector<double> raw_readingsList = getRaw_readingsCopy();
	  for(raw_readingsIt=raw_readingsList.begin(); raw_readingsIt!=raw_readingsList.end(); raw_readingsIt++) {
	  	os << *raw_readingsIt << " ";
	  }
	  os << ") ";
	}
	
	// convert to xml stream
	void CommIRScanCore::to_xml(std::ostream &os, const std::string &indent) const {
		size_t counter = 0;
		
		os << indent << "<distance_min>" << getDistance_min() << "</distance_min>";
		os << indent << "<distance_max>" << getDistance_max() << "</distance_max>";
		std::vector<CommBasicObjects::CommPose3d>::const_iterator poses3DIt;
		std::vector<CommBasicObjects::CommPose3d> poses3DList = getPoses3DCopy();
		counter = 0;
		os << indent << "<poses3DList n=\"" << poses3DList.size() << "\">";
		for(poses3DIt=poses3DList.begin(); poses3DIt!=poses3DList.end(); poses3DIt++) {
			os << indent << "<poses3D i=\"" << counter++ << "\">";
			poses3DIt->to_xml(os, indent);
			os << indent << "</poses3D>";
		}
		os << indent << "</poses3DList>";
		std::vector<double>::const_iterator distancesIt;
		std::vector<double> distancesList = getDistancesCopy();
		counter = 0;
		os << indent << "<distancesList n=\"" << distancesList.size() << "\">";
		for(distancesIt=distancesList.begin(); distancesIt!=distancesList.end(); distancesIt++) {
			os << indent << "<distances i=\"" << counter++ << "\">" << *distancesIt << "</distances>";
		}
		os << indent << "</distancesList>";
		os << indent << "<timeStamp>";
		getTimeStamp().to_xml(os, indent);
		os << indent << "</timeStamp>";
		std::vector<double>::const_iterator raw_readingsIt;
		std::vector<double> raw_readingsList = getRaw_readingsCopy();
		counter = 0;
		os << indent << "<raw_readingsList n=\"" << raw_readingsList.size() << "\">";
		for(raw_readingsIt=raw_readingsList.begin(); raw_readingsIt!=raw_readingsList.end(); raw_readingsIt++) {
			os << indent << "<raw_readings i=\"" << counter++ << "\">" << *raw_readingsIt << "</raw_readings>";
		}
		os << indent << "</raw_readingsList>";
	}
	
	// restore from xml stream
	void CommIRScanCore::from_xml(std::istream &is) {
		size_t counter = 0;
		
		static const Smart::KnuthMorrisPratt kmp_distance_min("<distance_min>");
		static const Smart::KnuthMorrisPratt kmp_distance_max("<distance_max>");
		static const Smart::KnuthMorrisPratt kmp_poses3DList("<poses3DList n=\"");
		static const Smart::KnuthMorrisPratt kmp_poses3D("\">");
		static const Smart::KnuthMorrisPratt kmp_distancesList("<distancesList n=\"");
		static const Smart::KnuthMorrisPratt kmp_distances("\">");
		static const Smart::KnuthMorrisPratt kmp_timeStamp("<timeStamp>");
		static const Smart::KnuthMorrisPratt kmp_raw_readingsList("<raw_readingsList n=\"");
		static const Smart::KnuthMorrisPratt kmp_raw_readings("\">");
		
		if(kmp_distance_min.search(is)) {
			double distance_minItem;
			is >> distance_minItem;
			setDistance_min(distance_minItem);
		}
		if(kmp_distance_max.search(is)) {
			double distance_maxItem;
			is >> distance_maxItem;
			setDistance_max(distance_maxItem);
		}
		if(kmp_poses3DList.search(is)) {
			size_t numberElements;
			is >> numberElements;
			CommBasicObjects::CommPose3d poses3DItem;
			std::vector<CommBasicObjects::CommPose3d> poses3DList;
			kmp_poses3D.search(is);
			for(counter=0; counter<numberElements; counter++) {
				if(kmp_poses3D.search(is)) {
					poses3DItem.from_xml(is);
					poses3DList.push_back(poses3DItem);
				}
			}
			setPoses3D(poses3DList);
		}
		if(kmp_distancesList.search(is)) {
			size_t numberElements;
			is >> numberElements;
			double distancesItem;
			std::vector<double> distancesList;
			kmp_distances.search(is);
			for(counter=0; counter<numberElements; counter++) {
				if(kmp_distances.search(is)) {
					is >> distancesItem;
					distancesList.push_back(distancesItem);
				}
			}
			setDistances(distancesList);
		}
		if(kmp_timeStamp.search(is)) {
			CommBasicObjects::CommTimeStamp timeStampItem;
			timeStampItem.from_xml(is);
			setTimeStamp(timeStampItem);
		}
		if(kmp_raw_readingsList.search(is)) {
			size_t numberElements;
			is >> numberElements;
			double raw_readingsItem;
			std::vector<double> raw_readingsList;
			kmp_raw_readings.search(is);
			for(counter=0; counter<numberElements; counter++) {
				if(kmp_raw_readings.search(is)) {
					is >> raw_readingsItem;
					raw_readingsList.push_back(raw_readingsItem);
				}
			}
			setRaw_readings(raw_readingsList);
		}
	}
	
	/*
	void CommIRScanCore::get(ACE_Message_Block *&msg) const
	{
		// start with a default internal buffer size(will automatically grow if needed)
		ACE_OutputCDR cdr(ACE_DEFAULT_CDR_BUFSIZE);
		
		CommBasicObjectsIDL::HashList hashes;
		getAllHashValues(hashes);
		cdr << static_cast<ACE_CDR::Long>(hashes.size());
		for(CommBasicObjectsIDL::HashList::const_iterator it=hashes.begin(); it!=hashes.end(); it++)
		{
			cdr << ACE_CString(it->c_str());
		}
		
		// Here the actual serialization takes place using the OutputCDR serialization operator<<
		// (see CommIRScanACE.hh)
		cdr << idl_CommIRScan;
		
	#ifdef ENABLE_HASH
		ACE_CDR::ULong data_hash = generateDataHash(idl_CommIRScan);
		cdr << data_hash;
		// std::cout << "CommIRScanCore: current data hash: " << data_hash << std::endl;
	#endif
		
		// return a shallow copy of the serialized message 
		// (no data is actually copied, only the internal reference counter is incremented)
		// in order to prevent memory leaks the caller of this get(msg) method must
		// manually free the memory by calling the release() method of the message block msg
		msg = cdr.begin()->duplicate();
	}
	
	void CommIRScanCore::set(const ACE_Message_Block *msg)
	{
		ACE_InputCDR cdr(msg);
	
		CommBasicObjectsIDL::HashList hashes;
		ACE_CDR::Long hashes_size;
		cdr >> hashes_size;
		for(int i=0; i<hashes_size; ++i) 
		{
			ACE_CString hash;
			cdr >> hash;
			hashes.push_back(hash.c_str());
		}
		checkAllHashValues(hashes);
		
		// Here the actual de-serialization takes place using the InputCDR serialization operator>>
		// (see CommIRScanACE.hh)
		cdr >> idl_CommIRScan;
		
	#ifdef ENABLE_HASH
		ACE_CDR::Long data_hash;
		cdr >> data_hash;
		ACE_CDR::Long own_hash = generateDataHash(idl_CommIRScan);
		assert(data_hash == own_hash);
		// std::cout << "CommIRScanCore: own data hash: " << own_hash << "; received data hash: " << data_hash << std::endl;
	#endif
	}
	*/
} /* namespace CommBasicObjects */
