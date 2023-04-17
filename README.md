 [![coverage report](https://git.app.uib.no/mavenless/rona-survivors/badges/main/coverage.svg)](https://git.app.uib.no/mavenless/rona-survivors)

README.md-filen skal (gjennom hele semesteret) holdes oppdatert med:

navn på team-medlemmene, teamet og prosjektet, samt gruppenummer
kort beskrivelse av spillet og hvordan det brukes (f.eks. hvilke tastetrykk som gjør hva).
hvordan koden kjøres.
hvor evt. grafikk/lyd-ressurser er hentet fra.

# INF112 Project – *Rona Survivors*

* Team: *Mavenless* (Gruppe 4.1): *Askild Heiret, Beate Skogvik, Kristian Fredrik Rognsvaag, Kristian Skeie, Tobias Husebø*
* Lenke til GitLab/Trello/etc.

## Om spillet
Du er en lege som må kjempe deg gjennom alt en pandemi kan kaste mot deg. 

## Kjøring
* Kompileres med `./make.py -d` (alias/helper for abstracion)
* Kjøres med `./make.py -r` (evt. `./gradlew desktop:run`)
* Krever Java 8

## Kjente feil
* Projectile colliding with other projectiles 

## Credits
Ingenting hittil 😎
<!-- Tileset fra https://opengameart.org/content/2d-cave-platformer-tileset-16x16 -->


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


Links to Trello: 

[TRELLO ISSUES](https://trello.com/b/QHYMXsMK/issues)

[TRELLO TASKS](https://trello.com/b/0YPRkMZo/tasks)


## Klassediagram
```mermaid
classDiagram
    DesktopLauncher <-- RonaSurvivors
    RonaSurvivors <-- MainMenu
    MainMenu <--> Shop
    MainMenu <-- GameScreen
    InputUtil --> RonaSurvivors
    InputUtil --> MainMenu
    UiHandler --> MainMenu
    LevelUtil --> GameScreen

    InputIndex --> InputUtil
    InputProfile --> InputUtil

    UiStyle --> UiHandler
    UiFont --> UiHandler

    CollisionBits --> LevelUtil

    %% Startup
    class DesktopLauncher {
        +void main()$
    }

    class RonaSurvivors {
        +SpriteBatch batch
        +InputUtil input

        +void create()
        +void render()
        +void dispose()
    }

    %% Screens
    class MainMenu {
        #RonaSurvivors game
        -ExtendViewport extendViewport
        -InputUtil inputUtil
        -Stage stage
        -Table table
        -Texture backgroundTexture

        +MainMenu(final RonaSurvivors game)
        +void show()
        +void render(float delta)
        +void resize(int width, int height)
        +void pause()
        +void resume()
        +void hide()
        +void dispose()
    }

    class Shop {
        #RonaSurvivors game
        -OrthographicCamera camera
        -Stage stage
        -Table table

        +Shop(final RonaSurvivors game)
        +void show()
        +void render(float delta)
        +void resize(int width, int height)
        +void pause()
        +void resume()
        +void hide()
        +void dispose()
    }

    class GameScreen {
        #RonaSurvivors game
        -LevelUtil levelUtil
        -ExtendViewport extendViewport
        -Box2DDebugRenderer box2dDebugRenderer
        -Stage stage
        -Player player
        -HP_bar hp_bar
        -Enemy enemy
        -Projectile projectile
        -TextureAtlas playerAtlas
        -TextureAtlas tmpEnemyAtlas
        -float timeSinceLastShot
        -float deltaTime
        -boolean fired
        +#long startTime
        -#ArrayList~Projectile~ activeProjectiles
        -#Pool~Projectile~ projectilePool

        +GameScreen(final RonaSurvivors game)
        +void show()
        -void defineEnemy()
        -float degreeOffset(float val, float offset)
        -void update()
        +void render(float delta)
        +TextureAtlas getAtlas()
        +void removeProjectile(Projectile projectile)
        +List~Projectile~ getProjectiles()
        +HP_bar getHp_bar()
        +void resize(int width, int height)
        +void pause()
        +void resume()
        +void hide()
        +void dispose()
    }

    %% Utils
    class InputUtil {
        -Controller controller
        -ArrayList~Integer~ buttons
        -HashMap~Integer~ ~Boolean~ buttonOn
        -HashMap~Integer~ ~Boolean~ buttonJustOn
        -HashMap~Integer~ ~Boolean~ buttonJustOff
        -InputIndex inputIndex
        -ArrayList~InputProfile~ inputProfiles

        +InputUtil()
        -boolean buttonDown(int buttonCode)
        -boolean buttonUp(int buttonCode)
        -int controllerButtonMapping(String button)
        -InputProfile getInputProfile(String name)
        +void update()
        +boolean down(String profile)
        +boolean up(String profile)
        +float moveX()
        +float moveY()
        +void vibrate(int milliseconds, float strength)
    }

    class InputIndex {
        -float deadzone
        -List~String~ profiles
        +InputIndex()
        +float getDeadzone()
        +List~String~ getProfiles()
        +String toString()
    }

    class InputProfile {
        -String name
        -List~String~ keyboard
        -List~String~ controller

        +InputProfile()
        +String getName()
        +List~String~ getKeyboard()
        +List~String~ getController()
        +String toString()
    }

    class BitUtil {
        -byte[] bytes

        +BitUtil(byte[] bytes)
        +BitUtil(String string)
        +BitUtil(short value)
        +BitUtil(int value)
        
        +byte[] getBytes()
        +String toString()
        +short toShort()
        +int toInt()
        +byte[] toLengthBytes()
        +String toHex()
    }

    class UiHandler {
        -BitmapFont loadFont(String path, int size, Color color)$
        -BitmapFont loadFontWithBorder(String path, int size, Color color, Color borderColor, int borderSize)$
        +LabelStyle labelStyle(BitmapFont font)$
        +TextButtonStyle textButtonStyle(UiStyle style, BitmapFont font)$
    }

    class UiStyle {
        <<enumeration>>
        Debug
        +Skin getSkin()*
        +Drawable getButtonUp()*
        +Drawable getButtonDown()*
    }

    class UiFont {
        <<enumeration>>
        Pixelated
        +BitmapFont getFont(int size, Color color)*
        +BitmapFont getFontBorder(int size, Color color, Color borderColor, int borderSize)*
    }

    class LevelUtil {
        +TiledMap tiledMap
        +World world
        +OrthogonalTiledMapRenderer mapRenderer
        -int tileWidth
        -int tileHeight
        -int mapWidth
        -int mapHeight

        +void loadTileMap(String tilePath)
        +void render(OrthographicCamera camera)
        +void getLayers()
        +int getTileWidth()
        +int getTileHeight()
        +int getMapWidth()
        +int getMapHeight()
    }

    %% Game
    class CollisionBits {
        +short CATEGORY_PLAYER$
        +short CATEGORY_ENEMY$
        +short CATEGORY_PROJECTILE$
        +short CATEGORY_SCENERY$

        +short MASK_PLAYER$
        +short MASK_ENEMY$
        +short MASK_PROJECTILE$
        +short MASK_SCENERY$
    }
```