package vn.com.vhc.alarm.util;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class AL_Attribute implements Comparable, Serializable {
	private static final long serialVersionUID = 1L;
	private int order; //Su dung trong truong hop muon sap xep thu tu Attribute trong List
    private String name;
    private String value;

    public AL_Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public AL_Attribute(String name, String value, int order) {
        this.name = name;
        this.value = value;
        this.order = order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int compareTo(Object o) {
        AL_Attribute att = (AL_Attribute)o;
        int objOrder = att.getOrder();

        return order - objOrder;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public String toString() {
        return name + ": " + value;
    }
}
