Project Outline:
- Query the NVD Server using their REST API interface.
  Using the following API :
  https://nvd.nist.gov/developers/vulnerabilitie9s

Pass CVE-ID as input (command line parameter), extract the below fields from the JSON Output  and print the below information
- CVE-ID
- Description
- CVSS Vector String (both V2 and V3)
- CVE Updated Timestamp

Used Spring-boot framework and WebClient API Client to fetch the JSON response NVD Server.

To start the application :
- Ensure You Have Gradle Installed: Make sure you have Gradle installed on your system.
  You can check if Gradle is installed by running gradle -v in your terminal.
  The project was developed using gradle version 8.1.1
  If it's not installed, you can download and install it from the official website: https://gradle.org/install/
- Setup Up Java Home in the env variable. The project was developed using Open JDK 20.0.1 .
- Open the root directory i.e ..\demo run command gradlew clean build
- Run gradlew bootRun --args=CVE-2019-1010218
    - Here CVE-2019-1010218 is the CVE-ID passed as command line input.
