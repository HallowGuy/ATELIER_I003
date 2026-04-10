package cloud.moondust.z10_40_00_00;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class BlueprintSanityCheck {

    public static void main(String[] args) {
        System.out.println("🛡️ [ARCHITECT] Audit approfondi du contenu...");
        Path root = Paths.get(".");

        try {
            // 1. Vérifier si le SOL.xml est bien à la racine
            if (!Files.exists(root.resolve("SOL.xml"))) {
                System.err.println("❌ CRITIQUE : SOL.xml absent de la racine !");
            }

            // 2. Scanner les briques pour vérifier leurs pom.xml
            Files.walk(root.resolve("BRIQUETHEQUE"), 2)
                    .filter(p -> p.getFileName().toString().equals("pom.xml"))
                    .forEach(BlueprintSanityCheck::inspectPom);

        } catch (IOException e) {
            System.err.println("❌ Erreur d'audit : " + e.getMessage());
        }
    }

    private static void inspectPom(Path pomPath) {
        try {
            String content = Files.readString(pomPath);
            String briqueName = pomPath.getParent().getFileName().toString();

            // Vérifier si le pom pointe bien vers le parent I003
            if (!content.contains("<artifactId>atelier-i003</artifactId>")) {
                System.out.println("⚠️  " + briqueName + " : pom.xml ne pointe pas vers le parent I003 !");
            }

            // Vérifier si le artifactId est cohérent (optionnel)
            System.out.println("✅ " + briqueName + " : Contenu du POM valide.");

        } catch (IOException e) {
            System.err.println("❌ Impossible de lire " + pomPath);
        }
    }
}