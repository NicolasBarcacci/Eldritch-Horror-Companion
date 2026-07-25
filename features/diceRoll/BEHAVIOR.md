# Comportement de la feature DiceRoll

## États

### 1. État initial (avant un roll, ou après un reset)
- Status : sélectionnable (activé)
- Nombre de dés : modifiable (activé)
- Résultat au centre : aucun, affiche un **?** pour chaque dé
- Bouton Roll/Reroll : affiche **Roll**, activé
- Bouton Clear : désactivé

### 2. Après un roll, aucun dé sélectionné
- Status : reste sélectionnable (activé)
- Nombre de dés : reste modifiable (activé)
- Résultat au centre : affiché
- Chaque dé affiche un **cadenas fermé** (verrouillé, ne sera pas relancé)
- Bouton Roll/Reroll : affiche **Reroll**, désactivé
- Bouton Clear : activé

### 3. Après un roll, au moins 1 dé sélectionné
- Identique à l'état 2, sauf :
- Chaque dé cliqué affiche un **cadenas ouvert** (déverrouillé, sera relancé) au lieu du cadenas fermé
- Bouton Reroll : activé

### 4. Après un reroll
- Les dés qui affichaient un cadenas ouvert sont relancés, les autres gardent leur valeur
- La sélection est réinitialisée (vide) et tous les dés reviennent au cadenas fermé
- Revient à l'état 2 (Reroll désactivé, Status/Nombre de dés toujours activés, Clear activé)

## Transition : reset vers l'état initial

Les actions suivantes ramènent à l'état 1 (résultat effacé, sélection effacée, bouton principal redevient **Roll** activé, Clear redevient désactivé), en conservant Status et Nombre de dés :

- **Sélection d'un nouveau status** : ce nouveau status est conservé
- **Changement du nombre de dés** (+/-) : cette nouvelle quantité est conservée
- **Clic sur Clear** : Status et Nombre de dés restent inchangés

## Tableau récapitulatif

| État                              | Status | Nb dés | Dés                            | Bouton principal   | Clear  |
|-----------------------------------|--------|--------|--------------------------------|---------------------|-----------|
| 1. État initial                   | activé | activé | `?`                             | Roll (activé)       | désactivé |
| 2. Après roll, 0 dé sélectionné    | activé | activé | tous cadenas fermé              | Reroll (désactivé)  | activé    |
| 3. Après roll, ≥1 dé sélectionné   | activé | activé | sélectionnés : cadenas ouvert   | Reroll (activé)     | activé    |
| 4. Après reroll                    | activé | activé | tous cadenas fermé              | Reroll (désactivé)  | activé    |
