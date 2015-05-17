package components.environment;

import components.common.Generator;
import components.common.Viewer;
import components.environment.EnvironmentManager;
import components.environment.Nest;
import sma.common.services.interfaces.IDisplay;
import sma.common.services.interfaces.IGeneration;
import sma.environment.services.interfaces.IEnvironmentViewing;
import sma.environment.services.interfaces.IInteraction;
import sma.environment.services.interfaces.IPerception;
import sma.environment.services.interfaces.IStore;

@SuppressWarnings("all")
public abstract class Environment {
  public interface Requires {
  }
  
  public interface Component extends Environment.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEnvironmentViewing visualisationService();
    
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
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Generator.Component randomGenerator();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Viewer.Component viewer();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Nest.Component redNest();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Nest.Component blueNest();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Nest.Component greenNest();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public EnvironmentManager.Component environmentManager();
  }
  
  public static class ComponentImpl implements Environment.Component, Environment.Parts {
    private final Environment.Requires bridge;
    
    private final Environment implementation;
    
    public void start() {
      assert this.randomGenerator != null: "This is a bug.";
      ((Generator.ComponentImpl) this.randomGenerator).start();
      assert this.viewer != null: "This is a bug.";
      ((Viewer.ComponentImpl) this.viewer).start();
      assert this.redNest != null: "This is a bug.";
      ((Nest.ComponentImpl) this.redNest).start();
      assert this.blueNest != null: "This is a bug.";
      ((Nest.ComponentImpl) this.blueNest).start();
      assert this.greenNest != null: "This is a bug.";
      ((Nest.ComponentImpl) this.greenNest).start();
      assert this.environmentManager != null: "This is a bug.";
      ((EnvironmentManager.ComponentImpl) this.environmentManager).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_randomGenerator() {
      assert this.randomGenerator == null: "This is a bug.";
      assert this.implem_randomGenerator == null: "This is a bug.";
      this.implem_randomGenerator = this.implementation.make_randomGenerator();
      if (this.implem_randomGenerator == null) {
      	throw new RuntimeException("make_randomGenerator() in components.environment.Environment should not return null.");
      }
      this.randomGenerator = this.implem_randomGenerator._newComponent(new BridgeImpl_randomGenerator(), false);
      
    }
    
    private void init_viewer() {
      assert this.viewer == null: "This is a bug.";
      assert this.implem_viewer == null: "This is a bug.";
      this.implem_viewer = this.implementation.make_viewer();
      if (this.implem_viewer == null) {
      	throw new RuntimeException("make_viewer() in components.environment.Environment should not return null.");
      }
      this.viewer = this.implem_viewer._newComponent(new BridgeImpl_viewer(), false);
      
    }
    
    private void init_redNest() {
      assert this.redNest == null: "This is a bug.";
      assert this.implem_redNest == null: "This is a bug.";
      this.implem_redNest = this.implementation.make_redNest();
      if (this.implem_redNest == null) {
      	throw new RuntimeException("make_redNest() in components.environment.Environment should not return null.");
      }
      this.redNest = this.implem_redNest._newComponent(new BridgeImpl_redNest(), false);
      
    }
    
    private void init_blueNest() {
      assert this.blueNest == null: "This is a bug.";
      assert this.implem_blueNest == null: "This is a bug.";
      this.implem_blueNest = this.implementation.make_blueNest();
      if (this.implem_blueNest == null) {
      	throw new RuntimeException("make_blueNest() in components.environment.Environment should not return null.");
      }
      this.blueNest = this.implem_blueNest._newComponent(new BridgeImpl_blueNest(), false);
      
    }
    
    private void init_greenNest() {
      assert this.greenNest == null: "This is a bug.";
      assert this.implem_greenNest == null: "This is a bug.";
      this.implem_greenNest = this.implementation.make_greenNest();
      if (this.implem_greenNest == null) {
      	throw new RuntimeException("make_greenNest() in components.environment.Environment should not return null.");
      }
      this.greenNest = this.implem_greenNest._newComponent(new BridgeImpl_greenNest(), false);
      
    }
    
    private void init_environmentManager() {
      assert this.environmentManager == null: "This is a bug.";
      assert this.implem_environmentManager == null: "This is a bug.";
      this.implem_environmentManager = this.implementation.make_environmentManager();
      if (this.implem_environmentManager == null) {
      	throw new RuntimeException("make_environmentManager() in components.environment.Environment should not return null.");
      }
      this.environmentManager = this.implem_environmentManager._newComponent(new BridgeImpl_environmentManager(), false);
      
    }
    
    protected void initParts() {
      init_randomGenerator();
      init_viewer();
      init_redNest();
      init_blueNest();
      init_greenNest();
      init_environmentManager();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Environment implem, final Environment.Requires b, final boolean doInits) {
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
    
    public IEnvironmentViewing visualisationService() {
      return this.environmentManager().viewingService();
    }
    
    public IInteraction interactionService() {
      return this.environmentManager().interactionService();
    }
    
    public IPerception perceptionService() {
      return this.environmentManager().perceptionService();
    }
    
    private Generator.Component randomGenerator;
    
    private Generator implem_randomGenerator;
    
    private final class BridgeImpl_randomGenerator implements Generator.Requires {
    }
    
    public final Generator.Component randomGenerator() {
      return this.randomGenerator;
    }
    
    private Viewer.Component viewer;
    
    private Viewer implem_viewer;
    
    private final class BridgeImpl_viewer implements Viewer.Requires {
    }
    
    public final Viewer.Component viewer() {
      return this.viewer;
    }
    
    private Nest.Component redNest;
    
    private Nest implem_redNest;
    
    private final class BridgeImpl_redNest implements Nest.Requires {
    }
    
    public final Nest.Component redNest() {
      return this.redNest;
    }
    
    private Nest.Component blueNest;
    
    private Nest implem_blueNest;
    
    private final class BridgeImpl_blueNest implements Nest.Requires {
    }
    
    public final Nest.Component blueNest() {
      return this.blueNest;
    }
    
    private Nest.Component greenNest;
    
    private Nest implem_greenNest;
    
    private final class BridgeImpl_greenNest implements Nest.Requires {
    }
    
    public final Nest.Component greenNest() {
      return this.greenNest;
    }
    
    private EnvironmentManager.Component environmentManager;
    
    private EnvironmentManager implem_environmentManager;
    
    private final class BridgeImpl_environmentManager implements EnvironmentManager.Requires {
      public final IDisplay displayService() {
        return Environment.ComponentImpl.this.viewer().displayService();
      }
      
      public final IStore dropRedService() {
        return Environment.ComponentImpl.this.redNest().dropService();
      }
      
      public final IStore dropGreenService() {
        return Environment.ComponentImpl.this.greenNest().dropService();
      }
      
      public final IStore dropBlueService() {
        return Environment.ComponentImpl.this.blueNest().dropService();
      }
      
      public final IGeneration generationService() {
        return Environment.ComponentImpl.this.randomGenerator().generationService();
      }
    }
    
    public final EnvironmentManager.Component environmentManager() {
      return this.environmentManager;
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
  
  private Environment.ComponentImpl selfComponent;
  
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
  protected Environment.Provides provides() {
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
  protected Environment.Requires requires() {
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
  protected Environment.Parts parts() {
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
  protected abstract Generator make_randomGenerator();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Viewer make_viewer();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Nest make_redNest();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Nest make_blueNest();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Nest make_greenNest();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract EnvironmentManager make_environmentManager();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Environment.Component _newComponent(final Environment.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Environment has already been used to create a component, use another one.");
    }
    this.init = true;
    Environment.ComponentImpl  _comp = new Environment.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Environment.Component newComponent() {
    return this._newComponent(new Environment.Requires() {}, true);
  }
}
