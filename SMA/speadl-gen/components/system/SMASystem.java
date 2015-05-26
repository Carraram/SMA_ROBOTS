package components.system;

import components.control.UserAccess;
import components.environment.Environment;
import sma.control.services.interfaces.IUserOperations;
import sma.environment.services.interfaces.IEnvManagement;

@SuppressWarnings("all")
public abstract class SMASystem {
  public interface Requires {
  }
  
  public interface Component extends SMASystem.Provides {
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
    public Environment.Component environment();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public UserAccess.Component manager();
  }
  
  public static class ComponentImpl implements SMASystem.Component, SMASystem.Parts {
    private final SMASystem.Requires bridge;
    
    private final SMASystem implementation;
    
    public void start() {
      assert this.environment != null: "This is a bug.";
      ((Environment.ComponentImpl) this.environment).start();
      assert this.manager != null: "This is a bug.";
      ((UserAccess.ComponentImpl) this.manager).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_environment() {
      assert this.environment == null: "This is a bug.";
      assert this.implem_environment == null: "This is a bug.";
      this.implem_environment = this.implementation.make_environment();
      if (this.implem_environment == null) {
      	throw new RuntimeException("make_environment() in components.system.SMASystem should not return null.");
      }
      this.environment = this.implem_environment._newComponent(new BridgeImpl_environment(), false);
      
    }
    
    private void init_manager() {
      assert this.manager == null: "This is a bug.";
      assert this.implem_manager == null: "This is a bug.";
      this.implem_manager = this.implementation.make_manager();
      if (this.implem_manager == null) {
      	throw new RuntimeException("make_manager() in components.system.SMASystem should not return null.");
      }
      this.manager = this.implem_manager._newComponent(new BridgeImpl_manager(), false);
      
    }
    
    protected void initParts() {
      init_environment();
      init_manager();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final SMASystem implem, final SMASystem.Requires b, final boolean doInits) {
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
      return this.manager().userService();
    }
    
    private Environment.Component environment;
    
    private Environment implem_environment;
    
    private final class BridgeImpl_environment implements Environment.Requires {
    }
    
    public final Environment.Component environment() {
      return this.environment;
    }
    
    private UserAccess.Component manager;
    
    private UserAccess implem_manager;
    
    private final class BridgeImpl_manager implements UserAccess.Requires {
      public final IEnvManagement envManagementService() {
        return SMASystem.ComponentImpl.this.environment().managementService();
      }
    }
    
    public final UserAccess.Component manager() {
      return this.manager;
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
  
  private SMASystem.ComponentImpl selfComponent;
  
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
  protected SMASystem.Provides provides() {
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
  protected SMASystem.Requires requires() {
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
  protected SMASystem.Parts parts() {
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
  protected abstract Environment make_environment();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract UserAccess make_manager();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized SMASystem.Component _newComponent(final SMASystem.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of SMASystem has already been used to create a component, use another one.");
    }
    this.init = true;
    SMASystem.ComponentImpl  _comp = new SMASystem.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public SMASystem.Component newComponent() {
    return this._newComponent(new SMASystem.Requires() {}, true);
  }
}
