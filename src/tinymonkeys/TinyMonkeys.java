package tinymonkeys;

import tinymonkeys.controleur.Controleur;

/**
 * Classe principale de TinyMonkeys.
 * 
 * @version 1.0
 * @author Clément Guet
 * 
 */
final public class TinyMonkeys
{

	/**
	 * Constructeur privee de TinyMonkeys.
	 * 
	 * Ce constructeur privee assure la non-instanciation de TinyMonkeys dans un
	 * programme. (TinyMonkeys est la classe principale du programme
	 * TinyMonkeys)
	 */
	private TinyMonkeys()
	{
		// Constructeur privee pour assurer la non-instanciation de TinyMonkeys.
	}

	/**
	 * Main du programme.
	 * 
	 * @param args
	 *            arguments.
	 */
	public static void main(final String[] args)
	{
		final Controleur controleur = new Controleur();
		controleur.lanceEvolutionsPersonnages();
	}

}
