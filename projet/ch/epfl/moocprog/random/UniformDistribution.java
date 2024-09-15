package ch.epfl.moocprog.random;

import java.util.Random;

/**
 * Classe finale utilitaire fournissant une méthode statique
 * {@link UniformDistribution#getValue(double, double)} pour obtenir des valeurs
 * pseudo-aléatoires suivant une distribution uniforme
 */
public final class UniformDistribution {
    private static final Random random = new Random();

    // Empêche l'instanciation de cette classe
    private UniformDistribution() {}

    /**
	 * Retourne une valeur suivant une distribution uniforme comprise entre
	 * {@code min} et {@code max}
	 *
	 * @param min : double représentant la borne inférieure de la distribution
	 * @param max : double représentant la borne supérieure de la distribution
	 * @return une valeur double pseudo-aléatoire suivant une distribution uniforme
	 *         avec les paramètres donnés
	 */
    public static double getValue(double min, double max) {
        if(max < min) {
            throw new IllegalArgumentException();
        }
        return random.nextDouble()*(max - min) + min;
    }
}
