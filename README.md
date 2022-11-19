# Don't Starve Together - Fix for 17 Nov 2022 mod update
This java repo is for repairing the game by extracting old legacy.bin mods and then simply placing them in the correct folder with the readOnly file attribute.

## Steps this repo takes
1. Takes three inputs namely Mod-Folder (a directory), Correct-Folder (a directory), Search-For-Item (a zip file or folder) >
2. Searches for all Search-For-Item instances >
3. Extracts all Search-For-Item instances into the Correct-Folder under the original Search-For-Item's parent folder name with "workshop-" as prefix >
4. Gives all extracted files and/or folders the read-only attribute (to prevent it from being removed by the game) >
5. Moves all Search-For-Item instances into a backup folder within Correct-Folder (to prevent the game from crashing when reading these files) >
6. Notifies user upon success or error of operation.
