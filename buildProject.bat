echo off
cls
echo Project builder wrapper for gradlew v. 1.2 beta
echo Made by NuarkNoir
pause
gradlew assembleDebug installDebug
copy "./app/build/outputs/RuMineMobile-Latest-debug.apk" /B "./release/RuMineMobile-Latest-DEBUG.apk" /B
echo Done!
pause