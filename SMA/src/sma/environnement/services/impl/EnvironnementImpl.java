package sma.environnement.services.impl;

import sma.common.services.impl.ZieuxConsoleImpl;
import compCommon.Zieux;
import compEnvironnement.Environnement;
import compEnvironnement.Nid;
import compEnvironnement.SuperComposantAnonyme;

public class EnvironnementImpl extends Environnement {

    /**
     * Instance du nid rouge
     */
    private Nid nidRouge;
    
    /**
     * Instance du nid bleu
     */
    private Nid nidBleu;
    
    /**
     * Instance du nid vert
     */
    private Nid nidVert;
    
	@Override
	protected Zieux make_zieux() {
		return new ZieuxConsoleImpl();
	}

	@Override
	protected SuperComposantAnonyme make_superCompAnonyme() {
		return new SuperComposantAnonymeImpl(nidRouge, nidBleu, nidVert);
	}

    @Override
    protected Nid make_nidRouge() {
        nidRouge = new NidImpl();
        return nidRouge;
    }

    @Override
    protected Nid make_nidBleu() {
        nidBleu = new NidImpl();
        return nidBleu;
    }

    @Override
    protected Nid make_nidVert() {
        nidVert = new NidImpl();
        return nidVert;
    }

}
