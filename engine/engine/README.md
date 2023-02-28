# Engine folder
This is the folder where engine "assets" are
stored. This is differentiated from the standard
"assets" folder because it is supposed to have
resource files like images, audio, music, tiles,
levels, etc. While the engine folder on the other
hand should have engine specific resources like 
input profiles and the icon of the executable.

## BTW
This should be the root folder for the engine, since
we could encounter folder/file conflicts if both the
"assets" and "engine" folder was a libGDX internal
root. This means to access for example the "input"
folder, you have to use the path `engine/input/`
instead of just `input/`.