package ch.epfl.moocprog;

import java.util.Objects;

/**
 * Classe finale représentant un identifieur unique ("Unique identifier")
 */
public final class Uid {
	/**
	 * Compteur statique assurant l'unicité de chaque instance de {@link Uid}
	 */
	private static long counter = 0;
	/**
	 * Numéro d'identification constant
	 */
	private final long ID;

	/**
	 * Constructeur non vide privé - afin d'empêcher son instanciation directe.
	 * 
	 * @param id : long, représentant un numéro d'identification supposément unique
	 */
    private Uid(long id) {
		this.ID = id;
    }

    /**
	 * Méthode de type "factory" retournant une instance de {@link Uid} à partir du
	 * constructeur privé {@link Uid#Uid(long)}.
	 *
	 * @return un nouvelle instance de {@link Uid}
	 */
    public static Uid createUid() {
        return new Uid(counter++);
    }

	/**
	 * Méthode redéfinie testant si l'instance {@code this} de {@link Uid} est égale
	 * à une instance {@code o} d'un {@link Object}. L'égalité ne sera valable que
	 * si {@code o} est bien une instance de {@link Uid} et que les {@link Uid#ID}
	 * de {@code this} et {@code o} seront bien égaux.
	 * 
	 * @param o : {@link Object}, qui sera testé pour égalité avec l'instance
	 *          {@code this}
	 */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Uid) {
            Uid that = (Uid) o;
			return that.ID == this.ID;
        } else {
            return false;
        }
    }

	/**
	 * Méthode redéfinie retournant le code {@link Objects#hash(Object...)} unique
	 * de l'instance {@code this} de {@link Uid}
	 */
    @Override
    public int hashCode() {
		return Objects.hash(ID);
    }
}
