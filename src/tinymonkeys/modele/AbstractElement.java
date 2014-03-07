package tinymonkeys.modele;

/**
 * Classe abstraite d'un element.
 * 
 * @version 1.0
 * @author Cl�ment Guet
 */
public abstract class AbstractElement
{

	/**
	 * Coordonnee en abscisse de l'element (indice dans la carte).
	 */
	protected int x;

	/**
	 * Coordonnee en ordonnee de l'element (indice dans la carte).
	 */
	protected int y;

	/**
	 * Constructeur de la classe abstraite AbstractElement.
	 * 
	 * @param x
	 *            la coordonnee en abscisse de l'element.
	 * @param y
	 *            la coordonnee en ordonnee de l'element.
	 */
	public AbstractElement(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Accesseur en lecture de la position en abscisse de l'élément.
	 * 
	 * @return la coordonnee en abscisse.
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Accesseur en lecture de la position en ordonnee de l'element.
	 * 
	 * @return la coordonnee en ordonnee.
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Accesseur en ecriture de la position de l'element.
	 * 
	 * @param x
	 *            la nouvelle coordonnee en abscisse de l'element.
	 * @param y
	 *            la nouvelle coordonnee en ordonnee de l'element.
	 */
	public void setPosition(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Retourne vrai si les coordonnees de l'element sont egales a celles
	 * indiquees, faux sinon.
	 * 
	 * @param x
	 *            la coordonnee en abscisse.
	 * @param y
	 *            le coordonnee en ordonnee.
	 * @return vrai ou faux selon l'egalite des coordonnees.
	 */
	public boolean coordonneesEgales(final int x, final int y)
	{
		return this.x == x && this.y == y;
	}

}
