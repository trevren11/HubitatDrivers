/* groovylint-disable CompileStatic, DuplicateStringLiteral, LineLength, MethodReturnTypeRequired, NoDef */
/*
 *    Wake On Lan Device
 *    Author: Trevor Renshaw, built partially from ramdev (Ramdev Shallem)
 */
metadata {
    definition(name: 'WakeAndSleepOnLan Device', namespace: 'trevren11', author: 'trevren11') {
        capability 'Actuator'
        capability 'Switch'
    }

    preferences {
        input(name:'myMac', type: 'text', required: true, title: 'MAC of workstation')
        input(name:'mySecureOn', type: 'text', required: false, title: 'SecureOn', description:'"SecureOn". The NIC is a hexadecimal password of 6 bytes. Example: \"EF4F34A2C43F\"')
        input(name:'myIP', type: 'text', required: false, title: 'IP Address', description:'Use this for accessing remote computers outside the local LAN. Defaults to all the devices inside the LAN (255.255.255.255)')
        input(name:'myPort', type: 'number', required: false, title: 'Port', description:'Default: 7', defaultValue :'7')
        input(name:'myOffURL', type: 'text', required: false, title: 'Off URL', description:'You can have a server that lets you turn this device off http://localhost:8080')
    }
}

def on() {
    sendEvent(name: 'switch', value: 'on', descriptionText: "${device.displayName} switch is on")
    String secureOn = mySecureOn ?: '000000000000'
    int port = myPort ?: 7
    String ip = myIP ?: '255.255.255.255'
    String macHEX = myMac.replaceAll('-', '').replaceAll(':', '').replaceAll(' ', '')
    log.info "Sent WOL to $myMac"
    String command = "FFFFFFFFFFFF$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$macHEX$secureOn"
    def myHubAction = new hubitat.device.HubAction(command,
                           hubitat.device.Protocol.LAN,
                           [type: hubitat.device.HubAction.Type.LAN_TYPE_UDPCLIENT,
                            destinationAddress: "$ip:$port",
                            encoding: hubitat.device.HubAction.Encoding.HEX_STRING])
    sendHubCommand(myHubAction)
    log.debug "Sent magic packet $command to $ip:$port"
}

def installed() {
    log.debug 'installed()'
}

def off() {
    sendEvent(name: 'switch', value: 'off', descriptionText: "${device.displayName} switch is off")
    String offURL = myOffURL
    log.debug "Sending off command to $offURL"
    try {
        httpGet(offURL) { resp ->
            if (resp.data) { log.debug "${resp.data }" }
            if (resp.success) {
                sendEvent(name: 'switch', value: 'on', isStateChange: true)
            }
        }
    /* groovylint-disable-next-line CatchException */
    } catch (Exception e) {
        log.warn "ON call failed with message: ${e.message}"
    }
}

def updated() {
    log.info 'updated'
    device.updateDataValue('MAC', myMac)
    device.updateDataValue('SecureOn', mySecureOn)
    device.updateDataValue('IP', myIP)
    device.updateDataValue('Port', myPort ? "$myPort" : '')
    device.updateDataValue('Off URL', myOffURL ? "$myOffURL" : '')
}

def configure() {
    log.info 'configure'
    sendEvent(name: 'numberOfButtons', value: 1)
}
