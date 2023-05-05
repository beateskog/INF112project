# Prosjektrapport 

## Gruppemøte 17.04.2023 (08.15-10.15)

Tilstede: Kristian S, Beate, Askild og Tobias. (Kristian Fredrik gyldig fravær)

**Forbedringspunkt for denne sprinten**
1. lage til oppgaver på trello om gjennværende arbeid, slik at alle kan plukke oppgaver etterhvert som de er ferdig med forrige. Så slipper man å bli usikker på hva som burde gjøres. (Videreføres fra forrige sprint, da vi enda ikke er fornøyd)

Prioriteringsliste: 
**Spillfunksjoner**
- Fleire fiender 
- Spiller kan dø 
- Fiende må kunne ta skade 
- Fiende må kunne dø 
- HP må mistes kontinuerlig under kontakt 

**Meny**
- Settings 

## Gruppemøte 20.04.2023 (12.15-14.15)

Tilstede: Kristian S, Kristian R, Beate og Tobias. (Askild gyldig fravær)

**Jobbet med i gruppetimen**
- Kristian R: Assets 
- Kristian S: Projectile sprite 
- Beate: Fiender ligger i Pool 
- Tobias: Fiende kan ta skade

## Gruppemøte 24.04.2023 (14.15-16.15)

Tilstede: Kristian S, Askild, Beate og Tobias. (Kristian R gyldig fravær)

Prioriteringslisten vi lagde 17.04 er vi allerede nesten ferdige med. Derfor brukte vi dette gruppemøtet til å bli enige om nye prioriteringspunkter. Disse la vi inn som tasks på trello, og alle kan plukke oppgaver der i fra, når de har fullført oppgaver de har begynt på. 

Prioriteringsliste: 
**Spillfunksjoner**
- Killcount -> for å tracke når vi vil ha oppgraderinger 
- Enemies blir sterkere over tid 
- Projectiles tar mer skade over tid
- Projetiles rendrer oftere over tid 
- Enemies rendrer oftere over tid 

Vi diskuterte også vår MVP, og mener selv at punkt 1-6 er de absolutt viktigste for et fungerende spill, og oppnådd MVP. De resterende punktene synes vi er viktige for å ha et spill som faktisk er gøy/givende å spille! Dermed ikke nødvendig for MVP, men mer et stretch goal. 

**Jobbet med i gruppetimen**
- Askild: Pause knapp  
- Kristian S: Killcount, enemies rendrer oftere 
- Beate: Fiender blir sterkere over tid 
- Tobias: Spiller kan dø 

## Gruppemøte 27.04.2023 (10.15-13.00)

Tilstede Kristian S, Askild og Beate.

Jobbet med individuelle ting, og sammarbeidet om å finne løsninger hvis noen hadde problemer.

## Gruppemøte 03.05.2023 (10.00-12.00)

Alle gruppemedlemmer tilstede

**Diskusjon**

- Finne ut når vi kan ha fremføring (Kun onsdag 10.mai alle kan møte opp)
- Fikse på det vi har fått kommentarer om på oblig 3 
- Hva som må gjøres før innlevering fredag, og hvem som gjør hva: 

Kristian R: Dokumentasjon tester, clean code
Kristian S: credits
Beate: Shine dokumenter før innlevering, få med alt som skal være med 
Tobias: Spiller kan dø -> game over 
Askild: hjelpeside om hvordan spille spillet

# Krav og spesifikasjon

**Hvilke krav vi har prioritert og hva vi har gjort siden forrige gang:**

### MVP (minimum viable product):  
1. Vise et spillebrett *(fullført)*
2. Vise spiller på spillbrettet  *(fullført)*
3. Flytte spiller i alle retninger  *(fullført)*
4. Fiender som går i mot spiller *(fullført)*
5. Spiller kan skyte automatisk *(fullført)*
6. Spiller kan dø/få mindre HP *(fullført)*
7. Fiender blir sterkere over tid *(fullført)*
8. Spillfigur kan få oppgraderinger underveis *(delvis fullført)*

**Tidligere MVP**
- Etter diskusjon og enighet i gruppen mener vi at disse punktene ikke er nødvendige for MVP. Men viktigere for å forbedre spillopplevelsen. Derfor prioriterer vi ikke disse punktene direkte i siste innlevering. 

9. Mål for spillbrett: få nok poeng
10. Låser opp nytt spillbrett/nytt nivå 
11. Start-skjerm ved oppstart. Kan kjøpe permanente oppgraderinger for opptjente penger. 

### Brukerhistorier vi har jobbet med denne obligen:
3. Som en spiller, vil jeg ha muligheten til å skyte mot forskjellige typer fiender, som blir sterkere, i tillegg til at jeg skal kunne bruke forskjellige våpen og ferdigheter, slik at jeg kan utfordre meg selv og gjøre fremgang i spillet.

4. Som en spiller, vil jeg ha muligheten til å velge og oppgradere egenskaper for å bli sterkere, slik at jeg kan komme videre i spillet.


### Akseptansekriterier:
<em>Akseptanse kriterie for brukerhistorie 3:</em>

* Spillet skal tilby spilleren en mulighet for å skyte fiender. *(fullført)*

* Spillet skal tilby ulike typer oppgraderinger som en spiller kan skaffe seg, som f.eks.: rustning, våpen, eller tilbehør, for å styrke spilleren. *(Ikke fullført, men spiller blir sterkere jo fler fiender han dreper, kan samle coins)*

* Spillet skal tilby en klar oversikt over hva og hvor mye en oppgradering skal forbedre en spillers egenskaper.

<em>Akseptanse kriterie for brukerhistorie 4:</em>

* Spillet gir spilleren valget mellom flere forskjellige egneskaper å oppgradere, som f.eks.: rekkevidde på våpen, hastighet på spiller, etc. *(Spilleren får ikke valgmuligheter, men det oppgraderes etter antall kills)*

* Oppgraderingene gir en merkbar og meningsfull økning i spillerens presentasjoner, slik at spilleren føler på progresjon.

* Det skal være to forskjellige typer oppgraderinger, enten på spiller, eller våpen. *(Både spiller og våpen blir bedre etter antall kills, spiller får mer liv når det plukkes opp coins)*

# Produkt kode 

I README ligger:

- klassediagramm

**Commits + Gruppedynamikk** 
- Vi har kanskje noe ujevne commits, men vi opplever selv at alle på gruppen bidrar like mye. Den ulike mengden kommer av ulike faktorer som at noen liker å gjøre seg helt ferdig med en feature før de psuher til git, mens andre liker å pushe underveis (noe som fører til flere commits). I tillegg har Beate fått ansvaret for å skrive møtereferater osv, men alle gruppemedlemmene er med på å utforme hva som skal stå i referatene. Så alle commits som omhandler obliger er et felles produkt, og ikke en enkelt persons arbeid. Gruppedynamikken og kommunikasjonen har fungert svært bra etter vi fikset de små kommunikasjonsproblemene vi hadde helt i starten (byttet fra discord til sms kommunikasjon). Vi har hatt et veldig godt arbeidsmiljø!

**Bugs**

- Fiender rendrer inne i scenery (men de kommer seg ut/blir ikke fanget, dermed egentlig ikke et problem vi har brukt tid på å fikse) 

**Linker til Trello**

* [TRELLO ISSUES](https://trello.com/b/QHYMXsMK/issues)

* [TRELLO TASKS](https://trello.com/b/0YPRkMZo/tasks)


**Roller**

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
    - Kundekontakt: kontakt med gruppeleder for å vite hva som forventes til hver obligatoriske innlevering

**Dette har vi fikset siden sist** 

*Spillfunksjoner vi har lagt til* 

*Enemies* 
- Enemies blitt poolable -> flere fiender blitt rendret
- Fiender tar skade og kan dø
- Fiender blir sterkere og raskere over tid  
- Fiender blir rendret oftere, slik at det blir vanskeligere å overleve
- Fiender rendres på en tilfeldig plass, ute av synsfeltet til player 
- Gjør mer skade på spiller over tid 

*Projectile* 
- Projectiles forsvinner når de kolliderer med noe 
- Projectiles gjør skade på fiende 
- Projectiles gjør mer skade over tid 
- Projectiles fortsetter å skyte i den retningen spilleren sist gikk, dette gjør det bedre/lettere å skite mot og treffe fiender
- Projectile Factory 

*HP*
- HP blir gradvis mindre under kontakt 
- Spiller kan dø

*Pickup* 
- Objekter som kan plukkes opp
- Dukker opp når enemies dør 
- Spiller får 1+ HP for hver mynt som plukkes opp 

*Spill logikk* 
- Laget killcount, og viser den på skjermen 
- Killcount styrer oppgraderingene til spiller, fiende og projectile
- Tid viser på skjermen, hvor lenge vi har overlevd/spillt
- Ryddet i koden 

*Screens* 
- laget instructions side

*Fra tilbakemeldinger på oblig:*
- Credits på grafikk og lyd
- Dokumentasjon av tester

