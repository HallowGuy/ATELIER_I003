# 📜 DOCTRINE ATELIER_I003
**Statut : LOI SUPRÊME**

## 1. NOMENCLATURE PHYSIQUE
- Format unique : `XX_XX_XX_XX` (Underscores obligatoires).
- Emplacement : Toute brique logicielle DOIT résider dans `BRIQUETHEQUE/`.
- Interdiction : Aucun dossier ne doit utiliser de points dans son nom.

## 2. STRUCTURE JAVA
- Package racine : `cloud.moondust.zXX_XX_XX_XX`.
- Le préfixe `z` est obligatoire avant l'UID pour la compatibilité Java.
- Pas de sous-packages profonds sans validation de l'Architecte.

## 3. DATA & SQL (DOCTRINE "NO-ORM")
- L'utilisation d'ORM (Hibernate, JPA) est STRICTEMENT INTERDITE.
- Chaque brique gérant des données doit avoir un dossier : `src/main/resources/TABLES.SQL/`.
- Le SQL doit être du pur dialecte PostgreSQL.

## 4. OUTILLAGE (SENTINELLES)
- Les scripts de surveillance résident dans `TOOLS/`.
- `BigBrother.ps1` : Vérifie la structure globale.
- `LittleBrother.ps1` : Extrait le contenu des briques.
- Aucun code métier ne doit être écrit dans `TOOLS/`.

## 5. DÉPENDANCES & MAVEN
- Chaque brique est un module Maven enfant de `pom.xml` (racine).
- L' `artifactId` dans le `pom.xml` doit être identique à l'UID du dossier.

## 6. MANIFESTES
- Chaque brique DOIT posséder un `brick-manifest.json` à sa racine.
- Un manifeste manquant est considéré comme une brique corrompue.