package ch.epfl.moocprog;

import static ch.epfl.moocprog.config.Config.ANIMAL_SIGHT_DISTANCE;
import static ch.epfl.moocprog.config.Config.ANT_MAX_PERCEPTION_DISTANCE;
import static ch.epfl.moocprog.config.Config.ANT_SMELL_MAX_DISTANCE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.moocprog.app.Context;
import ch.epfl.moocprog.config.Config;
import ch.epfl.moocprog.gfx.EnvironmentGraphicRenderer;
import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;

/**
 * Classe finale décrivant le monde dans lequel les animaux ({@link Ant} et
 * {@link Termite} évoluent au cours du temps et interagissent avec d'autres
 * éléments inertes de la simulation ({@link Anthill} et {@link Food}). Elle
 * implémente différentes interfaces simulant les "vues" restreintes de
 * plusieurs sortes de classes d'objets déployés dans la simulation :
 * {@link AnimalEnvironmentView}, {@link AntEnvironmentView},
 * {@link AntWorkerEnvironmentView}, {@link AnthillEnvironmentView},
 * {@link FoodGeneratorEnvironmentView} et {@link TermiteEnvironmentView}
 */
public final class Environment implements AnimalEnvironmentView, AntEnvironmentView, AntWorkerEnvironmentView, AnthillEnvironmentView, FoodGeneratorEnvironmentView, TermiteEnvironmentView {

	/**
	 * Instance de {@link FoodGenerator} : générateur de nourriture plaçant des
	 * sources de nourriture aléatoirement dans l'environnement
	 */
		private FoodGenerator theFoodGenerator;
	/**
	 * Liste d'instances de {@link Food} : sources de nourriture présentes dans
	 * l'environnement.
	 */
		private List <Food> listFood;
		/**
		 * Liste d'instances de {@link Anthill} : fourmilières présentes dans
		 * l'environnement.
		 */
		private List<Anthill> listAnthill;
		/**
		 * Liste d'instances de {@link Animal} : animaux présents dans l'environnement.
		 */
		private List <Animal> listAnimal;
		/**
		 * Liste d'instances de {@link Pheromone} : phéromones déposées dans
		 * l'environnement.
		 */
		private List <Pheromone> listPheromone;
		
		/**
		 * Constructeur vide, initialisant une instance de {@link Environment}.
		 */
		public Environment() {
			theFoodGenerator = new FoodGenerator();

			// Utilisation de LinkedList pour optimiser les opérations d'ajout/suppression.
			listFood = new LinkedList <Food> ();
			listAnthill = new LinkedList<Anthill>();
			listAnimal = new LinkedList <Animal> ();
			listPheromone = new LinkedList<Pheromone>();
		}
		
		/**
		 * Méthode retournant la hauteur constante de l'instance {@code this} de
		 * {@link Environment} telle que définie dans le fichier de configuration de la
		 * simulation. Cette méthode est exploitée par JavaFX afin de connaître les
		 * dimensions de l'environnement.
		 * 
		 * @return la hauteur de l'environnement
		 */
		public int getHeight() {
			return Context.getConfig().getInt(Config.WORLD_HEIGHT);
		}

		/**
		 * Méthode retournant la largeur constante de l'instance {@code this} de
		 * {@link Environment} telle que définie dans le fichier de configuration de la
		 * simulation. Cette méthode est exploitée par JavaFX afin de connaître les
		 * dimensions de l'environnement.
		 * 
		 * @return la largeur de l'environnement
		 */
		public int getWidth() {
			return Context.getConfig().getInt(Config.WORLD_WIDTH);
		}

		/**
		 * Méthode retournant les quantités de nourriture associées à chaque instance de
		 * {@link Food} présente dans l'instance {@code this} de {@link Environment}
		 * 
		 * @return une liste de {@code double} représentant les quantités de nourriture
		 *         disponibles dans l'instance {@code this} de {@link Environment}
		 */
		public List<Double> getFoodQuantities(){
			List<Double> result = new ArrayList <Double> ();
			
			for(Food aFood : listFood) {
				result.add(aFood.getQuantity());
			}
			
			return result;
		}
		
		/**
		 * Méthode retournant les {@link ToricPosition} associées à chaque instance de
		 * {@link Animal} présente dans l'instance {@code this} de {@link Environment}
		 * 
		 * @return une liste de {@link ToricPosition} représentant les positions des
		 *         animaux présents dans l'environnement
		 */
		public List<ToricPosition> getAnimalsPosition() {
			List<ToricPosition> resultat = new ArrayList<ToricPosition>();

			for (Animal animal : listAnimal) {
				ToricPosition position = animal.getPosition();
				resultat.add(position);
			}

			return resultat;
		}

		/**
		 * Méthode retournant les quantités de phéromones présentes dans l'instance
		 * {@code this} de {@link Environment}
		 * 
		 * @return une liste de {@code double} représentant les quantités de phéromones
		 *         déposées par les fourmis dans l'environnement
		 */
		public List<Double> getPheromonesQuantities(){
			List<Double> result = new ArrayList <Double> ();
			
			for(Pheromone aPheromone : listPheromone) {
				result.add(aPheromone.getQuantity());
			}
			
			return result;
		}
		
		/**
		 * Méthode redéfinie servant à ajouter une instance non nulle de {@link Food}
		 * dans l'instance {@code this} d'{@link Environment}
		 *
		 * @param food : {@link Food} nourriture à injecter dans l'instance {@code this}
		 *             d'{@link Environment}
		 * @throws IllegalArgumentException : exception lancée en cas d'injection d'une
		 *                                  instance {@code null} de {@link Food}
		 */
		@Override
		public void addFood(Food food) throws IllegalArgumentException {
			Utils.require("addFood() avec un objet null en argument n'a pas de sens.", (food != null));
			listFood.add(food);
		}
		
		/**
		 * Méthode servant à ajouter une instance non nulle de {@link Anthill} dans
		 * l'instance {@code this} d'{@link Environment}
		 *
		 * @param anthill : {@link Anthill} fourmilière à injecter dans l'instance
		 *                {@code this} d'{@link Environment}
		 * @throws IllegalArgumentException : exception lancée en cas d'injection d'une
		 *                                  instance {@code null} de {@link Anthill}
		 */
		public void addAnthill(Anthill anthill) throws IllegalArgumentException {
			Utils.require("addAnthill() avec un objet null en argument n'a pas de sens.", (anthill != null));
			listAnthill.add(anthill);
		}
		
		/**
		 * Méthode servant à ajouter une instance non nulle de {@link Animal} dans
		 * l'instance {@code this} d'{@link Environment}
		 *
		 * @param animal : {@link Animal} animal à injecter dans l'instance {@code this}
		 *               d'{@link Environment}
		 * @throws IllegalArgumentException : exception lancée en cas d'injection d'une
		 *                                  instance {@code null} de {@link Animal}
		 */
		public void addAnimal(Animal animal) throws IllegalArgumentException {
			Utils.require("addAnimal () avec un objet null en argument n'a pas de sens.", (animal != null));
			listAnimal.add(animal);
		}
		
		/**
		 * Méthode servant à ajouter une instance non nulle de {@link Ant} dans
		 * l'instance {@code this} d'{@link Environment} grâce à l'appel de la méthode
		 * {@link addAnimal()}
		 *
		 * @param ant : {@link Ant} fourmi à injecter dans l'instance {@code this}
		 *            d'{@link Environment}
		 * @throws IllegalArgumentException : exception lancée en cas d'injection d'une
		 *                                  instance {@code null} de {@link Ant}
		 */
		@Override
		public void addAnt(Ant ant) throws IllegalArgumentException {
			this.addAnimal(ant);
		}

		/**
		 * Méthode servant à ajouter une instance non nulle de {@link Pheromone} dans
		 * l'instance {@code this} d'{@link Environment}
		 *
		 * @param pheromone : {@link Pheromone} pheromone à injecter dans l'instance
		 *                  {@code this} d'{@link Environment}
		 * @throws IllegalArgumentException : exception lancée en cas d'injection d'une
		 *                                  instance {@code null} de {@link Pheromone}
		 */
		@Override
		public void addPheromone(Pheromone pheromone) throws IllegalArgumentException {
			Utils.require("addPheromone() avec un objet null en argument n'a pas de sens.", (pheromone != null));
			listPheromone.add(pheromone);
		}

		/**
		 * Calcule l'angle le plus proche entre un angle donné et un angle cible, en
		 * prenant en compte la circularité des angles (dans le cadre de [0, 2π]).
		 * Retourne la différence d'angle minimale, qu'elle soit dans le sens horaire ou
		 * antihoraire.
		 * 
		 * @param angle  : L'angle initial en radians
		 * @param target : L'angle cible en radians
		 * 
		 * @return La différence minimale entre l'angle et la cible, en radians,
		 *         toujours positive.
		 */
		private static double closestAngleFrom (double angle, double target) {
			// Calcul de la différence entre les deux angles
			double diff = angle - target;
			diff = normalizedAngle(diff);
			
			// Calcul de la différence alternative en tenant compte de la circularité
			double autreDiff = (2 * Math.PI) - diff;
			
			// Retourne la plus petite des deux différences d'angles
			return (diff <= autreDiff) ? diff : autreDiff;
		}

		/**
		 * Méthode redéfinie qui retourne si une instance de {@link AntWorker} peut
		 * déposer la nourriture qu'elle transporte dans sa fourmilière d'appartenance
		 * {@link Anthill}, à condition que celle-ci soit perceptible. La notion de
		 * perception est gérée par l'appel interne de la méthode
		 * {@link Environment#seeByAnt(double)}.
		 * 
		 * @param antWorker : {@link AntWorker}, instance de fourmi ouvrière à tester
		 *                  sur sa capacité à déposer de la nourriture. Attention: la
		 *                  quantité de nourriture peut être de zéro.
		 * 
		 * @return {@code true} si la nourriture est déposée avec succès, {@code false}
		 *         sinon
		 * 
		 * @throws IllegalArgumentException : exception lancée en cas de 1)
		 *                                  {@code antWorker null}, ou 2)
		 *                                  {@link Environment#listAnthill} {@code null}
		 *                                  ou 3) de quantité de nourriture de
		 *                                  {@code antWorker} qui soit négative
		 * 
		 */
		@Override
		public boolean dropFood(AntWorker antWorker) throws IllegalArgumentException {
			Utils.requireNonNull(antWorker);
			Utils.require(antWorker.getFoodQuantity() >= 0.0);
			Utils.requireNonNull(listAnthill);
			
			if (listAnthill.isEmpty()) {
				return false;
			}
			
			// Vec2d positionAnt = antWorker.getPosition().toVec2d();
			ToricPosition positionAnt = antWorker.getPosition();
			
			Uid anthillID = antWorker.getAnthillId();

			Iterator<Anthill> ah = listAnthill.iterator();

			while (ah.hasNext()) {
				Anthill a = ah.next();
			
				if (a.getAnthillId().equals(anthillID)) {
					ToricPosition positionAnthill = a.getPosition();

					if (positionAnthill != null && seeByAnt(positionAnt.toricDistance(positionAnthill))) {
						a.dropFood(antWorker.getFoodQuantity());
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * Méthode redéfinie qui retourne l'instance de {@link Food} perceptible la plus
		 * proche par une instance de {@link AntWorker}. La notion de perception est
		 * gérée par l'appel interne de la méthode {@link Environment#seeByAnt(double)}.
		 * 
		 * @param antWorker : {@link AntWorker}, instance cherchant la source de
		 *                  {@link Food} perceptible la plus proche
		 * 
		 * @return la source de nourriture {@link Food} la plus proche, ou {@code null}
		 *         si aucune source de nourriture n'est perceptible par l'instance
		 *         {@code antWorker} de {@link AntWorker}
		 * 
		 * @throws IllegalArgumentException : exception lancée si {@code antWorker}
		 *                                  et/ou si {@link Environment#listFood} sont
		 *                                  null
		 * 
		 */
		@Override
		public Food getClosestFoodForAnt(AntWorker antWorker) throws IllegalArgumentException {
			Utils.requireNonNull(antWorker);
			Utils.requireNonNull(listFood);

			if (listFood.isEmpty()) {
				return null;
			}
			
			Food nearestFood = Utils.closestFromPoint(antWorker, listFood);
			Utils.requireNonNull(nearestFood);

			if (!seeByAnt(antWorker.getPosition().toricDistance(nearestFood.getPosition()))) {
				nearestFood = null;
			}

			return nearestFood;
		}
		
		/**
		 * Méthode retournant une liste de quantité de {@link Pheromone#getQuantity()}
		 * perceptibles par une instance de {@link Ant} de {@link ToricPosition}
		 * {@code position}. Ces quantités sont classées au plus proche de chacun des
		 * angles d'observation (ramenés à des secteurs d'angle) mais dans la limite de
		 * perception sensorielle de la fourmi (fournie par la valeur
		 * {@code ANT_SMELL_MAX_DISTANCE} du fichier de configuration initial).
		 * 
		 * @param position          : {@link ToricPosition}, position de l'instance de
		 *                          {@link Ant} testée
		 * @param directionAngleRad : {@code double}, angle de direction de l'instance
		 *                          de {@link Ant} testée
		 * @param angles            : liste de {@code double} représentant les secteurs
		 *                          d'angles d'observation autour de l'instance de
		 *                          {@link Ant} testée
		 * @return liste de {@code double} correspondant aux quantités de phéromone
		 *         perceptibles par la fourmi et classées par secteur d'angles
		 */
		@Override
		public double[] getPheromoneQuantitiesPerIntervalForAnt(ToricPosition position, double directionAngleRad, double[] angles) {
			Utils.requireNonNull(position);
			Utils.requireNonNull(angles);
			
			double smellMaxDistance = Context.getConfig().getDouble(ANT_SMELL_MAX_DISTANCE);
			double [] perceptiblePheromoneQuantities = new double[angles.length];

			for (Pheromone p : listPheromone) {
				// Déterminer l'ensemble des phéromones non négligeables existantes dans le
				// rayon olfactif de la fourmi et les ajouter dans un tableau
				if (!p.isNegligible() && position.toricDistance(p.getPosition()) <= smellMaxDistance) {
					// Recherche l'angle beta que fait cette pheromone p avec la fourmi
					Vec2d v = position.toricVector(p.getPosition());
					double beta = v.angle() - directionAngleRad;

					// Recherche l'angle parmi les angles d'observation de la liste double [] angles
					// le plus proche de beta
					double nearest = Double.MAX_VALUE;
					int indexAngle = 0;
					for (int k = 0; k < angles.length; ++k) {
						double distance = closestAngleFrom(angles[k], beta);
						if (distance < nearest) {
							nearest = distance;
							indexAngle = k;
						}
					}

					// Ajout de la quantité de phéromone dans le secteur d'angles le plus proche de
					// beta
					perceptiblePheromoneQuantities[indexAngle] += p.getQuantity();
				}
			}

			return perceptiblePheromoneQuantities;
		}
		
		/**
		 * Méthode renvoyant la liste des instances vivantes de {@link Animal}, qui
		 * soient perceptibles et ennemies de l'instance {@code from} de {@link Animal}.
		 * La notion de perception est traitée via l'appel à la valeur constante
		 * {@code ANIMAL_SIGHT_DISTANCE} du fichier de configuration initial. Le statu
		 * d'ennemi est évalué par les méthodes surchargées {@code isEnnemy()} provenant
		 * des classes {@link Ant} et {@link Termite}.
		 * 
		 * @param from : {@link Animal} dont on veut obtenir la liste des ennemis
		 *             vivants et perceptibles
		 * 
		 * @throws IllegalArgumentException : exception lancée si {@code from} est
		 *                                  {@code null}
		 */
		@Override
		public List<Animal> getVisibleEnemiesForAnimal(Animal from) throws IllegalArgumentException {
			Utils.requireNonNull(from);
			
			double visionAnimalDistance = Context.getConfig().getDouble(ANIMAL_SIGHT_DISTANCE);
			List<Animal> visibleAnimals = new ArrayList<Animal>();
			
			for (Animal animal : listAnimal) {
				if (animal != null && !(animal.equals(from))) {
					double interAnimalDistance = animal.getPosition().toricDistance(from.getPosition());
					
					if (interAnimalDistance <= visionAnimalDistance && animal.isEnemy(from)) {
						visibleAnimals.add(animal);
					}
				}
			}
			
			return visibleAnimals;
		}
		
		/**
		 * Méthode renvoyant {@code true} l'instance {@code from} de {@link Animal} est
		 * visible d'ennemis proches, et {@code false} dans le cas contraire. Elle
		 * s'appuie sur la méthode
		 * {@link Environment#getVisibleEnemiesForAnimal(Animal)}
		 * 
		 * @param from : {@link Animal} dont on veut savoir si il est visble d'ennemis
		 *             proches ou non
		 * 
		 * @throws IllegalArgumentException : exception lancée si {@code from} est
		 *                                  {@code null}
		 */
		@Override
		public boolean isVisibleFromEnemies(Animal from) throws IllegalArgumentException {
			Utils.requireNonNull(from);
			
			List<Animal> reponse = new ArrayList <Animal> ();
			
			reponse = getVisibleEnemiesForAnimal(from);
			
			return reponse.isEmpty() ? false : true;
		}
		
		/**
		 * Normalise un angle pour qu'il soit compris entre 0 et 2π radians.
		 * 
		 * @param angle : L'angle à normaliser, en radians.
		 * @return Un angle normalisé compris dans l'intervalle [0, 2π].
		 */
		private static double normalizedAngle (double angle) {
			if (angle < 0.0) {
				angle += 2 * Math.PI;
			} else if (angle > (2 * Math.PI)) {
				angle -= 2 * Math.PI;
			}
			return angle;
		}
		
		/**
		 * Procède au rendu graphique des constituants de l'instance {@code this} de
		 * {@link Environment}. Les constituants sont composés d'instances de
		 * {@link Food}, {@link Anthill}, {@link Animal} (comprenant des
		 * {@link AntWorker}, {@link AntSoldier} et {@link Termite}) et de
		 * {@link Pheromone}.
		 * 
		 * @param environmentRenderer : {@link EnvironmentRenderer} interface de rendu
		 *                            graphique de l'environnement simulé par JavaFX,
		 *                            qui est actuellement implémentée par la classe
		 *                            {@link EnvironmentGraphicRenderer}
		 */
		public void renderEntities(EnvironmentRenderer environmentRenderer) {
			listFood.forEach(environmentRenderer::renderFood);
			listAnimal.forEach(environmentRenderer::renderAnimal);
			listAnthill.forEach(environmentRenderer::renderAnthill);
			listPheromone.forEach(environmentRenderer::renderPheromone);
		}
		
		/**
		 * Méthode permettant de définir si un objet présent dans l'instance
		 * {@code this} de {@link Environment} est visible par une instance de
		 * {@link Ant} en fonction de la distance qui les sépare
		 * 
		 * @param distance : {@code double} représentant la distance entre un objet et
		 *                 une instance de {@link Ant}
		 * @return {@code true} si {@code distance} est inférieure ou égale à une
		 *         constante {@code ANT_MAX_PERCEPTION_DISTANCE} extraite du fichier de
		 *         configuration initial; sinon retourne {@code false}
		 * @throws IllegalArgumentException si {@code distance} est négative.
		 */
		public boolean seeByAnt(double distance) {
			Utils.require("La distance doit être supérieure ou égale à zéro", distance >= 0);
			return distance <= Context.getConfig().getDouble(ANT_MAX_PERCEPTION_DISTANCE);
		}
		
		/**
		 * Méthode redéfinie intervenant dans le "double dispatch" utilisé pour gérer
		 * l'action après le déplacement de {@code ant}.
		 * 
		 * @param ant : instance de {@link Ant} en cours de déplacement
		 * @param dt  : {@link Time}, pas de temps périodique de mise à jour de
		 *            l'instance {@code this} de {@link Environment}
		 */
		@Override
		public void selectAfterMoveDispatch(Ant ant, Time dt) {
			// Grâce à la surchage des méthodes, cette méthode sera appelée par n'importe quelle instance de super classe Ant
			// dans son selectAfterMoveDispatch. On peut donc ensuite faire appeler par l'environnement n'importe quelle
			// méthode non privée de l'instance Ant, et lui passer n'importe quel paramètre
			
			ant.afterMoveAnt(this, dt);//dont celui d'un AntEnvironmentView, que Environement implémente
		}
		
		/**
		 * Méthode redéfinie intervenant dans le "double dispatch" utilisé pour gérer
		 * l'action après le déplacement de {@code termite}.
		 * 
		 * @param termite : instance de {@link Termite} en cours de déplacement
		 * @param dt      : {@link Time}, pas de temps périodique de mise à jour de
		 *                l'instance {@code this} de {@link Environment}
		 */
		@Override
		public void selectAfterMoveDispatch(Termite termite, Time dt) {
			// Grâce à la surchage des méthodes, cette méthode sera appelée par n'importe quelle instance de super classe Termite
			// dans son selectAfterMoveDispatch. On peut donc ensuite faire appeler par l'environnement n'importe quelle
			// méthode non privée de l'instance Termite, et lui passer n'importe quel paramètre
					
			termite.afterMoveTermite(this, dt);//dont celui d'un TermiteEnvironmentView, que Environement implémente
		}
		
		/**
		 * Méthode redéfinie intervenant dans le "double dispatch" utilisé pour gérer
		 * l'angle et la probabilité de rotation lors du déplacement de {@code ant}.
		 * 
		 * @param ant : instance de {@link Ant} en cours de déplacement
		 */
		@Override
		public RotationProbability selectComputeRotationProbsDispatch(Ant ant) {
			// Grâce à la surchage des méthodes, cette méthode sera appelée par n'importe quelle instance de super classe Ant
			// dans son computeRotationProbsDispatch. On peut donc ensuite faire appeler par l'environnement n'importe quelle
			// méthode non privée de l'instance Ant, et lui passer n'importe quel paramètre
			
			return ant.computeRotationProbs(this); // comme par exemple: le paramètre environnement, ici une AntEnvironmentView que Environment implémente
		}
		
		/**
		 * Méthode redéfinie intervenant dans le "double dispatch" utilisé pour gérer
		 * l'angle et la probabilité de rotation lors du déplacement de {@code termite}.
		 * 
		 * @param termite : instance de {@link Termite} en cours de déplacement
		 */
		public RotationProbability selectComputeRotationProbsDispatch(Termite termite) {
			// Grâce à la surchage des méthodes, cette méthode sera appelée par n'importe quelle instance de super classe Termite
			// dans son selectAfterMoveDispatch. On peut donc ensuite faire appeler par l'environnement n'importe quelle
			// méthode non privée de l'instance Termite, et lui passer n'importe quel paramètre
							
			return termite.computeRotationProbs(this); // comme par exemple: le paramètre environnement, ici une TermiteEnvironmentView que Environment implémente
		}
		
		/**
		 * Méthode gérant le "double dispatch" lié au comportement - permet à une
		 * instance de {@link AntSoldier} d'appeler la méthode
		 * {@link AntSoldier#seekForEnemies(AntEnvironmentView, Time)} à chaque mise à
		 * jour de l'instance {@code this} de {@link AntEnvironmentView}
		 * 
		 * @param antSoldier : instance de {@link AntSoldier}
		 * @param dt         : {@link Time}, pas de temps périodique de mise à jour de
		 *                   l'instance {@code this} de {@link AntEnvironmentView}
		 */
		@Override
		public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt) {
			// Grâce à la surchage des méthodes, cette méthode
			// sera appelée par AntSoldier dans son specificBehaviorDispatch.
			// Ici, on est libre d'appeler n'importe quelle méthode non privée de
			// AntSoldier, et lui passer n'importe quel paramètre, en particulier
			// une AntEnvironmentView !
					
			antSoldier.seekForEnemies(this /* AntEnvironmentView que Environment implémente */, dt);
		}
		
		/**
		 * Méthode gérant le "double dispatch" lié au comportement - permet à une
		 * instance de {@link AntWorker} d'appeler la méthode
		 * {@link AntWorker#seekForFood(AntWorkerEnvironmentView, Time)} à chaque mise à
		 * jour de l'instance {@code this} de {@link AntWorkerEnvironmentView}
		 * 
		 * @param antWorker : instance de {@link AntWorker}
		 * @param dt        : {@link Time}, pas de temps périodique de mise à jour de
		 *                  l'instance {@code this} de {@link AntWorkerEnvironmentView}
		 */
		@Override
		public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt) {
			// Grâce à la surchage des méthode, cette méthode
			// sera appelée par AntWorker dans son specificBehaviorDispatch.
			// Ici, on est libre d'appeler n'importe quelle méthode non privée de
			// AntWorker, et lui passer n'importe quelle paramètre, en particulier
			// une AntWorkerEnvironmentView !
			
			antWorker.seekForFood(this /* AntWorkerEnvironmentView que Environment implémente */, dt);
		}
		
		/**
		 * Méthode gérant le "double dispatch" lié au comportement - permet à une
		 * instance de {@link Termite} d'appeler la méthode
		 * {@link Termite#seekForEnemies(AnimalEnvironmentView, Time)} à chaque mise à
		 * jour de l'instance {@code this} de {@link AnimalEnvironmentView}
		 * 
		 * @param termite : instance de {@link Termite}
		 * @param dt      : {@link Time}, pas de temps périodique de mise à jour de
		 *                l'instance {@code this} de {@link AnimalEnvironmentView}
		 */
		@Override
		public void selectSpecificBehaviorDispatch(Termite termite, Time dt) {
			// Grâce à la surchage des méthode, cette méthode
			// sera appelée par Termite dans son specificBehaviorDispatch.
			// Ici, on est libre d'appeler n'importe quelle méthode non privée de
			// Termite!
				
			termite.seekForEnemies(this, dt);
		}
		
		/**
		 * Méthode principale de la simulation, en charge d'appeler périodiquement
		 * toutes les méthodes {@link update()} de chaque instance d'objet injecté dans
		 * l'instance {@code this} de {@link Environment}
		 * 
		 * @param dt : {@link Time}, pas de temps périodique de mise à jour de
		 *           l'instance {@code this} de {@link Environment}
		 */

		public void update(Time dt) {

			// Le FoodGenerator instancie des Food et les place dans l'environnement
			// ...(suite en fin de méthode)
			theFoodGenerator.update(this, dt);

			// Même logique que ci-dessus pour le FoodGenerator
			// appliquée à la gestion des phéromones.
			// Ici l'environnement retire de la simulation les phéromones en quantité
			// négligeable.
			Iterator <Pheromone> pheromoneIterator = listPheromone.iterator();
			
			while(pheromoneIterator.hasNext()) {
				Pheromone p = pheromoneIterator.next();
				
				if (p.isNegligible()) {
					pheromoneIterator.remove();
				} else {
					p.update(dt);
				}
			}
			
			
			// Même logique que ci-dessus pour le FoodGenerator
			// appliquée à la gestion des fourmilières.
			// Ici l'environnement ne retire jamais les fourmilières.
			Iterator <Anthill> anthillIterator = listAnthill.iterator();
			
			while (anthillIterator.hasNext()) {
				Anthill a = anthillIterator.next();
				
				a.update(this, dt);
			}
			

			// Même logique que ci-dessus pour le FoodGenerator
			// appliquée à la gestion des animaux.
			// Ici l'environnement retire de la simulation les animaux morts.
			Iterator <Animal> animalIterator = listAnimal.iterator();
			
			while(animalIterator.hasNext()) {
				Animal a = animalIterator.next();
				
				if (a.isDead()) {
					animalIterator.remove();
				} else {
					a.update(this, dt);
				}
			}

			// ... mais c'est bien l'environnement (et non le FoodGenerator) qui se charge
			// de supprimer les instances de Food dont la quantité de nourriture disponible
			// est inférieur ou égale à zéro
			listFood.removeIf(food -> food.getQuantity() <= 0.0);
		}
}
