package tinymonkeys.modele;

import java.util.EventListener;

/**
 * Ecouteur pour l'ile.
 * @version 1.0
 * @author Clément Guet
 *
 */
public interface IleEcouteur extends  EventListener
{
	/**
	 * CrÃ©ation de l'affichage de laa carte.
	 * 
	 * @param carte la carte a dessiner.
	 */
	void creationCarte(int[][] carte);
	
	/**
	 * Modifie la carte affichee.
	 * @param carte la nouvelle carte.
	 */
	void changementCarte(int[][] carte);
	
	/**
	 * Creation de l'image du singe erratique identifie aux coordonnees indiquees.
	 * @param id l'identifiant du singe erratique.
	 * @param x coordonnee en abscisse du singe erratique.
	 * @param y coordonnee en ordonnee du singe erratique.
	 */
	void creationSingeErratique(int id, int x, int y);
	
	/**
	 * Deplacement de l'image du singe erratique identifie aux coordonnees indiquees.
	 * @param id l'identifiant du singe erratique.
	 * @param x coordonnee en abscisse du singe erratique.
	 * @param y coordonnee en ordonnee du singe erratique.
	 */
	void deplacementSingeErratique(int id, int x, int y);
	
	/**
	 * Creation de l'image du tresor aux coordonnees indiquees.
	 * @param x coordonnee en abscisse du tresor.
	 * @param y coordonnee en ordonnee du tresor.
	 */
	void creationTresor(int x, int y);
	
	/**
	 * Suppression de l'image du tresor.  
	 */ 
	void suppressionTresor();

	
}
