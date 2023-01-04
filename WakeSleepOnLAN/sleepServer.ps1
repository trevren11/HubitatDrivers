# Put somewhere like C:\Users\<user>\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup\sleepServer.ps1
# wherever shell:startup leads you, can do win+R and type `shell:startup` and it will open the folder your PC will look to start automatically
# Start-Process "C:\path\to\startup.bat" # Start non minimized
Start-Process "C:\path\to\startup.bat" -WindowStyle Minimized
