@echo off

@REM echo Running batch script to listen to sleep commands at 192.168.1.66:8000
MODE CON COLS=50 LINES=2 
python C:\path\to\sleepServer.py
pause