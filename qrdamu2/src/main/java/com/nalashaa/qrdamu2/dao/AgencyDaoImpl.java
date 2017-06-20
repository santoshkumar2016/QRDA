
package com.nalashaa.qrdamu2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AgencyDaoImpl implements IFacilityDao {

    private static final Logger logger = LogManager.getLogger(AgencyDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
     * @SuppressWarnings("unchecked")
     * 
     * @Override public List<AgencyModel> findDetailsOfFacility(String userName)
     * { logger.info("Entered : FacilityDaoImpl - findDetailsOfFacility");
     * List<AgencyModel> facilityList = null; try { Query query =
     * entityManager.createNativeQuery(
     * "SELECT f.facilityName AS AgencyID, f.facilityName AS AgencyName, f.facilityPhone AS AgencyTelephone, f.facilityAddress AS AgencyAddress1, f.facilityCity AS AgencyCity, f.facilityState AS AgencyState, 'United States' AS AgencyCountry, f.facilityZip AS AgencyZip, upv.id AS Provider_NPI FROM facility f LEFT JOIN users u ON u.FACILITY = f.facilityId LEFT JOIN user_passport_values upv ON (upv.userId = :username AND userPassportId = '1') WHERE u.USERID = :username "
     * , AgencyModel.class); query.setParameter("username", userName);
     * facilityList = (List<AgencyModel>)query.getResultList(); for (AgencyModel
     * agencyModel : facilityList) {
     * System.out.println(agencyModel.getAgencyAddress1());
     * System.out.println(agencyModel.getAgencyName());
     * System.out.println(agencyModel.getProvider_NPI());
     * System.out.println(agencyModel.getAgencyCountry()); } } catch (Exception
     * e) { logger.error("Exception : FacilityDaoImpl - findDetailsOfFacility",
     * e); throw e; } logger.info(
     * "Exited : FacilityDaoImpl - findDetailsOfFacility"); return facilityList;
     * }
     */

    @Override
    public CachedRowSet findDetailsOfFacility(String userName) throws Exception {
        logger.info("Entered : FacilityDaoImpl - findDetailsOfFacility");
        CachedRowSet rowSet = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT f.facilityName AS AgencyID, f.facilityName AS AgencyName, f.facilityPhone AS AgencyTelephone, f.facilityAddress AS AgencyAddress1, f.facilityCity AS AgencyCity, f.facilityState AS AgencyState, 'United States' AS AgencyCountry, f.facilityZip AS AgencyZip, upv.id AS Provider_NPI FROM facility f LEFT JOIN users u ON u.FACILITY = f.facilityId LEFT JOIN user_passport_values upv ON (upv.userId = ? AND userPassportId = '1') WHERE u.USERID = ? ");
            statement.setString(1, userName);
            statement.setString(2, userName);
            ResultSet rs = statement.executeQuery();
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();
            rowSet = rowSetFactory.createCachedRowSet();
            rowSet.populate(rs);
        } catch (Exception e) {
            logger.error("Exception : FacilityDaoImpl - findDetailsOfFacility", e);
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
        logger.info("Exited : FacilityDaoImpl - findDetailsOfFacility");
        return rowSet;
    }

}
