Write-Host ""
Write-Host "=== [LITTLE BROTHER v3 : SMART EXTRACTOR] ===" -ForegroundColor Yellow

# --- LOGIQUE D'ANCRAGE ---
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Definition
if ($scriptPath -like "*\TOOLS") { $rootPath = Split-Path -Parent $scriptPath }
else { $rootPath = $scriptPath }
Set-Location $rootPath

# Demande du dossier cible
$targetFolder = Read-Host "Entrez l'UID du dossier a scanner (ex: 10_10_00_00)"
$targetPath = Join-Path "BRIQUETHEQUE" $targetFolder
$reportFile = "LittleBrotherReport_$targetFolder.txt"

if (-not (Test-Path $targetPath)) {
    Write-Host "❌ ERREUR : Le dossier $targetPath est introuvable !" -ForegroundColor Red
    exit
}

$script:reportContent = @("LITTLE BROTHER REPORT - $targetFolder", "=========================================")

function Extract-Content {
    param ([string]$CurrentPath)
    $excludeExt = @(".jar", ".class", ".png", ".jpg", ".exe")
    $items = Get-ChildItem $CurrentPath -Recurse | Where-Object { 
        $_.FullName -notmatch "\\target\\" -and 
        -not $_.PSIsContainer -and 
        $excludeExt -notcontains $_.Extension 
    }

    foreach ($file in $items) {
        $relativePath = $file.FullName.Replace($rootPath, "")
        Write-Host "Extraction : $relativePath" -ForegroundColor Gray
        $script:reportContent += ""
        $script:reportContent += "--- FILE_START: $relativePath ---"
        $script:reportContent += Get-Content $file.FullName -Raw -Encoding UTF8
        $script:reportContent += "--- FILE_END ---"
    }
}

Extract-Content -CurrentPath $targetPath
$script:reportContent | Out-File -FilePath $reportFile -Encoding utf8

Write-Host "=========================================" -ForegroundColor Yellow
Write-Host "RAPPORT GENERE : $reportFile" -ForegroundColor Green
Write-Host ""