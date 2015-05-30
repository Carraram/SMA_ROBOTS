package components.control;

import components.control.Persistence;
import components.control.SystemManager;
import sma.common.services.interfaces.IPersistence;
import sma.system.control.services.interfaces.IUserOperations;
import sma.system.environment.services.interfaces.IEnvManagement;

@SuppressWarnings("all")
public abstract class UserAccess {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IEnvManagement envManagementService();
  }
  
  public interface Component extends UserAccess.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IUserOperations userService();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Persistence.Component persistence();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public SystemManager.Component systemManager();
  }
  
  public static class ComponentImpl implements UserAccess.Component, UserAccess.Parts {
    private final UserAccess.Requires bridge;
    
    private final UserAccess implementation;
    
    public void start() {
      assert this.persistence != null: "This is a bug.";
      ((Persistence.ComponentImpl) this.persistence).start();
      assert this.systemManager != null: "This is a bug.";
      ((SystemManager.ComponentImpl) this.systemManager).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_persistence() {
      assert this.persistence == null: "This is a bug.";
      assert this.implem_persistence == null: "This is a bug.";
      this.implem_persistence = this.implementation.make_persistence();
      if (this.implem_persistence == null) {
      	throw new RuntimeException("make_persistence() in components.control.UserAccess should not return null.");
      }
      this.persistence = this.implem_persistence._newComponent(new BridgeImpl_persistence(), false);
      
    }
    
    private void init_systemManager() {
      assert this.systemManager == null: "This is a bug.";
      assert this.implem_systemManager == null: "This is a bug.";
      this.implem_systemManager = this.implementation.make_systemManager();
      if (this.implem_systemManager == null) {
      	throw new RuntimeException("make_systemManager() in components.control.UserAccess should not return null.");
      }
      this.systemManager = this.implem_systemManager._newComponent(new BridgeImpl_systemManager(), false);
      
    }
    
    protected void initParts() {
      init_persistence();
      init_systemManager();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final UserAccess implem, final UserAccess.Requires b, final boolean doInits) {
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
    
    public IUserOperations userService() {
      return this.systemManager().userService();
    }
    
    private Persistence.Component persistence;
    
    private Persistence implem_persistence;
    
    private final class BridgeImpl_persistence implements Persistence.Requires {
    }
    
    public final Persistence.Component persistence() {
      return this.persistence;
    }
    
    private SystemManager.Component systemManager;
    
    private SystemManager implem_systemManager;
    
    private final class BridgeImpl_systemManager implements SystemManager.Requires {
      public final IPersistence persistenceService() {
        return UserAccess.ComponentImpl.this.persistence().persistenceService();
      }
      
      public final IEnvManagement environmentManagementService() {
        return UserAccess.ComponentImpl.this.bridge.envManagementService();
      }
    }
    
    public final SystemManager.Component systemManager() {
      return this.systemManager;
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
  
  private UserAccess.ComponentImpl selfComponent;
  
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
  protected UserAccess.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected UserAccess.Requires requires() {
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
  protected UserAccess.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Persistence make_persistence();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract SystemManager make_systemManager();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized UserAccess.Component _newComponent(final UserAccess.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of UserAccess has already been used to create a component, use another one.");
    }
    this.init = true;
    UserAccess.ComponentImpl  _comp = new UserAccess.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
