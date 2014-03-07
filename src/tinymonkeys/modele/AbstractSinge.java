package tinymonkeys.modele;

/**
 * Classe abstraite Singe.
 * 
 * @version 1.0
 * @author Clément Guet
 */
public abstract class AbstractSinge extends AbstractElement
{
	/**
	 * Ile contenant tous les elements de celle-ci.
	 */
	protected Ile monkeyIsland;

	/**
	 * Constructeur de la classe Singe.
	 * 
	 * @param x
	 *            la coordonnee en abscisse du singe.
	 * @param y
	 *            la coordonnee en ordonnee du singe.
	 * @param ile
	 *            l'ile sur laquelle vit le singe.
	 */
	public AbstractSinge(final int x, final int y, final Ile ile)
	{
		super(x, y);
		this.monkeyIsland = ile;
	}

	/**
	 * Methode de deplacement du singe. Le deplacement est propre au type de
	 * singe (erratique, chasseur...).
	 */
	public abstract void deplacerSinge();

	/**
	 * Getter for monkeyIsland.
	 * 
	 * @return the monkeyIsland
	 */
	public final Ile getMonkeyIsland()
	{
		return this.monkeyIsland;
	}

	/**
	 * Setter for monkeyIsland.
	 * 
	 * @param monkeyIsland
	 *            the monkeyIsland to set
	 */
	public final void setMonkeyIsland(final Ile monkeyIsland)
	{
		this.monkeyIsland = monkeyIsland;
	}

}
