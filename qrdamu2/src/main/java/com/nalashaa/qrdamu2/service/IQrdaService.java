
package com.nalashaa.qrdamu2.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.nalashaa.qrdamu2.dto.QrdaDto;

public interface IQrdaService {

    //public void saveFiles(HashMap<String, List<HashMap<String, String>>> locationFiles) throws IOException;

    public Map<String, Object> createMU2files(QrdaDto qrdaDto) throws Exception;

	public void importQrda(MultipartFile file);
}
