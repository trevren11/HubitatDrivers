# Written by trevren11
import socket
import win32com.client
import subprocess
from http.server import BaseHTTPRequestHandler, HTTPServer


def shutdown(self):
    # Send a response to the client
    self.send_response(200)
    self.send_header("Content-type", "text/plain")
    self.end_headers()
    self.wfile.write(b"Turning Off Machine")
    self.finish()
    voice.Speak("Response received. Turning off machine")

    # sleeps the pc so it can be resumed again and again
    subprocess.run(
        ["rundll32.exe", "powrprof.dll,SetSuspendState", "0,1,0"], check=True)
    # sys.exit() # If you don't want it to run forever, you can close the server the first time it exits


class RequestHandler(BaseHTTPRequestHandler):
    def log_request(self, code='-', size='-'):
        pass  # Do nothing, suppress log output

    def do_POST(self):
        # Run the Windows command to put the computer to sleep
        print("Received do_POST, shutting down")
        shutdown(self)

    def do_GET(self):
        print("Received do_GET, shutting down")
        shutdown(self)


# Get the IP address of the network interface
ip_address = socket.gethostbyname(socket.gethostname())

# Create the server and start listening for requests
server = HTTPServer((ip_address, 8000), RequestHandler)
print(f"Listening for sleep commands: {ip_address}:8000")

voice = win32com.client.Dispatch("SAPI.SpVoice")
voice.Speak("Starting service to listen to sleep events")

server.serve_forever()
