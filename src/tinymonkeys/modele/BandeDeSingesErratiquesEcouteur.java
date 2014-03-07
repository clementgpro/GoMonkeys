/**
 * 
 */
package tinymonkeys.modele;

import java.util.EventListener;


/**
 * Ecouteur pour la bande de singes erratiques.
 * @version 1.0
 * @author Clément Guet
 *
 */
public interface BandeDeSingesErratiquesEcouteur extends EventListener
{

	/**
	 * Dessine l'avatar du singe erratique a la position indiquee. 
	 * 
	 * @param id l'identifiant du singe erratique.
	 * @param x la coordonnee en abscisse du singe.
	 * @param y la coordonnee en ordonnee du singe.
	 */
	void creationSingeErratique(int id, int x, int y);
	
	/**
	 * Deplace l'avatar du singe erratiques a sa nouvelle position.
	 * 
	 * @param id l'identifiant du singe erratique. 
	 * @param x la nouvelle coordonnee en abscisse du singe.
	 * @param y la nouvelle coordonnee en ordonnee du singe.
	 */
	void deplacementSingeErratique(int id, int x, int y);
	
}
