
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {

    public static void main(String[] args) {
        String saveDirPath = "D://Games/savegames/";
        String zipNameFile = "zip.zip";

        List<GameProgress> gameProgress = Arrays.asList(
                new GameProgress(112, 54, 12, 252.5),
                new GameProgress(146, 23, 10, 262.5),
                new GameProgress(136, 76, 17, 272.5)
        );

        saveGameProgressObj(gameProgress, saveDirPath);
        zipFiles(saveDirPath, gameProgress, zipNameFile);
        filesDelete(saveDirPath);
    }

    public static void saveGameProgressObj(List<GameProgress> gameProgress, String
            saveDirPath) {
        for (int i = 0; i < gameProgress.size(); i++) {
            String nameFileSave = "gameProgress" + (i + 1) + ".dat";
            String pathFileSave = saveDirPath + nameFileSave;
            saveGame(pathFileSave, gameProgress.get(i));

        }
    }

    public static void saveGame(String saveDirPath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(saveDirPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);

        } catch (Exception e) {
            System.out.println(e.getMessage(
            ));
        }
    }

    public static void zipFiles(String saveDirPath, List<GameProgress> gameProgress, String zipNameFile) {

        try (FileOutputStream fos = new FileOutputStream(saveDirPath + "zip.zip");
             ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (int i = 0; i < gameProgress.size(); i++) {
                String nameFileSave = "gameProgress" + (i + 1) + ".dat";
                File fileZip = new File(saveDirPath + nameFileSave);
                try (FileInputStream fis = new FileInputStream(fileZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileZip.getName());
                    zout.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    System.out.println("Файл \"" + nameFileSave + "\" добавлен в архив " + "\"" + "zip.zip" + "\"");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void filesDelete(String saveDirPath) {
        Arrays.stream(new File(saveDirPath).listFiles())
                .filter(x -> !x.getName().contains("zip.zip"))
                .forEach(File::delete);
        System.out.println("Файлы не лежащие в архиве удалены из " + "\"" + saveDirPath + "\"");
    }

}
