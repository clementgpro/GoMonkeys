package tinymonkeys.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.event.EventListenerList;

/**
 * Classe d'une bande de singes erratiques.
 * 
 * @version 1.0
 * @author Clément Guet
 * 
 */
public class BandeDeSingesErratiques extends Thread
{

	/**
	 * Vecteur contenant l'ensemble des singes erratiques.
	 */
	private final List<SingeErratique> erratiques;

	/**
	 * L'ile.
	 */
	private final Ile monkeyIsland;

	/**
	 * Liste des Ã©couteurs sur la bande de singes erratiques.
	 */
	private final EventListenerList bandeSingesEcouteurs;

	/**
	 * Génération de nombre aléatoires.
	 */
	private final Random random;

	/**
	 * Constructeur d'une bande de singes erratiques vide.
	 * 
	 * @param ile
	 *            l'ile contenant l'ensemble des elements de celle-ci.
	 */
	public BandeDeSingesErratiques(final Ile ile)
	{
		super();
		this.erratiques = new ArrayList<SingeErratique>();
		this.monkeyIsland = ile;
		this.bandeSingesEcouteurs = new EventListenerList();
		this.random = new Random();
	}

	/**
	 * Accesseur en lecture a l'ensemble des singes erratiques.
	 * 
	 * @return le vecteur de singes erratiques.
	 */
	public List<SingeErratique> getSingesErratiques()
	{
		return this.erratiques;
	}

	/**
	 * Ajout du nombre indique de singes erratiques a des positions libres
	 * aleatoires.
	 * 
	 * @param n
	 *            le nombre de singes a ajouter.
	 */
	public void ajoutSingesErratiques(final int n)
	{
		int id = 0;
		for (int nbSinges = 0; nbSinges < n; nbSinges++) {
			int x = this.random.nextInt(this.monkeyIsland.getLongueurCarte());
			int y = this.random.nextInt(this.monkeyIsland.getLargeurCarte());
			while (this.monkeyIsland.valeurCarte(x, y) == 0 && !isSinge(x, y)) {
				x = this.random.nextInt(this.monkeyIsland.getLongueurCarte());
				y = this.random.nextInt(this.monkeyIsland.getLargeurCarte());
			}
			this.erratiques.add(new SingeErratique(x, y, this.monkeyIsland));
			for (final BandeDeSingesErratiquesEcouteur singeEcouteur : this.bandeSingesEcouteurs
					.getListeners(BandeDeSingesErratiquesEcouteur.class)) {
				singeEcouteur.creationSingeErratique(id, x, y);
			}
			id++;
		}

	}

	/**
	 * Permet de savoir si un singe est présent à la case indiquée.
	 * 
	 * @param x
	 *            le x
	 * @param y
	 *            le y
	 * @return un boolean
	 */
	private boolean isSinge(final int x, final int y)
	{
		boolean singe = false;
		final Iterator<SingeErratique> iteSinge = this.erratiques.iterator();

		while (iteSinge.hasNext()) {
			final SingeErratique singeErratique = iteSinge.next();
			singe = singeErratique.coordonneesEgales(x, y);
		}
		return singe;
	}

	/**
	 * Enregistre dans la liste des ecouteurs de bande de singes l'ecouteur
	 * passe en parametre.
	 * 
	 * @param ecouteur
	 *            ecouteur de la bande de singes.
	 */
	public void enregistreEcBandeSinges(final BandeDeSingesErratiquesEcouteur ecouteur)
	{
		this.bandeSingesEcouteurs.add(BandeDeSingesErratiquesEcouteur.class, ecouteur);
	}

	@Override
	public void run()
	{
		while (true) {
			int id = 0;
			for (final SingeErratique singe : this.getSingesErratiques()) {
				singe.deplacerSinge();
				for (final BandeDeSingesErratiquesEcouteur bandeEcouteur : this.bandeSingesEcouteurs
						.getListeners(BandeDeSingesErratiquesEcouteur.class)) {
					bandeEcouteur.deplacementSingeErratique(id, singe.getX(), singe.getY());
				}
				id++;
			}
			try {
				Thread.sleep(300);
			} 
			catch (InterruptedException e) {
				System.err.println("Oups there is a bad mistake in the monkey's thread !");
			}
		}
	}

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
	 * Getter for bandeSingesEcouteurs.
	 * 
	 * @return the bandeSingesEcouteurs
	 */
	public final EventListenerList getBandeSingesEcouteurs()
	{
		return this.bandeSingesEcouteurs;
	}

	/**
	 * Getter for erratiques.
	 * 
	 * @return the erratiques
	 */
	public final List<SingeErratique> getErratiques()
	{
		return this.erratiques;
	}

	/**
	 * Getter for random.
	 * 
	 * @return the random
	 */
	public final Random getRandom()
	{
		return this.random;
	}

}
