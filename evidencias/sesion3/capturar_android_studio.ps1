Add-Type -AssemblyName System.Drawing
Add-Type -AssemblyName System.Windows.Forms
Add-Type @'
using System;
using System.Runtime.InteropServices;
public static class WindowCaptureNative {
    [StructLayout(LayoutKind.Sequential)]
    public struct RECT { public int Left; public int Top; public int Right; public int Bottom; }
    [DllImport("user32.dll")] public static extern bool SetForegroundWindow(IntPtr hWnd);
    [DllImport("user32.dll")] public static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);
    [DllImport("user32.dll")] public static extern bool GetWindowRect(IntPtr hWnd, out RECT rect);
}
'@

$studio = Get-Process studio64 |
    Where-Object { $_.MainWindowHandle -ne 0 } |
    Select-Object -First 1

if (-not $studio) {
    throw 'No se encontró una ventana visible de Android Studio.'
}

[WindowCaptureNative]::ShowWindow($studio.MainWindowHandle, 3) | Out-Null
[WindowCaptureNative]::SetForegroundWindow($studio.MainWindowHandle) | Out-Null
Start-Sleep -Seconds 2
[System.Windows.Forms.SendKeys]::SendWait('%1')
Start-Sleep -Seconds 2

$rect = New-Object WindowCaptureNative+RECT
[WindowCaptureNative]::GetWindowRect($studio.MainWindowHandle, [ref]$rect) | Out-Null
$width = $rect.Right - $rect.Left
$height = $rect.Bottom - $rect.Top

$bitmap = New-Object System.Drawing.Bitmap($width, $height)
$graphics = [System.Drawing.Graphics]::FromImage($bitmap)
$graphics.CopyFromScreen($rect.Left, $rect.Top, 0, 0, $bitmap.Size)
$path = Join-Path $PSScriptRoot '00_estructura_proyecto_android_studio.png'
$bitmap.Save($path, [System.Drawing.Imaging.ImageFormat]::Png)
$graphics.Dispose()
$bitmap.Dispose()
Get-Item -LiteralPath $path | Select-Object FullName, Length
