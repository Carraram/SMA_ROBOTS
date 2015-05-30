package components.agent.logging;

import components.agent.logging.Logging;
import sma.system.agents.logging.interfaces.ILog;

@SuppressWarnings("all")
public abstract class LoggingTest {
  public interface Requires {
  }
  
  public interface Component extends LoggingTest.Provides {
  }
  
  public interface Provides {
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Logging.Component log();
  }
  
  public static class ComponentImpl implements LoggingTest.Component, LoggingTest.Parts {
    private final LoggingTest.Requires bridge;
    
    private final LoggingTest implementation;
    
    public void start() {
      assert this.log != null: "This is a bug.";
      ((Logging.ComponentImpl) this.log).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_log() {
      assert this.log == null: "This is a bug.";
      assert this.implem_log == null: "This is a bug.";
      this.implem_log = this.implementation.make_log();
      if (this.implem_log == null) {
      	throw new RuntimeException("make_log() in components.agent.logging.LoggingTest should not return null.");
      }
      this.log = this.implem_log._newComponent(new BridgeImpl_log(), false);
      
    }
    
    protected void initParts() {
      init_log();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final LoggingTest implem, final LoggingTest.Requires b, final boolean doInits) {
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
    
    private Logging.Component log;
    
    private Logging implem_log;
    
    private final class BridgeImpl_log implements Logging.Requires {
    }
    
    public final Logging.Component log() {
      return this.log;
    }
  }
  
  public static class LoggerTest {
    public interface Requires {
    }
    
    public interface Component extends LoggingTest.LoggerTest.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ILog log();
    }
    
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Logging.Logger.Component s();
    }
    
    public static class ComponentImpl implements LoggingTest.LoggerTest.Component, LoggingTest.LoggerTest.Parts {
      private final LoggingTest.LoggerTest.Requires bridge;
      
      private final LoggingTest.LoggerTest implementation;
      
      public void start() {
        assert this.s != null: "This is a bug.";
        ((Logging.Logger.ComponentImpl) this.s).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_s() {
        assert this.s == null: "This is a bug.";
        assert this.implementation.use_s != null: "This is a bug.";
        this.s = this.implementation.use_s._newComponent(new BridgeImpl_log_s(), false);
        
      }
      
      protected void initParts() {
        init_s();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final LoggingTest.LoggerTest implem, final LoggingTest.LoggerTest.Requires b, final boolean doInits) {
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
      
      public ILog log() {
        return this.s().log();
      }
      
      private Logging.Logger.Component s;
      
      private final class BridgeImpl_log_s implements Logging.Logger.Requires {
      }
      
      public final Logging.Logger.Component s() {
        return this.s;
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
    
    private LoggingTest.LoggerTest.ComponentImpl selfComponent;
    
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
    protected LoggingTest.LoggerTest.Provides provides() {
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
    protected LoggingTest.LoggerTest.Requires requires() {
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
    protected LoggingTest.LoggerTest.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private Logging.Logger use_s;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized LoggingTest.LoggerTest.Component _newComponent(final LoggingTest.LoggerTest.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of LoggerTest has already been used to create a component, use another one.");
      }
      this.init = true;
      LoggingTest.LoggerTest.ComponentImpl  _comp = new LoggingTest.LoggerTest.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private LoggingTest.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected LoggingTest.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected LoggingTest.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected LoggingTest.Parts eco_parts() {
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
  
  private LoggingTest.ComponentImpl selfComponent;
  
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
  protected LoggingTest.Provides provides() {
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
  protected LoggingTest.Requires requires() {
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
  protected LoggingTest.Parts parts() {
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
  protected abstract Logging make_log();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized LoggingTest.Component _newComponent(final LoggingTest.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of LoggingTest has already been used to create a component, use another one.");
    }
    this.init = true;
    LoggingTest.ComponentImpl  _comp = new LoggingTest.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected LoggingTest.LoggerTest make_LoggerTest(final String name) {
    return new LoggingTest.LoggerTest();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public LoggingTest.LoggerTest _createImplementationOfLoggerTest(final String name) {
    LoggingTest.LoggerTest implem = make_LoggerTest(name);
    if (implem == null) {
    	throw new RuntimeException("make_LoggerTest() in components.agent.logging.LoggingTest should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_log != null: "This is a bug.";
    assert implem.use_s == null: "This is a bug.";
    implem.use_s = this.selfComponent.implem_log._createImplementationOfLogger(name);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected LoggingTest.LoggerTest.Component newLoggerTest(final String name) {
    LoggingTest.LoggerTest _implem = _createImplementationOfLoggerTest(name);
    return _implem._newComponent(new LoggingTest.LoggerTest.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public LoggingTest.Component newComponent() {
    return this._newComponent(new LoggingTest.Requires() {}, true);
  }
}
