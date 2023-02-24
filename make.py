import argparse
from os import remove, system, path
import urllib.request
from shutil import rmtree as rmdir

## Compile functions
def mac_x64():
    # Cleanup build/mac
    print("Cleanup build/mac")
    cleanupDir = "build/mac"
    if path.exists(cleanupDir) and path.isdir(cleanupDir):
        rmdir(cleanupDir)

    # Download JDK
    print("Download OpenJDK for MacOS\n")
    urllib.request.urlretrieve(
        "https://cdn.azul.com/zulu/bin/zulu8.38.0.13-ca-jdk8.0.212-macosx_x64.zip",
        "zulu8.38.0.13-ca-jdk8.0.212-macosx_x64.zip"
    )

    # Package jar
    print("Package the jar:\n")
    if system("java -jar packr.jar --classpath desktop/build/libs/desktop-1.0.jar -- packr-mac_x64.config.json") != 0:
        print("Something went wrong when compiling packaginf the jar")
        print("Removing packr.jar")
        remove("packr.jar")
    print("\nSuccessfully packaged jar\n")

    # Remove JDK
    print("Removing OpenJDK for MacOS")
    remove("zulu8.38.0.13-ca-jdk8.0.212-macosx_x64.zip")

def linux_x64():
    # Cleanup build/linux64
    print("Cleanup build/linux64")
    cleanupDir = "build/linux64"
    if path.exists(cleanupDir) and path.isdir(cleanupDir):
        rmdir(cleanupDir)

    # Download JDK
    print("Download OpenJDK for Linux\n")
    urllib.request.urlretrieve(
        "https://cdn.azul.com/zulu/bin/zulu8.52.0.23-ca-jdk8.0.282-linux_x64.zip",
        "zulu8.52.0.23-ca-jdk8.0.282-linux_x64.zip"
    )

    # Package jar
    print("Package the jar:\n")
    if system("java -jar packr.jar --classpath desktop/build/libs/desktop-1.0.jar -- packr-linux_x64.config.json") != 0:
        print("Something went wrong when compiling packaginf the jar")
        print("Removing packr.jar")
        remove("packr.jar")
    print("\nSuccessfully packaged jar\n")

    # Remove JDK
    print("Removing OpenJDK for Linux")
    remove("zulu8.52.0.23-ca-jdk8.0.282-linux_x64.zip")

def windows_x64():
    # Cleanup build/windows64
    print("Cleanup build/windows64")
    cleanupDir = "build/windows64"
    if path.exists(cleanupDir) and path.isdir(cleanupDir):
        rmdir(cleanupDir)

    # Download JDK
    print("Download OpenJDK for Windows\n")
    urllib.request.urlretrieve(
        "https://cdn.azul.com/zulu/bin/zulu8.52.0.23-ca-jdk8.0.282-win_x64.zip",
        "zulu8.52.0.23-ca-jdk8.0.282-win_x64.zip"
    )

    # Package jar
    print("Package the jar:\n")
    if system("java -jar packr.jar --classpath desktop/build/libs/desktop-1.0.jar -- packr-windows_x64.config.json") != 0:
        print("Something went wrong when compiling packaginf the jar")
        print("Removing packr.jar")
        remove("packr.jar")
    print("\nSuccessfully packaged jar\n")

    # Remove JDK
    print("Removing OpenJDK for Windows")
    remove("zulu8.52.0.23-ca-jdk8.0.282-win_x64.zip")


## Main
## Construct the argument parser
ap = argparse.ArgumentParser()

## Add the arguments to the parser
ap.add_argument(
    "-a", "--arch",
    required=True,
    help="build an executable for the binary ['all', 'mac', 'linux', 'windows']",
    type=str
)
args = vars(ap.parse_args())


## Sanity checks
supportedArchs = ['all', 'mac', 'linux', 'windows']
if args['arch'] not in supportedArchs:
    print('"' + args['arch'] + '" is not a supported compile architecture')
    print("The supported architecture options are: ", end="")
    print(supportedArchs)
    exit(1)

## Download packr
print("Download the libGDX packr")
urllib.request.urlretrieve(
    "https://github.com/libgdx/packr/releases/download/4.0.0/packr-all-4.0.0.jar",
    "packr.jar"
)

## Compile libGDX desktop
print("Compile libGDX for desktop")
if system("./gradlew desktop:dist") != 0:
    print("Something went wrong when compiling libGDX for desktop")
    print("Removing packr.jar")
    remove("packr.jar")
print("\nSuccessfully compiled libGDX\n")

if args['arch'] == "mac":
    print("Compiling for MacOS x86_64:")
    mac_x64()
elif args['arch'] == "linux":
    print("Compiling for Linux/GNU x86_64:")
    linux_x64()
elif args['arch'] == "windows":
    print("Compiling for WindowsNT x86_64:")
    windows_x64()
elif args['arch'] == "all":
    print("Compiling for all supported architectures:")
    mac_x64()
    linux_x64()
    windows_x64()

## Cleanup
print("Remove packr.jar")
remove("packr.jar")