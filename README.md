# nox_repository
Java project based on corba

## Useful Commands

/home/sudwoffendin/jacorb-3.9/bin/idl com/u1654949/airnox.idl

bash jaco org.jacorb.naming.NameServer -Djacorb.naming.ior_filename=/home/sudwoffendin/git/airnox_repository/nameserver.ior

/usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_42me98gh9rqzwrtceq8uzietx.argfile com.u1654949.airnox.mc.TMCInterface -ORBInitRef.NameService=file:/home/sudwoffendin/git/airnox_repository/nameserver.ior -Djacorb.log.default.verbosity=2

/usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_42me98gh9rqzwrtceq8uzietx.argfile com.u1654949.airnox.ls.TLSInterface -ORBInitRef.NameService=file:/home/sudwoffendin/git/airnox_repository/nameserver.ior -Djacorb.log.default.verbosity=2

/usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 @/tmp/cp_42me98gh9rqzwrtceq8uzietx.argfile com.u1654949.airnox.ms.TMSInterface -ORBInitRef.NameService=file:/home/sudwoffendin/git/airnox_repository/nameserver.ior -Djacorb.log.default.verbosity=2