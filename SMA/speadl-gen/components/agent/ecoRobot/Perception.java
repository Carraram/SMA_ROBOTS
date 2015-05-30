package components.agent.ecoRobot;

import sma.system.agents.ecoRobot.interfaces.IExecute;
import sma.system.agents.ecoRobot.interfaces.IKnowledge;
import sma.system.environment.services.interfaces.IPerception;

@SuppressWarnings("all")
public abstract class Perception {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IPerception sensors();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IKnowledge knowledge();
  }
  
  public interface Component extends Perception.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IExecute perceive();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements Perception.Component, Perception.Parts {
    private final Perception.Requires bridge;
    
    private final Perception implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_perceive() {
      assert this.perceive == null: "This is a bug.";
      this.perceive = this.implementation.make_perceive();
      if (this.perceive == null) {
      	throw new RuntimeException("make_perceive() in components.agent.ecoRobot.Perception should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_perceive();
    }
    
    public ComponentImpl(final Perception implem, final Perception.Requires b, final boolean doInits) {
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
    
    private IExecute perceive;
    
    public IExecute perceive() {
      return this.perceive;
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
  
  private Perception.ComponentImpl selfComponent;
  
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
  protected Perception.Provides provides() {
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
  protected abstract IExecute make_perceive();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Perception.Requires requires() {
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
  protected Perception.Parts parts() {
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
  public synchronized Perception.Component _newComponent(final Perception.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Perception has already been used to create a component, use another one.");
    }
    this.init = true;
    Perception.ComponentImpl  _comp = new Perception.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
