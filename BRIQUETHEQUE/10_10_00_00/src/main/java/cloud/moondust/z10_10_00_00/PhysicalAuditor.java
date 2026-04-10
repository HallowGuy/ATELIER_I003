package cloud.moondust.z10_10_00_00;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * MODULE : 10.10.00.00 (STRUCT_AUDITOR)
 * ROLE   : Garant de l'intégrité physique de la Briquéthèque.
 */
public class PhysicalAuditor {

    public static void main(String[] args) {
        System.out.println("\n[10.10.00.00] --- SCANNER DE STRUCTURE MOONDUST ---");
        Path briquethequePath = Paths.get("./BRIQUETHEQUE");

        try {
            auditRegistry(briquethequePath);
        } catch (IOException e) {
            System.err.println("❌ ECHEC CRITIQUE DU SCAN : " + e.getMessage());
        }
    }

    private static void auditRegistry(Path root) throws IOException {
        if (!Files.exists(root)) {
            System.out.println("⚠️ ALERTE : Dossier BRIQUETHEQUE manquant. Création requise.");
            return;
        }

        try (Stream<Path> stream = Files.list(root)) {
            stream.filter(Files::isDirectory)
                  .sorted()
                  .forEach(path -> {
                      String folderName = path.getFileName().toString();
                      // Vérification du pattern de nomenclature XX_XX_XX_XX
                      if (folderName.matches("^\\d{2}_\\d{2}_\\d{2}_\\d{2}$")) {
                          checkManifest(path, folderName);
                      } else {
                          System.out.println("🔴 [NOMENCLATURE HORS-NORME] : " + folderName);
                      }
                  });
        }
    }

    private static void checkManifest(Path folder, String uid) {
        Path manifestPath = folder.resolve("brick-manifest.json");
        if (Files.exists(manifestPath)) {
            System.out.println("🟢 [OK] " + uid + " -> Manifeste détecté.");
        } else {
            System.out.println("🟡 [ALERTE] " + uid + " -> Manifeste MANQUANT.");
        }
    }
}