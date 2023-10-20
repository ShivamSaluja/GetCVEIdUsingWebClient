Project Outline:
1. Query the NVD Server using their REST API interface.
  Using the following API : https://services.nvd.nist.gov/rest/json/cvehistory/2.0?cveId=CVE-2019-1010218

2. The cveId in the above api must be passed  as command line parameter.

3. From the api response, extract the below fields from the JSON Output and print the below information :

  3.1 CVE-ID
  3.2 Description
  3.3 CVSS Vector String (both V2 and V3)
  3.4 CVE Updated Timestamp

Used Spring-boot framework and WebClient API Client to fetch the JSON response NVD Server.

To start the application :
- Ensure You Have Gradle Installed: Make sure you have Gradle installed on your system.
  You can check if Gradle is installed by running gradle -v in your terminal.
  The project was developed using gradle version 8.1.1
  If it's not installed, you can download and install it from the official website: https://gradle.org/install/
- Setup Up Java Home in the env variable. The project was developed using Open JDK 21 .
- Open the root directory i.e ..\demo run command : gradlew clean build
- Run : gradlew bootRun --args=CVE-2019-1010218
    - Here CVE-2019-1010218 is the CVE-ID passed as command line input.

TODO Task - Refer this blog and enhance the code - https://habeebcycle.medium.com/spring-boot-webflux-resilient-service-with-resilience4j-retry-circuitbreaker-with-router-dbbc37427fc7
