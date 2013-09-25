package com.jcommerce.web.test.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MavenBuildError {

	public static void main(String[] args) throws IOException {
		
		File baseFile = new File("/home/kylin/work/tmp/gwt");
		
		String content = "/home/kylin/work/project/jcommerce/web/src/main/java:/home/kylin/work/project/jcommerce/web/src/main/resources:/home/kylin/work/project/jcommerce/web/target/www/WEB-INF/classes:/home/kylin/.m2/repository/com/jcommerce/jcommerce-core/1.0/jcommerce-core-1.0.jar:/home/kylin/.m2/repository/org/springframework/spring-context/2.5.6/spring-context-2.5.6.jar:/home/kylin/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:/home/kylin/.m2/repository/org/springframework/spring-beans/2.5.6/spring-beans-2.5.6.jar:/home/kylin/.m2/repository/org/springframework/spring-core/2.5.6/spring-core-2.5.6.jar:/home/kylin/.m2/repository/org/springframework/spring-orm/2.5.6/spring-orm-2.5.6.jar:/home/kylin/.m2/repository/org/springframework/spring-tx/2.5.6/spring-tx-2.5.6.jar:/home/kylin/.m2/repository/javax/jdo/jdo-api/3.0/jdo-api-3.0.jar:/home/kylin/.m2/repository/javax/transaction/transaction-api/1.1/transaction-api-1.1.jar:/home/kylin/.m2/repository/org/compass/compass/1.1/compass-1.1.jar:/home/kylin/.m2/repository/commons-codec/commons-codec/1.8/commons-codec-1.8.jar:/home/kylin/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:/home/kylin/.m2/repository/org/freemarker/freemarker/2.3.10/freemarker-2.3.10.jar:/home/kylin/.m2/repository/jfree/jfreechart/1.0.13/jfreechart-1.0.13.jar:/home/kylin/.m2/repository/org/antlr/antlr-runtime/3.2/antlr-runtime-3.2.jar:/home/kylin/.m2/repository/org/antlr/stringtemplate/3.2/stringtemplate-3.2.jar:/home/kylin/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/home/kylin/.m2/repository/javax/mail/mail/1.4.5/mail-1.4.5.jar:/home/kylin/.m2/repository/javax/activation/activation/1.1/activation-1.1.jar:/home/kylin/.m2/repository/jasperreports/jasperreports/3.5.0/jasperreports-3.5.0.jar:/home/kylin/.m2/repository/commons-beanutils/commons-beanutils/1.7.0/commons-beanutils-1.7.0.jar:/home/kylin/.m2/repository/commons-collections/commons-collections/20040616/commons-collections-20040616.jar:/home/kylin/.m2/repository/commons-digester/commons-digester/2.1/commons-digester-2.1.jar:/home/kylin/.m2/repository/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:/home/kylin/.m2/repository/com/lowagie/itext/2.1.0/itext-2.1.0.jar:/home/kylin/.m2/repository/bouncycastle/bcmail-jdk14/136/bcmail-jdk14-136.jar:/home/kylin/.m2/repository/bouncycastle/bcprov-jdk14/136/bcprov-jdk14-136.jar:/home/kylin/.m2/repository/jfree/jcommon/1.0.15/jcommon-1.0.15.jar:/home/kylin/.m2/repository/xml-apis/xml-apis/1.3.02/xml-apis-1.3.02.jar:/home/kylin/.m2/repository/eclipse/jdtcore/3.2.0.v_658/jdtcore-3.2.0.v_658.jar:/home/kylin/.m2/repository/net/sf/opencsv/opencsv/1.7/opencsv-1.7.jar:/home/kylin/.m2/repository/org/htmlparser/htmlparser/2.1/htmlparser-2.1.jar:/home/kylin/.m2/repository/org/htmlparser/htmllexer/2.1/htmllexer-2.1.jar:/home/kylin/.m2/repository/org/datanucleus/datanucleus-core/3.2.3/datanucleus-core-3.2.3.jar:/home/kylin/.m2/repository/com/googlecode/google-api-translate-java/0.92/google-api-translate-java-0.92.jar:/home/kylin/.m2/repository/opensymphony/xwork/2.0.0/xwork-2.0.0.jar:/home/kylin/.m2/repository/ognl/ognl/2.6.9/ognl-2.6.9.jar:/home/kylin/.m2/repository/org/apache/struts/struts2-core/2.1.2/struts2-core-2.1.2.jar:/home/kylin/.m2/repository/com/opensymphony/xwork/2.1.1/xwork-2.1.1.jar:/home/kylin/.m2/repository/opensymphony/ognl/2.6.11/ognl-2.6.11.jar:/home/kylin/.m2/repository/commons-fileupload/commons-fileupload/1.2.1/commons-fileupload-1.2.1.jar:/home/kylin/.m2/repository/commons-io/commons-io/1.3.2/commons-io-1.3.2.jar:/home/kylin/.m2/repository/net/sf/jasperreports/jasperreports/4.6.0/jasperreports-4.6.0.jar:/home/kylin/.m2/repository/org/codehaus/castor/castor/1.2/castor-1.2.jar:/home/kylin/.m2/repository/org/apache/poi/poi-ooxml/3.7/poi-ooxml-3.7.jar:/home/kylin/.m2/repository/org/apache/poi/poi/3.7/poi-3.7.jar:/home/kylin/.m2/repository/org/apache/poi/poi-ooxml-schemas/3.7/poi-ooxml-schemas-3.7.jar:/home/kylin/.m2/repository/org/apache/xmlbeans/xmlbeans/2.3.0/xmlbeans-2.3.0.jar:/home/kylin/.m2/repository/stax/stax-api/1.0.1/stax-api-1.0.1.jar:/home/kylin/.m2/repository/org/apache/geronimo/specs/geronimo-stax-api_1.0_spec/1.0/geronimo-stax-api_1.0_spec-1.0.jar:/home/kylin/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/home/kylin/.m2/repository/org/codehaus/jackson/jackson-core-asl/1.9.4/jackson-core-asl-1.9.4.jar:/home/kylin/.m2/repository/org/codehaus/jackson/jackson-mapper-asl/1.9.4/jackson-mapper-asl-1.9.4.jar:/home/kylin/.m2/repository/com/google/gwt/gwt-user/2.5.1/gwt-user-2.5.1.jar:/home/kylin/.m2/repository/org/json/json/20090211/json-20090211.jar:/home/kylin/.m2/repository/com/extjs/gxt/2.2.0/gxt-2.2.0.jar:/home/kylin/.m2/repository/javax/validation/validation-api/1.0.0.GA/validation-api-1.0.0.GA.jar:/home/kylin/.m2/repository/javax/validation/validation-api/1.0.0.GA/validation-api-1.0.0.GA-sources.jar:/home/kylin/.m2/repository/com/google/gwt/gwt-user/2.5.1/gwt-user-2.5.1.jar:/home/kylin/.m2/repository/com/google/gwt/gwt-dev/2.5.1/gwt-dev-2.5.1.jar";
		String[] array = content.split(":");
		for(String item : array){
			if(item.endsWith(".jar")) {
				File source = new File(item);
				copyFileUsingChannel(source, new File(baseFile, source.getName()));
				System.out.println("copy " + source.getName() + " done");
			}
			
		}
		
		
	}
	
	private static void copyFileUsingChannel(File source, File dest) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destChannel = new FileOutputStream(dest).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}
	}

}
