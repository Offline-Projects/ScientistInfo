#
# This is the Scientist Info Servlets and front pages.
#

1. Do Maven build, and will package war in the target folder, options:
	1)mvn clean install
	2)mvn clean package

2. For deployment, copy the genetated ScientistInfo.v2.war to tomcat webapps folder, and start the tomcat.
Then, copy the index.html/css/imgs/js/map from ${tomcat_home}/webapps/ScientistInfo.v2 to ${Apache_Http_home}/htdocs

3. For properties, copy the solr.properties in properties folder to C:/property
