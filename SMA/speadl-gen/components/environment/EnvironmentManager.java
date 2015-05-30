package components.environment;

import sma.common.services.interfaces.IDisplay;
import sma.common.services.interfaces.IGeneration;
import sma.system.environment.services.interfaces.IEnvManagement;
import sma.system.environment.services.interfaces.IEnvironmentViewing;
import sma.system.environment.services.interfaces.IInteraction;
import sma.system.environment.services.interfaces.IPerception;
import sma.system.environment.services.interfaces.IStore;

@SuppressWarnings("all")
public abstract class EnvironmentManager {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IStore dropRedService();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IStore dropGreenService();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IStore dropBlueService();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IDisplay displayService();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IGeneration generationService();
  }
  
  public interface Component extends EnvironmentManager.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IInteraction interactionService();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IPerception perceptionService();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEnvironmentViewing viewingService();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEnvManagement managementService();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements EnvironmentManager.Component, EnvironmentManager.Parts {
    private final EnvironmentManager.Requires bridge;
    
    private final EnvironmentManager implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_interactionService() {
      assert this.interactionService == null: "This is a bug.";
      this.interactionService = this.implementation.make_interactionService();
      if (this.interactionService == null) {
      	throw new RuntimeException("make_interactionService() in components.environment.EnvironmentManager should not return null.");
      }
    }
    
    private void init_perceptionService() {
      assert this.perceptionService == null: "This is a bug.";
      this.perceptionService = this.implementation.make_perceptionService();
      if (this.perceptionService == null) {
      	throw new RuntimeException("make_perceptionService() in components.environment.EnvironmentManager should not return null.");
      }
    }
    
    private void init_viewingService() {
      assert this.viewingService == null: "This is a bug.";
      this.viewingService = this.implementation.make_viewingService();
      if (this.viewingService == null) {
      	throw new RuntimeException("make_viewingService() in components.environment.EnvironmentManager should not return null.");
      }
    }
    
    private void init_managementService() {
      assert this.managementService == null: "This is a bug.";
      this.managementService = this.implementation.make_managementService();
      if (this.managementService == null) {
      	throw new RuntimeException("make_managementService() in components.environment.EnvironmentManager should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_interactionService();
      init_perceptionService();
      init_viewingService();
      init_managementService();
    }
    
    public ComponentImpl(final EnvironmentManager implem, final EnvironmentManager.Requires b, final boolean doInits) {
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
    
    private IInteraction interactionService;
    
    public IInteraction interactionService() {
      return this.interactionService;
    }
    
    private IPerception perceptionService;
    
    public IPerception perceptionService() {
      return this.perceptionService;
    }
    
    private IEnvironmentViewing viewingService;
    
    public IEnvironmentViewing viewingService() {
      return this.viewingService;
    }
    
    private IEnvManagement managementService;
    
    public IEnvManagement managementService() {
      return this.managementService;
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
  
  private EnvironmentManager.ComponentImpl selfComponent;
  
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
  protected EnvironmentManager.Provides provides() {
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
  protected abstract IInteraction make_interactionService();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IPerception make_perceptionService();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IEnvironmentViewing make_viewingService();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IEnvManagement make_managementService();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected EnvironmentManager.Requires requires() {
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
  protected EnvironmentManager.Parts parts() {
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
  public synchronized EnvironmentManager.Component _newComponent(final EnvironmentManager.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EnvironmentManager has already been used to create a component, use another one.");
    }
    this.init = true;
    EnvironmentManager.ComponentImpl  _comp = new EnvironmentManager.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
