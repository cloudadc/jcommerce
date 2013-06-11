/**
* Author: Bob Chen
*/

package com.jcommerce.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class ClassGenerator {
    String daopkg = "com.jcommerce.core.dao";
    String daoimplpkg = "com.jcommerce.core.dao.impl";
    String servicepkg = "com.jcommerce.core.service";
    String serviceimplpkg = "com.jcommerce.core.service.impl";
    File targetPath = new File("C:\\Perforce-ROOT\\jcommerce\\core\\src1");
    File sourcePath = new File("C:\\Perforce-ROOT\\jcommerce\\core\\src");
    
    public void setDaoPackage(String daopkg) {
        this.daopkg = daopkg;
    }

    public void setDaoImplPackage(String daoimplpkg) {
        this.daoimplpkg = daoimplpkg;
    }

    public void setServicePackage(String servicepkg) {
        this.servicepkg = servicepkg;
    }

    public void setServiceImplPackage(String serviceimplpkg) {
        this.serviceimplpkg = serviceimplpkg;
    }

    public void setTargetPath(File targetPath) {
        this.targetPath = targetPath;
    }

    public void setSourcePath(File sourcePath) {
        this.sourcePath = sourcePath;
    }

    public void generate(Class modelClass) {
        System.out.println("Processing "+modelClass.getSimpleName()+"...");
        generateDAO(modelClass);
        generateDAOImpl(modelClass);
        generateService(modelClass);
        generateServiceImpl(modelClass);
        generateApplicationContext(modelClass);
    }

    public void change(Class modelClass) {
        System.out.println("Processing "+modelClass.getSimpleName()+"...");
        changeService(modelClass);
        changeServiceImpl(modelClass);
    }

    public void generateApplicationContext(Class modelClass) {
        String cn = modelClass.getSimpleName();
        String vn = cn.substring(0, 1).toLowerCase()+cn.substring(1);
        
        StringBuffer sb = new StringBuffer();
        sb.append("    <bean id=\""+cn+"DAO\" class=\""+daoimplpkg+"."+cn+"DAOImpl\">\n");
        sb.append("     <property name=\"sessionFactory\">\n");
        sb.append("         <ref local=\"sessionFactory\"/>\n");
        sb.append("     </property>\n");
        sb.append("    </bean>\n");
        sb.append("    <bean id=\""+cn+"Manager\" class=\""+serviceimplpkg+"."+cn+"ManagerImpl\">\n");
        sb.append("     <property name=\""+vn+"DAO\">\n");
        sb.append("         <ref local=\""+cn+"DAO\"/>\n");
        sb.append("     </property>\n");
        sb.append("    </bean>\n");
        
        System.out.println(sb.toString());
    }
    
    public void generateDAO(Class modelClass) {
        String cn = modelClass.getSimpleName();
        
        StringBuffer sb = new StringBuffer();
        sb.append("/**").append("\n");
        sb.append("* Author: Bob Chen").append("\n");
        sb.append("*/").append("\n");
        sb.append("\n");
        sb.append("package "+daopkg+";").append("\n");
        sb.append("\n");
        sb.append("import java.util.List;").append("\n");
        sb.append("\n");
        sb.append("import "+modelClass.getName()+";").append("\n");
        sb.append("\n");
        
        sb.append("public interface "+cn+"DAO extends DAO {").append("\n");
        sb.append("    public List<"+cn+"> get"+cn+"List();").append("\n");
        sb.append("\n");
        sb.append("    public "+cn+" get"+cn+"(String id);").append("\n");
        sb.append("\n");
        sb.append("    public void save"+cn+"("+cn+" obj);").append("\n");
        sb.append("\n");
        sb.append("    public void remove"+cn+"(String id);").append("\n");
        sb.append("}").append("\n");
        
        File path = new File(targetPath, daopkg.replace(".", File.separator));
        if (!path.exists()) {
            path.mkdirs();
        }
        
        try {
            FileOutputStream fos = new FileOutputStream(new File(path, cn+"DAO.java"));
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public void generateDAOImpl(Class modelClass) {
        String cn = modelClass.getSimpleName();
        
        StringBuffer sb = new StringBuffer();
        sb.append("/**").append("\n");
        sb.append("* Author: Bob Chen").append("\n");
        sb.append("*/").append("\n");
        sb.append("\n");
        sb.append("package "+daoimplpkg+";").append("\n");
        sb.append("\n");
        sb.append("import java.util.List;").append("\n");
        sb.append("import "+modelClass.getName()+";").append("\n");
        sb.append("import "+daopkg+"."+cn+"DAO;").append("\n");
        sb.append("\n");
        //public class BonusTypeDAOImpl extends DAOImpl implements BonusTypeDAO {
        sb.append("public class "+cn+"DAOImpl extends DAOImpl implements "+cn+"DAO {").append("\n");
        sb.append("    public "+cn+"DAOImpl() {").append("\n");
        sb.append("        modelClass = "+cn+".class;\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public List<"+cn+"> get"+cn+"List() {").append("\n");
        sb.append("        return getList();\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public "+cn+" get"+cn+"(String id) {").append("\n");
        sb.append("        return ("+cn+")getById(id);\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public void save"+cn+"("+cn+" obj) {").append("\n");
        sb.append("        save(obj);\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public void remove"+cn+"(String id) {").append("\n");
        sb.append("        deleteById(id);\n");
        sb.append("    }\n");
        sb.append("}").append("\n");
        
        File path = new File(targetPath, daoimplpkg.replace(".", File.separator));
        if (!path.exists()) {
            path.mkdirs();
        }
        
        try {
            FileOutputStream fos = new FileOutputStream(new File(path, cn+"DAOImpl.java"));
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public void generateService(Class modelClass) {
        String cn = modelClass.getSimpleName();
        
        StringBuffer sb = new StringBuffer();
        sb.append("/**").append("\n");
        sb.append("* Author: Bob Chen").append("\n");
        sb.append("*/").append("\n");
        sb.append("\n");
        sb.append("package "+servicepkg+";").append("\n");
        sb.append("\n");
        sb.append("import java.util.List;").append("\n");
        sb.append("import "+modelClass.getName()+";").append("\n");
        sb.append("\n");
        
        sb.append("public interface "+cn+"Manager extends Manager {").append("\n");
        sb.append("    public "+cn+" initialize("+cn+" obj);").append("\n");
        sb.append("\n");
        sb.append("    public List<"+cn+"> get"+cn+"List();").append("\n");
        sb.append("\n");
        sb.append("    public "+cn+" get"+cn+"(String id);").append("\n");
        sb.append("\n");
        sb.append("    public void save"+cn+"("+cn+" obj);").append("\n");
        sb.append("\n");
        sb.append("    public void remove"+cn+"(String id);").append("\n");
        sb.append("}").append("\n");
        
        File path = new File(targetPath, servicepkg.replace(".", File.separator));
        if (!path.exists()) {
            path.mkdirs();
        }
        
        try {
            FileOutputStream fos = new FileOutputStream(new File(path, cn+"Manager.java"));
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public void generateServiceImpl(Class modelClass) {
        String cn = modelClass.getSimpleName();
        
        StringBuffer sb = new StringBuffer();
        sb.append("/**").append("\n");
        sb.append("* Author: Bob Chen").append("\n");
        sb.append("*/").append("\n");
        sb.append("\n");
        sb.append("package "+serviceimplpkg+";").append("\n");
        sb.append("\n");
        sb.append("import java.util.List;").append("\n");
        sb.append("\n");
        sb.append("import org.apache.commons.logging.Log;").append("\n");
        sb.append("import org.apache.commons.logging.LogFactory;").append("\n");
        sb.append("import org.hibernate.Hibernate;").append("\n");
        sb.append("\n");
        sb.append("import "+daopkg+"."+cn+"DAO;").append("\n");
        sb.append("import "+modelClass.getName()+";").append("\n");
        sb.append("import "+servicepkg+"."+cn+"Manager;").append("\n");
        sb.append("\n");
        
        sb.append("public class "+cn+"ManagerImpl extends ManagerImpl implements "+cn+"Manager {").append("\n");
        sb.append("    private static Log log = LogFactory.getLog("+cn+"ManagerImpl.class);").append("\n");
        sb.append("    private "+cn+"DAO dao;").append("\n");
        sb.append("\n");
        sb.append("    public void set"+cn+"DAO("+cn+"DAO dao) {").append("\n");
        sb.append("        this.dao = dao;\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public "+cn+" initialize("+cn+" obj) {").append("\n");
        sb.append("        if (obj != null && !Hibernate.isInitialized(obj)) {\n");
        sb.append("            obj = dao.get"+cn+"(obj.getId());\n");
        sb.append("        }\n");
        sb.append("        return obj;\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public List<"+cn+"> get"+cn+"List() {").append("\n");
        sb.append("        return dao.get"+cn+"List();\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public "+cn+" get"+cn+"(String id) {").append("\n");
        sb.append("        "+cn+" obj = dao.get"+cn+"(id);\n");
        sb.append("        return obj;\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public void save"+cn+"("+cn+" obj) {").append("\n");
        sb.append("        dao.save"+cn+"(obj);\n");
        sb.append("    }\n");
        sb.append("\n");
        sb.append("    public void remove"+cn+"(String id) {").append("\n");
        sb.append("        dao.remove"+cn+"(id);\n");
        sb.append("    }\n");
        sb.append("}").append("\n");
        
        File path = new File(targetPath, serviceimplpkg.replace(".", File.separator));
        if (!path.exists()) {
            path.mkdirs();
        }

        try {
            FileOutputStream fos = new FileOutputStream(new File(path, cn+"ManagerImpl.java"));
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    public void changeService(Class modelClass) {
        String cn = modelClass.getSimpleName();
        
        File file = new File(sourcePath, servicepkg.replace(".", File.separator) + File.separator+ cn+"Manager.java");
        if (!file.exists()) {
            System.out.println("File not exists: " + file);
            return;
        }
        
        try {
            List<String> lines = new ArrayList<String>();        
            LineNumberReader reader = new LineNumberReader(new FileReader(file));
            for ( ;;) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                
                lines.add(line);
            }
            reader.close();
            
            removeLine(lines, "get"+cn+"List(int firstRow, int maxRow)");
            removeLine(lines, "get"+cn+"Count(Criteria criteria)");
            removeLine(lines, "get"+cn+"List(Criteria criteria)");
            removeLine(lines, "get"+cn+"List(int firstRow, int maxRow, Criteria criteria)");
            
            removeLine(lines, "import com.jcommerce.core.service.Criteria;");
            
            int index = 0;
            for (String line : lines) {
                if (line.contains("import com.jcommerce.core.model.")) {
                    index = lines.indexOf(line) + 1;
                    break;
                }
            }
            
            lines.add(index, "import com.jcommerce.core.service.Criteria;");
            
            for (String line : lines) {
                if (line.contains("initialize")) {
                    index = lines.indexOf(line) + 2;
                    break;
                }
            }
            
            lines.add(index++, "    public List<"+cn+"> get"+cn+"List(int firstRow, int maxRow);");
            lines.add(index++, "");
            lines.add(index++, "    public int get"+cn+"Count(Criteria criteria);");
            lines.add(index++, "");
            lines.add(index++, "    public List<"+cn+"> get"+cn+"List(Criteria criteria);");
            lines.add(index++, "");
            lines.add(index++, "    public List<"+cn+"> get"+cn+"List(int firstRow, int maxRow, Criteria criteria);");
            lines.add(index++, "");
            
            File path = new File(targetPath, servicepkg.replace(".", File.separator));
            if (!path.exists()) {
                path.mkdirs();
            }
        
            FileOutputStream fos = new FileOutputStream(new File(path, cn+"Manager.java"));
            for (String line : lines) {
                fos.write((line + "\n").getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public void changeServiceImpl(Class modelClass) {
        String cn = modelClass.getSimpleName();
        
        File file = new File(sourcePath, serviceimplpkg.replace(".", File.separator) + File.separator+ cn+"ManagerImpl.java");
        if (!file.exists()) {
            System.out.println("File not exists: " + file);
            return;
        }
        
        try {
            List<String> lines = new ArrayList<String>();        
            LineNumberReader reader = new LineNumberReader(new FileReader(file));
            for ( ;;) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                
                lines.add(line);
            }
            reader.close();
            
            removeMethod(lines, "get"+cn+"List(int firstRow, int maxRow)");
            removeMethod(lines, "get"+cn+"Count(Criteria criteria)");
            removeMethod(lines, "get"+cn+"List(Criteria criteria)");
            removeMethod(lines, "get"+cn+"List(int firstRow, int maxRow, Criteria criteria)");
            
            removeLine(lines, "import com.jcommerce.core.service.Criteria");
            
            int index = 0;
            for (String line : lines) {
                if (line.contains("import com.jcommerce.core.model.")) {
                    index = lines.indexOf(line) + 1;
                    break;
                }
            }
            
            lines.add(index, "import com.jcommerce.core.service.Criteria;");
            
            for (String line : lines) {
                if (line.contains("this.dao = dao;")) {
                    index = lines.indexOf(line) + 1;
                    line = lines.get(index);
                    if (line.contains("super.setDao(dao);")) {
                        lines.remove(index);
                    }
                    break;
                }
            }
            
            lines.add(index, "        super.setDao(dao);");
            
            for (String line : lines) {
                if (line.contains("initialize")) {
                    index = lines.indexOf(line) + 7;
                    break;
                }
            }
            
            lines.add(index++, "    public List<"+cn+"> get"+cn+"List(int firstRow, int maxRow) {");
            lines.add(index++, "        List list = getList(firstRow, maxRow);");
            lines.add(index++, "        return (List<"+cn+">)list;");
            lines.add(index++, "    }");
            lines.add(index++, "");
            lines.add(index++, "    public int get"+cn+"Count(Criteria criteria) {");
            lines.add(index++, "        return getCount(criteria);");
            lines.add(index++, "    }");
            lines.add(index++, "");
            lines.add(index++, "    public List<"+cn+"> get"+cn+"List(Criteria criteria) {");
            lines.add(index++, "        List list = getList(criteria);");
            lines.add(index++, "        return (List<"+cn+">)list;");
            lines.add(index++, "    }");
            lines.add(index++, "");
            lines.add(index++, "    public List<"+cn+"> get"+cn+"List(int firstRow, int maxRow, Criteria criteria) {");
            lines.add(index++, "        List list = getList(firstRow, maxRow, criteria);");
            lines.add(index++, "        return (List<"+cn+">)list;");
            lines.add(index++, "    }");
            lines.add(index++, "");
            
            File path = new File(targetPath, serviceimplpkg.replace(".", File.separator));
            if (!path.exists()) {
                path.mkdirs();
            }
        
            FileOutputStream fos = new FileOutputStream(new File(path, cn+"ManagerImpl.java"));
            for (String line : lines) {
                fos.write((line + "\n").getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void removeMethod(List<String> lines, String method) {
        for (String line : lines) {
            if (line.contains(method)) {
                int index = lines.indexOf(line);
                lines.remove(index);
                for ( ; ; ) {
                    line = lines.get(index);
                    lines.remove(index);
                    if (line.startsWith("    }")) {
                        line = lines.get(index);
                        if (line.trim().length() == 0) {
                            lines.remove(index);
                        }
                        break;
                    }
                }
                
                break;
            }
        }
    }
  
    private void removeLine(List<String> lines, String method) {
        for (String line : lines) {
            if (line.contains(method)) {
                int index = lines.indexOf(line);
                lines.remove(index);
                line = lines.get(index);
                if (line.trim().length() == 0) {
                    lines.remove(index);
                }
                break;
            }
        }
    }
  
    private static void generate() {
        ClassGenerator gen = new ClassGenerator();
        System.out.println("Output path: "+gen.targetPath);
        gen.generate(com.jcommerce.core.model.AdminLog.class);
//        gen.generate(com.jcommerce.core.model.GoodsGallery.class);
//        gen.generate(com.jcommerce.core.model.Goods.class);
//        gen.generate(com.jcommerce.core.model.Gallery.class);
//        gen.generate(com.jcommerce.core.model.Brand.class);
//        gen.generate(com.jcommerce.core.model.BonusType.class);
//        gen.generate(com.jcommerce.core.model.Category.class);
//        gen.generate(com.jcommerce.core.model.GoodsAttribute.class);
//        gen.generate(com.jcommerce.core.model.Shipping.class);
//        gen.generate(com.jcommerce.core.model.AccountLog.class);
//        gen.generate(com.jcommerce.core.model.AdminAction.class);
//        gen.generate(com.jcommerce.core.model.AdminMessage.class);
//        gen.generate(com.jcommerce.core.model.AdminUser.class);
//        gen.generate(com.jcommerce.core.model.AffiliateLog.class);
//        gen.generate(com.jcommerce.core.model.Agency.class);
//        gen.generate(com.jcommerce.core.model.Attribute.class);
//        gen.generate(com.jcommerce.core.model.BookingGoods.class);
//        gen.generate(com.jcommerce.core.model.Card.class);
//        gen.generate(com.jcommerce.core.model.Cart.class);
//        gen.generate(com.jcommerce.core.model.CollectGoods.class);
//        gen.generate(com.jcommerce.core.model.ErrorLog.class);
//        gen.generate(com.jcommerce.core.model.FailedLogin.class);
//        gen.generate(com.jcommerce.core.model.FriendLink.class);
//        gen.generate(com.jcommerce.core.model.Gallery.class);
//        gen.generate(com.jcommerce.core.model.GoodsActivity.class);
//        gen.generate(com.jcommerce.core.model.GoodsType.class);
//        gen.generate(com.jcommerce.core.model.MailTemplate.class);
//        gen.generate(com.jcommerce.core.model.Order.class);
//        gen.generate(com.jcommerce.core.model.OrderAction.class);
//        gen.generate(com.jcommerce.core.model.OrderGoods.class);
//        gen.generate(com.jcommerce.core.model.Pack.class);
//        gen.generate(com.jcommerce.core.model.PayLog.class);
//        gen.generate(com.jcommerce.core.model.Payment.class);
//        gen.generate(com.jcommerce.core.model.Region.class);
//        gen.generate(com.jcommerce.core.model.Session.class);
//        gen.generate(com.jcommerce.core.model.ShippingArea.class);
//        gen.generate(com.jcommerce.core.model.Stats.class);
//        gen.generate(com.jcommerce.core.model.User.class);
//        gen.generate(com.jcommerce.core.model.UserAccount.class);
//        gen.generate(com.jcommerce.core.model.UserAddress.class);
//        gen.generate(com.jcommerce.core.model.UserBonus.class);
//        gen.generate(com.jcommerce.core.model.UserRank.class);        
    }
    
    private static void change() {
        ClassGenerator gen = new ClassGenerator();
        System.out.println("Read path: "+gen.sourcePath);
        System.out.println("Output path: "+gen.targetPath);
//        gen.change(com.jcommerce.core.model.AccountLog.class);
//        gen.change(com.jcommerce.core.model.AdminAction.class);
//        gen.change(com.jcommerce.core.model.AdminLog.class);
//        gen.change(com.jcommerce.core.model.AdminMessage.class);
//        gen.change(com.jcommerce.core.model.AdminUser.class);
//        gen.change(com.jcommerce.core.model.AdPosition.class);
//        gen.change(com.jcommerce.core.model.Adsense.class);
//        gen.change(com.jcommerce.core.model.Advertisement.class);
//        gen.change(com.jcommerce.core.model.AffiliateLog.class);
//        gen.change(com.jcommerce.core.model.Agency.class);
//        gen.change(com.jcommerce.core.model.Article.class);
//        gen.change(com.jcommerce.core.model.ArticleCategory.class);
//        gen.change(com.jcommerce.core.model.Attribute.class);
//        gen.change(com.jcommerce.core.model.AuctionLog.class);
//        gen.change(com.jcommerce.core.model.AutoManage.class);
//        gen.change(com.jcommerce.core.model.BonusType.class);
//        gen.change(com.jcommerce.core.model.BookingGoods.class);
//        gen.change(com.jcommerce.core.model.Brand.class);
//        gen.change(com.jcommerce.core.model.Card.class);
//        gen.change(com.jcommerce.core.model.Cart.class);
//        gen.change(com.jcommerce.core.model.Category.class);
//        gen.change(com.jcommerce.core.model.CollectGoods.class);
//        gen.change(com.jcommerce.core.model.Comment.class);
//        gen.change(com.jcommerce.core.model.Crons.class);
//        gen.change(com.jcommerce.core.model.EmailList.class);
//        gen.change(com.jcommerce.core.model.EmailSendList.class);
//        gen.change(com.jcommerce.core.model.ErrorLog.class);
//        gen.change(com.jcommerce.core.model.FailedLogin.class);
//        gen.change(com.jcommerce.core.model.FavourableActivity.class);
//        gen.change(com.jcommerce.core.model.Feedback.class);
//        gen.change(com.jcommerce.core.model.FriendLink.class);
//        gen.change(com.jcommerce.core.model.Gallery.class);
////        gen.change(com.jcommerce.core.model.Goods.class);
//        gen.change(com.jcommerce.core.model.GoodsActivity.class);
//        gen.change(com.jcommerce.core.model.GoodsArticle.class);
//        gen.change(com.jcommerce.core.model.GoodsAttribute.class);
//        gen.change(com.jcommerce.core.model.GoodsType.class);
//        gen.change(com.jcommerce.core.model.GroupGoods.class);
//        gen.change(com.jcommerce.core.model.Keywords.class);
//        gen.change(com.jcommerce.core.model.LinkGoods.class);
//        gen.change(com.jcommerce.core.model.MailTemplate.class);
//        gen.change(com.jcommerce.core.model.MemberPrice.class);
//        gen.change(com.jcommerce.core.model.Navigation.class);
//        gen.change(com.jcommerce.core.model.Order.class);
//        gen.change(com.jcommerce.core.model.OrderAction.class);
//        gen.change(com.jcommerce.core.model.OrderGoods.class);
//        gen.change(com.jcommerce.core.model.Pack.class);
//        gen.change(com.jcommerce.core.model.PayLog.class);
//        gen.change(com.jcommerce.core.model.Payment.class);
//        gen.change(com.jcommerce.core.model.Plugins.class);
//        gen.change(com.jcommerce.core.model.Region.class);
//        gen.change(com.jcommerce.core.model.SearchEngine.class);
//        gen.change(com.jcommerce.core.model.Session.class);
//        gen.change(com.jcommerce.core.model.SessionsData.class);
//        gen.change(com.jcommerce.core.model.Shipping.class);
//        gen.change(com.jcommerce.core.model.ShippingArea.class);
//        gen.change(com.jcommerce.core.model.ShopConfig.class);
//        gen.change(com.jcommerce.core.model.SnatchLog.class);
//        gen.change(com.jcommerce.core.model.Stats.class);
//        gen.change(com.jcommerce.core.model.Tag.class);
//        gen.change(com.jcommerce.core.model.Topic.class);
//        gen.change(com.jcommerce.core.model.User.class);
//        gen.change(com.jcommerce.core.model.UserAccount.class);
//        gen.change(com.jcommerce.core.model.UserAddress.class);
//        gen.change(com.jcommerce.core.model.UserBonus.class);
//        gen.change(com.jcommerce.core.model.UserRank.class);
//        gen.change(com.jcommerce.core.model.VirtualCard.class);
//        gen.change(com.jcommerce.core.model.Vote.class);
//        gen.change(com.jcommerce.core.model.VoteLog.class);
//        gen.change(com.jcommerce.core.model.VoteData.class);
//        gen.change(com.jcommerce.core.model.Wholesale.class);
    }
    
    public static void main(String[] args) {
        generate();
    }
}
