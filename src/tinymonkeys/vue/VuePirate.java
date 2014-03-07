package tinymonkeys.vue;

import java.awt.Graphics;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe de la vue d'un pirate.
 * 
 * @version 1.0
 * @author Cl�ment Guet
 * 
 */
public class VuePirate extends VuePersonnage
{

	/**
	 * UID auto-genere.
	 */
	private static final long serialVersionUID = 6369086645437970899L;

	/**
	 * Emplacement de l'avatar par defaut du pirate.
	 */
	private static final String AVATAR_PIRATE = "./img/Mon_pirate.png";

	/**
	 * Message d'erreur.
	 */
	private static final String ERROR = "Error : ";

	/**
	 * Emplacement de l'avatar du pirate mort.
	 */
	private static final String AVATAR_PIRATE_MORT = "./img/Pirate_Mort.png";

	/**
	 * La vue d'un pirate.
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
	 *            la position en abscisse du pirate (nombre de cases).
	 * @param y
	 *            la position en ordonnee du pirate (nombre de cases).
	 * @param avatar
	 *            le lien vers l'avatar du pirate.
	 */
	public VuePirate(final int tailleCase, final int xGrille, final int yGrille, final int x, final int y, final String avatar)
	{
		super(tailleCase, xGrille, yGrille, x, y);

		final String avatarPirate = (avatar == null) ? AVATAR_PIRATE : avatar;

		try {
			final File input = new File(avatarPirate);
			this.imageElement = ImageIO.read(input);
		}
		catch (IOException ie) {
			System.out.println(ERROR + ie.getMessage());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dessineElement(final Graphics g)
	{
		g.drawImage(this.imageElement, 0, 0, this.tailleImage, this.tailleImage, null);
	}

	/**
	 * L'avatar du pirate devient un avatar de mort.
	 */
	public void mortPirate()
	{
		try {
			final File input = new File(AVATAR_PIRATE_MORT);
			this.imageElement = ImageIO.read(input);
		}
		catch (IOException ie) {
			System.out.println(ERROR + ie.getMessage());
		}
	}
}
