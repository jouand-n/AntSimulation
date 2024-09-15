package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Utils;

/**
 * Classe utilitaire utilisée par la classe {@link Animal}, servant à définir
 * deux tableaux de valeurs de type double: un premier tableau correspondant à
 * des angles de rotation de déplacement, un second tableau constitué des
 * probabilités possibles (inertielles par défaut ou sensorielles pour les
 * instances de {@link Ant}).
 */
public class RotationProbability {

	/**
	 * Liste de double comprenant constitué des angles de rotation (en radian)
	 * possibles durant le déplacement de l'animal
	 */
	double[] angles;

	/**
	 * Liste de double constituée des probabilités inertielles associées aux dits
	 * angles de {@link RotationProbability#angles}
	 */
	double[] probabilites;
	
	/**
	 * Constructeur non vide initialisant une instance de
	 * {@link RotationProbability} à partir de deux tableaux de doubles injectés en
	 * argument et qui seront clonés dans les attributs privés internes à la classe.
	 * 
	 * @param listAngles   : tableau de double, constitué des angles de rotation (en
	 *                     radian) possibles durant le déplacement de l'animal
	 * @param probabilites : tableau de double, constitué des probabilités
	 *                     inertielles associées aux dits angles. Il n'est pas
	 *                     obligatoire que la somme des probalités de ce tableau
	 *                     soit égale à 1.
	 * @throws IllegalArgumentException: exception lancée dans 4 situations: 1) le
	 *                                   tableau d'angles de rotation de déplacement
	 *                                   est null, 2) le tableau de probabilités
	 *                                   inertielles est null, 3) les tableaux
	 *                                   d'angle et de probabilité associée sont de
	 *                                   tailles inégales, ou bien 4) sont vides.
	 */
	public RotationProbability (double [] listAngles, double [] probabilites) throws IllegalArgumentException {
		
		Utils.requireNonNull(
				"Construction d'une instance de RotationProbability : le tableau d'angles de rotation de déplacement est nulle.",
				listAngles);
		Utils.requireNonNull(
				"Construction d'une instance de RotationProbability : le tableau de probabilités inertielles est nulle.",
				probabilites);
		Utils.require(
				"Construction d'une instance de RotationProbability : les tableaux d'angle et de probabilité associée sont de tailles inégales.",
				listAngles.length == probabilites.length);
		
		if (listAngles.length == 0) {
			throw new IllegalArgumentException(
					"Construction d'une instance de RotationProbability : les tableaus sont instanciés mais de taille zéro.");
        } else {
        	this.angles = listAngles.clone();
        	this.probabilites = probabilites.clone();
        }
	}

	/**
	 * Getter
	 * 
	 * @return une copie clonée du tableau de double constitué des angles de
	 *         rotation (en radian) possibles durant le déplacement de l'animal
	 */
	public double [] getAngles() {
		return angles.clone();
	}
	
	/**
	 * Getter
	 * 
	 * @return une copie clonée du tableau de double constitué des probabilités
	 *         inertielles associées aux dits angles.
	 */
	public double [] getProbabilities() {
		return probabilites.clone();
	}
}

