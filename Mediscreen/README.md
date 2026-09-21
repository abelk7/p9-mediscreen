# Mediscreen — Microservices, NoSQL & Gestion Agile

## 📌 Présentation du projet
Ce projet s'inscrit dans le cadre du développement d'une solution logicielle pour une société internationale spécialisée dans le dépistage et la prévention des risques de maladies pour les cliniques et cabinets privés.

L'objectif était de concevoir, développer et documenter une application basée sur une **architecture en microservices**, en intégrant des bases de données relationnelles et **NoSQL**, tout en appliquant une **méthodologie Agile (Scrum)** sur 3 sprints en contexte de travail à distance.

### 🛠️ Stack Technique & Architecture
Langage & Framework : Java, Spring Boot (Spring Data JPA, Spring Data MongoDB)

Bases de données : MySQL / PostgreSQL (données relationnelles), MongoDB (NoSQL pour l'historique et les notes de santé)

Architecture : Microservices (API REST), Docker & Docker Compose (conteneurisation)

Tests & Qualité : JUnit, Mockito, rapports de couverture

Gestion de Projet & DevOps : Méthodologie Agile / Scrum (Kanban), Dockerfiles, Documentation OpenAPI / Swagger

### 🎯 Fonctionnalités & Organisation par Sprints
1. Gestion des Patients & Notes Médicales (Microservices & NoSQL)
* Découpage du système en microservices autonomes pour assurer l'évolutivité et l'isolement des responsabilités.

* Prise en charge des données patients relationnelles et intégration d'une base de données NoSQL (MongoDB) pour stocker et manipuler l'historique non structuré des notes des praticiens.

2. Algorithme de Dépistage & Calcul des Risques
* Implémentation d'un microservice dédié à l'évaluation du niveau de risque de maladie (ex. Diabète) basé sur des déclencheurs (triggers), l'âge et le genre des patients.

3. Méthodologie Agile & Travail à Distance
* Suivi du projet structuré sur 3 sprints via un tableau Kanban.

* Animation des cérémonies de sprint et rédaction de rétrospectives complètes à l'issue de chaque cycle pour optimiser les processus de livraison.

4. Conteneurisation & Documentation
* Conteneurisation de l'ensemble des microservices et bases de données à l'aide de Docker et Docker Compose.

* Rédaction d'une documentation technique et fonctionnelle complète des API REST (OpenAPI / Swagger).

### 💡 Compétences clés démontrées
* Conception et implémentation d'une architecture distribuée en microservices Java / Spring Boot.

* Utilisation d'une base de données NoSQL (MongoDB) adaptée aux contraintes métier.

* Conteneurisation et orchestration de services via Docker.

* Application rigoureuse des principes Agiles (Kanban, rétrospectives de sprint).

* Rédaction de documentation technique et exposition d'API REST standardisées.
