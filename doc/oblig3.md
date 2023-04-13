# Prosjektrapport

## Gruppemøte 13.03.2023 (08.15-10.00)

Alle gruppemedlemer tilstede 

- Gikk igjennom tilbakemeldinger fra gruppeleder på oblig1.
- Her fikk vi trekk på rollefordeling, og forklaring på hva rollene innebærer og har derfor utbedret dette. 

**Diskutert hva som er viktig å fokusere videre på** 
- Testing står høyt på listen siden vi har kommet sent i gang med dette. 

**Forbedringspunkt for denne sprinten**

1. Roller
2. Kontroll på hva som trengs hver innlevering 
3. Planlegge hva man skal jobbe mellom hver gang. Hittil har vi hatt generelle tema, men kanskje mer spesifikke oppgaver. (Bruke Trello mer)

## Gruppemøte 16.03.2023 (12.00-14.00)

Alle gruppemedlemer tilstede 

- Starter dagen med å snakke om hva vi har gjort siden sist
- Effektiv gruppetime,der vi setter i gang å jobbe med ulike oppgaver.  

**Jobbet med i gruppetimen, og fokus til neste time**

- Kristian x2: Jobbe med editor for player, enemies og items 
- Askild og Beate: Fortsette med skincomposer (UI design)
- Tobias: HP-bar

**Roller diskusjon**

- Vi trenger en som har en mer ansvarlig rolle i forhold til å passe på hva som skal med til innleveringer. 
- Siden Beate er kundekontakt tar hun nå mer ansvar for å delegere ut mer som må gjøres direkte i forhold til innleveringer.
- Ellers er folk fornøyd med rollene på dette tidspunktet. 

**Ting vi må finne ut av**

- Fått en rolle som tar mer ansvar for selve oblig-innleveringene 
- Gå i gruppetime for å finne ut av hva/hvordan vi skal teste.
- Ellers fungerer gruppedynamikken godt, vi har god dialog og alle gir en innsats. 

## Gruppemøte 20.03.2023 (08.15-10.00)

Alle gruppemedlemmer til stede

**Jobbet med i gruppetimen, og fokus til neste time**

Spurte gruppeleder om hvordan vi skal gjøre tester, siden det ikke er mulig å teste 
det grafiske. Fikk da beskjed om at vi kan gjøre manuelle tester for det grafiske.

- Kristian R: Jobbe med editor for player, enemies og items 
- Kristian S: Research Audio
- Beate: Projectile
- Tobias: HP-bar
- Askild: Skincomposer 

**Ting vi må finne ut av**

- Bli flinkere å bruke trello

## Gruppemøte 23.03.2023 (10.00-14.00)

Alle gruppemedlemmer til stede (Tobias gyldig fravær)

- Kristian R: Ordne Assests system 
- Kristian S: Audio
- Beate: Projectile
- Askild: UI design 

**Ting vi må finne ut av**

- Bli flinkere å bruke trello 

## Gruppemøte 30.03.2023 (10.00-14.00)

Alle gruppemedlemmer til stede

- Kristian R: Ordne Assests system 
- Kristian S: Projectile
- Beate: Projectile
- Askild: UI design implementering 
- Tobias: HP-bar

## Gruppemøte 13.04.2023 

Alle gruppemedlemmer til stede 



# Krav og spesifikasjon

**Hvilke krav vi har prioritert og hva vi har gjort siden forrige gang:**

### MVP (minimum viable product):  

1. Vise et spillebrett (fullført)
2. Vise spiller på spillbrettet  (fullført)
3. Flytte spiller i alle retninger  (fullført)
4. Fiender som går i mot spiller (fullført)
5. Spiller kan skyte automatisk (fullført)
6. Spiller kan dø/få mindre HP (ved kontakt med fiender)
7. Fiender blir sterkere over tid
8. Spillfigur kan få oppgraderinger underveis 
9. Mål for spillbrett: få nok poeng
10. Låser opp nytt spillbrett/nytt nivå 
11. Start-skjerm ved oppstart. Kan kjøpe permanente oppgraderinger for opptjente penger. 

### Brukerhistorier vi har jobbet med denne obligen:
2. Som en spiller, vil jeg ha en HP-bar, slik at fiender blir en hindiring og at spillet har et klart mål.
3. Som en spiller, vil jeg ha muligheten til å skyte mot forskjellige typer fiender, som blir sterkere, i tillegg til at jeg skal kunne bruke forskjellige våpen og ferdigheter, slik at jeg kan utfordre meg selv og gjøre fremgang i spillet.

### Akseptansekriterier:
<em>Akseptanse kriterie for brukerhistorie 2:</em>
* Spilleren kan unngå fiender med å forflytte seg rundt på spillbrettet. (fullført)
* Fiender er til stede på brettet og kan følge spilleren. (Delvis fullført med 1 fiende)
* Fiender skal kunne skade spilleren, og spillerens helse skal vise. (Ikke fullført)

<em>Akseptanse kriterie for brukerhistorie 3:</em>
* Spillet skal tilby spilleren en mulighet for å skyte fiender. (delvis fullført, fiender tar enda ikke skade)
* Spillet skal tilby ulike typer oppgraderinger som en spiller kan skaffe seg, som f.eks.: rustning, våpen, eller tilbehør, for å styrke spilleren.
* Spillet skal tilby en klar oversikt over hva og hvor mye en oppgradering skal forbedre en spillers egenskaper.

### Oppsummering 
- Jobbet med projectile, den skal skyte ut i fra hvilken vei spilleren er rettet. 
- Jobbet med HP-bar slik at vi kan begynne å implementere at spiller skal ta skade osv. 
- Jobbet med å lage ulike screens, slik at man kan velge character før man begynner spillet 
- Fått lagt til lyd på når spilleren går 

- Nevne bugs om de finnes

# Produkt kode 

**Dette har vi fikset siden sist** 

- [Manuelle tester](https://git.app.uib.no/mavenless/rona-survivors/-/blob/main/doc/manuelleTester.md)
- I tillegg har vi laget noen JUnit tester. 

- Link til klassediagram

- Links to Trello: 

    - [TRELLO ISSUES](https://trello.com/b/QHYMXsMK/issues)

    - [TRELLO TASKS](https://trello.com/b/0YPRkMZo/tasks)


- All kommentert ut kode som ikke brukes må fjernes, eller kommentar på hvorfor det er der hvis det må være der 

- Tester 

- Dokumentasjon for public metoder 

## Development-style og Rollefordeling:
* Development-style: *Scrum*
1. *Scrum-master* @Kristian.Rognsvaag
- En Scrum-master har i et større prosjekt mange funksjoner, men de vi velger å fokusere på i vårt mindre prosjekt er:

    - Sikrer Scrum-metodikken blir brukt for å guide teamet
    - Vi vil fokusere på å bruke "Daily Scrum", som vil være ca. 2 ganger ukentlig med vår arbeidsmengde.
    - Vi ser på de obligatoriske innleveringene som våre "sprinter" 

2. *Dev. Team-member* @Askild.Heiret
    - UX/UI ansvarlig: ansvar for brukervenlighet, layout og visual design. 

3. *Dev. Team-member* @Kristian.Skeie
    - Test ansvarlig: ansvar for produktkvalitet

4. *Dev. Team-member* @Tobias.Husebo
    -   Software arkitekt: beslutningstaker når det gjelder ordning av programvaren
    
5. *Dev. Team-member* @Beate.Skogvik
    - Kundekontakt: kontakt med gruppeleder for å vite hva som forventes til hver obligatoriske innlevering.  





