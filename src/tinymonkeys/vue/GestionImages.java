package tinymonkeys.vue;

import java.awt.Dimension;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Classe Gestion d'images de TinyMonkeys.
 * 
 * @version 1.0
 * 
 * @author Cl�ment Guet
 * 
 */
public class GestionImages extends JPanel
{

	/**
	 * UID auto-genere.
	 */
	private static final long serialVersionUID = 7424802032933884634L;

	/**
	 * Panneau de gestion.
	 */
	private final JLayeredPane layeredPane;

	/**
	 * La vue de la carte.
	 */
	private final VueCarte vueCarte;

	/**
	 * L'ensemble des vues des pirates trie selon l'identifiant de celui-ci.
	 */
	private VuePirate vuePirate;

	/**
	 * L'ensemble des vues des singes erratiques trie selon l'identifiant de
	 * celui-ci.
	 */
	private final Map<Integer, VueSingeErratique> vuesSingesErratiques;

	/**
	 * Vue du tresor.
	 */
	private VueTresor vueTresor;

	/**
	 * Constructeur du gestionnaire d'images.
	 * 
	 * @param aLayeredPane
	 *            le panneau gérant les différentes images.
	 * @param largeurEcran
	 *            la largeur de l'écran.
	 * @param hauteurEcran
	 *            la hauteur de l'écran.
	 * @param carte
	 *            la carte de l'ile.
	 */
	public GestionImages(final JLayeredPane aLayeredPane, final int largeurEcran, final int hauteurEcran, final int[][] carte)
	{
		super();

		this.layeredPane = aLayeredPane;

		this.vuesSingesErratiques = new Hashtable<Integer, VueSingeErratique>();

		this.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));

		this.vueCarte = new VueCarte(largeurEcran, hauteurEcran, carte);

		this.layeredPane.add(this.vueCarte, JLayeredPane.DEFAULT_LAYER);
	}

	/**
	 * Ajout de la vue d'un pirate identifie aux coordonnees fournies sur la vue
	 * de la carte.
	 * 
	 * @param id
	 *            identifiant du pirate.
	 * @param x
	 *            la coordonnee en abscisse du pirate (en nombres de cases).
	 * @param y
	 *            la coordonnee en ordonnee du pirate (en nombres de cases).
	 * @param avatar
	 *            le lien vers l'avatar du pirate.
	 */
	public void ajoutPirate(final int id, final int x, final int y, final String avatar)
	{
		this.vuePirate = new VuePirate(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille(), x, y, avatar);
		this.layeredPane.add(this.vuePirate, JLayeredPane.PALETTE_LAYER);
	}

	/**
	 * Deplacement de la vue d'un pirate identifie a ses nouvelles coordonnees.
	 * 
	 * @param id
	 *            identifiant du pirate.
	 * @param x
	 *            la nouvelle coordonnee en abscisse du pirate (en nombres de
	 *            cases).
	 * @param y
	 *            la nouvelle coordonnee en ordonnee du pirate (en nombres de
	 *            cases).
	 */
	public void deplacementPirate(final int id, final int x, final int y)
	{
		this.vuePirate.setPosition(x, y);
		repaint();
	}

	/**
	 * L'avatar du pirate identifie devient un avatar de mort.
	 * 
	 * @param id
	 *            l'identifiant du pirate.
	 */
	public void mortPirate(final int id)
	{
		this.vuePirate.mortPirate();
		repaint();
	}

	/**
	 * Changement de carte a afficher.
	 * 
	 * @param carte
	 *            nouvelle carte.
	 */
	public void changementCarte(final int[][] carte)
	{
		this.vueCarte.setVueCarte(carte);
		repaint();
	}

	/**
	 * Ajout de la vue d'un singe erratique identifie aux coordonnees fournies
	 * sur la vue de la carte.
	 * 
	 * @param id
	 *            identifiant du personnage.
	 * @param x
	 *            la coordonnee en abscisse du singe erratique (en nombres de
	 *            cases).
	 * @param y
	 *            la coordonnee en ordonnee du singe erratique (en nombres de
	 *            cases).
	 */
	public void ajoutSingeErratique(final int id, final int x, final int y)
	{
		final VueSingeErratique vse = new VueSingeErratique(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(),
				this.vueCarte.getYGrille(), x, y);
		this.vuesSingesErratiques.put(id, vse);
		this.layeredPane.add(vse, JLayeredPane.PALETTE_LAYER);
	}

	/**
	 * Deplacement de la vue d'un singe erratique identifie a ses nouvelles
	 * coordonnees.
	 * 
	 * @param id
	 *            identifiant du singe erratique.
	 * @param x
	 *            la nouvelle coordonnee en abscisse du singe erratique (en
	 *            nombres de cases).
	 * @param y
	 *            la nouvelle coordonnee en ordonnee du singe erratique (en
	 *            nombres de cases).
	 */
	public void deplacementSingeErratique(final int id, final int x, final int y)
	{
		final VueSingeErratique vse = this.vuesSingesErratiques.get(id);
		vse.setPosition(x, y);
		repaint();
	}

	/**
	 * Ajout de la vue d'un tresor aux coordonnees fournies sur la vue de la
	 * carte.
	 * 
	 * @param x
	 *            la coordonnee en abscisse du tresor (en nombres de cases).
	 * @param y
	 *            la coordonnee en ordonnee du tresor (en nombres de cases).
	 */
	public void ajoutTresor(final int x, final int y)
	{
		this.vueTresor = new VueTresor(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille(), x, y);
		this.layeredPane.add(this.vueTresor, JLayeredPane.PALETTE_LAYER);
	}

	/**
	 * Suppression de la vue du tresor.
	 */
	public void suppressionTresor()
	{
		this.layeredPane.remove(this.vueTresor);
	}

	/**
	 * Adapte la taille des vues des personnages à la taille de la vue de la
	 * carte.
	 */
	public void adapterDimensionsPersonnages()
	{
		this.vuePirate.setDimensions(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille());

		final Collection<VueSingeErratique> ensVuesSingesErratiques = this.vuesSingesErratiques.values();
		for (final VueSingeErratique vse : ensVuesSingesErratiques) {
			vse.setDimensions(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille());
		}

		this.vueTresor.setDimensions(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille());

		repaint();
	}

}
