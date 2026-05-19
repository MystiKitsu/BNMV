[![](https://img.shields.io/badge/style-NType-ff6b6b?style=for-the-badge&logo=google-fonts)](https://fonts.google.com/specimen/JetBrains+Mono)

# 🔥 Better Nothing Music Visualizer

<img 
  src="https://img.shields.io/github/downloads/Aleks-Levet/better-nothing-music-visualizer/total?style=for-the-badge&logo=github&label=Total%20app%20downloads%20from%20github:&color=ff0000&labelColor=000000"
  style="height:40px;">
  
[<img height="80" alt="Get it on GitHub" src="./.github/assets/get-it-on-github.png" />](https://github.com/Aleks-Levet/better-nothing-music-visualizer/releases)
   
# NEW LICENCE, PLEASE READ IT

## 🌐 Read this in other languages: 
🇮🇳 [हिन्दी](README_HI.md)
🇮🇳 [Marathi](README-MR.md)
🇹🇷 [Türkçe](README_TR.md)

## 🎉 What's this?
We have successfully moved from the simple Python script to a powerful Android app! It grabs the live audio stream from your device using **Media Projection** and processes it directly into the glyphs with 60 FPS and 12-bit depth!

## 🤔 Why this project?
For a lot of people (including me), the *stock Glyph Music Visualiastion provided by Nothing* feels random.  
Even if it technically isn't, the visual response to music just isn't very obvious. On top of that, the feature isn't really using the full potential of the Glyph Interface. So that's why I made my own visualizer!

## ⚖️ Stock vs Better Music Visualizer
| Feature | Nothing Stock | **Better Music Visualizer** |
| :--- | :--- | :--- |
| **Light levels** | ~2-bit depth (3 light levels) | **12-bit depth (4096 light levels)** |
| **Frame Rate** | ~25 FPS | **60 FPS** |
| **Precision** | Feels random, it's hard to acually see how it's synced | **Uses FFT analysis to precisely determine the intensity of each light** |
| **Zones** | Standard, full physical glyphs are used | **Each glyph segment and sub-zone is used and controlled independently** |
| **Visualisation method** | Real-time only | **Realtime with down to 20ms latency, or pre-processed audio files** |

## 🎬 [Video demos and examples](https://github.com/Aleks-Levet/better-nothing-music-visualizer/blob/main/Demo-video-examples.md)

### See the difference in action! [**Click here to easily browse our video demos!**](https://github.com/Aleks-Levet/better-nothing-music-visualizer/blob/main/Demo-video-examples.md)

## 📲 Supported Nothing Phone Models
**Currently these models are supported:**
- Nothing phone (1) 
  - Needs glyph debug mode **ON** for the app, set through an *ADB command*: `adb shell settings put global nt_glyph_interface_debug_enable 1`. This will be fixed once Nothing gives us their API keys!
- Nothing phone (2)
- Nothing phone (2a)
- Nothing phone (2a plus)
- Nothing phone (3a)
- Nothing phone (3a pro)
- Nothing Phone (4a)

**partial support:**
- Nothing Phone (4a) pro
- Nothing phone (3) 


### ⚙️ How it works (technically)
- A high quality audio stream is captured
- **FFT (Fast Fourier Transform)** is used to analyze frequencies in a **20 ms window** for each **16.666 ms frame** (60 FPS), making the visualization more accurate
- **Frequency ranges** for each glyph zone are defined in `zones.config` and are fully customizable.
- The **brightness** of each glyph is defined by the **peak magnitude** found in its assigned frequency range  
  This measures how loud different frequency "zones" are
- **Downward-only smoothing** is applied to make the animation smoother while preserving responsiveness (this is the secret sauce)
- Then it's ready to be displayed on the glyphs!

## 🛠️ Presets
The visualizer's behavior, from frequency ranges to animation smoothing, is entirely controlled by the `zones.config` file. Whether you want to tweak existing presets or add support for a new phone model, everything is customizable through this single configuration file.
### 📖 [**Detailed zones.config Documentation**](ZONES_CONFIG.md)

## 📖 How to use the App?
1. **Download the latest APK** from the releases.
2. **Grant Permissions**: The app needs Screen Capture (Media Projection) and Notification access.
3. **Start Visualizing**: Hit the "Start" button and play music from any app!
4. **Adjust Latency**: If the lights aren't perfectly synced with your Bluetooth speaker or headphones, use the **Audio** tab to add or remove delay.
5. **Change Presets**: Explore different visualization styles in the **Glyphs** tab, and tune the values to your liking!

## 📖 How to use the python script?
We made a detailed wiki page which explains the installation, usage, configuration files in detail and a troubleshooting section. You can also find out how to make new presets(not yet tho). [Just click here!](https://github.com/Aleks-Levet/better-nothing-music-visualizer/wiki)

## 🤝 Community
You want to talk or discuss? *Bugs, feature requests?* [**Feel free to jump in and join us in the official discord thread in the Nothing server!**](https://discord.com/channels/930878214237200394/1292066365640978543)

## 🏗️ Contributing
Come and help us! Contributions are very welcome!
You can:
- Open issues
- Submit pull requests
- Suggest improvements
- Experiment with new visualization ideas
- Create new presets
- Disscuss with the developpers

##  🔒 Security
**The link to the VirusTotal scan can be found here:**  
https://www.virustotal.com/gui/url/c92c1ff82b56eb60bfd1e159592d09f949f0ea2d195e01f7f5adbef0e0b0385b?nocache=1

### © Credits
#### Here are the people involved in this project
<table>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/Aleks-Levet">
        <img src="https://github.com/Aleks-Levet.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>Aleks-Levet</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Founder, Coordinator & Developer</strong><br/>
      Main idea and owner.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/oliver-lebaigue-bright-bench">
        <img src="https://github.com/oliver-lebaigue-bright-bench.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>Oliver Lebaigue</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Android Developer</strong>
      Enhancing the app + various nice additions
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/rKyzen">
        <img src="https://github.com/rKyzen.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>rKyzen</b></sub><br/>
        <i>(Shivank Dan)</i>
      </a>
    </td>
    <td align="left">
      <strong>Android App Developer</strong><br/>
      Implemented the real-time music stream.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/Nicouschulas">
        <img src="https://github.com/Nicouschulas.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>Nicouschulas</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Wiki & Documentation</strong><br/>
      Readme & Wiki enhancements.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/SebiAi">
        <img src="https://github.com/SebiAi.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>SebiAi</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Glyph Specialist</strong><br/>
      Glyphmodder and glyph related help.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/Earendel-lab">
        <img src="https://github.com/Earendel-lab.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>Earendel</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Documentation</strong><br/>
      Readme enhancements.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/Luke20YT">
        <img src="https://github.com/Luke20YT.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>あけ なるかみ</b></sub><br/>
        <i>(Luke20YT)</i>
      </a>
    </td>
    <td align="left">
      <strong>Integrator</strong><br/>
      Creating a Music app with an integration with this script.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/Interlastic">
        <img src="https://github.com/Interlastic.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>Interlastic</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Tools</strong><br/>
      Discord Bot to try the script easily (deprecated).
    </td>
  </tr>
</table>

### ⭐ Star History
<a href="https://www.star-history.com/?repos=Aleks-Levet%2Fbetter-nothing-music-visualizer&type=date&legend=top-left">
 <picture>
   <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/chart?repos=Aleks-Levet/better-nothing-music-visualizer&type=date&theme=dark&legend=top-left" />
   <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/chart?repos=Aleks-Levet/better-nothing-music-visualizer&type=date&legend=top-left" />
   <img alt="Star History Chart" src="https://api.star-history.com/chart?repos=Aleks-Levet/better-nothing-music-visualizer&type=date&legend=top-left" />
 </picture>
</a>

### 💻 The Development Team

<table>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/Aleks-Levet">
        <img src="https://github.com/Aleks-Levet.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>Aleks-Levet</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Founder, Coordinator & Developer</strong><br/>
      Main idea and owner.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/rKyzen">
        <img src="https://github.com/rKyzen.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>rKyzen</b></sub><br/>
        <i>(Shivank Dan)</i>
      </a>
    </td>
    <td align="left">
      <strong>Android App Developer</strong><br/>
      Implemented the real-time music stream.
    </td>
  </tr>
  <tr>
    <td width="100" align="center">
      <a href="https://github.com/oliver-lebaigue-bright-bench">
        <img src="https://github.com/oliver-lebaigue-bright-bench.png?size=100&mask=circle" width="50" style="border-radius: 50%; border: 2px solid #555;"><br/>
        <sub><b>Oliver Lebaigue</b></sub>
      </a>
    </td>
    <td align="left">
      <strong>Core Developer</strong>
      Enhancing the app + various nice additions
    </td>
  </tr>
</table>
