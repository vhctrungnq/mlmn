package vo.alarm.utils.feedback;

public class EntityActionProxy implements vo.alarm.utils.feedback.EntityAction {
  private String _endpoint = null;
  private vo.alarm.utils.feedback.EntityAction entityAction = null;
  
  public EntityActionProxy() {
    _initEntityActionProxy();
  }
  
  public EntityActionProxy(String endpoint) {
    _endpoint = endpoint;
    _initEntityActionProxy();
  }
  
  private void _initEntityActionProxy() {
    try {
      entityAction = (new vo.alarm.utils.feedback.EntityActionServiceLocator()).getEntityAction();
      if (entityAction != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)entityAction)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)entityAction)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (entityAction != null)
      ((javax.xml.rpc.Stub)entityAction)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public vo.alarm.utils.feedback.EntityAction getEntityAction() {
    if (entityAction == null)
      _initEntityActionProxy();
    return entityAction;
  }
  
  public java.lang.String executeCommand(java.lang.String command, java.lang.String user, java.lang.String pass) throws java.rmi.RemoteException{
    if (entityAction == null)
      _initEntityActionProxy();
    return entityAction.executeCommand(command, user, pass);
  }
  
  
}