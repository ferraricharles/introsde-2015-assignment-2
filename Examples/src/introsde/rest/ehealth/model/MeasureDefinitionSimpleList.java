package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.MeasureDefaultRange;
import introsde.rest.ehealth.model.MeasureDefinition;



import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement (name="measureTypes")
 public class MeasureDefinitionSimpleList {     

     @XmlElement
     List<String> measureType = new ArrayList<String>();



     public MeasureDefinitionSimpleList(){
     	System.out.println("constructor method achieved");

     	List <MeasureDefinition> md=  MeasureDefinition.getAll();
     	System.out.println("Measure Definition started");
     	System.out.println(md.size());

     	for(int i=0; i<md.size(); i++){
     		System.out.println(i+ "interaction");
     		MeasureDefinition m = md.get(i);
     		measureType.add(m.getMeasureName());
     	} 

     }
 }