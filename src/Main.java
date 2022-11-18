import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String modDirectory = "C:\\Users\\wwwhe\\OneDrive\\Desktop\\first",
                copyToDirectory = "C:\\Users\\wwwhe\\OneDrive\\Desktop\\second\\";

        var modFiles = new ModFiles();
        modFiles.findModFiles(modDirectory, copyToDirectory);
        modFiles.copyModFilesToPath(copyToDirectory);
    }
}

class ModFiles {
    private final ArrayList<String> modFilePaths = new ArrayList<>(),
            copiedModFilePaths = new ArrayList<>(),
            copiedModFolderNames = new ArrayList<>(),
            guideInfo = new ArrayList<>();

    public void findModFiles(String modFilePath, String copyToPath) {
        File readFile = new File(modFilePath);
        // insert parent for loop here
        for (String name : Objects.requireNonNull(readFile.list())) {
            if (name.contains("some")) {
                modFilePaths.add(readFile.getAbsolutePath() + "\\" + name);
                copiedModFilePaths.add(copyToPath + "\\" + name);
                // copiedModFolderNames.add(copyToPath + "\\" + file.getName());
                // guideInfo.add(name + " --> " + file.getName());
            }
        }
    }

    public void copyModFilesToPath(String copyToPath) {
        try {
            Path pathIn, pathOut;
            for (int i = 0; i < modFilePaths.size(); i++) {
                pathIn = Paths.get(modFilePaths.get(i));
                pathOut = Paths.get(copiedModFilePaths.get(i));
                Files.copy(pathIn,
                        pathOut,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("Successfully copied files to " + copyToPath);
        } catch (IOException e) {
            System.out.println("There seems to be an error\n"
                    + e.getMessage());
        }
    }

    public void createFoldersAndGuide(String copyToPath) {
        try {
            StringBuilder guideText = new StringBuilder();
            // Folders
            for (int i = 0; i < modFilePaths.size(); i++) {
                Path path = Paths.get(copiedModFolderNames.get(i));
                Files.createDirectory(path);
                guideText.append(guideInfo.get(i)).append("\n");
            }

            // Guide
            FileWriter myWriter = new FileWriter(copyToPath + "Guide.txt");
            myWriter.write(String.valueOf(guideText));
            myWriter.close();
            System.out.println("Successfully created folders and guide in " + copyToPath);
        } catch (IOException e) {
            System.out.println("There seems to be an error\n"
                    + e.getMessage());
        }
    }
}