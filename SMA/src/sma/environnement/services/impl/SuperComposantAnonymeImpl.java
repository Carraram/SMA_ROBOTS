package sma.environnement.services.impl;

import java.io.IOException;
import java.util.Map;

import sma.agents.pojo.EtatRobot;
import sma.common.pojo.CaseNonVideException;
import sma.common.pojo.Couleur;
import sma.common.pojo.Position;
import sma.common.pojo.PositionInvalideException;
import sma.environnement.pojo.BoiteCouleur;
import sma.environnement.pojo.EtatEnvironnement;
import sma.environnement.services.interfaces.IEnvVisualisation;
import sma.environnement.services.interfaces.IInteraction;
import sma.environnement.services.interfaces.IPerception;
import compEnvironnement.Nid;
import compEnvironnement.SuperComposantAnonyme;
import utils.PropertyFileReader;

public class SuperComposantAnonymeImpl extends SuperComposantAnonyme {

    /**
     * Etat de l'environnement
     */
    private EtatEnvironnement environnement;
    
    public SuperComposantAnonymeImpl(Nid nidRouge, Nid nidBleu, Nid nidVert) {
        System.out.println("\r\n***** La Genèse - Bible des SMA V0.1 *****\r\n");
        System.out.println("Au commencement, le Super Composant Anonyme créa l'environnement...");
        // Configuration de l'environnement
        int[] configurationEnv = new int[3];
        try {
            PropertyFileReader config = new PropertyFileReader("config/environnementConfig.properties");
            configurationEnv[0] = config.getProprieteInt("nbBoitesInit");
            configurationEnv[1] = config.getProprieteInt("nbColonnesGrille");
            configurationEnv[2] = config.getProprieteInt("nbLignesGrille");
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Une erreur est survenue lors de la lecture du fichier de configuration de l'environnement");
            System.err.println("Utilisation de la configuration par défaut");
            ex.printStackTrace();
            // Valeurs par défaut en cas d'échec de la lecture du fichier de configuration
            configurationEnv[0] = 0;
            configurationEnv[1] = 50;
            configurationEnv[2] = 50;
        }
        environnement = new EtatEnvironnement(configurationEnv[0], configurationEnv[1], configurationEnv[2]);
        
        // TODO Créer les nids à équidistance en fonction de la taille de la grille
        try {
            System.out.println("Les trois jours qui suivirent, le Super Composant Anonyme créa les nids...");
            environnement.creerNid(nidRouge, Couleur.ROUGE, new Position(0,0));
            environnement.creerNid(nidBleu, Couleur.BLEU, new Position(1,1));
            environnement.creerNid(nidVert, Couleur.VERT, new Position(2,2));
        } catch (CaseNonVideException | PositionInvalideException e) {
            e.printStackTrace();
        }
        
        System.out.println("Le Cinquième jour, le Super Composant Anonyme créa les boites...");
        // TODO Placer les boites de départ
        
        System.out.println("Le Sixième jour, le Super Composant Anonyme créa le générateur de boites...");
        // TODO Lancer le générateur de boites
        System.out.println("Le Septième Jour, le Super Composant Anonyme se reposa...");
        System.out.println("\r\n***** Et c'est ainsi que fut créé l'environnement *****\r\n");
    }
    
	@Override
	protected IInteraction make_interactionService() {
		return new IInteraction() {
			
			@Override
			public float deposerBoite(BoiteCouleur boite, EtatRobot etatRobot) {
			    float energieRecue = 0.F;
			    switch (boite) {
			    case BLEU:
			        energieRecue = SuperComposantAnonymeImpl.this.requires().depotBleuService().deposerBoite(boite, etatRobot);
			        break;
			    case ROUGE:
			        energieRecue = SuperComposantAnonymeImpl.this.requires().depotRougeService().deposerBoite(boite, etatRobot);
			        break;
			    case VERT:
			        energieRecue = SuperComposantAnonymeImpl.this.requires().depotVertService().deposerBoite(boite, etatRobot);
			        break;
			    }
			    
				etatRobot.augmenterEnergie(energieRecue);
				System.out.println("Le robot a recu " + energieRecue + "NE.");
				return energieRecue;
			}

            @Override
            public void seDeplacer(Position positionInitiale, Position nouvellePosition) throws CaseNonVideException, PositionInvalideException {
                if (environnement.estDeplacementValide(positionInitiale, nouvellePosition)) {
                    environnement.deplacerRobot(positionInitiale, nouvellePosition);
                }
                
            }
		};
	}

    @Override
    protected IPerception make_perceptionService() {
        return new IPerception() {
            
            @Override
            public Map<Couleur, Position> getPositionNids() {
                return environnement.getNids();
            }
        };
    }

    @Override
    protected IEnvVisualisation make_visualisationService() {
        return new IEnvVisualisation() {
            
            @Override
            public void afficherEtat() {
                Map<Couleur, Position> nids = environnement.getNids();
                String etatNids = "";
                etatNids += "Nid rouge : " + nids.get(Couleur.ROUGE);
                etatNids += "   Nid vert : " + nids.get(Couleur.VERT);
                etatNids += "   Nid bleu : " + nids.get(Couleur.BLEU);
                String etatBoites = "Nombre de boites dans l'environnement : " + environnement.getNombreBoites();
                String couleurMer = "C'est la mer noire";
                SuperComposantAnonymeImpl.this.requires().affichageService().afficherMessages(new String[] {etatNids, etatBoites, couleurMer});
            }
        };
    }

}
