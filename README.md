# Easy Client — Fabric Mod

Press **Left Ctrl** in-game to open the Easy Client menu. Select your server's Minecraft version (1.21.1–1.21.11), type a command, and hit Send.

- Client-side only — no server install needed
- Works on any 1.21.x server
- Command history (↑/↓ arrows)
- Doesn't pause singleplayer

---

## How to Build & Upload to Modrinth

### What you need
- [JDK 21](https://adoptium.net/) — install this first
- Internet connection

### Step 1 — Get the Gradle wrapper JAR

This file bootstraps the build system. You need to download it once.

**Windows (PowerShell):**
```powershell
Invoke-WebRequest -Uri "https://github.com/gradle/gradle/raw/v8.8.0/gradle/wrapper/gradle-wrapper.jar" -OutFile "gradle\wrapper\gradle-wrapper.jar"
```

**Mac / Linux:**
```bash
curl -Lo gradle/wrapper/gradle-wrapper.jar \
  https://github.com/gradle/gradle/raw/v8.8.0/gradle/wrapper/gradle-wrapper.jar
```

### Step 2 — Build

**Windows:**
```
gradlew.bat build
```

**Mac / Linux:**
```bash
chmod +x gradlew
./gradlew build
```

Gradle will download everything it needs automatically (takes a few minutes first time).

Your compiled mod will be at:
```
build/libs/easyclient-1.0.0.jar
```
> Ignore the `-sources.jar` file — only upload the main jar.

### Step 3 — Upload to Modrinth

1. Go to [modrinth.com](https://modrinth.com) → log in → **Create a project**
2. Choose **Mod** as the project type
3. Fill in:
   - **Name:** Easy Client
   - **Project ID:** easyclient
   - **Summary:** Press Left Ctrl to open a command sender GUI. Works on all 1.21.x servers.
   - **License:** MIT
4. Go to the **Versions** tab → **Create version**
5. Upload `easyclient-1.0.0.jar`
6. Set:
   - **Version number:** 1.0.0
   - **Game versions:** tick 1.21, 1.21.1, 1.21.2, 1.21.3, 1.21.4 (all 1.21.x you want)
   - **Loaders:** Fabric
   - **Dependencies:** Add `fabric-api` → Required
7. Click **Save version** → your mod is live!

---

## Installing (for players)

1. Install [Fabric Loader 0.16+](https://fabricmc.net/use/installer/) for 1.21.1
2. Drop [Fabric API](https://modrinth.com/mod/fabric-api) into your `mods/` folder
3. Drop `easyclient-1.0.0.jar` into your `mods/` folder
4. Launch Minecraft with the Fabric profile
5. Press **Left Ctrl** in-game
