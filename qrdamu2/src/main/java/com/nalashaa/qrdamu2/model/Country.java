
package com.nalashaa.qrdamu2.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "name", "capital", "population" })
@XmlRootElement(name = "Country")
public class Country {

    public int getPopulation() {
        return population;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public int getImportance() {
        return importance;
    }

    int population;
    String name;
    String capital;
    int importance;

    @XmlElement(name = "Country_Population")
    public void setPopulation(int population) {
        this.population = population;
    }

    @XmlElement(name = "Country_Name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Country_Capital")
    public void setCapital(String capital) {
        this.capital = capital;
    }

    @XmlAttribute(name = "importance", required = true)
    public void setImportance(int importance) {
        this.importance = importance;
    }
}