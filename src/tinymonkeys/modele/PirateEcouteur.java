package tinymonkeys.modele;

import java.util.EventListener;

/**
 * Ecouteur pour un pirate.
 * 
 * @version 1.0
 * @author Clément Guet
 */
public interface PirateEcouteur extends  EventListener
{
	/**
	 * Dessine l'avatar du pirate a la position indiquee. 
	 * 
	 * @param id l'identifiant du pirate.
	 * @param x la coordonnee en abscisse du pirate.
	 * @param y la coordonnee en ordonnee du pirate.
	 * @param avatar le lien vers l'avatar du pirate.
	 */
	void ajoutPirate(int id, int x, int y, String avatar);
	
	/**
	 * Deplace l'avatar du pirate a sa nouvelle position.
	 * 
	 * @param id l'identifiant du pirate. 
	 * @param x la nouvelle coordonnee en abscisse du pirate.
	 * @param y la nouvelle coordonnee en ordonnee du pirate.
	 */
	void deplacementPirate(int id, int x, int y);
	
	/**
	 * L'avatar du pirate identifie devient un avatar de mort.
	 * 
	 * @param id l'identifiant du pirate.
	 */
	void mortPirate(int id);
	
	/**
	 * Methode permettant de rendre le clavier disponible (actif) a l'utilisateur 
	 * (lorsque celui-ci a ete bloque).
	 */
	void liberationClavier();
}
