## TL;DR
Turn on and off a Windows PC from Hubitat and also works with Google Home automations

If you configure a simple python server (included) you can have it run to listen to a simple page request. This is how you can setup the WOL (Wake on LAN) and sleep as well quite simply

## Setup
1. Add both app and device drivers to hubitat, instructions are fairly simple and already created [here](https://community.hubitat.com/t/release-wake-on-lan-wake-up-your-computers-via-the-hub/78362)
2. Configure your pc and get it to turn on the pc from a sleeping state
3. Add the included python server to your pc you want to sleep
4. Add a bat or powershell etc script to start the script when your pc starts (run shell:startup in file explorer or run command)

Note: The python server and auto run is a bit more than needed, I just like it to run minimized and say it is working, you can remove all of that as it adds no true functionality


## Motivation
I had a plex server that I wanted to be able to turn on and off with a simple command or use easily in automations and I don't like virtual switches to create a bridge, the app built by ramdev is awesome for WOL, but not for sleeping and I wanted a simple way to make it a fully featured on/off app and device combo for hubitat


## Appendix
Useful for getting back into a switch that is recognized by Google Home, this app and device were previously not working with the code from ramdev so I made it just a regular switch, which also helps simplify the code
https://docs2.hubitat.com/developer/driver/overview


You can hit the endpoint to stop your pc by getting the server output and just loading it in a web browser, you will have to allow the connections from outside sources
Example load up `192.168.1.1:8000` and it should make the pc sleep