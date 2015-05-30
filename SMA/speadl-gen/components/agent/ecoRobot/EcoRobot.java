package components.agent.ecoRobot;

import components.agent.ecoRobot.Action;
import components.agent.ecoRobot.Decision;
import components.agent.ecoRobot.Perception;
import components.environment.Environment;
import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.agents.ecoRobot.interfaces.IActionBuffer;
import sma.system.agents.ecoRobot.interfaces.IActuators;
import sma.system.agents.ecoRobot.interfaces.IExecute;
import sma.system.agents.ecoRobot.interfaces.IKnowledge;
import sma.system.agents.ecoRobot.interfaces.IRobotOperations;
import sma.system.agents.ecoRobot.interfaces.IRobotStatus;
import sma.system.agents.logging.interfaces.ILog;
import sma.system.environment.services.interfaces.IInteraction;
import sma.system.environment.services.interfaces.IPerception;

@SuppressWarnings("all")
public abstract class EcoRobot {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ILog elog();
  }
  
  public interface Component extends EcoRobot.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements EcoRobot.Component, EcoRobot.Parts {
    private final EcoRobot.Requires bridge;
    
    private final EcoRobot implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final EcoRobot implem, final EcoRobot.Requires b, final boolean doInits) {
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
  }
  
  public static abstract class Robot {
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public ILog log();
    }
    
    public interface Component extends EcoRobot.Robot.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public IRobotOperations operations();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public IRobotStatus status();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public IActuators actuators();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public IPerception sensors();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public IKnowledge knowledge();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public IExecute execute();
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Perception.Component perception();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Decision.Component decision();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Action.Component action();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Environment.Component env();
    }
    
    public static class ComponentImpl implements EcoRobot.Robot.Component, EcoRobot.Robot.Parts {
      private final EcoRobot.Robot.Requires bridge;
      
      private final EcoRobot.Robot implementation;
      
      public void start() {
        assert this.perception != null: "This is a bug.";
        ((Perception.ComponentImpl) this.perception).start();
        assert this.decision != null: "This is a bug.";
        ((Decision.ComponentImpl) this.decision).start();
        assert this.action != null: "This is a bug.";
        ((Action.ComponentImpl) this.action).start();
        assert this.env != null: "This is a bug.";
        ((Environment.ComponentImpl) this.env).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_perception() {
        assert this.perception == null: "This is a bug.";
        assert this.implem_perception == null: "This is a bug.";
        this.implem_perception = this.implementation.make_perception();
        if (this.implem_perception == null) {
        	throw new RuntimeException("make_perception() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
        this.perception = this.implem_perception._newComponent(new BridgeImpl_perception(), false);
        
      }
      
      private void init_decision() {
        assert this.decision == null: "This is a bug.";
        assert this.implem_decision == null: "This is a bug.";
        this.implem_decision = this.implementation.make_decision();
        if (this.implem_decision == null) {
        	throw new RuntimeException("make_decision() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
        this.decision = this.implem_decision._newComponent(new BridgeImpl_decision(), false);
        
      }
      
      private void init_action() {
        assert this.action == null: "This is a bug.";
        assert this.implem_action == null: "This is a bug.";
        this.implem_action = this.implementation.make_action();
        if (this.implem_action == null) {
        	throw new RuntimeException("make_action() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
        this.action = this.implem_action._newComponent(new BridgeImpl_action(), false);
        
      }
      
      private void init_env() {
        assert this.env == null: "This is a bug.";
        assert this.implem_env == null: "This is a bug.";
        this.implem_env = this.implementation.make_env();
        if (this.implem_env == null) {
        	throw new RuntimeException("make_env() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
        this.env = this.implem_env._newComponent(new BridgeImpl_env(), false);
        
      }
      
      protected void initParts() {
        init_perception();
        init_decision();
        init_action();
        init_env();
      }
      
      private void init_operations() {
        assert this.operations == null: "This is a bug.";
        this.operations = this.implementation.make_operations();
        if (this.operations == null) {
        	throw new RuntimeException("make_operations() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
      }
      
      private void init_status() {
        assert this.status == null: "This is a bug.";
        this.status = this.implementation.make_status();
        if (this.status == null) {
        	throw new RuntimeException("make_status() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
      }
      
      private void init_actuators() {
        assert this.actuators == null: "This is a bug.";
        this.actuators = this.implementation.make_actuators();
        if (this.actuators == null) {
        	throw new RuntimeException("make_actuators() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
      }
      
      private void init_sensors() {
        assert this.sensors == null: "This is a bug.";
        this.sensors = this.implementation.make_sensors();
        if (this.sensors == null) {
        	throw new RuntimeException("make_sensors() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
      }
      
      private void init_knowledge() {
        assert this.knowledge == null: "This is a bug.";
        this.knowledge = this.implementation.make_knowledge();
        if (this.knowledge == null) {
        	throw new RuntimeException("make_knowledge() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
      }
      
      private void init_execute() {
        assert this.execute == null: "This is a bug.";
        this.execute = this.implementation.make_execute();
        if (this.execute == null) {
        	throw new RuntimeException("make_execute() in components.agent.ecoRobot.EcoRobot$Robot should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_operations();
        init_status();
        init_actuators();
        init_sensors();
        init_knowledge();
        init_execute();
      }
      
      public ComponentImpl(final EcoRobot.Robot implem, final EcoRobot.Robot.Requires b, final boolean doInits) {
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
      
      private IRobotOperations operations;
      
      public IRobotOperations operations() {
        return this.operations;
      }
      
      private IRobotStatus status;
      
      public IRobotStatus status() {
        return this.status;
      }
      
      private IActuators actuators;
      
      public IActuators actuators() {
        return this.actuators;
      }
      
      private IPerception sensors;
      
      public IPerception sensors() {
        return this.sensors;
      }
      
      private IKnowledge knowledge;
      
      public IKnowledge knowledge() {
        return this.knowledge;
      }
      
      private IExecute execute;
      
      public IExecute execute() {
        return this.execute;
      }
      
      private Perception.Component perception;
      
      private Perception implem_perception;
      
      private final class BridgeImpl_perception implements Perception.Requires {
        public final IPerception sensors() {
          return EcoRobot.Robot.ComponentImpl.this.sensors();
        }
        
        public final IKnowledge knowledge() {
          return EcoRobot.Robot.ComponentImpl.this.knowledge();
        }
      }
      
      public final Perception.Component perception() {
        return this.perception;
      }
      
      private Decision.Component decision;
      
      private Decision implem_decision;
      
      private final class BridgeImpl_decision implements Decision.Requires {
        public final IKnowledge knowledge() {
          return EcoRobot.Robot.ComponentImpl.this.knowledge();
        }
        
        public final IActionBuffer actionBuffer() {
          return EcoRobot.Robot.ComponentImpl.this.action().actionBuffer();
        }
      }
      
      public final Decision.Component decision() {
        return this.decision;
      }
      
      private Action.Component action;
      
      private Action implem_action;
      
      private final class BridgeImpl_action implements Action.Requires {
        public final IActuators actuators() {
          return EcoRobot.Robot.ComponentImpl.this.actuators();
        }
        
        public final IInteraction envInteraction() {
          return EcoRobot.Robot.ComponentImpl.this.env().interactionService();
        }
      }
      
      public final Action.Component action() {
        return this.action;
      }
      
      private Environment.Component env;
      
      private Environment implem_env;
      
      private final class BridgeImpl_env implements Environment.Requires {
      }
      
      public final Environment.Component env() {
        return this.env;
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
    
    private EcoRobot.Robot.ComponentImpl selfComponent;
    
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
    protected EcoRobot.Robot.Provides provides() {
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
    protected abstract IRobotOperations make_operations();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract IRobotStatus make_status();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract IActuators make_actuators();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract IPerception make_sensors();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract IKnowledge make_knowledge();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract IExecute make_execute();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected EcoRobot.Robot.Requires requires() {
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
    protected EcoRobot.Robot.Parts parts() {
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
    protected abstract Perception make_perception();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract Decision make_decision();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract Action make_action();
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract Environment make_env();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized EcoRobot.Robot.Component _newComponent(final EcoRobot.Robot.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Robot has already been used to create a component, use another one.");
      }
      this.init = true;
      EcoRobot.Robot.ComponentImpl  _comp = new EcoRobot.Robot.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private EcoRobot.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected EcoRobot.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected EcoRobot.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected EcoRobot.Parts eco_parts() {
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
  
  private EcoRobot.ComponentImpl selfComponent;
  
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
  protected EcoRobot.Provides provides() {
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
  protected EcoRobot.Requires requires() {
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
  protected EcoRobot.Parts parts() {
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
  public synchronized EcoRobot.Component _newComponent(final EcoRobot.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EcoRobot has already been used to create a component, use another one.");
    }
    this.init = true;
    EcoRobot.ComponentImpl  _comp = new EcoRobot.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract EcoRobot.Robot make_Robot(final float maxEnergy, final Colors robotColor, final Position initPosition);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public EcoRobot.Robot _createImplementationOfRobot(final float maxEnergy, final Colors robotColor, final Position initPosition) {
    EcoRobot.Robot implem = make_Robot(maxEnergy,robotColor,initPosition);
    if (implem == null) {
    	throw new RuntimeException("make_Robot() in components.agent.ecoRobot.EcoRobot should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
