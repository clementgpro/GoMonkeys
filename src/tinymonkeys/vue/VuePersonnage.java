package tinymonkeys.vue;

/**
 * Classe de la vue d'un personnage.
 * 
 * @version 1.0
 * @author Cl�ment Guet
 * 
 */
public class VuePersonnage extends VueElement
{

	/**
	 * UID auto-genere.
	 */
	private static final long serialVersionUID = 875195821150349113L;

	/**
	 * La vue d'un personnage.
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
	 *            la position en abscisse du personnage (nombre de cases).
	 * @param y
	 *            la position en ordonnee du personnage (nombre de cases).
	 */
	public VuePersonnage(final int tailleCase, final int xGrille, final int yGrille, final int x, final int y)
	{
		super(tailleCase, xGrille, yGrille, x, y);
	}

	/**
	 * Modifie l'emplacement de l'image du personnage.
	 * 
	 * @param x
	 *            la nouvelle abscisse de l'image du personnage.
	 * @param y
	 *            la nouvelle ordonnee de l'image du personnage.
	 */
	public void setPosition(final int x, final int y)
	{
		this.x = x;
		this.y = y;
		final int xImage = this.xGrille + x * this.tailleCase + DIFFERENCE_PLACEMENT_CASE_IMAGE;
		final int yImage = this.yGrille + y * this.tailleCase + DIFFERENCE_PLACEMENT_CASE_IMAGE;
		this.setLocation(xImage, yImage);
	}

}
