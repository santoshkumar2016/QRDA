
package com.nalashaa.qrdamu2.dao;

import javax.sql.rowset.CachedRowSet;

public interface IFacilityDao {

    CachedRowSet findDetailsOfFacility(String userName) throws Exception;
}
