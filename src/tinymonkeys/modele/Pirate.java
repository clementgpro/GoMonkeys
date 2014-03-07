package tinymonkeys.modele;

import java.util.Iterator;
import java.util.List;

import javax.swing.event.EventListenerList;

/**
 * Classe d'un pirate.
 * 
 * @version 1.0
 * @author Clément Guet
 * 
 */
public class Pirate
{

	/**
	 * Le chemin vers l'image du pirate.
	 */
	private String avatar;

	/**
	 * Coordonnee en abscisse du pirate (indice dans la carte).
	 */
	private int x;

	/**
	 * Coordonnee en ordonnee du pirate (indice dans la carte).
	 */
	private int y;

	/**
	 * Ile aux singes.
	 */
	private Ile monkeyIsland;

	/**
	 * Liste des Ã©couteurs sur le pirate.
	 */
	private final EventListenerList pirateEcouteurs;

	/**
	 * Indique si le pirate est mort.
	 */
	private boolean mort;

	/**
	 * Constructeur du pirate sans position ni nom renseignes mais avec l'ile
	 * associee.
	 * 
	 * @param ile
	 *            l'ile contenant tous les elements de celle-ci.
	 */
	public Pirate(final Ile ile)
	{
		this.monkeyIsland = ile;
		this.pirateEcouteurs = new EventListenerList();
		this.mort = false;
	}

	/**
	 * Constructeur du pirate avec le nom renseigne.
	 * 
	 * @param ile
	 *            l'ile contenant tous les elements de celle-ci.
	 * @param avatar
	 *            le lien vers l'avatar du pirate.
	 */
	public Pirate(final Ile ile, final String avatar)
	{
		this.monkeyIsland = ile;
		this.avatar = avatar;
		this.pirateEcouteurs = new EventListenerList();
	}

	/**
	 * Accesseur en lecture de la position en abscisse du pirate.
	 * 
	 * @return la coordonnee en abscisse.
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Accesseur en lecture de la position en ordonnee du pirate.
	 * 
	 * @return la coordonnee en ordonnee.
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Place le pirate a sa position initiale.
	 * 
	 * @param x
	 *            la coordonnee initiale en abscisse.
	 * @param y
	 *            la coordonnee initiale en ordonnee.
	 */
	public void positionInitiale(final int x, final int y)
	{
		this.x = x;
		this.y = y;
		for (final PirateEcouteur pirateEcouteur : this.pirateEcouteurs.getListeners(PirateEcouteur.class)) {
			pirateEcouteur.ajoutPirate(0, x, y, this.getAvatar());
		}
	}

	/**
	 * Methode deplacant le pirate selon la direction demandee. Si le
	 * deplacement indique positionne le pirate sur un singe, le pirate meurt.
	 * Si le deplacement indique positionne le pirate sur le tresor, le tresor
	 * est detruit.
	 * 
	 * @param dx
	 *            la direction en abscisse (comprise entre -1 et 1).
	 * @param dy
	 *            la direction en ordonnee (comprise entre -1 et 1).
	 */
	public void demandeDeplacement(final int dx, final int dy)
	{
		final int nouveauX = this.x + dx;
		final int nouveauY = this.y + dy;

		if (!this.mort) {
			// Verifier mort pirate
			final BandeDeSingesErratiques bandeSinge = this.monkeyIsland.getSingesErratiques();
			final List<SingeErratique> vectorSinge = bandeSinge.getSingesErratiques();
			final Iterator<SingeErratique> iteSinge = vectorSinge.iterator();
			while (iteSinge.hasNext() && !this.mort) {
				final SingeErratique singe = iteSinge.next();
				if (singe.coordonneesEgales(nouveauX, nouveauY)) {
					this.mort = true;
				}
			}
			// Verifier detruire tresor
			if (this.monkeyIsland.getTresor().coordonneesEgales(nouveauX, nouveauY)) {
				this.monkeyIsland.suppressionTresor();
			}

			// Appel à l'IHM
			for (final PirateEcouteur ecouteur : this.pirateEcouteurs.getListeners(PirateEcouteur.class)) {
				if (this.mort) {
					ecouteur.mortPirate(0);
				} else {
					ecouteur.liberationClavier();
					// Gestion des bords
					if (this.monkeyIsland.valeurCarte(nouveauX, nouveauY) == Integer.valueOf(1)) {
						this.setX(nouveauX);
						this.setY(nouveauY);
						ecouteur.deplacementPirate(0, nouveauX, nouveauY);
					}
				}
			}
		}
	}

	/**
	 * Accesseur en lecture de l'avatar.
	 * 
	 * @return le chemin vers l'image.
	 */
	public String getAvatar()
	{
		return this.avatar;
	}

	/**
	 * Accesseur en ecriture de l'avatar du pirate.
	 * 
	 * @param avatar
	 *            l'avatar du pirate.
	 */
	public void setAvatar(final String avatar)
	{
		this.avatar = avatar;
	}

	/**
	 * Enregistre dans la liste des ecouteurs de pirate l'ecouteur passe en
	 * parametre.
	 * 
	 * @param ecouteur
	 *            ecouteur du pirate.
	 */
	public void enregistreEcPirate(final PirateEcouteur ecouteur)
	{
		this.pirateEcouteurs.add(PirateEcouteur.class, ecouteur);
	}

	/**
	 * Getter de l'ile.
	 * 
	 * @return the monkeyIsland
	 */
	public final Ile getMonkeyIsland()
	{
		return this.monkeyIsland;
	}

	/**
	 * Setter de monkey island.
	 * 
	 * @param monkeyIsland
	 *            the monkeyIsland to set
	 */
	public final void setMonkeyIsland(final Ile monkeyIsland)
	{
		this.monkeyIsland = monkeyIsland;
	}

	/**
	 * Getter des ecouteurs de pirate.
	 * 
	 * @return the pirateEcouteurs
	 */
	public final EventListenerList getPirateEcouteurs()
	{
		return this.pirateEcouteurs;
	}

	/**
	 * Setter of x.
	 * 
	 * @param x
	 *            the x to set
	 */
	public final void setX(final int x)
	{
		this.x = x;
	}

	/**
	 * Setter of y.
	 * 
	 * @param y
	 *            the y to set.
	 */
	public final void setY(final int y)
	{
		this.y = y;
	}

	/**
	 * Getter for mort.
	 * 
	 * @return the mort
	 */
	public final boolean isMort()
	{
		return this.mort;
	}

	/**
	 * Setter for mort.
	 * 
	 * @param mort
	 *            the mort to set
	 */
	public final void setMort(final boolean mort)
	{
		this.mort = mort;
	}

}
