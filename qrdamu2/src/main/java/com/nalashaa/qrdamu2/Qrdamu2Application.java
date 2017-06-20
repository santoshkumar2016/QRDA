
package com.nalashaa.qrdamu2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.nalashaa.qrdamu2.model.CodeConstants;
import com.nalashaa.qrdamu2.model.ValueSet;

@SpringBootApplication
@EnableCaching
public class Qrdamu2Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Qrdamu2Application.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Qrdamu2Application.class, args);
    }

    @Bean
    public ValueSet applicationConstants() {
        ValueSet valueSet = new ValueSet();
        Map<String, String> constantsMap = new HashMap<String, String>();

        //50v3 
        constantsMap.put("Consultant Report", "Communication_From_Provider_to_Provider");
        constantsMap.put("Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        constantsMap.put("Ophthalmological Services", "Encounter_Performed");
        constantsMap.put("Preventive Care - Established Office Visit, 0 to 17", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Established Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Preventive Care Services-Initial Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Preventive Care- Initial Office Visit, 0 to 17", "Encounter_Performed");
        constantsMap.put("Referral", "Intervention_Performed");
        //entryRelationship for 50v3 
        constantsMap.put("50v3_Encounter_Performed_ER", "");
        constantsMap.put("50v3_Intervention_Performed_ER", "");
        constantsMap.put("50v3_Communication_From_Provider_to_Provider_ER", "");

        //68v4 
        constantsMap.put("Medications Encounter Code Set", "Encounter_Performed");
        constantsMap.put("birth date", "Patient_Characteristic ");
        constantsMap.put("Medical or Other reason not done", "Procedure_Performed_not_done");
        constantsMap.put("Current Medications Documented SNMD", "Procedure_Performed");

        //entryRelationship for 68v4  
        constantsMap.put("68v4_Encounter_Performed_ER", "Patient_Preference");
        constantsMap.put("68v4_Patient_Characteristic_ER", "");
        constantsMap.put("68v4_Procedure_Performed_not_done_ER", "");
        constantsMap.put("68v4_Procedure_Performed_ER", "");

        //90v4 
        constantsMap.put("All Cancer", "Diagnosis_Active");
        constantsMap.put("Heart Failure", "Diagnosis_Active");
        constantsMap.put(" Severe Dementia", "Diagnosis_Active");
        constantsMap.put(" Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        constantsMap.put("Functional Status Assessment for Heart Failure", "Functional_Status_Performed");
        //entryRelationship for 90v4 
        constantsMap.put("90v5_Diagnosis_Active_ER", "");
        constantsMap.put("90v5_Encounter_Performed_ER", "");
        constantsMap.put("90v5_Functional_Status_Performed_ER", "");

        //122v3 
        constantsMap.put("Diabetes", "Diagnosis_Active");
        constantsMap.put("Annual Wellness Visit", "Encounter_Performed");
        constantsMap.put("Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Home Healthcare Services", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Established Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Initial Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("HbA1c Laboratory Test", "Laboratory_Test_Performed");

        //entryRelationship for 122v3 
        constantsMap.put("122v4_Diagnosis_Active_ER", "");
        constantsMap.put("122v4_Encounter_Performed_ER", "");
        constantsMap.put("122v4_Laboratory_Test_Performed_ER", "");

        //138v4 
        constantsMap.put("Annual Wellness Visit", "Encounter_Performed");
        constantsMap.put("Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        constantsMap.put("Health &amp; Behavioral Assessment - Individual", "Encounter_Performed");
        constantsMap.put("Health &amp; Behavioral Assessment - Initial", "Encounter_Performed");
        constantsMap.put("Health &amp; Behavioral Assessment, Reassessment", "Encounter_Performed");
        constantsMap.put("Ophthalmological Services", "Encounter_Performed");
        constantsMap.put("Established Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Group Counseling", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Other", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Individual Counseling", "Encounter_Performed");
        constantsMap.put("Preventive Care Services-Initial Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Psych Visit - Diagnostic Evaluation", "Encounter_Performed");
        constantsMap.put("Psych Visit - Psychotherapy", "Encounter_Performed");
        constantsMap.put("Psychoanalysis", "Encounter_Performed");
        constantsMap.put("Speech and Hearing Evaluation", "Encounter_Performed");
        constantsMap.put("Tobacco Use Cessation Counseling", "Encounter_Performed");
        constantsMap.put("Tobacco Use Cessation Pharmacotherapy", "Medication_Active");
        constantsMap.put("Tobacco Use Cessation Pharmacotherapy", "Medication_Order");
        constantsMap.put("Tobacco Non-User", "Patient_Characteristic");
        constantsMap.put("Tobacco User", "Patient_Characteristic");
        constantsMap.put("Limited Life Expectancy", "Risk_Category_Assessment_not_done");
        constantsMap.put("Tobacco Use Screening", "Risk_Category_Assessment");
        //entryRelationship for 138v4 
        constantsMap.put("138v4_Encounter_Performed_ER", "");
        constantsMap.put("138v4_Medication_Active_ER", "");
        constantsMap.put("138v4_Medication_Oder_ER", "");
        constantsMap.put("138v4_Patient_Characteristic_ER", "");
        constantsMap.put("138v4_Risk_Category_Assessment_not_done_ER", "");
        constantsMap.put("138v4_Risk_Category_Assessment_ER", "");

        //155v4
        constantsMap.put("Pregnancy", "Diagnosis_Active");
        constantsMap.put("Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Home Healthcare Services", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        constantsMap.put("Preventive Care - Established Office Visit, 0 to 17", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Group Counseling", "Encounter_Performed");
        constantsMap.put("Preventive Care Services-Individual Counseling", "Encounter_Performed");
        constantsMap.put("Preventive Care- Initial Office Visit, 0 to 17", "Encounter_Performed");
        constantsMap.put("Counseling for Nutrition", "Intervention_Performed");
        constantsMap.put("Counseling for Physical Activity", "Intervention_Performed");
        constantsMap.put("BMI Percentile", "Physical_Exam_Performed");
        constantsMap.put("Height", "Physical_Exam_Performed");
        constantsMap.put("Weight", "Physical_Exam_Performed");
        //EntryRelationship for 155v4 
        constantsMap.put("155v4_Encounter_Performed_ER", "");
        constantsMap.put("155v4_Intervention_Performed_ER", "");
        constantsMap.put("155v4_Physical_Exam_Performed_ER", "");

        //164v4 
        constantsMap.put("Acute Myocardial Infraction", "Diagnosis_Active");
        constantsMap.put("Ischemic Vascular Disease", "Diagnosis_Active");
        constantsMap.put("Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Home Healthcare Services", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        constantsMap.put("Annual Wellness Visit", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Established Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Initial Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Aspirin and Other Anti-thrombotics", "Medication_Active");
        constantsMap.put("Coronary Artery Bypass Graft", "Procedure_Performed");
        constantsMap.put("Percutaneous Coronary Interventions", "Procedure_Performed");
        //EntryRelationship for 164v4 
        constantsMap.put("164v4_Diagnosis_Active_ER", "");
        constantsMap.put("164v4_Encounter_Performed_ER", "");
        constantsMap.put("164v4_Procedure_Performed_ER", "");
        constantsMap.put("164v4_Medication_Active_ER", "");

        //165v4 
        constantsMap.put("Chronic Kidney Disease, Stage 5", "Diagnosis_Active");
        constantsMap.put("End Stage Renal Disease", "Diagnosis_Active");
        constantsMap.put(" Essential Hypertension", "Diagnosis_Active");
        constantsMap.put("Pregnancy", "Diagnosis_Active");
        constantsMap.put("Adult Outpatient Visit", "Encounter_Performed");
        constantsMap.put("Annual Wellness Visit", "Encounter_Performed");
        constantsMap.put("ESRD Monthly Outpatient Services", "Encounter_Performed");
        constantsMap.put("Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Home Healthcare Services", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        constantsMap.put("Preventive Care Services - Established Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Preventive Care Services-Initial Office Visit, 18 and Up", "Encounter_Performed");
        constantsMap.put("Dialysis Education", "Intervention_Performed");
        constantsMap.put("Other Services Related to Dialysis", "Intervention_Performed");
        constantsMap.put("Diastolic Blood Pressure", "Physical_Exam_Performed");
        constantsMap.put("Systolic Blood Pressure", "Physical_Exam_Performed");
        constantsMap.put("Dialysis Services", "Procedure_Performed");
        constantsMap.put("Kidney Transplant", "Procedure_Performed");
        constantsMap.put("Vascular Access for Dialysis", "Procedure_Performed");
        //EntryRelationship for 164v4 
        constantsMap.put("165v4_Diagnosis_Active_ER", "");
        constantsMap.put("165v4_Encounter_Performed_ER", "");
        constantsMap.put("165v4_Procedure_Performed_ER", "");
        constantsMap.put("165v4_Intervention_Performed_ER", "");
        constantsMap.put("165v4_Physical_Exam_Performed_ER", "");

        //166v5 
        constantsMap.put("All Cancer", "Diagnosis_Active");
        constantsMap.put("IV Drug Abuse", "Diagnosis_Active");
        constantsMap.put("Low Back Pain", "Diagnosis_Active");
        constantsMap.put("Neurologic impairment", "Diagnosis_Active");
        constantsMap.put("Trauma", "Diagnosis_Active");
        constantsMap.put("All Cancer", "Diagnosis_Inactive");
        constantsMap.put("All Cancer", "Diagnosis_Resolved");
        constantsMap.put("CT Scan of Lower Spine", "Diagnostic_Study_Performed");
        constantsMap.put("MRI of Lower Spine", "Diagnostic_Study_Performed");
        constantsMap.put("X-Ray of Lower Spine", "Diagnostic_Study_Performed");
        constantsMap.put("Emergency Department Visit", "Encounter_Performed");
        constantsMap.put("Face-to-Face Interaction", "Encounter_Performed");
        constantsMap.put("Office Visit", "Encounter_Performed");
        //EntryRelationship for 164v4 
        constantsMap.put("166v5_Diagnosis_Active_ER", "");
        constantsMap.put("166v5_Diagnosis_Inactive_ER", "");
        constantsMap.put("166v5_Diagnosis_Resolved_ER", "");
        constantsMap.put("166v5_Encounter_Performed_ER", "");
        constantsMap.put("166v5_Diagnostic_Study_Performed_ER", "");
        
        //82v2
        constantsMap.put("Maternal Post Partum Depression Care", "Intervention_Performed");

        valueSet.setConstantsMap(constantsMap);

        return valueSet;

    }
    
    @Bean
    public CodeConstants codeConstants() {
        CodeConstants codeConstants = new CodeConstants();
        
        Map<String, List<String>> codeConstantsMap = new HashMap<String, List<String>>();
        
        /* Constants for 50v3*/
        List<String> _50v3CodeConstantsList =  new ArrayList<String>();
        _50v3CodeConstantsList.add("Preventive Care- Initial Office Visit, 0 to 17");
        _50v3CodeConstantsList.add("Preventive Care - Established Office Visit, 0 to 17");
        _50v3CodeConstantsList.add("Preventive Care Services - Established Office Visit, 18 and Up");
        _50v3CodeConstantsList.add("Preventive Care Services-Initial Office Visit, 18 and Up");
        _50v3CodeConstantsList.add("Office Visit");
        _50v3CodeConstantsList.add("Face-to-Face Interaction");
        codeConstantsMap.put("50v3",_50v3CodeConstantsList);
        
        /* Constants for 68v4*/
        /*List<String> _68v4CodeConstantsList =  new ArrayList<String>();
        _68v4CodeConstantsList.add("Patient Characteristic Birthdate: birth date" >= 18 year(s) starts before start of "Measurement Period");
        _68v4CodeConstantsList.add("Occurrence A of Encounter, Performed: Medications Encounter Code Set" during "Measurement Period");
        _68v4CodeConstantsList.add("Preventive Care Services - Established Office Visit, 18 and Up");
        _68v4CodeConstantsList.add("Preventive Care Services-Initial Office Visit, 18 and Up");
        _68v4CodeConstantsList.add("Office Visit");
        _68v4CodeConstantsList.add("Face-to-Face Interaction");
        codeConstantsMap.put("68v4",_68v4CodeConstantsList);*/
        
        codeConstants.setConstantsMap(codeConstantsMap);
        return codeConstants;
    }

    
}
