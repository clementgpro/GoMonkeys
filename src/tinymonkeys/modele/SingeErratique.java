package tinymonkeys.modele;

import java.util.List;
import java.util.Random;

/**
 * Classe du singe erratique.
 * 
 * @version 1.0
 * @author Clément Guet
 * 
 */
public class SingeErratique extends AbstractSinge
{

	/**
	 * Constructeur de la classe SingeErratique.
	 * 
	 * @param x
	 *            la coordonnee en abscisse du singe.
	 * @param y
	 *            la coordonnee en ordonnee du singe.
	 * @param ile
	 *            l'ile sur laquelle vit le singe.
	 */
	public SingeErratique(final int x, final int y, final Ile ile)
	{
		super(x, y, ile);
	}

	/**
	 * Deplacement aleatoire du singe erratique.
	 */
	public void deplacerSinge()
	{
		final Random random = new Random();
		int tmpX = this.x;
		int tmpY = this.y;
		do {
			tmpX = this.x;
			tmpY = this.y;
			final int d = random.nextInt(2);
			if (random.nextInt(2) == Integer.valueOf(0)) {
				// Déplacement du x
				tmpX = (d == 0) ? tmpX - 1 : tmpX + 1;
			} else {
				// Déplacement du y
				tmpY = (d == 0) ? tmpY - 1 : tmpY + 1;
			}
		} while (verifierDeplacementSinge(tmpX, tmpY));
		this.setPosition(tmpX, tmpY);
	}

	/**
	 * Vérifier si un singe est present a la case (x,y).
	 * 
	 * @param tmpX
	 *            l'abscicce
	 * @param tmpY
	 *            l'ordonnée
	 * @return un booleen
	 */
	private boolean verifierDeplacementSinge(final int tmpX, final int tmpY)
	{
		boolean dplInterdit = false;
		if (this.monkeyIsland.valeurCarte(tmpX, tmpY) == Integer.valueOf(0)) {
			dplInterdit = true;
		} else {
			// Verifier pas de singe
			int n = 0;
			final List<SingeErratique> erratiques = this.monkeyIsland.getSingesErratiques().getSingesErratiques();
			while (n < erratiques.size() && !dplInterdit) {
				dplInterdit = erratiques.get(n).coordonneesEgales(tmpX, tmpY);
				n++;
			}
			// Verifier pirate present => si oui a tuer
			if (tmpX == this.monkeyIsland.getPirate().getX() && tmpY == this.monkeyIsland.getPirate().getY()) {
				this.monkeyIsland.getPirate().setMort(true);
				if (this.monkeyIsland.getPirate().getPirateEcouteurs() != null) {
					for (final PirateEcouteur pirateEcouteur : this.monkeyIsland.getPirate().getPirateEcouteurs()
							.getListeners(PirateEcouteur.class)) {
						pirateEcouteur.mortPirate(0);
					}
				}
			}
		}
		return dplInterdit;
	}
}
