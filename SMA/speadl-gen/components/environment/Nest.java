package components.environment;

import sma.environment.services.interfaces.IStore;

@SuppressWarnings("all")
public abstract class Nest {
  public interface Requires {
  }
  
  public interface Component extends Nest.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IStore dropService();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements Nest.Component, Nest.Parts {
    private final Nest.Requires bridge;
    
    private final Nest implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_dropService() {
      assert this.dropService == null: "This is a bug.";
      this.dropService = this.implementation.make_dropService();
      if (this.dropService == null) {
      	throw new RuntimeException("make_dropService() in components.environment.Nest should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_dropService();
    }
    
    public ComponentImpl(final Nest implem, final Nest.Requires b, final boolean doInits) {
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
    
    private IStore dropService;
    
    public IStore dropService() {
      return this.dropService;
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
  
  private Nest.ComponentImpl selfComponent;
  
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
  protected Nest.Provides provides() {
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
  protected abstract IStore make_dropService();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Nest.Requires requires() {
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
  protected Nest.Parts parts() {
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
  public synchronized Nest.Component _newComponent(final Nest.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Nest has already been used to create a component, use another one.");
    }
    this.init = true;
    Nest.ComponentImpl  _comp = new Nest.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Nest.Component newComponent() {
    return this._newComponent(new Nest.Requires() {}, true);
  }
}
