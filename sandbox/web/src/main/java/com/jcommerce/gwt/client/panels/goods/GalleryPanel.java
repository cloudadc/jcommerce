/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsGallery;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.FileUploader;

class GalleryPanel extends LayoutContainer {
    VerticalPanel contentPanel = new VerticalPanel();

    List<ImageUploadPanel> uploaders = new ArrayList<ImageUploadPanel>();

    // The Galleries of the Goods
    private List<BeanObject> galleries = new ArrayList<BeanObject>();

    // The IDs of the Galleries deleted
    private List<String> deleteGallID = new ArrayList<String>();

    List<Map> galls = new ArrayList<Map>();

    // LayoutContainer lc2 = new LayoutContainer();

    // The panel shows the original galleries
    VerticalPanel imageV = null;

    private Boolean editting = false;

    private BeanObject goods = null;

    // The IDs of the Galleries
    private String[] ids = null;

    GalleryPanel() {
        this.editting = false;
        this.goods = null;
    }

    GalleryPanel(boolean editting, BeanObject goods) {
        this.editting = editting;
        this.goods = goods;
    }

    public List<FileUploader> getUploaders() {
        List<FileUploader> ret = new ArrayList<FileUploader>();
        for (ImageUploadPanel panel : uploaders) {
            ret.add(panel.getFileUploader());
        }
        return ret;
    }

    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<String, Object>();
        int i = 0;
        for (Iterator<ImageUploadPanel> it = uploaders.iterator(); it.hasNext();) {
            ImageUploadPanel panel = it.next();
            String imgUrl = panel.getFileUploader().getValue();
            if (!"".equals(imgUrl) && imgUrl != null) {
                Map<String, Object> gall = new HashMap<String, Object>();
                String desc = panel.getDesc();
                gall.put(IGoodsGallery.DESCRIPTION, desc);
                gall.put(IGoodsGallery.IMAGEURL, imgUrl);
                // imgUrl:images/100705/4bd0c857-1ee1-4f3e-8f09-88d90546c5d0.jpg
                int index = imgUrl.indexOf("/");
                index = imgUrl.indexOf("/", index + 1);
                String thumbUrl = imgUrl.substring(0, index + 1) + "thumb/thumb_" + imgUrl.substring(index + 1);
                gall.put(IGoodsGallery.THUMNURL, thumbUrl);
                gall.put(IGoodsGallery.ORIGINALIMAGE, panel.getFileUploader().getValue());
                galls.add(gall);
            }
            i++;
        }

        if (editting == true && goods != null) {
            deleteInDB();
            CheckDescription();
        }
        values.put(IGoods.GALLERIES, galls);
        return values;
    }

    // Check if the Description has been modified or not.
    private void CheckDescription() {
        if (imageV != null) {
            for (Iterator it = galleries.iterator(); it.hasNext();) {
                BeanObject gallNotDel = (BeanObject) it.next();
                Map<String, Object> gallND = new HashMap<String, Object>();
                String des = gallNotDel.getString(IGoodsGallery.DESCRIPTION);

                int m = imageV.getWidgetCount();
                while (m > 0) {
                    HorizontalPanel hp = (HorizontalPanel) imageV.getWidget(m - 1);
                    int n = hp.getWidgetCount();
                    while (n > 0) {
                        VerticalPanel vp = (VerticalPanel) hp.getWidget(n - 1);
                        String gallId = ((ImagePanel) vp.getWidget(1)).getId();
                        if (gallId.equals(gallNotDel.getString(IGoodsGallery.ID))) {
                            des = ((ImagePanel) vp.getWidget(1)).getDesc();
                        }
                        n--;
                    }
                    m--;
                }

                gallND.put(IGoodsGallery.DESCRIPTION, des);
                gallND.put(IGoodsGallery.IMAGEURL, gallNotDel.getString(IGoodsGallery.IMAGEURL));
                gallND.put(IGoodsGallery.THUMNURL, gallNotDel.getString(IGoodsGallery.THUMNURL));
                gallND.put(IGoodsGallery.ORIGINALIMAGE, gallNotDel.getString(IGoodsGallery.ORIGINALIMAGE));
                galls.add(gallND);
                new DeleteService().deleteBean(ModelNames.GALLERY, gallNotDel.getString(IGoodsGallery.ID), null);
            }
        }
    }

    // Delete the data in DB
    private void deleteInDB() {
        for (Iterator ite = deleteGallID.iterator(); ite.hasNext();) {
            String deleteID = (String) ite.next();
            for (Iterator iter = galleries.iterator(); iter.hasNext();) {
                BeanObject bo = (BeanObject) iter.next();
                if (deleteID.equals(bo.getString(IGoodsGallery.ID))) {
                    galleries.remove(bo);
                    break;
                }
            }
            new DeleteService().deleteBean(ModelNames.GALLERY, deleteID, null);
        }
    }

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
      
        SimplePanel holder = new SimplePanel();
        add(holder);
        holder.add(contentPanel);

        if (editting == true && goods != null) {
            final String[] ids = goods.getIDs(IGoods.GALLERIES);
            this.ids = ids;
            if (ids != null && ids.length != 0) {
                setGalleries(ids);
                new WaitService(new WaitService.Job() {
                    public boolean isReady() {
                        return galleries.size() != 0;
                    }

                    public void run() {
                        imageV = showGalleries();
                        contentPanel.add(imageV);
                        CreateFirstUploaderPanel();
                    }
                });
            } else {
                CreateFirstUploaderPanel();
            }
        } else {
            CreateFirstUploaderPanel();
        }
        
        this.layout(true);
    }

    private void CreateFirstUploaderPanel() {
        Button link = new Button("[+]");
        createUploader(link);

        link.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                addUploader();
            }
        });
    }

    // Set the value to the Galleries
    private void setGalleries(String[] ids) {
        new ReadService().getBeans(ModelNames.GALLERY, ids, new ReadService.Listener() {
            public void onSuccess(List<BeanObject> result) {
                galleries = result;
            }
        });
    }

    // Show the original galleries of the Goods
    private VerticalPanel showGalleries() {
        VerticalPanel imageV = new VerticalPanel();
        HorizontalPanel imageH = new HorizontalPanel();
        int i = 0;
        for (Iterator<BeanObject> it = galleries.iterator(); it.hasNext();) {
            if (i % 4 == 0) {
                imageH = new HorizontalPanel();
                imageV.add(imageH);
            }
            i++;

            BeanObject gall = it.next();

            VerticalPanel gallImg = new VerticalPanel();
            ImagePanel image = new ImagePanel(gall);
            gallImg.setBorderWidth(1);
            final Button link = new Button("[-]");
            gallImg.add(link);
            gallImg.add(image);
            gallImg.setCellHorizontalAlignment(link, HasHorizontalAlignment.ALIGN_CENTER);
            imageH.add(gallImg);
            imageH.setSpacing(10);
            link.addSelectionListener(new SelectionListener<ButtonEvent>() {
                public void componentSelected(ButtonEvent ce) {
                    final VerticalPanel vp = (VerticalPanel) link.getParent();
                    final HorizontalPanel hp = (HorizontalPanel) vp.getParent();
                    String id = ((ImagePanel) vp.getWidget(1)).getId();
                    deleteGallID.add(id);
                    hp.remove(vp);
                }
            });
        }

        return imageV;
    }

    private String getImagePath(String imageUrl) {
        String path = GWT.getModuleBaseURL() + "dynaImageService.do?fileName=" + imageUrl;
        return path;
    }

    private void addUploader() {
        Button link = new Button("[-]");
        final ImageUploadPanel panel = createUploader(link);

        link.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                contentPanel.remove(panel);
                uploaders.remove(panel);
            }
        });
    }

    private ImageUploadPanel createUploader(Button link) {
        ImageUploadPanel panel = new ImageUploadPanel(link);
        uploaders.add(panel);

        contentPanel.add(panel);

        return panel;
    }

    class ImagePanel extends VerticalPanel {
        TextBox des = new TextBox();
        String id;
        
        ImagePanel(BeanObject gall) {
            id = gall.getString(IGoodsGallery.ID);
            
            setBorderWidth(1);
            String imgPath = gall.getString(IGoodsGallery.THUMNURL);
            Image img = new Image(getImagePath(imgPath));
            des.setText(gall.getString(IGoodsGallery.DESCRIPTION));
            add(img);
            add(des);
            setCellHorizontalAlignment(img, HasHorizontalAlignment.ALIGN_CENTER);
            setCellVerticalAlignment(des, HasVerticalAlignment.ALIGN_BOTTOM);
            setPixelSize(150, 150);
        }
        
        String getDesc() {
            return des.getText();
        }

        String getId() {
            return id;
        }
    }
    
    class ImageUploadPanel extends HorizontalPanel {
        FileUploader imgUpload;
        TextBox txt;

        ImageUploadPanel(Button link) {
            imgUpload = new FileUploader();
            add(link);

            Label label = new Label(" Description");
            txt = new TextBox();
            add(label);
            add(txt);

            add(new Label(" Upload File"));
            imgUpload.addAllowedTypes(new String[] { ".jpg", ".gif" });
            add(imgUpload);
        }

        FileUploader getFileUploader() {
            return imgUpload;
        }

        String getDesc() {
            return txt.getText();
        }
    }
}
