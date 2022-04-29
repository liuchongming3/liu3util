package org.liu3.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SplicingMvn {

//    static String BASE_COMMAND_1 = "mvn install:install-file "
//            + "-Dfile=%s -DgroupId=%s -DartifactId=%s -Dversion=%s "
//            + "-Dpackaging=jar -s D:\\Tools\\mavenrepository\\setting.xml "
//            ;
    static String BASE_COMMAND_2 = "mvn install:install-file "
            + "-Dfile=%s -DgroupId=%s -DartifactId=%s -Dversion=%s "
            + "-Dpackaging=jar -s D:\\Tools\\mavenrepository\\setting_kaiya_ali.xml"
            ;
    
    public static void main(String[] args) throws Exception {
        
        SplicingMvn mvn = new SplicingMvn();
        try {
            StringBuilder sb = mvn.getMvnCommand(new File("D:\\Tools\\mavenrepository\\com\\longfor"));
            
            System.out.println(sb);
                
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public StringBuilder getMvnCommand(File path) throws IOException, DocumentException {
        List<File> pomList = getPom(path);
        List<Install> list = new ArrayList<>();
        for(File f : pomList) {
            list.add(getPomInfo(f));
        }
        
        StringBuilder command = new StringBuilder();
        
        for(Install ins : list) {
            String pomName = ins.getFile().getCanonicalPath();
            String jarName = pomName.substring(0, pomName.lastIndexOf("pom"))+"jar";
            String comm = String.format(BASE_COMMAND_2, jarName, ins.getGroupId(), ins.getArtifactId(), ins.getVersion());
            command.append(comm).append("\r\n");
        }
        return command;
    }
    
    
    public Install getPomInfo(File pom) throws DocumentException {
        SAXReader reader = new SAXReader();   
        Document doc = reader.read(pom);   
        Element project = doc.getRootElement();
        //System.out.println(root.asXML());
        //Element project = root.element("project");
        String groupId = project.elementText("groupId");
        String artifactId = project.elementText("artifactId");
        String version = project.elementText("version");
        return new Install(pom, groupId, artifactId, version);
    }
    
    
    public List<File> getPom(File top) throws IOException{
        List<File> list = new ArrayList<>();
        if(top.isFile()) {
            if(isPom(top)) {
                list.add(top);             
            }
        }else {
            File[] fs = top.listFiles();
            for(File f : fs) {
                list.addAll(getPom(f));
            }
        }
        return list;
    }
    
    private boolean isPom(File file) {
        String name = file.getName();
        return name.length() == name.lastIndexOf(".pom")+4;
    }
    
    class Install{
        File file;
        String groupId;
        String artifactId;
        String version;
        
        
        public Install() {
            super();
        }

        public Install(File file, String groupId, String artifactId, String version) {
            super();
            this.file = file;
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
        
    }
}
