$ErrorActionPreference = 'Stop'

$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
$sdkCandidates = @($env:ANDROID_SDK_ROOT, $env:ANDROID_HOME) |
    Where-Object { -not [string]::IsNullOrWhiteSpace($_) }

$localProperties = Join-Path $projectRoot 'local.properties'
if (Test-Path -LiteralPath $localProperties) {
    $sdkLine = Get-Content -LiteralPath $localProperties |
        Where-Object { $_ -like 'sdk.dir=*' } |
        Select-Object -First 1
    if ($sdkLine) {
        $sdkFromProperties = $sdkLine.Substring('sdk.dir='.Length).
            Replace('\:', ':').
            Replace('\\', '\')
        $sdkCandidates += $sdkFromProperties
    }
}

$adb = $sdkCandidates |
    ForEach-Object { Join-Path $_ 'platform-tools\adb.exe' } |
    Where-Object { Test-Path -LiteralPath $_ } |
    Select-Object -First 1

if (-not $adb) {
    $adbCommand = Get-Command adb -ErrorAction SilentlyContinue
    if ($adbCommand) {
        $adb = $adbCommand.Source
    }
}

if (-not $adb) {
    throw 'No se encontró adb. Configura ANDROID_SDK_ROOT, ANDROID_HOME o local.properties.'
}

$package = 'pe.edu.upeu.pharmamobil'
$activity = 'pe.edu.upeu.pharmamobil/.MainActivity'
$evidenceDir = $PSScriptRoot

function Start-CleanApp {
    & $adb shell am force-stop $package | Out-Null
    & $adb shell am start -n $activity | Out-Null
    Start-Sleep -Seconds 12
}

function Tap-And-Type([int]$x, [int]$y, [string]$text) {
    & $adb shell input tap $x $y
    Start-Sleep -Seconds 1
    if ($text.StartsWith('-')) {
        & $adb shell input keyevent 69
        & $adb shell input text $text.Substring(1)
    } else {
        & $adb shell input text $text
    }
    Start-Sleep -Seconds 2
}

function Save-Screenshot([string]$remoteName, [string]$localName) {
    & $adb shell screencap -p "/sdcard/$remoteName" | Out-Null
    & $adb pull "/sdcard/$remoteName" (Join-Path $evidenceDir $localName) | Out-Null
}

# Caso 2: nombre vacío.
Start-CleanApp
& $adb shell input tap 540 1110
Start-Sleep -Seconds 1
Save-Screenshot 'pharma_nombre_vacio.png' '02_validacion_nombre_vacio.png'

# Caso 3: precio no numérico.
Start-CleanApp
Tap-And-Type 540 500 'Paracetamol%s500%smg'
Tap-And-Type 540 710 'abc'
Tap-And-Type 540 920 '100'
& $adb shell input keyevent 4
Start-Sleep -Milliseconds 500
& $adb shell input tap 540 1110
Start-Sleep -Seconds 1
Save-Screenshot 'pharma_precio_invalido.png' '03_validacion_precio_invalido.png'

# Caso 4: stock negativo.
Start-CleanApp
Tap-And-Type 540 500 'Paracetamol%s500%smg'
Tap-And-Type 540 710 '8.50'
Tap-And-Type 540 920 '-5'
& $adb shell input keyevent 4
Start-Sleep -Milliseconds 500
& $adb shell input tap 540 1110
Start-Sleep -Seconds 1
Save-Screenshot 'pharma_stock_negativo.png' '04_validacion_stock_negativo.png'

# Caso 1: registro correcto.
Start-CleanApp
Tap-And-Type 540 500 'Paracetamol%s500%smg'
Tap-And-Type 540 710 '8.50'
Tap-And-Type 540 920 '100'
& $adb shell input keyevent 4
Start-Sleep -Milliseconds 500
& $adb shell input tap 540 1110
Start-Sleep -Seconds 1
Save-Screenshot 'pharma_registro_correcto.png' '05_registro_correcto.png'

Get-ChildItem -LiteralPath $evidenceDir -Filter '*.png' |
    Sort-Object Name |
    Select-Object Name, Length
