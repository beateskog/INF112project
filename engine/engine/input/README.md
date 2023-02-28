# Inputs
This is the input folder where different input profiles
are stored.   

## Index
The `index.json` file is the index that tells the engine what
profiles exist in this folder. The entries in the "profiles"
field must be strings with the basename of the profile file.

## Profiles
Each profile `.json` file must have a name and values for
which buttons activate them.   
<br>
For keyboards a valid value is a string with the name of the
return value from the `Keys.toString(int)` method when you
supply a `Keys.???`. An example is:
```java
Keys.toString(Keys.SPACE) // returns "Space"
```
<br>
For controllers a valid value is a string from this list of
valid controller buttons.  

- A
- B
- X
- Y
- DpadUp
- DpadDown
- DpadLeft
- DpadRight
- L1
- R1
- LeftStick
- RightStick
- Start

So an example of a profile `select.json` is:
```json
{
    "name": "select",
    "keyboard": [
        "Enter",
        "Space"
    ],
    "controller": [
        "A"
    ]
}
```