package agent.ecoRobotLogged;

import agent.ecoRobot.EcoRobot;
import agent.logging.Logging;
import sma.agents.logging.interfaces.ILog;
import sma.common.pojo.Couleur;
import sma.common.pojo.Position;

@SuppressWarnings("all")
public abstract class EcoRobotLogged {
  public interface Requires {
  }
  
  public interface Component extends EcoRobotLogged.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ILog elog();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Logging.Component l();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public EcoRobot.Component b();
  }
  
  public static class ComponentImpl implements EcoRobotLogged.Component, EcoRobotLogged.Parts {
    private final EcoRobotLogged.Requires bridge;
    
    private final EcoRobotLogged implementation;
    
    public void start() {
      assert this.l != null: "This is a bug.";
      ((Logging.ComponentImpl) this.l).start();
      assert this.b != null: "This is a bug.";
      ((EcoRobot.ComponentImpl) this.b).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_l() {
      assert this.l == null: "This is a bug.";
      assert this.implem_l == null: "This is a bug.";
      this.implem_l = this.implementation.make_l();
      if (this.implem_l == null) {
      	throw new RuntimeException("make_l() in agent.ecoRobotLogged.EcoRobotLogged should not return null.");
      }
      this.l = this.implem_l._newComponent(new BridgeImpl_l(), false);
      
    }
    
    private void init_b() {
      assert this.b == null: "This is a bug.";
      assert this.implem_b == null: "This is a bug.";
      this.implem_b = this.implementation.make_b();
      if (this.implem_b == null) {
      	throw new RuntimeException("make_b() in agent.ecoRobotLogged.EcoRobotLogged should not return null.");
      }
      this.b = this.implem_b._newComponent(new BridgeImpl_b(), false);
      
    }
    
    protected void initParts() {
      init_l();
      init_b();
    }
    
    private void init_elog() {
      assert this.elog == null: "This is a bug.";
      this.elog = this.implementation.make_elog();
      if (this.elog == null) {
      	throw new RuntimeException("make_elog() in agent.ecoRobotLogged.EcoRobotLogged should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_elog();
    }
    
    public ComponentImpl(final EcoRobotLogged implem, final EcoRobotLogged.Requires b, final boolean doInits) {
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
    
    private ILog elog;
    
    public ILog elog() {
      return this.elog;
    }
    
    private Logging.Component l;
    
    private Logging implem_l;
    
    private final class BridgeImpl_l implements Logging.Requires {
    }
    
    public final Logging.Component l() {
      return this.l;
    }
    
    private EcoRobot.Component b;
    
    private EcoRobot implem_b;
    
    private final class BridgeImpl_b implements EcoRobot.Requires {
      public final ILog elog() {
        return EcoRobotLogged.ComponentImpl.this.elog();
      }
    }
    
    public final EcoRobot.Component b() {
      return this.b;
    }
  }
  
  public static class RobotLogged {
    public interface Requires {
    }
    
    public interface Component extends EcoRobotLogged.RobotLogged.Provides {
    }
    
    public interface Provides {
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Logging.Logger.Component ll();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public EcoRobot.Robot.Component ba();
    }
    
    public static class ComponentImpl implements EcoRobotLogged.RobotLogged.Component, EcoRobotLogged.RobotLogged.Parts {
      private final EcoRobotLogged.RobotLogged.Requires bridge;
      
      private final EcoRobotLogged.RobotLogged implementation;
      
      public void start() {
        assert this.ll != null: "This is a bug.";
        ((Logging.Logger.ComponentImpl) this.ll).start();
        assert this.ba != null: "This is a bug.";
        ((EcoRobot.Robot.ComponentImpl) this.ba).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_ll() {
        assert this.ll == null: "This is a bug.";
        assert this.implementation.use_ll != null: "This is a bug.";
        this.ll = this.implementation.use_ll._newComponent(new BridgeImpl_l_ll(), false);
        
      }
      
      private void init_ba() {
        assert this.ba == null: "This is a bug.";
        assert this.implementation.use_ba != null: "This is a bug.";
        this.ba = this.implementation.use_ba._newComponent(new BridgeImpl_b_ba(), false);
        
      }
      
      protected void initParts() {
        init_ll();
        init_ba();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final EcoRobotLogged.RobotLogged implem, final EcoRobotLogged.RobotLogged.Requires b, final boolean doInits) {
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
      
      private Logging.Logger.Component ll;
      
      private final class BridgeImpl_l_ll implements Logging.Logger.Requires {
      }
      
      public final Logging.Logger.Component ll() {
        return this.ll;
      }
      
      private EcoRobot.Robot.Component ba;
      
      private final class BridgeImpl_b_ba implements EcoRobot.Robot.Requires {
        public final ILog log() {
          return EcoRobotLogged.RobotLogged.ComponentImpl.this.ll().log();
        }
      }
      
      public final EcoRobot.Robot.Component ba() {
        return this.ba;
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
    
    private EcoRobotLogged.RobotLogged.ComponentImpl selfComponent;
    
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
    protected EcoRobotLogged.RobotLogged.Provides provides() {
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
    protected EcoRobotLogged.RobotLogged.Requires requires() {
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
    protected EcoRobotLogged.RobotLogged.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private Logging.Logger use_ll;
    
    private EcoRobot.Robot use_ba;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized EcoRobotLogged.RobotLogged.Component _newComponent(final EcoRobotLogged.RobotLogged.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of RobotLogged has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoRobotLogged.RobotLogged.ComponentImpl  _comp = new EcoRobotLogged.RobotLogged.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoRobotLogged.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoRobotLogged.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoRobotLogged.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoRobotLogged.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
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
  
  private EcoRobotLogged.ComponentImpl selfComponent;
  
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
  protected EcoRobotLogged.Provides provides() {
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
  protected abstract ILog make_elog();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected EcoRobotLogged.Requires requires() {
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
  protected EcoRobotLogged.Parts parts() {
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
  protected abstract Logging make_l();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract EcoRobot make_b();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized EcoRobotLogged.Component _newComponent(final EcoRobotLogged.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EcoRobotLogged has already been used to create a component, use another one.");
    }
    this.init = true;
    EcoRobotLogged.ComponentImpl  _comp = new EcoRobotLogged.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected EcoRobotLogged.RobotLogged make_RobotLogged(final String name, final float maxEnergie, final Couleur couleur, final Position positionInitiale) {
    return new EcoRobotLogged.RobotLogged();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoRobotLogged.RobotLogged _createImplementationOfRobotLogged(final String name, final float maxEnergie, final Couleur couleur, final Position positionInitiale) {
    EcoRobotLogged.RobotLogged implem = make_RobotLogged(name,maxEnergie,couleur,positionInitiale);
    if (implem == null) {
    	throw new RuntimeException("make_RobotLogged() in agent.ecoRobotLogged.EcoRobotLogged should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_l != null: "This is a bug.";
    assert implem.use_ll == null: "This is a bug.";
    implem.use_ll = this.selfComponent.implem_l._createImplementationOfLogger(name);
    assert this.selfComponent.implem_b != null: "This is a bug.";
    assert implem.use_ba == null: "This is a bug.";
    implem.use_ba = this.selfComponent.implem_b._createImplementationOfRobot(maxEnergie,couleur,positionInitiale);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected EcoRobotLogged.RobotLogged.Component newRobotLogged(final String name, final float maxEnergie, final Couleur couleur, final Position positionInitiale) {
    EcoRobotLogged.RobotLogged _implem = _createImplementationOfRobotLogged(name,maxEnergie,couleur,positionInitiale);
    return _implem._newComponent(new EcoRobotLogged.RobotLogged.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public EcoRobotLogged.Component newComponent() {
    return this._newComponent(new EcoRobotLogged.Requires() {}, true);
  }
}
