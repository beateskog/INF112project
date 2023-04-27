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

Vi diskuterte også vår MVP, og mener selv at punkt 1-6 er de absolutt viktigste for et fungerende spill, og oppnådd MVP. De resterende punktene synes vi er viktige for å ha et spill som faktisk er gøy/givende å spille, men ikke nødvendig for MVP. 

**Jobbet med i gruppetimen**
- Askild: Pause knapp  
- Kristian S: Killcount, enemies rendrer oftere 
- Beate: Fiender blir sterkere over tid 
- Tobias: Spiller kan dø 

## Gruppemøte 27.04.2023 (10.15-13.00)

Tilstede Kristian S, Askild og Beate.

Jobbet med individuelle ting, og sammarbeidet om å finne løsninger hvis noen hadde problemer. 

# Krav og spesifikasjon

**Hvilke krav vi har prioritert og hva vi har gjort siden forrige gang:**

### MVP (minimum viable product):  
1. Vise et spillebrett (fullført)
2. Vise spiller på spillbrettet  (fullført)
3. Flytte spiller i alle retninger  (fullført)
4. Fiender som går i mot spiller (fullført)
5. Spiller kan skyte automatisk (fullført)
6. Spiller kan dø/få mindre HP (ved kontakt med fiender)
7. Fiender blir sterkere over tid (fullført)
8. Spillfigur kan få oppgraderinger underveis 
9. Mål for spillbrett: få nok poeng
10. Låser opp nytt spillbrett/nytt nivå 
11. Start-skjerm ved oppstart. Kan kjøpe permanente oppgraderinger for opptjente penger. 

### Brukerhistorier vi har jobbet med denne obligen:
3. Som en spiller, vil jeg ha muligheten til å skyte mot forskjellige typer fiender, som blir sterkere, i tillegg til at jeg skal kunne bruke forskjellige våpen og ferdigheter, slik at jeg kan utfordre meg selv og gjøre fremgang i spillet.
4. Som en spiller, vil jeg ha muligheten til å velge og oppgradere egenskaper for å bli sterkere, slik at jeg kan komme videre i spillet.
5. Som en spiller, vil jeg ha muligheten til å låse opp nye spillbrett nivå, slik at jeg opplever variasjon og progresjon i spillet.

### Akseptansekriterier:
<em>Akseptanse kriterie for brukerhistorie 3:</em>
* Spillet skal tilby spilleren en mulighet for å skyte fiender.
* Spillet skal tilby ulike typer oppgraderinger som en spiller kan skaffe seg, som f.eks.: rustning, våpen, eller tilbehør, for å styrke spilleren.
* Spillet skal tilby en klar oversikt over hva og hvor mye en oppgradering skal forbedre en spillers egenskaper.

<em>Akseptanse kriterie for brukerhistorie 4:</em>
* Spillet gir spilleren valget mellom flere forskjellige egneskaper å oppgradere, som f.eks.: rekkevidde på våpen, hastighet på spiller, etc. 
* Oppgraderingene gir en merkbar og meningsfull økning i spillerens presentasjoner, slik at spilleren føler på progresjon.
* Det skal være to forskjellige typer oppgraderinger, enten på spiller, eller våpen.

<em>Akseptanse kriterie for brukerhistorie 5:</em>
* Spillet har flere ulike spillbrett nivåer spilleren kan låse opp ved å fullføre tidligere nivåer, eller andre oppgaver i spillet.
* Nye spillbrett nivå byr på økt utfordring og krever at spilleren bruker nye strategier, eller ferdigheter for å fullføre dem.
* Hvert nye nivå oppleves som spennende og utfordrende for å gi spilleren motivasjon til å fortsette å spille.

# Produkt kode 

**Bugs**
Fiender rendrer inne i scenery, og utenfor map. 

**Dette har vi fikset siden sist** 


