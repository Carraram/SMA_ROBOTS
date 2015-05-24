package components.control;

import sma.common.services.interfaces.IPersistence;

@SuppressWarnings("all")
public abstract class Persistence {
  public interface Requires {
  }
  
  public interface Component extends Persistence.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IPersistence persistenceService();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements Persistence.Component, Persistence.Parts {
    private final Persistence.Requires bridge;
    
    private final Persistence implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_persistenceService() {
      assert this.persistenceService == null: "This is a bug.";
      this.persistenceService = this.implementation.make_persistenceService();
      if (this.persistenceService == null) {
      	throw new RuntimeException("make_persistenceService() in components.control.Persistence should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_persistenceService();
    }
    
    public ComponentImpl(final Persistence implem, final Persistence.Requires b, final boolean doInits) {
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
    
    private IPersistence persistenceService;
    
    public IPersistence persistenceService() {
      return this.persistenceService;
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
  
  private Persistence.ComponentImpl selfComponent;
  
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
  protected Persistence.Provides provides() {
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
  protected abstract IPersistence make_persistenceService();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Persistence.Requires requires() {
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
  protected Persistence.Parts parts() {
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
  public synchronized Persistence.Component _newComponent(final Persistence.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Persistence has already been used to create a component, use another one.");
    }
    this.init = true;
    Persistence.ComponentImpl  _comp = new Persistence.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Persistence.Component newComponent() {
    return this._newComponent(new Persistence.Requires() {}, true);
  }
}
