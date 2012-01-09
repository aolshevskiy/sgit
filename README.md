Super lightweight Git repository browser inspired by [klaus](https://github.com/jonashaag/klaus)

See demo here: http://sgit.demonico.cloudbees.net/

##Usage

1. Download latest standalone jar: http://github.com/downloads/siasia/sgit/sgit-1.0-SNAPSHOT-standalone.jar
1. Run it passing in list of repositories as a system property:

        java -Drepositories=demo/src/main/resources/xsbt-web-plugin.git:demo/src/main/resources/sgit.git\
        -jar sgit-1.0-SNAPSHOT-standalone.jar