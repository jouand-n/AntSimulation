package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.TERMITE_ATTACK_DURATION;
import static ch.epfl.moocprog.config.Config.TERMITE_HP;
import static ch.epfl.moocprog.config.Config.TERMITE_LIFESPAN;
import static ch.epfl.moocprog.config.Config.TERMITE_MAX_STRENGTH;
import static ch.epfl.moocprog.config.Config.TERMITE_MIN_STRENGTH;
import static ch.epfl.moocprog.config.Config.TERMITE_SPEED;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;

/**
 * Classe finale représentant le concept d'une termite. Cette classe hérite de
 * la classe {@link Animal}.
 */
public final class Termite extends Animal {

	/**
	 * Constructeur non vide initialisant une instance de {@link Termite} à partir
	 * d'une {@link ToricPosition}. Ce constructeur fait appel en interne au
	 * constructeur {@link Animal#Animal(ToricPosition, int, Time)}, qui va utiliser
	 * la {@link ToricPosition} injectée en argument. Il va aussi prendre les
	 * valeurs initiales constantes {@code TERMITE_HP} et {@code TERMITE_LIFESPAN}
	 * obtenues à partir du fichier de configuration initial pour ses attributs
	 * {@link Animal#hitPoints} et {@link Animal#lifespan.
	 * 
	 * @param position: {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link Termite} dans
	 *                  l'environnement
	 */
	public Termite (ToricPosition position) {
		super(position, Context.getConfig().getInt(TERMITE_HP), Context.getConfig().getTime(TERMITE_LIFESPAN));
	}
	
	/**
	 * Méthode redéfinie permettant de gérer la représentation graphique par JavaFX
	 * d'une {@link Termite} en garantissant que le {@link RenderingMedia} est bien
	 * visité par l'instance {@code this} de {@link Termite} grâce à l'appel de la
	 * méthode {@link visit()}
	 * 
	 * @param visitor : {@link AnimalVisitor} permet de garantir le type dynamique
	 *                de l'instance de {@link Animal} pour {@code s}
	 * @param s       : {@link RenderingMedia} le support de rendu
	 */
	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this,  s);
	}

	/**
	 * Méthode redéfinie renvoyant la vitesse de l'instance {code this} de
	 * {@link Termite},obtenue à partir du fichier de configuration initial au
	 * niveau de la valeur constante {@code TERMITE_SPEED}.
	 */
	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(TERMITE_SPEED);
	}

	/**
	 * Méthode du "double-dispatch" appelée après le déplacement de l'instance
	 * {@code this} de {@link Termite}. Appelle la méthode appropriée dans
	 * l'environnement {@code env} en fonction du type de l'animal.
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link AnimalEnvironmentView}
	 */
	@Override
	protected final void afterMoveDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectAfterMoveDispatch(this, dt);		
	}
	
	/**
	 * Méthode appelée après le déplacement de l'instance {@code this} de
	 * {@link Termite}. Aucune action particulière n'est définie pour une termite
	 * après son déplacement d'ou l'absence de code.
	 * 
	 * @param env : instance de {@link TermiteEnvironmentView} en charge d'appeler
	 *            et d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code env} de {@link TermiteEnvironmentView}
	 */
	public final void afterMoveTermite(TermiteEnvironmentView env, Time dt) {
	}
	
	/**
	 * Calcule les probabilités de rotation pour l'instance {@code this} de
	 * {@link Termite} en utilisant le modèle de probabilité de rotation.
	 * 
	 * @param env : instance de {@link TermiteEnvironmentView} en charge d'appeler
	 *            et d'être exploitée par {@code this}
	 * 
	 * @return les probabilités de rotation par défaut, de type
	 *         {@link RotationProbability}.
	 */
	protected RotationProbability computeRotationProbs (TermiteEnvironmentView env) {
		return computeDefaultRotationProbs();
	}
	
	/**
	 * Calcule les probabilités de rotation en utilisant le dispatch sur
	 * l'environnement pour obtenir les résultats appropriés pour le type spécifique
	 * d'animal (ici, l'instance {@code this} de {@link Termite})
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @return les probabilités de rotation calculées, de type
	 *         {@link RotationProbability}.
	 */
	@Override
	protected RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env) {
		return env.selectComputeRotationProbsDispatch(this);
	}

	/**
	 * Méthode rédéfinie pour obtenir la force minimale d'attaque de l'instance
	 * {@code this} de {@link Termite} définie d'après la valeur constante
	 * {@code TERMITE_MIN_STRENGTH} du fichier de configuration initial
	 * 
	 * @return la force minimale d'attaque de l'instance {@code this} de
	 *         {@link Termite}
	 */
	@Override
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(TERMITE_MIN_STRENGTH);
	}
	
	/**
	 * Méthode rédéfinie pour obtenir la force maximale d'attaque de l'instance
	 * {@code this} de {@link Termite} définie d'après la valeur constante
	 * {@code TERMITE_MAX_STRENGTH} du fichier de configuration initial
	 * 
	 * @return la force maximale d'attaque de l'instance {@code this} de
	 *         {@link Termite}
	 */
	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(TERMITE_MAX_STRENGTH);
	}
	
	/**
	 * Méthode rédéfinie pour obtenir la durée maximale d'attaque de l'instance
	 * {@code this} de {@link Termite} définie d'après la valeur constante
	 * {@code TERMITE_ATTACK_DURATION} du fichier de configuration initial
	 * 
	 * @return la durée maximale d'attaque de l'instance {@code this} de
	 *         {@link Termite}
	 */
	@Override
	public Time getMaxAttackDuration(){
		return Context.getConfig().getTime(TERMITE_ATTACK_DURATION);
	}
	
	/**
	 * Méthode finale pour définir qui sont les ennemis vivants de l'instance
	 * {@code this} de {@link Termite} vivante, par l'appel à
	 * {@link Termite#isEnemyDispatch(Ant)} et
	 * {@link Termite#isEnemyDispatch(Termite))}
	 * 
	 * @param other : autre instance de {@link Animal} dont on doit vérifier si elle
	 *              est ennemie de l'instance {@code this}
	 * 
	 * @return {@code true} si {@code other} est ennemie de {@code this}, ou
	 *         {@code false} dans le cas inverse
	 * 
	 * @throws IllegalArgumentException si {@code other} est {@code null}
	 */
	@Override
	public final boolean isEnemy(Animal otherAnimal) throws IllegalArgumentException {
		Utils.requireNonNull(otherAnimal);
		return !this.isDead() && !otherAnimal.isDead() && otherAnimal.isEnemyDispatch(this);
	}
	
	/**
	 * Méthode redéfinie et surchargée retournant toujours {@code true} car
	 * l'instance {@code this} de {@link Termite} est systématiquement l'ennemie
	 * d'une instance {@code anAnt} de {@link Ant}
	 */
	@Override
	protected boolean isEnemyDispatch(Ant anAnt) {
		return true;
	}
	
	/**
	 * Méthode redéfinie et surchargée retournant toujours {@code false} car
	 * l'instance {@code this} de {@link Termite} n'est pas l'ennemie d'une autre
	 * instance {@code otherTermite} de {@link Termite}
	 */
	@Override
	protected boolean isEnemyDispatch(Termite otherTermite) {
		return false;
	}
	
	/**
	 * Méthode permettant à l'instance {@code this} de {@link Termite} de se battre
	 * avec des ennemis via l'appel à
	 * {@link Termite#fight(AnimalEnvironmentView, Time)} si {@code this} possède un
	 * {@link Termite#getState()} en mode "ATTACK". Sinon, l'instance {@code this}
	 * fera appel à {@link Termite#move(AnimalEnvironmentView, Time)}, puis se
	 * battra si possible dans les conditions décrites précédemment.
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AnimalEnvironmentView}
	 */
	public void seekForEnemies (AnimalEnvironmentView env, Time dt) {
		if(!(getState().equals(State.ATTACK))){
			move(env, dt);
		}
		fight(env, dt);
	}
	
	/**
	 * Méthode permettant d'assurer un "double dispatch" - elle permet à
	 * l'environnement lors de sa mise à jour d'appeler spécifiquement des méthodes
	 * liées à l'instance {@code this} de {@link Termite}
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AnimalEnvironmentView}
	 */
	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// A ce moment là, on sait que l'on à affaire à une Termite.
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
		
		env.selectSpecificBehaviorDispatch(this /* ici le type de this est bien Termite ! */, dt);
	}
}
