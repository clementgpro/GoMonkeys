package tinymonkeys.vue;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe de la vue d'un singe erratique.
 * 
 * @version 1.0
 * @author Cl�ment Guet
 *
 */
public class VueSingeErratique extends VuePersonnage
{

	/**
	 * UID auto-genere.
	 */
	private static final long serialVersionUID = -3380651241366347981L;
	
	/**
	 * Emplacement de l'image du singe erratique.
	 */
	private static final String IMAGE_SINGE_ERRATIQUE = "./img/Singe_Erratique.png";
	
	
	/**
	 * La vue d'un singe erratique.
	 * 
	 * @param tailleCase la taille d'une case en nombre de pixels
	 * @param xGrille l'abscissse du coin supérieur gauche de la grille ou placer l'image (en pixels). 
	 * @param yGrille l'ordonnee du coin supérieur gauche de la grille ou placer l'image (en pixels). 
	 * @param x la position en abscisse du singe erratique (nombre de cases).
	 * @param y la position en ordonnee du singe erratique (nombre de cases).
	 */
	public VueSingeErratique(final int tailleCase, final int xGrille, final int yGrille, final int x, final int y) 
	{
		super(tailleCase, xGrille, yGrille, x, y);
		
		try {
			final File input = new File(IMAGE_SINGE_ERRATIQUE);
			this.imageElement = ImageIO.read(input);
		} 
		catch (IOException ie) {
			System.out.println("Error:" + ie.getMessage());
		}	
		
	}



}
