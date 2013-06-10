package com.jcommerce.core.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

import com.jcommerce.core.service.ReportManager;


public class ReportManagerImpl extends ManagerImpl implements ReportManager{

	private static int CHARTS_WIDTH = 700;
	private static int CHARTS_HEIGHT = 400;
	private String CHARTS_PATH = "";
	
	public String getPieChart(String type , String title , Map hashMap , Date startDate , Date endDate) {
		CHARTS_PATH = "C://" + type + startDate.getTime()+endDate.getTime()+".jpg";
		File file = new File(CHARTS_PATH);
		if(file.exists()){
			file.delete();
		}
		try {
			DefaultPieDataset dataset = new DefaultPieDataset();
			for(Object o : hashMap.keySet()){
				dataset.setValue(String.valueOf(o), Integer.parseInt((String) hashMap.get(o)));
			}
			JFreeChart chart = ChartFactory.createPieChart3D( title,       // 图表标题
				      dataset,     // 绘图数据集
				      false,       // 设定是否显示图例
				      false,       // 设定是否显示图例名称
				      false);      // 设定是否生成链接
			chart.setAntiAlias(false);
			
			PiePlot pieplot = (PiePlot)chart.getPlot();
			pieplot.setLabelFont(new Font("宋体", 0, 15));
	        pieplot.setNoDataMessage("NO DATA!");
	        pieplot.setCircular(true);
	        pieplot.setLabelGap(0.02D);
	        pieplot.setBackgroundPaint(new Color(199,237,204));
	                pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
	                "{0} {2}",
	                NumberFormat.getNumberInstance(),
	                new DecimalFormat("0.00%")));
	        pieplot.setForegroundAlpha(0.6f);
	                
	        LegendTitle legendtitle = new LegendTitle(chart.getPlot());
	        legendtitle.setWidth(100D);
	        BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
	       	blockcontainer.setBorder(new BlockBorder(1.0D, 1.0D, 1.0D, 1.0D));
	       
	       
			LabelBlock labelblock = new LabelBlock("Percent:", new Font("宋体", 1, 16));
			labelblock.setPadding(5D, 5D, 5D, 5D);
			blockcontainer.add(labelblock, RectangleEdge.TOP);
			BlockContainer blockcontainer1 = legendtitle.getItemContainer();
			blockcontainer1.setPadding(2D, 10D, 5D, 2D);
			blockcontainer.add(blockcontainer1);
			legendtitle.setWrapper(blockcontainer);
			legendtitle.setPosition(RectangleEdge.RIGHT);
			legendtitle.setHorizontalAlignment(HorizontalAlignment.LEFT);
			chart.addSubtitle(legendtitle);
			chart.setBackgroundPaint(new Color(199,237,204));
			
			TextTitle pcTitle = chart.getTitle();
			pcTitle.setFont(new Font("汉真广标", Font.BOLD, 21));
			pcTitle.setPaint(Color.RED);
			
			ChartUtilities.saveChartAsJPEG(file, chart, CHARTS_WIDTH, CHARTS_HEIGHT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CHARTS_PATH;
	}
	
	public byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e){
        }
        return null;
    }
}
