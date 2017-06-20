package com.nalashaa.qrdamu2.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class ProviderDao {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	public List<String> getProviders() {

		@SuppressWarnings("unchecked")
		List<String> providerList = (List<String>) em.createNativeQuery("SELECT DISTINCT OWNER FROM CALENDAR")
				.getResultList();
		
		//List<String> providerList = new ArrayList<String>();
		
		//List<String> providerList = (List<String>)
//				em.createNativeQuery("SELECT DISTINCT PROVIDERID FROM MU_QRDA_INPUTS")
//				.getResultList().forEach(item -> providerList.add(String.valueOf(item)));;
		return providerList;
	}
}
