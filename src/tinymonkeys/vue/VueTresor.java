package tinymonkeys.vue;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe de la vue d'un tresor.
 * 
 * @version 1.0
 * @author Cl�ment Guet
 * 
 */

public class VueTresor extends VueElement
{

	/**
	 * UID auto-genere.
	 */
	private static final long serialVersionUID = -6188222545656061618L;

	/**
	 * Emplacement de l'image d'une banane.
	 */
	private static final String IMAGE_TRESOR = "./img/Tresor.png";

	/**
	 * La vue d'un tresor.
	 * 
	 * @param tailleCase
	 *            la taille d'une case en nombre de pixels
	 * @param xGrille
	 *            l'abscissse du coin supérieur gauche de la grille ou placer
	 *            l'image (en pixels).
	 * @param yGrille
	 *            l'ordonnee du coin supérieur gauche de la grille ou placer
	 *            l'image (en pixels).
	 * @param x
	 *            la position en abscisse du tresor (nombre de cases).
	 * @param y
	 *            la position en ordonnee du tresor (nombre de cases).
	 */
	public VueTresor(final int tailleCase, final int xGrille, final int yGrille, final int x, final int y)
	{
		super(tailleCase, xGrille, yGrille, x, y);

		try {
			final File input = new File(IMAGE_TRESOR);
			this.imageElement = ImageIO.read(input);
		}
		catch (IOException ie) {
			System.out.println("Error:" + ie.getMessage());
		}

	}

}
