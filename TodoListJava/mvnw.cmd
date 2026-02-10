@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.2.0
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PSMODULEP_SAVE__=%PSModulePath%
@SET PSModulePath=
@FOR /F "usebackq tokens=1* delims==" %%A IN (`powershell -noprofile "& {$scriptDir='%~dp0'; $script='%__MVNW_ARG0_NAME__%'; icm -ScriptBlock ([Scriptblock]::Create((Get-Content -Raw '%~f0LET'))) -NoNewScope}"`) DO @(
  IF "%%A"=="MVN_CMD" (set __MVNW_CMD__=%%B) ELSE IF "%%B"=="" (echo.%%A) ELSE (echo.%%A=%%B)
)
@SET PSModulePath=%__MVNW_PSMODULEP_SAVE__%
@SET __MVNW_PSMODULEP_SAVE__=
@SET __MVNW_ARG0_NAME__=
@SET MVNW_USERNAME=
@SET MVNW_PASSWORD=
@SET MVNW_VERBOSE=
@IF NOT "%__MVNW_CMD__%"=="" (%__MVNW_CMD__% %*)
@echo Cannot start maven from wrapper >&2 && exit /b 1
@GOTO :EOF
: end batch / begin powershell #>

$ErrorActionPreference = "Stop"
if ($env:MVNW_VERBOSE -eq "true") {
    $VerbosePreference = "Continue"
}

$distributionUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip"
$distributionSha256 = "6eedd2cae3626d6ad3a5c9ee324bd265853d64297f07f033430c71f2f2d0b7c5"

$MVNW_REPOURL = $env:MVNW_REPOURL
if (-not $MVNW_REPOURL) {
    $MVNW_REPOURL = "https://repo.maven.apache.org/maven2"
}

$wrapperVersion = "3.2.0"
$wrapperSha256 = "e63a53cfb9c4d291eff3db1a1bae25d1f7ae53c20a36e9826e7b49e45696cfbc"
$wrapperJarPath = "$PSScriptRoot\.mvn\wrapper\maven-wrapper.jar"

function Get-Env-Or-Default([string]$Name, [string]$Default) {
    $val = [Environment]::GetEnvironmentVariable($Name)
    if ([string]::IsNullOrEmpty($val)) { return $Default }
    return $val
}

$MAVEN_PROJECTBASEDIR = Get-Env-Or-Default "MAVEN_BASEDIR" $PSScriptRoot
$MAVEN_USER_HOME = Get-Env-Or-Default "MAVEN_USER_HOME" "$env:USERPROFILE\.m2"

function Get-Maven-Download-Dir {
    $wrapperDir = Get-Env-Or-Default "MAVEN_USER_HOME" "$env:USERPROFILE\.m2"
    return "$wrapperDir\wrapper\dists"
}

function Get-Distribution-Url {
    $wrapperProperties = "$MAVEN_PROJECTBASEDIR\.mvn\wrapper\maven-wrapper.properties"
    if (Test-Path $wrapperProperties) {
        $content = Get-Content $wrapperProperties -Raw
        if ($content -match "distributionUrl\s*=\s*(.+)") {
            return $matches[1].Trim()
        }
    }
    return $distributionUrl
}

function Get-Distribution-Name([string]$url) {
    $uri = [System.Uri]$url
    return [System.IO.Path]::GetFileNameWithoutExtension($uri.Segments[-1])
}

function Download-File([string]$url, [string]$destPath) {
    Write-Verbose "Downloading from: $url"
    Write-Verbose "Downloading to: $destPath"
    
    $destDir = [System.IO.Path]::GetDirectoryName($destPath)
    if (-not (Test-Path $destDir)) {
        New-Item -ItemType Directory -Path $destDir -Force | Out-Null
    }
    
    try {
        $webClient = New-Object System.Net.WebClient
        if ($env:MVNW_USERNAME -and $env:MVNW_PASSWORD) {
            $credentials = [System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes("${env:MVNW_USERNAME}:${env:MVNW_PASSWORD}"))
            $webClient.Headers.Add("Authorization", "Basic $credentials")
        }
        $webClient.DownloadFile($url, $destPath)
    } catch {
        Write-Error "Failed to download $url : $_"
        throw
    }
}

function Extract-Zip([string]$zipPath, [string]$destDir) {
    Write-Verbose "Extracting: $zipPath to $destDir"
    if (-not (Test-Path $destDir)) {
        New-Item -ItemType Directory -Path $destDir -Force | Out-Null
    }
    Expand-Archive -Path $zipPath -DestinationPath $destDir -Force
}

# Main
$url = Get-Distribution-Url
$distName = Get-Distribution-Name $url
$downloadDir = Get-Maven-Download-Dir
$mavenHome = "$downloadDir\$distName"
$zipPath = "$downloadDir\$distName.zip"

if (-not (Test-Path "$mavenHome\bin\mvn.cmd")) {
    Write-Host "Downloading Maven distribution..."
    Download-File $url $zipPath
    Write-Host "Extracting Maven distribution..."
    Extract-Zip $zipPath $downloadDir
    
    # Maven extracts to apache-maven-x.x.x folder
    $extractedDir = Get-ChildItem -Path $downloadDir -Directory | Where-Object { $_.Name -like "apache-maven-*" } | Select-Object -First 1
    if ($extractedDir -and $extractedDir.FullName -ne $mavenHome) {
        if (Test-Path $mavenHome) { Remove-Item $mavenHome -Recurse -Force }
        Move-Item $extractedDir.FullName $mavenHome
    }
}

$env:MAVEN_HOME = $mavenHome
$env:M2_HOME = $mavenHome

$mvnCmd = "$mavenHome\bin\mvn.cmd"
Write-Output "MVN_CMD=$mvnCmd"
