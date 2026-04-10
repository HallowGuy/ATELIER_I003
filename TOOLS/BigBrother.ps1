Write-Host ""
Write-Host "=== [BIG BROTHER v3 : RECURSIVE ROOT SCAN] ===" -ForegroundColor Cyan

# --- LOGIQUE D'ANCRAGE ---
# On récupère le dossier où se trouve le script
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Definition
# On définit la racine : Si le script est dans TOOLS, on remonte d'un cran
if ($scriptPath -like "*\TOOLS") { $rootPath = Split-Path -Parent $scriptPath }
else { $rootPath = $scriptPath }

Set-Location $rootPath
$reportFile = "BigBrotherReport.txt"
$script:reportContent = @("Atelier_I003")

function Scan-Folder {
    param ([string]$CurrentPath, [int]$Level)
    $exclude = @("target", ".git", ".idea", "bin", "obj", "TOOLS")
    if (-not (Test-Path $CurrentPath)) { return }

    $items = Get-ChildItem $CurrentPath | Where-Object { $exclude -notcontains $_.Name } | Sort-Object Name
    foreach ($item in $items) {
        $prefix = ""
        for ($i = 0; $i -lt $Level; $i++) { $prefix += "-+" }
        $script:reportContent += "$prefix$($item.Name)"
        if ($item.PSIsContainer) {
            Scan-Folder -CurrentPath $item.FullName -Level ($Level + 1)
        }
    }
}

Scan-Folder -CurrentPath "." -Level 1
$script:reportContent | Out-File -FilePath $reportFile -Encoding utf8

Write-Host "Scan effectue depuis : $rootPath" -ForegroundColor Gray
Write-Host "RAPPORT GENERE : $reportFile" -ForegroundColor Green
Write-Host ""