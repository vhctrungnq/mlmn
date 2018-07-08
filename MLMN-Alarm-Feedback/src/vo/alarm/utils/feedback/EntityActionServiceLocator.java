/**
 * EntityActionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vo.alarm.utils.feedback;

public class EntityActionServiceLocator extends org.apache.axis.client.Service implements vo.alarm.utils.feedback.EntityActionService {

    public EntityActionServiceLocator() {
    }


    public EntityActionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EntityActionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EntityAction
    private java.lang.String EntityAction_address = "http://10.50.8.207/ccgwservices/services/EntityAction";

    public java.lang.String getEntityActionAddress() {
        return EntityAction_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EntityActionWSDDServiceName = "EntityAction";

    public java.lang.String getEntityActionWSDDServiceName() {
        return EntityActionWSDDServiceName;
    }

    public void setEntityActionWSDDServiceName(java.lang.String name) {
        EntityActionWSDDServiceName = name;
    }

    public vo.alarm.utils.feedback.EntityAction getEntityAction() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EntityAction_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEntityAction(endpoint);
    }

    public vo.alarm.utils.feedback.EntityAction getEntityAction(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            vo.alarm.utils.feedback.EntityActionSoapBindingStub _stub = new vo.alarm.utils.feedback.EntityActionSoapBindingStub(portAddress, this);
            _stub.setPortName(getEntityActionWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEntityActionEndpointAddress(java.lang.String address) {
        EntityAction_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (vo.alarm.utils.feedback.EntityAction.class.isAssignableFrom(serviceEndpointInterface)) {
                vo.alarm.utils.feedback.EntityActionSoapBindingStub _stub = new vo.alarm.utils.feedback.EntityActionSoapBindingStub(new java.net.URL(EntityAction_address), this);
                _stub.setPortName(getEntityActionWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("EntityAction".equals(inputPortName)) {
            return getEntityAction();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservices.gateway.fss.com", "EntityActionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservices.gateway.fss.com", "EntityAction"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EntityAction".equals(portName)) {
            setEntityActionEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
