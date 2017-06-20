
package com.nalashaa.qrdamu2.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.math.BigInteger;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.nalashaa.qrdamu2.dao.IFacilityDao;
import com.nalashaa.qrdamu2.exception.QrdaException;
import com.nalashaa.qrdamu2.model.AgencyModel;
import com.nalashaa.qrdamu2.model.ApiAttritubutesModel;
import com.nalashaa.qrdamu2.model.Concept;
import com.nalashaa.qrdamu2.model.ConceptList;
import com.nalashaa.qrdamu2.model.DenominatorExceptionsId;
import com.nalashaa.qrdamu2.model.DenominatorExclusionsId;
import com.nalashaa.qrdamu2.model.DenominatorPopulationId;
import com.nalashaa.qrdamu2.model.DescribedValueSet;
import com.nalashaa.qrdamu2.model.EntryRelationshipPatientEthnicity;
import com.nalashaa.qrdamu2.model.EntryRelationshipPatientRace;
import com.nalashaa.qrdamu2.model.EntryRelationshipPatientSex;
import com.nalashaa.qrdamu2.model.EntryRelationshipPaymentSource;
import com.nalashaa.qrdamu2.model.InitialPatientPopulationId;
import com.nalashaa.qrdamu2.model.Item;
import com.nalashaa.qrdamu2.model.Measure;
import com.nalashaa.qrdamu2.model.NumeratorId;
import com.nalashaa.qrdamu2.model.PatientDetailsModel;
import com.nalashaa.qrdamu2.model.ResultSetModel;
import com.nalashaa.qrdamu2.model.RetrieveMultipleValueSetsResponse;
import com.nalashaa.qrdamu2.model.StratumId;
import com.nalashaa.qrdamu2.model.TemplateCategory1;
import com.nalashaa.qrdamu2.model.TemplateCategory3;
import com.nalashaa.qrdamu2.model.ValueSet;
import com.nalashaa.qrdamu2.service.IQrdaJavaXMLConverterService;
import com.nalashaa.qrdamu2.util.ApplicationConstants;
import com.nalashaa.qrdamu2.util.ApplicationUtil;
import com.nalashaa.qrdamu2.util.Cat3Constants;
import com.nalashaa.qrdamu2.util.JavaXMLConverterUtil;
import com.nalashaa.qrdamu2.util.RestAPIUtil;
import com.nalashaa.qrdamu3.cat3.model.CD;
import com.nalashaa.qrdamu3.cat3.model.CE;
import com.nalashaa.qrdamu3.cat3.model.CS;
import com.nalashaa.qrdamu3.cat3.model.II;
import com.nalashaa.qrdamu3.cat3.model.INT;
import com.nalashaa.qrdamu3.cat3.model.IVLTS;
import com.nalashaa.qrdamu3.cat3.model.ON;
import com.nalashaa.qrdamu3.cat3.model.ObjectFactory;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Act;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040AssignedAuthor;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040AssignedCustodian;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040AssignedEntity;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Author;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040AuthoringDevice;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040ClinicalDocument;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Component2;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Component3;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Component4;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Custodian;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040CustodianOrganization;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040DocumentationOf;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Entry;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040EntryRelationship;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040ExternalDocument;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040ExternalObservation;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040InfrastructureRootTypeId;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040LegalAuthenticator;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Observation;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Organization;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Organizer;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040PatientRole;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Performer1;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040RecordTarget;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Reference;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040Section;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040ServiceEvent;
import com.nalashaa.qrdamu3.cat3.model.POCDMT000040StructuredBody;
import com.nalashaa.qrdamu3.cat3.model.StrucDocText;
import com.nalashaa.qrdamu3.cat3.model.TS;
import com.nalashaa.qrdamu3.cat3.model.XActClassDocumentEntryAct;
import com.nalashaa.qrdamu3.cat3.model.XActClassDocumentEntryOrganizer;
import com.nalashaa.qrdamu3.cat3.model.XActMoodDocumentObservation;
import com.nalashaa.qrdamu3.cat3.model.XActRelationshipEntryRelationship;
import com.nalashaa.qrdamu3.cat3.model.XActRelationshipExternalReference;
import com.nalashaa.qrdamu3.cat3.model.XDocumentActMood;
import com.nalashaa.qrdamu3.cat3.model.XServiceEventPerformer;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomListElement;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomListItemContentElement;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomListItemElement;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomTBodyElement;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomTHeadElement;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomTRElement;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomTableElement;
import com.nalashaa.qrdamu3.cat3.model.custom.CustomTextElement;

/**
 * 
 * @author prathap
 *
 */
@Service
@PropertySource("classpath:application.properties")
public class QrdaJavaXMLConverterServiceImpl implements IQrdaJavaXMLConverterService {

	private static final Logger logger = LogManager.getLogger(QrdaJavaXMLConverterServiceImpl.class);

	@Value("${qrda.template.cat1}")
	private String templateCat1;

	@Value("${qrda.template.cat3}")
	private String templateCat3;

	@Autowired
	private JavaXMLConverterUtil xmlConverterUtil;

	@Autowired
	private ValueSet valueSet;

	@Autowired
	private RestAPIUtil restAPIUtil;

	@Autowired
	private ApplicationUtil applicationUtil;

	@Autowired
	private IFacilityDao facilityDao;

	@Cacheable("TemplateCategory1")
	private TemplateCategory1 generateTemplateCategory1Class() throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory1Class");
		TemplateCategory1 templateCategory1 = null;
		try {
			File file = new ClassPathResource(templateCat1).getFile();
			JAXBContext jaxbContext = JAXBContext.newInstance(TemplateCategory1.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			templateCategory1 = (TemplateCategory1) jaxbUnmarshaller.unmarshal(file);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory1Class", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory1Class");
		return templateCategory1;
	}

	@Cacheable("TemplateCategory3")
	private TemplateCategory3 generateTemplateCategory3Class() throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory3Class");
		TemplateCategory3 templateCategory3 = null;
		try {
			File file = new ClassPathResource(templateCat3).getFile();
			JAXBContext jaxbContext = JAXBContext.newInstance(TemplateCategory3.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			templateCategory3 = (TemplateCategory3) jaxbUnmarshaller.unmarshal(file);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory3Class", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory3Class");
		return templateCategory3;
	}

	/**
	 * This function will generate XML dynamically and returns the String format
	 * of XML
	 */
	private String generateTemplateCategory1Xml(Map<String, String> valueMap, String convertedQrdaEntriesXmlString,
			String measureData) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory1Xml");
		String templateXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		try {
			TemplateCategory1 templateCategory1 = generateTemplateCategory1Class();

			String convertedQrdaHeaderXmlString = "";
			if (valueMap != null) {
				convertedQrdaHeaderXmlString = getHeaderForTemplateCat1(templateCategory1, valueMap);
			}
			templateXml += convertedQrdaHeaderXmlString;
			templateXml = templateXml.replaceFirst("(?s)<ENTRY[^>]*>.*?</ENTRY>", convertedQrdaEntriesXmlString);
			templateXml = templateXml.replaceFirst("(?s)<MEASUREDATA[^>]*>.*?</MEASUREDATA>", measureData);
			templateXml = templateXml.replace("<id extension=\"{%%authorIdExtension%%}\"", "<id nullFlavor=\"UNK\"");
			templateXml = templateXml.replace(
					"<telecom use=\"{%%authorTelecomUse%%}\" value=\"{%%authorTelecomValue%%}\"",
					"<telecom nullFlavor=\"NI\"");
			templateXml = templateXml.replaceAll("\\{%%.*?%%\\}", "");
			templateXml = templateXml.replace("<low value=\"\"", "<low nullFlavor=\"NI\"");
			templateXml = templateXml.replace("<high value=\"\"", "<high nullFlavor=\"NI\"");
			templateXml = templateXml.replace("extension=\"\"", "nullFlavor=\"NI\"");
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory1Xml", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory1Xml");
		return templateXml;
	}

	/**
	 * This function will generate XML dynamically and returns the String format
	 * of XML
	 */
	private String generateTemplateCategory3Xml(Map<String, String> valueMap) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory3Xml");
		String templateXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		try {
			TemplateCategory3 templateCategory3 = generateTemplateCategory3Class();

			String convertedQrdaHeaderXmlString = "";
			if (valueMap != null) {
				convertedQrdaHeaderXmlString = getHeaderForTemplateCat3(templateCategory3, valueMap);
			}
			templateXml += convertedQrdaHeaderXmlString;
			templateXml = templateXml.replace("<id extension=\"{%%authorIdExtension%%}\"", "<id nullFlavor=\"UNK\"");
			templateXml = templateXml.replace(
					"<telecom use=\"{%%authorTelecomUse%%}\" value=\"{%%authorTelecomValue%%}\"",
					"<telecom nullFlavor=\"NI\"");
			templateXml = templateXml.replaceAll("\\{%%.*?%%\\}", "");
			templateXml = templateXml.replace("<low value=\"\"", "<low nullFlavor=\"NI\"");
			templateXml = templateXml.replace("<high value=\"\"", "<high nullFlavor=\"NI\"");
			templateXml = templateXml.replace("extension=\"\"", "nullFlavor=\"NI\"");
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory3Xml", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - generateTemplateCategory3Xml");
		return templateXml;
	}

	/**
	 * 
	 * @param templateCategory1
	 * @param valueMap
	 * @return This function will generate Header XML dynamically and returns
	 *         the String format of XML
	 */
	private String getHeaderForTemplateCat1(TemplateCategory1 templateCategory1, Map<String, String> valueMap) {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getHeaderForTemplateCat1");
		String convertedXmlString = "";
		try {
			String qrdaHeader = templateCategory1.getQRDA();
			convertedXmlString = xmlConverterUtil.replaceWithValues(qrdaHeader, valueMap);

			// replace patient data
			// convertedXmlString = convertedXmlString.replace("'{%%" +
			// ApplicationConstants.PATIENT_DATA + "%%}'",
			// patientDataString.toString());

		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getHeaderForTemplateCat1", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getHeaderForTemplateCat1");
		return convertedXmlString;
	}

	/**
	 * 
	 * @param templateCategory1
	 * @param valueMap
	 * @return This function will generate Header XML dynamically and returns
	 *         the String format of XML
	 */
	private String getHeaderForTemplateCat3(TemplateCategory3 templateCategory3, Map<String, String> valueMap) {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getHeaderForTemplateCat3");
		String convertedXmlString = "";
		try {
			String qrdaHeader = templateCategory3.getQRDA();
			convertedXmlString = xmlConverterUtil.replaceWithValues(qrdaHeader, valueMap);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getHeaderForTemplateCat3", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getHeaderForTemplateCat3");
		return convertedXmlString;
	}

	/**
	 * 
	 * @param templateCategory1
	 * @param valueMap
	 * @return This function will generate Entries of XML dynamically and
	 *         returns the String format of XML
	 * @throws Exception
	 */
	private String getEntriesForTemplateCat1(String entryType, Map<String, String> valueMap) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getEntriesForTemplateCat1");
		String convertedXmlString = "";
		String qrdaEntry = "";
		try {
			TemplateCategory1 templateCategory1 = generateTemplateCategory1Class();
			if (!StringUtils.isEmpty(entryType)) {
				if (ApplicationConstants.ENCOUNTER_PERFORMED.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getEncounter_Performed();
				} else if (ApplicationConstants.INTERVENTION_PERFORMED.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getIntervention_Performed();
				} else if (ApplicationConstants.DIAGNOSIS_ACTIVE.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getDiagnosis_Active();
				} else if (ApplicationConstants.MEDICATION_DISPENSED.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getMedication_Dispensed();
				} else if (ApplicationConstants.MEDICATION_ACTIVE.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getMedication_Active();
				} else if (ApplicationConstants.PROCEDURE_PERFORMED.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getProcedure_Performed();
				} else if (ApplicationConstants.MEDICATION_ORDER.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getMedication_Order();
				} else if (ApplicationConstants.PHYSICAL_EXAM.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getPhysical_Exam();
				} else if (ApplicationConstants.COMMUNICATION_FROM_PROVIDER_TO_PROVIDER.equalsIgnoreCase(entryType)) {
					qrdaEntry = templateCategory1.getCommunication_From_Provider_to_Provider();
				}
			}
			convertedXmlString = xmlConverterUtil.replaceWithValues(qrdaEntry, valueMap);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getEntriesForTemplateCat1", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getEntriesForTemplateCat1");
		return convertedXmlString;
	}

	@Override
	public Map<String, Object> processTemplate1Xml(CachedRowSet cachedRowSet,
			RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse, String providerId, String startDate,
			String endDate) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - processTemplate1Xml");
		Map<String, Object> patientXmlStringMap = new HashMap<>();
		try {
			List<ResultSetModel> resultSetModellist = processResultSet(cachedRowSet);
			if (!CollectionUtils.isEmpty(resultSetModellist)) {
				Map<Integer, List<ResultSetModel>> resultSetModelMap = getUniquePatients(resultSetModellist);
				patientXmlStringMap = generateTemplate1XmlWithEnties(resultSetModelMap,
						retrieveMultipleValueSetsResponse, providerId, startDate, endDate);
			}
		} catch (SQLException sqlException) {
			logger.error("SQLException : QrdaJavaXMLConverterServiceImpl - processTemplate1Xml", sqlException);
			throw sqlException;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - processTemplate1Xml", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - processTemplate1Xml");
		/*
		 * if (CollectionUtils.isEmpty(patientXmlStringMap)) { throw new
		 * QrdaException("File Details is Empty"); }
		 */
		return patientXmlStringMap;
	}

	@Override
	public String processTemplate3Xml(String providerId, String startDate, String endDate, List<String> measureList)
			throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - processTemplate3Xml");
		String xmlContent = "";
		Map<String, String> valueMap = new HashMap<>();
		try {
			valueMap = generateMapForAgency(providerId);
			valueMap.putAll(getMapWithXmlKeyValuesForHeader(startDate, endDate));
			String measureEntries = "";
			if (!CollectionUtils.isEmpty(measureList)) {
				for (String measureId : measureList) {
					Map<String, String> measureValues = getMeasureDataInValues(measureId);
					measureEntries += getMeasureDataInStringCat3(measureValues, measureId);
				}
			}
			valueMap.put(ApplicationConstants.ENTRY_DATA, measureEntries);
			xmlContent = generateTemplateCategory3Xml(valueMap);
		} catch (SQLException sqlException) {
			logger.error("SQLException : QrdaJavaXMLConverterServiceImpl - processTemplate3Xml", sqlException);
			throw sqlException;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - processTemplate3Xml", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - processTemplate3Xml");
		if (StringUtils.isEmpty(xmlContent)) {
			throw new QrdaException("File Details is Empty");
		}
		return xmlContent;
	}

	private Map<String, Object> generateTemplate1XmlWithEnties(Map<Integer, List<ResultSetModel>> resultSetModelMap,
			RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse, String providerId, String startDate,
			String endDate) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - generateTemplate1XmlWithEnties");
		Map<String, Object> generateTemplate1XmlWithEntiesMap = new HashMap<>();
		HashMap<String, String> xmlKeyValue = new HashMap<>();
		String measureDataString = "";
		String firtName = "";
		String lastName = "";
		ArrayList<PatientDetailsModel> patientDetailsModelList = new ArrayList<>();
		try {
			Iterator<Entry<Integer, List<ResultSetModel>>> it = resultSetModelMap.entrySet().iterator();
			HashMap<String, String> xmlFacilityKeyValue = generateMapForAgency(providerId);
			patientDataString = new StringBuffer("patientDataString");
			while (it.hasNext()) {
				Map.Entry<Integer, List<ResultSetModel>> pair = (Map.Entry<Integer, List<ResultSetModel>>) it.next();
				List<ResultSetModel> resultSetModelList = resultSetModelMap.get(pair.getKey());
				String stringEntries = "";
				for (int i = 0; i < resultSetModelList.size(); i++) {
					ResultSetModel resultSetModel = resultSetModelList.get(i);
					if (i == 0) {
						xmlKeyValue = getMapWithXmlKeyValues(resultSetModel, startDate, endDate);
						xmlKeyValue.putAll(xmlFacilityKeyValue);

						String measureId = resultSetModel.getMeasureID();
						Map<String, String> valueMap = getMeasureDataInValues(measureId);
						measureDataString = getMeasureDataInString(valueMap);
						xmlKeyValue.putAll(valueMap);
						firtName = resultSetModel.getPatientNameFirst();
						lastName = resultSetModel.getPatientLastName();

						PatientDetailsModel patientDetailsModel = new PatientDetailsModel();
						patientDetailsModel.setProvider(providerId);
						patientDetailsModel.setCqm(measureId);
						patientDetailsModel.setFirstName(firtName);
						patientDetailsModel.setLastName(lastName);
						patientDetailsModel.setCid(String.valueOf(resultSetModel.getClientId()));
						patientDetailsModel.setGender(resultSetModel.getPatientGenderName());
						patientDetailsModel
								.setBirthDate(applicationUtil.formatToDateString(resultSetModel.getPatientBirthDate()));
						patientDetailsModelList.add(patientDetailsModel);
					}
					stringEntries += createStringEntries(resultSetModel, retrieveMultipleValueSetsResponse);
				}
				String template1XmlString = generateTemplateCategory1Xml(xmlKeyValue, stringEntries, measureDataString);
				// System.out.println(template1XmlString);
				generateTemplate1XmlWithEntiesMap.put(pair.getKey() + "_" + firtName + "_" + lastName,
						template1XmlString);
			}
			generateTemplate1XmlWithEntiesMap.put("cat1PatientDetails", patientDetailsModelList);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - generateTemplate1XmlWithEnties", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - generateTemplate1XmlWithEnties");
		return generateTemplate1XmlWithEntiesMap;
	}

	private HashMap<String, String> generateMapForAgency(String providerId) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - generateMapForAgency");
		HashMap<String, String> agencyMap = new HashMap<>();
		try {
			CachedRowSet cachedRowSet = getProviderDetails(providerId);
			ResultSetMetaData metaData = cachedRowSet.getMetaData();
			int count = metaData.getColumnCount(); // number of column
			List<AgencyModel> agencyModelList = new ArrayList<>();
			while (cachedRowSet.next()) {
				AgencyModel agencyModel = new AgencyModel();
				for (int i = 1; i <= count; i++) {
					if (ApplicationConstants.AGENCYID.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						// organizationtin
						agencyModel.setAgencyID(cachedRowSet.getString(metaData.getColumnName(i)));
					} else if (ApplicationConstants.AGENCYNAME.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						// blank
						agencyModel.setAgencyName(cachedRowSet.getString(i));
					} else if (ApplicationConstants.AGENCYTELEPHONE.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						// providertelephone
						agencyModel.setAgencyTelephone(cachedRowSet.getString(i));
					} else if (ApplicationConstants.AGENCYADDRESS1.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						// prov addre line
						agencyModel.setAgencyAddress1(cachedRowSet.getString(i));
					} else if (ApplicationConstants.AGENCYCITY.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						agencyModel.setAgencyCity(cachedRowSet.getString(i));
					} else if (ApplicationConstants.AGENCYSTATE.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						agencyModel.setAgencyState(cachedRowSet.getString(i));
					} else if (ApplicationConstants.AGENCYCOUNTRY.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						agencyModel.setAgencyCountry(cachedRowSet.getString(i));
					} else if (ApplicationConstants.AGENCYZIP.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						agencyModel.setAgencyZip(cachedRowSet.getString(i));
					} else if (ApplicationConstants.PROVIDER_NPI.equalsIgnoreCase(metaData.getColumnLabel(i))
							&& !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i)))) {
						agencyModel.setProvider_NPI(cachedRowSet.getString(i));
					}
				}
				agencyModelList.add(agencyModel);
			}
			if (!CollectionUtils.isEmpty(agencyModelList)) {
				AgencyModel agencyModel = agencyModelList.get(0);
				if (!StringUtils.isEmpty(agencyModel.getAgencyID())) {
					agencyMap.put(ApplicationConstants.AUTHOR_ID_EXTENSION, agencyModel.getAgencyID());
				}
				if (!StringUtils.isEmpty(agencyModel.getAgencyName())) {
					agencyMap.put(ApplicationConstants.CUSTODIAN_NAME, agencyModel.getAgencyName());
					agencyMap.put(ApplicationConstants.LEGAL_AUTHENTICATOR_NAME, agencyModel.getAgencyName());
				}
				if (!StringUtils.isEmpty(agencyModel.getAgencyTelephone())) {
					agencyMap.put(ApplicationConstants.LEGAL_AUTHENTICATOR_TELECOM_VALUE,
							agencyModel.getAgencyTelephone());
				}
				if (!StringUtils.isEmpty(agencyModel.getAgencyAddress1())) {
					agencyMap.put(ApplicationConstants.LEGAL_AUTHENTICATOR_STREET_ADDRESS_LINE,
							agencyModel.getAgencyAddress1());
					agencyMap.put(ApplicationConstants.AUTHOR_STREET_ADDRESS_LINE, agencyModel.getAgencyAddress1());
					agencyMap.put(ApplicationConstants.CUSTODIAN_STREET_ADDRESS_LINE, agencyModel.getAgencyAddress1());
				}
				if (!StringUtils.isEmpty(agencyModel.getAgencyCity())) {
					agencyMap.put(ApplicationConstants.LEGAL_AUTHENTICATOR_CITY, agencyModel.getAgencyCity());
					agencyMap.put(ApplicationConstants.AUTHOR_CITY, agencyModel.getAgencyCity());
					agencyMap.put(ApplicationConstants.CUSTODIAN_CITY, agencyModel.getAgencyCity());
				}
				if (!StringUtils.isEmpty(agencyModel.getAgencyState())) {
					agencyMap.put(ApplicationConstants.LEGAL_AUTHENTICATOR_STATE, agencyModel.getAgencyState());
					agencyMap.put(ApplicationConstants.AUTHOR_STATE, agencyModel.getAgencyState());
					agencyMap.put(ApplicationConstants.CUSTODIAN_STATE, agencyModel.getAgencyState());
				}
				if (!StringUtils.isEmpty(agencyModel.getAgencyCountry())) {
					agencyMap.put(ApplicationConstants.LEGAL_AUTHENTICATOR_COUNTRY, agencyModel.getAgencyCountry());
					agencyMap.put(ApplicationConstants.AUTHOR_COUNTRY, agencyModel.getAgencyCountry());
					agencyMap.put(ApplicationConstants.CUSTODIAN_COUNTRY, agencyModel.getAgencyCountry());
				}
				if (!StringUtils.isEmpty(agencyModel.getAgencyZip())) {
					agencyMap.put(ApplicationConstants.LEGAL_AUTHENTICATOR_POSTAL_CODE, agencyModel.getAgencyZip());
					agencyMap.put(ApplicationConstants.AUTHOR_POSTAL_CODE, agencyModel.getAgencyZip());
					agencyMap.put(ApplicationConstants.CUSTODIAN_POSTAL_CODE, agencyModel.getAgencyZip());
				}
				if (!StringUtils.isEmpty(agencyModel.getProvider_NPI())) {
					agencyMap.put(ApplicationConstants.PROVIDER_NPI, agencyModel.getProvider_NPI());
					agencyMap.put(ApplicationConstants.DOCUMENTATION_OF_NPI, agencyModel.getAgencyZip());
				}
			}
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - generateMapForAgency", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - generateMapForAgency");
		return agencyMap;
	}

	private Map<String, String> getMeasureDataInValues(String measureId) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getMeasureDataInValues");
		Map<String, String> valueMap = new HashMap<>();
		try {
			Measure measure = restAPIUtil.getMeasureById(measureId);
			if (measure != null) {
				if (!StringUtils.isEmpty(measure.getTitle()))
					valueMap.put(ApplicationConstants.MEASURE_TITLE, measure.getTitle());
				else
					valueMap.put(ApplicationConstants.MEASURE_TITLE, "");
				if (!StringUtils.isEmpty(measure.getVersionNeutralIdentifier()))
					valueMap.put(ApplicationConstants.MEASURE_VERSION_NEUTRAL_IDENTIFIER,
							measure.getVersionNeutralIdentifier());
				else
					valueMap.put(ApplicationConstants.MEASURE_VERSION_NEUTRAL_IDENTIFIER, "");
				if (!StringUtils.isEmpty(measure.getVersionNumber()))
					valueMap.put(ApplicationConstants.MEASURE_VERSION_NUMBER, measure.getVersionNumber());
				else
					valueMap.put(ApplicationConstants.MEASURE_VERSION_NUMBER, "");
				if (!StringUtils.isEmpty(measure.getVersionSpecificMeasureId()))
					valueMap.put(ApplicationConstants.MEASURE_VERSION_SPECIFIC_IDENTIFIER,
							measure.getVersionSpecificMeasureId());
				else
					valueMap.put(ApplicationConstants.MEASURE_VERSION_SPECIFIC_IDENTIFIER, "");
				if (!StringUtils.isEmpty(applicationUtil.generateUUID()))
					valueMap.put(ApplicationConstants.MEASURE_RANDOM_NUMBER, applicationUtil.generateUUID());
				else
					valueMap.put(ApplicationConstants.MEASURE_RANDOM_NUMBER, "");

			}
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getMeasureDataInValues", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getMeasureDataInValues");
		return valueMap;
	}

	private String getMeasureDataInString(Map<String, String> valueMap) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getMeasureDataInString");
		String convertedXmlString = "";
		try {
			TemplateCategory1 templateCategory1 = generateTemplateCategory1Class();
			String measureData = templateCategory1.getMEASUREDATA();
			convertedXmlString = xmlConverterUtil.replaceWithValues(measureData, valueMap);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getMeasureDataInString", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getMeasureDataInString");
		return convertedXmlString;
	}

	private String getMeasureDataInStringCat3(Map<String, String> valueMap, String measureId) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getMeasureDataInStringCat3");
		String convertedXmlString = "";
		try {
			TemplateCategory3 templateCategory3 = generateTemplateCategory3Class();
			String measureData = templateCategory3.getEntryData();
			String componentData = getComponentData(templateCategory3, measureId);
			valueMap.put(ApplicationConstants.OBSERVATION_COMPONENT, componentData);

			/*
			 * To get XML for Performance rate And Reporting Rate for every
			 * measure
			 */
			// String performanceRateString =
			// getPerformanceRate(templateCategory3);
			String reportingRateString = getReportingRate(templateCategory3);
			// valueMap.put(ApplicationConstants.PERFORMANCE_RATE_COMPONENT,
			// performanceRateString);
			valueMap.put(ApplicationConstants.REPORTING_RATE_COMPONENT, reportingRateString);

			convertedXmlString += xmlConverterUtil.replaceWithValues(measureData, valueMap);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getMeasureDataInStringCat3", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getMeasureDataInStringCat3");
		return convertedXmlString;
	}

	private String getPerformanceRate(TemplateCategory3 templateCategory3) {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getPerformanceRate");
		String convertedXmlString = "";
		try {
			convertedXmlString = templateCategory3.getComponentPerformanceRate();
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getPerformanceRate", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getPerformanceRate");
		return convertedXmlString;
	}

	private String getReportingRate(TemplateCategory3 templateCategory3) {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getReportingRate");
		String convertedXmlString = "";
		try {
			convertedXmlString = templateCategory3.getComponentreportingRate();
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getReportingRate", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getReportingRate");
		return convertedXmlString;
	}

	private String getComponentData(TemplateCategory3 templateCategory3, String measureId) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getComponentData");
		String convertedXmlString = "";
		try {
			if (!StringUtils.isEmpty(templateCategory3.getComponentIpp())) {
				Measure measure = restAPIUtil.getMeasureById(measureId);
				if (measure != null) {
					// IPP
					InitialPatientPopulationId initialPatientPopulation = measure.getInitialPatientPopulationId();
					if (initialPatientPopulation != null) {
						List<Item> itemlist = initialPatientPopulation.getItem();
						for (Item item : itemlist) {
							Map<String, String> valueMap = new HashMap<>();
							valueMap.put(ApplicationConstants.IPP_GUID, item.getValue());
							valueMap.put(ApplicationConstants.IPP_COUNT, String.valueOf(getIppCount()));
							String component = templateCategory3.getComponentIpp();

							/* Get Entry relationship string xml's */
							String entryRelationships = getEntryRelationshipStringXml(templateCategory3, measure);
							valueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP, entryRelationships);
							convertedXmlString += xmlConverterUtil.replaceWithValues(component, valueMap);
						}
					}

					// Denominator population
					DenominatorPopulationId denominatorPopulation = measure.getDenominatorPopulationId();
					if (denominatorPopulation != null) {
						List<Item> itemlist = denominatorPopulation.getItem();
						for (Item item : itemlist) {
							Map<String, String> valueMap = new HashMap<>();
							valueMap.put(ApplicationConstants.DENOMINATOR_COUNT, String.valueOf(getDenominatorCount()));
							valueMap.put(ApplicationConstants.DENOMINATOR_GUID, item.getValue());
							String component = templateCategory3.getComponentDenominator();

							/* Get Entry relationship string xmls */
							String entryRelationships = getEntryRelationshipStringXml(templateCategory3, measure);
							valueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP, entryRelationships);
							convertedXmlString += xmlConverterUtil.replaceWithValues(component, valueMap);
						}
					}

					// Denominator Exclusion
					DenominatorExclusionsId denominatorExclusion = measure.getDenominatorExclusionsID();
					if (denominatorExclusion != null) {
						List<Item> itemlist = denominatorExclusion.getItem();
						for (Item item : itemlist) {
							if (!StringUtils.isEmpty(item.getValue())) {
								Map<String, String> valueMap = new HashMap<>();
								valueMap.put(ApplicationConstants.DENOMINATOR_EXCLUSIONS_COUNT,
										String.valueOf(getDenominatorExclusionsCount()));
								valueMap.put(ApplicationConstants.DENOMINATOR_EXCLUSIONS_GUID, item.getValue());
								String component = templateCategory3.getComponentDenominatorExclusions();

								/* Get Entry relationship string xmls */
								String entryRelationships = getEntryRelationshipStringXml(templateCategory3, measure);
								valueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP, entryRelationships);
								convertedXmlString += xmlConverterUtil.replaceWithValues(component, valueMap);
							}
						}
					}

					// Numerator Id
					NumeratorId numerator = measure.getNumeratorId();
					if (numerator != null) {
						List<Item> itemlist = numerator.getItem();
						for (Item item : itemlist) {
							Map<String, String> valueMap = new HashMap<>();
							valueMap.put(ApplicationConstants.NUMERATOR_COUNT, String.valueOf(getNumeratorCount()));
							valueMap.put(ApplicationConstants.NUMERATOR_GUID, item.getValue());
							String component = templateCategory3.getComponentNumerator();

							/* Get Entry relationship string xmls */
							String entryRelationships = getEntryRelationshipStringXml(templateCategory3, measure);
							valueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP, entryRelationships);
							convertedXmlString += xmlConverterUtil.replaceWithValues(component, valueMap);
						}
					}

					// Denominator Exception Id
					DenominatorExceptionsId denominatorExceptions = measure.getDenominatorExceptionsId();
					if (denominatorExceptions != null) {
						List<Item> itemlist = denominatorExceptions.getItem();
						for (Item item : itemlist) {
							if (!StringUtils.isEmpty(item.getValue())) {
								Map<String, String> valueMap = new HashMap<>();
								valueMap.put(ApplicationConstants.DENOMINATOR_EXCEPTION_COUNT,
										String.valueOf(getDenominatorExceptionCount()));
								valueMap.put(ApplicationConstants.DENOMINATOR_EXCEPTION_GUID, item.getValue());
								String component = templateCategory3.getComponentDenominatorException();

								/* Get Entry relationship string xmls */
								String entryRelationships = getEntryRelationshipStringXml(templateCategory3, measure);
								valueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP, entryRelationships);
								convertedXmlString += xmlConverterUtil.replaceWithValues(component, valueMap);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getComponentData", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getComponentData");
		return convertedXmlString;
	}

	StringBuffer patientDataString = new StringBuffer();

	private String createStringEntries(ResultSetModel resultSetModel,
			RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - createStringEntries");
		HashMap<String, String> xmlKeyValue = new HashMap<>();
		String convertedQrdaEntriesXmlString = "";
		try {
			ApiAttritubutesModel apiAttritubutesModel = getEntryName(resultSetModel.getCodes(),
					retrieveMultipleValueSetsResponse);

			String generatedUUID = applicationUtil.generateUUID();
			if (!StringUtils.isEmpty(generatedUUID))
				xmlKeyValue.put(ApplicationConstants.RANDOM_NUMBER, generatedUUID);
			if (!StringUtils.isEmpty(apiAttritubutesModel.getCodeSystem()))
				xmlKeyValue.put(ApplicationConstants.CODE_SYSTEM, apiAttritubutesModel.getCodeSystem());
			if (!StringUtils.isEmpty(apiAttritubutesModel.getDescribedId()))
				xmlKeyValue.put(ApplicationConstants.VALUE_SET, apiAttritubutesModel.getDescribedId());
			if (!StringUtils.isEmpty(apiAttritubutesModel.getConceptDisplayName()))
				xmlKeyValue.put(ApplicationConstants.TEXT, apiAttritubutesModel.getConceptDisplayName());
			if (!StringUtils.isEmpty(apiAttritubutesModel.getDisplayName()))
				xmlKeyValue.put(ApplicationConstants.DISPLAY_NAME, apiAttritubutesModel.getDisplayName());
			if (resultSetModel.getStartDate() != null) {
				xmlKeyValue.put(ApplicationConstants.START_DATE,
						String.valueOf(applicationUtil.formatDateTime(resultSetModel.getStartDate())));
			}
			if (resultSetModel.getEndDate() != null) {
				xmlKeyValue.put(ApplicationConstants.END_DATE,
						String.valueOf(applicationUtil.formatDateTime(resultSetModel.getEndDate())));
			}
			if (!StringUtils.isEmpty(resultSetModel.getCodes())) {
				xmlKeyValue.put(ApplicationConstants.CODE, resultSetModel.getCodes());

				// for MU3

				xmlKeyValue.put(ApplicationConstants.GUID, applicationUtil.generateUUID());

				if (!StringUtils.isEmpty(apiAttritubutesModel.getDisplayName())) {

					// create patient data string
					String patientData = "<tr><td>code</td><td>DisplayName</td><td>startDate</td>" + "<td>endDate</td>"
							+ "<td>DataValue</td>" + "</tr>";

					patientData = patientData.replace("code", resultSetModel.getCodes());
					patientData = patientData.replace("DisplayName", apiAttritubutesModel.getDisplayName());
					patientData = patientData.replace("startDate",
							String.valueOf(applicationUtil.formatDateTime(resultSetModel.getStartDate())));
					patientData = patientData.replace("endDate",
							String.valueOf(applicationUtil.formatDateTime(resultSetModel.getEndDate())));
					patientData = patientData.replace("DataValue", resultSetModel.getDataElement());
					patientDataString.append(patientData);

					convertedQrdaEntriesXmlString = getEntriesForTemplateCat1(apiAttritubutesModel.getDisplayName(),
							xmlKeyValue);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - createStringEntries", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - createStringEntries");
		return convertedQrdaEntriesXmlString;
	}

	private ApiAttritubutesModel getEntryName(String codes,
			RetrieveMultipleValueSetsResponse retrieveMultipleValueSetsResponse) {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getEntryName");
		String entryName = "";
		ApiAttritubutesModel apiAttritubutesModel = new ApiAttritubutesModel();
		try {
			if (retrieveMultipleValueSetsResponse != null
					&& !CollectionUtils.isEmpty(retrieveMultipleValueSetsResponse.getDescribedValueSet())) {
				List<DescribedValueSet> describedValueSetList = retrieveMultipleValueSetsResponse
						.getDescribedValueSet();
				String displayName = "";

				for (DescribedValueSet describedValueSet : describedValueSetList) {
					List<ConceptList> conceptListList = describedValueSet.getConceptList();
					if (conceptListList != null && !CollectionUtils.isEmpty(conceptListList)) {
						for (ConceptList conceptList2 : conceptListList) {
							List<Concept> conceptList = conceptList2.getConcept();
							for (Concept concept : conceptList) {
								String conceptCode = concept.getCode();
								if (codes.equalsIgnoreCase(conceptCode)) {
									displayName = describedValueSet.getDisplayName();
									apiAttritubutesModel.setConceptDisplayName(concept.getDisplayName());
									apiAttritubutesModel.setDescribedId(describedValueSet.getId());
									apiAttritubutesModel.setCodeSystem(concept.getCodeSystem());
									break;
								}
							}
						}
					}
				}
				if (!StringUtils.isEmpty(displayName)) {
					Map<String, String> valueSetMap = valueSet.getConstantsMap();
					if (!CollectionUtils.isEmpty(valueSetMap) && valueSetMap.containsKey(displayName)) {
						entryName = valueSetMap.get(displayName);
						apiAttritubutesModel.setDisplayName(entryName);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getEntryName", e);
			throw e;
		}
		logger.info("Exiting : QrdaJavaXMLConverterServiceImpl - getEntryName");
		return apiAttritubutesModel;
	}

	private HashMap<String, String> getMapWithXmlKeyValues(ResultSetModel resultSetModel, String startDate,
			String endDate) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getMapWithXmlKeyValues");
		HashMap<String, String> xmlKeyValue = new HashMap<>();
		try {
			xmlKeyValue = getMapWithXmlKeyValuesForHeader(startDate, endDate);
			// xmlKeyValue.put(ApplicationConstants.EFFECTIVE_TIME,
			// applicationUtil.formatDateTime(new Date()));
			if (!StringUtils.isEmpty(resultSetModel.getClientId()))
				xmlKeyValue.put(ApplicationConstants.ID_EXTENSION, String.valueOf(resultSetModel.getClientId()));
			if (!StringUtils.isEmpty(resultSetModel.getPatientAddressType()))
				xmlKeyValue.put(ApplicationConstants.ADDR_USE, resultSetModel.getPatientAddressType());
			if (!StringUtils.isEmpty(resultSetModel.getPatientAddressLine1()))
				xmlKeyValue.put(ApplicationConstants.STREET_ADDRESS_LINE, resultSetModel.getPatientAddressLine1());
			if (!StringUtils.isEmpty(resultSetModel.getPatientAddressCity()))
				xmlKeyValue.put(ApplicationConstants.CITY, resultSetModel.getPatientAddressCity());
			if (!StringUtils.isEmpty(resultSetModel.getPatientAddressState()))
				xmlKeyValue.put(ApplicationConstants.STATE, resultSetModel.getPatientAddressState());
			if (!StringUtils.isEmpty(resultSetModel.getPatientAddressPostalCode()))
				xmlKeyValue.put(ApplicationConstants.POSTAL_CODE, resultSetModel.getPatientAddressPostalCode());
			if (!StringUtils.isEmpty(resultSetModel.getPatientAddressCountry())) {
				xmlKeyValue.put(ApplicationConstants.COUNTRY, resultSetModel.getPatientAddressCountry());
			} else {
				xmlKeyValue.put(ApplicationConstants.COUNTRY, ApplicationConstants.UNITED_STATES);
			}
			if (!StringUtils.isEmpty(resultSetModel.getPatientTelephoneType()))
				xmlKeyValue.put(ApplicationConstants.TELECOM_USE, resultSetModel.getPatientTelephoneType());
			if (!StringUtils.isEmpty(resultSetModel.getPatientTelephone()))
				xmlKeyValue.put(ApplicationConstants.TELECOM_VALUE, resultSetModel.getPatientTelephone());
			if (!StringUtils.isEmpty(resultSetModel.getPatientNameFirst()))
				xmlKeyValue.put(ApplicationConstants.NAME_GIVEN, resultSetModel.getPatientNameFirst());
			if (!StringUtils.isEmpty(resultSetModel.getPatientLastName()))
				xmlKeyValue.put(ApplicationConstants.NAME_FAMILY, resultSetModel.getPatientLastName());
			if (!StringUtils.isEmpty(resultSetModel.getPatientGenderCode()))
				xmlKeyValue.put(ApplicationConstants.ADMINISTRATIVE_GENDER_CODE_CODE,
						resultSetModel.getPatientGenderCode());
			if (!StringUtils.isEmpty(resultSetModel.getPatientBirthDate()))
				xmlKeyValue.put(ApplicationConstants.BIRTH_TIME,
						applicationUtil.formatStringDateTime(resultSetModel.getPatientBirthDate()));
			if (!StringUtils.isEmpty(resultSetModel.getPatientRaceCode()))
				xmlKeyValue.put(ApplicationConstants.RACE_CODE_CODE, resultSetModel.getPatientRaceCode());
			if (!StringUtils.isEmpty(resultSetModel.getPatientRaceName()))
				xmlKeyValue.put(ApplicationConstants.RACE_CODE_DISPLAY_NAME, resultSetModel.getPatientRaceName());
			if (!StringUtils.isEmpty(resultSetModel.getPatientEthnicityCode()))
				xmlKeyValue.put(ApplicationConstants.ETHNIC_GROUP_CODE_CODE, resultSetModel.getPatientEthnicityCode());
			if (!StringUtils.isEmpty(resultSetModel.getPatientEthnicityName()))
				xmlKeyValue.put(ApplicationConstants.ETHNIC_GROUP_CODE_DISPLAY_NAME,
						resultSetModel.getPatientEthnicityName());
			// xmlKeyValue.put(ApplicationConstants.AUTHOR_TIME,
			// applicationUtil.formatDateTime(new Date()));
			if (!StringUtils.isEmpty(resultSetModel.getPatientEthnicityName()))
				xmlKeyValue.put(ApplicationConstants.ETHNIC_GROUP_CODE_DISPLAY_NAME,
						resultSetModel.getPatientEthnicityName());
			// xmlKeyValue.put(ApplicationConstants.LEGAL_AUTHENTICATOR_TIME,
			// String.valueOf(new Date().getTime()));
			/*
			 * if (!StringUtils.isEmpty(startDate)){
			 * xmlKeyValue.put(ApplicationConstants.
			 * DOCUMENTATION_OF_EFFECTIVE_TIME_LOW,
			 * applicationUtil.formatStringDateTime(startDate));
			 * xmlKeyValue.put(ApplicationConstants.
			 * DOCUMENTATION_OF_PERFORMER_TIME_LOW,
			 * applicationUtil.formatStringDateTime(startDate));
			 * xmlKeyValue.put(ApplicationConstants.FORMATTED_START_DATE,
			 * String.valueOf(applicationUtil.formatFullStringDateTime(startDate
			 * ))); xmlKeyValue.put(ApplicationConstants.START_DATE,
			 * String.valueOf(applicationUtil.formatStringDateTime(startDate)));
			 * } if (!StringUtils.isEmpty(endDate)){
			 * xmlKeyValue.put(ApplicationConstants.
			 * DOCUMENTATION_OF_EFFECTIVE_TIME_HIGH,
			 * applicationUtil.formatStringDateTime(endDate));
			 * xmlKeyValue.put(ApplicationConstants.
			 * DOCUMENTATION_OF_PERFORMER_TIME_HIGH,
			 * applicationUtil.formatStringDateTime(endDate));
			 * xmlKeyValue.put(ApplicationConstants.FORMATTED_END_DATE,
			 * String.valueOf(applicationUtil.formatFullStringDateTime(endDate))
			 * ); xmlKeyValue.put(ApplicationConstants.END_DATE,
			 * String.valueOf(applicationUtil.formatStringDateTime(endDate))); }
			 */

		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getMapWithXmlKeyValues", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getMapWithXmlKeyValues");
		return xmlKeyValue;
	}

	private HashMap<String, String> getMapWithXmlKeyValuesForHeader(String startDate, String endDate) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getMapWithXmlKeyValuesForHeader");
		HashMap<String, String> xmlKeyValue = new HashMap<>();
		try {
			xmlKeyValue.put(ApplicationConstants.GUID, applicationUtil.generateUUID());
			xmlKeyValue.put(ApplicationConstants.EFFECTIVE_TIME, applicationUtil.formatDateTime(new Date()));
			xmlKeyValue.put(ApplicationConstants.AUTHOR_TIME, applicationUtil.formatDateTime(new Date()));
			xmlKeyValue.put(ApplicationConstants.LEGAL_AUTHENTICATOR_TIME, String.valueOf(new Date().getTime()));
			if (!StringUtils.isEmpty(startDate)) {
				xmlKeyValue.put(ApplicationConstants.DOCUMENTATION_OF_EFFECTIVE_TIME_LOW,
						applicationUtil.formatStringDateTime(startDate));
				xmlKeyValue.put(ApplicationConstants.DOCUMENTATION_OF_PERFORMER_TIME_LOW,
						applicationUtil.formatStringDateTime(startDate));
				xmlKeyValue.put(ApplicationConstants.FORMATTED_START_DATE,
						String.valueOf(applicationUtil.formatFullStringDateTime(startDate)));
				xmlKeyValue.put(ApplicationConstants.START_DATE,
						String.valueOf(applicationUtil.formatStringDateTime(startDate)));
				xmlKeyValue.put(ApplicationConstants.START_DATE_CAT3,
						String.valueOf(applicationUtil.formatStringDateTime(startDate)));
			}
			if (!StringUtils.isEmpty(endDate)) {
				xmlKeyValue.put(ApplicationConstants.DOCUMENTATION_OF_EFFECTIVE_TIME_HIGH,
						applicationUtil.formatStringDateTime(endDate));
				xmlKeyValue.put(ApplicationConstants.DOCUMENTATION_OF_PERFORMER_TIME_HIGH,
						applicationUtil.formatStringDateTime(endDate));
				xmlKeyValue.put(ApplicationConstants.FORMATTED_END_DATE,
						String.valueOf(applicationUtil.formatFullStringDateTime(endDate)));
				xmlKeyValue.put(ApplicationConstants.END_DATE,
						String.valueOf(applicationUtil.formatStringDateTime(endDate)));
				xmlKeyValue.put(ApplicationConstants.END_DATE_CAT3,
						String.valueOf(applicationUtil.formatStringDateTime(endDate)));
			}

		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getMapWithXmlKeyValuesForHeader", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getMapWithXmlKeyValuesForHeader");
		return xmlKeyValue;
	}

	private Map<Integer, List<ResultSetModel>> getUniquePatients(List<ResultSetModel> resultSetModellist) {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getUniquePatients");
		Map<Integer, List<ResultSetModel>> resultSetModelMap = new HashMap<>();
		try {
			// // use caseid for cleintid
			// for (ResultSetModel resultSetModel : resultSetModellist) {
			// if (resultSetModel.getca() != 0) {
			// if (resultSetModelMap.containsKey(resultSetModel.getClientId()))
			// {
			// resultSetModelMap.get(resultSetModel.getClientId()).add(resultSetModel);
			// } else {
			// ArrayList<ResultSetModel> resultSetModelList = new ArrayList<>();
			// resultSetModelList.add(resultSetModel);
			// resultSetModelMap.put(resultSetModel.getClientId(),
			// resultSetModelList);
			// }
			// }
			// }
			// for clientId
			for (ResultSetModel resultSetModel : resultSetModellist) {
				if (resultSetModel.getClientId() != 0) {
					if (resultSetModelMap.containsKey(resultSetModel.getClientId())) {
						resultSetModelMap.get(resultSetModel.getClientId()).add(resultSetModel);
					} else {
						ArrayList<ResultSetModel> resultSetModelList = new ArrayList<>();
						resultSetModelList.add(resultSetModel);
						resultSetModelMap.put(resultSetModel.getClientId(), resultSetModelList);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getUniquePatients", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getUniquePatients");
		return resultSetModelMap;
	}

	@Override
	public List<ResultSetModel> processResultSet(CachedRowSet cachedRowSet) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - processResultSet");
		List<ResultSetModel> resultSetModelList = new ArrayList<ResultSetModel>();
		try {
			ResultSetMetaData metaData = cachedRowSet.getMetaData();
			int count = metaData.getColumnCount(); // number of column
			while (cachedRowSet.next()) {
				ResultSetModel resultSetModel = new ResultSetModel();

				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.NQFID))) {
					resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.NQFID)));
				}

				if (!StringUtils.isEmpty(cachedRowSet.getInt(ApplicationConstants.CASE_ID))) {
					resultSetModel.setClientId(cachedRowSet.getInt(ApplicationConstants.CASE_ID));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PATIENT_F_NAME))) {
					resultSetModel.setPatientNameFirst(
							String.valueOf(cachedRowSet.getString(ApplicationConstants.PATIENT_F_NAME)));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PATIENT_L_NAME))) {
					resultSetModel.setPatientLastName(
							String.valueOf(cachedRowSet.getString(ApplicationConstants.PATIENT_L_NAME)));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.BIRTH_DATE))) {
					resultSetModel.setPatientBirthDate(
							String.valueOf(cachedRowSet.getString(ApplicationConstants.BIRTH_DATE)));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.AGE))) {
					// if it required
					resultSetModel.setPatientAge(cachedRowSet.getInt(ApplicationConstants.AGE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.ADDR_LINE_1))) {
					resultSetModel.setPatientAddressLine1(cachedRowSet.getString(ApplicationConstants.ADDR_LINE_1));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.Q_CITY))) {
					resultSetModel.setPatientAddressCity(cachedRowSet.getString(ApplicationConstants.Q_CITY));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.Q_STATE))) {
					resultSetModel.setPatientAddressState(cachedRowSet.getString(ApplicationConstants.Q_STATE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.Q_COUNTRY))) {
					resultSetModel.setPatientAddressCountry(cachedRowSet.getString(ApplicationConstants.Q_COUNTRY));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.Q_POSTAL_CODE))) {
					resultSetModel
							.setPatientAddressPostalCode(cachedRowSet.getString(ApplicationConstants.Q_POSTAL_CODE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.ADDR_TYPE))) {
					resultSetModel.setPatientAddressType(cachedRowSet.getString(ApplicationConstants.ADDR_TYPE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.TELEPHONE))) {
					resultSetModel.setPatientTelephone(cachedRowSet.getString(ApplicationConstants.TELEPHONE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.TELEPHONE_TYPE))) {
					resultSetModel.setPatientTelephoneType(cachedRowSet.getString(ApplicationConstants.TELEPHONE_TYPE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.GENDER_CODE))) {
					resultSetModel.setPatientGenderCode(cachedRowSet.getString(ApplicationConstants.GENDER_CODE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.GENDER_NAME))) {
					resultSetModel.setPatientGenderName(cachedRowSet.getString(ApplicationConstants.GENDER_NAME));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.RACE_CODE))) {
					resultSetModel.setPatientRaceCode(cachedRowSet.getString(ApplicationConstants.RACE_CODE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.RACE_NAME))) {
					resultSetModel.setPatientRaceName(cachedRowSet.getString(ApplicationConstants.RACE_NAME));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.ETHNICITY_CODE))) {
					resultSetModel.setPatientEthnicityCode(cachedRowSet.getString(ApplicationConstants.ETHNICITY_CODE));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.ETHNICITY_NAME))) {
					resultSetModel.setPatientEthnicityName(
							String.valueOf(cachedRowSet.getString(ApplicationConstants.ETHNICITY_NAME)));
				}
				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PAYER_CODE))) {
					resultSetModel.setPatientPayer(cachedRowSet.getString(ApplicationConstants.PAYER_CODE));
				}

				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.REPORT_START_DATE))) {
					resultSetModel.setProviderPeriodofCareBegin(
							String.valueOf(cachedRowSet.getTimestamp((ApplicationConstants.REPORT_START_DATE))));
				}

				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.REPORT_END_DATE))) {
					resultSetModel.setProviderPeriodofCareEnd(
							String.valueOf(cachedRowSet.getTimestamp(ApplicationConstants.REPORT_END_DATE)));
				}

				if (cachedRowSet.getDate(ApplicationConstants.Q_START_DATE) != null) {
					resultSetModel.setStartDate(cachedRowSet.getDate(ApplicationConstants.Q_START_DATE));
				}

				if (cachedRowSet.getDate(ApplicationConstants.Q_END_DATE) != null) {
					resultSetModel.setEndDate(cachedRowSet.getDate(ApplicationConstants.Q_END_DATE));
				}

				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.DATA_ELEM_NAME))) {
					resultSetModel.setDataElement(cachedRowSet.getString(ApplicationConstants.DATA_ELEM_NAME));
				}

				if (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.DATA_ELEM_CODE))) {
					resultSetModel.setCodes(cachedRowSet.getString(ApplicationConstants.DATA_ELEM_CODE));
				}

				// if
				// (ApplicationConstants.CODESYSTEM.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setCodeSystem(cachedRowSet.getString(i));
				// }

				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_ID)))
				// {
				// resultSetModel.setprovi(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_ID)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_F_NAME)))
				// {
				// resultSetModel.set(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_F_NAME)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_L_NAME)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_L_NAME)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_ADDR_LINE)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_ADDR_LINE)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_CITY)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_CITY)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_STATE)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_STATE)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_COUNTRY)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_COUNTRY)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_POSTAL_CODE)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_POSTAL_CODE)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.PROVIDER_TELEPHONE)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.PROVIDER_TELEPHONE)));
				// }
				// if
				// (!StringUtils.isEmpty(cachedRowSet.getString(ApplicationConstants.Q_PROVIDER_NPI)))
				// {
				// resultSetModel.setMeasureID(String.valueOf(cachedRowSet.getString(ApplicationConstants.Q_PROVIDER_NPI)));
				// }

				// for (int i = 1; i <= count; i++) {
				// if
				// (ApplicationConstants.MEASUREID.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setMeasureID(cachedRowSet.getString(metaData.getColumnName(i)));
				// } else if
				// (ApplicationConstants.CLIENTID.equalsIgnoreCase(metaData.getColumnLabel(i))
				// && (cachedRowSet.getInt(metaData.getColumnName(i)) != 0)) {
				// resultSetModel.setClientId(cachedRowSet.getInt(metaData.getColumnName(i)));
				// } else if
				// (ApplicationConstants.PATIENT_NAME_FIRST.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientNameFirst(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_LAST_NAME.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientLastName(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_BIRTHDATE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientBirthDate(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_AGE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// && (cachedRowSet.getInt(metaData.getColumnName(i)) != 0)) {
				// resultSetModel.setPatientAge(cachedRowSet.getInt(metaData.getColumnName(i)));
				// } else if
				// (ApplicationConstants.PATIENT_ADDRESS_LINE_1.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientAddressLine1(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_ADDRESS_CITY.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientAddressCity(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_ADDRESS_STATE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientAddressState(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_ADDRESS_COUNTRY.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientAddressCountry(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_ADDRESS_POSTALCODE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientAddressPostalCode(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_ADDRESS_TYPE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientAddressType(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_TELEPHONE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientTelephone(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_TELEPHONE_TYPE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientTelephoneType(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_GENDER_CODE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientGenderCode(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_GENDER_NAME.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientGenderName(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_RACE_CODE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientRaceCode(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_RACE_NAME.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientRaceName(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_ETHNICITY_CODE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientEthnicityCode(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_ETHNICITY_NAME.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientEthnicityName(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PATIENT_PAYER.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPatientPayer(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PROVIDERPERIODOFCARE_BEGIN.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setProviderPeriodofCareBegin(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PROVIDERPERIODOFCARE_END.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setProviderPeriodofCareEnd(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.PAYERNAME.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setPayerName(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.STARTDATE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// && cachedRowSet.getDate(metaData.getColumnName(i)) != null) {
				// resultSetModel.setStartDate(cachedRowSet.getDate(i));
				// } else if
				// (ApplicationConstants.ENDDATE.equalsIgnoreCase(metaData.getColumnLabel(i))
				// && cachedRowSet.getDate(metaData.getColumnName(i)) != null) {
				// resultSetModel.setEndDate(cachedRowSet.getDate(i));
				// } else if
				// (ApplicationConstants.DATA_ELEMENT.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setDataElement(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.CODES.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setCodes(cachedRowSet.getString(i));
				// } else if
				// (ApplicationConstants.CODESYSTEM.equalsIgnoreCase(metaData.getColumnLabel(i))
				// &&
				// !StringUtils.isEmpty(cachedRowSet.getString(metaData.getColumnName(i))))
				// {
				// resultSetModel.setCodeSystem(cachedRowSet.getString(i));
				// }
				// }
				resultSetModelList.add(resultSetModel);
			}
		} catch (SQLException sqlException) {
			logger.error("SQLException : QrdaJavaXMLConverterServiceImpl - processResultSet", sqlException);
			throw sqlException;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - processResultSet", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - processResultSet");
		return resultSetModelList;
	}

	private CachedRowSet getProviderDetails(String providerName) throws Exception {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getProviderDetails");
		CachedRowSet cachedRowSet = null;
		try {
			cachedRowSet = facilityDao.findDetailsOfFacility(providerName);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getProviderDetails", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getProviderDetails");
		return cachedRowSet;
	}

	private List<EntryRelationshipPatientSex> getEntryRelationshipPatientSex() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientSex");
		List<EntryRelationshipPatientSex> entryRelationshipPatientSexList = new ArrayList<>();
		try {
			EntryRelationshipPatientSex entryRelationshipPatientSex = new EntryRelationshipPatientSex();
			EntryRelationshipPatientSex entryRelationshipPatientSex2 = new EntryRelationshipPatientSex();
			entryRelationshipPatientSex.setPatientSexCode("1");
			entryRelationshipPatientSex.setPatientSexDisplayName("Female");
			entryRelationshipPatientSex.setPatientSexValue("1");

			entryRelationshipPatientSex2.setPatientSexCode("2");
			entryRelationshipPatientSex2.setPatientSexDisplayName("Male");
			entryRelationshipPatientSex2.setPatientSexValue("2");

			entryRelationshipPatientSexList.add(entryRelationshipPatientSex);
			entryRelationshipPatientSexList.add(entryRelationshipPatientSex2);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientSex", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientSex");
		return entryRelationshipPatientSexList;
	}

	private List<EntryRelationshipPatientEthnicity> getEntryRelationshipPatientEthnicity() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientEthnicity");
		List<EntryRelationshipPatientEthnicity> entryRelationshipPatientEthnicityList = new ArrayList<>();
		try {
			EntryRelationshipPatientEthnicity entryRelationshipPatientEthnicity = new EntryRelationshipPatientEthnicity();
			EntryRelationshipPatientEthnicity entryRelationshipPatientEthnicity2 = new EntryRelationshipPatientEthnicity();
			entryRelationshipPatientEthnicity.setPatientEthnicityCode("1");
			entryRelationshipPatientEthnicity.setPatientEthnicityDisplayName("DISPLAY");
			entryRelationshipPatientEthnicity.setPatientEthnicityValue("1");

			entryRelationshipPatientEthnicity2.setPatientEthnicityCode("2");
			entryRelationshipPatientEthnicity2.setPatientEthnicityDisplayName("DISPLAY1");
			entryRelationshipPatientEthnicity2.setPatientEthnicityValue("2");

			entryRelationshipPatientEthnicityList.add(entryRelationshipPatientEthnicity);
			entryRelationshipPatientEthnicityList.add(entryRelationshipPatientEthnicity2);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientEthnicity", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientEthnicity");
		return entryRelationshipPatientEthnicityList;
	}

	private List<EntryRelationshipPatientRace> getEntryRelationshipPatientRace() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientRace");
		List<EntryRelationshipPatientRace> entryRelationshipPatientRaceList = new ArrayList<>();
		try {
			EntryRelationshipPatientRace entryRelationshipPatientRace = new EntryRelationshipPatientRace();
			entryRelationshipPatientRace.setPatientRaceCode("1");
			entryRelationshipPatientRace.setPatientRaceDisplayName("DISPLAY");
			entryRelationshipPatientRace.setPatientRaceValue("1");

			entryRelationshipPatientRaceList.add(entryRelationshipPatientRace);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientRace", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPatientRace");
		return entryRelationshipPatientRaceList;
	}

	private List<EntryRelationshipPaymentSource> getEntryRelationshipPaymentSource() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPaymentSource");
		List<EntryRelationshipPaymentSource> entryRelationshipPaymentSourceList = new ArrayList<>();
		try {
			EntryRelationshipPaymentSource entryRelationshipPatientRace = new EntryRelationshipPaymentSource();
			entryRelationshipPatientRace.setPayerCode("1");
			entryRelationshipPatientRace.setPayerDisplayName("DISPLAY");
			entryRelationshipPatientRace.setPayerValue("1");
			entryRelationshipPatientRace.setPayerLow("20160212");
			entryRelationshipPatientRace.setPayerHigh("20160212");

			entryRelationshipPaymentSourceList.add(entryRelationshipPatientRace);
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPaymentSource", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipPaymentSource");
		return entryRelationshipPaymentSourceList;
	}

	private String getEntryRelationshipStringXml(TemplateCategory3 templateCategory3, Measure measure) {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipStringXml");
		String entryRelationShip = "";
		try {
			List<EntryRelationshipPatientEthnicity> entryRelationshipPatientEthnicityList = getEntryRelationshipPatientEthnicity();
			List<EntryRelationshipPatientRace> entryRelationshipPatientRaceList = getEntryRelationshipPatientRace();
			List<EntryRelationshipPatientSex> entryRelationshipPatientSexList = getEntryRelationshipPatientSex();
			List<EntryRelationshipPaymentSource> entryRelationshipPaymentSourceList = getEntryRelationshipPaymentSource();
			for (EntryRelationshipPatientEthnicity entryRelationship : entryRelationshipPatientEthnicityList) {
				Map<String, String> entryRelationshipValueMap = new HashMap<>();
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_ETHNICITY_CODE,
						entryRelationship.getPatientEthnicityCode());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_ETHNICITY_DISPLAYNAME,
						entryRelationship.getPatientEthnicityDisplayName());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_ETHNICITY_VALUE,
						entryRelationship.getPatientEthnicityValue());

				String standardEntryRelationShip = templateCategory3.getEntryRelationshipPatientEthnicity();
				standardEntryRelationShip = xmlConverterUtil.replaceWithValues(standardEntryRelationShip,
						entryRelationshipValueMap);
				entryRelationShip += standardEntryRelationShip;
			}
			for (EntryRelationshipPatientRace entryRelationship : entryRelationshipPatientRaceList) {
				Map<String, String> entryRelationshipValueMap = new HashMap<>();
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_RACE_CODE,
						entryRelationship.getPatientRaceCode());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_RACE_DISPLAYNAME,
						entryRelationship.getPatientRaceDisplayName());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_RACE_VALUE,
						entryRelationship.getPatientRaceValue());

				String standardEntryRelationShip = templateCategory3.getEntryRelationshipPatientRace();
				standardEntryRelationShip = xmlConverterUtil.replaceWithValues(standardEntryRelationShip,
						entryRelationshipValueMap);
				entryRelationShip += standardEntryRelationShip;
			}
			for (EntryRelationshipPatientSex entryRelationship : entryRelationshipPatientSexList) {
				Map<String, String> entryRelationshipValueMap = new HashMap<>();
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_SEX_CODE,
						entryRelationship.getPatientSexCode());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_SEX_DISPLAYNAME,
						entryRelationship.getPatientSexDisplayName());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PATIENT_SEX_VALUE,
						entryRelationship.getPatientSexValue());

				String standardEntryRelationShip = templateCategory3.getEntryRelationshipPatientSex();
				standardEntryRelationShip = xmlConverterUtil.replaceWithValues(standardEntryRelationShip,
						entryRelationshipValueMap);
				entryRelationShip += standardEntryRelationShip;
			}
			for (EntryRelationshipPaymentSource entryRelationship : entryRelationshipPaymentSourceList) {
				Map<String, String> entryRelationshipValueMap = new HashMap<>();
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PAYER_CODE,
						entryRelationship.getPayerCode());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PAYER_DISPLAYNAME,
						entryRelationship.getPayerDisplayName());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PAYER_VALUE,
						entryRelationship.getPayerValue());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PAYER_LOW,
						entryRelationship.getPayerLow());
				entryRelationshipValueMap.put(ApplicationConstants.ENTRY_RELATIONSHIP_PAYER_HIGH,
						entryRelationship.getPayerHigh());

				String standardEntryRelationShip = templateCategory3.getEntryRelationshipPaymentSource();
				standardEntryRelationShip = xmlConverterUtil.replaceWithValues(standardEntryRelationShip,
						entryRelationshipValueMap);
				entryRelationShip += standardEntryRelationShip;
			}

			/* This is to add Stratum template in Entry section */
			if (measure.getStratumID() != null) {
				StratumId stratum = measure.getStratumID();
				List<Item> itemlist = stratum.getItem();
				if (!CollectionUtils.isEmpty(itemlist)) {
					for (Item item : itemlist) {
						Map<String, String> valueMap = new HashMap<>();
						valueMap.put(ApplicationConstants.STRATUM_GUID, item.getValue());
						valueMap.put(ApplicationConstants.STRATUM_COUNT, String.valueOf(getStratumCount()));
						String component = templateCategory3.getEntryRelationshipStratum();
						entryRelationShip += xmlConverterUtil.replaceWithValues(component, valueMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipStringXml", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getEntryRelationshipStringXml");
		return entryRelationShip;
	}

	/**
	 * GET COUNT methods will return the total count of IPP, NUMTR, DEMNO,
	 * DENOEXCEP, DENOMEXCLs
	 * 
	 * @return
	 */
	private int getIppCount() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getIppCount");
		int count = 0;
		try {
			count = 50;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getIppCount", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getIppCount");
		return count;
	}

	private int getDenominatorCount() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getDenominatorCount");
		int count = 0;
		try {
			count = 10;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getDenominatorCount", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getDenominatorCount");
		return count;
	}

	private int getNumeratorCount() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getNumeratorCount");
		int count = 0;
		try {
			count = 5;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getNumeratorCount", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getNumeratorCount");
		return count;
	}

	private int getDenominatorExclusionsCount() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getDenominatorExclusionsCount");
		int count = 0;
		try {
			count = 20;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getDenominatorExclusionsCount", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getDenominatorExclusionsCount");
		return count;
	}

	private int getDenominatorExceptionCount() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getDenominatorExceptionCount");
		int count = 0;
		try {
			count = 1;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getDenominatorExceptionCount", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getDenominatorExceptionCount");
		return count;
	}

	private int getStratumCount() {
		logger.info("Entered : QrdaJavaXMLConverterServiceImpl - getStratumCount");
		int count = 0;
		try {
			count = 20;
		} catch (Exception e) {
			logger.error("Exception : QrdaJavaXMLConverterServiceImpl - getStratumCount", e);
			throw e;
		}
		logger.info("Exited : QrdaJavaXMLConverterServiceImpl - getStratumCount");
		return count;
	}

	public void createCat3File() {

		setCat3Header();

		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Xml Generation
		// JAXBContext context;
		// try {
		// context = JAXBContext.newInstance(POCDMT000040ClinicalDocument.class,
		// ObjectFactory.class);
		// Marshaller m = context.createMarshaller();
		// m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// m.marshal(clinicalDoc, baos);
		// byte []bite = baos.toByteArray();
		// System.out.println("xml display......"+new String(bite));
		// } catch (JAXBException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	SimpleDateFormat STRING_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	@Override
	public void setCat3Header() {

		POCDMT000040ClinicalDocument clinicalDoc = new POCDMT000040ClinicalDocument();

		

		CS realmCode = new CS();
		realmCode.setCode("US");
		List<CS> cslist = new ArrayList<>();
		cslist.add(realmCode);
		clinicalDoc.setRealmCode(cslist);

		POCDMT000040InfrastructureRootTypeId typeId = new POCDMT000040InfrastructureRootTypeId();
		typeId.setRoot(Cat3Constants.TYPE_ID_ROOT);
		typeId.setExtension(Cat3Constants.TYPE_ID_EXTENSION);
		clinicalDoc.setTypeId(typeId);

		II templateId = new II();
		templateId.setRoot(Cat3Constants.TEMPLATE_ID_ROOT);
		templateId.setExtension(Cat3Constants.TEMPLATE_ID_EXTENSION);
		clinicalDoc.getTemplateId().add(templateId);

		II id = new II();
		id.setRoot(applicationUtil.generateUUID());
		clinicalDoc.setId(id);

		CE code = new CE();
		code.setCode(Cat3Constants.CODE_CODE);
		code.setCodeSystem(Cat3Constants.CODE_CODE_SYSTEM);
		code.setCodeSystemName(Cat3Constants.CODE_CODE_SYSTEM_NAME);
		code.setDisplayName(Cat3Constants.CODE_DISPLAY_NAME);
		clinicalDoc.setCode(code);

		// ST value = new ST();
		// TEL tel = new TEL();
		// tel.setValue("title");
		// value.setReference(tel);
		clinicalDoc.setTitle("custom-title");

		TS effectiveTime = new TS();
		effectiveTime.setValue(STRING_DATE_FORMAT.format(new Date()));
		clinicalDoc.setEffectiveTime(effectiveTime);

		CE confidentialityCode = new CE();
		confidentialityCode.setCode(Cat3Constants.CONFIDETIALITY_CODE);// static
		confidentialityCode.setCodeSystem(Cat3Constants.CONFIDETIALITY_CODE_SYSTEM);// static
		// confidentialityCode.setCodeSystemName("LOINC");
		// confidentialityCode.setDisplayName("Quality Reporting Document
		// Architecture Calculated Summary Report");
		clinicalDoc.setConfidentialityCode(confidentialityCode);

		CS languageCode = new CS();
		languageCode.setCode(Cat3Constants.LANGUAGE_CODE);
		clinicalDoc.setLanguageCode(languageCode);

		INT versionNumber = new INT();
		BigInteger bigInt = new BigInteger(Cat3Constants.VERSION_NUMBER);// static
		versionNumber.setValue(bigInt);
		clinicalDoc.setVersionNumber(versionNumber);

		POCDMT000040RecordTarget recordTarget = new POCDMT000040RecordTarget();
		POCDMT000040PatientRole patientRole = new POCDMT000040PatientRole();
		II patientRole_id = new II();
		patientRole.getId().add(patientRole_id);
		// patientRole.getNullFlavor().add("N/A");
		patientRole.getId().get(0).getNullFlavor().add("N/A");//
		// addAll(patientRole.getNullFlavor());
		recordTarget.setPatientRole(patientRole);
		clinicalDoc.getRecordTarget().add(recordTarget);

		POCDMT000040Author author = new POCDMT000040Author();
		author.setTime(effectiveTime);
		POCDMT000040AssignedAuthor assignedAuthor = new POCDMT000040AssignedAuthor();
		II assignedAuthorId = new II();
		assignedAuthorId.setRoot(Cat3Constants.assignedAuthorId_root);
		assignedAuthorId.setExtension("frominputtable"); // get from input table
		assignedAuthorId.setAssigningAuthorityName(Cat3Constants.assigningAuthorityName);
		assignedAuthor.getId().add(assignedAuthorId);

		POCDMT000040AuthoringDevice authoringDevice = new POCDMT000040AuthoringDevice();
		// SC softwareName = new SC(); // TODO
		// softwareName.getc
		authoringDevice.setSoftwareName("Custom-software-name");
		assignedAuthor.setAssignedAuthoringDevice(authoringDevice);

		POCDMT000040Organization repOrganization = new POCDMT000040Organization();
		ON orgName = new ON();
		orgName.getContent().add("Good Health Hospital"); // TODO get from prop
															// file
		repOrganization.getName().add(orgName);
		assignedAuthor.setRepresentedOrganization(repOrganization);

		author.setAssignedAuthor(assignedAuthor);
		clinicalDoc.getAuthor().add(author);

		POCDMT000040Custodian custodian = new POCDMT000040Custodian();
		POCDMT000040AssignedCustodian assignedCustodian = new POCDMT000040AssignedCustodian();
		POCDMT000040CustodianOrganization repCustodianOrg = new POCDMT000040CustodianOrganization();
		II repCustodianOrg_id = new II();
		repCustodianOrg_id.setRoot(Cat3Constants.representedCustodianOrganization_root);
		repCustodianOrg_id.setExtension("orgTINinINPTBL");
		repCustodianOrg.getId().add(repCustodianOrg_id);
		ON repCustodianOrg_Name = new ON();
		repCustodianOrg_Name.getContent().add("Good Health Hospital"); // TODO
																		// get
																		// from
																		// prop
																		// file

		repCustodianOrg.setName(repCustodianOrg_Name);
		assignedCustodian.setRepresentedCustodianOrganization(repCustodianOrg);
		custodian.setAssignedCustodian(assignedCustodian);
		clinicalDoc.setCustodian(custodian);

		POCDMT000040LegalAuthenticator legalAuthenitcator = new POCDMT000040LegalAuthenticator();
		legalAuthenitcator.setTime(effectiveTime);// same so reuse
		CS sigCode = new CS();
		sigCode.setCode(Cat3Constants.SIGNATURE_CODE);
		legalAuthenitcator.setSignatureCode(sigCode);

		POCDMT000040AssignedEntity assignedEntity = new POCDMT000040AssignedEntity();
		II assignedEntity_id = new II();
		assignedEntity_id.setRoot(applicationUtil.generateUUID()); // TODO
		assignedEntity.getId().add(assignedEntity_id);
		legalAuthenitcator.setAssignedEntity(assignedEntity);

		POCDMT000040Organization repOrg = new POCDMT000040Organization();
		II repOrg_id = new II();
		repOrg_id.setRoot(Cat3Constants.representedCustodianOrganization_root); // TODO
		repOrg_id.setExtension("5454545"); // from where
		repOrg.getId().add(repOrg_id);
		ON repOrg_Name = new ON();
		repOrg_Name.getContent().add("Good Health Hospital"); // TODO get from
																// prop file

		assignedEntity.setRepresentedOrganization(repOrg);
		clinicalDoc.setLegalAuthenticator(legalAuthenitcator);

		POCDMT000040DocumentationOf docOf = new POCDMT000040DocumentationOf();
		POCDMT000040ServiceEvent servEvent = new POCDMT000040ServiceEvent();
		servEvent.getClassCode().add(Cat3Constants.serviceEvent_class_code);// check

		// set effective time
		IVLTS docEffTime = new IVLTS();
		TS docEffTime_low = new TS();
		docEffTime_low.setValue(STRING_DATE_FORMAT.format(new Date()));// report
																		// start
																		// date

		JAXBElement<TS> low = new JAXBElement<TS>(new QName("low"), TS.class, docEffTime_low);
		docEffTime.getRest().add(low);

		TS docEffTime_high = new TS();
		docEffTime_high.setValue(STRING_DATE_FORMAT.format(new Date()));// report
																		// end
																		// date
		JAXBElement<TS> high = new JAXBElement<TS>(new QName("high"), TS.class, docEffTime_low);
		docEffTime.getRest().add(high);

		servEvent.setEffectiveTime(docEffTime);

		POCDMT000040Performer1 performer = new POCDMT000040Performer1();

		performer.setTypeCode(XServiceEventPerformer.PRF); // if it is static
		performer.setTime(docEffTime); // if it is same effective time of
										// service event

		POCDMT000040AssignedEntity performer_assignedEntity = new POCDMT000040AssignedEntity();
		II performer_assignedEntity_id = new II();
		performer_assignedEntity_id.setRoot(Cat3Constants.assignedAuthorId_root); // TODO
		performer_assignedEntity_id.setExtension("providerNPI-INPTABLE");
		assignedEntity.getId().add(performer_assignedEntity_id);

		POCDMT000040Organization performer_repOrg = new POCDMT000040Organization();
		II perf_repOrg_id = new II();
		perf_repOrg_id.setRoot(Cat3Constants.representedCustodianOrganization_root); // TODO
		perf_repOrg_id.setExtension("5454545"); // from where
		performer_repOrg.getId().add(perf_repOrg_id);
		ON perf_repOrg_Name = new ON();
		perf_repOrg_Name.getContent().add("Good Health Hospital"); // TODO get
																	// from prop
																	// file

		performer_assignedEntity.setRepresentedOrganization(performer_repOrg);

		performer.setAssignedEntity(performer_assignedEntity);
		servEvent.getPerformer().add(performer);
		docOf.setServiceEvent(servEvent);

		clinicalDoc.getDocumentationOf().add(docOf);

		POCDMT000040Component2 component = new POCDMT000040Component2();
		POCDMT000040StructuredBody compStructureBody = new POCDMT000040StructuredBody();
		
		// Component Reporting start
		
		generateReportingComponent(compStructureBody);
		
		// Component Reporting End

		// Component Measure section start

		generateMeasureComponent(compStructureBody);

		// Component Measure section end

		
		component.setStructuredBody(compStructureBody);
		clinicalDoc.setComponent(component);

		try {

			StringWriter writer1 = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(POCDMT000040ClinicalDocument.class, ObjectFactory.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(clinicalDoc, writer1);

			String finalDoc = writer1.toString().replace("&lt;", "<").replace("&gt;", ">");
			System.out.println("xml display......" + finalDoc);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void generateReportingComponent(POCDMT000040StructuredBody compStructureBody){
		POCDMT000040Component3 reportingComponent = new POCDMT000040Component3();
		POCDMT000040Section reportingSection = new POCDMT000040Section();
		II repCompSecTempId_1 = new II();
		II repCompSecTempId_2 = new II();
		repCompSecTempId_1.setRoot(Cat3Constants.reporting_param_section_template_id_root);
		reportingSection.getTemplateId().add(repCompSecTempId_1);
		II category3_Reporting_Parameters_templateId = new II();
		category3_Reporting_Parameters_templateId.setRoot(Cat3Constants.category3_Reporting_Parameters_templateId);
		reportingSection.getTemplateId().add(category3_Reporting_Parameters_templateId);

		CE report_param_code = new CE();
		report_param_code.setCode(Cat3Constants.report_param_code);// static
		report_param_code.setCodeSystem(Cat3Constants.report_param_code_system);// static
		reportingSection.setCode(report_param_code);

		reportingSection.setTitle("custom-title");

		StrucDocText docText = new StrucDocText();
		docText.getContent().add("Reporting period: 01 January 2016 - 31 December 2016"); // date
																							// report
																							// start
																							// and
																							// end
		reportingSection.setText(docText);

		POCDMT000040Entry entry = new POCDMT000040Entry();
		POCDMT000040Act act = new POCDMT000040Act();
		act.setClassCode(XActClassDocumentEntryAct.ACT); // how
		act.setMoodCode(XDocumentActMood.EVN); // to choose these
		II act_templateId = new II();
		act_templateId.setRoot(Cat3Constants.act_templateId);
		act.getTemplateId().add(act_templateId);
		II act_Id = new II();
		act_Id.setRoot("GUID");
		act.getId().add(act_Id);

		CD act_code = new CD();
		act_code.setCode(Cat3Constants.act_code);
		act_code.setCodeSystem(Cat3Constants.act_code_system);
		act_code.setDisplayName(Cat3Constants.act_display_name);
		act.setCode(act_code);
		
		IVLTS docEffTime = new IVLTS();
		TS docEffTime_low = new TS();
		docEffTime_low.setValue(STRING_DATE_FORMAT.format(new Date()));
		JAXBElement<TS> low = new JAXBElement<TS>(new QName("low"), TS.class, docEffTime_low);
		docEffTime.getRest().add(low);

		TS docEffTime_high = new TS();
		docEffTime_high.setValue(STRING_DATE_FORMAT.format(new Date()));
		JAXBElement<TS> high = new JAXBElement<TS>(new QName("high"), TS.class, docEffTime_low);
		docEffTime.getRest().add(high);
		act.setEffectiveTime(docEffTime);

		entry.setAct(act);
		reportingSection.getEntry().add(entry);
		reportingComponent.setSection(reportingSection);
		
		compStructureBody.getComponent().add(reportingComponent);
	}

	private void generateMeasureComponent(POCDMT000040StructuredBody compStructureBody){
		POCDMT000040Component3 measureComponent = new POCDMT000040Component3();
		POCDMT000040Section measureSection = new POCDMT000040Section();
		II measureSection_templateId_1 = new II();
		measureSection_templateId_1.setRoot(Cat3Constants.measureSection_templateId_1_root);
		measureSection.getTemplateId().add(measureSection_templateId_1);
		II measureSection_templateId_2 = new II();
		measureSection_templateId_2.setRoot(Cat3Constants.measureSection_templateId_2_root);
		measureSection_templateId_2.setExtension(Cat3Constants.measureSection_templateId_2_extension);
		measureSection.getTemplateId().add(measureSection_templateId_2);

		CE measureSectionCode = new CE();
		measureSectionCode.setCode(Cat3Constants.measureSectionCode_code);// static
		measureSectionCode.setCodeSystem(Cat3Constants.measureSectionCode_codeSystem);// static
		measureSectionCode.setDisplayName(Cat3Constants.measureSectionCode_displayName);
		measureSection.setCode(measureSectionCode);

		measureSection.setTitle("custom-title:measure-section");

		StrucDocText measureText = new StrucDocText();

		CustomTextElement textElement = new CustomTextElement();
		CustomTableElement tableElem = new CustomTableElement();
		CustomTHeadElement theadElem = new CustomTHeadElement();
		List<CustomTRElement> theadTrElemList = new ArrayList<>();
		CustomTRElement theadTrElem = new CustomTRElement();
		List<String> thList = new ArrayList<>();
		thList.add("eMeasure Title");
		thList.add("eMeasure Version Number");
		thList.add("eMeasure Identifier (MAT)");
		thList.add("Version specific identifier");
		theadTrElem.setTh(thList);
		theadTrElemList.add(theadTrElem);
		theadElem.setTr(theadTrElemList);
		tableElem.setThead(theadElem);

		CustomTBodyElement tbodyElem = new CustomTBodyElement();
		List<CustomTRElement> tBodyTrList = new ArrayList<>();
		CustomTRElement tbodyTrElem = new CustomTRElement();
		List<String> tdList = new ArrayList<>();
		tdList.add("5");
		tdList.add("6");
		tdList.add("7");
		tdList.add("8");
		tbodyTrElem.setTd(tdList);
		tBodyTrList.add(tbodyTrElem);
		tbodyElem.setTr(tBodyTrList);
		tableElem.setTbody(tbodyElem);

		CustomListElement listElement = new CustomListElement();
		List<CustomListItemElement> listItemEleList = new ArrayList<>();
		CustomListItemElement listItemElem = new CustomListItemElement();
		List<CustomListItemContentElement> listItemContentElemList = new ArrayList<>();		
		CustomListItemContentElement listItemContentElem = new CustomListItemContentElement();
		List<String> mixedText = new ArrayList<>();
		mixedText.add("83%(Predicted = 62%)");
		listItemElem.setMixed(mixedText);
		listItemContentElem.setStyleCode("Bold");
		listItemContentElem.setContentValue("Performance Rate");
		listItemContentElemList.add(listItemContentElem);
		listItemElem.setContent(listItemContentElemList);
		listItemEleList.add(listItemElem);
		
		CustomListItemElement listItemElem_1 = new CustomListItemElement();
		List<CustomListItemContentElement> listItemContentElemList_1 = new ArrayList<>();
		CustomListItemContentElement listItemContentElem_1 = new CustomListItemContentElement();
		List<String> mixedText_1 = new ArrayList<>();
		mixedText_1.add("84%");
		listItemElem_1.setMixed(mixedText_1);
		listItemContentElem_1.setStyleCode("Bold");
		listItemContentElem_1.setContentValue("Reporting Rate");
		listItemContentElemList_1.add(listItemContentElem_1);
		listItemElem_1.setContent(listItemContentElemList_1);
		listItemEleList.add(listItemElem_1);
		
		CustomListItemElement listItemElem_2 = new CustomListItemElement();
		List<CustomListItemContentElement> listItemContentElemList_2 = new ArrayList<>();
		CustomListItemContentElement listItemContentElem_2 = new CustomListItemContentElement();
		List<String> mixedText_2 = new ArrayList<>();
		mixedText_2.add("1000");
		listItemElem_2.setMixed(mixedText_2);
		listItemContentElem_2.setStyleCode("Bold");
		listItemContentElem_2.setContentValue("Initial Patient Population");
		listItemContentElemList_2.add(listItemContentElem_2);
		listItemElem_2.setContent(listItemContentElemList_2);
		listItemEleList.add(listItemElem_2);
		
		CustomListItemElement listItemElem_3 = new CustomListItemElement();
		List<CustomListItemContentElement> listItemContentElemList_3 = new ArrayList<>();
		CustomListItemContentElement listItemContentElem_3 = new CustomListItemContentElement();
		List<String> mixedText_3 = new ArrayList<>();
		mixedText_3.add("500");
		listItemElem_3.setMixed(mixedText_3);
		listItemContentElem_3.setStyleCode("Bold");
		listItemContentElem_3.setContentValue("Denominator");
		listItemContentElemList_3.add(listItemContentElem_3);
		listItemElem_3.setContent(listItemContentElemList_3);
		listItemEleList.add(listItemElem_3);
		
		CustomListItemElement listItemElem_4 = new CustomListItemElement();
		List<CustomListItemContentElement> listItemContentElemList_4 = new ArrayList<>();
		CustomListItemContentElement listItemContentElem_4 = new CustomListItemContentElement();
		List<String> mixedText_4 = new ArrayList<>();
		mixedText_4.add("400 (predicted=300)");
		listItemElem_4.setMixed(mixedText_4);
		listItemContentElem_4.setStyleCode("Bold");
		listItemContentElem_4.setContentValue("Numerator");
		listItemContentElemList_4.add(listItemContentElem_4);
		listItemElem_4.setContent(listItemContentElemList_4);
		listItemEleList.add(listItemElem_4);
		
		CustomListItemElement listItemElem_5 = new CustomListItemElement();
		List<CustomListItemContentElement> listItemContentElemList_5 = new ArrayList<>();
		CustomListItemContentElement listItemContentElem_5 = new CustomListItemContentElement();
		List<String> mixedText_5 = new ArrayList<>();
		mixedText_5.add("20");
		listItemElem_5.setMixed(mixedText_5);
		listItemContentElem_5.setStyleCode("Bold");
		listItemContentElem_5.setContentValue("Denominator Exclusions");
		listItemContentElemList_5.add(listItemContentElem_5);
		listItemElem_5.setContent(listItemContentElemList_5);
		listItemEleList.add(listItemElem_5);
		
		
		listElement.setItem(listItemEleList);

		textElement.setTable(tableElem);
		textElement.setList(listElement);

		measureText.getContent().add(getTextContent(textElement));
		measureSection.setText(measureText);

		//Entry 
		POCDMT000040Entry measureEntry = new POCDMT000040Entry();
		POCDMT000040Organizer measureOrganizer = new POCDMT000040Organizer();
		measureOrganizer.setClassCode(XActClassDocumentEntryOrganizer.CLUSTER);
		measureOrganizer.getMoodCode().add(Cat3Constants.measureOrganizerMoodCode);
		II entryTemplateId_1 = new II();
		entryTemplateId_1.setRoot(Cat3Constants.measureTemplateId_1_root);
		measureOrganizer.getTemplateId().add(entryTemplateId_1);
		II entryTemplateId_2 = new II();
		entryTemplateId_2.setRoot(Cat3Constants.measureTemplateId_2_root);
		entryTemplateId_2.setExtension(Cat3Constants.measureTemplateId_2_ext);
		measureOrganizer.getTemplateId().add(entryTemplateId_2);
		II measureOrgId = new II();
		measureOrgId.setRoot("GUID");
		measureOrganizer.getId().add(measureOrgId);
		
		CS stCode = new CS();
		stCode.setCode("completed");
		measureOrganizer.setStatusCode(stCode);
		
		POCDMT000040Reference measRef = new POCDMT000040Reference();
		measRef.setTypeCode(XActRelationshipExternalReference.REFR);
		POCDMT000040ExternalDocument extDoc = new POCDMT000040ExternalDocument();
		extDoc.setClassCode(Cat3Constants.externalDoc_classCode);
		extDoc.getMoodCode().add(Cat3Constants.measureOrganizerMoodCode); // same as org mood code
		
		II extDocId_1 = new II();
		extDocId_1.setRoot("2.16.840.1.113883.4.738"); 
		extDocId_1.setExtension("versionSpecificMeasureID"); //versionSpecificMeasureID
		extDoc.getId().add(extDocId_1);
		
		II extDocId_2 = new II();
		extDocId_2.setRoot("2.16.840.1.113883.3.560.1"); 
		extDocId_2.setExtension("0436"); //nqfid in nput table
		extDoc.getId().add(extDocId_2);
		
		II extDocId_3 = new II();
		extDocId_3.setRoot("2.16.840.1.113883.3.560.101.2"); 
		extDocId_3.setExtension("71"); //static?
		extDoc.getId().add(extDocId_3);
		
		CD val = new CD();
		// static ?
		val.setCode("57024-2");
		val.setCodeSystem("2.16.840.1.113883.6.1");
		val.setCodeSystemName("LOINC");
		val.setDisplayName("Health Quality Measure Document");		
		extDoc.setCode(val);
		
		extDoc.setText("Anticoagulation Therapy for Atrial Fibrillation/Flutter"); // measure.xml title
		
		measRef.setExternalDocument(extDoc);
		
		measureOrganizer.getReference().add(measRef);
		
		POCDMT000040Reference measRef_1 = new POCDMT000040Reference();
		measRef_1.setTypeCode(XActRelationshipExternalReference.REFR);
		POCDMT000040ExternalObservation extObs = new POCDMT000040ExternalObservation();
		II extObsId = new II();
		extObsId.setRoot("RandomGUID"); 
		extObs.getId().add(extObsId);
		CD extObsCode = new CD();
		// static ?
		extObsCode.setCode("55185-3");
		extObsCode.setCodeSystem("2.16.840.1.113883.6.1");
		extObsCode.setCodeSystemName("LOINC");
		extObsCode.setDisplayName("measure set");		
		extObs.setCode(extObsCode);
		extObs.setText("Clinical Quality Measure Set 2015-2016");
		measRef_1.setExternalObservation(extObs);
		
		measureEntry.setOrganizer(measureOrganizer);
		measureSection.getEntry().add(measureEntry);
		
		measureOrganizer.getReference().add(measRef_1);
		
		// organizer component
				POCDMT000040Component4 organizerComponent = new POCDMT000040Component4();
				POCDMT000040Observation orgCompObservation = new POCDMT000040Observation();
				orgCompObservation.getClassCode().add("OBS");
				orgCompObservation.setMoodCode(XActMoodDocumentObservation.EVN);
				
				II obsTemplateId = new II();
				obsTemplateId.setRoot("2.16.840.1.113883.10.20.27.3.5"); 
				obsTemplateId.setExtension("2016-09-01"); //static?		
				orgCompObservation.getTemplateId().add(obsTemplateId);
				
				CD orgCompObsCode = new CD();
				// static ?
				orgCompObsCode.setCode("ASSERTION");
				orgCompObsCode.setCodeSystem("2.16.840.1.113883.5.4");
				orgCompObsCode.setCodeSystemName("ActCode");
				orgCompObsCode.setDisplayName("Assertion");
				orgCompObservation.setCode(orgCompObsCode);
				
				CS stCode_1 = new CS();
				stCode_1.setCode("completed");
				orgCompObservation.setStatusCode(stCode_1); // static same as previous
				
				
				CD entryRelaObsCodeVale = new CD();
				// static ?
				entryRelaObsCodeVale.setCode("IPOP");
				entryRelaObsCodeVale.setCodeSystem("2.16.840.1.113883.5.4");
				entryRelaObsCodeVale.setCodeSystemName("ActCode");
				entryRelaObsCodeVale.setDisplayName("initial patient populationww");
				
//				CustomValueElement valueElement = new CustomValueElement();
//				valueElement.setType("CD");
//				valueElement.setCode("IPOP");
//				valueElement.setCodeSystem("2.16.840.1.113883.5.4");
//				valueElement.setCodeSystemName("ActCode");
//				valueElement.setDisplayName("initial patient populationww");
				orgCompObservation.getValue().add(entryRelaObsCodeVale);
				
				// entry relationship aggregate count template
				POCDMT000040EntryRelationship entryRelationship = new POCDMT000040EntryRelationship();
				entryRelationship.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
				entryRelationship.setInversionInd(true);
				
				POCDMT000040Observation entryRelaObservation = new POCDMT000040Observation();
				entryRelaObservation.getClassCode().add("OBS");
				entryRelaObservation.setMoodCode(XActMoodDocumentObservation.EVN);
				
				II entryRelaObsTemplateId = new II();
				entryRelaObsTemplateId.setRoot("2.16.840.1.113883.10.20.27.3.3"); 
				entryRelaObservation.getTemplateId().add(entryRelaObsTemplateId);
				
				CD entryRelaObsCode = new CD();
				// static ?
				entryRelaObsCode.setCode("MSRAGG");
				entryRelaObsCode.setCodeSystem("2.16.840.1.113883.5.4");
				entryRelaObsCode.setCodeSystemName("ActCode");
				entryRelaObsCode.setDisplayName("rate aggregation");
				entryRelaObservation.setCode(entryRelaObsCode);
				
				INT entryRelaObservationValueElement = new INT();
				BigInteger ippCountValue = new BigInteger("1000"); //IPP Count we need to calculate from Input XML Values
				entryRelaObservationValueElement.setValue(ippCountValue);
//				CustomValueElement entryRelaObservationValueElement = new CustomValueElement();
//				entryRelaObservationValueElement.setType("INT");
			//	entryRelaObservationValueElement.setValue("IPP Count we need to calculate from Input XML Values");
				//entryRelaObservation.setValue(entryRelaObservationValueElement);
				
				CE ce = new CE();
				ce.setCode("COUNT");
				ce.setDisplayName("Count");
				ce.setCodeSystem("2.16.840.1.113883.5.84");
				ce.setCodeSystemName("ObservationMethod");
				
				entryRelaObservation.getMethodCode().add(ce);
				
				entryRelationship.setObservation(entryRelaObservation);
				
				orgCompObservation.getEntryRelationship().add(entryRelationship);
				
				//add entry relationship Ethnicity Supplemental Data Element (2.16.840.1.113883.10.20.27.3.7)
				POCDMT000040EntryRelationship entryRelationship_enthinicity = new POCDMT000040EntryRelationship();
				entryRelationship_enthinicity.setTypeCode(XActRelationshipEntryRelationship.COMP);
				
				POCDMT000040Observation entryRelaObservation_eth = new POCDMT000040Observation();
				entryRelaObservation_eth.getClassCode().add("OBS");
				entryRelaObservation_eth.setMoodCode(XActMoodDocumentObservation.EVN);
				
				II entryRelaObsTemplateId_eth = new II();
				entryRelaObsTemplateId_eth.setRoot("2.16.840.1.113883.10.20.27.3.7");
				entryRelaObsTemplateId_eth.setExtension("2016-09-01");
				entryRelaObservation_eth.getTemplateId().add(entryRelaObsTemplateId_eth);
				
				CD entryRelaObsCode_eth = new CD();
				// static ?
				entryRelaObsCode_eth.setCode("69490-1");
				entryRelaObsCode_eth.setCodeSystem("2.16.840.1.113883.6.1");
				entryRelaObsCode_eth.setCodeSystemName("LOINC");
				entryRelaObsCode_eth.setDisplayName("Ethnic Group");
				entryRelaObservation_eth.setCode(entryRelaObsCode_eth);
				
				CS statusCode = new CS();
				statusCode.setCode("completed");
				entryRelaObservation_eth.setStatusCode(statusCode);
				
				
				CD entryRelaObsValue = new CD();
				// static ?
				entryRelaObsValue.setCode("2186-5");
				entryRelaObsValue.setCodeSystem("2.16.840.1.113883.6.238");
				entryRelaObsValue.setCodeSystemName("Race &amp; Ethnicity - CDC");
				entryRelaObsValue.setDisplayName("Not Hispanic or Latino");
				entryRelaObservation_eth.getValue().add(entryRelaObsValue);
				
				POCDMT000040EntryRelationship nestedEntryRelationship = new POCDMT000040EntryRelationship();
				nestedEntryRelationship.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
				nestedEntryRelationship.setInversionInd(true);
				
				POCDMT000040Observation obervation_subj = new POCDMT000040Observation();
				obervation_subj.getClassCode().add("OBS");
				obervation_subj.setMoodCode(XActMoodDocumentObservation.EVN);
				II temp_id = new II();
				temp_id.setRoot("2.16.840.1.113883.10.20.27.3.3");
				obervation_subj.getTemplateId().add(temp_id);
				
				CD cod = new CD();
				cod.setCode("MSRAGG");
				cod.setCodeSystem("2.16.840.1.113883.5.4");
				cod.setCodeSystemName("ActCode");
				cod.setDisplayName("rate aggregation");
				obervation_subj.setCode(cod);
				
				INT value_1 = new INT();
				BigInteger iipEthCount = new BigInteger("350");
				value_1.setValue(iipEthCount); //IPP-Ethnicity Count, calculate from Input table
				obervation_subj.getValue().add(value_1);
				
				CE methodCode = new CE();
				methodCode.setCode("COUNT");
				methodCode.setCodeSystem("2.16.840.1.113883.5.84");
				methodCode.setCodeSystemName("ObservationMethod");
				methodCode.setDisplayName("Count");
				
				obervation_subj.getMethodCode().add(methodCode);
				nestedEntryRelationship.setObservation(obervation_subj);
				
				
				entryRelaObservation_eth.getEntryRelationship().add(nestedEntryRelationship);
				
				entryRelationship_enthinicity.setObservation(entryRelaObservation_eth);
				orgCompObservation.getEntryRelationship().add(entryRelationship_enthinicity);
				
				//<!-- Race Supplemental Data Element (2.16.840.1.113883.10.20.27.3.8) -->		
				POCDMT000040EntryRelationship entryRelationship_race = new POCDMT000040EntryRelationship();
				entryRelationship_race.setTypeCode(XActRelationshipEntryRelationship.COMP);
				
				POCDMT000040Observation entryRelaObservation_race = new POCDMT000040Observation();
				entryRelaObservation_race.getClassCode().add("OBS");
				entryRelaObservation_race.setMoodCode(XActMoodDocumentObservation.EVN);
				
				II entryRelaObsTemplateId_race = new II();
				entryRelaObsTemplateId_race.setRoot("2.16.840.1.113883.10.20.27.3.8");
				entryRelaObsTemplateId_race.setExtension("2016-09-01");
				entryRelaObservation_race.getTemplateId().add(entryRelaObsTemplateId_race);
				
				CD entryRelaObsCode_race = new CD();
				entryRelaObsCode_race.setCode("72826-1");
				entryRelaObsCode_race.setCodeSystem("2.16.840.1.113883.6.1");
				entryRelaObsCode_race.setCodeSystemName("LOINC");
				entryRelaObsCode_race.setDisplayName("Race");
				entryRelaObservation_race.setCode(entryRelaObsCode_race);		
				
				CS statusCode_race = new CS();
				statusCode_race.setCode("completed");
				entryRelaObservation_race.setStatusCode(statusCode_race);
				
				
				CD entryRelaObsValue_race = new CD();
				entryRelaObsValue_race.setCode("2054-5");
				entryRelaObsValue_race.setCodeSystem("2.16.840.1.113883.6.238");
				entryRelaObsValue_race.setCodeSystemName("Race &amp; Ethnicity - CDC");
				entryRelaObsValue_race.setDisplayName("Black or African American"); // from i/p table
				entryRelaObservation_race.getValue().add(entryRelaObsValue_race);
						
				POCDMT000040EntryRelationship nestedEntryRelationship_race = new POCDMT000040EntryRelationship();
				nestedEntryRelationship_race.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
				nestedEntryRelationship_race.setInversionInd(true);
				
				POCDMT000040Observation obervation_subj_race = new POCDMT000040Observation();
				obervation_subj_race.getClassCode().add("OBS");
				obervation_subj_race.setMoodCode(XActMoodDocumentObservation.EVN);
				II tempId_race = new II();
				tempId_race.setRoot("2.16.840.1.113883.10.20.27.3.3");
				obervation_subj_race.getTemplateId().add(tempId_race);
				
				CD cod_race = new CD();
				cod_race.setCode("MSRAGG");
				cod_race.setCodeSystem("2.16.840.1.113883.5.4");
				cod_race.setCodeSystemName("ActCode");
				cod.setDisplayName("rate aggregation");
				obervation_subj_race.setCode(cod);
				
				INT value_race = new INT();
				BigInteger iipEthCount_race = new BigInteger("300");
				value_race.setValue(iipEthCount_race); //IPP-Ethnicity Count, calculate from Input table
				obervation_subj_race.getValue().add(value_race);
				
				CE methodCode_race = new CE();
				methodCode_race.setCode("COUNT");
				methodCode_race.setCodeSystem("2.16.840.1.113883.5.84");
				methodCode_race.setCodeSystemName("ObservationMethod");
				methodCode_race.setDisplayName("Count");
				obervation_subj_race.getMethodCode().add(methodCode_race);
				
				nestedEntryRelationship_race.setObservation(obervation_subj_race);
				entryRelaObservation_race.getEntryRelationship().add(nestedEntryRelationship_race);
				entryRelationship_race.setObservation(entryRelaObservation_race);
				orgCompObservation.getEntryRelationship().add(entryRelationship_race);
				
				//<!-- Sex Supplemental Data Element -->
				POCDMT000040EntryRelationship entryRelationship_gender = new POCDMT000040EntryRelationship();
				entryRelationship_gender.setTypeCode(XActRelationshipEntryRelationship.COMP);
				
				POCDMT000040Observation entryRelaObservation_gender = new POCDMT000040Observation();
				entryRelaObservation_gender.getClassCode().add("OBS");
				entryRelaObservation_gender.setMoodCode(XActMoodDocumentObservation.EVN);
				
				II entryRelaObsTemplateId_gender = new II();
				entryRelaObsTemplateId_gender.setRoot(Cat3Constants.gender_templateId_root);
				entryRelaObsTemplateId_gender.setExtension(Cat3Constants.gender_templateId_EXTENSION);
				entryRelaObservation_gender.getTemplateId().add(entryRelaObsTemplateId_gender);
				
				CD entryRelaObsCode_gender = new CD();
				entryRelaObsCode_gender.setCode(Cat3Constants.gender_code_code);
				entryRelaObsCode_gender.setCodeSystem(Cat3Constants.gender_code_codeSystem);
				entryRelaObsCode_gender.setCodeSystemName(Cat3Constants.gender_code_codeSystemName);
				entryRelaObsCode_gender.setDisplayName(Cat3Constants.gender_code_displayName);
				entryRelaObservation_gender.setCode(entryRelaObsCode_gender);		
				
				CS statusCode_gender = new CS();
				statusCode_gender.setCode("completed");
				entryRelaObservation_gender.setStatusCode(statusCode_gender);
				
				CD entryRelaObsValue_gender = new CD();
				entryRelaObsValue_gender.setCode(Cat3Constants.gender_value_code);
				entryRelaObsValue_gender.setCodeSystem(Cat3Constants.gender_value_codeSystem);
				entryRelaObsValue_gender.setCodeSystemName(Cat3Constants.gender_value_codeSystemName);
				entryRelaObsValue_gender.setDisplayName(Cat3Constants.gender_value_displayName); // from i/p table
				entryRelaObservation_gender.getValue().add(entryRelaObsValue_gender);
						
				POCDMT000040EntryRelationship nestedEntryRelationship_gender = new POCDMT000040EntryRelationship();
				nestedEntryRelationship_gender.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
				nestedEntryRelationship_gender.setInversionInd(true);
				
				POCDMT000040Observation nestEntRelObs_gender = new POCDMT000040Observation();
				nestEntRelObs_gender.getClassCode().add("OBS");
				nestEntRelObs_gender.setMoodCode(XActMoodDocumentObservation.EVN);
				II tempId_gender = new II();
				tempId_gender.setRoot(Cat3Constants.observation_templateId_root);
				nestEntRelObs_gender.getTemplateId().add(tempId_gender);
				
				CD  nestEntRelObsCode_gender = new CD();
				 nestEntRelObsCode_gender.setCode(Cat3Constants.observation_code_code);
				 nestEntRelObsCode_gender.setCodeSystem(Cat3Constants.observation_code_codeSystem);
				 nestEntRelObsCode_gender.setCodeSystemName(Cat3Constants.observation_code_codeSystemName);
				cod.setDisplayName(Cat3Constants.observation_code_displayName);
				nestEntRelObs_gender.setCode(nestEntRelObsCode_gender);
				
				INT value_gender = new INT();
				BigInteger iipEthCount_gender = new BigInteger("400");
				value_gender.setValue(iipEthCount_gender); //IPP-Ethnicity Count, calculate from Input table
				nestEntRelObs_gender.getValue().add(value_gender);
				
				CE methodCode_gender = new CE();
				methodCode_gender.setCode("COUNT");
				methodCode_gender.setCodeSystem("2.16.840.1.113883.5.84");
				methodCode_gender.setCodeSystemName("ObservationMethod");
				methodCode_gender.setDisplayName("Count");
				nestEntRelObs_gender.getMethodCode().add(methodCode_gender);
				
				nestedEntryRelationship_gender.setObservation(nestEntRelObs_gender);
				entryRelaObservation_gender.getEntryRelationship().add(nestedEntryRelationship_gender);
				entryRelationship_gender.setObservation(entryRelaObservation_gender);
				orgCompObservation.getEntryRelationship().add(entryRelationship_gender);		
				
				//<!-- Sex Supplemental Data Element -->
				
				//<!-- Payer Supplemental Data Element start -->
				POCDMT000040EntryRelationship entryRelationship_payer = new POCDMT000040EntryRelationship();
				entryRelationship_payer.setTypeCode(XActRelationshipEntryRelationship.COMP);
				
				POCDMT000040Observation entryRelaObservation_payer = new POCDMT000040Observation();
				entryRelaObservation_payer.getClassCode().add("OBS");
				entryRelaObservation_payer.setMoodCode(XActMoodDocumentObservation.EVN);
				
				II entryRelaObsTemplateId_payer = new II();
				entryRelaObsTemplateId_payer.setRoot(Cat3Constants.payer_templateId_root);
				
				II entryRelaObsTemplateId_payer_1 = new II();
				entryRelaObsTemplateId_payer_1.setRoot(Cat3Constants.payer_templateId_1_root);
				entryRelaObsTemplateId_payer_1.setExtension(Cat3Constants.payer_templateId_1_EXTENSION);
				entryRelaObservation_payer.getTemplateId().add(entryRelaObsTemplateId_payer);
				entryRelaObservation_payer.getTemplateId().add(entryRelaObsTemplateId_payer_1);
				
				II obs_payer_id = new II();
				entryRelaObservation_payer.getId().add(obs_payer_id);
				entryRelaObservation_payer.getId().get(0).getNullFlavor().add("N/A");//
				
				CD entryRelaObsCode_payer = new CD();
				entryRelaObsCode_payer.setCode(Cat3Constants.payer_code_code);
				entryRelaObsCode_payer.setCodeSystem(Cat3Constants.payer_code_codeSystem);
				entryRelaObsCode_payer.setCodeSystemName(Cat3Constants.payer_code_codeSystemName);
				entryRelaObsCode_payer.setDisplayName(Cat3Constants.payer_code_displayName);
				entryRelaObservation_payer.setCode(entryRelaObsCode_payer);	
						
				CS statusCode_payer = new CS();
				statusCode_payer.setCode(Cat3Constants.statusCode);
				entryRelaObservation_payer.setStatusCode(statusCode_payer);
				//is it same as report start and end date
				//set effective time
				IVLTS docEffTime = new IVLTS();
				TS docEffTime_low = new TS();
				docEffTime_low.setValue(STRING_DATE_FORMAT.format(new Date()));//report start date
				
				JAXBElement<TS> low = new JAXBElement<TS>(new QName("low"), TS.class, docEffTime_low);
				docEffTime.getRest().add(low);
				
				TS docEffTime_high = new TS();
				docEffTime_high.setValue(STRING_DATE_FORMAT.format(new Date()));//report end date
				JAXBElement<TS> high = new JAXBElement<TS>(new QName("high"), TS.class, docEffTime_low);
				docEffTime.getRest().add(high);
				entryRelaObservation_payer.setEffectiveTime(docEffTime); 
				
				CD entryRelaObsValue_payer = new CD();
				entryRelaObsValue_payer.setCode(Cat3Constants.payer_value_code);
				entryRelaObsValue_payer.setCodeSystem(Cat3Constants.payer_value_codeSystem);
				entryRelaObsValue_payer.setCodeSystemName(Cat3Constants.payer_value_codeSystemName);
				entryRelaObservation_payer.getValue().add(entryRelaObsValue_payer);
						
				POCDMT000040EntryRelationship nestedEntryRelationship_payer = new POCDMT000040EntryRelationship();
				nestedEntryRelationship_payer.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
				nestedEntryRelationship_payer.setInversionInd(true);
				
				POCDMT000040Observation nestEntRelObs_payer = new POCDMT000040Observation();
				nestEntRelObs_payer.getClassCode().add("OBS");
				nestEntRelObs_payer.setMoodCode(XActMoodDocumentObservation.EVN);
				II tempId_payer = new II();
				tempId_payer.setRoot(Cat3Constants.observation_templateId_root); // same for other observation
				nestEntRelObs_payer.getTemplateId().add(tempId_payer);
				
				//same as other code
				CD  nestEntRelObsCode_payer = new CD();
				 nestEntRelObsCode_payer.setCode(Cat3Constants.observation_code_code);
				 nestEntRelObsCode_payer.setCodeSystem(Cat3Constants.observation_code_codeSystem);
				 nestEntRelObsCode_payer.setCodeSystemName(Cat3Constants.observation_code_codeSystemName);
				cod.setDisplayName(Cat3Constants.observation_code_displayName);
				nestEntRelObs_payer.setCode(nestEntRelObsCode_payer);
				
				INT value_payer = new INT();
				//We need to count from Input table
				BigInteger iipEthCount_payer = new BigInteger("550");
				value_payer.setValue(iipEthCount_payer); 
				nestEntRelObs_payer.getValue().add(value_payer);
				
				CE methodCode_payer = new CE();
				methodCode_payer.setCode(Cat3Constants.observation_methodCode_code);
				methodCode_payer.setCodeSystem(Cat3Constants.observation_methodCode_displayName);
				methodCode_payer.setCodeSystemName(Cat3Constants.observation_methodCode_codeSystem);
				methodCode_payer.setDisplayName(Cat3Constants.observation_methodCode_codeSystemName);
				nestEntRelObs_payer.getMethodCode().add(methodCode_payer);
				
				nestedEntryRelationship_payer.setObservation(nestEntRelObs_payer);
				entryRelaObservation_payer.getEntryRelationship().add(nestedEntryRelationship_payer);
				entryRelationship_payer.setObservation(entryRelaObservation_payer);
				orgCompObservation.getEntryRelationship().add(entryRelationship_payer);
				//<!-- Payer Supplemental Data Element END-->
				
				organizerComponent.setObservation(orgCompObservation);
				
				// reference
				POCDMT000040Reference orgCompObsRef = new POCDMT000040Reference();
				orgCompObsRef.setTypeCode(XActRelationshipExternalReference.REFR);
				POCDMT000040ExternalObservation orgCompObsRefExtObs = new POCDMT000040ExternalObservation();
				orgCompObsRefExtObs.getClassCode().add(Cat3Constants.observation_classCode);
				orgCompObsRefExtObs.getMoodCode().add(Cat3Constants.observation_moodCode);
				II orgCompObsRefExtObs_Id = new II();
				orgCompObsRefExtObs_Id.setRoot("77656F49-E00F-4AEA-9328-43DEC38AB232"); // from measure.xml
				orgCompObsRefExtObs.getId().add(orgCompObsRefExtObs_Id);
				orgCompObsRef.setExternalObservation(orgCompObsRefExtObs);
				orgCompObservation.getReference().add(orgCompObsRef);
				organizerComponent.setObservation(orgCompObservation);
				
				// <!--DENOM Template START-->
				measureOrganizer.getComponent().add(getDenominatorNumeratorComponent(Cat3Constants.COMPONENT_DENOMINATOR));
				// <!--DENOM Template END-->
				
				// <!--NUMER Template START-->
				measureOrganizer.getComponent().add(getDenominatorNumeratorComponent(Cat3Constants.COMPONENT_NUMER));
				// <!--NUMER Template END-->
				
				
				// <!--DENOM Exceptions Template START-->
				measureOrganizer.getComponent().add(getDenominatorNumeratorComponent(Cat3Constants.COMPONENT_DENOMINATOR_EXCEPTION));
				// <!--DENOM Exceptions Template END-->
				
				// <!--DENOM Exclusions Template START-->
				measureOrganizer.getComponent().add(getDenominatorNumeratorComponent(Cat3Constants.COMPONENT_DENOMINATOR_EXCLUTION));
				// <!--DENOM Exclusions Template END-->
				
				measureOrganizer.getComponent().add(organizerComponent);
				measureEntry.setOrganizer(measureOrganizer);
				measureSection.getEntry().add(measureEntry);
		
		
		measureComponent.setSection(measureSection);

		compStructureBody.getComponent().add(measureComponent);
	}
	
	private POCDMT000040Component4 getDenominatorNumeratorComponent(String componentName){
		// <!--DENOM Template START-->
				// denominator component
				POCDMT000040Component4 denominatorComponent = new POCDMT000040Component4();
				POCDMT000040Observation denomCompObservation = new POCDMT000040Observation();
				denomCompObservation.getClassCode().add(Cat3Constants.observation_classCode);
				denomCompObservation.setMoodCode(XActMoodDocumentObservation.EVN);
				
				II denomCompObservationTemplateId = new II();
				denomCompObservationTemplateId.setRoot("2.16.840.1.113883.10.20.27.3.5");
				denomCompObservationTemplateId.setExtension("2016-09-01"); // static?
				denomCompObservation.getTemplateId().add(denomCompObservationTemplateId);

				CD denomCompObsCode = new CD();
				denomCompObsCode.setCode("ASSERTION");
				denomCompObsCode.setCodeSystem("2.16.840.1.113883.5.4");
				denomCompObsCode.setCodeSystemName("ActCode");
				denomCompObsCode.setDisplayName("Assertion");
				denomCompObservation.setCode(denomCompObsCode);

				CS denomCompObservationStatusCode = new CS();
				denomCompObservationStatusCode.setCode("completed");
				denomCompObservation.setStatusCode(denomCompObservationStatusCode); // static same as previous

				CD denomCompObservationValue = new CD();
				if(Cat3Constants.COMPONENT_DENOMINATOR.equalsIgnoreCase(componentName)){
				denomCompObservationValue.setCode(Cat3Constants.COMPONENT_DENOMINATOR);
				denomCompObservationValue.setDisplayName("Denominator");
				} else if(Cat3Constants.COMPONENT_NUMER.equalsIgnoreCase(componentName)){
					denomCompObservationValue.setCode(Cat3Constants.COMPONENT_NUMER);
					denomCompObservationValue.setDisplayName("Numerator");
				} else if(Cat3Constants.COMPONENT_DENOMINATOR_EXCEPTION.equalsIgnoreCase(componentName)){
					denomCompObservationValue.setCode(Cat3Constants.COMPONENT_DENOMINATOR_EXCEPTION);
					denomCompObservationValue.setDisplayName("Denominator Exceptions");
				} else if(Cat3Constants.COMPONENT_DENOMINATOR_EXCLUTION.equalsIgnoreCase(componentName)){
					denomCompObservationValue.setCode(Cat3Constants.COMPONENT_DENOMINATOR_EXCLUTION);
					denomCompObservationValue.setDisplayName("Denominator Exclusions");
				} 
				
				denomCompObservationValue.setCodeSystem("2.16.840.1.113883.5.4");
				denomCompObservationValue.setCodeSystemName("ActCode");
				denomCompObservationValue.setDisplayName("Denominator");

				denomCompObservation.getValue().add(denomCompObservationValue);

				// entry relationship for denominator
				POCDMT000040EntryRelationship denomCompObservationEntryRelationship = new POCDMT000040EntryRelationship();
				denomCompObservationEntryRelationship.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
				denomCompObservationEntryRelationship.setInversionInd(true);

				POCDMT000040Observation denomCompObservationERObs = new POCDMT000040Observation();
				denomCompObservationERObs.getClassCode().add("OBS");
				denomCompObservationERObs.setMoodCode(XActMoodDocumentObservation.EVN);

				II denomCompObservationERObsTemplateId = new II();
				denomCompObservationERObsTemplateId.setRoot("2.16.840.1.113883.10.20.27.3.3");
				denomCompObservationERObs.getTemplateId().add(denomCompObservationERObsTemplateId);

				CD denomCompObservationERObsCode = new CD();
				// static ?
				denomCompObservationERObsCode.setCode("MSRAGG");
				denomCompObservationERObsCode.setCodeSystem("2.16.840.1.113883.5.4");
				denomCompObservationERObsCode.setCodeSystemName("ActCode");
				denomCompObservationERObsCode.setDisplayName("rate aggregation");
				denomCompObservationERObs.setCode(denomCompObservationERObsCode);

				INT denomCompObservationERObsValue = new INT();
				// IPP Count we need to calculate from Input XML Values
				BigInteger denomCompObservationERObsValueDenomCount = new BigInteger("500"); 
				denomCompObservationERObsValue.setValue(denomCompObservationERObsValueDenomCount);
				denomCompObservationERObs.getValue().add(denomCompObservationERObsValue);
				
				CE denomCompObservationERObsMethodCode = new CE();
				denomCompObservationERObsMethodCode.setCode(Cat3Constants.observation_methodCode_code);
				denomCompObservationERObsMethodCode.setDisplayName(Cat3Constants.observation_methodCode_displayName);
				denomCompObservationERObsMethodCode.setCodeSystem(Cat3Constants.observation_methodCode_codeSystem);
				denomCompObservationERObsMethodCode.setCodeSystemName(Cat3Constants.observation_methodCode_codeSystemName);

				denomCompObservationERObs.getMethodCode().add(denomCompObservationERObsMethodCode);		
				denomCompObservationEntryRelationship.setObservation(denomCompObservationERObs);
				denomCompObservation.getEntryRelationship().add(denomCompObservationEntryRelationship);
				denominatorComponent.setObservation(denomCompObservation);
				
				return denominatorComponent;
				//measureOrganizer.getComponent().add(denominatorComponent);
				// <!--DENOM Template END-->
	}
	private String getTextContent(CustomTextElement textElement) {

		JAXBContext context;
		// get text content
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StringWriter writer = new StringWriter();
		String textVal = "";
		try {
			context = JAXBContext.newInstance(CustomTextElement.class);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(textElement, writer);
			// byte[] bite = baos.toByteArray();
			// textElementContentAsString.append( new String(bite));
			String textTemp = writer.toString();
			textVal = textTemp.substring(textTemp.indexOf("text") + 6, textTemp.length() - 8);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("textElementContentAsString " + textVal);

		return textVal;
	}
}
