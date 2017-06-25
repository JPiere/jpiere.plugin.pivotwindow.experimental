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

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;

import jpiere.plugin.pivotwindow.model.CalloutPivotWindow;
import jpiere.plugin.pivotwindow.model.MPivotWindow;
import jpiere.plugin.pivotwindow.model.MPivotWindowDefault;
import jpiere.plugin.pivotwindow.model.MPivotWindowDefaultField;
import jpiere.plugin.pivotwindow.model.MPivotWindowField;

/**
 *  JPiere Plugin Pivot Window Callout Factory
 *
 *  JPIERE-0356:JPPS
 *
 *  @author Hideaki Hagiwara(h.hagiwara@oss-erp.co.jp)
 *
 */
public class JPierePluginPivotWindowCalloutFactory implements IColumnCalloutFactory {

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {

		List<IColumnCallout> list = new ArrayList<IColumnCallout>();

		if(tableName.startsWith("JP_Pivot"))
		{
			if(tableName.equals(MPivotWindow.Table_Name))
			{
				if(columnName.equals(MPivotWindow.COLUMNNAME_AD_Table_ID))
				{
						list.add(new CalloutPivotWindow());
				}
				
			}else if(tableName.equals(MPivotWindowField.Table_Name)){
				if(columnName.equals(MPivotWindowField.COLUMNNAME_AD_Element_ID)
						|| columnName.equals(MPivotWindowField.COLUMNNAME_AD_Reference_ID) )
				{
						list.add(new CalloutPivotWindow());
				}
			}

		}

		return list != null ? list.toArray(new IColumnCallout[0]) : new IColumnCallout[0];
	}

}
