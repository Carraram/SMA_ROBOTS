package components.control;

import sma.common.services.interfaces.IPersistence;
import sma.control.services.interfaces.IUserOperations;
import sma.environment.services.interfaces.IEnvManagement;

@SuppressWarnings("all")
public abstract class SystemManager {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IPersistence persistenceService();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IEnvManagement environmentManagementService();
  }
  
  public interface Component extends SystemManager.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IUserOperations userService();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements SystemManager.Component, SystemManager.Parts {
    private final SystemManager.Requires bridge;
    
    private final SystemManager implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_userService() {
      assert this.userService == null: "This is a bug.";
      this.userService = this.implementation.make_userService();
      if (this.userService == null) {
      	throw new RuntimeException("make_userService() in components.control.SystemManager should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_userService();
    }
    
    public ComponentImpl(final SystemManager implem, final SystemManager.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    private IUserOperations userService;
    
    public IUserOperations userService() {
      return this.userService;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private SystemManager.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected SystemManager.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IUserOperations make_userService();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected SystemManager.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected SystemManager.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized SystemManager.Component _newComponent(final SystemManager.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of SystemManager has already been used to create a component, use another one.");
    }
    this.init = true;
    SystemManager.ComponentImpl  _comp = new SystemManager.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
