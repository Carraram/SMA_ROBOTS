package sma.environnement.pojo;

import java.util.HashMap;
import java.util.Map;

import compEnvironnement.Nid;
import sma.common.pojo.CaseNonVideException;
import sma.common.pojo.Couleur;
import sma.common.pojo.Grille;
import sma.common.pojo.Position;
import sma.common.pojo.PositionInvalideException;

/**
 * Etat de l'environnement
 */
public class EtatEnvironnement {
    /**
     * Grille représentant l'environnement
     */
	private Grille grille;
	
	/**
	 * Nids situés dans l'environnement
	 */
	private Map<Couleur, Position> nids;
	
	/**
	 * Nombre de colonnes de la grille
	 */
	private final int largeurGrille;
	
	/**
	 * Nombre de lignes de la grille
	 */
	private final int hauteurGrille;
	
	/**
	 * Nombre de boites dans le système
	 */
	private int nombreBoites;
	
	/**
	 * Cree l'etat de l'environnement
	 * @param nombreBoitesInitial Nombre de boites placees initialement dans la grille
	 * @param largeurGrille Nombre de colonnes de la grille
	 * @param hauteurGrille Nombre de lignes de la grille
	 */
	public EtatEnvironnement(int nombreBoitesInitial, int largeurGrille, int hauteurGrille) {
	    nids = new HashMap<Couleur, Position>();
	    grille = new Grille();
		nombreBoites = nombreBoitesInitial;
		this.largeurGrille = largeurGrille;
		this.hauteurGrille = hauteurGrille;
	}
	
	/**
	 * Déplace le robot de sa position de départ jusqu'à sa position d'arrivée
	 * @param depart Position de départ
	 * @param arrivee Position d'arrivée
	 * @throws CaseNonVideException  Position d'arrivée déjà occupée
	 */
	public void deplacerRobot(Position depart, Position arrivee) throws CaseNonVideException {
	    // TODO Gérer le fait qu'un autre robot puisse se déplacer sur la case avant notre robot
	    // TODO Modifier le type de la variable robot (pour perception de l'env par les autres robots)
	    grille.ajouterElement(grille.getElement(depart), arrivee);
	    grille.retirerElement(depart);
	}
	
	/**
	 * Crée un nid dans la grille
	 * @param couleurNid Couleur du nid à créer
	 * @param positionNid Position du nid à créer
	 * @throws CaseNonVideException Position déjà occupée
	 * @throws PositionInvalideException Position en dehors de la grille
	 */
	public void creerNid(Nid instanceNid, Couleur couleurNid, Position positionNid) throws CaseNonVideException, PositionInvalideException {
	    if (!estPositionValide(positionNid)) {
	        throw new PositionInvalideException("La position " + positionNid + " est en dehors de la grille");
	    }
	    nids.put(couleurNid, positionNid);
	    grille.ajouterElement(instanceNid, positionNid);
	}
	
	/**
	 * Renvoie la position des nids avec leur couleur
	 * @return Map associant la position des nids à leur couleur
	 * @throws PositionInvalideException Position d'arrivée invalide
	 * @throws CaseNonVideException Position d'arrivée déjà occupée
	 */
	public Map<Couleur, Position> getNids() {
        return nids;
    }
	
	/**
	 * Renvoie le nombre de boîtes actuellement dans le système
	 * @return Nombre de boites dans le système
	 */
	public int getNombreBoites() {
	    return nombreBoites;
	}
	
	/**
	 * Vérifie si le déplacement est valide par rapport à la position initiale
	 * @param depart Position de départ
	 * @param arrivee Position d'arrivée
	 * @return TRUE si le déplacement est valide, FALSE sinon
	 * @throws PositionInvalideException Position d'arrivée en dehors de la grille
	 * @throws CaseNonVideException Position d'arrivée déjà occupée
	 */
	public boolean estDeplacementValide(Position depart, Position arrivee) throws PositionInvalideException, CaseNonVideException {
	    if (!estPositionValide(arrivee)) {
	        throw new PositionInvalideException("La position " + arrivee + " est en dehors des limites de la grille");
	    }
	    if (!grille.estCaseVide(arrivee)) {
	        throw new CaseNonVideException("La position " + arrivee + " n'est pas libre");
	    }
	    // TODO Vérifier si déplacement d'une seule case
	    return true;
	}
	
	/**
	 * Indique si une position est valide dans la grille
	 * @param position Position à vérifier
	 * @return TRUE si la Position est contenue dans les limites de la grille, FALSE sinon
	 */
	private boolean estPositionValide(Position position) {
	    boolean coordXValide = position.getCoordX() >= 0 && position.getCoordX() <= hauteurGrille;
	    boolean coordYValide = position.getCoordY() >= 0 && position.getCoordY() <= largeurGrille;
	    return coordXValide && coordYValide;
	}
}
