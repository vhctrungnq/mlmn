/**
 * EntityActionService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package vo.alarm.utils.feedback;

public interface EntityActionService extends javax.xml.rpc.Service {
    public java.lang.String getEntityActionAddress();

    public vo.alarm.utils.feedback.EntityAction getEntityAction() throws javax.xml.rpc.ServiceException;

    public vo.alarm.utils.feedback.EntityAction getEntityAction(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
