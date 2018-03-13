#
# This is the Scientist Info Servlets and front pages.
#

# Do Maven build, and will package war in the target folder
mvn clean install
#OR
mvn clean package

# For deployment, copy the genetated Scientist.v2.war to tomcat webapps folder, and start the tomcat
# then copy the index.html/css/imgs/js/map from ${tomcat_home}/webapps/Scientist.v2 to ${Apache_Http_home}/htdocs
