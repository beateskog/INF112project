##Gruppemøte 23.02.23:

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


##Gruppemøte 27.02.23:

Alle gruppemedlemmer til stede 

- Tobias og Krisitan S. jobbet med tiles
- Beate og Askild jobbet med UI design
- Kristian F. R. jobbet generelt med motoren (input system)


##Gruppemøte 02.03.23

Tilstede: Kristian S, Kristian R, Askild og Beate. 

Link til Figma: https://www.figma.com/file/qoGq03eyB2PVbSGEsXbSgC/Rona-Survivors?node-id=0%3A1&t=mJ4qQR2cGrKjyURL-1

Neste gjøremål
-  Implementere UI design
-  Planlegge UX 
- Jobbe med Player.java (Laste inn ulike characters)


### Re-cap fra forrige uke og kodeforklaring

**Desktop / desktopLauncher**
- kjører programmet
- setter frames,vsync, resolution, windowMode, icon
- start programmet med fil fra /src/ronaSurvivors

**/src/ronaSurvivors**
- InputUtil
    - klasse for lettere kontrollere inputs 
- create
    - første gang man kjører spillet
    - Gdx.app.setLogLevel -> logger alt som skjer i spillet (brukes for debugging)
    - batch
        - samler alt som skal på skjermen til en gruppe som skal rendres samtidig, framfor hver for seg.
        - hver gang noe skal tegnes bruker vi `batch.draw()`
    - `this.setScreen(new GameScreen(this))` - forteller hva som skal tegnes initially når spillet starter.
    - input
        - `input.update`for å oppdatere input
- dispose
    - clear memory   

**/src/GameScreen**
- Spill-scene
- `float viewSize` Forteller hvor stor del av verden som skal vises (zoom)
- `extendedViewport` - istendenfor å putte ting direkte på skjermen - ser på skjermen og pixlene, sjekker hvordan skjermen faktisk er (når den blir dratt/resize), regner ut forskjellene og viser objekter med "riktig" oppløsning ratio. (forandrer viewsize)
- `box2dDebugRendrer` lar oss se hitboxer mens vi utvikler
- Player
    - 

**/src/player**
- størrelse
- sprite (bilde.png)
- speed  (hastighet)
- levelUtil (for å kunne plassere seg selv i spillet(verdenen))
- BodyDef - nytt item i verden
    - bodydef.type - dynamisk / vegger er statiske
    - bodydef.position.set - size på skjermen
    - body = level.util.world - opprett en ny ting(meg) og putt meg inn i verden!
- Polygonshape 
    - fysikk form, hvordan er jeg, hvordan interagerer jeg med det rundt meg?
    - er en hitbox
- FixtureDef 
    - *continue here at later point* 
