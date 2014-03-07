package tinymonkeys.vue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import tinymonkeys.controleur.Controleur;
import tinymonkeys.modele.BandeDeSingesErratiquesEcouteur;
import tinymonkeys.modele.IleEcouteur;
import tinymonkeys.modele.PirateEcouteur;

/**
 * Classe Fenetre de TinyMonkeys.
 * 
 * @version 1.0
 * @author Cl�ment Guet
 * 
 */
public class Fenetre extends JFrame implements PirateEcouteur, BandeDeSingesErratiquesEcouteur, IleEcouteur, KeyListener
{

	/**
	 * UID auto-généré.
	 */
	private static final long serialVersionUID = 6776551061617508481L;

	/**
	 * Largeur de l'ecran en nombre de pixels.
	 */
	private final int largeurEcran;

	/**
	 * Hauteur de l'ecran en nombre de pixels.
	 */
	private final int hauteurEcran;

	/**
	 * Controleur permettant de transmettre les demandes clavier au serveur
	 * MonkeyIsland.
	 */
	final transient private Controleur controleur;

	/**
	 * Panneau de gestion.
	 */
	private final JLayeredPane layeredPane;

	/**
	 * La vue de la carte.
	 */
	private VueCarte vueCarte;

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
	 * Disponibilite du clavier.
	 */
	private boolean clavierDisponible;

	/**
	 * 
	 * Constructeur de la fenetre.
	 * 
	 * @param titre
	 *            libellé de la fenetre affiche dans la barre de titre.
	 * @param controleur
	 *            le controleur de la vue.
	 */
	public Fenetre(final String titre, final Controleur controleur)
	{
		// Construction de la fenetre.
		super();

		// Sortie de l'application en cas de fermeture de la fenetre.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Definition du titre de la fenetre.
		setTitle(titre);

		// Extrait des dimensions de l'écran.
		final Toolkit kit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = kit.getScreenSize();
		this.hauteurEcran = screenSize.height;
		this.largeurEcran = screenSize.width;

		// Dimensionnement de la fenetre.
		setSize(this.largeurEcran, this.hauteurEcran);

		// Récupération du conteneur.
		this.layeredPane = this.getLayeredPane();

		// Lien vers le controleur pour tout ce qui est des commandes clavier.
		this.controleur = controleur;

		// Mise en ecoute du clavier.
		this.clavierDisponible = true;
		this.addKeyListener(this);

		this.vuesSingesErratiques = new Hashtable<Integer, VueSingeErratique>();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void creationCarte(final int[][] carte)
	{
		this.vueCarte = new VueCarte(this.largeurEcran, this.hauteurEcran, carte);
		this.layeredPane.add(this.vueCarte, JLayeredPane.DEFAULT_LAYER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changementCarte(final int[][] carte)
	{
		this.vueCarte.setVueCarte(carte);

		this.vuePirate.setDimensions(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille());

		final Collection<VueSingeErratique> ensVuesSingesErratiques = this.vuesSingesErratiques.values();
		for (final VueSingeErratique vse : ensVuesSingesErratiques) {
			vse.setDimensions(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille());
		}

		this.vueTresor.setDimensions(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille());

		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void ajoutPirate(final int id, final int x, final int y, final String avatar)
	{
		this.vuePirate = new VuePirate(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille(), x, y, avatar);
		this.layeredPane.add(this.vuePirate, JLayeredPane.PALETTE_LAYER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deplacementPirate(final int id, final int x, final int y)
	{
		this.vuePirate.setPosition(x, y);
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mortPirate(final int id)
	{
		this.vuePirate.mortPirate();
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void creationSingeErratique(final int id, final int x, final int y)
	{
		final VueSingeErratique vse = new VueSingeErratique(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(),
				this.vueCarte.getYGrille(), x, y);
		this.vuesSingesErratiques.put(id, vse);
		this.layeredPane.add(vse, JLayeredPane.PALETTE_LAYER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deplacementSingeErratique(final int id, final int x, final int y)
	{
		final VueSingeErratique vse = this.vuesSingesErratiques.get(id);
		vse.setPosition(x, y);
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void creationTresor(final int x, final int y)
	{
		this.vueTresor = new VueTresor(this.vueCarte.getTailleCase(), this.vueCarte.getXGrille(), this.vueCarte.getYGrille(), x, y);
		this.layeredPane.add(this.vueTresor, JLayeredPane.DEFAULT_LAYER);
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void suppressionTresor()
	{
		this.layeredPane.remove(this.vueTresor);
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void liberationClavier()
	{
		this.clavierDisponible = true;
	}

	/**
	 * Reconnaissance de l'appui sur les touches directionnelles afin de de
	 * commander le pirate.
	 * 
	 * @param keyEvent
	 *            l'evenement sur le clavier.
	 */
	@Override
	public void keyPressed(final KeyEvent keyEvent)
	{
		if (this.clavierDisponible) {
			// blocage du clavier afin de le rendre inactif.
			this.clavierDisponible = false;

			final int code = keyEvent.getKeyCode();

			if (code >= KeyEvent.VK_LEFT && code <= KeyEvent.VK_DOWN) {
				int dx = 0;
				int dy = 0;

				switch (code) {
				case KeyEvent.VK_LEFT: // 37
					dx = -1;
					dy = 0;
					break;
				case KeyEvent.VK_UP: // 38
					dx = 0;
					dy = -1;
					break;
				case KeyEvent.VK_RIGHT: // 39
					dx = 1;
					dy = 0;
					break;
				case KeyEvent.VK_DOWN: // 40
					dx = 0;
					dy = 1;
					break;
				default:
					break;
				}

				this.controleur.demandeDeplacementPirate(dx, dy);
			}

		}
	}

	@Override
	public void keyReleased(final KeyEvent arg0)
	{

	}

	@Override
	public void keyTyped(final KeyEvent keyEvent)
	{

	}

}
