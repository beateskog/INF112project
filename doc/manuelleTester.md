# Manuelle tester for akseptanse kriterene våre
*Dette er tester som ikke lar seg teste gjennom JUnit*

1. **Akseptanse kriterie for brukerhistorie 1:**
    - Spillbrettet gir muligheten til å bevege segi alle retninger, ved hjelp av kontroller.
    - Spilleren kan navigere brettet med jevne bevegelser, uten feilaktig respons fra kontrollene.
    ```ad-note
        1. Start programmet ved kommando `./make.py -r`
        2. Spiller vises midt på skjermen.
        3. For keyboard er kontrollere satt til `W-A-S-D`
        4. `W` trykkes på keyboard, Observasjon: Spiller beveger seg opp på brettet med tilsvarende animasjon
        5. `S` trykkes på keyboard, Observasjon: Spiller beveger seg ned på brettet med tilsvarende animasjon 
        6. `A` trykkes på keyboard, Observasjon: Spiller beveger seg mot venstre på brettet med tilsvarende animasjon 
        7. `D` trykkes på keyboard, Observasjon: Spiller beveger seg mot høyre på brettet med tilsvarende animasjon 

    ```

2. **Akseptanse kriterie for brukerhistorie 2:**
    - Spilleren kan unngå fiender med å forflytte seg rundt på spillbrettet.
    - Fiender er til stede på brettet og kan følge spilleren.
    - Fiender skal kunne skade spilleren, og spillerens helse skal vise.
    ```note
        1. Spiller kan flytte seg og unngå fiender ved bevegelse (Se manuell test nr 1).
        2. Fiende observeres fra spillet starter.
        3. Fiende observeres å følge spilleren i alle retninger spilleren måtte gå. 
        4. Fiende kan skade spilleren i det kontakt oppstår.
        5. Fiender "spawner" med jevne mellomrom. 
        6. ##HP-BAR OBSERVERES, MEN SAMSVARER IKKE MED SPILLERENS EGENTLIGE HELSE##
    ```
3. **Akseptanse kriterie for brukerhistorie 3:**
    - Spillet skal tilby spilleren en mulighet for å skyte fiender. 
    - Spillet skal tilby ulike typer oppgraderinger som en spiller kan skaffe seg, som f.eks.: rustning, våpen, eller tilbehør, for å styrke spilleren.
    - Spillet skal tilby en klar oversikt over hva og hvor mye en oppgradering skal forbedre en spillers egenskaper.
    ```note
        1. Spilleren skyter, fiende tar skade og forsvinner fra skjerm når den har mistet all "HP".
    ```
