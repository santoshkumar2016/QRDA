
package com.nalashaa.qrdamu2.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDao {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CachedRowSet getPatientDetails(String query) throws SQLException {
        Connection conn = null;
        conn = dataSource.getConnection();
        CachedRowSet rowSet = null;
        try {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(query);
        RowSetFactory rowSetFactory = RowSetProvider.newFactory();
        rowSet = rowSetFactory.createCachedRowSet();
        rowSet.populate(rs);
        } finally {
        	if(conn != null){
        		conn.close();
        	}
        }
        return rowSet;
    }
}
