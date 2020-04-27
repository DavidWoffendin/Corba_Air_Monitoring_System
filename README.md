# nox_repository

Java project based on the CORBA architecture for a university assignment to create a nox gas monitoring system.

## Running Structre

The air nox system contain three individual systems:

* TMC - The Monitoring Center is the top of the network structure and is the end point for all alarms
* TLS - The Local Station, recieves readings and alerts the Monitoring Centre once it confirms a legitimate alarm
* TMS - The Monitoring Station, captures air readings and relays them to their connected Local Server

### Commands for project Use

1. Generate IDL: 
    /home/sudwoffendin/jacorb-3.9/bin/idl com/u1654949/airnox.idl

2. Start the name server: 
    bash /home/sudwoffendin/jacorb-3.9/bin/jaco org.jacorb.naming.NameServer -Djacorb.naming.ior_filename=/home/sudwoffendin/git/airnox_repository/nameserver.ior

3. Start the Monitoring Centre:
    /usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_42me98gh9rqzwrtceq8uzietx.argfile com.u1654949.airnox.mc.TMCInterface -ORBInitRef.NameService=file:/home/sudwoffendin/git/airnox_repository/nameserver.ior -Djacorb.log.default.verbosity=2

4. Start the Local Server:
    /usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_42me98gh9rqzwrtceq8uzietx.argfile com.u1654949.airnox.ls.TLSInterface -ORBInitRef.NameService=file:/home/sudwoffendin/git/airnox_repository/nameserver.ior -Djacorb.log.default.verbosity=2

5. Start the Monitoring Centre:
    /usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_42me98gh9rqzwrtceq8uzietx.argfile com.u1654949.airnox.ms.TMSInterface -ORBInitRef.NameService=file:/home/sudwoffendin/git/airnox_repository/nameserver.ior -Djacorb.log.default.verbosity=2

### Requirements

* Jacorb 3.9
* OpenJDK 11