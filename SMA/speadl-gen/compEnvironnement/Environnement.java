package compEnvironnement;

import compCommon.Zieux;
import compEnvironnement.Nid;
import compEnvironnement.SuperComposantAnonyme;
import sma.common.services.interfaces.IAffichage;
import sma.environnement.services.interfaces.IDepot;
import sma.environnement.services.interfaces.IEnvVisualisation;
import sma.environnement.services.interfaces.IInteraction;
import sma.environnement.services.interfaces.IPerception;

@SuppressWarnings("all")
public abstract class Environnement {
  public interface Requires {
  }
  
  public interface Component extends Environnement.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEnvVisualisation visualisationService();
    
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
    public Zieux.Component zieux();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Nid.Component nidRouge();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Nid.Component nidBleu();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Nid.Component nidVert();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public SuperComposantAnonyme.Component superCompAnonyme();
  }
  
  public static class ComponentImpl implements Environnement.Component, Environnement.Parts {
    private final Environnement.Requires bridge;
    
    private final Environnement implementation;
    
    public void start() {
      assert this.zieux != null: "This is a bug.";
      ((Zieux.ComponentImpl) this.zieux).start();
      assert this.nidRouge != null: "This is a bug.";
      ((Nid.ComponentImpl) this.nidRouge).start();
      assert this.nidBleu != null: "This is a bug.";
      ((Nid.ComponentImpl) this.nidBleu).start();
      assert this.nidVert != null: "This is a bug.";
      ((Nid.ComponentImpl) this.nidVert).start();
      assert this.superCompAnonyme != null: "This is a bug.";
      ((SuperComposantAnonyme.ComponentImpl) this.superCompAnonyme).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_zieux() {
      assert this.zieux == null: "This is a bug.";
      assert this.implem_zieux == null: "This is a bug.";
      this.implem_zieux = this.implementation.make_zieux();
      if (this.implem_zieux == null) {
      	throw new RuntimeException("make_zieux() in compEnvironnement.Environnement should not return null.");
      }
      this.zieux = this.implem_zieux._newComponent(new BridgeImpl_zieux(), false);
      
    }
    
    private void init_nidRouge() {
      assert this.nidRouge == null: "This is a bug.";
      assert this.implem_nidRouge == null: "This is a bug.";
      this.implem_nidRouge = this.implementation.make_nidRouge();
      if (this.implem_nidRouge == null) {
      	throw new RuntimeException("make_nidRouge() in compEnvironnement.Environnement should not return null.");
      }
      this.nidRouge = this.implem_nidRouge._newComponent(new BridgeImpl_nidRouge(), false);
      
    }
    
    private void init_nidBleu() {
      assert this.nidBleu == null: "This is a bug.";
      assert this.implem_nidBleu == null: "This is a bug.";
      this.implem_nidBleu = this.implementation.make_nidBleu();
      if (this.implem_nidBleu == null) {
      	throw new RuntimeException("make_nidBleu() in compEnvironnement.Environnement should not return null.");
      }
      this.nidBleu = this.implem_nidBleu._newComponent(new BridgeImpl_nidBleu(), false);
      
    }
    
    private void init_nidVert() {
      assert this.nidVert == null: "This is a bug.";
      assert this.implem_nidVert == null: "This is a bug.";
      this.implem_nidVert = this.implementation.make_nidVert();
      if (this.implem_nidVert == null) {
      	throw new RuntimeException("make_nidVert() in compEnvironnement.Environnement should not return null.");
      }
      this.nidVert = this.implem_nidVert._newComponent(new BridgeImpl_nidVert(), false);
      
    }
    
    private void init_superCompAnonyme() {
      assert this.superCompAnonyme == null: "This is a bug.";
      assert this.implem_superCompAnonyme == null: "This is a bug.";
      this.implem_superCompAnonyme = this.implementation.make_superCompAnonyme();
      if (this.implem_superCompAnonyme == null) {
      	throw new RuntimeException("make_superCompAnonyme() in compEnvironnement.Environnement should not return null.");
      }
      this.superCompAnonyme = this.implem_superCompAnonyme._newComponent(new BridgeImpl_superCompAnonyme(), false);
      
    }
    
    protected void initParts() {
      init_zieux();
      init_nidRouge();
      init_nidBleu();
      init_nidVert();
      init_superCompAnonyme();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Environnement implem, final Environnement.Requires b, final boolean doInits) {
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
    
    public IEnvVisualisation visualisationService() {
      return this.superCompAnonyme().visualisationService();
    }
    
    public IInteraction interactionService() {
      return this.superCompAnonyme().interactionService();
    }
    
    public IPerception perceptionService() {
      return this.superCompAnonyme().perceptionService();
    }
    
    private Zieux.Component zieux;
    
    private Zieux implem_zieux;
    
    private final class BridgeImpl_zieux implements Zieux.Requires {
    }
    
    public final Zieux.Component zieux() {
      return this.zieux;
    }
    
    private Nid.Component nidRouge;
    
    private Nid implem_nidRouge;
    
    private final class BridgeImpl_nidRouge implements Nid.Requires {
    }
    
    public final Nid.Component nidRouge() {
      return this.nidRouge;
    }
    
    private Nid.Component nidBleu;
    
    private Nid implem_nidBleu;
    
    private final class BridgeImpl_nidBleu implements Nid.Requires {
    }
    
    public final Nid.Component nidBleu() {
      return this.nidBleu;
    }
    
    private Nid.Component nidVert;
    
    private Nid implem_nidVert;
    
    private final class BridgeImpl_nidVert implements Nid.Requires {
    }
    
    public final Nid.Component nidVert() {
      return this.nidVert;
    }
    
    private SuperComposantAnonyme.Component superCompAnonyme;
    
    private SuperComposantAnonyme implem_superCompAnonyme;
    
    private final class BridgeImpl_superCompAnonyme implements SuperComposantAnonyme.Requires {
      public final IAffichage affichageService() {
        return Environnement.ComponentImpl.this.zieux().affichageService();
      }
      
      public final IDepot depotRougeService() {
        return Environnement.ComponentImpl.this.nidRouge().dropService();
      }
      
      public final IDepot depotVertService() {
        return Environnement.ComponentImpl.this.nidVert().dropService();
      }
      
      public final IDepot depotBleuService() {
        return Environnement.ComponentImpl.this.nidBleu().dropService();
      }
    }
    
    public final SuperComposantAnonyme.Component superCompAnonyme() {
      return this.superCompAnonyme;
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
  
  private Environnement.ComponentImpl selfComponent;
  
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
  protected Environnement.Provides provides() {
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
  protected Environnement.Requires requires() {
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
  protected Environnement.Parts parts() {
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
  protected abstract Zieux make_zieux();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Nid make_nidRouge();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Nid make_nidBleu();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Nid make_nidVert();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract SuperComposantAnonyme make_superCompAnonyme();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Environnement.Component _newComponent(final Environnement.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Environnement has already been used to create a component, use another one.");
    }
    this.init = true;
    Environnement.ComponentImpl  _comp = new Environnement.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Environnement.Component newComponent() {
    return this._newComponent(new Environnement.Requires() {}, true);
  }
}
