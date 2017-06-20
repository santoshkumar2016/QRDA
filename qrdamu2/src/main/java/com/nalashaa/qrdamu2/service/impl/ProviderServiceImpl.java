package com.nalashaa.qrdamu2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nalashaa.qrdamu2.dao.ProviderDao;
import com.nalashaa.qrdamu2.service.IProviderService;

@Service
public class ProviderServiceImpl implements IProviderService{

	@Autowired
	private ProviderDao providerDao;
	
	@Override
	public List<String> getProviders() {
		// TODO Auto-generated method stub
		return providerDao.getProviders();
	}

}
