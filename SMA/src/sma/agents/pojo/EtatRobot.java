package sma.agents.pojo;

import sma.common.pojo.Couleur;
import sma.common.pojo.Position;
/**
 * Etat d'un robot
 */
public class EtatRobot {
	private final float maxEnergie;
	private final Couleur couleur;
	private float niveauEnergie;
	private float vitesse;
	private Position position;
	
	/**
	 * Cr�e l'�tat d'un robot
	 * @param maxEnergie Energie maximale du robot
	 * @param couleur Couleur du robot
	 */
	public EtatRobot(float maxEnergie, Couleur couleur, Position positionInitiale) {
		super();
		this.maxEnergie = maxEnergie;
		this.couleur = couleur;
		this.position = positionInitiale;
		niveauEnergie = this.maxEnergie;
		mettreAJourVitesse();
	}
	
	/**
	 * Augmente l'�nergie et met la vitesse � jour
	 * @param energieRecue Energie re�ue
	 */
	public void augmenterEnergie(float energieRecue) {
		float energieTotale = niveauEnergie += energieRecue;
		if (energieTotale > maxEnergie) {
			niveauEnergie = maxEnergie;
		} else {
			niveauEnergie = energieTotale;
		}
		mettreAJourVitesse();
	}
	
	/**
	 * Met � jour la vitesse du robot en fonction de son niveau d'�nergie
	 */
	public void mettreAJourVitesse() {
		float unTiers = maxEnergie / 3;
		float deuxTiers = 2 * maxEnergie / 3;
		if (niveauEnergie > deuxTiers) {
			vitesse = 1.F;
		}
		if (niveauEnergie <= deuxTiers && niveauEnergie > unTiers) {
			vitesse = 0.5F;
		}
		if (unTiers >= niveauEnergie && niveauEnergie > 0) {
			vitesse = 0.3F;
		}
		if (niveauEnergie == 0) {
			vitesse = 0;
		}
	}
	
	public void mettreAJourPosition(Position nouvellePosition) {
	    position = nouvellePosition;
	}
	
	public Couleur getCouleurRobot() {
		return couleur;
	}
	
	public float getEnergieMaximale() {
		return maxEnergie;
	}
	
	public float getNiveauEnergie() {
		return niveauEnergie;
	}
	
	public float getVitesse() {
		return vitesse;
	}
	
	public Position getPositionCourante() {
	    return position;
	}
}
