# Java Hotel Management System - GitHub Copilot Dojo
Bienvenue dans le Dojo GitHub Copilot pour apprendre à utiliser cette nouvelle technologie passionnante tout en développant un système de gestion d'hôtel en Java. Ce dojo se concentre sur la gestion des réservations d'hôtel à l'aide de GitHub Copilot.

### Prérequis
Assurez-vous d'avoir installé les outils suivants avant de commencer :

* Java 17
* Visual Studio Code
* Git
* Une licence GitHub Copilot

## Avant de commencer :
* Clonez le projet : `git clone https://github.com/landschootl/hotel-management-dojo.git`.
* Ouvrez le dans Visual Studio Code.

## Exercices

### Exercice 1 : Compréhension du projet (5 minutes)
Ouvrez le projet dans Visual Studio Code et explorez les fichiers et les dossiers tout en vous familiarisant avec le projet.
Utilisez GitHub Copilot pour vous aider à comprendre le code existant.

### Exercice 2 : Génération de la javadoc (5 minutes)
Utilisez GitHub Copilot pour générer la javadoc des différentes classes du projet.

### Exercice 3 : Fixation & correction des bugs (10 minutes)
* Sur la méthode `searchBooking` de la classe `ManageHotel`, il y a un bout de code manquant (voir todo). Utilisez GitHub Copilot pour le générer.
* Jouez les tests présents dans la classe `ManageHotelTest`. Si il y a des tests qui échoue, utilisez GitHub Copilot pour comprendre le problème et corriger le bug.

Si vous modifiez le code, n'oubliez pas de jouer les tests pour vérifier que vous n'avez pas introduit de nouveaux bugs et de regénérer la javadoc.

### Exercice 4 : Génération des tests unitaires (20 minutes)
* Générez les tests unitaires pour la méthode `bookRoom` de la classe `ManageHotel` à l'aide de GitHub Copilot. 
 
Assurez vous de tester les cas suivants :
* La réservation est possible
* La réservation n'est pas possible car la date de réservation est invalide
* La réservation n'est pas possible car la date de réservation est dans le passé
* La réservation n'est pas possible car la date de check-out est avant la date de check-in
* La réservation n'est pas possible car la date de check-out est le même jour que la date de check-in
* La réservation n'est pas possible car la chambre n'existe pas
* La réservation n'est pas possible car la chambre est déjà réservée (testez tous les cas possibles)
* ...

### Exercice 5 : Génération de code (10 minutes)
* Générez le code et les tests de la méthode `cancelBooking` de la classe `ManageHotel` à l'aide de GitHub Copilot.
* Regénérez la javadoc.

La méthode doit permettre d'annuler une réservation à partir de sa référence. Si la réservation n'est pas trouvée, une exception doit être levée.

### Exercice 6 : Génération de code (20 minutes)
* Générez le code et les tests de la méthode `suggestRoom` de la classe `ManageHotel` à l'aide de GitHub Copilot.
* Regénérez la javadoc.

La méthode doit permettre de suggérer une chambre à partir de sa capacité et de la date de réservation. Si aucune chambre n'est trouvée, une exception doit être levée.
Assurez-vous de gérer les exceptions en cas d'indisponibilité de la chambre, de paramètres invalides, etc.

Vous avez terminé ! Vous avez maintenant un système de gestion d'hôtel fonctionnel et vous avez appris à utiliser GitHub Copilot ! 🚀