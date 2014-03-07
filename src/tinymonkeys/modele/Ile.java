package tinymonkeys.modele;

import java.util.Random;

import javax.swing.event.EventListenerList;

/**
 * Classe Ile.
 * 
 * @version 1.0
 * @author Clément Guet
 * 
 */
public class Ile
{
	/**
	 * La carte de l'ile : une matrice indiquant si chaque case est de type mer
	 * (0) ou terre (1).
	 */
	private int[][] carte;

	/**
	 * Les singes erratiques.
	 */
	private final BandeDeSingesErratiques erratiques;

	/**
	 * Le tresor.
	 */
	private Tresor tresor;

	/**
	 * Le pirate du joueur.
	 */
	private Pirate pirate;

	/**
	 * Liste des ecouteurs sur l'ile.
	 */
	private final EventListenerList ileEcouteurs;

	/**
	 * Génération de nombre aléatoires.
	 */
	private final Random random;

	/**
	 * Constructeur de la classe Ile.
	 */
	public Ile()
	{
		this.erratiques = new BandeDeSingesErratiques(this);
		this.pirate = new Pirate(this);
		this.ileEcouteurs = new EventListenerList();
		this.random = new Random();
	}

	/**
	 * Indique la largeur de la carte en nombre de cases.
	 * 
	 * @return la largeur de la carte.
	 */
	public int getLargeurCarte()
	{
		return this.carte.length;
	}

	/**
	 * Indique la longueur de la carte en nombre de cases.
	 * 
	 * @return la longueur de la carte.
	 */
	public int getLongueurCarte()
	{
		return this.carte[0].length;
	}

	/**
	 * Permet l'acces en lecture a la valeur de la carte aux coordonnees
	 * indiquees.
	 * 
	 * @param x
	 *            la coordonnee en abscisse.
	 * @param y
	 *            la coordonnee en ordonnee.
	 * @return la valeur de la case de la carte aux coordonnees indiquees.
	 */
	public int valeurCarte(final int x, final int y)
	{
		return this.carte[x][y];
	}

	/**
	 * Creation de la carte.
	 * 
	 * @param carte
	 *            la matrice terre-mer.
	 */
	public void creationCarte(final int[][] carte)
	{
		this.carte = new int[carte.length][carte[0].length];
		for (int i = 0; i < carte.length; i++) {
			for (int j = 0; j < carte[i].length; j++) {
				this.carte[i][j] = carte[i][j];
			}
		}

		for (final IleEcouteur ileEcouteur : this.ileEcouteurs.getListeners(IleEcouteur.class)) {
			ileEcouteur.creationCarte(this.carte);
		}
	}

	/**
	 * Creation du pirate sur l'ile.
	 * 
	 * @param avatar
	 *            le lien vers l'image du pirate.
	 */
	public void ajoutPirate(final String avatar)
	{
		int x = 0;
		int y = 0;
		// Tant que le pirate est dans la mere
		while (this.valeurCarte(x, y) == 0) {
			x = this.random.nextInt(this.getLongueurCarte());
			y = this.random.nextInt(this.getLargeurCarte());
		}
		this.pirate.setAvatar(avatar);
		this.pirate.positionInitiale(x, y);
	}

	/**
	 * Methode permettant de faire la demande de deplacement du pirate. Cette
	 * methode fait suite a un appui sur une fleche directionnelle du clavier.
	 * 
	 * @param dx
	 *            la direction en abscisse.
	 * @param dy
	 *            la direction en ordonnee.
	 */
	public void demandeDeplacementPirate(final int dx, final int dy)
	{
		this.pirate.demandeDeplacement(dx, dy);
	}

	/**
	 * Creation du tresor a une position aleatoire.
	 */
	public void creationTresor()
	{
		// x et y forcement dans une case terre
		final int x = 1 + this.random.nextInt(this.getLongueurCarte() - 2);
		final int y = 1 + this.random.nextInt(this.getLargeurCarte() - 2);
		this.tresor = new Tresor(x, y);
		for (final IleEcouteur ileEcouteur : this.ileEcouteurs.getListeners(IleEcouteur.class)) {
			ileEcouteur.creationTresor(x, y);
		}

	}

	/**
	 * Suppression du tresor.
	 */
	public void suppressionTresor()
	{
		for (final IleEcouteur ileEcouteur : this.ileEcouteurs.getListeners(IleEcouteur.class)) {
			ileEcouteur.suppressionTresor();
		}
		this.creationTresor();
	}

	/**
	 * Enregistre dans la liste des ecouteurs de l'ile l'ecouteur passe en
	 * parametre.
	 * 
	 * @param ecouteur
	 *            ecouteur de l'ile.
	 */
	public void enregistreEcIle(final IleEcouteur ecouteur)
	{
		this.ileEcouteurs.add(IleEcouteur.class, ecouteur);
	}

	/**
	 * Accesseur en lecture du pirate de l'ile.
	 * 
	 * @return le pirate.
	 */
	public Pirate getPirate()
	{
		return this.pirate;
	}
	
	/**
	 * Getter for carte.
	 * 
	 * @return the carte
	 */
	public final int[][] getCarte()
	{
		final int carte[][] = new int[this.carte.length][this.carte[0].length];
		for (int i = 0; i < this.carte.length; i++) {
			for (int j = 0; j < this.carte[i].length; j++) {
				carte[i][j] = this.carte[i][j];
			}
		}
		return carte;
	}

	/**
	 * Mise Ã  jour de la carte.
	 * 
	 * @param carte
	 *            la matrice terre-mer.
	 */
	public void setCarte(final int[][] carte)
	{
		for (int i = 0; i < carte.length; i++) {
			for (int j = 0; j < carte[i].length; j++) {
				this.carte[i][j] = carte[i][j];
			}
		}
	}

	/**
	 * Getter for ileEcouteurs.
	 * 
	 * @return the ileEcouteurs
	 */
	public final EventListenerList getIleEcouteurs()
	{
		return this.ileEcouteurs;
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

	/**
	 * Setter for tresor.
	 * 
	 * @param tresor
	 *            the tresor to set
	 */
	public final void setTresor(final Tresor tresor)
	{
		this.tresor = tresor;
	}

	/**
	 * Setter for pirate.
	 * 
	 * @param pirate
	 *            the pirate to set
	 */
	public final void setPirate(final Pirate pirate)
	{
		this.pirate = pirate;
	}

	/**
	 * Accesseur en lecture de la bande de singes erratiques.
	 * 
	 * @return la bande de singes erratiques.
	 */
	public BandeDeSingesErratiques getSingesErratiques()
	{
		return this.erratiques;
	}

	/**
	 * Ajout du nombre indique de singes erratiques dans la liste des singes
	 * erratiques.
	 * 
	 * @param n
	 *            le nombre de singes erratiques a ajouter.
	 */
	public void ajoutSingesErratiques(final int n)
	{
		this.erratiques.ajoutSingesErratiques(n);
	}

	/**
	 * Accesseur en lecture du tresor.
	 * 
	 * @return le tresor.
	 */
	public Tresor getTresor()
	{
		return this.tresor;
	}

}
