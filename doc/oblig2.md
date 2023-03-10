# Prosjektrapport

## Gruppemøte 23.02.23:

Alle gruppemedlemmer til stede 

Vi har diskutert issue board og bestemt oss for å lage en trello.
Vi har diskutert kommunikasjonen vår, og kommet frem til at vi må lage en SMS-gruppe.

Grunnet innleveringer og generelt kaos har vi vært dårlige på å møte hverandre denne uka. Vi har kommet frem til at vi må:
    Være strengere med oss selv.
    Ha bedre kommunikasjonskanaler(SMS-gruppe, ikke bare discord).
    Akseptere å møte opp selv om vi ikke er fulltallige.

Hittil har noen på gruppen stått for flesteparten av committene. Grunnen til dette er at vi har jobbet samlet, og at det derfor ville ha vært unaturlig og uoversiktlig om alle skulle ha notert ting.


Oppsumering/forbedringspunkter:
1. Bedre oppmøte
2. Bedre kommunikasjon
3. Avklare arbeidsoppgaver


Planlegging for videre arbeid (ressurser):
- Spine for figurer / movable monsters & objects /  (http://en.esotericsoftware.com/)
- Tiled for bakgrunn / verden / level (https://www.mapeditor.org/)
    - HyperLap2D dersom Tiled dersom tiled ikke strekker til
- 2D Particle Editor 


Prio innen neste fredag
- lage tile for verden
- vise verden 
- vise sprite på skjerm 
- UI design (Shop, Main menu)


## Gruppemøte 27.02.23:

Alle gruppemedlemmer til stede 

- Tobias og Krisitan S. jobbet med tiles
- Beate og Askild jobbet med UI design
- Kristian F. R. jobbet generelt med motoren (input system)


## Gruppemøte 02.03.23 (10:15-16:00)

Tilstede: Kristian S, Kristian R, Askild og Beate (Tobias gyldig fravær)

Link til design: [FIGMA](https://www.figma.com/file/qoGq03eyB2PVbSGEsXbSgC/Rona-Survivors?node-id=0%3A1&t=mJ4qQR2cGrKjyURL-1)

**Gjort idag**
- ordnet templates for trello-issues; Visual, Audio, Mechanics og Engine.
    - [Trello](https://trello.com/b/QHYMXsMK/issues#)
- Gått gjennom kode og skrevet ned hovedkarakteristikk (under). 
- Jobbet med UI-design
- Diskutert planer og idéer for the future
- Sett på design til spillfigurer (sprites)


**Neste gjøremål**
-  Implementere UI design
-  Planlegge UX 
-  Jobbe med Player.java (Laste inn ulike characters)
-  Jobbe med Wiki

## Gruppemøte 09.03.23

Alle gruppemedlemer tilstede 

**Gjort i dag** 
- Beate og Tobias: Jobbet med Enemy class
- Kristian S: player animation 
- Kristian R: planlegger game resource loading 
- Askild: Testmappe + resarch skincomposer (planlegger hvordan man skal implementere meny)

**Oppsummering:**
- Komunikasjonen fungerer bedre etter at vi opprettet SMS-gruppe. 

**Forbedringspunkter til neste sprint**
1. Vi har hittil blitt veldig ivrige på å få til ting, og har egentlig glemt å implementere så mange tester. Dette er et stort forbedringspunkt! 
2. Vi kan bli enda flinkere å oppdatere og bruke trello
3. Vi kan bli enda flinkere på gode commit-meldinger til git

# Krav og spesifikasjon 

### MVP (minimum viable product):  
1. Vise et spillebrett (fullført)
2. Vise spiller på spillbrettet  (fullført)
3. Flytte spiller i alle retninger  (fullført)
4. Fiender som går i mot spiller (fullført)
5. Spiller kan skyte automatisk 
6. Spiller kan dø/få mindre HP (ved kontakt med fiender)
7. Fiender blir sterkere over tid
8. Spillfigur kan få oppgraderinger underveis 
9. Mål for spillbrett: få nok poeng
10. Låser opp nytt spillbrett/nytt nivå 
11. Start-skjerm ved oppstart. Kan kjøpe permanente oppgraderinger for opptjente penger. 

### Brukerhistorier vi har jobbet med hittil:
1. Som en spiller, vil jeg kunne ha et spillbrett, hvor jeg kan bevege meg i alle forskjellige retninger, slik at jeg kan unngå fiender på best mulig måte. 
2. Som en spiller, vil jeg ha en HP-bar, slik at fiender blir en hindiring og at spillet har et klart mål.

### Akseptansekriterier:
<em>Akseptanse kriterie for brukerhistorie 1:</em>
* Spillbrettet gir muligheten til å bevege seg i alle retninger, ved hjelp av kontroller. (Fullført)
* Spilleren kan navigere brettet med jevne bevegelser, uten feilaktig respons fra kontrollene. (Fullført)

<em>Akseptanse kriterie for brukerhistorie 2:</em>
* Spilleren kan unngå fiender med å forflytte seg rundt på spillbrettet. (fullført)
* Fiender er til stede på brettet og kan følge spilleren. (Delvis fullført med 1 fiende)
* Fiender skal kunne skade spilleren, og spillerens helse skal vise. (Ikke fullført)

# Produkt og Kode

### Dette har vi fikset siden sist

- Ordnet Meny
- Ordnet bakgrunn 
- UI handler
- Fått inn Player
    - Animasjon for løpe venstre & høyre
    - Ny og bedre hitbox
- Fått inn Enemy
- Save system 
- Ordnet spilldemo der man kan bevege en player med keyboard input eller controller 
- Laget Wiki 

### Kodeforklaring

**Desktop / desktopLauncher**
- kjører programmet
- setter frames,vsync, resolution, windowMode, icon
- start programmet med fil fra /src/ronaSurvivors

**/src/ronaSurvivors**
- InputUtil
    - klasse for lettere kontrollere inputs 
- create
    - første gang man kjører spillet
    - `Gdx.app.setLogLevel()` -> logger alt som skjer i spillet (brukes for debugging)
    - batch
        - samler alt som skal på skjermen til en gruppe som skal rendres samtidig, framfor hver for seg.
        - hver gang noe skal tegnes bruker vi `batch.draw()`
    - `this.setScreen(new GameScreen(this))` - forteller hva som skal tegnes initially når spillet starter.
    - input
        - `input.update`for å oppdatere input
- `dispose()`
    - clear memory   

**/src/GameScreen**
- Spill-scene
- `float viewSize` Forteller hvor stor del av verden som skal vises (zoom)
- `extendedViewport` - istendenfor å putte ting direkte på skjermen - ser på skjermen og pixlene, sjekker hvordan skjermen faktisk er (når den blir dratt/resize), regner ut forskjellene og viser objekter med "riktig" oppløsning ratio. (forandrer viewsize)
- `box2dDebugRendrer` lar oss se hitboxer mens vi utvikler
- `update()`
    - oppdater fysikken 1/60, 6 ganger regn ut velocity, 2 ganger regn ut position før neste frame. 
    - `player.move()`
    - ``game.input.don("menu")` - lagret i .json filer hvor menu har sine attributter. 
- `render()`
    - `ScreenUtils.clear()` - fjerner det som var før, gjør hele skjermen svart
    - `extendViewport.apply()` - for å kun ha ett kamera for verden vi er i nå
    - `update()` - gi meg oppdateringer og beveg etc..
    - `game.bach.setProjectionMatrix()` - batch rendrer til camera
    - `levelUtil.render()` - viser det kameraet er ser
    - `game.batch.begin()` - åpner opp for det vil vi ha i batch
    - `game.render(game.batch)` - vis alt i batchen
    - `game.batch.end()` - lukkbatch
    
**/src/player**
- størrelse
- sprite (bilde.png)
- speed  (hastighet)
- levelUtil (for å kunne plassere seg selv i spillet(verdenen))
- `BodyDef()` - nytt item i verden
    - bodydef.type - dynamisk / vegger er statiske
    - bodydef.position.set - size på skjermen
    - body = level.util.world - opprett en ny ting(meg) og putt meg inn i verden!
- `Polygonshape()`
    - fysikk form, hvordan er jeg, hvordan interagerer jeg med det rundt meg?
    - er en hitbox
- `FixtureDef()` 
    - fixture (noe du fester på en figur) - fysikk basert
    - Festes på en body (figur) og forteller hvordan figuren (body) skal operere
    - `body.createFixture()` oppretter en fixtureDef som tar over hva box gjorde (box var en mal)
        - Vi bruker box.dispose() siden denne ikke er nødvendig lenger, vi bruker heller fixtureDef.
    - `body.setFixedRotation(true)` setter hitboxen til å ikke kunne rotere.
- `render()`
    - Gir en batch av det vi skal rendre (spilleren skal rendres)
- `move()`
    - `boy.setLinear()..` - beveger figuren
    - `camera.position.set()..` - setter kamera til å være sentrert på hvor spilleren er
    - `camera.update()` - oppdaterer kamera

