
package com.nalashaa.poc;

/*import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;*/

public class MarshalDemo {

/*	public static void main(String[] args) {
		MarshalDemo demo = new MarshalDemo();
		MarshalDemo demo1 = new MarshalDemo();
		System.out.println(demo.hashCode());
		System.out.println(demo1.hashCode());
		System.out.println(demo.equals(demo1));
	}*/
    /*public static void main(String[] args) throws Exception {
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer.getPhoneNumbers().add(new PhoneNumber());
        customer.getPhoneNumbers().add(new PhoneNumber());
        customer.getPhoneNumbers().add(new PhoneNumber());

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("customer.xsd"));

        JAXBContext jc = JAXBContext.newInstance(Customer.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setSchema(schema);
        marshaller.setEventHandler(new MyValidationEventHandler());
        marshaller.marshal(customer, System.out);
    }*/
	
	/*public static void main(String[] args) {
		System.out.println(bomberAlgo("weeeeevvxxxxx"));;
	}
	
	public static String bomberAlgo(String inputString){
		String finalString = "";
		if(!StringUtils.isEmpty(inputString)){
			String[] stringArray = inputString.split("");
			List<String> stringList = Arrays.asList(stringArray);
			List<String> stringList2 =new ArrayList<>(stringList);
			String previousString = "";
			int firstElement = 0;
			int lastelement = 0;
			int count = 1;
			for (int i = 0; i < stringList.size(); i++) {
				String currentString = stringList.get(i);
				if(i == 0){
					previousString = currentString;
				}else if(currentString.equalsIgnoreCase(previousString)){
					lastelement = i;
					if(count ==1)
						firstElement = i-1;
					count++;
				}else{
					if(count >= 3){
						int placeHolder = 0;
						for(int j = firstElement; j <= lastelement; j++){
							stringList2.remove(j-placeHolder);
							placeHolder++;
						}
					}
					previousString = currentString;
					firstElement = 0;
					lastelement = 0;
					count = 1;
				}
			}
			for (String string : stringList2) {
				System.out.println(string);
			}
		}
		return finalString ;
		}*/
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 234;
	}
}