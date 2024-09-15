_Introduction  _:__

Ce projet a été produit dans le cadre du MOOC « Projet de programmation en Java » de l’École Polytechnique Fédérale de Lausane hébergé sur CourSera. 
Il m’a permis de mettre en œuvre mes premières connaissances et pratiquer concrètement autour des concepts essentiels acquis en programmation orientée-objet en langage Java: abstraction et encapsulation, héritage, polymorphisme, double dispatch. 

Je remercie chaleureusement toutes les personnes ayant créée, développée, participé à la création de ce MOOC, qui m'aura été précieux pour aborder ces notions en autodidacte. 

_Description du projet _:__

Le but de ce projet est de produire une simulation de colonies de fourmis, insectes sociaux capables d’intelligence collective, au travers de la création d’une application simulant des colonies (« fourmilières ») générant des fourmis en quête de nourriture.
La simulation repose sur un modèle simple constitué d’un environnement global, comprenant les fourmilières, les fourmis, les sources de nourriture, et les termites, ennemis naturels des fourmis.
L’environnement contient dès le départ des fourmilières auxquelles sont rattachées les fourmis qui en émergent périodiquement. De plus, des sources de nourriture sont ajoutées périodiquement et aléatoirement dispersées dans cet environnement, afin que les fourmis puissent se nourrir. 

Les fourmis sont de 2 natures : 
-	Ouvrières : fourmis capable de collecter la nourriture et de déposer des traces de phéromones dans l’environnement ; ces traces leur permettent de retrouver leur fourmilière plus facilement ainsi que d’indiquer à leurs consoeurs et elle-même  où se déplacer pour quérir de la nourriture; ces traces s’évaporent au cours du temps jusqu’à totalement disparaître.
-	Soldates : fourmis ayant pour rôle de combattre des ennemis, constitués de prédateurs naturels que sont les termites, ainsi que de fourmis issues de fourmilières étrangères et concurrentes. Elles suivent aussi les traces de phéromone déposées par leurs consoeurs afin de mieux les protéger.

Deux modes de déplacement des animaux simulés sont possibles : 
-	Déplacement Inertiel : les animaux se déplacent aléatoirement en privilégiant les directions qui leur font face ; ce mode de déplacement sera celui utilisé par les termites et par les fourmis en l’absence de phéromones dans l’environnement ;
-	Déplacement sensoriel : les animaux se déplaceront de manière guidée en suivant les traces de phéromone déposées dans l’environnement par des fourmis ou par elle-même.

L’environnement est modélisé comme un terrain rectangulaire en deux dimensions, qui est géré de manière torique : les animaux dépassant les limites de l’environnement par un côté réapparaissent sur le côté opposé. 
L’évolution de l’environnement en temps « réel » est géré au travers d’une boucle qui a la charge de mettre à jour tous les constituants en fonction du temps. Chaque itération correspond donc à un cycle de simulation.

_Organisation du projet :_

Le projet est organisé dans un package ayant pour racine « ch.epfl.moocprog », comprenant plusieurs dossiers : 
-	App : contient le programme principal (dont son fichier de démarrage : Main.java) dans sa version graphique gérée via JavaFX ;
-	Config : contient le matériel permettant de gérer les fichiers de ressources du dossier res (cf. plus loin) ;
-	Gfx : contient le matériel de l’interface graphique JavaFX exploitée ;
-	Random : contient des méthodes utilitaires servant dans le projet à gérer la notion d’aléatoire ;
-	Utils : contient divers utilitaires de base (représentation du temps, de paires de valeurs etc.)
-	Tests : contient un programme principal (Main.java) servant à faire des tests ponctuels locaux manuels.

_Paramètres de la simulation :_

Le dossier « res » -situé en dehors de la racine commune « ch.epfl.moocprog » - contient les ressources utiles à l’exécution de la simulation, dont les paramètres de configuration au lancement.
Le fichier de paramètre « res/app.cfg » contient un ensemble de constantes écrites sous la forme « ANIMAL_PARAMETRE_CONSTANT :class :valeur ». Par exemple, « TERMITE_HP :int :500 » représente un nombre entier constant associé à la quantité de vie d’une termite unique (HP acronyme de Hit Points en anglais signifiant Points de vie en français) tandis que « TERMITE_SPEED » est associé à un réel représentant sa vitesse de déplacement (speed signifiant vitesse en anglais).
Le fichier de paramètre « res/config.cfg » sert quant à lui à déclarer les éléments qui vont être injectés dès le démarrage de la simulation, ainsi que leur position dans l’environnement torique. Parmi ces éléments seront typiquement déclarés les termites et les fourmilières. Par exemple, la déclaration « Anthill: 800, 450 » suivi à la ligne de « Termite:20,30 » permettra d’initialiser la simulation avec une foumillière (Anthill en anglais) et une termite respectivement en position torique (x,y) de coordonnées (800, 450) et (20, 30).
La classe « ch/epfl/moocprog/app/Context.java » sert à faire le lien entre ce fichier de paramètres et le programme de simulation, dans le but d’extraire les valeurs associées à ces constantes.
L’interface graphique gérée par JavaFX permet d’agir directement sur le fichier de paramètres « res/app.cfg » sans avoir à l’éditer séparément. 

_Lancement de la simulation :_

La version graphique du programme principal de simulation se trouve dans : ch/epfl/moocprog/app/Main.java .Pour le lancer, il suffit de faire un clic droit dans Eclipse puis Run as > Java application.
 
Lorsque vous exécutez le programme, vous devriez voir une représentation graphique de l'environnement (un rectangle bleu). La simulation est initialement en pause. Pour la lancer, appuyez sur le bouton "Play" en haut à gauche. Vous devriez alors voir apparaître spontanément dans l'environnement des tas de nourriture distribués aléatoirement (petits tas verts), mais aussi des Termites et des Fourmilières.

Des informations utiles au débogage peuvent être obtenues à l'aide des boutons situés juste en dessous du rectangle bleu. Par exemple, pour examiner et vérifier les quantités de nourriture présentes dans chaque tas de nourriture, vous pouvez cliquer sur le bouton "Quantity". De même, la position des différents objets peut être affichée en cliquant sur le bouton "Position". Cliquer à nouveau sur le bouton "Quantity" ou "Position" effacera les informations de débogage textuelles correspondantes.

Vous pouvez à tout moment recommencer la simulation depuis zéro (réinitialisation de l'environnement) en utilisant le bouton "Restart" ou en arrêtant temporairement le déroulement au moyen du bouton "Pause". Cela permet d'observer tranquillement les informations de débogage liées aux objets affichés avant de poursuivre la simulation.

Les paramètres éditables sont sur la droite. Il suffit pour cela de modifier la valeur du champ désiré, puis de cliquer sur « Apply » pour inscrire la modification en dur dans le fichier. Pour revenir à la configuration initiale de la simulation, il suffira de cliquer sur « Reset » afin de rappeler le fichier « res/app.cfg » avec l’ensemble de ses valeurs réinitialisées à leurs valeurs antérieures. Si vous souhaitez retrouver ces valeurs par défaut lors du prochain lancement du programme, n'oubliez jamais de cliquer sur "Reset" avant de quitter le programme ou avant un "Restart".

Par exemple, changez la valeur de TIME_FACTOR à 5, puis appuyez sur "Apply". Vous devriez voir la simulation se dérouler beaucoup plus vite. Cliquez sur "Reset" pour retrouver la valeur d'origine de TIME_FACTOR et être certain de retrouver la valeur d’origine dans la configuration initiale. 

Remarque : la modification des valeurs WORLD_WIDTH et WORLD_HEIGHT (taille graphique de l'environnement) ne prendra effet qu'une fois que vous aurez quitté et relancé le programme. Il en est ainsi pour tous les paramètres qui sont directement utilisés par les constructeurs des objets de la simulation.
