package components.control;

import sma.system.agents.ecoRobotLogged.interfaces.IAgentManagement;
import sma.system.control.services.interfaces.IUserOperations;
import sma.system.environment.services.interfaces.IEnvManagement;

@SuppressWarnings("all")
public abstract class Visualisation {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IEnvManagement envDisplayService();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IAgentManagement agentDisplayService();
  }
  
  public interface Component extends Visualisation.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IUserOperations userServiceDisplay();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements Visualisation.Component, Visualisation.Parts {
    private final Visualisation.Requires bridge;
    
    private final Visualisation implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_userServiceDisplay() {
      assert this.userServiceDisplay == null: "This is a bug.";
      this.userServiceDisplay = this.implementation.make_userServiceDisplay();
      if (this.userServiceDisplay == null) {
      	throw new RuntimeException("make_userServiceDisplay() in components.control.Visualisation should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_userServiceDisplay();
    }
    
    public ComponentImpl(final Visualisation implem, final Visualisation.Requires b, final boolean doInits) {
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
    
    private IUserOperations userServiceDisplay;
    
    public IUserOperations userServiceDisplay() {
      return this.userServiceDisplay;
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
  
  private Visualisation.ComponentImpl selfComponent;
  
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
  protected Visualisation.Provides provides() {
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
  protected abstract IUserOperations make_userServiceDisplay();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Visualisation.Requires requires() {
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
  protected Visualisation.Parts parts() {
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
  public synchronized Visualisation.Component _newComponent(final Visualisation.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Visualisation has already been used to create a component, use another one.");
    }
    this.init = true;
    Visualisation.ComponentImpl  _comp = new Visualisation.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
