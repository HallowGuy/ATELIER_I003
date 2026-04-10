package cloud.moondust.z10_40_00_00;

import java.io.IOException;
import java.nio.file.*;

public class BrickGenerator {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("❌ Erreur : Précisez le nom de la brique (ex: B2_DATA)");
            return;
        }

        String brickName = args[0];
        String artifactId = brickName.toLowerCase().replace("_", "-");
        String packageName = brickName.toLowerCase().split("_")[0];

        try {
            // 1. Création de l'arborescence
            Path root = Paths.get("BRIQUETHEQUE", brickName);
            Path javaPath = root.resolve("src/main/java/cloud/moondust/" + packageName);
            Files.createDirectories(javaPath);
            Files.createDirectories(root.resolve("src/main/resources"));

            // 2. Génération du pom.xml local
            String pomTemplate = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>
                    <parent>
                        <groupId>cloud.moondust</groupId>
                        <artifactId>atelier-i003</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <relativePath>../../pom.xml</relativePath>
                    </parent>
                    <artifactId>%s</artifactId>
                </project>
                """.formatted(artifactId);

            Files.writeString(root.resolve("pom.xml"), pomTemplate);

            System.out.println("✅ [ARCHITECT] Brique " + brickName + " imprimée avec succès.");
            System.out.println("👉 Pense à l'ajouter dans le <modules> du pom.xml racine !");

        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la génération : " + e.getMessage());
        }
    }
}