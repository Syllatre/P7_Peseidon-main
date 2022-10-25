# P7_Poseidon
Transformez votre backend en API pour rendre votre application plus flexible

# A propos du projet
Poseidon est un logiciel d’entreprise déployé sur le Web qui vise à générer davantage de transactions pour les investisseurs institutionnels qui achètent et vendent des titres à revenu fixe.

Objectif à réaliser :
* Implementer les methodes CRUD pour chaque entité
* gérer l'authentification par les deux modes
* réalisation des tests

# Prérequis

* Java 11
* mySQL 8


# Application
Avant le lancement de l'application il faudrait creer la database poseidon et poseidontest.
Pour accéder à l'application rendez-vous sur l'url http://localhost:8080
Vous pouvez directement logger à l'application avec l' ``` identifiant :
  user``` et ``` mot de passe : 1234 ``` en tant que user
  ou en tant que administrateur avec l' ``` identifiant :
  user``` et ``` mot de passe : 1234 ```

sinon vous pouvez creer un compte en cliquant sur manager qui se trouve dans la page d'acceuil "/"

# Lancer les tests

*``` mvn test ``` pour les tests unitaires
*``` mvn verify ``` pour les tests d'intégrations
