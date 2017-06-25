/******************************************************************************
 * Product: JPiere                                                            *
 * Copyright (C) Hideaki Hagiwara (h.hagiwara@oss-erp.co.jp)                  *
 *                                                                            *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY.                          *
 * See the GNU General Public License for more details.                       *
 *                                                                            *
 * JPiere is maintained by OSS ERP Solutions Co., Ltd.                        *
 * (http://www.oss-erp.co.jp)                                                 *
 *****************************************************************************/
package jpiere.plugin.pivotwindow.factory;

import java.util.logging.Level;

import org.adempiere.webui.factory.IFormFactory;
import org.adempiere.webui.panel.ADForm;
import org.compiere.util.CLogger;

import jpiere.plugin.pivotwindow.form.AbstractPivotWindowForm;


/**
 *  JPiere Plugin Pivot Window Form Factory
 *
 *  JPIERE-0356
 *
 *  @author Hideaki Hagiwara（h.hagiwara@oss-erp.co.jp）
 *
 */
public class JPierePluginPivotWindowFormFactory implements IFormFactory{

	private static final CLogger log = CLogger.getCLogger(JPierePluginPivotWindowFormFactory.class);

	@Override
	public ADForm newFormInstance(String formName) {
		Object form = null;
	     if (formName.startsWith("JP_PivotWindow_ID="))
	     {

	    	String JP_PivotWindow_ID = formName.substring("JP_PivotWindow_ID=".length());

	       	ClassLoader loader = this.getClass().getClassLoader();
	       	Class<?> clazz = null;

			try
    		{
    			//	Create instance w/o parameters
        		clazz = loader.loadClass("jpiere.plugin.pivotwindow.form.JPierePivotWindow");
    		}
    		catch (Exception e)
    		{
    			if (log.isLoggable(Level.INFO))
    				log.log(Level.INFO, e.getLocalizedMessage(), e);
    		}


			if (clazz != null) {
				try
	    		{
	    			form = clazz.newInstance();
	    		}
	    		catch (Exception e)
	    		{
	    			if (log.isLoggable(Level.WARNING))
	    				log.log(Level.WARNING, e.getLocalizedMessage(), e);
	    		}
			}

		      if (form != null) {
		    	  if (form instanceof AbstractPivotWindowForm )
					{
		    		  	AbstractPivotWindowForm  controller = (AbstractPivotWindowForm) form;
						controller.createPivotWindow(JP_PivotWindow_ID);
						ADForm adForm = controller.getForm();
						adForm.setICustomForm(controller);
						return adForm;
					}
		     }
	     }
	     return null;
	}


}
