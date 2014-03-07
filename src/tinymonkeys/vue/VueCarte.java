package tinymonkeys.vue;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


/**
 * Classe du panneau de la carte. 
 * 
 * @version 1.0
 * @author Cl�ment Guet
 *
 */
public class VueCarte extends JPanel
{

	/**
	 * UID auto-généré.
	 */
	private static final long serialVersionUID = 4884966649331011259L;
	
	/**
	 * Rapport entre la taille de la carte et la taille de l'écran.
	 */
	private static final double RAPPORT_ECRAN = 0.75; 
	
	/**
	 * Constante permettant de placer un objet à la moitié de l'écran.
	 */
	private static final int DIVISEUR_MILIEU = 2;
	
	/**
	 * Constante permettant de placer un objet au quart de l'écran.
	 */
	private static final int DIVISEUR_QUART = 4;
	
	/**
	 * Constante indiquant la couleur des cases représentant la mer. 
	 */
	private static final Color OCEAN = new Color(0, 120, 220);
	
	/**
	 * Taille de la case en nombre de pixels.
	 */
	private int tailleCase;
	
	/**
	 * La coordonnee en abscisse du coin supérieur gauche de la grille. 
	 */
	private int xGrille;
	
	/**
	 * La coordonnee en ordonnée du coin supérieur gauche de la grille. 
	 */
	private int yGrille;
	
	/**
	 * Largeur de l'ecran en nombre de pixels.
	 */
	private final int largeurEcran;
	
	/**
	 * Hauteur de l'ecran en nombre de pixels.
	 */
	private final int hauteurEcran;
	
	/**
	 * Largeur de la grille en nombre de cases.
	 */
	private int largeurGrille;
	
	/**
	 * Hauteur de la grille en nombre de cases.
	 */
	private int hauteurGrille;
	
	/**
	 * La carte.
	 */
	private int[][] carte;
	
		
	/**
	 * Constructeur de la vue de la carte. 
	 * 
	 * @param largeurEcran largeur de l'ecran en nombre de pixels.
	 * @param hauteurEcran hauteur de l'ecran en nombre de pixels.
	 * @param carte la carte a dessiner
	 */
	public VueCarte(final int largeurEcran, final int hauteurEcran, final int[][] carte)
	{
		super();
		this.largeurEcran = largeurEcran;
		this.hauteurEcran = hauteurEcran;
		this.largeurGrille = carte.length;
		this.hauteurGrille = carte[0].length;
		this.copieCarte(carte);
		this.placementGrille();
		this.setBounds(this.xGrille, this.yGrille, 
				this.largeurGrille * this.tailleCase + 1, 
				this.hauteurGrille * this.tailleCase + 1);
		this.setOpaque(false);
	}

	/**
	 * Dessine la carte de l'ile avec la grille. 
	 * 
	 * @param g le graphique dans lequel dessiner. 
	 */
	public void paintComponent(final Graphics g)
	{
		super.paintComponent(g);
		dessineIle(g);
		dessineGrille(g);
	}	

	/**
	 * Place la carte au centre de l'écran.
	 */
	private void placementGrille()
	{
		final int diviseurLargeur;
		final int diviseurHauteur;		
		
		final int largeurCase = (int) ((this.largeurEcran * RAPPORT_ECRAN) / this.largeurGrille);
		final int hauteurCase = (int) ((this.hauteurEcran * RAPPORT_ECRAN) / this.hauteurGrille);
				
		if (largeurCase < hauteurCase) {
			this.tailleCase = largeurCase;
			diviseurLargeur = DIVISEUR_QUART;
			diviseurHauteur = DIVISEUR_MILIEU;
		} else {
			this.tailleCase = hauteurCase;
			diviseurLargeur = DIVISEUR_MILIEU;
			diviseurHauteur = DIVISEUR_QUART;
		}		
			
		this.xGrille = (int) ((this.largeurEcran - (this.tailleCase * this.largeurGrille)) / diviseurLargeur);
		this.yGrille = (int) ((this.hauteurEcran - (this.tailleCase * this.hauteurGrille)) / diviseurHauteur);

	}
	
	/**
	 * Dessine la grille. 
	 * 
	 * @param g le graphique dans lequel dessiner. 
	 */
	public void dessineGrille(final Graphics g)
	{	
		//La grille apparait en noir.
		g.setColor(Color.BLACK);
		
		//colonnes
		for (int i = 0; i <= (this.tailleCase * this.largeurGrille); i += this.tailleCase) {
			g.drawLine(i, 0, i, this.tailleCase * this.hauteurGrille);
		}
		
		//lignes
		for (int j = 0; j <= this.tailleCase * this.hauteurGrille; j += this.tailleCase) {
			g.drawLine(0, j, this.tailleCase * this.largeurGrille, j);
		}
	}
	
	/**
	 * Dessine l'ile.
	 * 
	 * @param g le graphique dans lequel dessiner. 
	 */
	public void dessineIle(final Graphics g)
	{	
		for (int i = 0; i < this.largeurGrille; i++) {
			for (int j = 0; j < this.hauteurGrille; j++) {
				// Si la case est de type mer.
				if (this.carte[i][j] == Integer.valueOf(0)) {	
					g.setColor(OCEAN);
					g.fillRect(i * this.tailleCase, j * this.tailleCase, this.tailleCase, this.tailleCase);

				} 
				// Coloration inutile pour les cases terre.
			}
		}
	}

	/**
	 * Modifie la carte de l'ile.
	 * 
	 * @param carte la nouvelle carte.
	 */
	public void setVueCarte(final int[][] carte)
	{
		this.largeurGrille = carte.length;
		this.hauteurGrille = carte[0].length;
		this.copieCarte(carte);
 		this.placementGrille();
		this.setBounds(this.xGrille, this.yGrille, 
				this.largeurGrille * this.tailleCase + 1,
				this.hauteurGrille * this.tailleCase + 1);
		this.setOpaque(false);
	}
	
	/**
	 * Accesseur en lecture de la taille d'une case.
	 * 
	 * @return la taille d'une case.
	 */
	public int getTailleCase()
	{
		return this.tailleCase;
	}
	
	/**
	 * Accesseur en lecture de l'abscisse de la grille.
	 * 
	 * @return l'abscisse de la grille.
	 */
	public int getXGrille()
	{
		return this.xGrille;
	}
	
	/**
	 * Accesseur en lecture de l'ordonnee de la grille.
	 * 
	 * @return l'ordonnee de la grille.
	 */
	public int getYGrille()
	{
		return this.yGrille;
	}
	
	/**
	 * Recopie de la carte dans l'attribut carte.
	 * 
	 * @param carte la carte a copier.
	 */
	private void copieCarte(final int[][] carte)
	{
		this.carte = new int[carte.length][carte[0].length];
		for (int i = 0; i < carte.length; i++) {
			for (int j = 0; j < carte[0].length; j++) {
				this.carte[i][j] = carte[i][j];
			}
		}
	}
	
}
