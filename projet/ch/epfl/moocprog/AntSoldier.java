package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_ATTACK_DURATION;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_HP;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_LIFESPAN;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_MAX_STRENGTH;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_MIN_STRENGTH;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_SPEED;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.utils.Time;

/**
 * Classe finale représentant le concept d'une fourmi soldat. Cette classe
 * hérite de la classe {@link Ant}.
 */
public final class AntSoldier extends Ant {

	/**
	 * Constructeur non vide initialisant une instance de {@link AntSoldier} à
	 * partir d'une {@link Animal#ToricPosition} et d'une {@link Ant#anthillID}. Ce
	 * constructeur fait appel en interne au constructeur
	 * {@link Ant#Ant(ToricPosition, int, Time, Uid)}. Il initialise respectivement
	 * les attributs {@link Animal#hitpoints} et {@link Animal#lifespan} avec les
	 * valeurs initiales constantes {@code ANT_SOLDIER_HP} et
	 * {@code ANT_SOLDIER_LIFESPAN} obtenues à partir du fichier de configuration
	 * initial.
	 * 
	 * @param position  : {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link AntSoldier} dans
	 *                  l'environnement
	 * @param anthillID : {@link Uid}, identifiant unique de l'instance {@code this}
	 *                  de {@link AntSoldier}
	 */
	public AntSoldier (ToricPosition position, Uid anthillID) {
		super(position, Context.getConfig().getInt(ANT_SOLDIER_HP), Context.getConfig().getTime(ANT_SOLDIER_LIFESPAN), anthillID);
	}
	
	/**
	 * Constructeur non vide initialisant une instance de {@link AntSoldier} à
	 * partir d'une {@link Animal#ToricPosition} et de valeurs initiales pour les
	 * attributs {@link Ant#anthillID} et de {@link Ant#probModel}. Ce constructeur
	 * fait appel en interne au constructeur
	 * {@link Ant#Ant(ToricPosition, int, Time, Uid, AntRotationProbabilityModel)}.
	 * Il initialise respectivement les attributs {@link Animal#hitpoints} et
	 * {@link Animal#lifespan} avec les valeurs initiales constantes
	 * {@code ANT_SOLDIER_HP} et {@code ANT_SOLDIER_LIFESPAN} obtenues à partir du
	 * fichier de configuration initial.
	 * 
	 * @param position  : {@link ToricPosition}, position torique initiale de
	 *                  l'instance {@code this} de {@link AntSoldier} dans
	 *                  l'environnement
	 * @param anthillID : {@link Uid}, identifiant unique de l'instance {@code this}
	 *                  de {@link AntSoldier}
	 * @param probModel : {@link AntRotationProbabilityModel} représentant la
	 *                  probabilité de rotation lors d'un déplacement de l'instance
	 *                  {@code this} de {@link AntSoldier}
	 */
	public AntSoldier (ToricPosition position, Uid anthillID, AntRotationProbabilityModel probModel) {
		super(position, Context.getConfig().getInt(ANT_SOLDIER_HP), Context.getConfig().getTime(ANT_SOLDIER_LIFESPAN), anthillID, probModel);
	}
	
	/**
	 * Méthode redéfinie permettant de gérer la représentation graphique par JavaFX
	 * d'une {@link AntSoldier} en garantissant que le {@link RenderingMedia} est
	 * bien visité par l'instance {@code this} de {@link AntSoldier} grâce à l'appel
	 * de la méthode {@link visit()}
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
	 * Méthode rédéfinie pour obtenir la force minimale d'attaque de l'instance
	 * {@code this} de {@link AntSoldier} définie d'après la valeur constante
	 * {@code ANT_SOLDIER_MIN_STRENGTH} du fichier de configuration initial
	 * 
	 * @return la force minimale d'attaque de l'instance {@code this} de
	 *         {@link AntSoldier}
	 */
	@Override
	public int getMinAttackStrength() {
		return Context.getConfig().getInt(ANT_SOLDIER_MIN_STRENGTH);
	}
	
	/**
	 * Méthode rédéfinie pour obtenir la force maximale d'attaque de l'instance
	 * {@code this} de {@link AntSoldier} définie d'après la valeur constante
	 * {@code ANT_SOLDIER_MAX_STRENGTH} du fichier de configuration initial
	 * 
	 * @return la force maximale d'attaque de l'instance {@code this} de
	 *         {@link AntSoldier}
	 */
	@Override
	public int getMaxAttackStrength() {
		return Context.getConfig().getInt(ANT_SOLDIER_MAX_STRENGTH);
	}
	
	/**
	 * Méthode rédéfinie pour obtenir la durée maximale d'attaque de l'instance
	 * {@code this} de {@link AntSoldier} définie d'après la valeur constante
	 * {@code ANT_SOLDIER_ATTACK_DURATION} du fichier de configuration initial
	 * 
	 * @return la durée maximale d'attaque de l'instance {@code this} de
	 *         {@link AntSoldier}
	 */
	@Override
	public Time getMaxAttackDuration(){
		return Context.getConfig().getTime(ANT_SOLDIER_ATTACK_DURATION);
	}
	
	/**
	 * Méthode redéfinie renvoyant la vitesse de l'instance {code this} de
	 * {@link AntSoldier},obtenue à partir du fichier de configuration initial au
	 * niveau de la valeur constante {@code ANT_SOLDIER_SPEED}
	 */
	@Override
	public double getSpeed() {
		return Context.getConfig().getDouble(ANT_SOLDIER_SPEED);
	}
	
	/**
	 * Méthode permettant à l'instance {@code this} de {@link AntSoldier} de se
	 * battre avec des ennemis via l'appel à
	 * {@link AntSoldier#fight(AnimalEnvironmentView, Time)} si {@code this} possède
	 * un {@link AntSoldier#getState()} en mode "ATTACK". Sinon, l'instance
	 * {@code this} fera appel à
	 * {@link AntSoldier#move(AnimalEnvironmentView, Time)}, puis se battra si
	 * possible dans les conditions décrites précédemment.
	 * 
	 * @param env : instance de {@link AntEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AntEnvironmentView}
	 */
	protected void seekForEnemies(AntEnvironmentView env, Time dt) {
		if(!(getState().equals(State.ATTACK))){
			move(env, dt);
		}
		fight(env, dt);
	}
	
	/**
	 * Méthode permettant d'assurer un "double dispatch" - elle permet à
	 * l'environnement lors de sa mise à jour d'appeler spécifiquement des méthodes
	 * liées à l'instance {@code this} de {@link AntSoldier}
	 * 
	 * @param env : instance de {@link AnimalEnvironmentView} en charge d'appeler et
	 *            d'être exploitée par {@code this}
	 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
	 *            l'instance {@code this} de {@link AnimalEnvironmentView}
	 */
	@Override
	protected void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		// A ce moment là, on sait que l'on à affaire à un AntSoldier.
		// Grâce à l'appel suivant, on informe AnimalEnvironmentView de notre type !
		
		env.selectSpecificBehaviorDispatch(this /* ici le type de this est bien AntSoldier ! */, dt);
	}
}
