Project to test page layout, JS errors, resources loaded
=================================================

Testing method
-----------------------
Project uses current eMarketing Sanity project as a dependency.

Tests are run using FireFox browser, which needs to be pre-installed. 
Additionally there must be resources available on test machine.
To consult correct content of resources folder please contact eMarketing test automation team. Must include

- har Viewer
- correct netExport plugin
- correct firebug plugin


Available settings
-----------------------

- url - report URL used for logging results, when using Jenkins then set to ${BUILD_URL} 
- suiteName - name of the suite to run
- preparation-mode - use false for screen comparison
- browser - set to FF
- capability - FF webDriver capability selection
- base-path - path to patterns
- firefox.firebug.export-path - temp working path
- resource - path to resources needed for test, use C:\test\
- reportSubdirectory - name of the project folder in the repository, in this case "sanityRodip"

**Default values are not set in project pom.xm file, need to be handled in mvn command**

Running tests
-----------------------
`mvn clean test -Durl=${BUILD_URL} -DsuiteName=SuiteRodipQA -Dpreparation-mode=false -Dbrowser=FF -DreportSubdirectory=sanityRodip -Dcapability=firebug+jserrors -Dbase-path=src/test/resources/patterns/ -Dfirefox.firebug.export-path=C:\\\\tmp -Dresource-path=C:\test\`

