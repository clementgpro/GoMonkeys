package tinymonkeys.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tinymonkeys.controleur.Controleur;
import tinymonkeys.modele.BandeDeSingesErratiques;
import tinymonkeys.modele.Ile;
import tinymonkeys.modele.Pirate;
import tinymonkeys.modele.SingeErratique;

/**
 * Classe de test.
 * 
 * @author Clement Guet
 * @version 1.1
 */
public class SingeErratiqueTest
{
	/** Indice dans le tableau pour gauche. */
	private final static int GAUCHE = 0;

	/** Indice dans le tableau pour haut. */
	private final static int HAUT = 1;

	/** Indice dans le tableau pour droite. */
	private final static int DROITE = 2;

	/** Indice dans le tableau pour bas. */
	private final static int BAS = 3;

	/** Constante pour le delta autorisé. */
	private final static double DELTA = 0.03;

	/** Constante N pour l'equiprobabilite. */
	private final static double N = 10000;

	/** Constante de longueur de carte. */
	private final static int LONGUEUR_CARTE = 20;

	/** Constante de largeur de carte. */
	private final static int LARGEUR_CARTE = 20;

	/** Mock de l'île. */
	private Ile ileMock;

	/** Mock de la bande de singes. */
	private BandeDeSingesErratiques bandeDeSingesErratiquesMock;

	/** Singe erratique. */
	private SingeErratique singeErratiqueIleMock;

	/** Singe erratique. */
	private SingeErratique singeErratiqueIle;

	/** Autre Singe erratique. */
	private SingeErratique singeErratiqueAutre;

	/** Mock d'un singe erratique. */
	private SingeErratique singeErratiqueMock;

	/** Pirate. */
	private Pirate pirate;

	/** Pirate. */
	private Pirate pirateMock;

	/**
	 * Lance avant chaque test.
	 * 
	 * @throws Exception
	 *             exception technique
	 */
	@Before
	public void setUp() throws Exception
	{
		this.ileMock = EasyMock.createMock(Ile.class);
		final Ile ile = new Ile();
		ile.creationCarte(Controleur.exempleCarte());
		this.bandeDeSingesErratiquesMock = EasyMock.createMock(BandeDeSingesErratiques.class);
		this.pirate = new Pirate(ile);
		this.pirateMock = EasyMock.createMock(Pirate.class);
		this.singeErratiqueIleMock = new SingeErratique(2, 2, this.ileMock);
		this.singeErratiqueIle = new SingeErratique(2, 2, ile);
		this.singeErratiqueAutre = new SingeErratique(0, 0, ile);
		this.singeErratiqueMock = EasyMock.createMock(SingeErratique.class);
	}

	/**
	 * Test de verification de deplacement du singe.
	 * 
	 * @throws Exception
	 *             expection technique de l'acces a la methode privee
	 */
	@Test
	public void testVerifierDeplacementSinge() throws Exception
	{
		final List<SingeErratique> erratiques = new ArrayList<SingeErratique>();
		this.preparerAutreSingeHaut();
		erratiques.add(this.singeErratiqueMock);
		EasyMock.expect(this.bandeDeSingesErratiquesMock.getSingesErratiques()).andStubReturn(erratiques);
		EasyMock.replay(this.bandeDeSingesErratiquesMock);

		this.preparerPirateMock(2, 3);
		this.preparerIleMock();

		// Accessibilité de la méthode privée
		final Method method = SingeErratique.class.getDeclaredMethod("verifierDeplacementSinge", int.class, int.class);
		method.setAccessible(true);

		// * Déplacement autorisé
		// Sur la terre
		Assert.assertFalse(((boolean) method.invoke(this.singeErratiqueIleMock, 2, 1)));

		// Pirate
		Assert.assertFalse(((boolean) method.invoke(this.singeErratiqueIleMock, 2, 3)));

		// * Déplacement interdit
		// Autre singe
		Assert.assertTrue(((boolean) method.invoke(this.singeErratiqueIleMock, 1, 2)));

		// Dans l'eau
		Assert.assertTrue(((boolean) method.invoke(this.singeErratiqueIleMock, 2, 0)));
	}

	/**
	 * Test de deplacement bouchonne.
	 */
	@Test
	public void testDeplacerSinge()
	{
		this.preparerBandeDeSingesErratiquesMock(false);
		this.preparerPirateMock(0, 0);
		this.preparerIleMock();
		final int precedentX = this.singeErratiqueIleMock.getX();
		final int precedentY = this.singeErratiqueIleMock.getY();
		this.singeErratiqueIleMock.deplacerSinge();
		Assert.assertTrue(precedentX != this.singeErratiqueIleMock.getX() || precedentY != this.singeErratiqueIleMock.getY());
	}

	/**
	 * Test d'equiprobabilite bouchonne.
	 */
	@Test
	public void testEquiprobabiliteQuatreDirectionBouchonne()
	{
		this.preparerBandeDeSingesErratiquesMock(false);
		this.preparerPirateMock(0, 0);
		this.preparerIleMock();
		final double[] tabDirection = this.construireEquiprobabilite(true, 2, 2);
		final Double expected = 1 / Double.valueOf(4);
		Assert.assertEquals(expected, tabDirection[GAUCHE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[HAUT] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[DROITE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[BAS] / N, DELTA);
	}

	/**
	 * Test d'equiprobabilite.
	 */
	@Test
	public void testEquiprobabiliteQuatreDirection()
	{
		this.preparerBandeDeSingesErratiques(false);
		this.preparerPirate(0, 0);
		final double[] tabDirection = this.construireEquiprobabilite(false, 2, 2);
		final Double expected = 1 / Double.valueOf(4);
		Assert.assertEquals(expected, tabDirection[GAUCHE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[HAUT] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[DROITE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[BAS] / N, DELTA);
	}

	/**
	 * Test d'equiprobabilite bouchonne.
	 */
	@Test
	public void testEquiprobabiliteTroisDirectionBouchonne()
	{
		this.preparerBandeDeSingesErratiquesMock(false);
		this.preparerPirateMock(0, 0);
		this.preparerIleMock();
		final double[] tabDirection = this.construireEquiprobabilite(true, 2, 1);
		final Double expected = 1 / Double.valueOf(3);
		Assert.assertEquals(0.0, tabDirection[GAUCHE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[HAUT] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[DROITE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[BAS] / N, DELTA);
	}

	/**
	 * Test d'equiprobabilite bouchonne.
	 */
	@Test
	public void testEquiprobabiliteTroisDirection()
	{
		this.preparerBandeDeSingesErratiques(false);
		this.preparerPirate(0, 0);
		final double[] tabDirection = this.construireEquiprobabilite(false, 2, 1);
		final Double expected = 1 / Double.valueOf(3);
		Assert.assertEquals(0.0, tabDirection[GAUCHE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[HAUT] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[DROITE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[BAS] / N, DELTA);
	}

	/**
	 * Test d'equiprobabilite bouchonne.
	 */
	@Test
	public void testEquiprobabiliteDeuxDirectionBouchonne()
	{
		this.preparerBandeDeSingesErratiquesMock(false);
		this.preparerPirateMock(0, 0);
		this.preparerIleMock();
		final double[] tabDirection = this.construireEquiprobabilite(true, 1, 1);
		final Double expected = 1 / Double.valueOf(2);
		Assert.assertEquals(0.0, tabDirection[GAUCHE] / N, DELTA);
		Assert.assertEquals(0.0, tabDirection[HAUT] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[DROITE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[BAS] / N, DELTA);
	}

	/**
	 * Test d'equiprobabilite bouchonne.
	 */
	@Test
	public void testEquiprobabiliteDeuxDirection()
	{
		this.preparerBandeDeSingesErratiques(false);
		this.preparerPirate(0, 0);
		final double[] tabDirection = this.construireEquiprobabilite(false, 1, 1);
		final Double expected = 1 / Double.valueOf(2);
		Assert.assertEquals(0.0, tabDirection[GAUCHE] / N, DELTA);
		Assert.assertEquals(0.0, tabDirection[HAUT] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[DROITE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[BAS] / N, DELTA);
	}

	/**
	 * Test d'equiprobabilite bouchonne.
	 */
	@Test
	public void testEquiprobabiliteTroisDirectionSingeBouchonne()
	{
		// Deplacer le singe en haut
		this.preparerAutreSingeHaut();
		this.preparerBandeDeSingesErratiquesMock(true);
		this.preparerPirateMock(0, 0);
		this.preparerIleMock();
		final double[] tabDirection = this.construireEquiprobabilite(true, 2, 2);
		final Double expected = 1 / Double.valueOf(3);
		Assert.assertEquals(expected, tabDirection[GAUCHE] / N, DELTA);
		// Le singe est en haut
		Assert.assertEquals(0.0, tabDirection[HAUT] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[DROITE] / N, DELTA);
		Assert.assertEquals(expected, tabDirection[BAS] / N, DELTA);
	}

	/**
	 * Prepare un autre singe a la position (1, 2).
	 */
	private void preparerAutreSingeHaut()
	{
		EasyMock.expect(this.singeErratiqueMock.coordonneesEgales(2, 1)).andStubReturn(false);
		EasyMock.expect(this.singeErratiqueMock.coordonneesEgales(1, 2)).andStubReturn(true);
		EasyMock.expect(this.singeErratiqueMock.coordonneesEgales(2, 3)).andStubReturn(false);
		EasyMock.expect(this.singeErratiqueMock.coordonneesEgales(3, 2)).andStubReturn(false);
		EasyMock.expect(this.singeErratiqueMock.coordonneesEgales(3, 1)).andStubReturn(false);
		EasyMock.replay(this.singeErratiqueMock);
	}

	/**
	 * Calcule le nombre d'iteration pour chaque direction.
	 * 
	 * @param mock
	 *            indique si mock
	 * @param positionX
	 *            indique l'abscicce que doit reprendre le singe avant une
	 *            nouvelle iteration
	 * @param positionY
	 *            indique l'ordonnee que doit reprendre le singe avant une
	 *            nouvelle iteration
	 * @return le tableau de resultat des iterations
	 */
	private final double[] construireEquiprobabilite(final boolean mock, final int positionX, final int positionY)
	{
		final SingeErratique singe = mock ? this.singeErratiqueIleMock : this.singeErratiqueIle;
		final double[] tabNombreDirection = new double[4];

		for (int i = 0; i < N; i++) {
			singe.setPosition(positionX, positionY);
			final int previousX = singe.getX();
			final int previousY = singe.getY();
			singe.deplacerSinge();

			if (previousX == singe.getX() && previousY - 1 == singe.getY()) {
				// Gauche
				tabNombreDirection[GAUCHE]++;
			} else if (previousX - 1 == singe.getX() && previousY == singe.getY()) {
				// Haut
				tabNombreDirection[HAUT]++;
			} else if (previousX == singe.getX() && previousY + 1 == singe.getY()) {
				// Droite
				tabNombreDirection[DROITE]++;
			} else if (previousX + 1 == singe.getX() && previousY == singe.getY()) {
				// Bas
				tabNombreDirection[BAS]++;
			}
		}
		return tabNombreDirection;
	}

	/**
	 * Prepapre l'ile.
	 */
	private void preparerIleMock()
	{
		EasyMock.expect(this.ileMock.valeurCarte(0, 1)).andStubReturn(0);
		EasyMock.expect(this.ileMock.valeurCarte(1, 0)).andStubReturn(0);
		EasyMock.expect(this.ileMock.valeurCarte(1, 1)).andStubReturn(1);
		EasyMock.expect(this.ileMock.valeurCarte(1, 2)).andStubReturn(1);
		EasyMock.expect(this.ileMock.valeurCarte(2, 0)).andStubReturn(0);
		EasyMock.expect(this.ileMock.valeurCarte(2, 1)).andStubReturn(1);
		EasyMock.expect(this.ileMock.valeurCarte(2, 2)).andStubReturn(1);
		EasyMock.expect(this.ileMock.valeurCarte(2, 3)).andStubReturn(1);
		EasyMock.expect(this.ileMock.valeurCarte(3, 1)).andStubReturn(1);
		EasyMock.expect(this.ileMock.valeurCarte(3, 2)).andStubReturn(1);
		EasyMock.expect(this.ileMock.valeurCarte(0, 0)).andStubReturn(1);
		EasyMock.expect(this.ileMock.getLongueurCarte()).andStubReturn(LONGUEUR_CARTE);
		EasyMock.expect(this.ileMock.getLargeurCarte()).andStubReturn(LARGEUR_CARTE);
		EasyMock.expect(this.ileMock.getSingesErratiques()).andStubReturn(this.bandeDeSingesErratiquesMock);
		EasyMock.expect(this.ileMock.getPirate()).andStubReturn(this.pirateMock);
		EasyMock.replay(this.ileMock);
	}

	/**
	 * Prepare le mock du pirate (se trouve a la position x,y).
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	private void preparerPirateMock(final int x, final int y)
	{
		EasyMock.expect(this.pirateMock.getX()).andStubReturn(x);
		EasyMock.expect(this.pirateMock.getY()).andStubReturn(y);
		EasyMock.replay(this.pirateMock);
	}

	/**
	 * Prepare le pirate (se trouve a la position x,y).
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	private void preparerPirate(final int x, final int y)
	{
		this.pirate.setX(x);
		this.pirate.setY(y);
	}

	/**
	 * Prepare le mock de bande de singes erratiques.
	 * 
	 * @param singe
	 *            indique si on doit ajouter un singe
	 */
	private void preparerBandeDeSingesErratiquesMock(final boolean singe)
	{
		final List<SingeErratique> erratiques = new ArrayList<SingeErratique>();
		if (singe) {
			erratiques.add(this.singeErratiqueMock);
		}
		EasyMock.expect(this.bandeDeSingesErratiquesMock.getSingesErratiques()).andStubReturn(erratiques);
		EasyMock.replay(this.bandeDeSingesErratiquesMock);
	}

	/**
	 * Prepare la bande de singes erratiques.
	 * 
	 * @param singe
	 *            indique si on doit ajouter un singe
	 */
	private void preparerBandeDeSingesErratiques(final boolean singe)
	{
		final List<SingeErratique> erratiques = new ArrayList<SingeErratique>();
		if (singe) {
			erratiques.add(this.singeErratiqueAutre);
		}
		// this.bandeDeSingesErratiques.a
	}

}
