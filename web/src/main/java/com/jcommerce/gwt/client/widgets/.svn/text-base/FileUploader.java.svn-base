/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.ReadService;

public class FileUploader extends Composite {
    private FormPanel formPanel;
    private List<String> types = new ArrayList<String>();
    private String fileName;
    private boolean success = true;
    private boolean needFileName = false;
    
    //storeType= "img"       -->Store original picture      
    //           "thumb"     -->Make it to a thumb picture to store    
    //           "img_thumb" -->Store its original and thumb picture   
    //If you want to upload files of other types, you need to modify some codes in FileUploadServlet.
    private String storeType = "img";
    
	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
		formPanel.setAction(GWT.getModuleBaseURL() + "/uploadService?storeType=" + storeType);
	}

	public FileUploader() {
        formPanel = new FormPanel();
        formPanel.setAction(GWT.getModuleBaseURL() + "/uploadService?storeType=" + storeType);
        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
        formPanel.setMethod(FormPanel.METHOD_POST);

        FlowPanel panel = new FlowPanel();
        formPanel.setWidget(panel);

        final FileUpload fileUpload = new FileUpload();
        fileUpload.setName("file");
        panel.add(fileUpload);

        formPanel.addSubmitHandler(new FormPanel.SubmitHandler() {
            
            public void onSubmit(SubmitEvent event) {
                if (fileUpload.getFilename().length() == 0) {
                    event.cancel();
                    success = true;
                    needFileName = false;
                } else if (!isTypeAllowed(fileUpload.getFilename())) {
                    String msg = "Acceptable file type should be ";
                    boolean first = true;
                    for (String type : types) {
                        if (!first) {
                            msg += ",";
                        }
                        first = false;
                        msg += type;
                    }
                    Window.alert(msg);
                    event.cancel();
                    success = false;
                    needFileName = false;
                } else {
                    success = true;
                    needFileName = true;
                }
            }
        });

        formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            
            public void onSubmitComplete(SubmitCompleteEvent event) {
                String results = event.getResults();
                fileName = results;
                if (fileName != null && fileName.contains(">")) {
                    fileName = fileName.substring(fileName.indexOf(">") +1);
                }
                if (fileName != null && fileName.contains("<")) {
                    fileName = fileName.substring(0, fileName.indexOf("<"));
                }
                if (fileName != null && fileName.endsWith(";")) {
                    fileName = fileName.substring(0, fileName.length() -1);
                }
                // Window.alert(event.getResults());
            }
        });
        
//        Image image = iShop.images.no().createImage();
//        panel.add(image);
//        
//        image.addClickListener(new ClickListener() {
//            public void onClick(Widget sender) {
//            }
//        });
        
        initWidget(formPanel);        
    }

	public void setImagePath(final String imagePath) {
        if((imagePath!=null)&&(!imagePath.equals("null"))&&(!imagePath.equals(""))&&(!imagePath.equals("IS NULL"))){
            Image imgInfo = new Image("hasPic.png");
            imgInfo.setTitle("Click to view");
            ((FlowPanel)formPanel.getWidget()).add(imgInfo);
            
            imgInfo.addClickHandler(new ClickHandler() {
                
                public void onClick(ClickEvent arg0) {
                    DialogBox dlg = createDialogBox(imagePath);
                    dlg.center();
                    dlg.show();
                }
            });
        }
	}
	
    public void setImageInfo(String modelName, String id, final String attr) {        
        new ReadService().getBean(modelName, id, new ReadService.Listener() {
            public void onSuccess(BeanObject bean){
                String imagePath = bean.getString(attr);
                setImagePath(imagePath);
            }
        });
    }
	
    public boolean submit() {
        formPanel.submit();
        return success;
    }

    public boolean isFinish() {
        if (needFileName) {
            return fileName != null;
        } else {
            return true;
        }        
    }

    public String getValue() {
        return fileName;
    }
    
    public void setValue(String value) {
        this.fileName = value;
    }

    /**
     * the type should be .jpg, .gif, etc 
     */
    public void addAllowedType(String type) {
        types.add(type.toLowerCase());
    }

    public void addAllowedTypes(String[] types) {
        if (types != null) {
            for (String type : types) {
                this.types.add(type.toLowerCase());
            }
        }
    }
    
    private boolean isTypeAllowed(String name) {
        name = name.toLowerCase();
        for (String type : types) {
            if (name.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    private DialogBox createDialogBox(String imgUrl) {
        // Create a dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Show Image");

        // Create a table to layout the content
        VerticalPanel dialogContents = new VerticalPanel();
        Image showImg = new Image(getImagePath(imgUrl));
        dialogContents.add(showImg);
        
        showImg.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                dialogBox.hide();
            }
          });

        
        Button btnOK = new Button("OK", new ClickHandler() {
           
            public void onClick(ClickEvent arg0) {
                dialogBox.hide();
            }
        });
        dialogContents.setSpacing(4);
        dialogContents.setHorizontalAlign(HorizontalAlignment.CENTER);
        dialogContents.add(btnOK);
        dialogBox.setWidget(dialogContents);
        
        return dialogBox;
    }
    
    private String getImagePath(String imageUrl) {
        String path = GWT.getModuleBaseURL() + "dynaImageService.do?fileName=" + imageUrl;
        return path;
    }
}
