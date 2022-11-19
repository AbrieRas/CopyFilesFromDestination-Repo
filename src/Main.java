import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String modDirectory = "C:\\Users\\user-pc\\Downloads\\SteamLibrary\\steamapps\\workshop\\content\\322330",
                copyToDirectory = "C:\\Users\\user-pc\\Desktop\\FilesToEdit\\",
                searchForFile = "scripts";

        var modFiles = new ModFiles();
        modFiles.findModFiles(modDirectory, copyToDirectory, searchForFile);
        modFiles.createFoldersAndGuide();
        modFiles.copyModFilesToPath();
//        modFiles.debug();
    }
}

class ModFiles {
    private final ArrayList<String> modFilePaths = new ArrayList<>(),
            copiedModFolderNames = new ArrayList<>();
    private final ArrayList<File> copiedModFilePaths = new ArrayList<>();
    private String modDirectory, copyToDirectory, searchForFile;

    public void findModFiles(String modFilePath, String copyToPath, String searchForFile) {
        this.modDirectory = modFilePath;
        this.copyToDirectory = copyToPath;
        this.searchForFile = searchForFile;

        File parentFolder = new File(this.modDirectory);
        for (File childFolder : Objects.requireNonNull(parentFolder.listFiles())) {

            String outerChildName = childFolder.getName();
            for (File childChildFolder : Objects.requireNonNull(childFolder.listFiles())) {

                String innerChildName = childChildFolder.getName();
                if (innerChildName.contains(this.searchForFile)) {

                    this.modFilePaths.add(childFolder.getAbsolutePath() + "\\");
                    this.copiedModFilePaths.add(childChildFolder);
                    this.copiedModFolderNames.add(this.copyToDirectory + "workshop-" + outerChildName + "\\");
                }
            }
        }
    }

    public void copyModFilesToPath() {
        try {

            Path pathIn, pathOut;
            for (int i = 0; i < this.modFilePaths.size(); i++) {

                for (File parentFile : this.copiedModFilePaths) {

                    for (File childFile : Objects.requireNonNull(parentFile.listFiles())) {

                        // copyFromPath\2325441848\behaviours
                        pathIn = Paths.get(this.modFilePaths.get(i) + "\\" + this.searchForFile + "\\" + childFile.getName());

                        // copyToPath\workshop-2325441848\behaviours
                        pathOut = Paths.get(this.copiedModFolderNames.get(i) + "\\" + childFile.getName());

                        Files.copy(pathIn,
                                pathOut,
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            System.out.println("Successfully copied files to " + this.copyToDirectory);
        } catch (IOException e) {
            System.out.println("There seems to be an error\n"
                    + e.getMessage());

            e.printStackTrace();
        }
    }

    public void createFoldersAndGuide() {
        try {
            // Folders
            for (int i = 0; i < this.modFilePaths.size(); i++) {
                Path path = Paths.get(this.copiedModFolderNames.get(i));
                if (!Files.isDirectory(path)) Files.createDirectory(path);
            }

            System.out.println("Successfully created folders in " + this.modDirectory);
        } catch (IOException e) {
            System.out.println("There seems to be an error\n"
                    + e.getMessage());
        }
    }

    public void debug() {
        for (int i = 0; i < this.modFilePaths.size(); i++) {
            System.out.println("modFilePaths --> " + this.modFilePaths.get(i) + "\n"
                    + "copiedModFolderNames --> " + this.copiedModFolderNames.get(i) + "\n"
                    + "copiedModFilePaths --> " + this.copiedModFilePaths.get(i).getName() + "\n");
        }
    }
}